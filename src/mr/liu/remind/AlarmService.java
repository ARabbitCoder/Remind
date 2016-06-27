package mr.liu.remind;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import mr.liu.beans.Remind;
import mr.liu.dao.DBRemindHelper;
import mr.liu.remind.AlarmActivity;
import mr.liu.utils.AlarmUtils;
import mr.liu.utils.TimeUtils;

public class AlarmService extends IntentService {
	private static final int DateType = 0;
	private static final int WeekType = 1;
	private static final int DayType = 2;
	private AlarmManager mAlarmMgr;
	private DBRemindHelper dbhelper;
	private boolean flag;

	public AlarmService(String name) {
		super("AlarmService");
	}

	public AlarmService() {
		super("AlarmService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		int addid = intent.getIntExtra("remindid", -1);
		int cancleid = intent.getIntExtra("cancleid", -1);
		int repeatid = intent.getIntExtra("repeatid", -1);
		if (addid >= 0) {
			startRequestAlarm(addid);
			return;
		} else if (cancleid >= 0) {
			cancelRequestAlarm(cancleid);
			return;
		} else if (repeatid >= 0) {
			startRepeatAlarm(repeatid);
		}
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mAlarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		dbhelper = new DBRemindHelper(this);
		dbhelper = dbhelper.openDB();
		flag = AlarmUtils.sdkVersion();
		System.out.println("AlarmService is create");
	}

	private void startRepeatAlarm(int requestCode) {
		cancelRequestAlarm(requestCode);
		Remind re = dbhelper.getById(requestCode);
		if (flag) {
			mAlarmMgr.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + re.getRepeat(),
					getOperationIntent(requestCode));
		} else {
			mAlarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + re.getRepeat(),
					getOperationIntent(requestCode));
		}
		// dbhelper.close();
		System.out.println("repeat alarm service");
	}

	private void startRequestAlarm(int requestCode) {
		cancelRequestAlarm(requestCode);
		Remind re = dbhelper.getById(requestCode);
		switch (re.getType()) {
		case DateType:
			long dvalue = TimeUtils.DateDValue(re.getDate());
			if (dvalue >= 0) {
				if (flag) {
					mAlarmMgr.setExact(AlarmManager.RTC_WAKEUP, dvalue, getOperationIntent(requestCode));
				} else {
					mAlarmMgr.set(AlarmManager.RTC_WAKEUP, dvalue, getOperationIntent(requestCode));
				}
			}
			break;
		case WeekType:
			String[] date = re.getDate().split(":");
			long dvalue1 = TimeUtils.WeekDValue(Integer.parseInt(date[0].trim()), Integer.parseInt(date[1].trim()),
					Integer.parseInt(date[2].trim()));
			if (flag) {
				mAlarmMgr.setExact(AlarmManager.RTC_WAKEUP, dvalue1, getOperationIntent(requestCode));
			} else {
				mAlarmMgr.set(AlarmManager.RTC_WAKEUP, dvalue1, getOperationIntent(requestCode));
			}
			System.out.println("WeekType");
			break;
		case DayType:
			String[] time = re.getDate().split(":");
			long dvalue2 = TimeUtils.DayDValue(Integer.parseInt(time[0].trim()), Integer.parseInt(time[1].trim()));
			if (flag) {
				mAlarmMgr.setExact(AlarmManager.RTC_WAKEUP, dvalue2, getOperationIntent(requestCode));
			} else {
				mAlarmMgr.set(AlarmManager.RTC_WAKEUP, dvalue2, getOperationIntent(requestCode));
			}
			break;
		}
		// dbhelper.close();
		System.out.println("alarmservice is start");
	}

	private void cancelRequestAlarm(int requestCode) {
		Remind re = dbhelper.getById(requestCode);
		mAlarmMgr.cancel(getOperationIntent(requestCode));
		System.out.println("RequsetAlarm is cancled");
	}

	private PendingIntent getOperationIntent(int requestCode) {
		Intent intent = new Intent(this, AlarmActivity.class);
		intent.putExtra("getremindid", requestCode);
		PendingIntent operation = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
		return operation;
	}
}
