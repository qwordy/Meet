package sjtu.se.Ubma;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class MonitorService extends Service {
	boolean started = false;
	BroadcastReceiver receiver;
	boolean screenOn;
	Calendar calendar;
	ActiveTimeData activeTimeData;
	File file;

	public MonitorService() {
	}

	@Override
	public void onCreate() {
		//Log.d("Meet", getFilesDir().toString());
		//Log.d("Meet", getCacheDir().toString());
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

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		screenOn = pm.isScreenOn();
		calendar = Calendar.getInstance();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		receiver = new Receiver();
		registerReceiver(receiver, intentFilter);
	}

	class Receiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Calendar newCalendar = Calendar.getInstance();
			long gap = newCalendar.getTimeInMillis() - calendar.getTimeInMillis();
			Log.d("Meet", String.valueOf(gap));

			String action = intent.getAction();
			if (action.equals(Intent.ACTION_SCREEN_OFF)) {
				screenOn = false;
				activeTimeData.addTime(calendar, newCalendar);
				try {
					ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
					output.writeObject(activeTimeData);
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (action.equals(Intent.ACTION_SCREEN_ON)) {
				screenOn = true;
			}
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		/*Log.d("Meet", String.valueOf(started));
		if (started) return START_STICKY;
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

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}