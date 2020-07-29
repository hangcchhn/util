package hn.cch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 根据String的时间格式将其转化为Date
     *
     * @param pattern
     * @param text
     * @return
     */
    public static Date toDate(String pattern, String text) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date date = sdf.parse(text);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("date format parse error : " + e.getMessage());
            return null;
        }

    }

    /**
     * 将Date转化为指定时间格式的String
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String toText(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String text = sdf.format(date);
        return text;
    }

    /**
     * 获取date日期之前的day天的yyyyMMdd时间格式列表
     * <p>
     * 用于处理每天触发的定时任务
     *
     * @param date
     * @param day
     * @return
     */
    public static List<String> getDays(Date date, int day) {

        ArrayList<String> days = new ArrayList<String>();
        String pattern = "yyyyMMdd";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for (int i = 0; i < day; i++) {
            calendar.add(Calendar.DATE, -i);
            days.add(toText(pattern, calendar.getTime()));
        }

        return days;
    }

    /**
     * 改变日期时间
     *
     * @param date 日期时间
     * @param time 改变单位:依从大到小次序年月日时分秒标记单位为654321
     * @param size 改变大小：正数延后，负数提前
     * @return
     */
    public static Date changeDate(Date date, int time, int size) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        /*
        根据Calendar类中的定义来
        public final static int YEAR = 1;
        public final static int MONTH = 2;
        public final static int DATE = 5;
        public final static int HOUR = 10;
        public final static int MINUTE = 12;
        public final static int SECOND = 13;
        public final static int MILLISECOND = 14;
         */
        int temp = Calendar.MILLISECOND;//默认值
        switch (time) {
            case 1:
                temp = Calendar.SECOND;
                break;
            case 2:
                temp = Calendar.MINUTE;
                break;
            case 3:
                temp = Calendar.HOUR;
                break;
            case 4:
                temp = Calendar.DATE;
                break;
            case 5:
                temp = Calendar.MONTH;
                break;
            case 6:
                temp = Calendar.YEAR;
                break;
            default:
                logger.error(time + "依从大到小次序年月日时分秒标记单位为654321");
                return calendar.getTime();
        }
        calendar.add(temp, size);
        return calendar.getTime();
    }

    /**
     * 两个日期时间比较：以第一日期时间为当前日期时间
     * @param now 当作当前日期时间
     * @param date
     * @param flag 之前true或之后false
     * @return 比较之前之后
     */
    public static boolean compareDate(Date now, Date date, boolean flag) {

        if (flag) {//之前
            if (now.before(date)) {
                return true;
            } else {
                return false;
            }
        } else {//之后
            if (now.after(date)) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * @param datetime
     * @return
     */
    public static boolean syncTime(Date datetime) {
        try {
            String osName = System.getProperty("os.name");
            logger.info("os.name:{}", osName);
            String[] exec = null;
            if (osName.matches("^(?i)Windows.*$")) {
                // 日期格式：yyyy-MM-dd
                String date = toText("yyyy-MM-dd", datetime);
                // 时间格式：HH:mm:ss
                String time = toText("HH:mm:ss", datetime);
                exec = new String[]{"cmd", "/c", "date", date, "time", time};
            } else if (osName.matches("^(?i)Linux.*$")) {
                // 日期时间格式：yyyy-MM-dd HH:mm:ss
                String text = toText("yyyy-MM-dd HH:mm:ss", datetime);
                exec = new String[]{"date", "-s", text};
            } else {
                logger.error("sync time system unknown windows|linux");
                return false;
            }
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(exec);
            int waitFor = process.waitFor();
            int exitValue = process.exitValue();
            return waitFor == 0 && exitValue == 0;
        } catch (Exception e) {
            logger.error("sync time Exception error : " + e.getMessage());
            return false;
        }


    }


}
