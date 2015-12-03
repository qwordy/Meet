package sjtu.se.Activity.ChatPlatform.UI;

import sjtu.se.Meet.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PageIndicatorView extends ImageView {
	private final String TAG = "PageIndicatorView";
	private LinearLayout mPageIndicLayout;

	public PageIndicatorView(Context context) {
		super(context);
		setSelectedView(false);
	}
	
	public void setSelectedView(boolean selected){
		Bitmap bitmap;
		if(selected){
			bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.page_select);
		}else{
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.page_item);
		}
		this.setImageBitmap(bitmap);
	}
}