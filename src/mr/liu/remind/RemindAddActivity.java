package mr.liu.remind;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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
import mr.liu.customviews.RepeatSelectView;
import mr.liu.customviews.RepeatSelectView.SelectItemListenser;
import mr.liu.dao.DBRemindHelper;
import mr.liu.remind.R.color;
import mr.liu.utils.AlarmUtils;
import mr.liu.utils.PickerUtils;

public class RemindAddActivity extends Activity {
	private DatePicker datePicker;
	private TextView current;
	private Button insertRemind;
	private RepeatSelectView rs;
	private EditText desc;
	private DBRemindHelper dbhelper;
	private LinearLayout repeat;
	private TextView crepeat;
	private final long tenrepeat = 600000;
	private final long halfrepeat = 1800000;
	private final long onerepeat = 3600000;
	private final long tworepeat = 7200000;
	private final long sixrepeat = 21600000;
	private long currentrepeat = tenrepeat;
	private final int TYPE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind_add);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		current = (TextView) findViewById(R.id.current_choose);
		insertRemind = (Button) findViewById(R.id.insertRemind);
		desc = (EditText) findViewById(R.id.reminddesc);
		repeat = (LinearLayout) findViewById(R.id.date_repeat_select);
		crepeat = (TextView) findViewById(R.id.date_remind_repeat);
		dbhelper = new DBRemindHelper(this);
		PickerUtils.zoomPicker(datePicker);
		if (getIntent().getExtras() != null) {
			Remind re = (Remind) getIntent().getExtras().get("remind");
			UpdateView(re);
		} else {
			AddView();
		}
	}

	private void AddView() {
		initChoose();
		setListenser();
	}

	private void UpdateView(Remind re) {
		String[] s1 = re.getDate().split("-");
		datePicker.updateDate(Integer.parseInt(s1[0].trim()), Integer.parseInt(s1[1].trim()),
				Integer.parseInt(s1[2].trim()));
		long getrepeat = re.getRepeat();
		initRepeat(getrepeat);
		desc.setText(re.getDesc());
		initChoose();
		setListenser();
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
							currentrepeat);
					dbhelper.insertRemind(re);
					int a = dbhelper.isInsert(re);
					if (a >= 0) {
						startAlarmService(a);
						makeToast(getString(R.string.insert_sucess));
						desc.setText("");
					} else {
						makeToast(getString(R.string.insert_failed));
					}
				}
			}
		});
		repeat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showRepeatSelect();
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

	private void showRepeatSelect() {
		rs = new RepeatSelectView(RemindAddActivity.this, R.layout.repeat_select);
		rs.showAtLocation(RemindAddActivity.this.findViewById(R.id.date_layout_parent),
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

	private void initRepeat(long repeat1) {
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
}
