package sjtu.se.Util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import sjtu.se.Meet.R;

public class Notify {
	public static final int NOTIFY_ID1 = 1001;
	
	public static void notifyMessage(Context context, String ticker, String title, String text, Activity activity){

		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, activity.getClass()), 0);

		Notification notify = new Notification.Builder(context)
				.setSmallIcon(R.drawable.icon) // 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap icon)
				.setTicker(ticker)// 设置在status bar上显示的提示文字
				.setContentTitle(title)// 设置在下拉status bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
				.setContentText(text)// TextView中显示的详细内容
				.setContentIntent(pendingIntent)
				.getNotification();

		notify.defaults |= Notification.DEFAULT_VIBRATE;
		notify.defaults |= Notification.DEFAULT_SOUND; // 调用系统自带声音
		notify.flags |= Notification.FLAG_AUTO_CANCEL; // 点击清除按钮或点击通知后会自动消失
		notify.defaults |= Notification.DEFAULT_LIGHTS;
		notify.vibrate = new long[]{300, 500};

		manager.notify(NOTIFY_ID1, notify);
	}
}
