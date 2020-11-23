package com.sugon.protal.utils;

import Util.Arithmetic.time_date.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils9 {
	/**
	 * 一个日期早于一个日期区间
	 */
	public static final int BEFORE_START_DATE = -2;

	/**
	 * 一个日期等于一个日期区间的开始日期
	 */
	public static final int EQUAL_START_DATE = -1;

	/**
	 * 一个日期在一个日期区间之内
	 */
	public static final int BETWEEN_TOW_DATE = 0;

	/**
	 * 一个日期等于一个日期区间的结束日期
	 */
	public static final int EQUAL_END_DATE = 1;

	/**
	 * 一个日期晚于一个日期区间
	 */
	public static final int AFTER_END_DATE = 2;

	/**
	 * 三个日期相等
	 */
	public static final int TREE_DATE_EQUAL = 3;

	/**
	 * 普通日期格式
	 */
	public static final String NORMAL_DATE_PATTERN = "yyyy-MM-dd";

	public static final String NORMAL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final String ZHCN_DATE_PATTERN = "yyyy年MM月dd日";

	public static final String ZHCN_MONTH_DAY_PATTERN = "MM月dd日";

	public static final String DATE_PATTERN_YMD = "yyyyMMdd";

	public static final String ZHCN_DATE_TIME_PATTERN = "yyyy年MM月dd日 HH:mm:ss";

	private DateUtils() {
	}

	/**
	 * @param firstDate
	 * @param secondDate
	 * @return
	 * @Description:判断<firstDate>时间点是否在<secondDate>时间点之前 如果此 firstDate
	 * 的时间在参数<secondDate
	 * >表示的时间之前，则返回小于 0 的值
	 * @ReturnType boolean
	 * @author:
	 * @Created 2012 2012-9-20上午08:40:33
	 */
	public static boolean isBefore(Date firstDate, Date secondDate) {

		return compare(firstDate, secondDate) < 0 ? true : false;
	}

	/**
	 * @param firstDate
	 * @param secondDate
	 * @Description:判断<firstDate>时间点是否在<secondDate>时间点之后 如果此 firstDate
	 * 的时间在参数<secondDate
	 * >表示的时间之后，则返回大于 0 的值
	 * @ReturnType boolean
	 * @author:
	 * @Created 2012 2012-9-20上午08:38:48
	 */
	public static boolean isAfter(Date firstDate, Date secondDate) {

		return compare(firstDate, secondDate) > 0 ? true : false;
	}

	/**
	 * @param firstDate
	 * @param secondDate
	 * @Description:比较两个时间点是否相等
	 * @ReturnType boolean
	 * @author:
	 * @Created 2012 2012-9-20上午08:37:30
	 */
	public static boolean isEqual(Date firstDate, Date secondDate) {

		return compare(firstDate, secondDate) == 0 ? true : false;
	}

	/**
	 * @param firstDate
	 * @param secondDate
	 * @Description:比较两个时间点 如果secondDate表示的时间等于此 firstDate 表示的时间，则返回 0 值； 如果此
	 * firstDate 的时间在参数<secondDate>表示的时间之前，则返回小于 0 的值； 如果此
	 * firstDate 的时间在参数<secondDate>表示的时间之后，则返回大于 0 的值
	 * @ReturnType int
	 * @author:
	 * @Created 2012 2012-9-20上午08:34:33
	 */
	public static int compare(Date firstDate, Date secondDate) {

		Calendar firstCalendar = null;
		/** 使用给定的 Date 设置此 Calendar 的时间。 **/
		if (firstDate != null) {
			firstCalendar = Calendar.getInstance();
			firstCalendar.setTime(firstDate);
		}

		Calendar secondCalendar = null;
		/** 使用给定的 Date 设置此 Calendar 的时间。 **/
		if (firstDate != null) {
			secondCalendar = Calendar.getInstance();
			secondCalendar.setTime(secondDate);
		}

		try {
			/**
			 * 比较两个 Calendar 对象表示的时间值（从历元至现在的毫秒偏移量）。 如果参数表示的时间等于此 Calendar
			 * 表示的时间，则返回 0 值； 如果此 Calendar 的时间在参数表示的时间之前，则返回小于 0 的值； 如果此
			 * Calendar 的时间在参数表示的时间之后，则返回大于 0 的值
			 * **/
			return firstCalendar.compareTo(secondCalendar);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @param inDate
	 * @return
	 * @Description:
	 * @ReturnType int
	 * @author:
	 * @Created 2012 2012-9-20上午08:42:06
	 */
	public static int betweenTowDate(Date startDate, Date endDate, Date inDate) {

		/**
		 * 判断<endDate>时间点是否在<startDate>时间点之前
		 * 如果为真表示<endDate>时间点在<startDate>时间点之前则抛出异常 即结束时间在开始时间之前是不正常的
		 */
		if (isBefore(endDate, startDate)) {
			throw new IllegalArgumentException(
					"endDate can not before startDate!");
		}

		/**
		 * 比较两个时间点<inDate>和<startDate> 如果inDate表示的时间等于此 startDate 表示的时间，则返回 0 值；
		 * 如果此 inDate 的时间在参数<startDate>表示的时间之前，则返回小于 0 的值； 如果此 inDate
		 * 的时间在参数<startDate>表示的时间之后，则返回大于 0 的值
		 */
		int sflag = compare(inDate, startDate);

		/**
		 * 比较两个时间点<inDate>和<endDate> 如果inDate表示的时间等于此 endDate 表示的时间，则返回 0 值； 如果此
		 * inDate 的时间在参数<endDate>表示的时间之前，则返回小于 0 的值； 如果此 inDate
		 * 的时间在参数<endDate>表示的时间之后，则返回大于 0 的值
		 */
		int eflag = compare(inDate, endDate);

		int flag = 0;

		/** 如果此 inDate 的时间在参数<startDate>表示的时间之前，则返回小于 0 的值 **/
		if (sflag < 0) {
			/** 一个日期早于一个日期区间 **/
			flag = DateUtils.BEFORE_START_DATE;
		} else if (sflag == 0) {
			/** 如果inDate表示的时间等于此 startDate 表示的时间，则返回 0 值； **/
			/** 如果inDate表示的时间等于此 endDate 表示的时间，则返回 0 值； **/
			if (eflag == 0) {
				/** 三个日期相等 **/
				flag = DateUtils.TREE_DATE_EQUAL;
			} else {
				/** 一个日期等于一个日期区间的开始日期 **/
				flag = DateUtils.EQUAL_START_DATE;
			}
		} else if (sflag > 0 && eflag < 0) {
			/**
			 * 满足-inDate 的时间在参数<startDate>表示的时间之后，和 inDate
			 * 的时间在参数<endDate>表示的时间之前
			 **/
			/** 一个日期在一个日期区间之内 **/
			flag = DateUtils.BETWEEN_TOW_DATE;
		} else if (eflag == 0) {
			/** 如果inDate表示的时间等于此 endDate 表示的时间 **/
			/** 一个日期等于一个日期区间的结束日期 **/
			flag = DateUtils.EQUAL_END_DATE;
		} else if (eflag > 0) {
			/** 满足inDate 的时间在参数<endDate>表示的时间之后 **/
			/** 一个日期晚于一个日期区间 **/
			flag = DateUtils.AFTER_END_DATE;
		}
		return flag;
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 * @Description:分别判断当前日期是与一个日期区间的六种情况比较 （1） 一个日期早于一个日期区间 （2）三个日期相等
	 * （3）一个日期等于一个日期区间的开始日期
	 * （4）一个日期在一个日期区间之内
	 * （5）一个日期等于一个日期区间的结束日期 （6）一个日期晚于一个日期区间
	 * @ReturnType int
	 * @author:
	 * @Created 2012 2012-9-20上午09:00:31
	 */
	public static int betweenTowDate(Date startDate, Date endDate) {
		return betweenTowDate(startDate, endDate, new Date());
	}

	/**
	 * 将Date型转换为String型
	 *
	 * @param dtDate     Date 要转换的时间
	 * @param strPattern String 转换的格式
	 * @return String 转换后的时间 说明：格式可以为 yyyy-MM-dd HH:mm:ss等，可任意组合，如 yyyy年MM月dd日
	 * yyyy代表年 MM代表月 dd代表日 HH hh kk代表小时 mm代表分钟 ss代表秒钟 format
	 */
	public static String dateToString(Date dtDate, String strPattern) {
		SimpleDateFormat lsdfDate = new SimpleDateFormat(strPattern);
		return lsdfDate.format(dtDate);
	}

	/**
	 * @param dateStr 日期字符串
	 * @param pattern 日期格式
	 * @Description:将符合相应格式的字符串转化为日期 <格式自定义>
	 * @ReturnType Date 日期字串为空或者不符合日期格式时返回null
	 * @author:
	 * @Created 2012 2012-9-20上午09:06:00
	 */
	public static Date getDate(String dateStr, String pattern) {

		return getDate(dateStr, pattern, null);
	}

	/**
	 * 将符合相应格式的字符串转化为日期 格式自定义
	 *
	 * @param dateStr     日期字符串
	 * @param pattern     日期格式
	 * @param defaultDate 默认日期
	 * @return 日期字串为空或者不符合日期格式时返回defaultDate
	 */
	public static Date getDate(String dateStr, String pattern, Date defaultDate) {

		if (dateStr != null && pattern != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				return sdf.parse(dateStr);
			} catch (ParseException e) {

			}
		}
		return defaultDate;
	}

	/**
	 * @param date    需要格式的日期参数
	 * @param pattern 日期格式
	 * @Description:将某种日期转换成指定格式的日期数据 <获取相应格式的Date>
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-20上午09:08:24
	 */
	public static Date getFormatDate(Date date, String pattern) {

		if (date == null) {
			throw new IllegalArgumentException("date can not be null!");
		}

		if (pattern == null) {
			pattern = NORMAL_DATE_PATTERN;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		String dateStr = sdf.format(date);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {

		}
		return date;
	}

	/**
	 * @param dateStr
	 * @return 日期字串为空或者不符合日期格式时返回null
	 * @Description:将符合相应格式的字符串转化为日期 格式 yyyy-MM-dd
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-20上午09:04:28
	 */
	public static Date getDate(String dateStr) {
		return getDate(dateStr, DateUtils.NORMAL_DATE_PATTERN);
	}

	/**
	 * @Description:将当前日期转换成字符串 格式 yyyy-MM-dd
	 * @ReturnType String
	 * @author:
	 * @Created 2012 2012-9-19下午05:54:42
	 */
	public static String datetoStr() {
		SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_PATTERN);
		String curDate = sdf.format(new Date());
		return curDate;
	}

	/**
	 * @Description: 获取当天日期, 日期格式为YYYYMMDD
	 * @ReturnType String
	 * @author:
	 * @Created 2012 2012-9-20上午09:17:19
	 */
	public static String getIntraday() {
		Calendar date = Calendar.getInstance();// 获得当前日期
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH) + 1;
		int day = date.get(Calendar.DAY_OF_MONTH);
		String updateFileDate = new String(new Integer(year).toString()
				+ new Integer(month).toString() + new Integer(day).toString());
		return updateFileDate;
	}

	/**
	 * @return
	 * @Description:获取当天日期，日期格式为YYYY-MM-DD HH:mm:ss
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-20上午09:58:36
	 */
	public static Date getCurrentlyDate() {
		Date currentDate = new Date();
		return getFormatDate(currentDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @return
	 * @Description:获取当天日期，日期格式为YYYY-MM-DD
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-20上午09:58:36
	 */
	public static String getCurrentlyDateStr() {
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(currentDate);
		return dateStr;
	}

	/**
	 * @return
	 * @Description:获取当天日期
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-20上午09:58:36
	 */
	public static Timestamp getCurrentlyTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * @return
	 * @Description: 获取时间的小时数（24小时制）
	 * @ReturnType int
	 * @author:
	 * @Created 2012 2012-9-25下午08:33:44
	 */
	public static int getTime24Hour() {

		Calendar calender = Calendar.getInstance();
		calender.setTime(getCurrentlyDate());
		return calender.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * @param date
	 * @return
	 * @Description:获取前一日
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-25下午08:32:00
	 */
	public static Date getBeforeDate(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DATE, -1);
		return calender.getTime();
	}

	/**
	 * @Description:取得当前日期的下一日
	 * @ReturnType String
	 * @author:
	 * @Created 2012 2012-9-20上午09:12:06
	 */
	public static String getTomorrowDate() {
		Date myDate = new Date();
		Calendar calender = Calendar.getInstance();
		calender.setTime(myDate);
		calender.add(Calendar.DATE, 1);
		return dateToString(calender.getTime(), "yyyy-MM-dd");
	}

	/**
	 * @return
	 * @Description:取当前日期后的第二日
	 * @ReturnType String
	 * @author:
	 * @Created 2012 2012-9-20上午10:37:47
	 */
	public static String getNextTomorrowDate() {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Date myDate = new Date();

		long myTime = (myDate.getTime() / 1000) - 60 * 60 * 24 * 365;

		myDate.setTime(myTime * 1000);

		String mDate = formatter.format(myDate);

		myTime = (myDate.getTime() / 1000) + 60 * 60 * 24;

		myDate.setTime(myTime * 1000);

		mDate = formatter.format(myDate);

		return mDate;
	}

	/**
	 * @param date
	 * @return
	 * @Description:获取当前星期在当前月份中的第几个星期
	 * @ReturnType int
	 * @author:
	 * @Created 2012 2012-10-29上午11:45:13
	 */
	public static int getTimeWeekOfMonth(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		return calender.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 功能描述：获取时间在当前星期的第几天
	 *
	 * @param date
	 * @return 返回星期数, 其中: 1表示星期一, ...7表示星期天
	 * <p>
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 * @author <p>
	 * 创建日期 ：2012 2012-10-29上午11:45:52
	 * </p>
	 */
	public static int getTimeDateOfWeek(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		int week = calender.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == 0)
			week = 7;
		return week;
	}

	/**
	 * 功能描述：获取时间在当前星期的第几天
	 *
	 * @param date
	 * @return 返回星期数, 其中: 7表示星期六..1表示星期天
	 * <p>
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 * @author <p>
	 * 创建日期 ：2012 2012-10-29上午11:45:52
	 * </p>
	 */
	public static int getQuartzTimeDateOfWeek(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		int week = calender.get(Calendar.DAY_OF_WEEK);
		return week;
	}

	/**
	 * @return
	 * @Description:某个时间与当前时间进行求差
	 * @ReturnType long
	 * @author:
	 * @Created 2012 2012-12-12下午01:00:53
	 */
	public static long getAppointTimeDifference(Date startDate, Date endDate) {
		long l = endDate.getTime() - startDate.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * @return
	 * @Description:获取当天日期，日期格式为MM月DD日
	 * @ReturnType Date
	 * @author: tww
	 */
	public static String getCurrentMonthAndDayStr() {
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(ZHCN_MONTH_DAY_PATTERN);
		String dateStr = sdf.format(currentDate);
		return dateStr;
	}

	/**
	 * @param days:指定几天
	 * @Description:获取当天日期前的指定天数日期数组,如今天是10月1日,参数是7,则结果是:["10月1日","10月2日","10月3日","10月4日","10月5日","10月6日","10月7日"]
	 * @author: tww
	 */
	public static String[] getMonthAndDaysFromTodayCnStrs(int days) {
		String[] result = new String[days];
		SimpleDateFormat fomater = new SimpleDateFormat(ZHCN_MONTH_DAY_PATTERN);
		for (int i = 0; i < days; i++) {
			Calendar calendar = Calendar.getInstance();
			int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
			calendar.set(Calendar.DAY_OF_YEAR, dayOfYear - i);
			result[(days - 1) - i] = fomater.format(calendar.getTime());
		}
		return result;
	}

	/**
	 * @param days:指定几天
	 * @Description:获取当天日期前的指定天数日期数组,如今天是1007,参数是7,则结果是:["1001","1002","1003","1004","1005","1006","1007"]
	 * @author: tww
	 */
	public static String[] getMonthAndDaysFromTodayEnStrs(int days) {
		String[] result = new String[days];
		SimpleDateFormat fomater = new SimpleDateFormat(DATE_PATTERN_YMD);
		for (int i = 0; i < days; i++) {
			Calendar calendar = Calendar.getInstance();
			int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
			calendar.set(Calendar.DAY_OF_YEAR, dayOfYear - i);
			result[(days - 1) - i] = fomater.format(calendar.getTime());
		}
		return result;
	}

	/**
	 * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
	 *
	 * @param date       String 想要格式化的日期
	 * @param oldPattern String 想要格式化的日期的现有格式
	 * @param newPattern String 想要格式化成什么格式
	 * @return String
	 */
	public static String convertStringDateFormat(String date, String oldPattern, String newPattern) {
		if (date == null || oldPattern == null || newPattern == null) {
			return "";
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern);        // 实例化模板对象
		SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern);        // 实例化模板对象
		Date d = null;
		try {
			d = sdf1.parse(date);   // 将给定的字符串中的日期提取出来
		} catch (Exception e) {            // 如果提供的字符串格式有错误，则进行异常处理
			e.printStackTrace();       // 打印异常信息
		}
		return sdf2.format(d);
	}

}