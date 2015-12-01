package sjtu.se.Activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

//程治谦
//import sjtu.se.service.Server;
//import sjtu.se.Activity.ContactCard.ContactCardSettings;
import sjtu.se.Activity.ContactCard.ContactCardSettings;
import sjtu.se.Activity.Information.BaseInfoSettings;
import sjtu.se.Activity.Information.ShowInformation;
import sjtu.se.Activity.Setting.SystemSettings;
import sjtu.se.Activity.Want.WantSettings;
import sjtu.se.Util.Format;
import sjtu.se.Util.Match;
import sjtu.se.UserInformation.Information;
import sjtu.se.UserInformation.Want;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.KeyEvent;

import com.example.bluetoothtry.R;

public class Search extends Activity {

	private static final int REQUEST_FOR_ENABLE = 1;

    private int CMD = 0;

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
    private Handler handler;
    private IntentFilter intentFilter;

	public BluetoothAdapter mBluetoothAdapter;
	public DevBluetoothAdapter DeviceListAdapter;
	public DevBluetoothAdapter HistoryDevListAdapter;
	public DevBluetoothAdapter RecommendDevListAdapter;

	private ListView DeviceList;
	private ListView HistoryDeviceList;
	private ListView RecommendDeviceList;
	private ArrayList<DevBluetooth> OldRecommendList;

	static class ViewHolder{
		public TextView address;
		public TextView information;
	};

	private class DevBluetooth{
		public String Address;
		public String Information;
		public Information Info;
		public Date FoundTime;
		public DevBluetooth(String addr, String info, Information i){
			Address = addr;
			Information = info;
			Info = new Information(i);
			FoundTime = new Date();
		};

		@Override
		public boolean equals(Object obj) {
			DevBluetooth tmp = (DevBluetooth)obj;
			return (this.Address.equals(tmp.Address) && this.Information.equals(tmp.Information));
		};
	};

	public class DevBluetoothAdapter extends BaseAdapter{

		private LayoutInflater mInflater = null;
		private ArrayList<DevBluetooth> lst;

		public DevBluetoothAdapter(Context context, ArrayList<DevBluetooth> l){
			this.lst = l;
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return lst.size();
		}

