package sjtu.se.Activity.Want;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Activity.Information.BaseInfoSettings;
import sjtu.se.UserInformation.Want;
import sjtu.se.Activity.Setting.SystemSettings;
import sjtu.se.Meet.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

public class WantSettings extends AppCompatActivity{

    private Context ctx = this;

    private SharedPreferences wantSettings;

    private TextView want1;
    private TextView want2;
    private TextView want3;
    private TextView want4;
    private TextView want5;
    private TextView want6;
    private TextView want7;
    private TextView want8;

    public void CreateOrModify(View view){
        //System.out.println("hello paler!");
        //System.out.println(((TextView)view).getId()-R.id.wantSlot1);
        String msg = "";
        String key = "";
        int id = ((TextView)view).getId();
        switch(id){
            case R.id.wantSlot1:{
                msg = wantSettings.getString(ActivityControlCenter.KEY_WANT_1, "");
                key = ActivityControlCenter.KEY_WANT_1;
                break;
            }
            case R.id.wantSlot2:{
                msg = wantSettings.getString(ActivityControlCenter.KEY_WANT_2, "");
                key = ActivityControlCenter.KEY_WANT_2;
                break;
            }
            case R.id.wantSlot3:{
                msg = wantSettings.getString(ActivityControlCenter.KEY_WANT_3, "");
                key = ActivityControlCenter.KEY_WANT_3;
                break;
            }
            case R.id.wantSlot4:{
                msg = wantSettings.getString(ActivityControlCenter.KEY_WANT_4, "");
                key = ActivityControlCenter.KEY_WANT_4;
                break;
            }
            case R.id.wantSlot5:{
                msg = wantSettings.getString(ActivityControlCenter.KEY_WANT_5, "");
                key = ActivityControlCenter.KEY_WANT_5;
                break;
            }
            case R.id.wantSlot6:{
                msg = wantSettings.getString(ActivityControlCenter.KEY_WANT_6, "");
                key = ActivityControlCenter.KEY_WANT_6;
                break;
            }
            case R.id.wantSlot7:{
                msg = wantSettings.getString(ActivityControlCenter.KEY_WANT_7, "");
                key = ActivityControlCenter.KEY_WANT_7;
                break;
            }
            case R.id.wantSlot8:{
                msg = wantSettings.getString(ActivityControlCenter.KEY_WANT_8, "");
                key = ActivityControlCenter.KEY_WANT_8;
                break;
            }
        }

        Intent intent = new Intent(WantSettings.this, ShowWants.class);
        intent.putExtra("message", msg);
        intent.putExtra("key", key);
        ctx.startActivity(intent);

        return;
    }

    private void updateText(){
        String defult = "新建";
        String unname = "未命名";
        String txt = "";
        Want want = null;

        txt = wantSettings.getString(ActivityControlCenter.KEY_WANT_1, "");
        want = Want.parseWant(txt);
        if (want == null)
            want1.setText(defult);
        else if(want.tableName.equals(""))
            want1.setText(unname);
        else
            want1.setText(want.tableName);

        txt = wantSettings.getString(ActivityControlCenter.KEY_WANT_2, "");
        want = Want.parseWant(txt);
        if (want == null)
            want2.setText(defult);
        else if(want.tableName.equals(""))
            want2.setText(unname);
        else
            want2.setText(want.tableName);

        txt = wantSettings.getString(ActivityControlCenter.KEY_WANT_3, "");
        want = Want.parseWant(txt);
        if (want == null)
            want3.setText(defult);
        else if(want.tableName.equals(""))
            want3.setText(unname);
        else
            want3.setText(want.tableName);

        txt = wantSettings.getString(ActivityControlCenter.KEY_WANT_4, "");
        want = Want.parseWant(txt);
        if (want == null)
            want4.setText(defult);
        else if(want.tableName.equals(""))
            want4.setText(unname);
        else
            want4.setText(want.tableName);

        txt = wantSettings.getString(ActivityControlCenter.KEY_WANT_5, "");
        want = Want.parseWant(txt);
        if (want == null)
            want5.setText(defult);
        else if(want.tableName.equals(""))
            want5.setText(unname);
        else
            want5.setText(want.tableName);

        txt = wantSettings.getString(ActivityControlCenter.KEY_WANT_6, "");
        want = Want.parseWant(txt);
        if (want == null)
            want6.setText(defult);
        else if(want.tableName.equals(""))
            want6.setText(unname);
        else
            want6.setText(want.tableName);

        txt = wantSettings.getString(ActivityControlCenter.KEY_WANT_7, "");
        want = Want.parseWant(txt);
        if (want == null)
            want7.setText(defult);
        else if(want.tableName.equals(""))
            want7.setText(unname);
        else
            want7.setText(want.tableName);

        txt = wantSettings.getString(ActivityControlCenter.KEY_WANT_8, "");
        want = Want.parseWant(txt);
        if (want == null)
            want8.setText(defult);
        else if(want.tableName.equals(""))
            want8.setText(unname);
        else
            want8.setText(want.tableName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.want_settings);
        ctx = this;

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ActivityControlCenter.WANTS_MAY_CHANGED = true;

        wantSettings = getSharedPreferences(ActivityControlCenter.WANT_SETTINGS, 0);

        want1 = (TextView)findViewById(R.id.wantSlot1);
        want2 = (TextView)findViewById(R.id.wantSlot2);
        want3 = (TextView)findViewById(R.id.wantSlot3);
        want4 = (TextView)findViewById(R.id.wantSlot4);
        want5 = (TextView)findViewById(R.id.wantSlot5);
        want6 = (TextView)findViewById(R.id.wantSlot6);
        want7 = (TextView)findViewById(R.id.wantSlot7);
        want8 = (TextView)findViewById(R.id.wantSlot8);
    }

    protected void onResume(){
        super.onResume();
        //System.out.println("Resuming........................................");
        this.updateText();
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
        int id = item.getItemId();
        if (id == R.id.action_news) {
            return false;
        }
        if (id == R.id.action_settings) {
            this.startActivity(new Intent(this, SystemSettings.class));
            return true;
        }
        /*if (id == R.id.action_personal){
            this.startActivity(new Intent(this, BaseInfoSettings.class));
            return true;
        }*/
        /*if (id == R.id.action_want){
            this.startActivity(new Intent(this, WantSettings.class));
            return true;
        }*/
        /*if (id == R.id.action_analysis){
            return false;
        }*/
        if (id == R.id.action_logout) {
            super.finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
