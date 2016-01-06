package sjtu.se.Activity.Search;

import android.app.FragmentTransaction;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import sjtu.se.Activity.Information.BaseInfoSettings;
import sjtu.se.Activity.Setting.SettingFragment;
import sjtu.se.Activity.Setting.SystemSettings;
import sjtu.se.Activity.Want.WantSettings;
import sjtu.se.Ubma.MonitorService;
import sjtu.se.Ubma.UbmaDrawerActivity;
import android.content.Intent;
import android.os.Bundle;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;

import sjtu.se.Meet.R;
import sjtu.se.UserInformation.ContactCard;
import sjtu.se.Util.InsertThread;

public class Search extends AppCompatActivity implements CreateNdefMessageCallback, OnNdefPushCompleteCallback {

	private String[] mMenuTitles;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;

    NfcAdapter mNfcAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mTitle = mDrawerTitle = getTitle();
        mMenuTitles = getResources().getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.container);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,myToolbar,
                R.string.drawer_open, R.string.drawer_close){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
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

        /*mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter != null) {
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }*/

        Intent intent = new Intent(this, MonitorService.class);
        startService(intent);
	}

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        switch(position){
            case 0:
                ft.replace(R.id.pref_fragment_container, new SearchFragment());
                break;
            case 1:
                ft.replace(R.id.pref_fragment_container, new SettingFragment());
                break;
            case 2:
                ft.replace(R.id.pref_fragment_container, new BaseInfoSettings());
                break;
            case 3:
                ft.replace(R.id.pref_fragment_container, new WantSettings());
                break;
            case 4:
                startActivity(new Intent(this, UbmaDrawerActivity.class));
                break;
        }
        /*if(position != 4) {
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
        }*/
        ft.commit();
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
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    @Override
	protected void onResume(){
		super.onResume();

		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
	        processIntent(getIntent());
	    }
	}

    void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        String str = new String(msg.getRecords()[0].getPayload());
        InsertThread insert = new InsertThread(str,this);
        if(insert.status){
            insert.start();

            Toast tst = Toast.makeText(this, "名片已经接收！", Toast.LENGTH_LONG);
            tst.setGravity(Gravity.CENTER | Gravity.TOP, 0, 240);
            tst.show();
        }
        else{
            Toast tst = Toast.makeText(this, "名片接收失败。", Toast.LENGTH_LONG);
            tst.setGravity(Gravity.CENTER | Gravity.TOP, 0, 240);
            tst.show();
        }
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
		// TODO Auto-generated method stub
        /*Toast tst = Toast.makeText(this, "名片已经发送！", Toast.LENGTH_LONG);
        tst.setGravity(Gravity.CENTER | Gravity.TOP, 0, 240);
        tst.show();*/
	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		// TODO Auto-generated method stub
        ContactCard card = new ContactCard();
        card.setContactCard(this);
		NdefMessage msg = new NdefMessage(NdefRecord.createMime("application/sjtu.se.Meet", card.toString().getBytes())
         /**
          * The Android Application Record (AAR) is commented out. When a device
          * receives a push with an AAR in it, the application specified in the AAR
          * is guaranteed to run. The AAR overrides the tag dispatch system.
          * You can add it back in to guarantee that this
          * activity starts when receiving a beamed message. For now, this code
          * uses the tag dispatch system.
          */
        );
        return msg;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
        if(keyCode == KeyEvent.KEYCODE_BACK){
            mDrawerList.setItemChecked(0, true);
            setTitle(mMenuTitles[0]);
            mDrawerLayout.closeDrawer(mDrawerList);
            getFragmentManager().beginTransaction().replace(R.id.pref_fragment_container, new SearchFragment()).commit();
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
