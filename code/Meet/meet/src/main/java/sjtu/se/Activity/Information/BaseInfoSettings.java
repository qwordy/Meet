package sjtu.se.Activity.Information;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import sjtu.se.Meet.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Activity.Setting.SystemSettings;

public class BaseInfoSettings extends AppCompatActivity {

	private Context ctx = this;
	private SharedPreferences baseInfo;
	private TextView item_nick;
	private TextView item_name;
	private TextView item_gender;
	private TextView item_birthday;
	private TextView item_homeland;
	private TextView item_location;
	private TextView item_keywords;

	private Switch item_name_overt;
	private Switch item_gender_overt;
	private Switch item_birthday_overt;
	private Switch item_homeland_overt;
	private Switch item_location_overt;
	private Switch item_keywords_overt;

	public void baseInfoNickEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_name_10, (ViewGroup) findViewById(R.layout.text_edit_name_10));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_name_10);
		et.setText(baseInfo.getString(ActivityControlCenter.KEY_NICK, ""));

		new AlertDialog.Builder(ctx).setTitle("设置昵称").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putString(ActivityControlCenter.KEY_NICK, str);
				editor.commit();
				item_nick.setText(str);
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}

	public void baseInfoNameEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_name_10, (ViewGroup) findViewById(R.layout.text_edit_name_10));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_name_10);
		et.setText(baseInfo.getString(ActivityControlCenter.KEY_NAME, ""));

		new AlertDialog.Builder(ctx).setTitle("设置昵称").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putString(ActivityControlCenter.KEY_NAME, str);
				editor.commit();
				item_name.setText(str);
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}

	public void baseInfoGenderEdit(View view){
		new AlertDialog.Builder(this).setTitle("选择性别")
		.setSingleChoiceItems(new String[]{"男", "女"}, 0, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String str = String.valueOf(which);
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putString(ActivityControlCenter.KEY_GENDER, str);
				editor.commit();
				item_gender.setText(ActivityControlCenter.genders[Integer.parseInt(str)]);
				dialog.dismiss();
			}
		}).setNegativeButton("取消", null).show();
	}

	public void baseInfoBirthdayEdit(View view){
		final DatePicker dp = new DatePicker(ctx);
		new AlertDialog.Builder(ctx).setTitle("选择生日").setView(dp)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				int y = dp.getYear();
				int m = dp.getMonth() + 1;
				int d = dp.getDayOfMonth();
				String mdate;
				if (m < 10) {
					if (d < 10)
						mdate = String.valueOf(y) + "-0" + String.valueOf(m) + "-0" + String.valueOf(d);
					else
						mdate = String.valueOf(y) + "-0" + String.valueOf(m) + "-" + String.valueOf(d);
				} else {
					if (d < 10)
						mdate = String.valueOf(y) + "-" + String.valueOf(m) + "-0" + String.valueOf(d);
					else
						mdate = String.valueOf(y) + "-" + String.valueOf(m) + "-" + String.valueOf(d);
				}
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putString(ActivityControlCenter.KEY_BIRTHDAY, mdate);
				editor.commit();
				item_birthday.setText(mdate);
			}
		}).setNegativeButton("返回", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}

	public void baseInfoHomelandEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(baseInfo.getString(ActivityControlCenter.KEY_HOMELAND, ""));

		new AlertDialog.Builder(ctx).setTitle("设置籍贯").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putString(ActivityControlCenter.KEY_HOMELAND, str);
				editor.commit();
				item_homeland.setText(str);
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		}).show();
	}

	public void baseInfoLocationEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(baseInfo.getString(ActivityControlCenter.KEY_LOCATION, ""));

		new AlertDialog.Builder(ctx).setTitle("设置居住地").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putString(ActivityControlCenter.KEY_LOCATION, str);
				editor.commit();
				item_location.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void baseInfoKeywordsEdit(View view){
		final EditText et = new EditText(ctx);
		et.setText(baseInfo.getString(ActivityControlCenter.KEY_KEYWORDS, ""));

		new AlertDialog.Builder(ctx).setTitle("设置标签").setView(et)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putString(ActivityControlCenter.KEY_KEYWORDS, str);
				editor.commit();
				item_keywords.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void setBaseInfoNameOvertListener(){
		item_name_overt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_NAME_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setBaseInfoGenderOvertListener(){
		item_gender_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_GENDER_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setBaseInfoBirthdayOvertListener(){
		item_birthday_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_BIRTHDAY_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setBaseInfoHomelandOvertListener(){
		item_homeland_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_HOMELAND_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setBaseInfoLocationOvertListener(){
		item_location_overt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_LOCATION_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setBaseInfoKeywordsOvertListener(){
		item_keywords_overt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = baseInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_KEYWORDS_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void goToContactInfo(View view){
		this.startActivity(new Intent(BaseInfoSettings.this, ContactInfoSettings.class));
	}

	public void goToEducationInfo(View view){
		this.startActivity(new Intent(BaseInfoSettings.this, EducationInfoSettings.class));
	}

	public void goToHobbyInfo(View view){
		this.startActivity(new Intent(BaseInfoSettings.this, HobbyInfoSettings.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_info);

		Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
		setSupportActionBar(myToolbar);

		ActivityControlCenter.PERSONAL_INFO_MAY_CHANGED = true;

		baseInfo = getSharedPreferences(ActivityControlCenter.PERSONAL_BASE_INFO, 0);

		item_nick = (TextView) findViewById(R.id.base_info_nick);
		item_nick.setText(baseInfo.getString(ActivityControlCenter.KEY_NICK, ""));
		item_name = (TextView) findViewById(R.id.base_info_name);
		item_name.setText(baseInfo.getString(ActivityControlCenter.KEY_NAME, ""));
		item_gender = (TextView) findViewById(R.id.base_info_gender);
		item_gender.setText(ActivityControlCenter.genders[Integer.parseInt(baseInfo.getString(ActivityControlCenter.KEY_GENDER, "2"))]);
		item_birthday = (TextView) findViewById(R.id.base_info_birthday);
		item_birthday.setText(baseInfo.getString(ActivityControlCenter.KEY_BIRTHDAY, ""));
		item_homeland = (TextView) findViewById(R.id.base_info_homeland);
		item_homeland.setText(baseInfo.getString(ActivityControlCenter.KEY_HOMELAND, ""));
		item_location = (TextView) findViewById(R.id.base_info_location);
		item_location.setText(baseInfo.getString(ActivityControlCenter.KEY_LOCATION, ""));
		item_keywords = (TextView) findViewById(R.id.base_info_keywords);
		item_keywords.setText(baseInfo.getString(ActivityControlCenter.KEY_KEYWORDS, ""));

		item_name_overt = (Switch) findViewById(R.id.base_info_name_overt);
		item_name_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_NAME_OVERT, false));
		setBaseInfoNameOvertListener();

		item_gender_overt = (Switch) findViewById(R.id.base_info_gender_overt);
		item_gender_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_GENDER_OVERT, false));
		setBaseInfoGenderOvertListener();

		item_birthday_overt = (Switch) findViewById(R.id.base_info_birthday_overt);
		item_birthday_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_BIRTHDAY_OVERT, false));
		setBaseInfoBirthdayOvertListener();

		item_homeland_overt = (Switch) findViewById(R.id.base_info_homeland_overt);
		item_homeland_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_HOMELAND_OVERT, false));
		setBaseInfoHomelandOvertListener();

		item_location_overt = (Switch) findViewById(R.id.base_info_location_overt);
		item_location_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_LOCATION_OVERT, false));
		setBaseInfoLocationOvertListener();

		item_keywords_overt = (Switch) findViewById(R.id.base_info_keywords_overt);
		item_keywords_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_KEYWORDS_OVERT, false));
		setBaseInfoKeywordsOvertListener();
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
		getMenuInflater().inflate(R.menu.base_information, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			this.startActivity(new Intent(BaseInfoSettings.this, SystemSettings.class));
			return true;
		}
		if (id == android.R.id.home){
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
