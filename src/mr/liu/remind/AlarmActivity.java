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
import android.widget.Button;
import android.widget.TextView;
import mr.liu.beans.Remind;
import mr.liu.dao.DBRemindHelper;

public class AlarmActivity extends Activity {
	private MediaPlayer mPlayer;
	private TextView tv;
	private DBRemindHelper dbhelper;
	private Remind re;
	private int requestCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		tv = (TextView) findViewById(R.id.alarmtext);
		dbhelper = new DBRemindHelper(this);
		dbhelper = dbhelper.openDB();
		Intent intent = getIntent();
		requestCode = intent.getIntExtra("getremindid", -1);
		re = dbhelper.getById(requestCode);
		dbhelper.close();
		if (re != null) {
			tv.setText(re.getDesc());
			startAlarm();
			initListenser();
		}
		dbhelper.close();
	}

	private void initListenser() {
	}

	private void CancleRemind() {
		stopAlarm();
		finish();
	}

	private void LaterRemind() {
		stopAlarm();
		Intent intent = new Intent(this,AlarmService.class);
		intent.putExtra("remindid", requestCode);
		startService(intent);
		System.out.println("start service");
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
}
