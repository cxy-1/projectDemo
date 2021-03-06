package Util.Arithmetic.time_date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zhuwz
 */
public class DateUtil_kunshan {
	public final static SimpleDateFormat SF_TIME = new SimpleDateFormat("HH:mm:ss");
	private final static SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat SFT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	private final static SimpleDateFormat SF_DAY = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat SF_HOUR_CHINA = new SimpleDateFormat("yyyy年MM月dd日");
	private final static SimpleDateFormat SF_DAY_NUM = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat EXLS_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd#HH:mm:ss");
	final static public long SECOND = 1000;
	final static public long MINUTE = 60 * SECOND;
	final static public long HOUR = 60 * MINUTE;
	final static public long DAY = 24 * HOUR;
	final static public long WEEK = 7 * DAY;

	/**
	 * 判断两个时间值是同一天
	 */
	static public boolean isSameDay(long date1, long date2) {
		return compareDate(new Date(date2), new Date(date1)) == 0;
	}

	/**
	 * 判断两个日期是否是同一周
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	synchronized static public boolean isSameWeek(long date1, long date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setFirstDayOfWeek(Calendar.MONDAY);
		c2.setFirstDayOfWeek(Calendar.MONDAY);
		c1.setTimeInMillis(date1);
		c2.setTimeInMillis(date2);
		return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.WEEK_OF_YEAR) == c2.get(Calendar.WEEK_OF_YEAR);
	}

	static public boolean isSameDay(Date date1, Date date2) {
		return compareDate(date1, date2) == 0;
	}

	/**
	 * 返回date1与date2相差的天数，若date1在date2之前，返回值<0
	 */
	static public long difference_Day(long date1, long date2) {
		long day1 = date1 / DAY;
		long day2 = date2 / DAY;
		return day1 - day2;
	}

	/**
	 * date2比date1多的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1,Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		//同一年
		if(year1 != year2)
		{
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				//闰年
				if(i%4==0 && i%100!=0 || i%400==0)
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2-day1) ;
		}
		else    //不同年
		{
			System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2-day1;
		}
	}



	/**
	 * 返回date1与date2相差的小时，若date1在date2之前，返回值<0
	 *
	 * @param date1 新
	 * @param date2 老
	 * @return
	 */
	static public long difference_Hour(long date1, long date2) {
		long hour1 = date1 / HOUR;
		long hour2 = date2 / HOUR;
		return hour1 - hour2;
	}

