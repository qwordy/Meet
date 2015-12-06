package sjtu.se.Activity.Setting;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import sjtu.se.Fragment.Pref_Fragment;
import sjtu.se.Meet.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import sjtu.se.Activity.ActivityControlCenter;

public class SystemSettings extends AppCompatActivity {

	//public static boolean dayOrNightChange = false;
	//public static boolean night = false;

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

	@Override

	protected void onCreate(Bundle savedInstanceState) {

		//SystemSettings.dayOrNight(this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pref);

		Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
		setSupportActionBar(myToolbar);
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		getFragmentManager().beginTransaction().replace(R.id.pref_fragment_container, new Pref_Fragment()).commit();

		//intentFilter = new IntentFilter();
		//intentFilter.addAction(ActivityControlCenter.ACTIVITY_EXIT_ACTION);
		//this.registerReceiver(receiver, intentFilter);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.search, menu);
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
			//this.startActivity(new Intent(SystemSettings.this, SystemSettings.class));
			return false;
		}

		return super.onOptionsItemSelected(item);
	}

}
