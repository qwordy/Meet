package sjtu.se.Activity.Search;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Activity.ChatPlatform.ChatActivity;
import sjtu.se.Activity.Information.ShowInformation;
import sjtu.se.Meet.R;
import sjtu.se.UserInformation.Information;
import sjtu.se.Util.TaskService;

import java.util.ArrayList;

public class DevBluetoothAdapter extends RecyclerView.Adapter<DevBluetoothAdapter.ViewHolder> {
    private ArrayList<DevBluetooth> lst;
    private Context ctx;
    public static Handler mHandler;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        public TextView nickname;
        public TextView information;
        public ImageView gender;
        public ArrayList<DevBluetooth> list;
        public Context ctx;
        public DevBluetooth dev;

        public ViewHolder(ViewGroup v,ArrayList<DevBluetooth> lst,Context context) {
            super(v);
            list = lst;
            ctx=context;
            gender = (ImageView)v.getChildAt(0);
            nickname = (TextView)v.getChildAt(1);
            information = (TextView)v.getChildAt(2);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        public void onClick(View v) {
            dev=list.get(getAdapterPosition());

            Bundle bundle = new Bundle();
            Information info = new Information(dev.Info);
            bundle.putParcelable("information", info);
            Intent intent = new Intent(ctx, ShowInformation.class);
            intent.putExtras(bundle);
            ctx.startActivity(intent);
        }

        public boolean onLongClick(View view) {
            dev=list.get(getAdapterPosition());
            if(dev.mRemoteDevice == null) return true;

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            String nick = dev.Info.baseinfo.Nick;
            builder.setMessage("确定与 "+ nick +" 建立连接么？");
            builder.setTitle("提示");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    TaskService.newTask(new TaskService.Task(mHandler, TaskService.Task.TASK_START_CONN_THREAD, new Object[]{dev.mRemoteDevice}));
                    Toast tst = Toast.makeText(ctx, "正在连接请稍等...", Toast.LENGTH_LONG);
                    tst.setGravity(Gravity.CENTER | Gravity.TOP, 0, 240);
                    tst.show();

                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return true;
        }
    }

    public DevBluetoothAdapter(Context context,ArrayList<DevBluetooth> l,Handler handler){
        this.lst=l;
        this.ctx = context;
        this.mHandler = handler;
    }

    public long getItemId(int position) {
        return position;
    }

    public DevBluetoothAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ViewGroup v = (ViewGroup)LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v,lst,ctx);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DevBluetooth dev = lst.get(position);
        if(dev.Info.baseinfo.Gender.equals("0")){
            holder.gender.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.boy_24dp));
        }else if(dev.Info.baseinfo.Gender.equals("1")){
            holder.gender.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.girl_24dp));
        }/*else{
            holder.gender.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.gender_24dp));
        }*/
        holder.nickname.setText(dev.Info.baseinfo.Nick);
        holder.information.setText(dev.Info.keywords);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lst.size();
    }
}
