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
	 * һ����������һ����������
	 */
	public static final int BEFORE_START_DATE = -2;

	/**
	 * һ�����ڵ���һ����������Ŀ�ʼ����
	 */
	public static final int EQUAL_START_DATE = -1;

	/**
	 * һ��������һ����������֮��
	 */
	public static final int BETWEEN_TOW_DATE = 0;

	/**
	 * һ�����ڵ���һ����������Ľ�������
	 */
	public static final int EQUAL_END_DATE = 1;

	/**
	 * һ����������һ����������
	 */
	public static final int AFTER_END_DATE = 2;

	/**
	 * �����������
	 */
	public static final int TREE_DATE_EQUAL = 3;

	/**
	 * ��ͨ���ڸ�ʽ
	 */
	public static final String NORMAL_DATE_PATTERN = "yyyy-MM-dd";

	public static final String NORMAL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final String ZHCN_DATE_PATTERN = "yyyy��MM��dd��";

	public static final String ZHCN_MONTH_DAY_PATTERN = "MM��dd��";

	public static final String DATE_PATTERN_YMD = "yyyyMMdd";

	public static final String ZHCN_DATE_TIME_PATTERN = "yyyy��MM��dd�� HH:mm:ss";

	private DateUtils() {
	}

	/**
	 * @param firstDate
	 * @param secondDate
	 * @return
	 * @Description:�ж�<firstDate>ʱ����Ƿ���<secondDate>ʱ���֮ǰ ����� firstDate
	 * ��ʱ���ڲ���<secondDate
	 * >��ʾ��ʱ��֮ǰ���򷵻�С�� 0 ��ֵ
	 * @ReturnType boolean
	 * @author:
	 * @Created 2012 2012-9-20����08:40:33
	 */
	public static boolean isBefore(Date firstDate, Date secondDate) {

		return compare(firstDate, secondDate) < 0 ? true : false;
	}

	/**
	 * @param firstDate
	 * @param secondDate
	 * @Description:�ж�<firstDate>ʱ����Ƿ���<secondDate>ʱ���֮�� ����� firstDate
	 * ��ʱ���ڲ���<secondDate
	 * >��ʾ��ʱ��֮���򷵻ش��� 0 ��ֵ
	 * @ReturnType boolean
	 * @author:
	 * @Created 2012 2012-9-20����08:38:48
	 */
	public static boolean isAfter(Date firstDate, Date secondDate) {

		return compare(firstDate, secondDate) > 0 ? true : false;
	}

	/**
	 * @param firstDate
	 * @param secondDate
	 * @Description:�Ƚ�����ʱ����Ƿ����
	 * @ReturnType boolean
	 * @author:
	 * @Created 2012 2012-9-20����08:37:30
	 */
	public static boolean isEqual(Date firstDate, Date secondDate) {

		return compare(firstDate, secondDate) == 0 ? true : false;
	}

	/**
	 * @param firstDate
	 * @param secondDate
	 * @Description:�Ƚ�����ʱ��� ���secondDate��ʾ��ʱ����ڴ� firstDate ��ʾ��ʱ�䣬�򷵻� 0 ֵ�� �����
	 * firstDate ��ʱ���ڲ���<secondDate>��ʾ��ʱ��֮ǰ���򷵻�С�� 0 ��ֵ�� �����
	 * firstDate ��ʱ���ڲ���<secondDate>��ʾ��ʱ��֮���򷵻ش��� 0 ��ֵ
	 * @ReturnType int
	 * @author:
	 * @Created 2012 2012-9-20����08:34:33
	 */
	public static int compare(Date firstDate, Date secondDate) {

		Calendar firstCalendar = null;
		/** ʹ�ø����� Date ���ô� Calendar ��ʱ�䡣 **/
		if (firstDate != null) {
			firstCalendar = Calendar.getInstance();
			firstCalendar.setTime(firstDate);
		}

		Calendar secondCalendar = null;
		/** ʹ�ø����� Date ���ô� Calendar ��ʱ�䡣 **/
		if (firstDate != null) {
			secondCalendar = Calendar.getInstance();
			secondCalendar.setTime(secondDate);
		}

		try {
			/**
			 * �Ƚ����� Calendar �����ʾ��ʱ��ֵ������Ԫ�����ڵĺ���ƫ�������� ���������ʾ��ʱ����ڴ� Calendar
			 * ��ʾ��ʱ�䣬�򷵻� 0 ֵ�� ����� Calendar ��ʱ���ڲ�����ʾ��ʱ��֮ǰ���򷵻�С�� 0 ��ֵ�� �����
			 * Calendar ��ʱ���ڲ�����ʾ��ʱ��֮���򷵻ش��� 0 ��ֵ
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
	 * @Created 2012 2012-9-20����08:42:06
	 */
	public static int betweenTowDate(Date startDate, Date endDate, Date inDate) {

		/**
		 * �ж�<endDate>ʱ����Ƿ���<startDate>ʱ���֮ǰ
		 * ���Ϊ���ʾ<endDate>ʱ�����<startDate>ʱ���֮ǰ���׳��쳣 ������ʱ���ڿ�ʼʱ��֮ǰ�ǲ�������
		 */
		if (isBefore(endDate, startDate)) {
			throw new IllegalArgumentException(
					"endDate can not before startDate!");
		}

		/**
		 * �Ƚ�����ʱ���<inDate>��<startDate> ���inDate��ʾ��ʱ����ڴ� startDate ��ʾ��ʱ�䣬�򷵻� 0 ֵ��
		 * ����� inDate ��ʱ���ڲ���<startDate>��ʾ��ʱ��֮ǰ���򷵻�С�� 0 ��ֵ�� ����� inDate
		 * ��ʱ���ڲ���<startDate>��ʾ��ʱ��֮���򷵻ش��� 0 ��ֵ
		 */
		int sflag = compare(inDate, startDate);

		/**
		 * �Ƚ�����ʱ���<inDate>��<endDate> ���inDate��ʾ��ʱ����ڴ� endDate ��ʾ��ʱ�䣬�򷵻� 0 ֵ�� �����
		 * inDate ��ʱ���ڲ���<endDate>��ʾ��ʱ��֮ǰ���򷵻�С�� 0 ��ֵ�� ����� inDate
		 * ��ʱ���ڲ���<endDate>��ʾ��ʱ��֮���򷵻ش��� 0 ��ֵ
		 */
		int eflag = compare(inDate, endDate);

		int flag = 0;

		/** ����� inDate ��ʱ���ڲ���<startDate>��ʾ��ʱ��֮ǰ���򷵻�С�� 0 ��ֵ **/
		if (sflag < 0) {
			/** һ����������һ���������� **/
			flag = DateUtils.BEFORE_START_DATE;
		} else if (sflag == 0) {
			/** ���inDate��ʾ��ʱ����ڴ� startDate ��ʾ��ʱ�䣬�򷵻� 0 ֵ�� **/
			/** ���inDate��ʾ��ʱ����ڴ� endDate ��ʾ��ʱ�䣬�򷵻� 0 ֵ�� **/
			if (eflag == 0) {
				/** ����������� **/
				flag = DateUtils.TREE_DATE_EQUAL;
			} else {
				/** һ�����ڵ���һ����������Ŀ�ʼ���� **/
				flag = DateUtils.EQUAL_START_DATE;
			}
		} else if (sflag > 0 && eflag < 0) {
			/**
			 * ����-inDate ��ʱ���ڲ���<startDate>��ʾ��ʱ��֮�󣬺� inDate
			 * ��ʱ���ڲ���<endDate>��ʾ��ʱ��֮ǰ
			 **/
			/** һ��������һ����������֮�� **/
			flag = DateUtils.BETWEEN_TOW_DATE;
		} else if (eflag == 0) {
			/** ���inDate��ʾ��ʱ����ڴ� endDate ��ʾ��ʱ�� **/
			/** һ�����ڵ���һ����������Ľ������� **/
			flag = DateUtils.EQUAL_END_DATE;
		} else if (eflag > 0) {
			/** ����inDate ��ʱ���ڲ���<endDate>��ʾ��ʱ��֮�� **/
			/** һ����������һ���������� **/
			flag = DateUtils.AFTER_END_DATE;
		}
		return flag;
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 * @Description:�ֱ��жϵ�ǰ��������һ�������������������Ƚ� ��1�� һ����������һ���������� ��2�������������
	 * ��3��һ�����ڵ���һ����������Ŀ�ʼ����
	 * ��4��һ��������һ����������֮��
	 * ��5��һ�����ڵ���һ����������Ľ������� ��6��һ����������һ����������
	 * @ReturnType int
	 * @author:
	 * @Created 2012 2012-9-20����09:00:31
	 */
	public static int betweenTowDate(Date startDate, Date endDate) {
		return betweenTowDate(startDate, endDate, new Date());
	}

	/**
	 * ��Date��ת��ΪString��
	 *
	 * @param dtDate     Date Ҫת����ʱ��
	 * @param strPattern String ת���ĸ�ʽ
	 * @return String ת�����ʱ�� ˵������ʽ����Ϊ yyyy-MM-dd HH:mm:ss�ȣ���������ϣ��� yyyy��MM��dd��
	 * yyyy������ MM������ dd������ HH hh kk����Сʱ mm�������� ss�������� format
	 */
	public static String dateToString(Date dtDate, String strPattern) {
		SimpleDateFormat lsdfDate = new SimpleDateFormat(strPattern);
		return lsdfDate.format(dtDate);
	}

	/**
	 * @param dateStr �����ַ���
	 * @param pattern ���ڸ�ʽ
	 * @Description:��������Ӧ��ʽ���ַ���ת��Ϊ���� <��ʽ�Զ���>
	 * @ReturnType Date �����ִ�Ϊ�ջ��߲��������ڸ�ʽʱ����null
	 * @author:
	 * @Created 2012 2012-9-20����09:06:00
	 */
	public static Date getDate(String dateStr, String pattern) {

		return getDate(dateStr, pattern, null);
	}

	/**
	 * ��������Ӧ��ʽ���ַ���ת��Ϊ���� ��ʽ�Զ���
	 *
	 * @param dateStr     �����ַ���
	 * @param pattern     ���ڸ�ʽ
	 * @param defaultDate Ĭ������
	 * @return �����ִ�Ϊ�ջ��߲��������ڸ�ʽʱ����defaultDate
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
	 * @param date    ��Ҫ��ʽ�����ڲ���
	 * @param pattern ���ڸ�ʽ
	 * @Description:��ĳ������ת����ָ����ʽ���������� <��ȡ��Ӧ��ʽ��Date>
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-20����09:08:24
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
	 * @return �����ִ�Ϊ�ջ��߲��������ڸ�ʽʱ����null
	 * @Description:��������Ӧ��ʽ���ַ���ת��Ϊ���� ��ʽ yyyy-MM-dd
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-20����09:04:28
	 */
	public static Date getDate(String dateStr) {
		return getDate(dateStr, DateUtils.NORMAL_DATE_PATTERN);
	}

	/**
	 * @Description:����ǰ����ת�����ַ��� ��ʽ yyyy-MM-dd
	 * @ReturnType String
	 * @author:
	 * @Created 2012 2012-9-19����05:54:42
	 */
	public static String datetoStr() {
		SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_PATTERN);
		String curDate = sdf.format(new Date());
		return curDate;
	}

	/**
	 * @Description: ��ȡ��������, ���ڸ�ʽΪYYYYMMDD
	 * @ReturnType String
	 * @author:
	 * @Created 2012 2012-9-20����09:17:19
	 */
	public static String getIntraday() {
		Calendar date = Calendar.getInstance();// ��õ�ǰ����
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH) + 1;
		int day = date.get(Calendar.DAY_OF_MONTH);
		String updateFileDate = new String(new Integer(year).toString()
				+ new Integer(month).toString() + new Integer(day).toString());
		return updateFileDate;
	}

	/**
	 * @return
	 * @Description:��ȡ�������ڣ����ڸ�ʽΪYYYY-MM-DD HH:mm:ss
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-20����09:58:36
	 */
	public static Date getCurrentlyDate() {
		Date currentDate = new Date();
		return getFormatDate(currentDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @return
	 * @Description:��ȡ�������ڣ����ڸ�ʽΪYYYY-MM-DD
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-20����09:58:36
	 */
	public static String getCurrentlyDateStr() {
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(currentDate);
		return dateStr;
	}

	/**
	 * @return
	 * @Description:��ȡ��������
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-20����09:58:36
	 */
	public static Timestamp getCurrentlyTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * @return
	 * @Description: ��ȡʱ���Сʱ����24Сʱ�ƣ�
	 * @ReturnType int
	 * @author:
	 * @Created 2012 2012-9-25����08:33:44
	 */
	public static int getTime24Hour() {

		Calendar calender = Calendar.getInstance();
		calender.setTime(getCurrentlyDate());
		return calender.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * @param date
	 * @return
	 * @Description:��ȡǰһ��
	 * @ReturnType Date
	 * @author:
	 * @Created 2012 2012-9-25����08:32:00
	 */
	public static Date getBeforeDate(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DATE, -1);
		return calender.getTime();
	}

	/**
	 * @Description:ȡ�õ�ǰ���ڵ���һ��
	 * @ReturnType String
	 * @author:
	 * @Created 2012 2012-9-20����09:12:06
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
	 * @Description:ȡ��ǰ���ں�ĵڶ���
	 * @ReturnType String
	 * @author:
	 * @Created 2012 2012-9-20����10:37:47
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
	 * @Description:��ȡ��ǰ�����ڵ�ǰ�·��еĵڼ�������
	 * @ReturnType int
	 * @author:
	 * @Created 2012 2012-10-29����11:45:13
	 */
	public static int getTimeWeekOfMonth(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		return calender.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * ������������ȡʱ���ڵ�ǰ���ڵĵڼ���
	 *
	 * @param date
	 * @return ����������, ����: 1��ʾ����һ, ...7��ʾ������
	 * <p>
	 * <p>
	 * �޸���ʷ ��(�޸��ˣ��޸�ʱ�䣬�޸�ԭ��/����)
	 * </p>
	 * @author <p>
	 * �������� ��2012 2012-10-29����11:45:52
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
	 * ������������ȡʱ���ڵ�ǰ���ڵĵڼ���
	 *
	 * @param date
	 * @return ����������, ����: 7��ʾ������..1��ʾ������
	 * <p>
	 * <p>
	 * �޸���ʷ ��(�޸��ˣ��޸�ʱ�䣬�޸�ԭ��/����)
	 * </p>
	 * @author <p>
	 * �������� ��2012 2012-10-29����11:45:52
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
	 * @Description:ĳ��ʱ���뵱ǰʱ��������
	 * @ReturnType long
	 * @author:
	 * @Created 2012 2012-12-12����01:00:53
	 */
	public static long getAppointTimeDifference(Date startDate, Date endDate) {
		long l = endDate.getTime() - startDate.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * @return
	 * @Description:��ȡ�������ڣ����ڸ�ʽΪMM��DD��
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
	 * @param days:ָ������
	 * @Description:��ȡ��������ǰ��ָ��������������,�������10��1��,������7,������:["10��1��","10��2��","10��3��","10��4��","10��5��","10��6��","10��7��"]
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
	 * @param days:ָ������
	 * @Description:��ȡ��������ǰ��ָ��������������,�������1007,������7,������:["1001","1002","1003","1004","1005","1006","1007"]
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
	 * ��String�͸�ʽ��,������Ҫ��2011-11-11��ʽ����2011��11��11��,��StringPattern("2011-11-11","yyyy-MM-dd","yyyy��MM��dd��").
	 *
	 * @param date       String ��Ҫ��ʽ��������
	 * @param oldPattern String ��Ҫ��ʽ�������ڵ����и�ʽ
	 * @param newPattern String ��Ҫ��ʽ����ʲô��ʽ
	 * @return String
	 */
	public static String convertStringDateFormat(String date, String oldPattern, String newPattern) {
		if (date == null || oldPattern == null || newPattern == null) {
			return "";
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern);        // ʵ����ģ�����
		SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern);        // ʵ����ģ�����
		Date d = null;
		try {
			d = sdf1.parse(date);   // ���������ַ����е�������ȡ����
		} catch (Exception e) {            // ����ṩ���ַ�����ʽ�д���������쳣����
			e.printStackTrace();       // ��ӡ�쳣��Ϣ
		}
		return sdf2.format(d);
	}

}