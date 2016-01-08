package sjtu.se.Activity.Information;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import sjtu.se.Meet.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton.OnCheckedChangeListener;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Util.DatePickerFragment;

public class BaseInfoSettings extends Fragment implements View.OnFocusChangeListener{

	private Context ctx;
	private View view;
    private ViewGroup container;
    private LayoutInflater inflater;
	private SharedPreferences baseInfo;
	private TextView item_name;
	private Spinner item_gender;
	private TextView item_birthday;
	private TextView item_homeland;
	private TextView item_location;
	private TextView item_keywords;
    private TextView item_goto_contact_info;
    private TextView item_goto_edu_info;
    private TextView item_goto_hobby_info;

	private ToggleButton item_name_overt;
	private ToggleButton item_gender_overt;
	private ToggleButton item_birthday_overt;
	private ToggleButton item_homeland_overt;
	private ToggleButton item_location_overt;
	private ToggleButton item_keywords_overt;

	/*public void baseInfoGenderEdit(View view){
		new AlertDialog.Builder(ctx).setTitle("选择性别")
		.setSingleChoiceItems(new String[]{"男", "女", "无"}, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String str = String.valueOf(which);
                SharedPreferences.Editor editor = baseInfo.edit();
                if(which ==2)
                    editor.putString(ActivityControlCenter.KEY_GENDER, "");
                else
                    editor.putString(ActivityControlCenter.KEY_GENDER, str);
                editor.commit();
                //item_gender.setText(ActivityControlCenter.genders[Integer.parseInt(str)]);
                dialog.dismiss();
            }
        }).setNegativeButton("取消", null).show();
	}*/

