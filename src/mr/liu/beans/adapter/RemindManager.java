package mr.liu.beans.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import mr.liu.beans.Remind;
import mr.liu.dao.DBRemindHelper;
import mr.liu.remind.R;

public class RemindManager extends BaseAdapter{
	private Context context;
	private int id;
	private DBRemindHelper dbhelper;
	private List<Remind> list;
	private Remind re;
	public RemindManager(Context context,int resoureid) {
		this.context = context;
		this.id = resoureid;
		dbhelper = new DBRemindHelper(context);
		list = dbhelper.openDB().getAllRemind();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		re = list.get(position);
		if (convertView == null) {
			convertView = View.inflate(context,
					id, null);
			new ViewHolder(convertView);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if(re.getType()==0) {
			holder.timetext.setText(re.getDate());
		}else if(re.getType()==1) {//week
			holder.timetext.setText(formatWeek(re.getDate()));
		}else {
			holder.timetext.setText(context.getString(R.string.everyday)+" "+re.getDate());
		}
		holder.desctext.setText(re.getDesc());
		holder.donetext.setText(re.getDone()==1?context.getString(R.string.done):context.getString(R.string.undone));
		holder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Fresh(re, position);
			}
		});
		return convertView;
	}
	class ViewHolder {
		TextView  timetext;
		TextView  desctext;
		TextView  donetext;
		TextView  update;
		TextView  delete;
		public ViewHolder(View view) {
			timetext = (TextView) view.findViewById(R.id.item_time);
			desctext = (TextView) view.findViewById(R.id.item_desc);
			donetext = (TextView) view.findViewById(R.id.isdone);
			update = (TextView) view.findViewById(R.id.update);
			delete = (TextView) view.findViewById(R.id.delete);
			view.setTag(this);
		}
	}
	private String formatWeek(String week) {
		String[] s1 = week.split(":");
		System.out.println(s1[0].trim().equals("2"));
		if(s1[0].trim().equals("1")) {
			return context.getString(R.string.sunday)+" "+s1[1]+":"+s1[2];
		}else if(s1[0].trim().equals("2")) {
			return context.getString(R.string.monday)+" "+s1[1]+":"+s1[2];
		}else if(s1[0].trim().equals("3")) {
			return context.getString(R.string.tuesday)+" "+s1[1]+":"+s1[2];
		}else if(s1[0].trim().equals("4")) {
			return context.getString(R.string.wednsday)+" "+s1[1]+":"+s1[2];
		}else if(s1[0].trim().equals("5")) {
			return context.getString(R.string.thursday)+" "+s1[1]+":"+s1[2];
		}else if(s1[0].trim().equals("6")) {
			return context.getString(R.string.friday)+" "+s1[1]+":"+s1[2];
		}else {
			return context.getString(R.string.saturday)+" "+s1[1]+":"+s1[2];
		}
	}
	private void Fresh(Remind re,int position) {
		dbhelper.deleteRemind(re);
		list.remove(position);
		notifyDataSetChanged();
	}
}
