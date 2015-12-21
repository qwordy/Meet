//package sjtu.se.Activity.ContactCard;
//
//import android.view.*;
//import sjtu.se.Activity.Setting.SystemSettings;
//import sjtu.se.Util.ContactInterface;
//import sjtu.se.Activity.ActivityControlCenter;
//import sjtu.se.UserInformation.ContactCard;
//
//import sjtu.se.Meet.R;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.nfc.NdefMessage;
//import android.nfc.NdefRecord;
//import android.nfc.NfcAdapter;
//import android.nfc.NfcAdapter.CreateNdefMessageCallback;
//import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
//import android.nfc.NfcEvent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.os.Parcelable;
//import android.text.InputType;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class ShowContactCard extends Activity implements CreateNdefMessageCallback,
//	OnNdefPushCompleteCallback {
//
//    NfcAdapter mNfcAdapter;
//    private static final int MESSAGE_SENT = 11;
//
//	private Context ctx;
//	private SharedPreferences contactSettings;
//	private String key;
//	private ContactCard contact;
//
//	private TextView tablename;
//	private TextView name;
//	private TextView phone;
//	private TextView email;
//
//	public void contactTableEdit(View view){
//		LayoutInflater inflater = getLayoutInflater();
//		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
//		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
//		String tmp = contactSettings.getString(key, "");
//		contact = ContactCard.parseContactCard(tmp);
//		if (contact == null)
//			contact = new ContactCard();
//		et.setText(contact.tableName);
//
//		new AlertDialog.Builder(ctx).setTitle("设置表名").setView(layout)
//		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				String str = et.getText().toString();
//				contact.tableName = str;
//				SharedPreferences.Editor editor = contactSettings.edit();
//				editor.putString(key, contact.toString());
//				editor.commit();
//				tablename.setText(str);
//			}
//		})
//		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog,int which) {}
//		}).show();
//	}
//
//	public void contactNameEdit(View view){
//		LayoutInflater inflater = getLayoutInflater();
//		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
//		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
//		String tmp = contactSettings.getString(key, "");
//		contact = ContactCard.parseContactCard(tmp);
//		if (contact == null)
//			contact = new ContactCard();
//		et.setText(contact.name);
//
//		new AlertDialog.Builder(ctx).setTitle("姓名").setView(layout)
//		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				String str = et.getText().toString();
//				contact.name = str;
//				SharedPreferences.Editor editor = contactSettings.edit();
//				editor.putString(key, contact.toString());
//				editor.commit();
//				name.setText(str);
//			}
//		})
//		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog,int which) {}
//		}).show();
//	}
//
//	public void contactPhoneEdit(View view){
//		final EditText et = new EditText(ctx);
//		String tmp = contactSettings.getString(key, "");
//		contact = ContactCard.parseContactCard(tmp);
//		if (contact == null)
//			contact = new ContactCard();
//		et.setText(contact.phone);
//		et.setInputType(InputType.TYPE_CLASS_PHONE);
//
//		new AlertDialog.Builder(ctx).setTitle("电话").setView(et)
//		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				String str = et.getText().toString();
//				contact.phone = str;
//				SharedPreferences.Editor editor = contactSettings.edit();
//				editor.putString(key, contact.toString());
//				editor.commit();
//				phone.setText(str);
//			}
//		})
//		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog,int which) {}
//		}).show();
//	}
//
//	public void contactEmailEdit(View view){
//		final EditText et = new EditText(ctx);
//		String tmp = contactSettings.getString(key, "");
//		contact = ContactCard.parseContactCard(tmp);
//		if (contact == null)
//			contact = new ContactCard();
//		et.setText(contact.email);
//		et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
//
//		new AlertDialog.Builder(ctx).setTitle("邮箱").setView(et)
//		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				String str = et.getText().toString();
//				contact.email = str;
//				SharedPreferences.Editor editor = contactSettings.edit();
//				editor.putString(key, contact.toString());
//				editor.commit();
//				email.setText(str);
//			}
//		})
//		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog,int which) {}
//		}).show();
//	}
//
//	private void updateContacts(){
//		String str = contact.tableName;
//		if (str.equals(""))
//			tablename.setText("未命名");
//		else
//			tablename.setText(str);
//		name.setText(contact.name);
//		phone.setText(contact.phone);
//		email.setText(contact.email);
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.show_contact_card);
//
//		/*this.getActionBar().setDisplayHomeAsUpEnabled(true);*/
//
//		ctx = this;
//
//		contactSettings = getSharedPreferences(ActivityControlCenter.CONTACT_SETTINGS, 0);
//		key = this.getIntent().getStringExtra("key");
//
//		tablename = (TextView)findViewById(R.id.contact_tablename);
//		name = (TextView)findViewById(R.id.contact_name);
//		phone = (TextView)findViewById(R.id.contact_phone);
//		email = (TextView)findViewById(R.id.contact_email);
//
//        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        if (mNfcAdapter != null) {
//            // Register callback to set NDEF message
//            mNfcAdapter.setNdefPushMessageCallback(this, this);
//            // Register callback to listen for message-sent success
//            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
//        }
//	}
//
//	@Override
//	protected void onResume(){
//		super.onResume();
//		String msg = this.getIntent().getStringExtra("message");
//		contact = ContactCard.parseContactCard(msg);
//		if (contact == null)
//			contact = new ContactCard();
//
//		this.updateContacts();
//		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
//	        processIntent(getIntent());
//	    }
//	}
//
//	@Override
//    public void onNewIntent(Intent intent) {
//        // onResume gets called after this to handle the intent
//        setIntent(intent);
//    }
//
//	void processIntent(Intent intent) {
//        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
//                NfcAdapter.EXTRA_NDEF_MESSAGES);
//        // only one message sent during the beam
//        NdefMessage msg = (NdefMessage) rawMsgs[0];
//        // record 0 contains the MIME type, record 1 is the AAR, if present
//        String str = new String(msg.getRecords()[0].getPayload());
//        ContactCard c = ContactCard.parseContactCard(str);
//        ContactInterface.insert(c, ctx);
//        Toast.makeText(getApplicationContext(), "名片已经接收！", Toast.LENGTH_LONG).show();
//        this.finish();
//    }
//
//	@Override
//	public void onNdefPushComplete(NfcEvent event) {
//		// TODO Auto-generated method stub
//        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
//	}
//
//	@SuppressLint("NewApi")
//	@Override
//	public NdefMessage createNdefMessage(NfcEvent event) {
//		// TODO Auto-generated method stub
//		String text = contact.toString();
//		NdefMessage msg = new NdefMessage(NdefRecord.createMime("application/sjtu.se.bluetoothtry", text.getBytes())
//         /**
//          * The Android Application Record (AAR) is commented out. When a device
//          * receives a push with an AAR in it, the application specified in the AAR
//          * is guaranteed to run. The AAR overrides the tag dispatch system.
//          * You can add it back in to guarantee that this
//          * activity starts when receiving a beamed message. For now, this code
//          * uses the tag dispatch system.
//          */
//          //,NdefRecord.createApplicationRecord("com.example.android.beam")
//        );
//        return msg;
//	}
//
//	private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//            case MESSAGE_SENT:
//                Toast.makeText(getApplicationContext(), "名片已经递送！", Toast.LENGTH_LONG).show();
//                break;
//            }
//        }
//    };
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
//		getMenuInflater().inflate(R.menu.show_contact_card, menu);
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
//			this.startActivity(new Intent(ShowContactCard.this, SystemSettings.class));
//			return true;
//		}
//		if (id == android.R.id.home){
//			this.finish();
//		}
//		return super.onOptionsItemSelected(item);
//	}
//}
