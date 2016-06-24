package mr.liu.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MyHandler extends Handler{
	private Context context;
	private SimpleDateFormat sdf;
	private Date date = null;
	private TextView year;
	private TextView week;
	private TextView time;
	private String[] formatTime;
	public MyHandler(Context context,TextView year,TextView week,TextView time) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.year = year;
		this.week = week;
		this.time = time;
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
	}
	
	public String[] getTime() {
		date = null;
		date = new Date();
		String sourse = sdf.format(date);
		return sourse.split(" ");
	}
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case 0x111:
			formatTime = getTime();
			year.setText(formatTime[0]);
			time.setText(formatTime[1]);
			week.setText(formatTime[2]);
			break;
		default:
			break;
		}
		
		
	}
	
	
}
