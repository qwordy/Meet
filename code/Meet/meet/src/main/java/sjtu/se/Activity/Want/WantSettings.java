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

import java.util.ArrayList;

public class WantSettings extends android.support.v4.app.Fragment{

    private Context ctx;
    private View view;

    private SharedPreferences wantSettings;

    private WantAdapter madapter;
    public ArrayList<Want> want_list;

    private String[] mTitles = {
            ActivityControlCenter.KEY_WANT_1, ActivityControlCenter.KEY_WANT_2,
            ActivityControlCenter.KEY_WANT_3,ActivityControlCenter.KEY_WANT_4,
            ActivityControlCenter.KEY_WANT_5,ActivityControlCenter.KEY_WANT_6,
            ActivityControlCenter.KEY_WANT_7,ActivityControlCenter.KEY_WANT_8
    };

    public void CreateWant(){
        String msg;
        String key;
        Want want;
        String txt;
        int position=want_list.size();
        if(position<8){
            txt = wantSettings.getString(mTitles[position], "");
            want = Want.parseWant(txt);
            want_list.add(want);
            msg = wantSettings.getString(mTitles[position], "");
            key = mTitles[position];
            madapter.notifyDataSetChanged();
            Intent intent = new Intent(ctx, ShowWants.class);
            intent.putExtra("message", msg);
            intent.putExtra("key", key);
            ctx.startActivity(intent);
        }
    }

    public void Modify(View view,int position){
        String msg = wantSettings.getString(mTitles[position-1], "");
        String key = mTitles[position-1];
        Intent intent = new Intent(ctx, ShowWants.class);
        intent.putExtra("message", msg);
        intent.putExtra("key", key);
        ctx.startActivity(intent);
    }

    private void updateText(){
        String unname = "未命名";
        String txt = "";
        Want want = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.want_settings, container, false);
        ctx=getActivity();

        ActivityControlCenter.WANTS_MAY_CHANGED = true;

        wantSettings = ctx.getSharedPreferences(ActivityControlCenter.WANT_SETTINGS, 0);
        int position=0;
        String txt="";
        Want want;
        want_list=new ArrayList<Want>();
        while(position<8){
            txt = wantSettings.getString(mTitles[position], "");
            want = Want.parseWant(txt);
            position++;
            if (want == null || want.isEmpty()){
                continue;
            }
            want_list.add(want);
        }

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        madapter = new WantAdapter(ctx,want_list);
        gridview.setAdapter(madapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //Log.v("0",Integer.toString(position));
                if(position==0){
                    CreateWant();
                }else{
                    Modify(v,position);
                }
            }
        });

        return view;
    }

    public void onResume(){
        super.onResume();
        //System.out.println("Resuming........................................");
        //this.updateText();
    }
}
