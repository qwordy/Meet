package sjtu.se.Activity.Setting;

import com.example.bluetoothtry.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import sjtu.se.Activity.ActivityControlCenter;

public class SystemSettings extends PreferenceActivity {

	//public static boolean dayOrNightChange = false;
	public static boolean night = false;

	private IntentFilter intentFilter;
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ActivityControlCenter.ACTIVITY_EXIT_ACTION)){
				finish();
			}
		}
	};

	@SuppressWarnings("deprecation")
	@Override

	protected void onCreate(Bundle savedInstanceState) {

		//SystemSettings.dayOrNight(this);

		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_system_settings);
		addPreferencesFromResource(R.xml.system);

		intentFilter = new IntentFilter();
		intentFilter.addAction(ActivityControlCenter.ACTIVITY_EXIT_ACTION);
		this.registerReceiver(receiver, intentFilter);

		//ActivityControlCenter.personal_base_info = getSharedPreferences("test", MODE_WORLD_READABLE & MODE_WORLD_WRITEABLE);
		//System.out.println(ActivityControlCenter.personal_base_info.getString("test", null));

		CheckBoxPreference nightModel = (CheckBoxPreference) findPreference("day_or_night");
		nightModel.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				//SystemSettings.dayOrNightChange = true;
				//SystemSettings.day = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("day_or_night", false);
				recreate();
				return true;
			}
		});
	}

	public static void dayOrNight(Context context){
		boolean b = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("day_or_night", false);
		night = b;
		if (b)
			context.setTheme(android.R.style.Theme_Holo);
		else
			context.setTheme(android.R.style.Theme_Holo_Light);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.system_settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home){
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
