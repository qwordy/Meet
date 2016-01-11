package sjtu.se.Activity.Want;

import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
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
    private int[] order = new int[8];
    private GridView gridview;
    private Runnable run;

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
        String txt="";
        int position=0;
        while(position<8){
            txt = wantSettings.getString(mTitles[position], "");
            want = Want.parseWant(txt);
            if (want == null || want.isEmpty()){
                want_list.add(want);
                order[want_list.size()-1]=position;
                break;
            }else
                position++;
        }
        if(position<8){
            msg = txt;
            key = mTitles[position];
            madapter.notifyDataSetChanged();
            Intent intent = new Intent(ctx, ShowWants.class);
            intent.putExtra("message", msg);
            intent.putExtra("key", key);
            ctx.startActivity(intent);
        }
    }

    public void Modify(View view,int position){
        String msg = wantSettings.getString(mTitles[order[position-1]], "");
        String key = mTitles[order[position-1]];
        Intent intent = new Intent(ctx, ShowWants.class);
        intent.putExtra("message", msg);
        intent.putExtra("key", key);
        ctx.startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.want_settings, container, false);
        ctx=getActivity();

        ActivityControlCenter.WANTS_MAY_CHANGED = true;

        wantSettings = ctx.getSharedPreferences(ActivityControlCenter.WANT_SETTINGS, 0);
        want_list= createWantList();

        gridview = (GridView) view.findViewById(R.id.gridview);
        madapter = new WantAdapter(ctx,want_list);
        gridview.setAdapter(madapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //Log.v("0",Integer.toString(position));
                if (position == 0) {
                    CreateWant();
                } else {
                    Modify(v, position);
                }
            }
        });

        return view;
    }

    public ArrayList<Want> createWantList(){
        int position=0,n=0;
        String txt="";
        Want want;
        want_list=new ArrayList<Want>();
        while(position<8){
            txt = wantSettings.getString(mTitles[position], "");
            want = Want.parseWant(txt);
            position++;
            if (want == null || want.isEmpty()){
                continue;
            }else{
                want_list.add(want);
                order[n++]=position-1;
            }
        }
        return want_list;
    }

    public View getViewByPosition(int pos, GridView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public void updateTitles(){
        int i;
        TextView view;
        for(i=0;i<want_list.size();i++){
            view = (TextView)getViewByPosition(i+1,gridview);
            view.setText(want_list.get(i).tableName);
        }
    }

    public void onResume(){
        super.onResume();
        //updateTitles();
        //System.out.println("Resuming........................................");
        //wantSettings = ctx.getSharedPreferences(ActivityControlCenter.WANT_SETTINGS, 0);
        //want_list= createWantList();
        //madapter.notifyDataSetChanged();
        String unname = "未命名";
        String txt;
        Want want;
        int i;
        TextView view;
        for(i=0;i<want_list.size();i++){
            txt = wantSettings.getString(mTitles[order[i]], "");
            want = Want.parseWant(txt);
            view = (TextView)getViewByPosition(i+1,gridview);
            if (want == null ||want.tableName.equals(""))
                view.setText(unname);
            else
                view.setText(want.tableName);
            want_list.set(i,want);
        }
    }
}
