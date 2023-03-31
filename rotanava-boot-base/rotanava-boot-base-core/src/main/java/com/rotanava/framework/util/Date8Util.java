package com.rotanava.framework.util;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 基于Java8的时间工具类
 *
 * @author keji
 * @version $Id: DateUtil.java, v 0.1 2018-12-28 14:11 keji Exp $
 */
public class Date8Util {

    /**
     * 例如:2018-12-28
     */
    public static final String DATE = "yyyy-MM-dd";
    /**
     * 例如:2018-12-28 10:00:00
     */
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 例如:10:00:00
     */
    public static final String TIME = "HH:mm:ss";
    /**
     * 例如:10:00
     */
    public static final String TIME_WITHOUT_SECOND = "HH:mm";

    /**
     * 例如:2018-12-28 10:00
     */
    public static final String DATE_TIME_WITHOUT_SECONDS = "yyyy-MM-dd HH:mm";


    /**
     * 获取年
     *
     * @return 年
     */
    public static int getYear() {
        LocalTime localTime = LocalTime.now();
        return localTime.get(ChronoField.YEAR);
    }

    /**
     * 获取月份
     *
     * @return 月份
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取某月的第几天
     *
     * @return 几号
     */
    public static int getMonthOfDay() {
        LocalTime localTime = LocalTime.now();
        return localTime.get(ChronoField.DAY_OF_MONTH);
    }

