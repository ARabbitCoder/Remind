package mr.liu.remind;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import mr.liu.beans.Remind;
import mr.liu.dao.DBRemindHelper;
import mr.liu.remind.R.color;
import mr.liu.utils.AlarmUtils;
import mr.liu.utils.PickerUtils;

public class RemindAddActivity extends Activity {
	private DatePicker datePicker;
	private TextView current;
	private Button insertRemind;
	private EditText desc;
	private DBRemindHelper dbhelper;
	private final int TYPE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind_add);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		current = (TextView) findViewById(R.id.current_choose);
		insertRemind = (Button) findViewById(R.id.insertRemind);
		desc = (EditText) findViewById(R.id.reminddesc);
		dbhelper = new DBRemindHelper(this);
		initChoose();
		setListenser();
		PickerUtils.zoomPicker(datePicker);
	}

	/**
	 * 设置textview当前时间
	 */
	private void initChoose() {
		String str = "";
		int a = datePicker.getMonth() + 1;
		if (a < 10) {
			str = datePicker.getYear() + "-" + 0 + a + "-" + datePicker.getDayOfMonth();
		} else {
			str = datePicker.getYear() + "-" + a + "-" + datePicker.getDayOfMonth();
		}
		current.setText(str);
	}

	/**
	 * 为组件设置监听
	 */
	private void setListenser() {
		datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
				new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						int a = monthOfYear + 1;
						if (a < 10) {
							current.setText(year + "-" + 0 + a + "-" + dayOfMonth);
						} else {
							current.setText(year + "-" + a + "-" + dayOfMonth);
						}
					}
				});

		insertRemind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (desc.getText().toString().trim().isEmpty()) {
					Toast.makeText(RemindAddActivity.this, "详细的提醒内容不能为空", 0).show();
				} else {
					dbhelper = dbhelper.openDB();
					Remind re = new Remind(TYPE, current.getText().toString(), desc.getText().toString().trim(), 0,
							5000);
					dbhelper.insertRemind(re);
					int a = dbhelper.isInsert(re);
					if (a >= 0) {
						startAlarmService(a);
						makeToast(getString(R.string.insert_sucess));
					} else {
						makeToast(getString(R.string.insert_failed));
					}
				}
			}
		});
	}

	private void startAlarmService(int id) {
		Intent intent = new Intent(RemindAddActivity.this, AlarmService.class);
		intent.putExtra("remindid", id);
		startService(intent);
	}

	private void makeToast(String str) {
		Toast.makeText(RemindAddActivity.this, str, 0).show();
	}
	
}
