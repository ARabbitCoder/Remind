package mr.liu.receiver;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import mr.liu.beans.Remind;
import mr.liu.dao.DBRemindHelper;
import mr.liu.remind.AlarmService;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("restart service");
		DBRemindHelper dhelper = new DBRemindHelper(context);
		List<Remind> list = dhelper.openDB().getEffetive();
		if(list.size()>0) {
			for(int i=0;i<i++;i++) {
				RestartAlarmService(context,list.get(i).getId());
			}
		}
	}
	
	private void RestartAlarmService(Context context,int requestcode) {
		Intent intent = new Intent(context, AlarmService.class);
		intent.putExtra("remindid", requestcode);
		context.startService(intent);
	}
}