    /**
     * 格式化日期为字符串默认yyyy-MM-dd HH:mm:ss
     *
     * @param date date
     * @return 日期字符串
     */
    public static String format(Date date) {

        Instant instant = date.toInstant();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME));
    }

    /**
     * 格式化日期为字符串
     *
     * @param date    date
     * @param pattern 格式
     * @return 日期字符串
     */
    public static String format(Date date, String pattern) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析字符串日期为Date
     *
     * @param dateStr 日期字符串
     * @param pattern 格式
     * @return Date
     */
    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 为Date增加分钟,减传负数
     *
     * @param date        日期
     * @param plusMinutes 要增加的分钟数
     * @return 新的日期
     */
    public static Date addMinutes(Date date, Long plusMinutes) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime newDateTime = dateTime.plusMinutes(plusMinutes);
        return Date.from(newDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 为Date增加分钟,减传负数
     *
     * @param date        日期
     * @param plusMinutes 要增加的分钟数
     * @return 新的日期
     */
    public static Date addMinutes(Date date, int plusMinutes) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime newDateTime = dateTime.plusMinutes(plusMinutes);
        return Date.from(newDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 增加时间
     *
     * @param date date
     * @param hour 要增加的小时数
     * @return new date
     */
    public static Date addHour(Date date, Long hour) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime localDateTime = dateTime.plusHours(hour);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 增加时间
     *
     * @param date date
     * @param hour 要增加的天数
     * @return new date
     */
    public static Date addDays(Date date, Long days) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime localDateTime = dateTime.plusDays(days);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @return 返回当天的起始时间
     */
    public static Date getStartTime() {
        return getStartTime(new Date());
    }

    /**
     * @return 返回当天的起始时间
     */
    public static Date getStartTime(Date date) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime now = dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
        return localDateTime2Date(now);
    }

    /**
     * @return 返回当天的起始时间
     */
    public static String getStartTimeStr(String format) {
        LocalDateTime now = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        Date date = localDateTime2Date(now);
        return format(date, format);
    }


    /**
     * @return 返回当天的结束时间
     */
    public static Date getEndTime() {
        return getEndTime(new Date());
    }

    /**
     * @return 返回当天的结束时间
     */
    public static Date getEndTime(Date date) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime now = dateTime.withHour(23).withMinute(59).withSecond(59).withNano(9999);
        return localDateTime2Date(now);
    }

    /**
     * @return 返回当天的结束时间
     */
    public static String getEndTimeStr(String format) {
        LocalDateTime now = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(9999);
        Date date = localDateTime2Date(now);
        return format(date, format);
    }

    /**
     * @return 返回现在的时间
     */
    public static Date getNow() {
        LocalDateTime now = LocalDateTime.now();
        return localDateTime2Date(now);
    }

    public static String getNowStr(String format) {
        LocalDateTime now = LocalDateTime.now();
        Date date = localDateTime2Date(now);
        return format(date, format);
    }

    /**
     * @return 返回现在的时间
     */
    public static long getNowTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        return localDateTime2Date(now).getTime();
    }

    /**
     * 减月份
     *
     * @param monthsToSubtract 月份
     * @return Date
     */
    public static Date minusMonths(long monthsToSubtract) {
        LocalDate localDate = LocalDate.now().minusMonths(monthsToSubtract);

        return localDate2Date(localDate);
    }



    /**
     * LocalDate类型转为Date
     *
     * @param localDate LocalDate object
     * @return Date object
     */
    public static Date localDate2Date(LocalDate localDate) {

        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());

        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * LocalDateTime类型转为Date
     *
     * @param localDateTime LocalDateTime object
     * @return Date object
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 查询当前年的第一天
     *
     * @param pattern 格式，默认格式yyyyMMdd
     * @return 20190101
     */
    public static String getFirstDayOfCurrentYear(String pattern) {
        LocalDateTime localDateTime = LocalDateTime.now().withMonth(1).withDayOfMonth(1);

        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyyMMdd";
        }

        return format(localDateTime2Date(localDateTime), pattern);
    }

    /**
     * 查询当月的第一天0点时间
     */
    public static Date getFirstDayOfMonth() {
        LocalDateTime localDateTime = LocalDateTime.now().withMonth(getMonth()).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        return (localDateTime2Date(localDateTime));
    }

    /**
     * 查询前一年最后一个月第一天
     *
     * @param pattern 格式，默认格式yyyyMMdd
     * @return 20190101
     */
    public static String getLastMonthFirstDayOfPreviousYear(String pattern) {
        LocalDateTime localDateTime = LocalDateTime.now().minusYears(1L).withMonth(12).withDayOfMonth(1);
        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyyMMdd";
        }
        return format(localDateTime2Date(localDateTime), pattern);
    }

    /**
     * 查询前一年最后一个月第一天
     *
     * @param pattern 格式，默认格式yyyyMMdd
     * @return 20190101
     */
    public static String getLastMonthLastDayOfPreviousYear(String pattern) {
        LocalDateTime localDateTime = LocalDateTime.now().minusYears(1L).with(TemporalAdjusters.lastDayOfYear());
        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyyMMdd";
        }
        return format(localDateTime2Date(localDateTime), pattern);
    }

    /**
     * 获取当前日期
     *
     * @param pattern 格式，默认格式yyyyMMdd
     * @return 20190101
     */
    public static String getCurrentDay(String pattern) {
        LocalDateTime localDateTime = LocalDateTime.now();

        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyyMMdd";
        }

        return format(localDateTime2Date(localDateTime), pattern);
    }

    /**
     * 功能: 获取这周周几 周一到周日 1-7
     * 作者: zjt
     * 日期: 2020/4/17 14:59
     * 版本: 1.0
     */
    public static int getWeek() {
        Date today = new Date();
        return getWeek(today);
    }

    /**
     * 功能: 获取这周周几 周一到周日 1-7
     * 作者: zjt
     * 日期: 2020/4/17 14:59
     * 版本: 1.0
     */
    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week = c.get(Calendar.DAY_OF_WEEK);
        week--;
        if (week == 0) {
            week = 7;
        }
        return week;
    }

    /**
     * 功能: 获取这周周几 周一到周日 1-7
     * 作者: zjt
     * 日期: 2020/4/17 14:59
     * 版本: 1.0
     */
    public static String getWeekStr(Date date) {
        final int week = getWeek(date);
        String[] weekStr = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        return weekStr[week - 1];
    }

    /**
     * @return void
     * @Description 计算两个日期间隔的天数
     * @Author yangsu
     * @Date 9:39 2020/6/3
     * @Param []
     **/
    public static long betweenDays(Date begin, Date end) {
        long between_days = (getStartTime(end).getTime() - getStartTime(begin).getTime()) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取过去第几天的日期
     *
     * @param pastInt 天数
     * @return long
     * @author WeiQiangMiao
     * @date 2020/6/9
     */
    public static long getPastDate(int pastInt) {
        long createTime = DateUtils.addDays(new Date(), -pastInt + 1).getTime();
        return createTime - ((createTime + 28800000) % (86400000));
    }


    /**
     * 根据天数 过去时间
     *
     * @param pastInt 天数
     * @return java.util.Date
     * @author WeiQiangMiao
     * @version 1.0.0
     * @since 2020/7/6
     */
    public static Date pastDate(int pastInt) {
        LocalDateTime now = LocalDateTime.now().plusDays(-pastInt);
        return localDateTime2Date(now);
    }

    /**
     * 获取某天数的开始时间
     *
     * @return java.util.Date
     * @author WeiQiangMiao
     * @date 2020/6/19
     */
    public static Date getStartTimeCertainDay(Integer day) {
        LocalDateTime now = LocalDateTime.now().plusDays(day).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return localDateTime2Date(now);
    }

    /**
     * 获取某天数的结束时间
     *
     * @return java.util.Date
     * @author WeiQiangMiao
     * @date 2020/6/19
     */
    public static Date getEndTimeCertainDay(Integer day) {
        LocalDateTime now = LocalDateTime.now().plusDays(day).withHour(23).withMinute(59).withSecond(59).withNano(999);
        return localDateTime2Date(now);
    }


    public static String getYesterday(String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return format(cal.getTime(), format);
    }

    public static int dateCompare(Date date1, Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateFirst = dateFormat.format(date1);
        String dateLast = dateFormat.format(date2);
        int dateFirstIntVal = Integer.parseInt(dateFirst);
        int dateLastIntVal = Integer.parseInt(dateLast);
        if (dateFirstIntVal > dateLastIntVal) {
            return 1;
        } else if (dateFirstIntVal < dateLastIntVal) {
            return -1;
        }
        return 0;
    }


    /**
     * 功能: 获取time 格式HH:mm:ss
     * 作者: zjt
     * 日期: 2020/10/21 11:53
     * 版本: 1.0
     */
    public static Time getTime(String time) {
        final Date date = parse(time, TIME);
        return new Time(date.getTime());
    }

    /**
     * 功能: 年月日加时分秒
     * 作者: zjt
     * 日期: 2020/10/27 16:27
     * 版本: 1.0
     */
    public static Date dateAddTime(Date date, String time) {
        final String data = format(date, DATE);
        final String newDate = data + " " + time;
        return parse(newDate, DATE_TIME);
    }

    /**
     * 功能: 年月日加时分秒
     * 作者: zjt
     * 日期: 2020/10/27 16:27
     * 版本: 1.0
     */
    public static Date dateAddTime(Date date, String time, String pattern) {
        final String data = format(date, DATE);
        final String newDate = data + " " + time;
        return parse(newDate, pattern);
    }

    /**
     * 功能: 判断是否是同一天
     * 作者: zjt
     * 日期: 2020/10/28 17:44
     * 版本: 1.0
     */
    public static boolean isSameDay(Date date1, Date date2) {
        return getEndTime(date1).getTime() == getEndTime(date2).getTime();
    }


    /**
     * 功能: 获取某个时间段内所有的日期
     * 作者: zjt
     * 日期: 2020/11/18 15:52
     * 版本: 1.0
     */
    public static List<String> findDates(Date dBegin, Date dEnd, String pattern) {
        List<String> lDate = new ArrayList<>();
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        lDate.add(sd.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()) && !isSameDay(calBegin.getTime(), dEnd)) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(sd.format(calBegin.getTime()));
        }
        return lDate;
    }

    /**
     * 功能: 判断某个时间是否在这个范围
     * 作者: zjt
     * 日期: 2021/1/7 14:43
     * 版本: 1.0
     */
    public static boolean between(Date startTime, Date endTime, Date compareTime) {
        return compareTime.getTime() >= startTime.getTime() && compareTime.getTime() <= endTime.getTime();
    }


    /**
     * 第二个时间
     *
     * @param seconds 秒
     * @return {@link StringBuilder }
     * @author WeiQiangMiao
     * @date 2021-01-08
     * @version 1.0.0
     * @update [序号][日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public static StringBuilder secondToTime(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("Seconds must be a positive number!");
        }

        int other = seconds % 3600;
        int minute = other / 60;
        int second = other % 60;
        final StringBuilder sb = new StringBuilder();
        sb.append(minute);
        sb.append("分");
        if (second < 10) {
            sb.append("0");
        }
        sb.append(second);
        sb.append("秒");
        return sb;
    }


    public static Long getFileCreateTime(String filePath) {
        File file = new File(filePath);
        try {
            Path path = Paths.get(filePath);
            BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            BasicFileAttributes attr = basicview.readAttributes();
            return attr.creationTime().toMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return file.lastModified();
        }
    }

    /**
     * 获得指定日期区间内的年份和季节<br>
     *
     * @param startDate 起始日期（包含）
     * @param endDate   结束日期（包含）
     * @param field     日历字段。
     * @param amount    结束日期（包含） 要添加到字段中的日期或时间的数量
     * @return 数
     * @since 4.1.15
     */
    public static List<Calendar> between(long startDate, long endDate, int field, int amount) {
        final List<Calendar> calendars = new ArrayList<>();
        final Calendar startCal = cn.hutool.core.date.DateUtil.calendar(startDate);
        startCal.setFirstDayOfWeek(Calendar.MONDAY);
        final Calendar endCal = DateUtil.calendar(endDate);
        endCal.setFirstDayOfWeek(Calendar.MONDAY);

        while (true) {
            if (!startCal.after(endCal)) {
                calendars.add((Calendar) startCal.clone());
                startCal.add(field, amount);
            } else {
                Calendar calendar = calendars.get(calendars.size() - 1);
                if (calendar.get(field) == endCal.get(field)) {
                    calendars.remove(calendars.size() - 1);
                }
                calendars.add((Calendar) endCal.clone());
                break;
            }
        }


        return calendars;
    }


}
