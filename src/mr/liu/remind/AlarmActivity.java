package mr.liu.remind;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
	private int requestCode;
	private boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		tv = (TextView) findViewById(R.id.alarmtext);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alarmshake);
		alarm = (ImageView) findViewById(R.id.remindimage);
		alarm.setAnimation(anim);
		alarm.startAnimation(anim);
		System.out.println(System.currentTimeMillis());
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
		if(re!=null) {
			dbhelper.setDone(re);
			CancleAlarmService();
		}
		finish();
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
		stopAlarm();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (!flag) {
			LaterRemind();
		}
		dbhelper.close();
		super.onDestroy();
	}
}