		@Override
		public Object getItem(int position) {
			return lst.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public ArrayList<DevBluetooth> getList(){
			return lst;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.device_list_item, null);
				holder.address = (TextView)convertView.findViewById(R.id.Address);
				holder.information = (TextView)convertView.findViewById(R.id.Information);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder)convertView.getTag();
			}
			holder.address.setText(lst.get(position).Info.baseinfo.Nick);
			holder.information.setText(lst.get(position).Address);
			return convertView;
		}

		public void add(String Addr, String Info, Information in){
			int size = lst.size();
			for (int i=0; i<size;i++){
				if (Addr.equals(lst.get(i).Address)){
					lst.set(i, new DevBluetooth(Addr, Info, in));
					this.notifyDataSetChanged();
					return;
				}
			}
			lst.add(new DevBluetooth(Addr, Info, in));
			this.notifyDataSetChanged();
		}

		public void reset(){
			int size = lst.size();
			for (int i=0;i<size;i++){
				DevBluetooth dev = lst.get(i);
				if ((new Date()).getTime() - dev.FoundTime.getTime() >= 10000){
					lst.remove(i);
					size--;
					i--;
				}
			}
			this.notifyDataSetChanged();
		}
	};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		ctx = this;
		overt_user = new Information();
		full_user  = new Information();

		DeviceList = (ListView)findViewById(R.id.DeviceList);
		HistoryDeviceList = (ListView)findViewById(R.id.HistoryDeviceList);
		RecommendDeviceList = (ListView)findViewById(R.id.RecommendDeviceList);

		setDeviceListClick();
		setRecommendDeviceListClick();
		setHistoryDeviceListClick();

		DeviceListAdapter = new DevBluetoothAdapter(this, new ArrayList<DevBluetooth>());
		HistoryDevListAdapter = new DevBluetoothAdapter(this, new ArrayList<DevBluetooth>());
		HistoryDeviceList.setVisibility(View.GONE);
		RecommendDevListAdapter = new DevBluetoothAdapter(this, new ArrayList<DevBluetooth>());
		RecommendDeviceList.setVisibility(View.GONE);

		OldRecommendList = new ArrayList<DevBluetooth>();

		DeviceList.setAdapter(DeviceListAdapter);
		HistoryDeviceList.setAdapter(HistoryDevListAdapter);
		RecommendDeviceList.setAdapter(RecommendDevListAdapter);

		((Button)findViewById(R.id.SearchShowDev)).setBackground(ActivityControlCenter.dayClicked);
		((Button)findViewById(R.id.FindShowDev)).setBackground(ActivityControlCenter.dayNormal);
		((Button)findViewById(R.id.FindShowDev)).setTextColor(Color.WHITE);
		((Button)findViewById(R.id.RecommendShowDev)).setBackground(ActivityControlCenter.dayNormal);
		((Button)findViewById(R.id.RecommendShowDev)).setTextColor(Color.WHITE);

		intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		intentFilter.addAction(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED);
		intentFilter.addAction(ActivityControlCenter.ACTIVITY_EXIT_ACTION);
		intentFilter.addAction(ActivityControlCenter.ACTION_LAUNCHED);
		this.registerReceiver(receiver, intentFilter);

		OpenBluetooth();
		Rename();

		// 启动服务
		//程治谦
		//Intent intent = new Intent(Search.this, Server.class);
		//startService(intent);

        /*ContactCard contact = new ContactCard();
		contact.name = "Paler";
		contact.phone = "13312345678";
		contact.email = "test@sjtu.edu.cn";
		ContactInterface.insert(contact, ctx);*/

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch(msg.what){
					case 0:{
						DeviceListAdapter.reset();
						RecommendDevListAdapter.reset();
						recommendNotify(Search.getAddition(OldRecommendList, RecommendDevListAdapter.getList()));
						//System.out.println("handle message~~~~~~~" + OldRecommendList.size() + " " + RecommendDevListAdapter.getList().size());
						OldRecommendList = (ArrayList<DevBluetooth>) RecommendDevListAdapter.getList().clone();
						Rename();
						doDiscovery();
						Message message = this.obtainMessage(CMD);
						this.sendMessageDelayed(message, 5000);
						break;
					}
					case 1:{
						recreate();
						break;
					}
					case 2:{
						mBluetoothAdapter.cancelDiscovery();
						break;
					}
				}
			}
		};
		Message message = handler.obtainMessage(0);
		handler.sendMessageDelayed(message, 0);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(BluetoothDevice.ACTION_FOUND)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				//String btname = device.getName();
				String addr = device.getAddress();
				String btname = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
				//String btname = mBluetoothAdapter.getRemoteDevice(addr).getName();
				System.out.println("********New*********");
				System.out.println("new -> " + btname);
				//System.out.println("length : " + btname.getBytes().length);
				System.out.println("********End*********");

				Information info = Format.DeFormat(btname);

				if (info != null){
					DeviceListAdapter.add(device.getAddress(), device.getName(), info);
					if (Match.isInterest(info, full_user) ||
							Match.isWanted(info, want1) ||
							Match.isWanted(info, want2) ||
							Match.isWanted(info, want3) ||
							Match.isWanted(info, want4) ||
							Match.isWanted(info, want5) ||
							Match.isWanted(info, want6) ||
							Match.isWanted(info, want7) ||
							Match.isWanted(info, want8)){
						RecommendDevListAdapter.add(device.getAddress(), device.getName(), info);
					}
				}
			}
			if (action.equals(ActivityControlCenter.ACTIVITY_EXIT_ACTION)){
				finish();
			}
			//程治谦
			/*if (action.equals(ActivityControlCenter.ACTION_LAUNCHED)){
				CMD = 2;
				Bundle bundle = new Bundle();
				bundle.putParcelable("information", overt_user);
				Intent intent2 = new Intent(Search.this, ChatPlatform.class);
				intent2.putExtra("address", intent.getStringExtra("address"));
				intent2.putExtra("isclient", false);
				intent2.putExtras(bundle);
				//System.out.println("here");
				ctx.startActivity(intent2);
			}*/
		}
	};

	private void OpenBluetooth (){
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null){
			Toast.makeText(this, "本机没有找到蓝牙设备或驱动！", Toast.LENGTH_SHORT).show();
			finish();
		}
		if (!mBluetoothAdapter.isEnabled()){
			Intent Intentenabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(Intentenabler,REQUEST_FOR_ENABLE);
		}
        ActivityControlCenter.savedName = mBluetoothAdapter.getName();
		ActivityControlCenter.savedBTAdapter = mBluetoothAdapter;
	}

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case REQUEST_FOR_ENABLE:{
                switch (resultCode){
                    case RESULT_OK:{
                        ActivityControlCenter.savedName = mBluetoothAdapter.getName();
                        Rename();
                        Toast.makeText(this, "蓝牙已经开启", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case RESULT_CANCELED:{
                        Toast.makeText(this, "不允许蓝牙开启", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    }
                }
            }
        }
    }

	private void Rename(){
		updateBaseInfo();
		updateContactInfo();
		updateEducationInfo();
		updateHobbyInfo();

		String newname = Format.DoFormat(overt_user);
        mBluetoothAdapter.setName(newname);
	}

	protected void doDiscovery(){
		if (mBluetoothAdapter.isDiscovering()){
			mBluetoothAdapter.cancelDiscovery();
		}
		mBluetoothAdapter.startDiscovery();
	}

	protected void recommendNotify(ArrayList<DevBluetooth> change){
		if (change.isEmpty())
			return;
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

		int icon = R.drawable.icon5;
		CharSequence tickerText = "附近有你可能感兴趣的人哦";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);

		Context context = getApplicationContext();
		CharSequence contentTitle = "你可能感兴趣的人，快去看看吧";

		String name = "";
		for (DevBluetooth dev : change){
			name += dev.Info.baseinfo.Nick + ", ";
		}
        name =name.substring(0,name.length()-2);
		CharSequence contentText = name;

		Intent notificationIntent = new Intent(this, Search.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

		mNotificationManager.notify(1, notification);
		Shake();
	}

	protected void Shake(){
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long [] pattern = {100,400,100,400,100,400};
		vibrator.vibrate(pattern, -1);
		//vibrator.cancel();
	}

	public static ArrayList<DevBluetooth> getAddition(ArrayList<DevBluetooth> formal, ArrayList<DevBluetooth> later){
		ArrayList<DevBluetooth> ret;
		ret = new ArrayList<DevBluetooth>();
		if (formal.isEmpty() && later.isEmpty()){
			return ret;
		}

        for (DevBluetooth dev : later) {
            int status = 0;
            for (DevBluetooth old : formal) {
                if (old.Address.equals(dev.Address))
                    status = 1;
            }
            if (status == 0)
                ret.add(dev);
        }

		return ret;
	}

	protected void getPairedDevice(){
		Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
		for (BluetoothDevice device : devices) {
			String btname = device.getName();
			Information info = Format.DeFormat(btname);
			if (info != null)
				HistoryDevListAdapter.add(device.getAddress(), device.getName(), info);
		}
	}

	@Override
	protected void onResume(){
		super.onResume();
		if (ActivityControlCenter.PERSONAL_INFO_MAY_CHANGED){
			Rename();
			ActivityControlCenter.PERSONAL_INFO_MAY_CHANGED = false;
		}
		if (ActivityControlCenter.WANTS_MAY_CHANGED){
			updateWants();
			ActivityControlCenter.WANTS_MAY_CHANGED = false;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Make sure we're not doing discovery anymore
		if (mBluetoothAdapter != null) {
			mBluetoothAdapter.cancelDiscovery();
		}
		// Unregister broadcast listeners
		this.unregisterReceiver(receiver);
	}

	private void updateBaseInfo(){
		SharedPreferences sp = getSharedPreferences(ActivityControlCenter.PERSONAL_BASE_INFO, 0);
		// base information
		if (sp.getBoolean(ActivityControlCenter.KEY_NAME_OVERT, false))
			overt_user.baseinfo.Name = sp.getString(ActivityControlCenter.KEY_NAME, "");
		else
			overt_user.baseinfo.Name = "";
		full_user.baseinfo.Name = sp.getString(ActivityControlCenter.KEY_NAME, "");

		overt_user.baseinfo.Nick = sp.getString(ActivityControlCenter.KEY_NICK, "");
		full_user.baseinfo.Nick = sp.getString(ActivityControlCenter.KEY_NICK, "");

		if (sp.getBoolean(ActivityControlCenter.KEY_GENDER_OVERT, false))
			overt_user.baseinfo.Gender = sp.getString(ActivityControlCenter.KEY_GENDER, "");
		else
			overt_user.baseinfo.Gender = "";
		full_user.baseinfo.Gender = sp.getString(ActivityControlCenter.KEY_GENDER, "");

		if (sp.getBoolean(ActivityControlCenter.KEY_BIRTHDAY_OVERT, false))
			overt_user.baseinfo.BirthDay = sp.getString(ActivityControlCenter.KEY_BIRTHDAY, "");
		else
			overt_user.baseinfo.BirthDay = "";
		full_user.baseinfo.BirthDay = sp.getString(ActivityControlCenter.KEY_BIRTHDAY, "");

		if (sp.getBoolean(ActivityControlCenter.KEY_HOMELAND_OVERT, false))
			overt_user.baseinfo.Homeland = sp.getString(ActivityControlCenter.KEY_HOMELAND, "");
		else
			overt_user.baseinfo.Homeland = "";
		full_user.baseinfo.Homeland = sp.getString(ActivityControlCenter.KEY_HOMELAND, "");

		if (sp.getBoolean(ActivityControlCenter.KEY_LOCATION_OVERT, false))
			overt_user.baseinfo.Location = sp.getString(ActivityControlCenter.KEY_LOCATION, "");
		else
			overt_user.baseinfo.Location = "";
		full_user.baseinfo.Location = sp.getString(ActivityControlCenter.KEY_LOCATION, "");


		if (sp.getBoolean(ActivityControlCenter.KEY_KEYWORDS_OVERT, false)){
			overt_user.keywords = sp.getString(ActivityControlCenter.KEY_KEYWORDS, "");
		}
		else
			overt_user.keywords = "";

        full_user.keywords = sp.getString(ActivityControlCenter.KEY_KEYWORDS, "");

	}

	private void updateContactInfo(){
		SharedPreferences sp = getSharedPreferences(ActivityControlCenter.PERSONAL_CONTACT_INFO, 0);

		if (sp.getBoolean(ActivityControlCenter.KEY_PHONE_OVERT, false))
			overt_user.contactinfo.Phone = sp.getString(ActivityControlCenter.KEY_PHONE, "");
		else
			overt_user.contactinfo.Phone = "";
		full_user.contactinfo.Phone = sp.getString(ActivityControlCenter.KEY_PHONE, "");

		if (sp.getBoolean(ActivityControlCenter.KEY_QQ_OVERT, false))
			overt_user.contactinfo.QQ = sp.getString(ActivityControlCenter.KEY_QQ, "");
		else
			overt_user.contactinfo.QQ = "";
		full_user.contactinfo.QQ = sp.getString(ActivityControlCenter.KEY_QQ, "");

		if (sp.getBoolean(ActivityControlCenter.KEY_EMAIL_OVERT, false))
			overt_user.contactinfo.E_Mail = sp.getString(ActivityControlCenter.KEY_EMAIL, "");
		else
			overt_user.contactinfo.E_Mail = "";
		full_user.contactinfo.E_Mail = sp.getString(ActivityControlCenter.KEY_EMAIL, "");

		if (sp.getBoolean(ActivityControlCenter.KEY_WEIBO_OVERT, false))
			overt_user.contactinfo.Weibo = sp.getString(ActivityControlCenter.KEY_WEIBO, "");
		else
			overt_user.contactinfo.Weibo = "";

		if (sp.getBoolean(ActivityControlCenter.KEY_SOCIALNET_OVERT, false))
			overt_user.contactinfo.Social_Network = sp.getString(ActivityControlCenter.KEY_SOCIALNET, "");
		else
			overt_user.contactinfo.Social_Network  = "";
	}

	private void updateEducationInfo(){
		SharedPreferences sp = getSharedPreferences(ActivityControlCenter.PERSONAL_EDUCATION_INFO, 0);

		if (sp.getBoolean(ActivityControlCenter.KEY_COLLEGE_OVERT, false))
			overt_user.edu.College = sp.getString(ActivityControlCenter.KEY_COLLEGE, "");
		else
			overt_user.edu.College = "";
		full_user.edu.College = sp.getString(ActivityControlCenter.KEY_COLLEGE, "");

		if (sp.getBoolean(ActivityControlCenter.KEY_HIGH_OVERT, false))
			overt_user.edu.High_School = sp.getString(ActivityControlCenter.KEY_HIGH, "");
		else
			overt_user.edu.High_School = "";
		full_user.edu.High_School = sp.getString(ActivityControlCenter.KEY_HIGH, "");

		if (sp.getBoolean(ActivityControlCenter.KEY_MIDDLE_OVERT, false))
			overt_user.edu.Middle_School = sp.getString(ActivityControlCenter.KEY_MIDDLE, "");
		else
			overt_user.edu.Middle_School = "";
		full_user.edu.Middle_School = sp.getString(ActivityControlCenter.KEY_MIDDLE, "");

		if (sp.getBoolean(ActivityControlCenter.KEY_PRIMARY_OVERT, false))
			overt_user.edu.Primary_School = sp.getString(ActivityControlCenter.KEY_PRIMARY, "");
		else
			overt_user.edu.Primary_School = "";
		full_user.edu.Primary_School = sp.getString(ActivityControlCenter.KEY_PRIMARY, "");
	}

	private void updateHobbyInfo(){
		SharedPreferences sp = getSharedPreferences(ActivityControlCenter.PERSONAL_HOBBY_INFO, 0);

		if (sp.getBoolean(ActivityControlCenter.KEY_GAME_OVERT, false))
			overt_user.hobby.Game = sp.getString(ActivityControlCenter.KEY_GAME, "");
		else
			overt_user.hobby.Game = "";

		if (sp.getBoolean(ActivityControlCenter.KEY_SPORT_OVERT, false))
			overt_user.hobby.Sport = sp.getString(ActivityControlCenter.KEY_SPORT, "");
		else
			overt_user.hobby.Sport = "";

		if (sp.getBoolean(ActivityControlCenter.KEY_COMIC_OVERT, false))
			overt_user.hobby.Comic = sp.getString(ActivityControlCenter.KEY_COMIC, "");
		else
			overt_user.hobby.Comic = "";

		if (sp.getBoolean(ActivityControlCenter.KEY_MUSIC_OVERT, false))
			overt_user.hobby.Music = sp.getString(ActivityControlCenter.KEY_MUSIC, "");
		else
			overt_user.hobby.Music = "";

		if (sp.getBoolean(ActivityControlCenter.KEY_BOOKS_OVERT, false))
			overt_user.hobby.Books = sp.getString(ActivityControlCenter.KEY_BOOKS, "");
		else
			overt_user.hobby.Books = "";

		if (sp.getBoolean(ActivityControlCenter.KEY_MOVIE_OVERT, false))
			overt_user.hobby.Movie = sp.getString(ActivityControlCenter.KEY_MOVIE, "");
		else
			overt_user.hobby.Movie = "";

		if (sp.getBoolean(ActivityControlCenter.KEY_OTHER_OVERT, false))
			overt_user.hobby.Other = sp.getString(ActivityControlCenter.KEY_OTHER, "");
		else
			overt_user.hobby.Other = "";

		full_user.hobby.Game = sp.getString(ActivityControlCenter.KEY_GAME, "");
		full_user.hobby.Sport = sp.getString(ActivityControlCenter.KEY_SPORT, "");
		full_user.hobby.Comic = sp.getString(ActivityControlCenter.KEY_COMIC, "");
		full_user.hobby.Music = sp.getString(ActivityControlCenter.KEY_MUSIC, "");
		full_user.hobby.Books = sp.getString(ActivityControlCenter.KEY_BOOKS, "");
		full_user.hobby.Movie = sp.getString(ActivityControlCenter.KEY_MOVIE, "");
		full_user.hobby.Other = sp.getString(ActivityControlCenter.KEY_OTHER, "");
	}

	private void updateWants(){
		SharedPreferences sp = getSharedPreferences(ActivityControlCenter.WANT_SETTINGS,0);
		String str;
		str = sp.getString(ActivityControlCenter.KEY_WANT_1, "");
		if (!str.equals(""))
			want1 = Want.parseWant(str);
		else
			want1 = new Want();

		str = sp.getString(ActivityControlCenter.KEY_WANT_2, "");
		if (!str.equals(""))
			want2 = Want.parseWant(str);
		else
			want2 = new Want();

		str = sp.getString(ActivityControlCenter.KEY_WANT_3, "");
		if (!str.equals(""))
			want3 = Want.parseWant(str);
		else
			want3 = new Want();

		str = sp.getString(ActivityControlCenter.KEY_WANT_4, "");
		if (!str.equals(""))
			want4 = Want.parseWant(str);
		else
			want4 = new Want();

		str = sp.getString(ActivityControlCenter.KEY_WANT_5, "");
		if (!str.equals(""))
			want5 = Want.parseWant(str);
		else
			want5 = new Want();

		str = sp.getString(ActivityControlCenter.KEY_WANT_6, "");
		if (!str.equals(""))
			want6 = Want.parseWant(str);
		else
			want6 = new Want();

		str = sp.getString(ActivityControlCenter.KEY_WANT_7, "");
		if (!str.equals(""))
			want7 = Want.parseWant(str);
		else
			want7 = new Want();

		str = sp.getString(ActivityControlCenter.KEY_WANT_8, "");
		if (!str.equals(""))
			want8 = Want.parseWant(str);
		else
			want8 = new Want();
	}

	@SuppressLint("NewApi")
	public void ShowDeviceList(View view){
		((Button)findViewById(R.id.SearchShowDev)).setBackground(ActivityControlCenter.dayClicked);
		((Button)findViewById(R.id.SearchShowDev)).setTextColor(Color.BLACK);
		((Button)findViewById(R.id.FindShowDev)).setBackground(ActivityControlCenter.dayNormal);
		((Button)findViewById(R.id.FindShowDev)).setTextColor(Color.WHITE);
		((Button)findViewById(R.id.RecommendShowDev)).setBackground(ActivityControlCenter.dayNormal);
		((Button)findViewById(R.id.RecommendShowDev)).setTextColor(Color.WHITE);
		HistoryDeviceList.setVisibility(View.GONE);
		RecommendDeviceList.setVisibility(View.GONE);
		DeviceList.setVisibility(View.VISIBLE);
	}

	@SuppressLint("NewApi")
	public void ShowRecommendDeviceList(View view){
		((Button)findViewById(R.id.RecommendShowDev)).setBackground(ActivityControlCenter.dayClicked);
		((Button)findViewById(R.id.RecommendShowDev)).setTextColor(Color.BLACK);
		((Button)findViewById(R.id.SearchShowDev)).setBackground(ActivityControlCenter.dayNormal);
		((Button)findViewById(R.id.SearchShowDev)).setTextColor(Color.WHITE);
		((Button)findViewById(R.id.FindShowDev)).setBackground(ActivityControlCenter.dayNormal);
		((Button)findViewById(R.id.FindShowDev)).setTextColor(Color.WHITE);
		DeviceList.setVisibility(View.GONE);
		HistoryDeviceList.setVisibility(View.GONE);
		RecommendDeviceList.setVisibility(View.VISIBLE);
	}

	@SuppressLint("NewApi")
	public void ShowHistoryDeviceList(View view)
	{
		((Button)findViewById(R.id.FindShowDev)).setBackground(ActivityControlCenter.dayClicked);
		((Button)findViewById(R.id.FindShowDev)).setTextColor(Color.BLACK);
		((Button)findViewById(R.id.SearchShowDev)).setBackground(ActivityControlCenter.dayNormal);
		((Button)findViewById(R.id.SearchShowDev)).setTextColor(Color.WHITE);
		((Button)findViewById(R.id.RecommendShowDev)).setBackground(ActivityControlCenter.dayNormal);
		((Button)findViewById(R.id.RecommendShowDev)).setTextColor(Color.WHITE);
		DeviceList.setVisibility(View.GONE);
		RecommendDeviceList.setVisibility(View.GONE);
		HistoryDeviceList.setVisibility(View.VISIBLE);
		getPairedDevice();
	}

	//程治谦
	private void setDeviceListClick(){
		DeviceList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String addr = ((DevBluetooth) DeviceListAdapter.getItem(position)).Address;
                SharedPreferences sp = getSharedPreferences(ActivityControlCenter.DETAIL_INFORMATION, 0);
                String res = sp.getString(addr, "Not found");
                if (!res.equals("Not found")) {
                    Information info = Information.parseInformation(res);
                    if (info != null) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("information", info);
                        Intent intent = new Intent(Search.this, ShowInformation.class);
                        intent.putExtras(bundle);
                        ctx.startActivity(intent);
                        return;
                    }
                }
                Bundle bundle = new Bundle();
                Information info = new Information(((DevBluetooth) DeviceListAdapter.getItem(position)).Info);
                bundle.putParcelable("information", info);
                Intent intent = new Intent(Search.this, ShowInformation.class);
                intent.putExtras(bundle);
                ctx.startActivity(intent);
			}
		});

		/*DeviceList.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new Builder(ctx);
				String nick = ((DevBluetooth)DeviceListAdapter.getItem(position)).Info.baseinfo.Nick;
				final String address = ((DevBluetooth)DeviceListAdapter.getItem(position)).Address;
				builder.setMessage("确定与"+ address +"建立连接么？");
				builder.setTitle("提示");
				builder.setPositiveButton("确定", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						CMD = 2;
						
						Bundle bundle = new Bundle();
						bundle.putParcelable("information", overt_user);
						Intent intent = new Intent(Search.this, ChatPlatform.class);
						intent.putExtra("address", address);
						intent.putExtra("isclient", true);
						intent.putExtras(bundle);
						ctx.startActivity(intent);
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("取消", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create().show();
				return true;
			}
		});*/
    }

    private void setRecommendDeviceListClick() {
        RecommendDeviceList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String addr = ((DevBluetooth) DeviceListAdapter.getItem(position)).Address;
                SharedPreferences sp = getSharedPreferences(ActivityControlCenter.DETAIL_INFORMATION, 0);
                String res = sp.getString(addr, "Not found");
                if (!res.equals("Not found")) {
                    Information info = Information.parseInformation(res);
                    if (info != null) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("information", info);
                        Intent intent = new Intent(Search.this, ShowInformation.class);
                        intent.putExtras(bundle);
                        ctx.startActivity(intent);
                        return;
                    }
                }
                Bundle bundle = new Bundle();
                Information info = new Information(((DevBluetooth) RecommendDevListAdapter.getItem(position)).Info);
                bundle.putParcelable("information", info);
				Intent intent=new Intent(Search.this, ShowInformation.class); 
				intent.putExtras(bundle);
				ctx.startActivity(intent);	
			}
		});
		
		/*RecommendDeviceList.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new Builder(ctx);
				String nick = ((DevBluetooth)RecommendDevListAdapter.getItem(position)).Info.baseinfo.Nick;
				final String address = ((DevBluetooth)RecommendDevListAdapter.getItem(position)).Address;
				builder.setMessage("确定与"+ address +"建立连接么？");
				builder.setTitle("提示");
				builder.setPositiveButton("确定", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						CMD = 2;
						
						Bundle bundle = new Bundle();
						bundle.putParcelable("information", overt_user);
						Intent intent = new Intent(Search.this, ChatPlatform.class);
						intent.putExtra("address", address);
						intent.putExtra("isclient", true);
						intent.putExtras(bundle);
						ctx.startActivity(intent);
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("取消", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create().show();
				return true;
			}
		});*/
    }

    private void setHistoryDeviceListClick() {
        HistoryDeviceList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String addr = ((DevBluetooth) HistoryDevListAdapter.getItem(position)).Address;
                SharedPreferences sp = getSharedPreferences(ActivityControlCenter.DETAIL_INFORMATION, 0);
                String res = sp.getString(addr, "Not found");
                if (!res.equals("Not found")) {
                    Information info = Information.parseInformation(res);
                    if (info != null) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("information", info);
                        Intent intent = new Intent(Search.this, ShowInformation.class);
                        intent.putExtras(bundle);
                        ctx.startActivity(intent);
                        return;
                    }
                }
                Bundle bundle = new Bundle();
                Information info = new Information(((DevBluetooth) HistoryDevListAdapter.getItem(position)).Info);
                bundle.putParcelable("information", info);
				Intent intent=new Intent(Search.this, ShowInformation.class); 
				intent.putExtras(bundle);
				ctx.startActivity(intent);
            }

        });
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
		if (id == R.id.action_personal){
			this.startActivity(new Intent(Search.this, BaseInfoSettings.class));
			return true;
		}
		if (id == R.id.action_want){
			this.startActivity(new Intent(Search.this, WantSettings.class));
			return true;
		}
		if (id == R.id.action_contact){
			this.startActivity(new Intent(Search.this, ContactCardSettings.class));
			return true;
		}
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
		return super.onOptionsItemSelected(item);
	}
}
