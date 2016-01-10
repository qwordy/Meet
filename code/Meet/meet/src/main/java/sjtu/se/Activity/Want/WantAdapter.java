package sjtu.se.Activity.Want;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Meet.R;
import sjtu.se.UserInformation.Want;

import java.util.ArrayList;

public class WantAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private SharedPreferences wantSettings;
    private ArrayList<Want> lst;

    public WantAdapter(Context c,ArrayList<Want> list) {
        this.lst=list;
        mContext = c;
        this.mInflater = LayoutInflater.from(c);
        wantSettings = mContext.getSharedPreferences(ActivityControlCenter.WANT_SETTINGS, 0);
    }

    public int getCount() {
        return lst.size()+1;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new View for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView wantView;
        Want want;
        String unname = "未命名";
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            wantView = (TextView)mInflater.inflate(R.layout.want_list_item,null);
        } else {
            wantView = (TextView) convertView;
        }
        if(position==0){
            Drawable topDrawable = mContext.getResources().getDrawable(R.drawable.ic_add_24dp);
            topDrawable.setBounds(0,0,topDrawable.getMinimumWidth(),topDrawable.getMinimumHeight());
            wantView.setCompoundDrawables(null,topDrawable,null,null);
        }else{
            want=lst.get(position-1);
            wantView.setCompoundDrawables(null,null,null,null);
            if(want==null || want.tableName.equals("")) {
                wantView.setText(unname);
            }
            else
                wantView.setText(want.tableName);
            //String txt = "";
            //txt = wantSettings.getString(mTitles[position - 1], "");
            //want = Want.parseWant(txt);
            want = lst.get(position-1);
            /*if (want == null || want.isEmpty())
                wantView.setVisibility(View.GONE);
            else if(want.tableName.equals("")) {
            wantView.setVisibility(View.VISIBLE);
            wantView.setText(unname);
        }
        else {
            wantView.setVisibility(View.VISIBLE);*/
            //wantView.setText(want.tableName);
            //}
        }
        return wantView;
    }

    private String[] mTitles = {
        ActivityControlCenter.KEY_WANT_1, ActivityControlCenter.KEY_WANT_2,
            ActivityControlCenter.KEY_WANT_3,ActivityControlCenter.KEY_WANT_4,
            ActivityControlCenter.KEY_WANT_5,ActivityControlCenter.KEY_WANT_6,
            ActivityControlCenter.KEY_WANT_7,ActivityControlCenter.KEY_WANT_8
    };

    /*private int[] ids = {
            R.id.wantSlot1, R.id.wantSlot2,
            R.id.wantSlot3,R.id.wantSlot4,
            R.id.wantSlot5,R.id.wantSlot6,
            R.id.wantSlot7,R.id.wantSlot8
    };*/

}
