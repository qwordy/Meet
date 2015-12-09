package sjtu.se.Ubma;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;
import sjtu.se.Meet.R;

public class UbmaOldActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		//toolbar.setNavigationIcon(R.drawable.);
		//monitorScreen();
		//setAlarm();
		Intent intent = new Intent(this, MonitorService.class);
		startService(intent);
	}

	public void AppListActivity(View view) {
		Intent intent = new Intent(this, AllAppsActivity.class);
		startActivity(intent);
	}

	public void ActiveTimeActivity(View view) {
		Intent intent = new Intent(this, ActiveTimeActivity.class);
		startActivity(intent);
	}

	void setAlarm() {
		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 5);
		//Log.d("Meet", calendar.toString());
		//alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5000, pendingIntent);
	}

	void monitorScreen() {
		final int interval = 1000;
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
		handler.postDelayed(runnable, interval);

		Intent intent = new Intent("android.intent.action.");
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
