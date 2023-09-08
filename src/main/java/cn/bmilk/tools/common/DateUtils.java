package cn.bmilk.tools.common;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Stack;

public class DateUtils {

    private final static String YYYY_MM_DD = "yyyy-MM-dd";

    private final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private final static String HH_MM_SS = "HH:mm:ss";

    private final static String YYYY = "yyyy";

    private final static String MM_MONTH = "MM";

    private final static String DD = "dd";

    /**
     * 获取当前时间YYYY_MM_DD格式时间
     */
    public static String toDateStr(){
        return toDateStr(new Date());
    }
    /**
     * 获取当前时间YYYY_MM_DD_HH_MM_SS格式时间
     */
    public static String toDateTimeStr(){
        return toDateTimeStr(new Date());
    }
    /**
     * 获取当前时间HH_MM_SS格式时间
     */
    public static String toTimeStr(){
        return toTimeStr(new Date());
    }
    /**
     * 获取YYYY_MM_DD格式时间
     */
    public static String toDateStr(Date date){
        return toDateTimeStr(date, YYYY_MM_DD);
    }
    /**
     * 获取YYYY_MM_DD_HH_MM_SS格式时间
     */
    public static String toDateTimeStr(Date date){
        return toDateTimeStr(date, YYYY_MM_DD_HH_MM_SS);
    }
    /**
     * 获取HH_MM_SS格式时间
     */
    public static String toTimeStr(Date date){
        return toDateTimeStr(date, HH_MM_SS);
    }

    /**
     * 格式化当前时间为指定格式
     */
    public static String toDateTimeStr(String pattern){
        return toDateTimeStr(new Date(), pattern);
    }
    /**
     * 获取当前年份
     */
    public static String getYear() {
        return toDateTimeStr(new Date(), YYYY);
    }

    /**
     * 获取当前月份
     */
    public static String getMonth() {
        return toDateTimeStr(new Date(), MM_MONTH);
    }

    /**
     * 获取当前日
     */
    public static String getDay() {
        return toDateTimeStr(new Date(), DD);
    }
    /**
     * 格式化时间为指定格式
     */
    public static String toDateTimeStr(Date date, String pattern){
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 格式化YYYY_MM_DD格式字符串为日期
     */
    public static Date toDate_YYYY_MM_DD(String dateStr){
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, YYYY_MM_DD);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 格式化YYYY_MM_DD_HH_MM_SS格式字符串为日期
     */
    public static Date toDate_YYYY_MM_DD_HH_MM_SS(String dateStr){
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, YYYY_MM_DD_HH_MM_SS);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date toDate(String dateStr, String pattern){
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, pattern);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
