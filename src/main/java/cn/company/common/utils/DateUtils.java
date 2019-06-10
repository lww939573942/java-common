package cn.company.common.utils;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日期工具处理类
 *
 * @author donaldhan
 */
public abstract class DateUtils {
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    public static final String DATE_NUMBER_FORMAT = "yyyyMMdd";
    public static final String DATE_TIME_NUMBER_FORMAT = "yyyyMMddHHmmss";
    public static final String DATA_FORMAT = "yyyy-MM-dd";
    public static final String DATA_TIME_M_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATA_TIME_S_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取指定日期相加月份后的新日期相应月份的开始时间
     *
     * @param dateObject 指定日期
     * @param monthsLater 相加月份
     *
     * @return 时间戳
     */
    public static Timestamp getMonthRangeStart(Object dateObject, int monthsLater) {
        DateTime dateTime = parseObjectToDateTime(dateObject);
        DateTime plusMonthsDateTime = dateTime.plusMonths(monthsLater).withTime(0, 0, 0, 0);

        return new Timestamp(plusMonthsDateTime.dayOfMonth().withMinimumValue().getMillis());
    }

    /**
     * 获取指定日期相加月份后的新日期相应月份的结束时间
     *
     * @param dateObject 指定日期
     * @param monthsLater 相加月份
     *
     * @return 时间戳
     */
    public static Timestamp getMonthRangeEnd(Object dateObject, int monthsLater) {
        DateTime dateTime = parseObjectToDateTime(dateObject);
        DateTime plusMonthsDateTime = dateTime.plusMonths(monthsLater).withTime(23, 59, 59, 999);

        return new Timestamp(plusMonthsDateTime.dayOfMonth().withMaximumValue().getMillis());
    }

    /**
     * 日期转换为指定格式的字符串
     *
     * @param dateObject 日期
     * @param formatString 日期格式
     *
     * @return 日期
     */
    public static String formatDateToString(Object dateObject, String formatString) {
        DateTime dateTime = parseObjectToDateTime(dateObject);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(formatString);

        return fmt.print(dateTime);
    }

    /**
     * 获取两个日期区间每一天的日期
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param format 日期格式
     *
     * @return list
     *
     * @throws ParseException 转换异常
     */
    public static List<String> getEveryDayDateFromStartDateAndEndDate(Object startDate, Object endDate, String format) {
        List<String> dateTimeList = new ArrayList<String>();
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);

        DateTime start = parseObjectToDateTime(startDate);
        DateTime end = parseObjectToDateTime(endDate);
        int daysInterval = Days.daysBetween(start, end).getDays();

        for (int i = 0; i <= daysInterval; i++) {
            DateTime nextDateTime = start.plusDays(i);
            dateTimeList.add(fmt.print(nextDateTime));
        }

