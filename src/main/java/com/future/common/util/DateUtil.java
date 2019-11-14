package com.future.common.util;

import com.future.common.enums.GlobalResultCode;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 提供对日期时间操作的常用方法
 *
 * @author Admin
 * @version: 1.0
 */
public class DateUtil {

    private static final long ONE_DAY = 24 * 3600000;

    private static final long ONE_MINUTE = 60000;

    private static String datePattern = "yyyy-MM-dd";

    private static String timePattern = "HH:mm:ss";

    private static String millisecondPattern = ".SSS";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    private static SimpleDateFormat datetimeFormat = new SimpleDateFormat(datePattern + " " + timePattern);

    private static SimpleDateFormat timeStampFormat = new SimpleDateFormat(
            datePattern + " " + timePattern + millisecondPattern);


    /**
     * 将日期对象转换为字符串，格式为yyyy-MM-dd
     *
     * @param date 日期
     * @return 日期对应的日期字符串
     */
    public static String toDateString(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormat.format(date);
    }

    /**
     * 将字符串转换为日期对象，字符串必须符合yyyy-MM-dd的格式
     *
     * @param s 要转化的字符串
     * @return 字符串转换成的日期.如字符串为NULL或空串, 返回NULL
     */
    public static Date toDate(String s) {
        s = org.apache.commons.lang3.StringUtils.trim(s);
        if (s.length() < 1) {
            return null;
        }
        try {
            if (s.length() <= 10) {
                return dateFormat.parse(s);
            }
            return toDate(Timestamp.valueOf(s));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将时间戳转化为日期
     * @param timeStamp
     * @return
     */
    public static Date toDataFormTimeStamp(long timeStamp){
        try {
            String s = timeStampFormat.format(timeStamp);
            if (s.length() <= 10) {
                return datetimeFormat.parse(s);
            }
            return toDate(Timestamp.valueOf(s));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字符串转换为日期对象，字符串必须符合yyyy-MM-dd HH:mm:ss.SSS的格式
     *
     * @param s 要转化的字符串
     * @return 字符串转换成的日期.如字符串为NULL或空串, 返回NULL
     */
    public static Date toDateTimeFromString(String s) {
        s = org.apache.commons.lang3.StringUtils.trim(s);
        if (s.length() < 1) {
            return null;
        }
        try {
            if (s.length() <= 10) {
                return datetimeFormat.parse(s);
            }
            return toDate(Timestamp.valueOf(s));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将日期对象转换为字符串，转换后的格式为yyyy-MM-dd HH:mm:ss
     *
     * @param date 要转换的日期对象
     * @return 字符串, 格式为yyyy-MM-dd HH:mm:ss
     */
    public static String toDatetimeString(Date date) {
        if (date == null) {
            return null;
        }
        return datetimeFormat.format(date);
    }

    /**
     * 将日期对象转换为字符串，转换后的格式为yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param date 要转换的日期对象
     * @return 字符串, 格式为yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String toTimeStampString(Date date) {
        if (date == null) {
            return null;
        }
        return timeStampFormat.format(date);
    }

    /**
     * 将日期对象转换为字符串，转换后的格式为yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param date 要转换的日期对象
     * @return 字符串, 格式为yyyy-MM-dd HH:mm:ss.SSS
     */
    public static Date toDatetime(String date) {
        if (date == null) {
            return null;
        }
        try {
            return timeStampFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 计算两个日期间相隔的周数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static int computeWeek(Date startDate, Date endDate) {

        int weeks = 0;

        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(startDate);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        while (beginCalendar.before(endCalendar)) {

            // 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环
            if (beginCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR)
                    && beginCalendar.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH) && beginCalendar
                    .get(Calendar.DAY_OF_WEEK_IN_MONTH) == endCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)) {
                break;

            } else {

                beginCalendar.add(Calendar.DAY_OF_YEAR, 7);
                weeks += 1;
            }
        }

        return weeks;
    }

    /**
     * 返回当前系统时间
     *
     * @return
     */
    public static String getCurrDateTime() {
        return toDatetimeString(new Date());

    }

    /**
     * 获取系统当前时间，待后期可扩展到取数据库时间
     *
     * @return 系统当前时间
     */
    public static String getCurrDate() {
        return toDateString(new Date());

    }

    /**
     * 将Timestamp转换为日期
     *
     * @param timestamp 时间戳
     * @return 日期对象.如时间戳为NULL, 返回NULL
     */
    public static Date toDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp.getTime());
    }

    /**
     * 将日期转换为Timestamp
     *
     * @param date 日期
     * @return 时间戳.如日期为NULL, 返回NULL
     */
    public static Timestamp toTimestamp(Date date) {
        if (date == null) {
            return null;
        }

        return new Timestamp(date.getTime());
    }

    /**
     * 将时间戳对象转化成字符串
     *
     * @param t 时间戳对象
     * @return 时间戳对应的字符串.如时间戳对象为NULL, 返回NULL
     */
    public static String toDateString(Timestamp t) {
        if (t == null) {
            return null;
        }
        return toDateString(toDate(t));
    }

    /**
     * 将Timestamp转换为日期时间字符串
     *
     * @param t 时间戳对象
     * @return Timestamp对应的日期时间字符串.如时间戳对象为NULL, 返回NULL
     */
    public static String toDatetimeString(Timestamp t) {
        if (t == null) {
            return null;
        }
        return toDatetimeString(toDate(t));
    }

    /**
     * 将日期字符串转换为Timestamp对象
     *
     * @param s 日期字符串
     * @return 日期时间字符串对应的Timestamp.如字符串对象为NULL, 返回NULL
     */

    public static Timestamp toTimestamp(String s) {
        return toTimestamp(toDate(s));
    }

    /**
     * 返回年份，如2014
     */
    public static int getYear(Date d) {

        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.YEAR);
    }

    public static int getYear() {
        return getYear(new Date());
    }

    /**
     * 返回月份，为1－－ － 12内
     */
    public static int getMonth(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getMonth() {
        return getMonth(new Date());
    }

    /**
     * 取得季度
     *
     * @param d 日期类型
     * @return
     */
    public static final int getQuarter(Date d) {
        return getQuarter(getMonth(d));
    }

    /**
     * 取得当前的季度
     *
     * @return
     */
    public static final int getQuarter() {
        return getQuarter(getMonth());
    }

    /**
     * 传递月份,取得季度
     *
     * @param num
     * @return
     */
    public static final int getQuarter(int num) {
        num = num % 3 == 0 ? num / 3 : (num / 3 + 1);
        return num % 4 == 0 ? 4 : num % 4;

    }

    /**
     * 返回日期，为1－－ － 31内
     */
    public static int getDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得将来的日期.如果timeDiffInMillis > 0,返回将来的时间;否则，返回过去的时间
     *
     * @param currDate         现在日期
     * @param timeDiffInMillis 毫秒级的时间差
     * @return 经过 timeDiffInMillis 毫秒后的日期
     */
    public static Date getFutureDate(Date currDate, long timeDiffInMillis) {
        long l = currDate.getTime();

        l += timeDiffInMillis;
        return new Date(l);
    }

    /**
     * 获得将来的日期.如果timeDiffInMillis > 0,返回将来的时间;否则，返回过去的时间
     *
     * @param currDate         现在日期
     * @param timeDiffInMillis 毫秒级的时间差
     * @return 经过 timeDiffInMillis 毫秒后的日期
     */
    public static Date getFutureDate(String currDate, long timeDiffInMillis) {
        return getFutureDate(toDate(currDate), timeDiffInMillis);
    }

    /**
     * 获得将来的日期.如果 days > 0,返回将来的时间;否则，返回过去的时间
     *
     * @param currDate 现在日期
     * @param days     经过的天数
     * @return 经过days天后的日期
     */
    public static Date getFutureDate(Date currDate, int days) {
        long l = currDate.getTime();
        long l1 = (long) days * ONE_DAY;

        l += l1;
        return new Date(l);
    }

    /**
     * 获得将来的日期.如果 days > 0,返回将来的时间;否则，返回过去的时间
     *
     * @param currDate 现在日期,字符型如2005-05-05 [14:32:10]
     * @param days     经过的天数
     * @return 经过days天后的日期
     */
    public static Date getFutureDate(String currDate, int days) {
        return getFutureDate(toDate(currDate), days);
    }

    /**
     * 检查是否在核算期内
     *
     * @param currDate  当前时间
     * @param dateRange 核算期日期范围
     * @return 是否在核算期内
     */
    public static boolean isDateInRange(String currDate, String[] dateRange) {
        if (currDate == null || dateRange == null || dateRange.length < 2) {
            throw new IllegalArgumentException(GlobalResultCode.PARAM_IS_INVALID.message());
        }
        currDate = getDatePart(currDate);
        return (currDate.compareTo(dateRange[0]) >= 0 && currDate.compareTo(dateRange[1]) <= 0);
    }

    /**
     * 只获取日期部分.获取日期时间型的日期部分
     *
     * @param currDate 日期[时间]型的字串
     * @return 日期部分的字串
     */
    public static String getDatePart(String currDate) {
        if (currDate != null && currDate.length() > 10) {
            return currDate.substring(0, 10);
        }

        return currDate;
    }

    /**
     * 计算两天的相差天数,不足一天按一天算
     *
     * @param stopDate  结束日期
     * @param startDate 开始日期
     * @return 相差天数 = 结束日期 - 开始日期
     */
    public static int getDateDiff(String stopDate, String startDate) {
        long t2 = toDate(stopDate).getTime();
        long t1 = toDate(startDate).getTime();

        int diff = (int) ((t2 - t1) / ONE_DAY); // 相差天数
        // 如有剩余时间，不足一天算一天
        diff += (t2 > (t1 + diff * ONE_DAY) ? 1 : 0);
        return diff;
    }

    /**
     * 计算两天的相差分钟,不足一分钟按一分钟算
     *
     * @param stopDate  结束日期
     * @param startDate 开始日期
     * @return 相差分钟数 = 结束日期 - 开始日期
     */
    public static int getMinutesDiff(String stopDate, String startDate) {
        long t2 = toDate(stopDate).getTime();
        long t1 = toDate(startDate).getTime();

        int diff = (int) ((t2 - t1) / ONE_MINUTE); // 相差分钟数
        // 如有剩余时间，不足一天算一天
        diff += (t2 > (t1 + diff * ONE_MINUTE) ? 1 : 0);
        return diff;
    }

    /**
     * 判断两个日期是否在同一周
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setFirstDayOfWeek(Calendar.MONDAY);
        cal2.setFirstDayOfWeek(Calendar.MONDAY);
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 按月获取周序号
     *
     * @param currDate
     * @return
     */
    public static int getSeqWeekByMonth(Date currDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(currDate);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        return c.get(Calendar.WEEK_OF_MONTH);
    }

}