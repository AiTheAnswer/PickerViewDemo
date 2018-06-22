package com.allen.pickerviewdemo.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;


import com.allen.pickerviewdemo.R;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * <p>日期工具类</p>
 *
 * @author guor
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {

    private static final String TAG = "DateUtils";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * <p>将字符串转换为给定格式的日期，并以字符串形式返回</p>
     *
     * @param string 字符串
     * @param format 目标日期格式
     * @return 日期字符串
     */
    public static String formatStrToStr(String string, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return formatDateToStr(sdf.parse(string), format);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * <p>将字符串转换为给定格式的日期，并以字符串形式返回</p>
     *
     * @param string 字符串
     * @param format 目标日期格式
     * @return 日期字符串
     */
    public static String formatStrToStr2(String string, String format) {
        try {
            return formatDateToStr(sdf2.parse(string), format);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * <p>日期类型的数据转换为字符串</p>
     *
     * @param date 日期
     * @return 日期字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateToStr(Date date) {
        return formatDateToStr(date, null);
    }

    /**
     * <p>日期类型的数据转换为字符串</p>
     *
     * @param date   日期
     * @param format 日期格式
     * @return 日期字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateToStr(Date date, String format) {
        if (!TextUtils.isEmpty(format)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } else {
            return sdf.format(date);
        }
    }

    /**
     * <p>格式化日期</p>
     *
     * @param date
     * @param format
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * <p>字符串转换为日期类型的数据</p>
     *
     * @param string 被转换的字符串
     * @param format 目标格式
     * @return 日期
     */
    public static Date formatStrToDate(String string, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(string);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * <p>字符串转换为日历类型的数据</p>
     *
     * @param string 被转换的字符串
     * @param format 目标格式
     * @return 日历格式数据
     */
    public static Calendar formatStrToCalendar(String string, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = GregorianCalendar.getInstance();
        try {
            calendar.setTime(sdf.parse(string));
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * <p>将字符串转为时间戳</p>
     *
     * @param date   日期字符串
     * @param format 目标格式
     * @return 时间戳
     * <p>例：2018-03-15 16:47:40以yyyy-MM-dd HH:mm:ss格式转换后得到1521043200</p>
     */
    public static long formatStrToStamp(String date, String format) {
        long time_stamp = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date d = sdf.parse(date);
            time_stamp = d.getTime() / 1000;
        } catch (ParseException e) {
            return time_stamp;
        }
        return time_stamp;
    }

    /**
     * <p>将字符串转换为UTC时间戳</p>
     *
     * @param date   日期字符串
     * @param format 目标格式
     * @return 时间戳
     * <p>例：2018-03-15 16:47:40以yyyy-MM-dd HH:mm:ss格式转换后得到1521072000</p>
     */
    public static long formatStrToUTCStamp(String date, String format) {
        long time_stamp = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date d = sdf.parse(date);
            time_stamp = d.getTime() / 1000;
        } catch (ParseException e) {
            return time_stamp;
        }
        return time_stamp;

    }

    /**
     * <p>将时间戳转化为给定格式的日期字符串</p>
     *
     * @param timeStamp 时间戳
     * @return 日期
     * <p>例：1521103636400l以yyyy-MM-dd HH:mm:ss格式转换后得到2018-03-15 16:47:16</p>
     */
    public static String formatStampToStr(long timeStamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(timeStamp));
    }

    /**
     * <p>将时间戳转化为给定格式的UTC日期字符串</p>
     *
     * @param timeStamp 时间戳
     * @return 日期
     * <p>例：1521103636400l以yyyy-MM-dd HH:mm:ss格式转换后得到2018-03-15 08:47:16</p>
     */
    public static String formatStampToUTCStr(long timeStamp) {
        SimpleDateFormat simpleStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleStampFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleStampFormat.format(new Date(timeStamp));
    }

    /**
     * <p>判断所给时间是否在当前时间之前</p>
     *
     * @param date 给定时间，单位为秒
     * @return true or false
     */
    public static boolean timeCompare(String date) {
        long selectTime = DateUtils.formatStrToStamp(date, "yyyy-MM-dd HH:mm:ss");
        //所选时间在当前时间之前
        if (new Date().getTime() / 1000 >= selectTime) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较两个日期大小
     *
     * @param dateStr1 日期
     * @param dateStr2 日期
     * @return 第一个时期是否在第二个日期之前
     */
    public static boolean dateCompare(String dateStr1, String dateStr2) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date sDate = format.parse(dateStr1);
            Date eDate = format.parse(dateStr2);
            if (dateStr1.equals(dateStr2)) {
                return true;
            } else {
                return sDate.before(eDate);
            }
        } catch (ParseException e) {
            Log.i(TAG, "dateFormat-->" + e.getMessage());
        }
        return false;
    }

    /**
     * <p>获取当前年份</p>
     *
     * @return 当前年
     */
    public static String getCurrentYear() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return String.valueOf(c.get(Calendar.YEAR));
    }

    /**
     * <p>获取当前月</p>
     *
     * @return 当前月
     */
    public static String getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return String.valueOf(c.get(Calendar.MONTH) + 1);
    }

    /**
     * <p>获取当前日</p>
     *
     * @return 当前日
     */
    public static String getCurrentDayOfMonth() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return String.valueOf(c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * <p>获取当前星期</p>
     *
     * @return 当前星期
     */
    public static String getCurrentDayOfWeek() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return String.valueOf(c.get(Calendar.DAY_OF_WEEK));
    }

    /**
     * <p>获取当前星期（中文）</p>
     *
     * @return 当前星期
     */
    public static int getChDayOfWeek() {
        String day = getCurrentDayOfWeek();
        int id = -1;
        if ("1".equals(day)) {
            id = R.string.weekday_sunday;
        } else if ("2".equals(day)) {
            id = R.string.weekday_monday;
        } else if ("3".equals(day)) {
            id = R.string.weekday_tuesday;
        } else if ("4".equals(day)) {
            id = R.string.weekday_wednesday;
        } else if ("5".equals(day)) {
            id = R.string.weekday_thursday;
        } else if ("6".equals(day)) {
            id = R.string.weekday_friday;
        } else if ("7".equals(day)) {
            id = R.string.weekday_saturday;
        }
        return id;
    }

    /**
     * 获取某一天是周几
     *
     * @param sDate      某日日期
     * @param dateFormat 日期格式
     * @return 周几
     */
    public static String getDateOfWeek(String sDate, String dateFormat) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(sDate, pos);
        c.setTime(date);
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return String.valueOf(c.get(Calendar.DAY_OF_WEEK));

    }

    /**
     * 获取某一天的"上周同期"
     *
     * @param sDate      日期字符串
     * @param dateFormat 日期格式
     * @return 某一天"上周同期"的字符串资源id
     */
    public static int getSamePeriodLastWeek(String sDate, String dateFormat) {
        String day = getDateOfWeek(sDate, dateFormat);
        int id = -1;
        switch (day) {
            case "1":
                id = R.string.last_week_sunday;
                break;
            case "2":
                id = R.string.last_week_monday;
                break;
            case "3":
                id = R.string.last_week_tuesday;
                break;
            case "4":
                id = R.string.last_week_wednesday;
                break;
            case "5":
                id = R.string.last_week_thursday;
                break;
            case "6":
                id = R.string.last_week_friday;
                break;
            case "7":
                id = R.string.last_week_saturday;
                break;
            default:
                id = R.string.last_week_sunday;
                break;
        }
        return id;
    }

    /**
     * <p>获取当前星期（中文）</p>
     *
     * @return 当前星期
     */
    public static int getDayOfWeek() {
        String day = getCurrentDayOfWeek();
        int id = -1;
        if ("1".equals(day)) {
            id = R.string.weekday_abbr_sunday;
        } else if ("2".equals(day)) {
            id = R.string.weekday_abbr_monday;
        } else if ("3".equals(day)) {
            id = R.string.weekday_abbr_tuesday;
        } else if ("4".equals(day)) {
            id = R.string.weekday_abbr_wednesday;
        } else if ("5".equals(day)) {
            id = R.string.weekday_abbr_thursday;
        } else if ("6".equals(day)) {
            id = R.string.weekday_abbr_friday;
        } else if ("7".equals(day)) {
            id = R.string.weekday_abbr_saturday;
        }
        return id;
    }

    /**
     * <p>获取指定月的最后一天</p>
     *
     * @param year  指定年
     * @param month 指定月
     * @return 指定月的最后一天
     */
    public static int getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // set year
        cal.set(Calendar.YEAR, year);
        // set month
        cal.set(Calendar.MONTH, month - 1);

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * <p>获取昨天的日期</p>
     *
     * @return 昨天的日期
     */
    public static String getDateOfYesterday(String format) {
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();
        long yesterdayTime = time - 24 * 3600 * 1000;
        Date date = new Date(yesterdayTime);
        if (TextUtils.isEmpty(format)) {
            return sdf.format(date);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
    }

    /**
     * @return
     */
    public static String getTodayDateWeekOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        return String.format("%d-%d", year, weekOfYear);
    }

    /**
     * 获取前几个周是当前年的第多少周
     *
     * @param week
     * @return
     */
    public static String getXDateBeforeWeekOfYear(int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.DATE, -week * 7);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        return String.format("%d-%d", year, weekOfYear);
    }

    /**
     * <p>获取本周的第一天日期（周一为第一天）</p>
     *
     * @return 当前周的第一天的日期
     */
    public static String getFirstDateOfCurrentWeek(String format) {
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 1);
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取本周的最后一天日期（周一为第一天）</p>
     *
     * @return 当前周的最后一天的日期
     */
    public static String getLastDateOfCurrentWeek(String format) {
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 7);
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取上周的第一天日期（周一为第一天）</p>
     *
     * @return 上周第一天的日期
     */
    public static String getFirstDateOfLastWeek(String format) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.DATE, -1 * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取上周的最后一天日期（周一为第一天）</p>
     *
     * @return 上周最后一天的日期
     */
    public static String getLastDateOfLastWeek(String format) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.DATE, -1 * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取本月的第一天日期</p>
     *
     * @return 本月第一天
     */
    public static String getFirstDateOfCurrentMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取本月的最后一天日期</p>
     *
     * @return 本月最后一天
     */
    public static String getLastDateOfCurrentMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取指定月的最后一天</p>
     *
     * @param date 指定日期
     * @return 指定月的最后一天的日期
     */
    public static Date getMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * <p>获取指定年的第一天</p>
     *
     * @return 指定年的第一天
     */
    public static String getYearFirstDay(Date date, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取上月第一天</p>
     *
     * @return 上月第一天
     */
    public static String getFirstDateOfLastMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取上月最后一天</p>
     *
     * @return 上月最后一天
     */
    public static String getLastDateOfLastMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取本年的第一天</p>
     *
     * @return 本年第一天
     */
    public static String getFirstDateOfCurrentYear(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取本年的最后一天</p>
     *
     * @return 本年最后一天
     */
    public static String getLastDateOfCurrentYear(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取去年的第一天</p>
     *
     * @return 去年第一天
     */
    public static String getFirstDateOfLastYear(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取去年最后一天</p>
     *
     * @return 去年最后一天
     */
    public static String getLastDateOfLastYear(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取前n天的日期</p>
     *
     * @param days   前n天
     * @param format 日期格式
     * @return 日期
     */
    public static String getXDateBeforeToday(int days, String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1 * days);
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }


    /**
     * <p>获取几个月前同期</p>
     *
     * @param months 前n月
     * @param format 日期格式
     * @return 日期
     */
    public static String getXDateBeforeCurrentMonth(int months, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -months);
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取几年前同期</p>
     *
     * @param year   前n年
     * @param format 日期格式
     * @return 日期
     */
    public static String getXDateBeforeCurrentYear(int year, String format) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, -1 * year);
        cal.getTime();
        if (TextUtils.isEmpty(format)) {
            return sdf.format(cal.getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(cal.getTime());
        }
    }

    /**
     * <p>获取指定日期所在周的第一天日期（周一为一周的开始）</p>
     *
     * @param date 指定日期
     * @return 指定日期所在周的第一天
     */
    public static Date getWeekStart(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        return calendar.getTime();
    }

    /**
     * <p>获取指定日期所在周的最后一天日期（周一为一周的开始）</p>
     *
     * @param date 指定日期
     * @return 指定日期所在周的最后一天
     */
    public static Date getWeekEnd(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        return calendar.getTime();
    }

    /**
     * <p>检查第一个日期{@code date1}是否在第二个日期之后</p>
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return {@code true} when this date1 is after date2, {@code false} otherwise.
     */
    public static boolean checkFDateAfterSDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.after(cal2);
    }


    /**
     * <p>计算两个日期相差的天数，注意：第一个日期必须在第二个日期前</p>
     *
     * @param dateStr1 日期
     * @param dateStr2 日期
     * @return 日期差
     */
    public static int daysOfTwoDate(String dateStr1, String dateStr2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date sDate = format.parse(dateStr1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sDate);
            int sYear = calendar.get(Calendar.YEAR);
            int sDay = calendar.get(Calendar.DAY_OF_YEAR);
            Date eDate = format.parse(dateStr2);
            calendar.setTime(eDate);
            int eYear = calendar.get(Calendar.YEAR);
            int eDay = calendar.get(Calendar.DAY_OF_YEAR);
            if (eYear - sYear == 0) {
                return eDay - sDay;
            } else if (eYear - sYear == 1) {
                if (sYear % 4 == 0 && sYear % 100 != 0 || sYear % 400 == 0) {//闰年的判断规则
                    return eDay + (366 - sDay);
                } else {
                    return eDay + (365 - sDay);
                }
            } else {
                int days = 0;
                if (sYear % 4 == 0 && sYear % 100 != 0 || sYear % 400 == 0) {//闰年的判断规则
                    days = 366 - sDay;
                } else {
                    days = 365 - sDay;
                }
                for (int i = sYear + 1; i <= eYear - 1; i++) {
                    if (sYear % 4 == 0 && sYear % 100 != 0 || sYear % 400 == 0) {//闰年的判断规则
                        days += 366;
                    } else {
                        days += 365;
                    }
                }
                return days + eDay;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * <p>将中文月转换为英文</p>
     *
     * @param month 中文月
     * @return 英文月
     * <p>例：1月转换为Jan.</p>
     */
    public static String formatMonthChToEn(String month) {
        switch (month) {
            case "1月":
                return "Jan.";
            case "2月":
                return "Feb.";
            case "3月":
                return "Mar.";
            case "4月":
                return "Apr.";
            case "5月":
                return "May.";
            case "6月":
                return "Jun.";
            case "7月":
                return "Jul.";
            case "8月":
                return "Aug.";
            case "9月":
                return "Sept.";
            case "10月":
                return "Oct.";
            case "11月":
                return "Nov.";
            case "12月":
                return "Dec.";
            default:
                return month;
        }
    }

    /**
     * <p>将英文月转换为中文</p>
     *
     * @param month 英文月
     * @return 中文月
     * <p>例：JANUARY转换为1月</p>
     */
    public static String formatMonthEnToCh(String month) {
        switch (month) {
            case "JANUARY":
                return "1月";
            case "FEBRUARY":
                return "2月";
            case "MARCH":
                return "3月";
            case "APRIL":
                return "4月";
            case "MAY":
                return "5月";
            case "JUNE":
                return "6月";
            case "JULY":
                return "7月";
            case "AUGUST":
                return "8月";
            case "SEPTEMBER":
                return "9月";
            case "OCTOBER":
                return "10月";
            case "NOVEMBER":
                return "11月";
            case "DECEMBER":
                return "12月";
            default:
                return month;
        }
    }

    /**
     * <p>将英文日期转换为缩写形式</p>
     *
     * @param month 月
     * @return 缩写月份.
     * <p>例：JANUARY转换为Jan.</p>
     */
    public static String formatMonthEnToAbbrv(String month) {
        switch (month) {
            case "JANUARY":
                return "Jan.";
            case "FEBRUARY":
                return "Feb.";
            case "MARCH":
                return "Mar.";
            case "APRIL":
                return "Apr.";
            case "MAY":
                return "May.";
            case "JUNE":
                return "Jun.";
            case "JULY":
                return "Jul.";
            case "AUGUST":
                return "Aug.";
            case "SEPTEMBER":
                return "Sept.";
            case "OCTOBER":
                return "Oct.";
            case "NOVEMBER":
                return "Nov.";
            case "DECEMBER":
                return "Dec.";
            default:
                return month;
        }
    }

    /**
     * <p>将中文星期转换为英文</p>
     *
     * @param day 星期
     * @return 英文星期
     * <p>例：周一|星期一转换为Mon.</p>
     */
    public static String formatDayChToEn(String day) {
        if (day.equals("周一") || day.equals("星期一")) {
            return "Mon.";
        } else if (day.equals("周二") || day.equals("星期二")) {
            return "Tue.";
        } else if (day.equals("周三") || day.equals("星期三")) {
            return "Wed.";
        } else if (day.equals("周四") || day.equals("星期四")) {
            return "Thu.";
        } else if (day.equals("周五") || day.equals("星期五")) {
            return "Fri.";
        } else if (day.equals("周六") || day.equals("星期六")) {
            return "Sat.";
        } else if (day.equals("周日") || day.equals("星期日")) {
            return "Sun.";
        } else {
            return day;
        }

    }

    /**
     * <p>将英文星期转换为中文</p>
     *
     * @param day 星期
     * @return 英文星期
     * <p>例：周一|星期一转换为Mon.</p>
     */
    public static String formatDayEnToCh(String day) {
        switch (day) {
            case "MON":
                return "一";
            case "TUE":
                return "二";
            case "WED":
                return "三";
            case "THU":
                return "四";
            case "FRI":
                return "五";
            case "SAT":
                return "六";
            case "SUN":
                return "日";
            default:
                return day;
        }
    }

    /**
     * <p>将英文星期转换为英文小写</p>
     *
     * @param day 缩写的英文星期
     * @return 英文星期
     * <p>例：MON转换为Mon.</p>
     */
    public static String formatDayEnToAbbrv(String day) {
        switch (day) {
            case "MON":
                return "Mon.";
            case "TUE":
                return "Tue.";
            case "WED":
                return "Wed.";
            case "THU":
                return "Thu.";
            case "FRI":
                return "Fri.";
            case "SAT":
                return "Sat.";
            case "SUN":
                return "Sun.";
            default:
                return day;
        }
    }

    /**
     * 获取当前时间N分钟之前（后）的时间
     *
     * @param format 分钟
     * @return 时间
     */
    public static String get30MinDateStr(int minute, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minute);
        return sdf.format(cal.getTimeInMillis());
    }

}
