package sjtu.se.Ubma;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by qwordy on 12/5/15.
 * Utility
 */

public class Utility {
	static float dp2px(float size) {
		Resources r = Resources.getSystem();
		return TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, size, r.getDisplayMetrics());
	}

	static float sp2px(float size) {
		Resources r = Resources.getSystem();
		return TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, size, r.getDisplayMetrics());
	}
}
