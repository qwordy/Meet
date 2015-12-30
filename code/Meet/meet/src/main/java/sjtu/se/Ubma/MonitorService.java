package sjtu.se.Ubma;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.TextView;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonitorService extends Service {
	private BroadcastReceiver receiver;
	private Calendar calendar;
	private ActiveTimeData activeTimeData;
	private File file;
	private List<AppInfo> aiList;
	private List<AppInfo> userAiList;
	private UserBehaviourSummary userBehaviourSummary;
	private IBinder mBinder = new MyBinder();

	public MonitorService() {
	}

	public List<AppInfo> getAiList() {
		return aiList;
	}

	public List<AppInfo> getUserAiList() {
		return userAiList;
	}

	public UserBehaviourSummary getUserBehaviourSummary() {
		while (userBehaviourSummary == null)
			Thread.yield();
		return userBehaviourSummary;
	}

	@Override
	public void onCreate() {
		Log.d("Meet", "MonitorService started");
		//Log.d("Meet", getFilesDir().toString());
		//Log.d("Meet", getCacheDir().toString());

		new MyThread().start();

		try {
			file = new File(getFilesDir(), "activeTimeData");
			if (file.exists()) {
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
				activeTimeData = (ActiveTimeData) input.readObject();
				input.close();
			} else {
				activeTimeData = new ActiveTimeData();
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				output.writeObject(activeTimeData);
				output.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		calendar = Calendar.getInstance();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		receiver = new Receiver();
		registerReceiver(receiver, intentFilter);
	}

	private class MyThread extends Thread {
		@Override
		public void run() {
			PackageManager pm = getPackageManager();
			List<PackageInfo> piList = pm.getInstalledPackages(0);
			aiList = new ArrayList<>();
			userAiList = new ArrayList<>();
			for (PackageInfo pi : piList) {
				AppInfo appInfo = new AppInfo(
						pi.packageName,
						pi.versionName,
						pi.versionCode,
						pi.applicationInfo.loadLabel(pm).toString(),
						pi.applicationInfo.loadIcon(pm),
						pi.applicationInfo.loadLogo(pm));
				aiList.add(appInfo);
				if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
					userAiList.add(appInfo);
			}
			Log.d("Meet", "MonitorService getAppList");

			userBehaviourSummary = new UserBehaviourSummary(MonitorService.this);
			userBehaviourSummary.appsTags();
		}
	}

	private class Receiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Calendar newCalendar = Calendar.getInstance();
			long gap = newCalendar.getTimeInMillis() - calendar.getTimeInMillis();
			Log.d("Meet", String.valueOf(gap));

			String action = intent.getAction();
			if (action.equals(Intent.ACTION_SCREEN_OFF)) {
				activeTimeData.addTime(calendar, newCalendar);
				try {
					ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
					output.writeObject(activeTimeData);
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("Meet", "MonitorService onStartCommand");
		/*if (started) return START_STICKY;
		started = true;
		final int interval = 5000;
		final Handler handler = new Handler();
		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				handler.postDelayed(this, interval);
				if (pm.isScreenOn())
					Log.d("Meet", "on");
				else
					Log.d("Meet", "off");
			}
		};
		handler.postDelayed(runnable, interval);*/
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
	}

	public class MyBinder extends Binder {
		public MonitorService getService() {
			return MonitorService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
}
