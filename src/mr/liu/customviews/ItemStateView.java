package mr.liu.customviews;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import mr.liu.remind.R;

public class ItemStateView extends RelativeLayout {
	private TextView desc;
	private CheckBox check;
	private String text;
	private boolean click = false;
	private CheckStateListenser checkState;
	
	public CheckStateListenser getCheckstate() {
		return checkState;
	}

	public void setCheckstate(CheckStateListenser checkstate) {
		this.checkState = checkstate;
	}

	public ItemStateView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public ItemStateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ItemSelectView);
		text = a.getString(R.styleable.ItemSelectView_desc);
		click = a.getBoolean(R.styleable.ItemSelectView_check, false);
		init();
	}

	public ItemStateView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	private void init() {
		View.inflate(getContext(), R.layout.item_select, this);
		desc = (TextView) findViewById(R.id.textview);
		check = (CheckBox) findViewById(R.id.checkbox);
		desc.setText(text);
		check.setChecked(click);
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click =!click;
				check.setChecked(click);
				checkState.getState(click);
			}
		});
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
		check.setChecked(click);
	}
	public interface CheckStateListenser {
		public void getState(boolean b);
	}
}
