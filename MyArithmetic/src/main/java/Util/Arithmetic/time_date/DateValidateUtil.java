package com.sugon.log.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 日期格式的统一校验
 * */
@Slf4j
public class DateValidateUtil {

          /*  20130118
            20130118104043
            2014/4/29
            2000-06-12 00:00:00.000
            2011-7-18
            2015年8月28日
            2016-6-20 0:12:43
            2005/10/31 16:30:13
            2012-6-6 9:53:24
            10-6月 -15 03.16.30.572000 下午*/

    public static void main(String[] args) {

        String dateStr="2012-6-6 9:53:24";
        validate(dateStr);

    }

    public static void validate(String dateStr) {


        String []dateFormat={"yyyyMMdd","yyMMddHHmmss","yyyy/MM/dd","yyyy-MM-dd HH:mm:ss.SSSZ","yyyy-MM-dd","yyyy年M月d日",
                "yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss"};

        boolean dateFlag=true;

        for(String dates:dateFormat){

            SimpleDateFormat format=new SimpleDateFormat(dates);
            format.setLenient(false);
            try {

                Date date = format.parse(dateStr);

                log.info("format:{},dateStr：{}",dates,dateStr);

                dateFlag=true;

            }catch (ParseException e)
            {

                dateFlag=false;
                log.info("error format:{}",dates);
            }finally{
                log.info("日期是否满足要求"+dateFlag);
            }

            if(dateFlag){
                break;
            }

        }

    }


}
