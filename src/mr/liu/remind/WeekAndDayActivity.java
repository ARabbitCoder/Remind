package mr.liu.remind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;
import mr.liu.beans.Remind;
import mr.liu.customviews.BottomSelectView;
import mr.liu.customviews.BottomSelectView.SelectResult;
import mr.liu.customviews.RepeatSelectView;
import mr.liu.customviews.RepeatSelectView.SelectItemListenser;
import mr.liu.dao.DBRemindHelper;
import mr.liu.utils.PickerUtils;
import mr.liu.utils.TimeUtils;

public class WeekAndDayActivity extends Activity {
	private BottomSelectView bs;
	private RepeatSelectView rs;
	private TimePicker tpicker;
	private TextView ctime;
	private TextView ctype;
	private TextView crepeat;
	private EditText desc;
	private Button add;
	private LinearLayout week_select;
	private LinearLayout repeat_select;
	private boolean[] select = new boolean[8];
	private DBRemindHelper dbhelper;
	private final long tenrepeat = 600000;
	private final long halfrepeat = 1800000;
	private final long onerepeat = 3600000;
	private final long tworepeat = 7200000;
	private final long sixrepeat = 21600000;
	private long currentrepeat = tenrepeat;
	private final int TYPE_WEEK = 1;
	private final int TYPE_DAY = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_week_and_day);
		dbhelper = new DBRemindHelper(this);
		tpicker = (TimePicker) findViewById(R.id.timePicker);
		tpicker.setIs24HourView(true);
		week_select = (LinearLayout) findViewById(R.id.week_select);
		repeat_select = (LinearLayout) findViewById(R.id.repeat_select);
		ctime = (TextView) findViewById(R.id.current_time);
		ctype = (TextView) findViewById(R.id.current_type);
		crepeat = (TextView) findViewById(R.id.remind_repeat);
		add = (Button) findViewById(R.id.insertRemind);
		desc = (EditText) findViewById(R.id.reminddesc);
		PickerUtils.zoomPicker(tpicker);
		if (getIntent().getExtras() != null) {
			Remind re = (Remind) getIntent().getExtras().get("remind");
			UpdateView(re);
			initListenser(false);
		} else {
			initListenser(true);
		}
	}
	private void UpdateView(Remind re) {
		if(re.getType()==1) {
			String[] s1 = re.getDate().split(":");
			int a = Integer.parseInt(s1[0].trim());
			int hour = Integer.parseInt(s1[1].trim());
			int minute = Integer.parseInt(s1[2].trim());
			tpicker.setCurrentHour(hour);
			tpicker.setCurrentMinute(minute);
			setWeek(a);
		}else {
			String[] s1 = re.getDate().split(":");
			int hour = Integer.parseInt(s1[0].trim());
			int minute = Integer.parseInt(s1[1].trim());
			tpicker.setCurrentHour(hour);
			tpicker.setCurrentMinute(minute);
			setWeek(0);
		}
		desc.setText(re.getDesc());
	}
	private void setRepeat(long repeat1) {
		if (repeat1 == tenrepeat) {
			crepeat.setText(R.string.tenminute);
			currentrepeat = tenrepeat;
		} else if (repeat1 == halfrepeat) {
			crepeat.setText(R.string.halfhour);
			currentrepeat = tenrepeat;
		} else if (repeat1 == onerepeat) {
			crepeat.setText(R.string.onehour);
			currentrepeat = tenrepeat;
		} else if (repeat1 == tworepeat) {
			crepeat.setText(R.string.twohour);
			currentrepeat = tenrepeat;
		} else if (repeat1 == sixrepeat) {
			crepeat.setText(R.string.sixhour);
			currentrepeat = tenrepeat;
		}
	}
	private void setWeek(int a) {
		switch (a) {
		case 0:
			ctype.setText(R.string.everyday);
			select[0] = true;
			break;
		case 1:
			ctype.setText(R.string.sunday);
			select[1] = true;
			break;
		case 2:
			ctype.setText(R.string.monday);
			select[2] = true;
			break;
		case 3:
			ctype.setText(R.string.tuesday);
			select[3] = true;
			break;
		case 4:
			ctype.setText(R.string.wednsday);
			select[4] = true;
			break;
		case 5:
			ctype.setText(R.string.thursday);
			select[5] = true;
			break;
		case 6:
			ctype.setText(R.string.friday);
			select[6] = true;
			break;
		case 7:
			ctype.setText(R.string.saturday);
			select[7] = true;
			break;
		}
	}
	private void initListenser(boolean b) {
		if(b) {
			select[0] = true;
		}
		String str = TimeUtils.formatHourNum(tpicker.getCurrentHour(), tpicker.getCurrentMinute());
		ctime.setText(str);
		week_select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showBottomSelect();
			}
		});
		repeat_select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showRepeatSelect();
			}
		});
		tpicker.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				String st1 = TimeUtils.formatHourNum(hourOfDay, minute);
				ctime.setText(st1);
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (desc.getText().toString().trim().isEmpty()) {
					Toast.makeText(WeekAndDayActivity.this, "详细的提醒内容不能为空", 0).show();
				} else {
					InsertRemind();
					desc.setText("");
				}
			}
		});
	}

	private void InsertRemind() {
		dbhelper = dbhelper.openDB();
		if (select[0]) {
			Remind re = new Remind(TYPE_DAY, ctime.getText().toString(), desc.getText().toString().trim(), 0, currentrepeat);
			dbhelper.insertRemind(re);
			int a = dbhelper.isInsert(re);
			if (a >= 0) {
				startAlarmService(a);
				makeToast(getString(R.string.insert_sucess));
			} else {
				makeToast(getString(R.string.insert_failed));
			}
		} else {
			for (int i = 1; i < select.length; i++) {
				if (select[i]) {
					if (i < 7) {
						Remind re = new Remind(TYPE_WEEK, (i + 1) + ":" + ctime.getText().toString(),
								desc.getText().toString().trim(), 0, currentrepeat);
						dbhelper.insertRemind(re);
						int a = dbhelper.isInsert(re);
						if (a >= 0) {
							startAlarmService(a);
							makeToast(getString(R.string.insert_sucess));
						} else {
							makeToast(getString(R.string.insert_failed));
						}
					} else {
						Remind re = new Remind(TYPE_WEEK, "1" + ":" + ctime.getText().toString(),
								desc.getText().toString().trim(), 0, currentrepeat);
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
			}
		}
		dbhelper.close();
	}

	private void showBottomSelect() {
		bs = new BottomSelectView(WeekAndDayActivity.this, R.layout.bottom_select);
		bs.showAtLocation(WeekAndDayActivity.this.findViewById(R.id.layoutparent),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		bs.setGetResult(new SelectResult() {
			@Override
			public void getResult(boolean[] b) {
				// TODO Auto-generated method stub
				select = b;
				String str = "";
				String[] week = getResources().getStringArray(R.array.all_week);
				for (int i = 0; i < b.length; i++) {
					if (b[i]) {
						str = str + " " + week[i];
					}
				}
				ctype.setText(str);
			}
		});
	}
	private void showRepeatSelect() {
		rs = new RepeatSelectView(WeekAndDayActivity.this, R.layout.repeat_select);
		rs.showAtLocation(WeekAndDayActivity.this.findViewById(R.id.layoutparent),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		rs.setItemListenser(new SelectItemListenser() {
			@Override
			public void getItem(int id) {
				// TODO Auto-generated method stub
				switch (id) {
				case 0:
					crepeat.setText(R.string.tenminute);
					currentrepeat = tenrepeat;
					break;
				case 1:
					crepeat.setText(R.string.halfhour);
					currentrepeat = halfrepeat;
					break;
				case 2:
					crepeat.setText(R.string.onehour);
					currentrepeat = onerepeat;
					break;
				case 3:
					crepeat.setText(R.string.twohour);
					currentrepeat = tworepeat;
					break;
				case 4:
					crepeat.setText(R.string.sixhour);
					currentrepeat = sixrepeat;
					break;
				}
			}
		});
	}
	private void startAlarmService(int id) {
		Intent intent = new Intent(WeekAndDayActivity.this, AlarmService.class);
		intent.putExtra("remindid", id);
		startService(intent);
	}

	private void makeToast(String str) {
		Toast.makeText(WeekAndDayActivity.this, str, 0).show();
	}
}
