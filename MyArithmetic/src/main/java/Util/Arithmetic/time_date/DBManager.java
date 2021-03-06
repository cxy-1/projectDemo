package com.sugon.sgcl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.sgcl.foodinfo.service.impl.SpwlqyServiceImpl;

public class DateUtils {

 public static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

 /**
  *
  * 日期转换
  *
  * */
 public static String DateChange(String strDate) {

  SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
  Date date;
  try {
   date = format.parse(strDate);
   format = new SimpleDateFormat("yyyy-MM");
   String newD = format.format(date);
   return newD;
  } catch (ParseException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
   throw new RuntimeException("日期格式有误");
  }

 }


 public static String DateChangeTwo(String strDate) {

  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
  Date date;
  try {
   date = format.parse(strDate);
   format = new SimpleDateFormat("yyyyMM");
   String newD = format.format(date);
   return newD;
  } catch (ParseException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
   throw new RuntimeException("日期格式有误");
  }

 }

 /**
  *
  * 根据日期处理数据范围
  *
  * */
 public static List DealList(String []times,String date,List list){
  int len=times.length-1;
  int positon = Arrays.binarySearch(times, date);
  int middle = (1 + len) / 2;

  logger.info(middle+"=="+positon+"=="+len);

  //获取位置
  if(positon == len){//最大的日期
   list=list.subList((len-3), len+1);
  }else if(positon == 0){//最小
   list=list.subList(0, 4);
  }else{//取周围的四个
   if(positon<middle){
    list=list.subList((positon-1), (positon+2)+1);
   }else{
    list=list.subList((positon-2), (positon+1)+1);
   }
  }
  return list;
 }

 //取周围的几个元素   1 2 3 4 5 6 7 8
 public static List DealList(String []times,String date,List list,int num){
  int len=times.length;
  int positon = Arrays.binarySearch(times, date);   //下标从0开始
  int end=0;
  int start=0;
  if((positon+num)>len){ //说明最大到len
   end=len;
   start=end-num;
  }else if (positon==0){
   end=positon+num;
   start=positon;
  }else {
   end=positon+num-1;
   start=positon-1;
  }
  //获取位置
  if(positon == len){//最大的日期
   list=list.subList((list.size()-(num)), list.size());
  }else if(positon == 0){//最小
   list=list.subList(0, num);
  }else if(end<list.size()){//取周围的四个
   list=list.subList(start,end);
  }else{
   list=list.subList((list.size()-(num)), list.size());
  }
  return list;
 }

 public static void main(String[] args) {
  System.out.println(DateChange("201709"));
  System.out.println(DateChangeTwo("2017-09"));
 }

}
