package com.rotanava.framework.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Log4j2
public class DateUtil {

    /**
     * 获取该日期为这周的第几天
     *
     * @return
     */
    public static int getWeekDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置星期一为一周开始的第一天
        calendar.setTimeInMillis(System.currentTimeMillis());//获得当前的时间戳
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);//获得当前日期属于今年的第几周
        return weekOfYear;
    }


    public static Date parseTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseTime(String time, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用SimpleDateFormat计算时间差
     *
     * @throws ParseException
     */
    public static long calculateTimeDifferenceBySimpleDateFormat(String begin, String end) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(end);
            Date d2 = df.parse(begin);
            long between = (d2.getTime() - d1.getTime()) / 1000;

            long min = between / 60;
//            System.out.println(min+"分");
            return min;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long calculateSecondDifferenceBySimpleDateFormat(Date begin, Date end) {
        try {
            long between = (begin.getTime() - end.getTime()) / 1000;
            return between;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getDateBySend(String date, int hour, int minute, int second) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = df.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(parse);
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, second);
//        c.set(Calendar.MILLISECOND, 0);
            return df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAddDateBySend(String date, int day, int hour, int minute, int second) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = df.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(parse);
            c.add(Calendar.DAY_OF_MONTH, day);
            c.add(Calendar.HOUR_OF_DAY, hour);
            c.add(Calendar.MINUTE, minute);
            c.add(Calendar.SECOND, second);
//        c.set(Calendar.MILLISECOND, 0);
            return df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAddDateBySend(String date, int day, int hour, int minute, int second, SimpleDateFormat df) {
        try {
            Date parse = df.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(parse);
            c.add(Calendar.DAY_OF_MONTH, day);
            c.add(Calendar.HOUR_OF_DAY, hour);
            c.add(Calendar.MINUTE, minute);
            c.add(Calendar.SECOND, second);
//        c.set(Calendar.MILLISECOND, 0);
            return df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAddDateBySend(String date, int hour, int minute, int second) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = df.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(parse);
            c.add(Calendar.HOUR_OF_DAY, hour);
            c.add(Calendar.MINUTE, minute);
            c.add(Calendar.SECOND, second);
//        c.set(Calendar.MILLISECOND, 0);
            return df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取该日期为这周的第几天
     *
     * @return
     */
    public static int getWeekDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置星期一为一周开始的第一天
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);//获得当前日期属于今年的第几周
        return weekOfYear;
    }


    /**
     * 获取这周的第一天
     *
     * @param x
     * @return
     */
    public static Calendar getFirstDayOfWeek(Calendar x) {
        boolean isFirstSunday = (x.getFirstDayOfWeek() == Calendar.SUNDAY);
        int weekDay = x.get(Calendar.DAY_OF_WEEK);
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        x.add(Calendar.DAY_OF_YEAR, (-1) * (weekDay - 1));
        return x;
    }

    public static Calendar getLastDayOfWeek(Calendar x) {
        boolean isFirstSunday = (x.getFirstDayOfWeek() == Calendar.SUNDAY);
        int weekDay = x.get(Calendar.DAY_OF_WEEK);
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        x.add(Calendar.DAY_OF_YEAR, 7 - weekDay);
        return x;
    }


    /**
     * 获取每个月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Calendar getCurrentMonthFirstDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, year);
        return calendar;
    }

    /**
     * 获取每个月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Calendar getCurrentMonthLastDays(int year, int month) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar;
    }

    public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }


    public static Date paseDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getNowTime(SimpleDateFormat df) {
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getToday() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getTodayTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getTodayZero() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return df.format(zero);// new Date()为获取当前系统时间
    }

    /**
     * subtract days to date in java
     * @param date
     * @param days
     * @return
     */
    public static Date subtractDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);

        return cal.getTime();

    }



    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static Integer daysBetween(Date smdate,Date bdate)
    {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;


    }


    public static Date localDate2Date(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * @Description 把时间补充完整
     * @Author yangsu
     * @Date 16:10 2020/4/7
     * @Param
     * @return
     **/
//    public static String supplementTime(String dateTime,String time){
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        SimpleDateFormat dfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
//        Date date = null;
//        try {
//            date = dfTime.parse(dateTime + " " +time);
//        } catch (ParseException e) {
//            return null;
//        }
//        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
//        return Date.from(zonedDateTime.toInstant());
//    }

    /**
     * @return java.lang.String
     * @Description 截取时间的年月日，和判断时间格式是否正确
     * @Author yangsu
     * @Date 18:56 2020/4/7
     * @Param [date]
     **/
    public static String InterceptAndCheckTime(String date, DateFormat formatter) {
        try {
            formatter.setLenient(false);
            Date dateTime = formatter.parse(date);
            return formatter.format(dateTime);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @return boolean
     * @Description 对比时间, 判断beginTime是否在endTime之前。
     * @Author yangsu
     * @Date 19:42 2020/4/7
     * @Param beginTime, endTime
     **/
    public static boolean comparisonTime(String beginTime, String endTime, DateFormat df) {
        try {
            Date beginDate = df.parse(beginTime);
            Date endDate = df.parse(endTime);
            return beginDate.before(endDate);
        } catch (Exception e) {
            return false;
        }
    }

    public static String supplementTime(String dateTime, String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        SimpleDateFormat dfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        Date date = null;
        try {
            date = dfTime.parse(dateTime + " " + time);
        } catch (ParseException e) {
            return null;
        }
        String format = df.format(date);
        return format;
    }

    /**
     * 将时间yyyy-MM-dd转换成yyyyMMdd数字字符串
     * tianai
     */
    public static String timeStrToTimeNumber(String timeStr) throws ParseException {
        if (StringUtil.isNullOrEmpty(timeStr) || StringUtils.isBlank(timeStr)) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sf.parse(timeStr);
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");
        return sf2.format(date);
    }


    /**
     * 获得该月第一天 的00：00；00
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置时间
        cal.set(year, month - 1, firstDay, 0, 0, 0);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String time) {
        String stap;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return Long.toString(System.currentTimeMillis());

        }
        long ts = date.getTime();//获取时间的时间戳
        stap = String.valueOf(ts);
        return stap;
    }



    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 功能: 判断时间是否在时间段内
     * 作者: zjt
     * 日期: 2020/8/31 11:36
     * 版本: 1.0
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
    }



}
