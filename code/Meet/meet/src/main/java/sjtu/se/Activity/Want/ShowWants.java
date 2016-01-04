package sjtu.se.Activity.Want;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.UserInformation.Want;
import sjtu.se.Activity.Setting.SystemSettings;
import sjtu.se.Meet.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class ShowWants extends AppCompatActivity {

    private Context ctx;
    private SharedPreferences wantSettings;
    private String key;
    private Want want;

    private TextView tablename;
    private TextView gender;
    private TextView age;
    private TextView homeland;
    private TextView location;
    private TextView college;
    private TextView high;
    private TextView middle;
    private TextView primary;
    private TextView keywords;

    public void wantTableEdit(View view){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
        final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
        String tmp = wantSettings.getString(key, "");
        want = Want.parseWant(tmp);
        if (want == null)
            want = new Want();
        et.setText(want.tableName);

        new AlertDialog.Builder(ctx).setTitle("设置表名").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        want.tableName = str;
                        SharedPreferences.Editor editor = wantSettings.edit();
                        editor.putString(key, want.toString());
                        editor.commit();
                        tablename.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    public void wantGenderEdit(View view){
        new AlertDialog.Builder(this).setTitle("选择性别")
                .setSingleChoiceItems(new String[]{"男", "女", "不限"}, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String str = String.valueOf(which);
                        if(which == 2)
                            want.gender = "";
                        else
                            want.gender = str;
                        SharedPreferences.Editor editor = wantSettings.edit();
                        editor.putString(key, want.toString());
                        editor.commit();
                        gender.setText(ActivityControlCenter.genders[Integer.parseInt(str)]);
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", null).show();
    }

    public void wantAgeEdit(View view){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.text_edit_age_range, (ViewGroup) findViewById(R.layout.text_edit_age_range));
        final EditText et1 = (EditText) layout.findViewById(R.id.edittext_age_from);
        final EditText et2 = (EditText) layout.findViewById(R.id.edittext_age_to);
        String tmp = wantSettings.getString(key, "");
        want = Want.parseWant(tmp);
        if (want == null)
            want = new Want();
        //et.setText(want.Homeland);
        tmp = want.ageRange;
        if (tmp.equals("")){
            et1.setText("");
            et2.setText("");
        }
        else{
            String[] ages = tmp.split("-");
            et1.setText(ages[0]);
            et2.setText(ages[1]);
        }

        new AlertDialog.Builder(ctx).setTitle("设置年龄范围").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str1 = et1.getText().toString();
                        String str2 = et2.getText().toString();
                        String str = str1 + "-" + str2;
                        if (str1.equals("") && str2.equals(""))
                            str = "";
                        else if (str1.equals(""))
                            str = "0-" + str2;
                        else if (str2.equals(""))
                            str = str1 + "-999";
                        want.ageRange = str;
                        SharedPreferences.Editor editor = wantSettings.edit();
                        editor.putString(key, want.toString());
                        editor.commit();
                        age.setText(str);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    public void wantHomelandEdit(View view){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
        final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
        String tmp = wantSettings.getString(key, "");
        want = Want.parseWant(tmp);
        if (want == null)
            want = new Want();
        et.setText(want.Homeland);

        new AlertDialog.Builder(ctx).setTitle("设置籍贯").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        want.Homeland = str;
                        SharedPreferences.Editor editor = wantSettings.edit();
                        editor.putString(key, want.toString());
                        editor.commit();
                        homeland.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    public void wantLocationEdit(View view){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
        final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
        String tmp = wantSettings.getString(key, "");
        want = Want.parseWant(tmp);
        if (want == null)
            want = new Want();
        et.setText(want.Location);

        new AlertDialog.Builder(ctx).setTitle("设置居住地").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        want.Location = str;
                        SharedPreferences.Editor editor = wantSettings.edit();
                        editor.putString(key, want.toString());
                        editor.commit();
                        location.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    public void wantCollegeEdit(View view){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
        final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
        String tmp = wantSettings.getString(key, "");
        want = Want.parseWant(tmp);
        if (want == null)
            want = new Want();
        et.setText(want.College);

        new AlertDialog.Builder(ctx).setTitle("设置大学").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        want.College = str;
                        SharedPreferences.Editor editor = wantSettings.edit();
                        editor.putString(key, want.toString());
                        editor.commit();
                        college.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    public void wantHighEdit(View view){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
        final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
        String tmp = wantSettings.getString(key, "");
        want = Want.parseWant(tmp);
        if (want == null)
            want = new Want();
        et.setText(want.High_School);

        new AlertDialog.Builder(ctx).setTitle("设置高中").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        want.High_School = str;
                        SharedPreferences.Editor editor = wantSettings.edit();
                        editor.putString(key, want.toString());
                        editor.commit();
                        high.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    public void wantMiddleEdit(View view){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
        final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
        String tmp = wantSettings.getString(key, "");
        want = Want.parseWant(tmp);
        if (want == null)
            want = new Want();
        et.setText(want.Middle_School);

        new AlertDialog.Builder(ctx).setTitle("设置中学").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        want.Middle_School = str;
                        SharedPreferences.Editor editor = wantSettings.edit();
                        editor.putString(key, want.toString());
                        editor.commit();
                        middle.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    public void wantPrimaryEdit(View view){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.text_edit_place_16, (ViewGroup) findViewById(R.layout.text_edit_place_16));
        final EditText et = (EditText) layout.findViewById(R.id.edittext_place_16);
        String tmp = wantSettings.getString(key, "");
        want = Want.parseWant(tmp);
        if (want == null)
            want = new Want();
        et.setText(want.Primary_School);

        new AlertDialog.Builder(ctx).setTitle("设置小学").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        want.Primary_School = str;
                        SharedPreferences.Editor editor = wantSettings.edit();
                        editor.putString(key, want.toString());
                        editor.commit();
                        primary.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    public void wantKeywordsEdit(View view){
        final EditText et = new EditText(ctx);
        String tmp = wantSettings.getString(key, "");
        want = Want.parseWant(tmp);
        if (want == null)
            want = new Want();
        et.setText(want.keywords);

        new AlertDialog.Builder(ctx).setTitle("设置关键词").setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String str = et.getText().toString();
                        want.keywords = str;
                        SharedPreferences.Editor editor = wantSettings.edit();
                        editor.putString(key, want.toString());
                        editor.commit();
                        keywords.setText(str);
                    }
                })
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {}
                }).show();
    }

    private void updateWants(){
        String str = want.tableName;
        if (str.equals(""))
            tablename.setText("未命名");
        else
            tablename.setText(str);
        str = want.gender;
        if (str.equals("0") || str.equals("1"))
            gender.setText(ActivityControlCenter.genders[Integer.parseInt(str)]);
        else
            gender.setText("");
        age.setText(want.ageRange);
        homeland.setText(want.Homeland);
        location.setText(want.Location);
        college.setText(want.College);
        high.setText(want.High_School);
        middle.setText(want.Middle_School);
        primary.setText(want.Primary_School);
        keywords.setText(want.keywords);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_wants);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ctx = this;

        wantSettings = getSharedPreferences(ActivityControlCenter.WANT_SETTINGS, 0);
        key = this.getIntent().getStringExtra("key");

        tablename = (TextView)findViewById(R.id.want_tablename);
        gender = (TextView)findViewById(R.id.want_gender);
        age = (TextView)findViewById(R.id.want_age);
        homeland = (TextView)findViewById(R.id.want_homeland);
        location = (TextView)findViewById(R.id.want_location);
        college = (TextView)findViewById(R.id.want_college);
        high = (TextView)findViewById(R.id.want_high);
        middle = (TextView)findViewById(R.id.want_middle);
        primary = (TextView)findViewById(R.id.want_primary);
        keywords = (TextView)findViewById(R.id.want_keywords);

    }

    protected void onResume(){
        super.onResume();
        String msg = this.getIntent().getStringExtra("message");
        want = Want.parseWant(msg);
        if (want == null)
            want = new Want();

        this.updateWants();
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
