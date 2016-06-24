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

public class BottomSelectView extends PopupWindow {
	private Context context;
	private int contentViewID;
	private boolean[] selectState = new boolean[8];
	private SelectResult getResult;

	public BottomSelectView(Context context, int ContentViewID) {
		this.context = context;
		this.contentViewID = ContentViewID;
		initView();
	}

	public SelectResult getGetResult() {
		return getResult;
	}

	public void setGetResult(SelectResult getResult) {
		this.getResult = getResult;
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(contentViewID, null);
		final ItemStateView everyday = (ItemStateView) view.findViewById(R.id.everyday);
		everyday.setClick(true);
		final ItemStateView week1 = (ItemStateView) view.findViewById(R.id.weekone);
		final ItemStateView week2 = (ItemStateView) view.findViewById(R.id.weektwo);
		final ItemStateView week3 = (ItemStateView) view.findViewById(R.id.weekthree);
		final ItemStateView week4 = (ItemStateView) view.findViewById(R.id.weekfour);
		final ItemStateView week5 = (ItemStateView) view.findViewById(R.id.weekfive);
		final ItemStateView week6 = (ItemStateView) view.findViewById(R.id.weeksix);
		final ItemStateView week7 = (ItemStateView) view.findViewById(R.id.weekend);
		CheckStateListenser checklistenser = new CheckStateListenser() {
			@Override
			public void getState(boolean b) {
				// TODO Auto-generated method stub
				if (everyday.isClick() && b) {
					everyday.setClick(!b);
				}
			}
		};
		everyday.setCheckstate(new CheckStateListenser() {
			@Override
			public void getState(boolean b) {
				// TODO Auto-generated method stub
				if (b) {
					System.out.println("everyday--"+b);
					week1.setClick(!b);
					week2.setClick(!b);
					week3.setClick(!b);
					week4.setClick(!b);
					week5.setClick(!b);
					week6.setClick(!b);
					week7.setClick(!b);
				}
			}
		});
		week1.setCheckstate(checklistenser);
		week2.setCheckstate(checklistenser);
		week3.setCheckstate(checklistenser);
		week4.setCheckstate(checklistenser);
		week5.setCheckstate(checklistenser);
		week6.setCheckstate(checklistenser);
		week7.setCheckstate(checklistenser);

		Button enter = (Button) view.findViewById(R.id.select);
		enter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectState[0] = everyday.isClick();
				selectState[1] = week1.isClick();
				selectState[2] = week2.isClick();
				selectState[3] = week3.isClick();
				selectState[4] = week4.isClick();
				selectState[5] = week5.isClick();
				selectState[6] = week6.isClick();
				selectState[7]= week7.isClick();
				getResult.getResult(selectState);
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

	public boolean[] getSelectState() {
		return selectState;
	}

	public void setSelectState(boolean[] selectState) {
		this.selectState = selectState;
	}

	public interface SelectResult {
		public void getResult(boolean[] b);
	}

}