        return dateTimeList;
    }

    /**
     * 获取两个日期相差的月份数
     *
     * @param startDateTime 开始时间
     * @param endDateTime 结束时间
     *
     * @return int
     */
    public static int getMonthInterval(Object startDateTime, Object endDateTime) {
        DateTime start = parseObjectToDateTime(startDateTime);
        DateTime end = parseObjectToDateTime(endDateTime);

        return Math.abs(Months.monthsBetween(start, end).getMonths());
    }

    /**
     * 获取两个日期相差的分钟数
     *
     * @param startDateTime 开始时间
     * @param endDateTime 结束时间
     *
     * @return int
     */
    public static int getMinutesInterval(Object startDateTime, Object endDateTime) {
        DateTime start = parseObjectToDateTime(startDateTime);
        DateTime end = parseObjectToDateTime(endDateTime);

        return Math.abs(Minutes.minutesBetween(start, end).getMinutes());
    }

    /**
     * 获取两个日期相差的小时数
     *
     * @param startDateTime 开始时间
     * @param endDateTime 结束时间
     *
     * @return int
     */
    public static int getHoursInterval(Object startDateTime, Object endDateTime) {
        DateTime start = parseObjectToDateTime(startDateTime);
        DateTime end = parseObjectToDateTime(endDateTime);

        return Math.abs(Hours.hoursBetween(start, end).getHours());
    }

    /**
     * 检查开始时间是否早于结束时间
     *
     * @param startDateTime 开始时间
     * @param endDateTime 结束时间
     *
     * @return boolean
     */
    public static boolean isBefore(Object startDateTime, Object endDateTime) {
        DateTime start = parseObjectToDateTime(startDateTime);
        DateTime end = parseObjectToDateTime(endDateTime);

        return start.isBefore(end.getMillis());
    }

    /**
     * 检查开始时间是否晚于结束时间
     *
     * @param startDateTime 开始时间
     * @param endDateTime 结束时间
     *
     * @return boolean
     */
    public static boolean isAfter(Object startDateTime, Object endDateTime) {
        DateTime start = parseObjectToDateTime(startDateTime);
        DateTime end = parseObjectToDateTime(endDateTime);

        return start.isAfter(end.getMillis());
    }

    /**
     * 获取日期当天的开始时间
     *
     * @param date 日期
     *
     * @return dateTime
     */
    public static DateTime getStartDateTimeOfDay(Object date) {
        DateTime dateTime = parseObjectToDateTime(date);
        dateTime = dateTime.withTime(0, 0, 0, 0);

        return dateTime;
    }

    /**
     * 获取日期当天的结束时间
     *
     * @param date 日期
     *
     * @return dateTime
     */
    public static DateTime getEndDateTimeOfDay(Object date) {
        DateTime dateTime = parseObjectToDateTime(date);
        dateTime = dateTime.withTime(23, 59, 59, 999);

        return dateTime;
    }

    /**
     * 获取两个日期区间的整点日期集合
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     *
     * @return list
     */
    public static List<Date> getDay24HList(Date startDate, Date endDate) {
        startDate = getDate(startDate, "yyyy-MM-dd HH");
        endDate = getDate(addMinute(endDate, 60), "yyyy-MM-dd HH");

        List<Date> dateList = new ArrayList<Date>();
        while (startDate.getTime() <= endDate.getTime()) {
            dateList.add(startDate);
            startDate = addMinute(startDate, 60);
        }

        return dateList;
    }

    /**
     * 获取当天的整点时间列表
     *
     * @return list
     */
    public static List<Date> getToDay24HList() {
        Date date = getDate(new Date(), "yyyy-MM-dd");
        List<Date> dateList = new ArrayList<Date>();
        dateList.add(date);
        for (int i = 0; i < 24; i++) {
            date = addMinute(date, 60);
            dateList.add(date);
        }

        return dateList;
    }

    /**
     * 转换成指定日期格式
     *
     * @param date 日期
     * @param pattern 日期格式
     *
     * @return date日期
     */
    private static Date getDate(Date date, String pattern) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        DateTime dateTime = fmt.parseDateTime(format(date, pattern));
        return dateTime.toDate();
    }

    /**
     * 两个日期相差秒数
     *
     * @param d1 开始时间
     * @param d2 结束时间
     *
     * @return long
     */
    public static long getSecondsDiff(Object d1, Object d2) {
        DateTime dateTime1 = parseObjectToDateTime(d1);
        DateTime dateTime2 = parseObjectToDateTime(d2);
        Seconds seconds = Seconds.secondsBetween(dateTime1, dateTime2);

        return seconds.getSeconds();
    }

    /**
     * 获取日期当年第一天的相应时间
     *
     * @param date 日期
     *
     * @return 日期
     */
    public static Date getFirstDateOfYear(Object date) {
        DateTime dateTime = parseObjectToDateTime(date);
        dateTime = dateTime.dayOfYear().withMinimumValue();

        return dateTime.toDate();
    }

    /**
     * 获取日期当月第一天的相应时间
     *
     * @param date 日期
     *
     * @return 日期
     */
    public static Date getFirstDateOfMonth(Object date) {
        DateTime dateTime = parseObjectToDateTime(date);
        dateTime = dateTime.dayOfMonth().withMinimumValue();

        return dateTime.toDate();
    }

    /**
     * 获取日期当月最后一天的相应时间
     *
     * @param date 日期
     *
     * @return 日期
     */
    public static Date getLastDayMonth(Object date) {
        DateTime dateTime = parseObjectToDateTime(date);
        dateTime = dateTime.dayOfMonth().withMaximumValue();

        return dateTime.toDate();
    }

    /**
     * 获取日期当月最大的天数值
     *
     * @param date 日期
     *
     * @return int
     */
    public static int getMaxDayOfMonth(Object date) {
        DateTime dateTime = parseObjectToDateTime(date);
        return dateTime.dayOfMonth().getMaximumValue();
    }

    /**
     * 获取日期指定格式的字符串
     *
     * @param date 日期
     * @param pattern 日期格式
     *
     * @return string日期
     */
    public static String format(Date date, String pattern) {
        DateTime dateTime = new DateTime(date.getTime());
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.print(dateTime);
    }

    /**
     * 获取日期加减分钟数后的日期
     *
     * @param date 日期
     * @param minute 加减分钟数
     *
     * @return 新日期
     */
    public static Date addMinute(Object date, int minute) {
        DateTime dateTime = parseObjectToDateTime(date);
        dateTime = dateTime.plusMinutes(minute);
        return dateTime.toDate();
    }

    /**
     * 获取日期加减秒钟数后的日期
     *
     * @param date 日期
     * @param seconds 加减秒钟数
     *
     * @return 新日期
     */
    public static Date addSeconds(Object date, int seconds) {
        DateTime dateTime = parseObjectToDateTime(date);
        dateTime = dateTime.plusSeconds(seconds);
        return dateTime.toDate();
    }

    /**
     * 获取日期加减天数后的日期
     *
     * @param date 日期
     * @param day 加减天数
     *
     * @return 新日期
     */
    public static Date addDay(Object date, int day) {
        DateTime dateTime = parseObjectToDateTime(date);
        dateTime = dateTime.plusDays(day);
        return dateTime.toDate();
    }

    /**
     * 获取日期加减年数后的日期
     *
     * @param date 日期
     * @param year 加减年数
     *
     * @return 新日期
     */
    public static Date addYear(Object date, int year) {
        DateTime dateTime = parseObjectToDateTime(date);
        dateTime = dateTime.plusYears(year);
        return dateTime.toDate();
    }

    /**
     * 获取两个时间相差的毫秒数
     *
     * @param date 结束日期
     * @param date1 开日日期
     *
     * @return long
     */
    public static long mathDateMinus(Date date, Date date1) {
        return date.getTime() - date1.getTime();
    }

    /**
     * 获取日期上个月相应时间
     *
     * @param date 日期
     *
     * @return date
     */
    public static Date getLastDate(Object date) {
        DateTime dateTime = parseObjectToDateTime(date);
        dateTime = dateTime.plusMonths(-1);
        return dateTime.toDate();
    }

    /**
     * 是否是同一天
     *
     * @param d1 日期1
     * @param d2 日期2
     *
     * @return boolean
     */
    public static boolean theSameDay(Object d1, Object d2) {
        boolean isSame = false;
        DateTime dateTime1 = parseObjectToDateTime(d1);
        DateTime dateTime2 = parseObjectToDateTime(d2);
        boolean isSameYear = dateTime1.getYear() == dateTime2.getYear();
        boolean isSameMonth = dateTime1.getMonthOfYear() == dateTime2.getMonthOfYear();
        boolean isSameDay = dateTime1.getDayOfMonth() == dateTime2.getDayOfMonth();
        if (isSameYear && isSameMonth && isSameDay) {
            isSame = true;
        }
        return isSame;
    }

    /**
     * 获取当前日期的小时
     *
     * @param d 日期
     *
     * @return 几点钟
     */
    public static int getHour(Object d) {
        DateTime dateTime = parseObjectToDateTime(d);
        return dateTime.getHourOfDay();
    }

    /**
     * 获取当前日期的星期
     *
     * @param d 日期
     *
     * @return 星期几
     */
    public static int getWeek(Object d) {
        DateTime dateTime = parseObjectToDateTime(d);
        return dateTime.dayOfWeek().get();
    }

    /**
     * 获取日期的月份开始时间
     *
     * @param object 日期
     *
     * @return dateTime
     */
    public static DateTime getMonthFirstDayTime(Object object) {
        DateTime dateTime = parseObjectToDateTime(object);
        dateTime = dateTime.withTime(0, 0, 0, 0);
        return dateTime.dayOfMonth().withMinimumValue();
    }

    /**
     * 将日期转换为DateTime
     */
    private static DateTime parseObjectToDateTime(Object dateObject) {
        if (ValidateUtils.isEmpty(dateObject)) {
            logger.error("日期处理异常,日期参数不能为null");
            throw new NullPointerException("日期处理异常,日期参数不能为null");
        }

        DateTime dateTime;
        if (dateObject instanceof String) {
            String str = (String) dateObject;
            DateTimeFormatter fmt = DateTimeFormat.forPattern(DATA_TIME_S_FORMAT);
            try {
                dateTime = fmt.parseDateTime(str);
            } catch (Exception e) {
                logger.error("日期处理异常");
                throw e;
            }
        } else {
            try {
                dateTime = new DateTime(dateObject);
            } catch (Exception e) {
                logger.error("日期处理异常,");
                throw e;
            }
        }

        return dateTime;
    }

    /**
     * 获取日期的月份结束时间
     *
     * @param object 日期
     *
     * @return dateTime
     */
    public static DateTime getMonthLastDayTime(Object object) {
        DateTime dateTime = parseObjectToDateTime(object);

        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        int lastDay = dateTime.dayOfMonth().withMaximumValue().dayOfMonth().get();

        dateTime = new DateTime(year, month, lastDay, 23, 59, 59, 999);

        return dateTime;
    }

    /**
     * 获取当前系统时间
     *
     * @return 时间戳
     */
    public static Timestamp getNowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * String转为Timestamp
     *
     * @param dataString 格式yyyy-MM-dd HH:mm:ss
     *
     * @return 时间戳
     */
    public static Timestamp getTimestamp(String dataString) {
        return Timestamp.valueOf(dataString);
    }

    public static Date parse(String pattern, String date) {
        DateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
