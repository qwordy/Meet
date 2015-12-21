//package sjtu.se.Activity.ContactCard;
//
//import android.view.KeyEvent;
//import sjtu.se.Activity.ActivityControlCenter;
//import sjtu.se.Activity.Setting.SystemSettings;
//import sjtu.se.UserInformation.ContactCard;
//
//import sjtu.se.Meet.R;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.TextView;
//
//public class ContactCardSettings extends Activity {
//
//	private Context ctx = this;
//
//	private SharedPreferences contactSettings;
//
//	private TextView contact1;
//	private TextView contact2;
//	private TextView contact3;
//	private TextView contact4;
//	private TextView contact5;
//	private TextView contact6;
//	private TextView contact7;
//	private TextView contact8;
//
//	public void CreateOrModify(View view){
//		String msg = "";
//		String key = "";
//		int id = ((TextView)view).getId();
//		switch(id){
//		case R.id.contactSlot1:{
//			msg = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_1, "");
//			key = ActivityControlCenter.KEY_CONTACT_1;
//			break;
//		}
//		case R.id.contactSlot2:{
//			msg = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_2, "");
//			key = ActivityControlCenter.KEY_CONTACT_2;
//			break;
//		}
//		case R.id.contactSlot3:{
//			msg = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_3, "");
//			key = ActivityControlCenter.KEY_CONTACT_3;
//			break;
//		}
//		case R.id.contactSlot4:{
//			msg = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_4, "");
//			key = ActivityControlCenter.KEY_CONTACT_4;
//			break;
//		}
//		case R.id.contactSlot5:{
//			msg = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_5, "");
//			key = ActivityControlCenter.KEY_CONTACT_5;
//			break;
//		}
//		case R.id.contactSlot6:{
//			msg = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_6, "");
//			key = ActivityControlCenter.KEY_CONTACT_6;
//			break;
//		}
//		case R.id.contactSlot7:{
//			msg = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_7, "");
//			key = ActivityControlCenter.KEY_CONTACT_7;
//			break;
//		}
//		case R.id.contactSlot8:{
//			msg = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_8, "");
//			key = ActivityControlCenter.KEY_CONTACT_8;
//			break;
//		}
//		}
//
//		Intent intent = new Intent(ContactCardSettings.this, ShowContactCard.class);
//		intent.putExtra("message", msg);
//		intent.putExtra("key", key);
//		ctx.startActivity(intent);
//
//		return;
//	}
//
//	private void updateText(){
//		String defult = "新建";
//		String unname = "未命名";
//		String txt = "";
//		ContactCard contact = null;
//
//		txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_1, "");
//		contact = ContactCard.parseContactCard(txt);
//		if (contact == null)
//			contact1.setText(defult);
//        else if(contact.tableName.equals(""))
//            contact1.setText(unname);
//		else
//			contact1.setText(contact.tableName);
//
//		txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_2, "");
//		contact = ContactCard.parseContactCard(txt);
//		if (contact == null)
//			contact2.setText(defult);
//        else if(contact.tableName.equals(""))
//            contact2.setText(unname);
//		else
//			contact2.setText(contact.tableName);
//
//		txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_3, "");
//		contact = ContactCard.parseContactCard(txt);
//		if (contact == null)
//			contact3.setText(defult);
//        else if(contact.tableName.equals(""))
//            contact3.setText(unname);
//		else
//			contact3.setText(contact.tableName);
//
//		txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_4, "");
//		contact = ContactCard.parseContactCard(txt);
//		if (contact == null)
//			contact4.setText(defult);
//        else if(contact.tableName.equals(""))
//            contact4.setText(unname);
//		else
//			contact4.setText(contact.tableName);
//
//		txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_5, "");
//		contact = ContactCard.parseContactCard(txt);
//		if (contact == null)
//			contact5.setText(defult);
//        else if(contact.tableName.equals(""))
//            contact5.setText(unname);
//		else
//			contact5.setText(contact.tableName);
//
//		txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_6, "");
//		contact = ContactCard.parseContactCard(txt);
//		if (contact == null)
//			contact6.setText(defult);
//        else if(contact.tableName.equals(""))
//            contact6.setText(unname);
//		else
//			contact6.setText(contact.tableName);
//
//		txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_7, "");
//		contact = ContactCard.parseContactCard(txt);
//		if (contact == null)
//			contact7.setText(defult);
//        else if(contact.tableName.equals(""))
//            contact7.setText(unname);
//		else
//			contact7.setText(contact.tableName);
//
//		txt = contactSettings.getString(ActivityControlCenter.KEY_CONTACT_8, "");
//		contact = ContactCard.parseContactCard(txt);
//		if (contact == null)
//			contact8.setText(defult);
//        else if(contact.tableName.equals(""))
//            contact8.setText(unname);
//		else
//			contact8.setText(contact.tableName);
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.contact_card_settings);
//
//		/*this.getActionBar().setDisplayHomeAsUpEnabled(true);*/
//		ctx = this;
//
//		ActivityControlCenter.CONTACTS_MAY_CHANGED = true;
//
//		contactSettings = getSharedPreferences(ActivityControlCenter.CONTACT_SETTINGS, 0);
//
//		contact1 = (TextView)findViewById(R.id.contactSlot1);
//		contact2 = (TextView)findViewById(R.id.contactSlot2);
//		contact3 = (TextView)findViewById(R.id.contactSlot3);
//		contact4 = (TextView)findViewById(R.id.contactSlot4);
//		contact5 = (TextView)findViewById(R.id.contactSlot5);
//		contact6 = (TextView)findViewById(R.id.contactSlot6);
//		contact7 = (TextView)findViewById(R.id.contactSlot7);
//		contact8 = (TextView)findViewById(R.id.contactSlot8);
//	}
//
//	protected void onResume(){
//		super.onResume();
//		//System.out.println("Resuming........................................");
//		this.updateText();
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
//		getMenuInflater().inflate(R.menu.contact_card_settings, menu);
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
//			this.startActivity(new Intent(ContactCardSettings.this, SystemSettings.class));
//			return true;
//		}
//		if (id == android.R.id.home){
//			this.finish();
//		}
//		return super.onOptionsItemSelected(item);
//	}
//}
