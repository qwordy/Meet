package sjtu.se.Activity.Search;

import java.util.ArrayList;
import java.util.Set;

//程治谦
//import sjtu.se.Activity.ContactCard.ContactCardSettings;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.*;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Activity.ChatPlatform.ChatActivity;
import sjtu.se.Activity.Information.BaseInfoSettings;
import sjtu.se.Activity.Information.ShowInformation;
import sjtu.se.Activity.Setting.SettingFragment;
import sjtu.se.Activity.Setting.SystemSettings;
import sjtu.se.Activity.Want.WantSettings;
import sjtu.se.Ubma.UbmaActivity;
import sjtu.se.Ubma.UbmaDrawerActivity;
import sjtu.se.Util.*;
import sjtu.se.UserInformation.Information;
import sjtu.se.UserInformation.Want;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.view.KeyEvent;

import sjtu.se.Meet.R;
public class Search extends AppCompatActivity {

	private static final int REQUEST_FOR_ENABLE = 1;

	private String[] mMenuTitles;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;

    private Want want1;
    private Want want2;
    private Want want3;
    private Want want4;
    private Want want5;
    private Want want6;
    private Want want7;
    private Want want8;

    private Information overt_user;
    private Information full_user;

    private Context ctx;
    private IntentFilter intentFilter;

	public BluetoothAdapter mBluetoothAdapter;

	public DevBluetoothAdapter DeviceListAdapter;
	public DevBluetoothAdapter HistoryDevListAdapter;
	public DevBluetoothAdapter RecommendDevListAdapter;

	private ListView DeviceList;
	private ListView HistoryDeviceList;
	private ListView RecommendDeviceList;
	private ArrayList<DevBluetooth> OldRecommendList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

		ctx = this;

        mTitle = mDrawerTitle = getTitle();
        mMenuTitles = getResources().getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.container);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,myToolbar,
                R.string.drawer_open, R.string.drawer_close){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mMenuTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getFragmentManager().beginTransaction().replace(R.id.pref_fragment_container, new SearchFragment()).commit();
	}

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        switch(position){
            case 0:
                //startActivity(new Intent(this, Search.class));
                getFragmentManager().beginTransaction().replace(R.id.pref_fragment_container, new SearchFragment()).commit();
                break;
            case 1:
                //getFragmentManager().beginTransaction().replace(R.id.pref_fragment_container, new SettingFragment()).commit();
                break;
            case 2:
                getFragmentManager().beginTransaction().replace(R.id.pref_fragment_container, new SettingFragment()).commit();
                break;
            case 3:
                getFragmentManager().beginTransaction().replace(R.id.pref_fragment_container, new BaseInfoSettings()).commit();
                break;
            case 4:
                getFragmentManager().beginTransaction().replace(R.id.pref_fragment_container, new WantSettings()).commit();
                break;
            case 5:
                startActivity(new Intent(this, UbmaDrawerActivity.class));
                break;
        }
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
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
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			this.startActivity(new Intent(Search.this, SystemSettings.class));
			return true;
		}
		/*if (id == R.id.action_personal){
			this.startActivity(new Intent(Search.this, BaseInfoSettings.class));
			return true;
		}
		if (id == R.id.action_want){
			this.startActivity(new Intent(Search.this, WantSettings.class));
			return true;
		}
        if(id == R.id.action_analysis) {
            this.startActivity(new Intent(Search.this, UbmaDrawerActivity.class));
            return true;
        }*/
		/*if (id == R.id.action_contact){
			this.startActivity(new Intent(Search.this, ContactCardSettings.class));
			return true;
		}*/
		/*if (id == R.id.action_logout){
			ActivityControlCenter.SetOriginName();
			Intent intent = new Intent(Search.this, Server.class);
			this.stopService(intent);
			intent = new Intent();
			intent.setAction(ActivityControlCenter.ACTIVITY_EXIT_ACTION);
			this.sendBroadcast(intent);
			super.finish();
			//ActivityControlCenter.AllExit();
			//finish();
		}*/
		if (id == R.id.action_logout) {
			super.finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
