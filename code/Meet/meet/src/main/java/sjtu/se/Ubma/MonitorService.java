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
import android.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonitorService extends Service {
	private BroadcastReceiver receiver;
	private Calendar calendar;
	private ActiveTimeData activeTimeData;
	private IBinder mBinder = new MyBinder();

	@Override
	public void onCreate() {
		Log.d("Meet", "MonitorService started");
		//Log.d("Meet", getFilesDir().toString());

		Environment.activeTimeFile = new File(getFilesDir(), "activeTimeData");
		Environment.activeTimeData = activeTimeData = new ActiveTimeData();
		Environment.setAiList(new ArrayList<AppInfo>());
		Environment.setUserAiList(new ArrayList<AppInfo>());
		calendar = Calendar.getInstance();
		new MyThread().start();

		// Screen on/off receiver
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		receiver = new Receiver();
		registerReceiver(receiver, intentFilter);
	}

	private class MyThread extends Thread {
		@Override
		public void run() {
			List<AppInfo> aiList = Environment.getAiList();
			List<AppInfo> userAiList = Environment.getUserAiList();
			PackageManager pm = getPackageManager();
			List<PackageInfo> piList = pm.getInstalledPackages(0);
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

			Environment.setAiList(aiList);
			Environment.setUserAiList(userAiList);
			Environment.setUserBehaviourSummary(new UserBehaviourSummary());
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
				activeTimeData.writeInstance();
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
