package org.tyf.com.tinylog;

import org.tyf.com.config.Config;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *   @desc : 自定义日志
 *   @auth : tyf
 *   @date : 2025-06-30 14:25:40
*/
public class TinyLog {


    public enum Level {
        TRACE(1), DEBUG(2), INFO(3);

        private final int priority;
        Level(int p) { priority = p; }
        public int getPriority() { return priority; }
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


    // 大于系统配置的日志级别才输出
    public static void log(Level level, String message) {
        if (level.getPriority() >= Config.currentLevel.getPriority()) {
            String timestamp = DATE_FORMAT.format(new Date());
            System.out.printf("[%s] [%s] %s%n", timestamp, level, message);
        }
    }



}