    public void baseInfoBirthdayDelete(View view){
        new AlertDialog.Builder(ctx).setTitle("删除生日吗？")
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = baseInfo.edit();
                editor.putString(ActivityControlCenter.KEY_BIRTHDAY, "");
                editor.commit();
                item_birthday.setText("");
                dialog.dismiss();
            }
        }).setNegativeButton("取消", null).show();
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
		item_gender_overt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = baseInfo.edit();
                editor.putBoolean(ActivityControlCenter.KEY_GENDER_OVERT, isChecked);
                editor.commit();
            }
        });
	}

	public void setBaseInfoBirthdayOvertListener(){
		item_birthday_overt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = baseInfo.edit();
                editor.putBoolean(ActivityControlCenter.KEY_BIRTHDAY_OVERT, isChecked);
                editor.commit();
            }
        });
	}

	public void setBaseInfoHomelandOvertListener(){
		item_homeland_overt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
        getActivity().startActivity(new Intent(ctx, ContactInfoSettings.class));
	}

	public void goToEducationInfo(View view){
		getActivity().startActivity(new Intent(ctx, EducationInfoSettings.class));
	}

	public void goToHobbyInfo(View view){
        getActivity().startActivity(new Intent(ctx, HobbyInfoSettings.class));
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
        this.inflater=inflater;
		this.view = inflater.inflate(R.layout.base_info, container, false);
        this.ctx=getActivity();
        ActivityControlCenter.PERSONAL_INFO_MAY_CHANGED = true;

        baseInfo = ctx.getSharedPreferences(ActivityControlCenter.PERSONAL_BASE_INFO, 0);

        container = (ViewGroup)view.findViewById(R.id.focus_container);
        container.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch (View v, MotionEvent event){
                InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                v.requestFocus();
                return true;
            }
        });

        item_name = (TextView) view.findViewById(R.id.base_info_name);
        item_name.setText(baseInfo.getString(ActivityControlCenter.KEY_NAME, ""));
        item_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    String str = v.getText().toString();
                    SharedPreferences.Editor editor = baseInfo.edit();
                    editor.putString(ActivityControlCenter.KEY_NAME, str);
                    editor.apply();
                    InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    view.findViewById(R.id.focus_container).requestFocus();
                    //Log.e(" ", "没有响应");
                    handled = true;
                }
                return handled;
            }
        });
        item_name.setOnFocusChangeListener(this);

        item_gender = (Spinner) view.findViewById(R.id.base_info_gender);
        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(ctx,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        item_gender.setAdapter(gender_adapter);
        item_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //String[] languages = getResources().getStringArray(R.array.gender_array);
                String str = String.valueOf(pos);
                SharedPreferences.Editor editor = baseInfo.edit();
                if (pos == 2)
                    editor.putString(ActivityControlCenter.KEY_GENDER, "");
                else
                    editor.putString(ActivityControlCenter.KEY_GENDER, str);
                editor.commit();
                //item_gender.setText(ActivityControlCenter.genders[Integer.parseInt(str)]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        if(baseInfo.getString(ActivityControlCenter.KEY_GENDER, "2").equals(""))
            item_gender.setSelection(2);
        else
            item_gender.setSelection(Integer.parseInt(baseInfo.getString(ActivityControlCenter.KEY_GENDER, "2")));

        item_birthday = (TextView) view.findViewById(R.id.base_info_birthday);
        item_birthday.setText(baseInfo.getString(ActivityControlCenter.KEY_BIRTHDAY, ""));
        item_birthday.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                baseInfoBirthdayDelete(v);return true;
            }
        });
        item_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                //item_birthday.requestFocus();//setText(baseInfo.getString(ActivityControlCenter.KEY_BIRTHDAY, ""));
            }
        });
        //item_birthday.setOnFocusChangeListener(this);

        item_homeland = (TextView) view.findViewById(R.id.base_info_homeland);
        item_homeland.setText(baseInfo.getString(ActivityControlCenter.KEY_HOMELAND, ""));
        item_homeland.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    String str = v.getText().toString();
                    SharedPreferences.Editor editor = baseInfo.edit();
                    editor.putString(ActivityControlCenter.KEY_HOMELAND, str);
                    editor.apply();
                    InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    view.findViewById(R.id.focus_container).requestFocus();
                    handled = true;
                }
                return handled;
            }
        });
        item_homeland.setOnFocusChangeListener(this);

        item_location = (TextView) view.findViewById(R.id.base_info_location);
        item_location.setText(baseInfo.getString(ActivityControlCenter.KEY_LOCATION, ""));
        item_location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    String str = v.getText().toString();
                    SharedPreferences.Editor editor = baseInfo.edit();
                    editor.putString(ActivityControlCenter.KEY_LOCATION, str);
                    editor.apply();
                    InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    view.findViewById(R.id.focus_container).requestFocus();
                    handled = true;
                }
                return handled;
            }
        });
        item_location.setOnFocusChangeListener(this);

        item_keywords = (TextView) view.findViewById(R.id.base_info_keywords);
        item_keywords.setText(baseInfo.getString(ActivityControlCenter.KEY_KEYWORDS, ""));
        item_keywords.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    String str = v.getText().toString();
                    SharedPreferences.Editor editor = baseInfo.edit();
                    editor.putString(ActivityControlCenter.KEY_KEYWORDS, str);
                    editor.apply();
                    InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    view.findViewById(R.id.focus_container).requestFocus();
                    handled = true;
                }
                return handled;
            }
        });
        item_keywords.setOnFocusChangeListener(this);

        item_name_overt = (ToggleButton) view.findViewById(R.id.base_info_name_overt);
        item_name_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_NAME_OVERT, false));
        setBaseInfoNameOvertListener();

        item_gender_overt = (ToggleButton) view.findViewById(R.id.base_info_gender_overt);
        item_gender_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_GENDER_OVERT, false));
        setBaseInfoGenderOvertListener();

        item_birthday_overt = (ToggleButton) view.findViewById(R.id.base_info_birthday_overt);
        item_birthday_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_BIRTHDAY_OVERT, false));
        setBaseInfoBirthdayOvertListener();

        item_homeland_overt = (ToggleButton) view.findViewById(R.id.base_info_homeland_overt);
        item_homeland_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_HOMELAND_OVERT, false));
        setBaseInfoHomelandOvertListener();

        item_location_overt = (ToggleButton) view.findViewById(R.id.base_info_location_overt);
        item_location_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_LOCATION_OVERT, false));
        setBaseInfoLocationOvertListener();

        item_keywords_overt = (ToggleButton) view.findViewById(R.id.base_info_keywords_overt);
        item_keywords_overt.setChecked(baseInfo.getBoolean(ActivityControlCenter.KEY_KEYWORDS_OVERT, false));
        setBaseInfoKeywordsOvertListener();

        item_goto_contact_info = (Button) view.findViewById(R.id.textView9);
        item_goto_contact_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToContactInfo(v);
            }
        });
        item_goto_edu_info = (Button) view.findViewById(R.id.textView10);
        item_goto_edu_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEducationInfo(v);
            }
        });
        item_goto_hobby_info = (Button) view.findViewById(R.id.textView11);
        item_goto_hobby_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHobbyInfo(v);
            }
        });

        return view;
	}

    public void onFocusChange(View v, boolean hasFocus){
        if(v==item_name && !hasFocus){
            String str = ((TextView)v).getText().toString();
            SharedPreferences.Editor editor = baseInfo.edit();
            editor.putString(ActivityControlCenter.KEY_NAME, str);
            editor.apply();
        }else if(v==item_homeland && !hasFocus){
            String str = ((TextView)v).getText().toString();
            SharedPreferences.Editor editor = baseInfo.edit();
            editor.putString(ActivityControlCenter.KEY_HOMELAND, str);
            editor.apply();
        }else if(v==item_location && !hasFocus){
            String str = ((TextView)v).getText().toString();
            SharedPreferences.Editor editor = baseInfo.edit();
            editor.putString(ActivityControlCenter.KEY_LOCATION, str);
            editor.apply();
        }else if(v==item_keywords&& !hasFocus){
            String str = ((TextView)v).getText().toString();
            SharedPreferences.Editor editor = baseInfo.edit();
            editor.putString(ActivityControlCenter.KEY_KEYWORDS, str);
            editor.apply();
        }
    }
}
