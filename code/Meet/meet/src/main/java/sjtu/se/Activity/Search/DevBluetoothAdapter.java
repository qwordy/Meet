package sjtu.se.Activity.Search;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import sjtu.se.Meet.R;
import sjtu.se.UserInformation.Information;

import java.util.ArrayList;
import java.util.Date;

public class DevBluetoothAdapter extends BaseAdapter {

    static class ViewHolder{
        public TextView address;
        public TextView information;
    };

    private LayoutInflater mInflater = null;
    private ArrayList<DevBluetooth> lst;

    public DevBluetoothAdapter(Context context, ArrayList<DevBluetooth> l){
        this.lst = l;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int position) {
        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<DevBluetooth> getList(){
        return lst;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.device_list_item, null);
            holder.address = (TextView)convertView.findViewById(R.id.Address);
            holder.information = (TextView)convertView.findViewById(R.id.Information);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.address.setText(lst.get(position).Info.baseinfo.Nick);
        holder.information.setText(lst.get(position).Address);
        return convertView;
    }

    public void add(String Addr, String Info, Information in,BluetoothDevice device){
        int size = lst.size();
        for (int i=0; i<size;i++){
            if (Addr.equals(lst.get(i).Address)){
                lst.set(i, new DevBluetooth(Addr, Info, in, device));
                this.notifyDataSetChanged();
                return;
            }
        }
        lst.add(new DevBluetooth(Addr, Info, in, device));
        this.notifyDataSetChanged();
    }

    public void reset(){
        int size = lst.size();
        for (int i=0;i<size;i++){
            DevBluetooth dev = lst.get(i);
            if ((new Date()).getTime() - dev.FoundTime.getTime() >= 10000){
                lst.remove(i);
                size--;
                i--;
            }
        }
        this.notifyDataSetChanged();
    }
};
