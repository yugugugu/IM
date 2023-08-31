package com.yugugugu.server.ddd.infrastructure.common.SensitiveWord;

import com.yugugugu.server.ddd.infrastructure.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author liuyu
 * @date 2023/8/20
 **/
public class WordContext {
    /**
     * 敏感词字典
     */
    private final Map wordMap= new HashMap(1024);
    /**
     * 是否已初始化
     */
    private boolean init;
    /**
     * 黑名单列表地址
     */
    private final String blackList;
    /**
     * 白名单列表地址
     */
    private final String whiteList;

    public WordContext() {
        this.blackList = "/blacklist.txt";
        this.whiteList = "/whitelist.txt";
        initKeyWord();
    }

    public WordContext(String blackList, String whiteList) {
        this.blackList = blackList;
        this.whiteList = whiteList;
        initKeyWord();
    }


    public Map getWordMap() {
        return wordMap;
    }
    /**
     * 初始化构建前缀树
     */
    private synchronized void initKeyWord() {
        try {
            if (!init) {
                // 将敏感词库加入到HashMap中
                addWord(readWordFile(blackList), Constants.WordType.BLACK);
                // 将非敏感词库也加入到HashMap中
                addWord(readWordFile(whiteList), Constants.WordType.WHITE);
            }
            init = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addWord(Set<String> wordSet, Constants.WordType wordType) {
        Map nowMap;
        Map<String,String> newWordMap;
        for(String key : wordSet){
            nowMap = wordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);
                Object wordMap = nowMap.get(key);
                if (wordMap != null) nowMap= (Map)wordMap;
                else {
                    //构建一个新的Map,注意这里初始化容量为4
                    newWordMap = new HashMap<>(4);
                    newWordMap.put("isEnd", String.valueOf(Constants.EndType.HAS_NEXT.ordinal()));
                    nowMap.put(keyChar, newWordMap);
                    nowMap = newWordMap;
                }
                if (i == key.length()-1){
                    //最后一个设置一下结束和wordttype
                    nowMap.put("isEnd", String.valueOf(Constants.EndType.IS_END.ordinal()));
                    nowMap.put("isWhiteWord", String.valueOf(wordType.ordinal()));
                }
            }
        }

    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     */
    private Set<String> readWordFile(String file) throws Exception {
        Set<String> set;
        // 字符编码
        String encoding = "UTF-8";
        try (InputStreamReader read = new InputStreamReader(
                this.getClass().getResourceAsStream(file), encoding)) {
            set = new HashSet<>();
            BufferedReader bufferedReader = new BufferedReader(read);
            String txt;
            // 读取文件，将文件内容放入到set中
            while ((txt = bufferedReader.readLine()) != null) {
                set.add(txt);
            }
        }
        // 关闭文件流
        return set;
    }


    /**
     * 在线删除敏感词
     *
     * @param wordList 敏感词列表
     * @param wordType 黑名单 BLACk，白名单WHITE
     */
    public synchronized void  removeWord(Set<String> wordList, Constants.WordType wordType){
        Map nowMap;
        for (String key : wordList) {
            List<Map> cacheList = new ArrayList<>();//记录下走的map的路径
            nowMap = wordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);
                Object map = nowMap.get(keyChar);

                if (map != null) {
                    nowMap = (Map) map;
                    cacheList.add(nowMap);
                } else return;

                if (i == key.length() - 1) {
                    char[] keys = key.toCharArray();
                    boolean cleanable = false;
                    char lastChar = 0;

                    for (int j = cacheList.size() - 1; j >= 0; j--) {
                        Map cacheMap = cacheList.get(j);
                        if (j == cacheList.size() - 1) {
                            if (String.valueOf(Constants.WordType.BLACK.ordinal()).equals(cacheMap.get("isWhiteWord"))) {
                                if (wordType == Constants.WordType.WHITE) {
                                    return;
                                }
                            }
                            if (String.valueOf(Constants.WordType.WHITE.ordinal()).equals(cacheMap.get("isWhiteWord"))) {
                                if (wordType == Constants.WordType.BLACK) {
                                    return;
                                }
                            }
                            cacheMap.put("isEnd",Constants.EndType.HAS_NEXT.ordinal());
                            if (cacheMap.size() == 2) {//大小为二表示没有下面的树了，这个是叶子节点
                                cleanable = true;
                                continue;
                            }
                        }

                        if (cleanable) {
                            Object isEnd = cacheMap.get("isEnd");
                            if (String.valueOf(Constants.EndType.IS_END.ordinal()).equals(isEnd)) {
                                cleanable = false;
                            }
                            cacheMap.remove(lastChar);
                        }
                        lastChar = keys[j];
                    }
                    if (cleanable) {
                        wordMap.remove(lastChar);
                    }
                }
            }
        }
    }
}
