package sjtu.se.Ubma;

import android.graphics.drawable.Drawable;

/**
 * Created by qwordy on 11/22/15.
 * Application infomation
 */

public class AppInfo {
	public AppInfo(String packageName, String versionName, int versionCode, String appLabel, Drawable appIcon, Drawable appLogo) {
		this.packageName = packageName;
		this.versionName = versionName;
		this.versionCode = versionCode;
		this.appLabel = appLabel;
		this.appIcon = appIcon;
		this.appLogo = appLogo;
		this.category = AppClassifier.classify(appLabel);
		//Log.d("Meet", this.category.toString());
	}

	public String packageName;
	public String versionName;
	public int versionCode;
	public String appLabel;
	public Drawable appIcon;
	public Drawable appLogo;
	public AppClassifier.Category category;
}
