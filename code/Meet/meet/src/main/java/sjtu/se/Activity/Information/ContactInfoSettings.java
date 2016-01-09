package sjtu.se.Activity.Information;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import sjtu.se.Meet.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.CompoundButton.OnCheckedChangeListener;
import sjtu.se.Activity.ActivityControlCenter;

public class ContactInfoSettings extends AppCompatActivity
implements TextView.OnEditorActionListener,View.OnFocusChangeListener {

    private Context ctx = this;

    private SharedPreferences contactInfo;
    private TextView item_phone;
    private TextView item_qq;
    private TextView item_email;
    private TextView item_weibo;
    private TextView item_wechat;

    private ToggleButton item_phone_overt;
    private ToggleButton item_qq_overt;
    private ToggleButton item_email_overt;
    private ToggleButton item_weibo_overt;
    private ToggleButton item_wechat_overt;

    private ViewGroup container;

    public void setContactInfoPhoneOvertListener(){
        item_phone_overt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = contactInfo.edit();
                editor.putBoolean(ActivityControlCenter.KEY_PHONE_OVERT, isChecked);
                editor.commit();
            }
        });
    }

    public void setContactInfoQQOvertListener(){
        item_qq_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = contactInfo.edit();
                editor.putBoolean(ActivityControlCenter.KEY_QQ_OVERT, isChecked);
                editor.commit();
            }
        });
    }

    public void setContactInfoEmailOvertListener(){
        item_email_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = contactInfo.edit();
                editor.putBoolean(ActivityControlCenter.KEY_EMAIL_OVERT, isChecked);
                editor.commit();
            }
        });
    }

    public void setContactInfoWeiboOvertListener(){
        item_weibo_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = contactInfo.edit();
                editor.putBoolean(ActivityControlCenter.KEY_WEIBO_OVERT, isChecked);
                editor.commit();
            }
        });
    }

    public void setContactInfoWechatOvertListener(){
        item_wechat_overt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = contactInfo.edit();
                editor.putBoolean(ActivityControlCenter.KEY_WECHAT_OVERT, isChecked);
                editor.commit();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_info);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ActivityControlCenter.PERSONAL_INFO_MAY_CHANGED = true;

        contactInfo = getSharedPreferences(ActivityControlCenter.PERSONAL_CONTACT_INFO, 0);

        container = (ViewGroup)findViewById(R.id.focus_container);
        container.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch (View v, MotionEvent event){
                InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                v.requestFocus();
                return true;
            }
        });

        item_phone = (TextView) findViewById(R.id.contact_info_phone);
        item_phone.setText(contactInfo.getString(ActivityControlCenter.KEY_PHONE, ""));
        item_phone.setOnEditorActionListener(this);
        item_phone.setOnFocusChangeListener(this);
        item_qq = (TextView) findViewById(R.id.contact_info_qq);
        item_qq.setText(contactInfo.getString(ActivityControlCenter.KEY_QQ, ""));
        item_qq.setOnEditorActionListener(this);
        item_qq.setOnFocusChangeListener(this);
        item_email = (TextView) findViewById(R.id.contact_info_email);
        item_email.setText(contactInfo.getString(ActivityControlCenter.KEY_EMAIL, ""));
        item_email.setOnEditorActionListener(this);
        item_email.setOnFocusChangeListener(this);
        item_weibo = (TextView) findViewById(R.id.contact_info_weibo);
        item_weibo.setText(contactInfo.getString(ActivityControlCenter.KEY_WEIBO, ""));
        item_weibo.setOnEditorActionListener(this);
        item_weibo.setOnFocusChangeListener(this);
        item_wechat = (TextView) findViewById(R.id.contact_info_wechat);
        item_wechat.setText(contactInfo.getString(ActivityControlCenter.KEY_WECHAT, ""));
        item_wechat.setOnEditorActionListener(this);
        item_wechat.setOnFocusChangeListener(this);

        item_phone_overt = (ToggleButton) findViewById(R.id.contact_info_phone_overt);
        item_phone_overt.setChecked(contactInfo.getBoolean(ActivityControlCenter.KEY_PHONE_OVERT, false));
        setContactInfoPhoneOvertListener();

        item_qq_overt = (ToggleButton) findViewById(R.id.contact_info_qq_overt);
        item_qq_overt.setChecked(contactInfo.getBoolean(ActivityControlCenter.KEY_QQ_OVERT, false));
        setContactInfoQQOvertListener();

        item_email_overt = (ToggleButton) findViewById(R.id.contact_info_email_overt);
        item_email_overt.setChecked(contactInfo.getBoolean(ActivityControlCenter.KEY_EMAIL_OVERT, false));
        setContactInfoEmailOvertListener();

        item_weibo_overt = (ToggleButton) findViewById(R.id.contact_info_weibo_overt);
        item_weibo_overt.setChecked(contactInfo.getBoolean(ActivityControlCenter.KEY_WEIBO_OVERT, false));
        setContactInfoWeiboOvertListener();

        item_wechat_overt = (ToggleButton) findViewById(R.id.contact_info_wechat_overt);
        item_wechat_overt.setChecked(contactInfo.getBoolean(ActivityControlCenter.KEY_WECHAT_OVERT, false));
        setContactInfoWechatOvertListener();
    }

    public boolean onEditorAction (TextView v, int actionId, KeyEvent event){
        boolean handled = false;
        String ControlCenter_KEY="";
        if(v==item_phone){
            ControlCenter_KEY=ActivityControlCenter.KEY_PHONE;
        }else if(v==item_qq){
            ControlCenter_KEY=ActivityControlCenter.KEY_QQ;
        }else if(v==item_email){
            ControlCenter_KEY=ActivityControlCenter.KEY_EMAIL;
        }else if(v==item_weibo){
            ControlCenter_KEY=ActivityControlCenter.KEY_WEIBO;
        }else if(v==item_wechat){
            ControlCenter_KEY=ActivityControlCenter.KEY_WECHAT;
        }
        if (!ControlCenter_KEY.equals("") && actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE
                || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
            String str = v.getText().toString();
            SharedPreferences.Editor editor = contactInfo.edit();
            editor.putString(ControlCenter_KEY, str);
            editor.apply();
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            findViewById(R.id.focus_container).requestFocus();
            //Log.e(" ", "没有响应");
            handled = true;
        }
        return handled;
    }

    public void onFocusChange(View v, boolean hasFocus){
        if(v==item_phone && !hasFocus){
            String str = ((TextView)v).getText().toString();
            SharedPreferences.Editor editor = contactInfo.edit();
            editor.putString(ActivityControlCenter.KEY_PHONE, str);
            editor.apply();
        }else if(v==item_qq && !hasFocus){
            String str = ((TextView)v).getText().toString();
            SharedPreferences.Editor editor = contactInfo.edit();
            editor.putString(ActivityControlCenter.KEY_QQ, str);
            editor.apply();
        }else if(v==item_email && !hasFocus){
            String str = ((TextView)v).getText().toString();
            SharedPreferences.Editor editor = contactInfo.edit();
            editor.putString(ActivityControlCenter.KEY_EMAIL, str);
            editor.apply();
        }else if(v==item_weibo){
            String str = ((TextView)v).getText().toString();
            SharedPreferences.Editor editor = contactInfo.edit();
            editor.putString(ActivityControlCenter.KEY_WEIBO, str);
            editor.apply();
        }else if(v==item_wechat) {
            String str = ((TextView) v).getText().toString();
            SharedPreferences.Editor editor = contactInfo.edit();
            editor.putString(ActivityControlCenter.KEY_WECHAT, str);
            editor.apply();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
