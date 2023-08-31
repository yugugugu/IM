package com.yugugugu.server.ddd.infrastructure.common.SensitiveWord;

import com.yugugugu.server.ddd.infrastructure.common.SensitiveWord.WordContext;

import java.util.Map;

/**
 * @author liuyu
 * @date 2023/8/20
 **/
public class WordFilter {

    /**
     * 敏感词表
     */
    private final Map wordMap;
    private char ReplCh = '*';

    /**
     * 构造函数
     */
    public WordFilter(WordContext context) {
        this.wordMap = context.getWordMap();
    }

    public WordFilter(WordContext context,char replCh) {
        this.wordMap = context.getWordMap();
        this.ReplCh = replCh;
    }


    /**
     * 替换敏感词
     *
     * @param text 输入文本
     */
    public String replace(final String text) {
        return replace(text, 0, ReplCh);
    }

    /**
     * 替换敏感词
     *
     * @param text   输入文本
     * @param symbol 替换符号
     */
    public String replace(final String text, final char symbol) {
        return replace(text, 0, symbol);
    }

    /**
     * 替换敏感词
     *
     * @param text   输入文本
     * @param skip   文本距离
     * @param symbol 替换符号
     */
    public String replace(final String text, final int skip, final char symbol) {
        char[] charset = text.toCharArray();
        for (int i = 0; i < charset.length; i++) {
            FlagIndex fi = getFlagIndex(charset, i, skip);
            if (fi.isFlag()) {
                if (!fi.isWhiteWord()) {
                    for (int j : fi.getIndex()) {
                        charset[j] = symbol;
                    }
                } else {
                    i += fi.getIndex().size() - 1;
                }
            }
        }
        return new String(charset);
    }

    private FlagIndex getFlagIndex(char[] charset, int i, int skip) {
        return null;
    }

}
