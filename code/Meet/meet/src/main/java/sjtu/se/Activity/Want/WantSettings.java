package sjtu.se.Activity.Want;

import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.UserInformation.Want;
import sjtu.se.Meet.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class WantSettings extends android.support.v4.app.Fragment{

    private Context ctx;
    private View view;

    private SharedPreferences wantSettings;

    private TextView want1;
    private TextView want2;
    private TextView want3;
    private TextView want4;
    private TextView want5;
    private TextView want6;
    private TextView want7;
    private TextView want8;

    private WantAdapter madapter;

    private String[] mTitles = {
            ActivityControlCenter.KEY_WANT_1, ActivityControlCenter.KEY_WANT_2,
            ActivityControlCenter.KEY_WANT_3,ActivityControlCenter.KEY_WANT_4,
            ActivityControlCenter.KEY_WANT_5,ActivityControlCenter.KEY_WANT_6,
            ActivityControlCenter.KEY_WANT_7,ActivityControlCenter.KEY_WANT_8
    };

    public void CreateWant(){
        String msg = "";
        String key = "";
        Want want = null;
        String txt = "";
        int position=0;
        Log.v("0", "0");
        while(position<8){
            txt = wantSettings.getString(mTitles[position], "");
            want = Want.parseWant(txt);
            position++;
            if (want == null || want.isEmpty()){
                break;
            }
        }
        msg = wantSettings.getString(mTitles[position - 1], "");
        key = mTitles[position - 1];
        Intent intent = new Intent(ctx, ShowWants.class);
        intent.putExtra("message", msg);
        intent.putExtra("key", key);
        ctx.startActivity(intent);
    }

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

        Intent intent = new Intent(ctx, ShowWants.class);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.want_settings, container, false);
        ctx=getActivity();

        ActivityControlCenter.WANTS_MAY_CHANGED = true;

        wantSettings = ctx.getSharedPreferences(ActivityControlCenter.WANT_SETTINGS, 0);

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        madapter = new WantAdapter(ctx);
        gridview.setAdapter(madapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Log.v("0",Integer.toString(position));
                if(position==0){
                    madapter.notifyDataSetChanged();
                    CreateWant();
                }
            }
        });

        want1 = (TextView)view.findViewById(R.id.wantSlot1);
        want1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrModify(v);
            }
        });
        want2 = (TextView)view.findViewById(R.id.wantSlot2);
        want2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrModify(v);
            }
        });
        want3 = (TextView)view.findViewById(R.id.wantSlot3);
        want3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrModify(v);
            }
        });
        want4 = (TextView)view.findViewById(R.id.wantSlot4);
        want4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrModify(v);
            }
        });
        want5 = (TextView)view.findViewById(R.id.wantSlot5);
        want5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrModify(v);
            }
        });
        want6 = (TextView)view.findViewById(R.id.wantSlot6);
        want6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrModify(v);
            }
        });
        want7 = (TextView)view.findViewById(R.id.wantSlot7);
        want7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrModify(v);
            }
        });
        want8 = (TextView)view.findViewById(R.id.wantSlot8);
        want8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrModify(v);
            }
        });

        return view;
    }

    public void onResume(){
        super.onResume();
        //System.out.println("Resuming........................................");
        this.updateText();
    }
}