	/**
	 * 今天是几号
	 *
	 * @return
	 */
	static public int today_date() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}


	/**
	 * 返回date1与date2相差的分钟，若date1在date2之前，返回值<0
	 */
	static public long difference_Minute(long date1, long date2) {
		long min1 = date1 / MINUTE;
		long min2 = date2 / MINUTE;
		return min1 - min2;
	}

	/**
	 * 返回date1与date2相差的秒数，若date1在date2之前，返回值<0
	 */
	static public long difference_Second(long date1, long date2) {
		long sec1 = date1 / SECOND;
		long sec2 = date2 / SECOND;
		return sec1 - sec2;
	}

	/**
	 * 获取时间所在当天0时的时间
	 */
	static public long getDay(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time / MINUTE * MINUTE);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		return c.getTimeInMillis();
	}

	/**
	 * 对比天数
	 *
	 * @param da1 老
	 * @param da2 新
	 * @return
	 */
	public static int compareDate1(Date da1, Date da2) {
		int n = -1;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(da1);
			c2.setTime(da2);
		} catch (Exception e3) {
		}
		// 循环对比，直到相等，n 就是所要的结果
		while (!c1.after(c2)) {
			n++;
			// 比较天数，日期+1
			c1.add(Calendar.DATE, 1);
		}
		return n;
	}

	/**
	 * @param fDate 新
	 * @param oDate 老
	 * @return
	 */
	public static int compareDate(Date fDate, Date oDate) {
		return compareDate(fDate.getTime(), oDate.getTime());
	}

	/**
	 * @param fDate 新
	 * @param oDate 老
	 * @return
	 */
	public static int compareDate(long fDate, long oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTimeInMillis(fDate);
		int year1 = aCalendar.get(Calendar.YEAR);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		int yearmax = aCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
		aCalendar.setTimeInMillis(oDate);
		int year2 = aCalendar.get(Calendar.YEAR);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		int tmp = year1 - year2;
		switch (tmp) {
			case 0:
				return day1 - day2;
			case 1:
				return yearmax - day2 + day1;
			case -1:
				return -(yearmax - day1 + day2);
			default:
				return -1;
		}
	}

	public static int compareHour(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.HOUR_OF_DAY);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.HOUR_OF_DAY);
		return day1 - day2;
	}

	public static int compareMinute(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.MINUTE);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.MINUTE);
		return day1 - day2;
	}

	/**
	 * 分析时间
	 *
	 * @param format
	 * @param time
	 * @return
	 */
	public static Calendar AnalyzeTimeString(SimpleDateFormat format, String time) {
		try {
			Date date = format.parse(time);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分析时间
	 *
	 * @param format
	 * @param time
	 * @return
	 */
	public synchronized static Date AnalyzeTimeStringToDate(SimpleDateFormat format, String time) {
		try {
			Date date = format.parse(time);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分析时间
	 *
	 * @param format
	 * @param time
	 * @return
	 */
	public static String AnalyzeTimeToString(SimpleDateFormat format, long time) {
		try {
			synchronized (format) {
				Date date = new Date(time);
				return format.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将当前时间以HH:mm:s格式字符串返回
	 *
	 * @return
	 */
	public static String getCurrentUTFTime() {
		synchronized (SF_TIME) {
			return SF_TIME.format(new Date());
		}
	}

	public static String formatSF(Date date) {
		synchronized (SF) {
			return SF.format(date);
		}
	}

	/**
	 * 将long型数据转化成为格式为yyyy-MM-dd的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getUTFDay(long date) {
		return formatSF_DAY(new Date(date));
	}

	/**
	 * 将yyyy-MM-dd的字符串转为long
	 *
	 * @return
	 */
	public static Date parseY(String timestr) {
		Date date;
		try {
			synchronized (SF_DAY) {
				date = SF_DAY.parse(timestr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}

	/**
	 * 将Date型数据转化成为格式为yyyy-MM-dd的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String formatSF_DAY(Date date) {
		synchronized (SF_DAY) {
			return SF_DAY.format(date);
		}
	}

	/**
	 * 将Date型数据转化成为格式为yyyy-MM-dd的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String format_DAY(Date date, String str) {
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(date);
	}

	/**
	 * 将日期对象转化成为yyyy-MM-dd HH:mm:ss格式字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getTime(Date date) {
		synchronized (SF) {
			return SF.format(date);
		}
	}

	/**
	 * 将当前时间转化成为yyyy-MM-dd HH:mm:ss格式字符串
	 *
	 * @return
	 */
	public static String getNowTime() {
		return getTime(new Date());
	}

	public static String getNowTimestamp() {
		return getTime(new Date(), SFT);
	}

	/**
	 * 将日期对象转化成为yyyy-MM-dd HH:mm:ss格式字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getTime(Date date, SimpleDateFormat sdf) {
		synchronized (sdf) {
			return sdf.format(date);
		}
	}

	/**
	 * 将格式为yyyy-MM-dd HH:mm:ss的字符串转化为Date对象
	 *
	 * @param timestr
	 * @return
	 */
	public static Date parsSF(String timestr) {
		Date date;
		try {
			synchronized (SF) {
				date = SF.parse(timestr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}


	public static String formatSF_HOUR_CHINA(Date date) {
		synchronized (SF_HOUR_CHINA) {
			return SF_HOUR_CHINA.format(date);
		}
	}

	public static String getNowInNum() {
		synchronized (SF_DAY_NUM) {
			return SF_DAY_NUM.format(new Date());
		}
	}

	public static Date parseSF_HOUR_CHINA(String str) {
		Date date;
		synchronized (SF_HOUR_CHINA) {
			try {
				date = SF_HOUR_CHINA.parse(str);
			} catch (Exception e) {
				return null;
			}
		}
		return date;
	}

	public static int getDaysToNow(long time) {
		long now = System.currentTimeMillis();
		if (isSameDay(now, time)) {
			return 0;
		}
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(now);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		long nowZear = c.getTimeInMillis();
		long daySecs = 24 * 60 * 60 * 1000;
		return (int) ((nowZear - time) / daySecs) + 1;
	}

	private static long todayStartTime = -1;

	/**
	 * 得到一天开始的时间
	 *
	 * @return
	 */
	public static long getTodayBeginTime() {
		long now = System.currentTimeMillis();
		// 没有初始化过 或者 超过了1天那么就重新计算
		if (todayStartTime == -1L || now - todayStartTime > DAY) {
			// 每1分钟更新todayStartTime字段
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			todayStartTime = calendar.getTimeInMillis();
		}
		return todayStartTime;
	}

	public static long getNextTime_Hour(int delayhour) {
		Date now = new Date(System.currentTimeMillis());
		@SuppressWarnings("deprecation")
		int nowhour = now.getHours();
		int off = delayhour - nowhour % delayhour;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.HOUR_OF_DAY, off);
		return c.getTimeInMillis();
	}

	/**
	 * 得到今天开始到现在的时间
	 *
	 * @return
	 */
	public static long getCurTimeOfToday() {
		long cur = System.currentTimeMillis() - getTodayBeginTime();
		return cur;
	}

	/**
	 * 获取过去7天时间数组
	 */
	public static ArrayList<String> pastDaysList(int intervals) {
		ArrayList<String> pastDaysList = new ArrayList<>();
		for (int i = 0; i < intervals; i++) {
			pastDaysList.add(getPastDate(i));
		}
		return pastDaysList;
	}

	/**
	 * 获取过去第几天的日期
	 *
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取未来 第 past 天的日期
	 *
	 * @param past
	 * @return
	 */
	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取指定日期， 未来 第 past 天的日期
	 *
	 * @param past
	 * @return
	 */
	public static Date getFetureDate(Date date, int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		return today;
	}

	/**
	 * 获取指定日期， 未来 第 past 月的日期
	 *
	 * @param past
	 * @return
	 */
	public static Date getFetureDateForMon(Date date, int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + past);
		Date today = calendar.getTime();
		return today;
	}


	/**
	 * 获取当前系统时间最近12月的年月（含当月）
	 */
	public static String[] getLatest12Month() {
		int month = 12;
		String[] latest12Months = new String[12];
		Calendar cal = Calendar.getInstance();
		//要先+1,才能把本月的算进去
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		for (int i = 0; i < month; i++) {
			//逐次往前推1个月
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
			latest12Months[11 - i] = cal.get(Calendar.YEAR) + "-" + fillZero(cal.get(Calendar.MONTH) + 1);
		}
		return latest12Months;
	}

	/**
	 * 格式化月份
	 *
	 * @param i
	 * @return
	 */
	public static String fillZero(int i) {
		String month = "";
		int size = 10;
		if (i < size) {
			month = "0" + i;
		} else {
			month = String.valueOf(i);
		}
		return month;
	}

	/**
	 * 获取时间的月份
	 *
	 * @param date
	 * @return
	 */
	public static Integer getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取时间的年份
	 *
	 * @param date
	 * @return
	 */
	public static Integer getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取时间的年份 月份
	 *
	 * @param date
	 * @return
	 */
	public static String getYearAndMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1);
	}

	/**
	 * 获取时间的年份 月份
	 *
	 * @param date
	 * @return
	 */
	public static String getYearAndMonth2(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月";
	}


	public static List<String> get(Integer postNumMon) {
		List<String> monList = new ArrayList<String>();
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		monList.add(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1));
		for (int i = 0; i < postNumMon - 1; i++) {
			cal.add(Calendar.MONTH, -1);
			monList.add(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1));
		}
		return monList;
	}


	public static List<String> getYear(Integer postNumMon) {
		List<String> yearList = new ArrayList<String>();
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		yearList.add(String.valueOf(cal.get(Calendar.YEAR)));
		for (int i = 0; i < postNumMon - 1; i++) {
			cal.add(Calendar.YEAR, -1);
			yearList.add(String.valueOf(cal.get(Calendar.YEAR)));
		}
		return yearList;
	}

	/**
	 * 判断两个日期年月大小
	 *
	 * @param d1
	 * @param d2
	 */
	public static Integer getComperDate(Date d1, Date d2) {
		Integer flag = 0;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		Integer num1 = cal1.get(Calendar.YEAR) + cal1.get(Calendar.MONTH);
		Integer num2 = cal2.get(Calendar.YEAR) + cal2.get(Calendar.MONTH);
		if (num1 < num2) {
			flag = 0;
		} else {
			flag = 1;
		}
		return flag;
	}

	/**
	 * 判断两个日期年月大小
	 *
	 * @param d1
	 * @param d2
	 */
	public static Integer getComperDate2(Date d1, Date d2) {
		Integer flag = 0;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		Integer num1 = cal1.get(Calendar.YEAR) + cal1.get(Calendar.MONTH);
		Integer num2 = cal2.get(Calendar.YEAR) + cal2.get(Calendar.MONTH);
		if (num1 < num2) {
			flag = 0;
		} else if (num1.equals(num2)) {
			flag = 1;
		} else {
			flag = 2;
		}
		return flag;
	}

	public static Date firstday(Date date) {
		// 获取当月第一天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date lastday(Date date) {
		// 获取当月最后一天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		//将分钟至0
		cal.set(Calendar.MINUTE, 0);
		//将秒至0
		cal.set(Calendar.SECOND, 0);
		//将毫秒至0
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}


//    static public long difference_Day(long date1, long date2) {
//        long day1 = date1 / DAY;
//        long day2 = date2 / DAY;
//        return day1 - day2;
//    }



//    public static void main(String[] args) {
//        Date d = new Date();
//        Calendar calendar =Calendar.getInstance();
//        calendar.set(2019,12,12,02,34,34);
//        Date date1=calendar.getTime();
//
//        calendar.set(2019,12,16,06,34,34);
//        Date date2=calendar.getTime();
//
//
//        System.out.println(difference_Day(date1.getTime(),date2.getTime()));
//
////        System.out.println(DateUtil.firstday(date));
////        System.out.println(DateUtil.lastday(date));
//
////        System.out.println(new Date().getTime());
//    }

}
