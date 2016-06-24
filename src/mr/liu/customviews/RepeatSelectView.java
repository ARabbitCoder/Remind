package mr.liu.customviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import mr.liu.customviews.ItemStateView.CheckStateListenser;
import mr.liu.remind.R;

public class RepeatSelectView extends PopupWindow {
	private Context context;
	private int contentViewID;
	private int id = 0;
	private SelectItemListenser itemListenser;
	public SelectItemListenser getItemListenser() {
		return itemListenser;
	}
	public void setItemListenser(SelectItemListenser itemListenser) {
		this.itemListenser = itemListenser;
	}

	public RepeatSelectView(Context context, int ContentViewID) {
		this.context = context;
		this.contentViewID = ContentViewID;
		initView();
	}



	private void initView() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(contentViewID, null);
		final ItemStateView tenminute = (ItemStateView) view.findViewById(R.id.tenminute);
		tenminute.setClick(true);
		final ItemStateView halfhour = (ItemStateView) view.findViewById(R.id.halfhour);
		final ItemStateView onehour = (ItemStateView) view.findViewById(R.id.onehour);
		final ItemStateView twohour = (ItemStateView) view.findViewById(R.id.twohour);
		final ItemStateView sixhour = (ItemStateView) view.findViewById(R.id.sixhour);
		tenminute.setCheckstate(new CheckStateListenser() {
			@Override
			public void getState(boolean b) {
				// TODO Auto-generated method stub
				if(b) {
					id = 0;
					halfhour.setClick(!b);
					onehour.setClick(!b);
					twohour.setClick(!b);
					sixhour.setClick(!b);
				}
			}
		});
		halfhour.setCheckstate(new CheckStateListenser() {
			@Override
			public void getState(boolean b) {
				// TODO Auto-generated method stub
				if(b) {
					id = 1;
					tenminute.setClick(!b);
					onehour.setClick(!b);
					twohour.setClick(!b);
					sixhour.setClick(!b);
				}
			}
		});
		onehour.setCheckstate(new CheckStateListenser() {
			@Override
			public void getState(boolean b) {
				// TODO Auto-generated method stub
				if(b) {
					id = 2;
					halfhour.setClick(!b);
					tenminute.setClick(!b);
					twohour.setClick(!b);
					sixhour.setClick(!b);
				}
			}
		});
		twohour.setCheckstate(new CheckStateListenser() {
			@Override
			public void getState(boolean b) {
				// TODO Auto-generated method stub
				if(b) {
					id = 3;
					halfhour.setClick(!b);
					onehour.setClick(!b);
					tenminute.setClick(!b);
					sixhour.setClick(!b);
				}
			}
		});
		sixhour.setCheckstate(new CheckStateListenser() {
			@Override
			public void getState(boolean b) {
				// TODO Auto-generated method stub
				if(b) {
					id = 4;
					halfhour.setClick(!b);
					onehour.setClick(!b);
					twohour.setClick(!b);
					tenminute.setClick(!b);
				}
			}
		});
		Button enter = (Button) view.findViewById(R.id.weekrepeat);
		enter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itemListenser.getItem(id);
				dismiss();
			}
		});
		View topview = view.findViewById(R.id.top);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		this.setContentView(view);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
	}

	public interface SelectItemListenser {
		public void getItem(int id);
	}

}
