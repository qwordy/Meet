package sjtu.se.Activity.Information;

import android.view.KeyEvent;
import com.example.bluetoothtry.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Activity.Setting.SystemSettings;
import sjtu.se.Activity.Want.WantSettings;

public class ContactInfoSettings extends Activity {

    private Context ctx = this;

    private SharedPreferences contactInfo;
    private TextView item_phone;
    private TextView item_qq;
    private TextView item_email;
    private TextView item_weibo;
    private TextView item_socialnet;

    private Switch item_phone_overt;
    private Switch item_qq_overt;
    private Switch item_email_overt;
    private Switch item_weibo_overt;
    private Switch item_socialnet_overt;

    public void contactInfoPhoneEdit(View view){
        final EditText et = new EditText(ctx);
        et.setText(contactInfo.getString(ActivityControlCenter.KEY_PHONE, ""));
        et.setInputType(InputType.TYPE_CLASS_PHONE);

        new AlertDialog.Builder(ctx).setTitle("设置手机号").setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        SharedPreferences.Editor editor = contactInfo.edit();
                        editor.putString(ActivityControlCenter.KEY_PHONE, str);
                        editor.commit();
                        item_phone.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    public void contactInfoQQEdit(View view){
        final EditText et = new EditText(ctx);
        et.setText(contactInfo.getString(ActivityControlCenter.KEY_QQ, ""));
        et.setInputType(InputType.TYPE_CLASS_PHONE);

        new AlertDialog.Builder(ctx).setTitle("设置QQ号").setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        SharedPreferences.Editor editor = contactInfo.edit();
                        editor.putString(ActivityControlCenter.KEY_QQ, str);
                        editor.commit();
                        item_qq.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    public void contactInfoEmailEdit(View view){
        final EditText et = new EditText(ctx);
        et.setText(contactInfo.getString(ActivityControlCenter.KEY_EMAIL, ""));
        et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        new AlertDialog.Builder(ctx).setTitle("设置邮箱").setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        SharedPreferences.Editor editor = contactInfo.edit();
                        editor.putString(ActivityControlCenter.KEY_EMAIL, str);
                        editor.commit();
                        item_email.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    public void contactInfoWeiboEdit(View view){
        final EditText et = new EditText(ctx);
        et.setText(contactInfo.getString(ActivityControlCenter.KEY_WEIBO, ""));
        et.setInputType(InputType.TYPE_TEXT_VARIATION_URI);

        new AlertDialog.Builder(ctx).setTitle("设置微博").setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        SharedPreferences.Editor editor = contactInfo.edit();
                        editor.putString(ActivityControlCenter.KEY_WEIBO, str);
                        editor.commit();
                        item_weibo.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    public void contactInfoSocialnetEdit(View view){
        final EditText et = new EditText(ctx);
        et.setText(contactInfo.getString(ActivityControlCenter.KEY_SOCIALNET, ""));
        et.setInputType(InputType.TYPE_TEXT_VARIATION_URI);

        new AlertDialog.Builder(ctx).setTitle("设置个人主页").setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        SharedPreferences.Editor editor = contactInfo.edit();
                        editor.putString(ActivityControlCenter.KEY_SOCIALNET, str);
                        editor.commit();
                        item_socialnet.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

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

    public void setContactInfoSocialnetOvertListener(){
        item_socialnet_overt.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = contactInfo.edit();
                editor.putBoolean(ActivityControlCenter.KEY_SOCIALNET_OVERT, isChecked);
                editor.commit();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_info);

        this.getActionBar().setDisplayHomeAsUpEnabled(true);

        ActivityControlCenter.PERSONAL_INFO_MAY_CHANGED = true;

        contactInfo = getSharedPreferences(ActivityControlCenter.PERSONAL_CONTACT_INFO, 0);

        item_phone = (TextView) findViewById(R.id.contact_info_phone);
        item_phone.setText(contactInfo.getString(ActivityControlCenter.KEY_PHONE, ""));
        item_qq = (TextView) findViewById(R.id.contact_info_qq);
        item_qq.setText(contactInfo.getString(ActivityControlCenter.KEY_QQ, ""));
        item_email = (TextView) findViewById(R.id.contact_info_email);
        item_email.setText(contactInfo.getString(ActivityControlCenter.KEY_EMAIL, ""));
        item_weibo = (TextView) findViewById(R.id.contact_info_weibo);
        item_weibo.setText(contactInfo.getString(ActivityControlCenter.KEY_WEIBO, ""));
        item_socialnet = (TextView) findViewById(R.id.contact_info_socialnet);
        item_socialnet.setText(contactInfo.getString(ActivityControlCenter.KEY_SOCIALNET, ""));

        item_phone_overt = (Switch) findViewById(R.id.contact_info_phone_overt);
        item_phone_overt.setChecked(contactInfo.getBoolean(ActivityControlCenter.KEY_PHONE_OVERT, false));
        setContactInfoPhoneOvertListener();

        item_qq_overt = (Switch) findViewById(R.id.contact_info_qq_overt);
        item_qq_overt.setChecked(contactInfo.getBoolean(ActivityControlCenter.KEY_QQ_OVERT, false));
        setContactInfoQQOvertListener();

        item_email_overt = (Switch) findViewById(R.id.contact_info_email_overt);
        item_email_overt.setChecked(contactInfo.getBoolean(ActivityControlCenter.KEY_EMAIL_OVERT, false));
        setContactInfoEmailOvertListener();

        item_weibo_overt = (Switch) findViewById(R.id.contact_info_weibo_overt);
        item_weibo_overt.setChecked(contactInfo.getBoolean(ActivityControlCenter.KEY_WEIBO_OVERT, false));
        setContactInfoWeiboOvertListener();

        item_socialnet_overt = (Switch) findViewById(R.id.contact_info_socialnet_overt);
        item_socialnet_overt.setChecked(contactInfo.getBoolean(ActivityControlCenter.KEY_SOCIALNET_OVERT, false));
        setContactInfoSocialnetOvertListener();
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
        getMenuInflater().inflate(R.menu.contact_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            this.startActivity(new Intent(ContactInfoSettings.this, SystemSettings.class));
            return true;
        }
        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
