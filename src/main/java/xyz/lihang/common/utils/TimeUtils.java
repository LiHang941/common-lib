package xyz.lihang.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间转毫秒
 *
 * @author : lihang1329@gmail.com
 * @since : 18-3-20
 */
public class TimeUtils {


    /**
     * n 秒
     *
     * @param second
     * @return
     */
    public static Long timeBySecond(int second) {
        return second * 1000L;
    }

    /**
     * n分钟
     *
     * @param minute
     * @return
     */
    public static Long timeByMinute(int minute) {
        return minute * timeBySecond(60);
    }


    /**
     * n小时
     *
     * @param hour
     * @return
     */
    public static Long timeByHour(int hour) {
        return hour * timeByMinute(60);
    }


    /**
     * n 天的毫秒
     *
     * @param days
     * @return
     */
    public static Long timeByDay(int days) {
        return days * timeByHour(24);
    }


    public static boolean checkTimeOut (Long startTime,Long timeOut){
        return new Date().getTime() >= (startTime + timeOut);
    }

    /** 检查时间是否超过当前时间 */
    public static boolean checkEndTime(Long endTime){
        return new Date().getTime() >=  endTime;
    }


    /** 获取当天时间的毫秒值 */
    public static long toDay0Hour(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /** 获取当天时间的毫秒值 */
    public static long toDay0Hour(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


}
