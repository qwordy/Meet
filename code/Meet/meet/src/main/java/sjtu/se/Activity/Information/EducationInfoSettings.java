package sjtu.se.Activity.Information;

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
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Activity.Setting.SystemSettings;
import sjtu.se.Activity.Want.WantSettings;

public class EducationInfoSettings extends Activity {

	private Context ctx = this;

	private SharedPreferences educationInfo;
	private TextView item_college;
	private TextView item_high;
	private TextView item_middle;
	private TextView item_primary;

	private Switch item_college_overt;
	private Switch item_high_overt;
	private Switch item_middle_overt;
	private Switch item_primary_overt;

	public void educationInfoCollegeEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(educationInfo.getString(ActivityControlCenter.KEY_COLLEGE, ""));

		new AlertDialog.Builder(ctx).setTitle("大学").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = educationInfo.edit();
				editor.putString(ActivityControlCenter.KEY_COLLEGE, str);
				editor.commit();
				item_college.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void educationInfoHighEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(educationInfo.getString(ActivityControlCenter.KEY_HIGH, ""));

		new AlertDialog.Builder(ctx).setTitle("高中").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = educationInfo.edit();
				editor.putString(ActivityControlCenter.KEY_HIGH, str);
				editor.commit();
				item_high.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void educationInfoMiddleEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(educationInfo.getString(ActivityControlCenter.KEY_MIDDLE, ""));

		new AlertDialog.Builder(ctx).setTitle("初中").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = educationInfo.edit();
				editor.putString(ActivityControlCenter.KEY_MIDDLE, str);
				editor.commit();
				item_middle.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void educationInfoPrimaryEdit(View view){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
		final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
		et.setText(educationInfo.getString(ActivityControlCenter.KEY_PRIMARY, ""));

		new AlertDialog.Builder(ctx).setTitle("小学").setView(layout)
		.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String str = et.getText().toString();
				SharedPreferences.Editor editor = educationInfo.edit();
				editor.putString(ActivityControlCenter.KEY_PRIMARY, str);
				editor.commit();
				item_primary.setText(str);
			}
		})
		.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {}
		}).show();
	}

	public void setEducationInfoCollegeOvertListener(){
		item_college_overt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = educationInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_COLLEGE_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setEducationInfoHighOvertListener(){
		item_high_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = educationInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_HIGH_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setEducationInfoMiddleOvertListener(){
		item_middle_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = educationInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_MIDDLE_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	public void setEducationInfoPrimaryOvertListener(){
		item_primary_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = educationInfo.edit();
				editor.putBoolean(ActivityControlCenter.KEY_PRIMARY_OVERT, isChecked);
				editor.commit();
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.education_info);

		this.getActionBar().setDisplayHomeAsUpEnabled(true);

		ActivityControlCenter.PERSONAL_INFO_MAY_CHANGED = true;

		educationInfo = getSharedPreferences(ActivityControlCenter.PERSONAL_EDUCATION_INFO, 0);

		item_college = (TextView) findViewById(R.id.education_info_college);
		item_college.setText(educationInfo.getString(ActivityControlCenter.KEY_COLLEGE, ""));
		item_high = (TextView) findViewById(R.id.education_info_high);
		item_high.setText(educationInfo.getString(ActivityControlCenter.KEY_HIGH, ""));
		item_middle = (TextView) findViewById(R.id.education_info_middle);
		item_middle.setText(educationInfo.getString(ActivityControlCenter.KEY_MIDDLE, ""));
		item_primary = (TextView) findViewById(R.id.education_info_primary);
		item_primary.setText(educationInfo.getString(ActivityControlCenter.KEY_PRIMARY, ""));

		item_college_overt = (Switch) findViewById(R.id.education_info_college_overt);
		item_college_overt.setChecked(educationInfo.getBoolean(ActivityControlCenter.KEY_COLLEGE_OVERT, false));
		setEducationInfoCollegeOvertListener();

		item_high_overt = (Switch) findViewById(R.id.education_info_high_overt);
		item_high_overt.setChecked(educationInfo.getBoolean(ActivityControlCenter.KEY_HIGH_OVERT, false));
		setEducationInfoHighOvertListener();

		item_middle_overt = (Switch) findViewById(R.id.education_info_middle_overt);
		item_middle_overt.setChecked(educationInfo.getBoolean(ActivityControlCenter.KEY_MIDDLE_OVERT, false));
		setEducationInfoMiddleOvertListener();

		item_primary_overt = (Switch) findViewById(R.id.education_info_primary_overt);
		item_primary_overt.setChecked(educationInfo.getBoolean(ActivityControlCenter.KEY_PRIMARY_OVERT, false));
		setEducationInfoPrimaryOvertListener();
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
		getMenuInflater().inflate(R.menu.education_information, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			this.startActivity(new Intent(EducationInfoSettings.this, SystemSettings.class));
			return true;
		}
		if (id == android.R.id.home){
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
