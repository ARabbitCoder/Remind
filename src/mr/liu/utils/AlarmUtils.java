package mr.liu.utils;

import android.os.Build;

public class AlarmUtils {
	public static boolean sdkVersion() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			return true;
		} else {
			return false;
		}
	}

}
