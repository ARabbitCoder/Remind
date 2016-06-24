package mr.liu.remind;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import mr.liu.customviews.ExpandView;
import mr.liu.handler.MyHandler;

public class MainActivity extends Activity {
	private TextView month_day;
	private TextView week;
	private TextView curtime;
	private ImageView add;
	private MyHandler handler;
	private Animation openAnim;
	private Animation closeAnim;
	private ExpandView expandview;
	private boolean isclick = false;
	private boolean startMonthActivity = false;
	private boolean startWeekActivity = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		month_day = (TextView) findViewById(R.id.current_year_month);
		week = (TextView) findViewById(R.id.current_weekday);
		curtime = (TextView) findViewById(R.id.current_time);
		add = (ImageView) findViewById(R.id.add_remind);
		expandview = (ExpandView) findViewById(R.id.expand_view);
		initAnim();
		setExpandListenser();
		handler = new MyHandler(this, month_day, week, curtime);
		Timer timer = new Timer();
		timer.schedule(new myTimerTask(), 0, 1000);
	}
	/**
	 * 设置展开textview中的点击监听
	 */
	private void setExpandListenser() {
		View view = expandview.getRootView();
		TextView monthremind = (TextView) findViewById(R.id.month_remind);
		TextView dayremind = (TextView) findViewById(R.id.day_remind);
		monthremind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				closedAnim();
				startMonthActivity = true;
			}
		});
		dayremind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				closedAnim();
				startWeekActivity = true;
			}
		});
	}
	/**
	 * 初始化添加按钮的打开及关闭动画
	 */
	private void initAnim() {
		openAnim = AnimationUtils.loadAnimation(this, R.anim.add_open);  
		closeAnim = AnimationUtils.loadAnimation(this, R.anim.add_close);
		openAnim.setFillAfter(true);
		closeAnim.setFillAfter(true);
		closeAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				if(startMonthActivity) {
					Intent intent = new Intent(MainActivity.this,RemindAddActivity.class);
					startActivity(intent);
					startMonthActivity = false;
				}
				if(startWeekActivity) {
					Intent intent = new Intent(MainActivity.this,WeekAndDayActivity.class);
					startActivity(intent);
					startWeekActivity = false;
				}
			}
		});
	}
	/**
	 * 添加按钮的click触发事件
	 * @param view
	 */
	public void addRemind(View view) {
		if(isclick) {
			closedAnim();
		}else{
			expandAnim();
		}
	}
	private void closedAnim() {
		add.clearAnimation();
		add.startAnimation(closeAnim);
		expandview.collapse();
		isclick = false;
	}
	private void expandAnim() {
		add.clearAnimation();
		add.startAnimation(openAnim);
		expandview.expand();
		isclick = true;
	}
	class myTimerTask extends TimerTask{
		@Override
		public void run() { 
			handler.sendEmptyMessage(0x111);
		}
	}
}
