//package sjtu.se.Activity;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.UUID;
//
//import android.view.*;
//import sjtu.se.Activity.ContactCard.ContactCardSettings;
//import sjtu.se.Activity.Information.BaseInfoSettings;
//import sjtu.se.Util.ContactInterface;
//import sjtu.se.Activity.Information.ShowInformation;
//import sjtu.se.UserInformation.ContactCard;
//import sjtu.se.UserInformation.Information;
//
//import com.example.bluetoothtry.R;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.content.DialogInterface.OnClickListener;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.os.Parcelable;
//import android.widget.BaseAdapter;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class ChatPlatform extends Activity {
//
//	private ListView conversation;
//	private EditText input;
//	private String address;
//	private Context ctx;
//
//	private Information overt_user;
//
//	private SharedPreferences detailInformation;
//
//	public static int PREFIX_LENGTH = 8;
//	public static String PREFIX_CONTENT = ":_ctnt_:";
//	public static String PREFIX_CONTACT = ":_ctct_:";
//	public static String PREFIX_INFORMATION = ":_info_:";
//	public static String PREFIX_SYSTEM = ":_sysm_:";
//
//	private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//	private clientThread clientConnectThread = null;
//	private BluetoothDevice device = null;
//	private BluetoothSocket socket = null;
//	private ContentsAdapter mcontentsAdapter;
//	private readThread mreadThread = null;
//	private boolean isClient = true;
//
//	private String theMsg = "";
//
//	private Handler handler = new Handler(){
//		@Override
//		public void handleMessage(Message msg) {
//			switch(msg.what){
//			case 0:{
//				String tmp = (String)msg.obj;
//				String prefix = getPrefix(tmp);
//
//				if (prefix.equals(ChatPlatform.PREFIX_SYSTEM)){
//					mcontentsAdapter.add("与"+ address +"连接完成", false, "系统信息");
//		  	  		mcontentsAdapter.notifyDataSetChanged();
//				}
//				else if(prefix.equals(ChatPlatform.PREFIX_CONTACT)){
//					final String str = unpack(tmp);
//					AlertDialog.Builder builder = new Builder(ctx);
//					builder.setMessage("是否接受来自"+ address +"的名片么？");
//					builder.setTitle("提示");
//					builder.setPositiveButton("确定", new OnClickListener(){
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							ContactCard contact = ContactCard.parseContactCard(str);
//							ContactInterface.insert(contact, ctx);
//							dialog.dismiss();
//							Toast.makeText(getApplicationContext(),"添加名片成功", Toast.LENGTH_SHORT).show();
//						}
//					});
//					builder.setNegativeButton("取消", new OnClickListener(){
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							dialog.dismiss();
//						}
//					});
//					builder.create().show();
//				}
//				else if(prefix.equals(ChatPlatform.PREFIX_CONTENT)){
//					String str = unpack(tmp);
//					mcontentsAdapter.add(str, false);
//		  	  		mcontentsAdapter.notifyDataSetChanged();
//				}
//				else if(prefix.equals(ChatPlatform.PREFIX_INFORMATION)){
//					String str = unpack(tmp);
//					SharedPreferences.Editor editor = detailInformation.edit();
//					editor.putString(address, str);
//					editor.commit();
//				}
//				else{
//					return;
//				}
//
//				/*if (tmp.equals(""+(char)1)){
//					mcontentsAdapter.add("与"+ address +"连接完成", false, "系统信息");
//				}
//				else if (!unpack(tmp).equals("")) {
//					final String str = unpack(tmp);
//					AlertDialog.Builder builder = new Builder(ctx);
//					builder.setMessage("是否接受来自"+ address +"的名片么？");
//					builder.setTitle("提示");
//					builder.setPositiveButton("确定", new OnClickListener(){
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							ContactCard contact = ContactCard.parseContactCard(str);
//							ContactInterface.insert(contact, ctx);
//							dialog.dismiss();
//						}
//					});
//					builder.setNegativeButton("取消", new OnClickListener(){
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							dialog.dismiss();
//						}
//					});
//					builder.create().show();
//				}
//				else{
//					mcontentsAdapter.add((String)msg.obj, false);
//				}
//	  	  		mcontentsAdapter.notifyDataSetChanged();*/
//			}
//			}
//		}
//	};
//
//	private class Contents{
//		public String strings;
//		public String mtime;
//		public boolean me;
//		public String extra;
//		public Contents(String s, String t, boolean m){
//			strings = s;
//			mtime = t;
//			me = m;
//			extra = "";
//		};
//		public Contents(String s, String t, boolean m, String e){
//			strings = s;
//			mtime = t;
//			me = m;
//			extra = e;
//		};
//
//		@Override
//		public boolean equals(Object obj) {
//			Contents tmp = (Contents)obj;
//			return (strings.equals(tmp.strings) && (me == tmp.me) && (mtime.equals(mtime)));
//		};
//	};
//
//	static class ViewHolder{
//		public TextView mwho;
//		public TextView mcontent;
//		public TextView mtime;
//	};
//
//	public class ContentsAdapter extends BaseAdapter{
//		private LayoutInflater mInflater = null;
//		private ArrayList<Contents> lst;
//
//		public ContentsAdapter(Context context, ArrayList<Contents> l){
//			this.lst = l;
//			this.mInflater = LayoutInflater.from(context);
//		}
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return lst.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return lst.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			boolean isMe = lst.get(position).me;
//			ViewHolder holder = null;
//			//if (convertView == null){
//				holder = new ViewHolder();
//				if (!isMe){
//					convertView = mInflater.inflate(R.layout.conversation_item, null);
//					holder.mcontent = (TextView)convertView.findViewById(R.id.conversationContent);
//					holder.mtime = (TextView)convertView.findViewById(R.id.conversationTime);
//					holder.mwho = (TextView)convertView.findViewById(R.id.conversationWho);
//				}
//				else{
//					convertView = mInflater.inflate(R.layout.conversation_item_right, null);
//					holder.mcontent = (TextView)convertView.findViewById(R.id.conversationContentRight);
//					holder.mtime = (TextView)convertView.findViewById(R.id.conversationTimeRight);
//					holder.mwho = (TextView)convertView.findViewById(R.id.conversationWhoRight);
//				}
//				convertView.setTag(holder);
//			//}
//			//else{
//				//holder = (ViewHolder)convertView.getTag();
//			//}
//			holder.mcontent.setText(lst.get(position).strings);
//			holder.mtime.setText(lst.get(position).mtime);
//			if (lst.get(position).me)
//				holder.mwho.setText("我");
//			else
//				holder.mwho.setText(address);
//			String ex = lst.get(position).extra;
//			if (!ex.equals(""))
//				holder.mwho.setText(ex);
//			return convertView;
//		}
//
//		public ArrayList<Contents> getList(){
//			return lst;
//		}
//
//		public void add(String str, boolean m){
//			Date now = new Date();
//			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//			String timeTmp = timeFormat.format(now);
//			lst.add(new Contents(str, timeTmp, m));
//			//this.notifyDataSetChanged();
//		}
//
//		public void add(String str, boolean m, String extra){
//			Date now = new Date();
//			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//			String timeTmp = timeFormat.format(now);
//			lst.add(new Contents(str, timeTmp, m, extra));
//			//this.notifyDataSetChanged();
//		}
//	}
//
//	//开启客户端
//	private class clientThread extends Thread {
//		public void run() {
//			try {
//				//创建一个Socket连接：只需要服务器在注册时的UUID号
//				socket = device.createRfcommSocketToServiceRecord(UUID.fromString(ActivityControlCenter.MUUID));
//				//连接
//				mcontentsAdapter.add("正在尝试与"+ address +"连接...", false, "系统信息");
//				socket.connect();
//				sendMessage(pack(overt_user.toString(), ChatPlatform.PREFIX_INFORMATION));
//
//				//启动接受数据
//				mreadThread = new readThread();
//				mreadThread.start();
//			}
//			catch (IOException e) {
//				//mcontentsAdapter.add("与"+ address + "连接出现异常，请重新连接", false, "系统信息");
//			}
//		}
//	};
//
//	// 停止客户端连接
//	private void shutdownClient() {
//		new Thread() {
//			public void run() {
//				if(clientConnectThread!=null)
//				{
//					clientConnectThread.interrupt();
//					clientConnectThread= null;
//				}
//				if(mreadThread != null)
//				{
//					mreadThread.interrupt();
//					mreadThread = null;
//				}
//				if (socket != null) {
//					try {
//						socket.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					socket = null;
//				}
//			};
//		}.start();
//	}
//
//	//读取数据
//    private class readThread extends Thread {
//        public void run() {
//
//            byte[] buffer = new byte[1024];
//            int bytes;
//            InputStream mmInStream = null;
//
//			try {
//				mmInStream = socket.getInputStream();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//            while (true) {
//                try {
//                    // Read from the InputStream
//                    if( (bytes = mmInStream.read(buffer)) > 0 )
//                    {
//	                    byte[] buf_data = new byte[bytes];
//				    	for(int i=0; i<bytes; i++)
//				    	{
//				    		buf_data[i] = buffer[i];
//				    	}
//						String s = new String(buf_data);
//						Message msg = handler.obtainMessage(0, s);
//						handler.sendMessage(msg);
//                    }
//                } catch (IOException e) {
//                	try {
//						mmInStream.close();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//                    break;
//                }
//            }
//        }
//    };
//
//  //发送数据
//  	private void sendMessage(String msg)
//  	{
//  		System.out.println(socket);
//  		if (socket == null)
//  		{
//  			return;
//  		}
//  		try {
//  			OutputStream os = socket.getOutputStream();
//  			os.write(msg.getBytes());
//  		} catch (IOException e) {
//  			// TODO Auto-generated catch block
//  			e.printStackTrace();
//  		}
//  	}
//
//	public void doSend(View view){
//		String msg = input.getText().toString();
//		System.out.println(msg);
//		input.setText("");
//		if (isClient){
//			sendMessage(pack(msg, ChatPlatform.PREFIX_CONTENT));
//  	  		mcontentsAdapter.add(msg, true);
//  	  		mcontentsAdapter.notifyDataSetChanged();
//		}
//		else{
//			Intent intent = new Intent();
//	    	intent.setAction(ActivityControlCenter.ACTION_ACTIVITY_TO_SERVICE);
//	    	intent.putExtra("msg", pack(msg, ChatPlatform.PREFIX_CONTENT));
//	    	sendBroadcast(intent);
//	  		mcontentsAdapter.add(msg, true);
//  	  		mcontentsAdapter.notifyDataSetChanged();
//		}
//	}
//
//	private BroadcastReceiver receiver = new BroadcastReceiver(){
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//			if (action.equals(ActivityControlCenter.ACTION_SERVICE_TO_ACTIVITY)){
//				if (!isClient) {
//					String tmp = intent.getStringExtra("msg");
//					String prefix = getPrefix(tmp);
//
//					if (prefix.equals(ChatPlatform.PREFIX_SYSTEM)){
//						mcontentsAdapter.add("与"+ address +"连接完成", false, "系统信息");
//			  	  		mcontentsAdapter.notifyDataSetChanged();
//					}
//					else if(prefix.equals(ChatPlatform.PREFIX_CONTACT)){
//						final String str = unpack(tmp);
//						AlertDialog.Builder builder = new Builder(ctx);
//						builder.setMessage("是否接受来自"+ address +"的名片么？");
//						builder.setTitle("提示");
//						builder.setPositiveButton("确定", new OnClickListener(){
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								// TODO Auto-generated method stub
//								ContactCard contact = ContactCard.parseContactCard(str);
//								ContactInterface.insert(contact, ctx);
//								dialog.dismiss();
//								Toast.makeText(getApplicationContext(),"添加名片成功", Toast.LENGTH_SHORT).show();
//							}
//						});
//						builder.setNegativeButton("取消", new OnClickListener(){
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								// TODO Auto-generated method stub
//								dialog.dismiss();
//							}
//						});
//						builder.create().show();
//					}
//					else if(prefix.equals(ChatPlatform.PREFIX_CONTENT)){
//						String str = unpack(tmp);
//						mcontentsAdapter.add(str, false);
//			  	  		mcontentsAdapter.notifyDataSetChanged();
//					}
//					else if(prefix.equals(ChatPlatform.PREFIX_INFORMATION)){
//						String str = unpack(tmp);
//						SharedPreferences.Editor editor = detailInformation.edit();
//						editor.putString(address, str);
//						editor.commit();
//					}
//					else{
//						return;
//					}
//					/*String tmp = intent.getStringExtra("msg");
//					final String str = unpack(tmp);
//					if (!str.equals("")){
//						AlertDialog.Builder builder = new Builder(ctx);
//						builder.setMessage("是否接受来自"+ address +"的名片么？");
//						builder.setTitle("提示");
//						builder.setPositiveButton("确定", new OnClickListener(){
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								// TODO Auto-generated method stub
//								ContactCard contact = ContactCard.parseContactCard(str);
//								ContactInterface.insert(contact, ctx);
//								dialog.dismiss();
//							}
//						});
//						builder.setNegativeButton("取消", new OnClickListener(){
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								// TODO Auto-generated method stub
//								dialog.dismiss();
//							}
//						});
//						builder.create().show();
//					}
//					else{
//						mcontentsAdapter.add(intent.getStringExtra("msg"), false);
//		  	  			mcontentsAdapter.notifyDataSetChanged();
//					}*/
//				}
//			}
//		}
//	};
//
//	private String pack(String in, String prefix){
//		String out = "";
//		out = prefix + in;
//		return out;
//	}
//
//	private String getPrefix(String in){
//		String prefix = in.substring(0, ChatPlatform.PREFIX_LENGTH);
//		if (prefix.equals(ChatPlatform.PREFIX_CONTACT)){
//			return prefix;
//		}
//		else if (prefix.equals(ChatPlatform.PREFIX_CONTENT)){
//			return prefix;
//		}
//		else if (prefix.equals(ChatPlatform.PREFIX_INFORMATION)){
//			return prefix;
//		}
//		else if (prefix.equals(ChatPlatform.PREFIX_SYSTEM)){
//			return prefix;
//		}
//		else{
//			return null;
//		}
//	}
//
//	private String unpack(String in){
//		//System.out.println(in);
//		//System.out.println(in.substring(0, 10));
//		//System.out.println(in.substring(11));
//		String prefix = in.substring(0, ChatPlatform.PREFIX_LENGTH);
//		if (prefix.equals(ChatPlatform.PREFIX_CONTACT)){
//			return in.substring(ChatPlatform.PREFIX_LENGTH);
//		}
//		else if (prefix.equals(ChatPlatform.PREFIX_CONTENT)){
//			return in.substring(ChatPlatform.PREFIX_LENGTH);
//		}
//		else if (prefix.equals(ChatPlatform.PREFIX_INFORMATION)){
//			return in.substring(ChatPlatform.PREFIX_LENGTH);
//		}
//		else if (prefix.equals(ChatPlatform.PREFIX_SYSTEM)){
//			return in.substring(ChatPlatform.PREFIX_LENGTH);
//		}
//		else{
//			return null;
//		}
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.chat_platform);
//
//		this.getActionBar().setDisplayHomeAsUpEnabled(true);
//		ctx = this;
//
//		detailInformation = getSharedPreferences(ActivityControlCenter.DETAIL_INFORMATION, 0);
//
//		address = this.getIntent().getStringExtra("address");
//		isClient = this.getIntent().getBooleanExtra("isclient", true);
//		conversation = (ListView)findViewById(R.id.conversation);
//		input = (EditText)findViewById(R.id.inputField);
//		mcontentsAdapter = new ContentsAdapter(this, new ArrayList<Contents>());
//		conversation.setAdapter(mcontentsAdapter);
//
//		Parcelable parcelable = this.getIntent().getParcelableExtra("information");
//		overt_user = (Information)parcelable;
//
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(ActivityControlCenter.ACTION_SERVICE_TO_ACTIVITY);
//		this.registerReceiver(receiver, filter);
//
//		System.out.println(isClient +"   "+address);
//		if (isClient){
//			device = mBluetoothAdapter.getRemoteDevice(address);
//			clientConnectThread = new clientThread();
//			clientConnectThread.start();
//		}
//		else{
//			Intent intent = new Intent();
//	    	intent.setAction(ActivityControlCenter.ACTION_ACTIVITY_TO_SERVICE);
//	    	intent.putExtra("msg", pack(overt_user.toString(), ChatPlatform.PREFIX_INFORMATION));
//	    	sendBroadcast(intent);
//			mcontentsAdapter.add("已经与"+ address +"建立连接", false, "系统信息");
//		}
//	}
//
//	protected void onDestroy() {
//        super.onDestroy();
//        this.unregisterReceiver(receiver);
//        this.shutdownClient();
//	}
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.chat_platform, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		if (id == android.R.id.home){
//			this.finish();
//		}
//		if (id == R.id.action_contact){
//			this.startActivity(new Intent(ChatPlatform.this, ContactCardSettings.class));
//			return true;
//		}
//		if (id == R.id.action_personal){
//			this.startActivity(new Intent(ChatPlatform.this, BaseInfoSettings.class));
//			return true;
//		}
//		if (id == R.id.send_contact){
//			final SharedPreferences contactSettings;
//			contactSettings = getSharedPreferences(ActivityControlCenter.CONTACT_SETTINGS, 0);
//
//			ArrayList<String> name_list = new ArrayList<String>();
//			final ArrayList<String> key_list = new ArrayList<String>();
//
//			String txt = "";
//			ContactCard contact = null;
//
//			txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_1, "");
//			contact = ContactCard.parseContactCard(txt);
//			if (contact != null){
//				name_list.add(contact.tableName);
//				key_list.add(ActivityControlCenter.KEY_CONTACT_1);
//			}
//
//			txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_2, "");
//			contact = ContactCard.parseContactCard(txt);
//			if (contact != null){
//				name_list.add(contact.tableName);
//				key_list.add(ActivityControlCenter.KEY_CONTACT_2);
//			}
//
//			txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_3, "");
//			contact = ContactCard.parseContactCard(txt);
//			if (contact != null){
//				name_list.add(contact.tableName);
//				key_list.add(ActivityControlCenter.KEY_CONTACT_3);
//			}
//
//			txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_4, "");
//			contact = ContactCard.parseContactCard(txt);
//			if (contact != null){
//				name_list.add(contact.tableName);
//				key_list.add(ActivityControlCenter.KEY_CONTACT_4);
//			}
//
//			txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_5, "");
//			contact = ContactCard.parseContactCard(txt);
//			if (contact != null){
//				name_list.add(contact.tableName);
//				key_list.add(ActivityControlCenter.KEY_CONTACT_5);
//			}
//
//			txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_6, "");
//			contact = ContactCard.parseContactCard(txt);
//			if (contact != null){
//				name_list.add(contact.tableName);
//				key_list.add(ActivityControlCenter.KEY_CONTACT_6);
//			}
//
//			txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_7, "");
//			contact = ContactCard.parseContactCard(txt);
//			if (contact != null){
//				name_list.add(contact.tableName);
//				key_list.add(ActivityControlCenter.KEY_CONTACT_7);
//			}
//
//			txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_8, "");
//			contact = ContactCard.parseContactCard(txt);
//			if (contact != null){
//				name_list.add(contact.tableName);
//				key_list.add(ActivityControlCenter.KEY_CONTACT_8);
//			}
//
//			new AlertDialog.Builder(this).setTitle("选择名片")
//			.setSingleChoiceItems(name_list.toArray(new String[name_list.size()]), 0, new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					String str = pack(contactSettings.getString(key_list.get(which), ""), ChatPlatform.PREFIX_CONTACT);
//					if (isClient)
//						sendMessage(str);
//					else{
//						Intent intent = new Intent();
//				    	intent.setAction(ActivityControlCenter.ACTION_ACTIVITY_TO_SERVICE);
//				    	intent.putExtra("msg", str);
//				    	sendBroadcast(intent);
//					}
//					dialog.dismiss();
//					}
//				}).setNegativeButton("取消", null).show();
//		}
//		if (id == R.id.action_information){
//			SharedPreferences sp = getSharedPreferences(ActivityControlCenter.DETAIL_INFORMATION, 0);
//			String res = sp.getString(address, "Not found");
//			if (!res.equals("Not found")){
//				Information info = Information.parseInformation(res);
//				if (info != null){
//					Bundle bundle = new Bundle();
//					bundle.putParcelable("information", info);
//					Intent intent=new Intent(ChatPlatform.this, ShowInformation.class);
//					intent.putExtras(bundle);
//					ctx.startActivity(intent);
//				}
//			}
//			else{
//				Toast.makeText(getApplicationContext(),"找不到信息，请重新连接", Toast.LENGTH_SHORT).show();
//			}
//		}
//		return super.onOptionsItemSelected(item);
//	}
//}
