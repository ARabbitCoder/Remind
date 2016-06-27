package mr.liu.test;

import java.util.Date;

import android.content.SyncStatusObserver;
import junit.framework.TestCase;
import mr.liu.utils.TimeUtils;

public class Test extends TestCase {
	public void test() {
		System.out.println(System.currentTimeMillis());
		System.out.println(TimeUtils.DayDValue(10,51));
		System.out.println(TimeUtils.DayDValue(9,0));
	}
}
