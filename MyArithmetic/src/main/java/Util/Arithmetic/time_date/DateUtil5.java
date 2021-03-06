package Util.Arithmetic.time_date;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
public class DateUtil5 {

    public static String getNowDate() {
        return LocalDate.now().toString() + " 00:00:00.0";
    }

    public static Long convertTimeToLong(String date) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime parse = LocalDateTime.parse(date, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static String dateToString(DateTimeFormatter dateTimeFormatter, LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }

    public static LocalDateTime stringToDate(DateTimeFormatter dateTimeFormatter, String str) {
        return LocalDateTime.parse(str, dateTimeFormatter);
    }

    public static void main(String[] args) {

        System.out.println(getHalfYearStartTime());

    }


    public static String getHalfYearStartTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 5); //逐次往前推1个月
        return sdf.format(cal.getTime());
    }

    private static void getBetweenDates(Date start, Date end) {
        List<Integer> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        // result.add(start);
        while (tempStart.before(tempEnd)) {
            System.out.println(tempStart.get(Calendar.YEAR));
            // result.add(tempStart.get(Calendar.YEAR));
            // tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        // return result;
    }

    /**
     * 日期格式化：20190205转2019-02-01
     * @param date //
     * @return //
     */
    public static String formatDate(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        return year + "-" + month + "-" + day;
    }

    public static String getSysYear() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy");
        return now.format(dateTimeFormatter);
    }

    /**
     * 获取系统时间
     *
     * @return 时间字符串如 "2018-03-13"
     */
    public static String getSysDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(dateTimeFormatter);
    }
    public static String getSysTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(dateTimeFormatter);
    }


    /**
     * 计算年份差
     * @param workingHours 开始时间
     * @return 年份差
     * @throws ParseException //
     */
    public static String getWorkingYears(String workingHours,String applicatYear) throws ParseException {
        String sysTime = applicatYear!=null?applicatYear:getSysDate();
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        bef.setTime(sdf.parse(sysTime));
        aft.setTime(sdf.parse(workingHours));
        int surplus = aft.get(Calendar.DATE) - bef.get(Calendar.DATE);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int year = aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR);
        if(result < 0){
            result = 1;
        }else if(result == 0){
            result = surplus <= 0 ? 1 : 0;
        }else{
            result = 0;
        }
        int num = (Math.abs(year)) + result;
        log.info("相差年份：" + num);
        return String.valueOf(num);
    }

    /**
     * 年假计算
     * 已满1年不满10年的，年休假5天；
     * 已满10年不满20年的，年休假10天；
     * 已满20年的，年休假15天
     * @param workingYears 工作年限
     * @return 年假天数
     */
    public static int getLeaveDays(String workingYears) {
        int parseInt = Integer.parseInt(workingYears);
        if (parseInt < 1) {
            return 0;
        } else if (parseInt < 10) {
            return 5;
        } else if (parseInt < 20) {
            return 10;
        } else {
            return 15;
        }
    }

    /**
     * 计算婚假
     * @param sex 性别
     * @param age 年龄
     * @return 婚假
     */
    public static int getMarriageDays(String sex, String age) {
        int parseInt = Integer.parseInt(age);
        if ("男".equals(sex)) {
            if (parseInt >= 25) {
                return 15;
            } else {
                return 3;
            }
        } else {
            if (parseInt >= 23) {
                return 15;
            } else {
                return 3;
            }
        }
    }

    /**
     * 时间字符串转Date
     * @param strDate //
     * @return //
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 获取某个月的天数
     * @param date //
     * @return //
     */
    public static int getDaysOfMonth(String date) {
        Date date1 = strToDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


}
