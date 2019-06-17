package com.future.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 *
 * @author Admin
 * @version: 1.0
 */
public class LogUtil {

    private Logger logger;

    private LogUtil(Logger logger) {
        this.logger = logger;
    }

    public static <T> LogUtil logger(Class<T> clazz) {
        return new LogUtil(LoggerFactory.getLogger(clazz));
    }

    public static LogUtil logger(Object object) {
        return logger(object.getClass());
    }

    public static LogUtil logger() {
        return logger(LogUtil.class);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void debug(String format, Object... arguments) {
        logger.debug(format, arguments);
    }

    public void debug(String msg, Exception e) {
        logger.debug(msg, e);
    }

    public void debug(Exception e) {
        logger.debug("", e);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void info(String format, Object... arguments) {
        logger.info(format, arguments);
    }

    public void info(String msg, Exception e) {
        logger.info(msg, e);
    }

    public void info(Exception e) {
        logger.info("", e);
    }

    public void error(String msg, Exception e) {
        logger.error(msg, e);
    }

    public void error(Exception e) {
        logger.error("", e);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void error(String format, Object... arguments) {
        logger.error(format, arguments);
    }

    public void warn(String msg, Exception e) {
        logger.warn(msg, e);
    }

    public void warn(Exception e) {
        logger.warn("", e);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void warn(String format, Object... arguments) {
        logger.warn(format, arguments);
    }
}