package mr.liu.utils;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.LinearLayout.LayoutParams;

public class PickerUtils {
	public static void zoomPicker(FrameLayout fp) {
		List<NumberPicker> npList = findNumberPicker(fp);

		for (int i = 0; i < npList.size(); i++) {
			resizeNumberPicker(npList.get(i));
		}
	}
	private static List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
		List<NumberPicker> npList = new ArrayList<NumberPicker>();
		View child = null;
		if (null != viewGroup) {
			for (int i = 0; i < viewGroup.getChildCount(); i++) {
				child = viewGroup.getChildAt(i);
				if (child instanceof NumberPicker) {
					npList.add((NumberPicker) child);
				} else if (child instanceof LinearLayout) {
					List<NumberPicker> result = findNumberPicker((ViewGroup) child);
					if (result.size() > 0) {
						return result;
					}
				}
			}
		}
		return npList;
	}
	private static void resizeNumberPicker(NumberPicker np) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams defultparam = (LayoutParams) np.getLayoutParams();
		params.setMargins(defultparam.leftMargin, 0, defultparam.rightMargin, 0);
		np.setLayoutParams(params);
		np.setScaleY((float) 0.8);
		np.setScaleX((float) 0.8);
	}
}
