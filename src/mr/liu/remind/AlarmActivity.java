package mr.liu.remind;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import mr.liu.beans.Remind;
import mr.liu.dao.DBRemindHelper;

public class AlarmActivity extends Activity {
	private MediaPlayer mPlayer;
	private TextView tv;
	private ImageView alarm;
	private Button b1;
	private Button b2;
	private DBRemindHelper dbhelper;
	private Remind re;
	private WakeLock mWakelock;
	private int requestCode;
	private boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Window win = getWindow();
		 win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
		 | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		 win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
		 | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		setContentView(R.layout.activity_alarm);
		PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
		mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.SCREEN_DIM_WAKE_LOCK, "SimpleTimer");
		mWakelock.acquire();
		tv = (TextView) findViewById(R.id.alarmtext);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alarmshake);
		alarm = (ImageView) findViewById(R.id.remindimage);
		alarm.setAnimation(anim);
		alarm.startAnimation(anim);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		dbhelper = new DBRemindHelper(this);
		dbhelper = dbhelper.openDB();
		Intent intent = getIntent();
		requestCode = intent.getIntExtra("getremindid", -1);
		re = dbhelper.getById(requestCode);
		if (re != null) {
			tv.setText(re.getDesc());
			startAlarm();
			initListenser();
		}
	}

	private void initListenser() {
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LaterRemind();
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CancleRemind();
			}
		});

	}

	private void LaterRemind() {
		stopAlarm();
		RepeatAlarmService();
		finish();
	}

	private void CancleRemind() {
		stopAlarm();
		flag = true;
		if (re != null) {
			if (re.getType() != 0) {
				WeekAlarmService();
			} else {
				CancleAlarmService();
			}
			dbhelper.setDone(re);
		}
		finish();
	}

	private void WeekAlarmService() {
		Intent intent = new Intent(this, AlarmService.class);
		intent.putExtra("remindid", requestCode);
		startService(intent);
	}

	private void CancleAlarmService() {
		Intent intent = new Intent(this, AlarmService.class);
		intent.putExtra("cancleid", requestCode);
		startService(intent);
	}

	private void RepeatAlarmService() {
		Intent intent = new Intent(this, AlarmService.class);
		intent.putExtra("repeatid", requestCode);
		startService(intent);
	}

	private void startAlarm() {
		mPlayer = MediaPlayer.create(this, getSystemDefaultRing());
		mPlayer.setLooping(true);
		try {
			mPlayer.prepare();
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPlayer.start();
		System.out.println("alarm play");
	}

	private void stopAlarm() {
		mPlayer.stop();
	}

	private Uri getSystemDefaultRing() {
		return RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		System.out.println("-----onStart-----");
		super.onStart();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		stopAlarm();
		super.onStop();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		System.out.println("-----onStart-----");
		super.onRestart();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopAlarm();
		mPlayer.release();
		mWakelock.release();
		if (!flag) {
			LaterRemind();
		}
		dbhelper.close();
		super.onDestroy();
	}
}
