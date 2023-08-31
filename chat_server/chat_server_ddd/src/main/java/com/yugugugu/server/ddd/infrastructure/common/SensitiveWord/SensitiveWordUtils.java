package com.yugugugu.server.ddd.infrastructure.common.SensitiveWord;

import org.springframework.scheduling.concurrent.ScheduledExecutorTask;

import java.io.*;
import java.util.*;

public class SensitiveWordUtils {
    /**
     * 词库上下文环境
     */
    public static final WordContext CONTENT = new WordContext();

    public static final WordFilter WORD_FILTER = new WordFilter(CONTENT);
}
