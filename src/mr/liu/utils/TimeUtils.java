package mr.liu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.res.Resources;
import mr.liu.remind.R;

public class TimeUtils {
	public static final long offsetTime = 21600000;

	public static String FormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static Date FormatString(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static long DateDValue(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = sdf.parse(date);
			Calendar c = Calendar.getInstance();
			Calendar c1 = Calendar.getInstance();
			c.setTime(date1);
			if (c.before(c1)) {
				return -1;
			} else {
				return c.getTimeInMillis() + offsetTime;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public static long WeekDValue(int tWeek, int hour, int minute) {
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		int cWeek = c.get(Calendar.DAY_OF_WEEK);
		int dvalue = tWeek - cWeek >= 0 ? (tWeek - cWeek) : (tWeek - cWeek + 7);
		if(dvalue==0) {
			c1.set(Calendar.HOUR_OF_DAY, hour);
			c1.set(Calendar.MINUTE, minute);
			if(c1.after(c)) {
				int cDay = c.get(Calendar.DAY_OF_MONTH);
				c.set(Calendar.DAY_OF_MONTH, cDay + dvalue);
				c.set(Calendar.HOUR_OF_DAY, hour);
				c.set(Calendar.MINUTE, minute);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				return c.getTimeInMillis();
			}else {
				int cDay = c.get(Calendar.DAY_OF_MONTH);
				c.set(Calendar.DAY_OF_MONTH, cDay + 7);
				c.set(Calendar.HOUR_OF_DAY, hour);
				c.set(Calendar.MINUTE, minute);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				return c.getTimeInMillis();
			}
		}else {
			int cDay = c.get(Calendar.DAY_OF_MONTH);
			c.set(Calendar.DAY_OF_MONTH, cDay + dvalue);
			c.set(Calendar.HOUR_OF_DAY, hour);
			c.set(Calendar.MINUTE, minute);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			return c.getTimeInMillis();
		}
	}
	public static long DayDValue(int hour,int minute) {
		System.out.println(hour+":"+minute);
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.HOUR_OF_DAY, hour);
		c1.set(Calendar.MINUTE, minute);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		if(c1.after(c)) {
			System.out.println("current is before"+c1.getTimeInMillis());
			return c1.getTimeInMillis();
		}else {
			int cday = c.get(Calendar.DAY_OF_MONTH);
			c.set(Calendar.DAY_OF_MONTH, cday+1);
			return c.getTimeInMillis();
		}
	}
	public static String formatHourNum(int hour,int minute) {
		String str = "";
		if (hour < 10) {
			str =  "0" + hour + " : " ;
			if(minute<10) {
				str = str+"0"+minute;
			}else {
				str = str+minute;
			}
		} else {
			str =  hour + " : " ;
			if(minute<10) {
				str = str+"0"+minute;
			}else {
				str = str+minute;
			}
		}
		return str;
	}
	public static boolean Before(String date) {
		Date d1 = FormatString(date);
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		c.setTime(d1);
		return c.before(c1);
	}
}
