package com.yugugugu.server.ddd.infrastructure.common;

import org.springframework.scheduling.concurrent.ScheduledExecutorTask;

import java.io.*;
import java.util.*;

public class SensitiveWordUtils {
    private String ENCODING = "UTF-8";
    private String PATH_RELATIVE = "/src/main/resources/static/senc.txt";//脱敏文本地址配置
    private HashMap sensitiveWordMap;//脱敏前缀树
    private Integer MIN_CONTAIN=2;//包含敏感词的最多字数
    public static int minMatchTYpe = 1;      //最小匹配规则
    public static int maxMatchType = 2;      //最大匹配规则

    private static SensitiveWordUtils util;
    //懒加载获得敏感词工具类
    public static SensitiveWordUtils getInstance(){
        if (util == null){
            util = new SensitiveWordUtils();
            util.readSensitiveWordFromFile();//构造前缀树
            return util;
        }
        return util;
    }



    private Set<String> readSensitiveWordFromFile(){
        Set<String> wordSet = null;
        //获取项目路径
        String projectPath = System.getProperty("user.dir");
        //拼接得到敏感词汇的路径
        String path=projectPath + PATH_RELATIVE;
        File file = new File(path);
        try {
            //指定编码读取文件
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
            //判断文件是否存在
            if (file.isFile() && file.exists()) {
                //初始化wordSet
                wordSet = new HashSet<String>();
                //br用于读取文件的每一行
                BufferedReader br = new BufferedReader(read);
                String txt = null;
                while ((txt = br.readLine()) != null) {
                    wordSet.add(txt);
                }
                br.close();
            }else{
                throw new Exception("文件错误或文件不存在！");
            }
            read.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordSet;
    }

    /**
     * 将从文本读出来的敏感词表处理成明杆词树
     * @param keyWordSet
     */
    private void addSensitiveWordToHashMap(Set<String> keyWordSet){
        sensitiveWordMap = new HashMap<>(keyWordSet.size());
        String key = null;
        Map nowMap = null;
        //对每个敏感词进行遍历
        Iterator iter = keyWordSet.iterator();
        while (iter.hasNext()){
            key = (String) iter.next();
            nowMap = sensitiveWordMap;


        }
    }

    private String replaceSensitiveWord(String txt,int matchType,String repl){
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
//            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    private Set<String> getSensitiveWord(String txt, int matchType) {
        return null;
    }
}
