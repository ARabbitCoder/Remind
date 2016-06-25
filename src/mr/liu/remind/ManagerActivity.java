package mr.liu.remind;

import android.app.ListActivity;
import android.os.Bundle;
import mr.liu.beans.adapter.RemindManager;

public class ManagerActivity extends ListActivity {
	private RemindManager adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager);
		adapter = new RemindManager(this, R.layout.listview_item);
		setListAdapter(adapter);
	}
}
