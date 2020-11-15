package com.sugon.analysis.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author hxb
 */
@Slf4j
public class DateUtil {
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            //�����������ڵ�ǰʱ�䣬�޷�����
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //��ǰ���
        int yearNow = cal.get(Calendar.YEAR);
        //��ǰ�·�
        int monthNow = cal.get(Calendar.MONTH);
        //��ǰ����
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //����������
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {

                if (dayOfMonthNow < dayOfMonthBirth){
                    //��ǰ����������֮ǰ�������һ
                    age--;
                }
            } else {
                //��ǰ�·�������֮ǰ�������һ
                age--;
            }
        } return age;
    }

    /**
     * @����������
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static  Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }

    /**
     * @��ȡ��ǰ���
     * @return
     */
    public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * @��ȡ��������м���������
     * @param startDate
     * @param endxDate
     * @return
     */
    public static List<String> getYears(String startDate, String endxDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int calendarType = Calendar.YEAR;
        List<String> result = new ArrayList<>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        try {
            min.setTime(sdf.parse(startDate));
            min.add(calendarType, 0);
            max.setTime(sdf.parse(endxDate));
            max.add(calendarType, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (min.before(max)) {
            result.add(sdf.format(min.getTime()));
            min.add(calendarType, 1);
        }
        return result;
    }

    /**
     ? ? ?* ��ȡ��������֮�����������
     ? ? ?*?
     ? ? ?* @param startDate??��ʼ����
     ? ? ?* @param endDate? ?��������
     ? ? ?* @return ?List����
     ? ? ?*/
    public static List<String> getDays(String startTime, String endTime) {

        // ���ص����ڼ���
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// ���ڼ�1(��������)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }


}
