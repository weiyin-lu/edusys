package online.weiyin.edusys.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author 卢子昂
 * @version 1.0.0
 * @classname redisUtil
 * @description Redis键生成工具类
 */
public class RedisUtil {
    /**
     * 获取当日redis中记录考勤信息的key值
     *
     * @param year 数据源学年
     * @return 规范格式key
     */
    public static String getKey(String year) {
        // 格式化当天日期作为redis键的一部分
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String current = now.format(formatter);
        // 组成键名
        return year + ":" + current;
    }

    /**
     * 获取指定日期中记录考勤信息的key值
     * @param year
     * @return
     */
    public static String getKey(String year,LocalDate date) {
        // 格式化当天日期作为redis键的一部分
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String current = date.format(formatter);
        // 组成键名
        return year + ":" + current;
    }
}
