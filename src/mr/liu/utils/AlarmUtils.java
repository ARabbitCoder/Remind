package mr.liu.utils;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.os.Build;

public class AlarmUtils {
	private Context context;
	private AlarmManager manager;
	
	public AlarmUtils(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		manager =   (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
	}
	
	public void DateTask(PendingIntent pi,int repeattime,String date) {
		Calendar c = Calendar.getInstance();
		Date da = TimeUtils.FormatString(date);
		if(da!=null) {
//			c.setTime(da);
//			if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT) {
//				manager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
//			}else {
//				manager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 5000, pi);
//			}
			if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT) {
				manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+3000, pi);
			}else {
				manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+3000, 5000, pi);
			}
			
		}
	}
	
}
