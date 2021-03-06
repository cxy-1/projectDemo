package Util.Arithmetic.time_date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author MaJing
 * @date 2018/1/18
 */
public class DateTimeUtil8 {

    public static final String OLD_STANDER_FORMAT="yyyyMMdd";

    public static String  getControlDay(String formatStr,Integer days){
        DateTime dateTime=new DateTime(new Date());
        //昨天
        DateTime yesterday = dateTime.plusDays(days);
        return  yesterday.toString(formatStr);
    }

    public static String getMonFirst(){
        DateTime dt = new DateTime();
        // 获取当前时间月的第一天
        LocalDate firstDayOfMonth = dt.toLocalDate().withDayOfMonth(1);
        return firstDayOfMonth.toString(OLD_STANDER_FORMAT);
    }

    public static String getMonLast(){
        return  DateTime.now().dayOfMonth().withMaximumValue().toString(OLD_STANDER_FORMAT);
    }

    public static String getYesterday(String formatStr){
        DateTime dateTime = new DateTime(new Date());
        //昨天
        DateTime yesterday = dateTime.minusDays(1);
        return  yesterday.toString(formatStr);
    }

    public static String getYester2day(String formatStr){
        DateTime dateTime = new DateTime(new Date());
        //昨天
        DateTime yesterday = dateTime.minusDays(2);
        return  yesterday.toString(formatStr);
    }

    public static String minus(int day){
        DateTime dateTime = new DateTime(new Date());
        //昨天
        DateTime week = dateTime.minusDays(day);
        return  week.toString(OLD_STANDER_FORMAT);
    }

    public static String getLastWeek(String formatStr){
        DateTime dateTime = new DateTime(new Date());
        //昨天
        DateTime week = dateTime.minusDays(7);
        return week.toString(formatStr);
    }

    public static String getLastTwoWeek(String formatStr){
        DateTime dateTime = new DateTime(new Date());
        //昨天
        DateTime week = dateTime.minusDays(14);
        return week.toString(formatStr);
    }

    public static String getLastMon(String formatStr){
        DateTime dateTime = new DateTime(new Date());
        //昨天
        DateTime mon = dateTime.minusDays(30);
        return  mon.toString(formatStr);
    }

    public static String getLastThreeMon(String formatStr){
        DateTime dateTime = new DateTime(new Date());
        //昨天
        DateTime mon = dateTime.minusDays(90);
        return mon.toString(formatStr);
    }

    public static String getLastTwoMon(String formatStr){
        DateTime dateTime = new DateTime(new Date());
        //昨天
        DateTime mon = dateTime.minusDays(60);
        return mon.toString(formatStr);
    }

    public static String getLastYear(String formatStr){
        DateTime dateTime = new DateTime(new Date());
        //一年
        DateTime mon = dateTime.minusDays(365);
        return  mon.toString(formatStr);
    }

    public static String getDay(String formatStr){
        DateTime dateTime = new DateTime(new Date());
        //今天
        return dateTime.toString(formatStr);
    }

    public static String getNow(){
        DateTime dateTime = new DateTime(new Date());
        return  dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    public static String getNow(String formatStr){
        DateTime dateTime = new DateTime(new Date());
        return  dateTime.toString(formatStr);
    }

    public static String getLastSecond(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateTime dateTime = new DateTime(date);
        //昨天
        DateTime lastSecond = dateTime.minusSeconds(1);
        return  lastSecond.toString("yyyyMMddHHmmss");
    }

    public static String getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dates = simpleDateFormat.format(todayStart.getTime());
        return dates;
    }

    public static final String STANDER_FORMAT="yyyy-MM-dd HH:mm:ss";

    public static Date strToDate(String dateTimeStr,String formatStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date,String formatStr){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDER_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDER_FORMAT);
    }


}
