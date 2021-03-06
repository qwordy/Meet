package sjtu.se.Activity.Search;

import android.app.*;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.*;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.*;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Activity.ChatPlatform.ChatActivity;
import sjtu.se.Meet.R;
import sjtu.se.UserInformation.Information;
import sjtu.se.UserInformation.Want;
import sjtu.se.Util.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class SearchFragment extends android.support.v4.app.Fragment {

    private static final int REQUEST_FOR_ENABLE = 1;

    private Want want1;
    private Want want2;
    private Want want3;
    private Want want4;
    private Want want5;
    private Want want6;
    private Want want7;
    private Want want8;

    private View view;
    private Context ctx;

    private Information overt_user;
    private Information full_user;
    private IntentFilter intentFilter;

    public BluetoothAdapter mBluetoothAdapter;

    private RecyclerView DeviceList;
    private RecyclerView.Adapter DeviceListAdapter;
    private ArrayList<DevBluetooth> device_list;

    private RecyclerView RecommendDeviceList;
    private RecyclerView.Adapter RecommendDevListAdapter;
    private ArrayList<DevBluetooth> recommend_device_list;

    private RecyclerView HistoryDeviceList;
    private RecyclerView.Adapter HistoryDevListAdapter;
    private ArrayList<DevBluetooth> history_device_list;

    private SwipeRefreshLayout device_list_swipe;
    private SwipeRefreshLayout recomm_list_swipe;

    private ArrayList<DevBluetooth> OldRecommendList;

    private Button SearchShowBtn;
    private Button RecommendShowBtn;
    private Button FindShowBtn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_search, container, false);

        DeviceList = (RecyclerView) view.findViewById(R.id.DeviceList);
        DeviceList.setHasFixedSize(true);
        DeviceList.setLayoutManager(new LinearLayoutManager(ctx));
        device_list = new ArrayList<DevBluetooth>();
        DeviceListAdapter = new DevBluetoothAdapter(ctx, device_list, mHandler, search);
        DeviceList.setAdapter(DeviceListAdapter);

        RecommendDeviceList = (RecyclerView) view.findViewById(R.id.RecommendDeviceList);
        RecommendDeviceList.setHasFixedSize(true);
        RecommendDeviceList.setLayoutManager(new LinearLayoutManager(ctx));
        recommend_device_list = new ArrayList<DevBluetooth>();
        RecommendDevListAdapter = new DevBluetoothAdapter(ctx, recommend_device_list, mHandler, search);
        RecommendDeviceList.setAdapter(RecommendDevListAdapter);

        HistoryDeviceList = (RecyclerView) view.findViewById(R.id.HistoryDeviceList);
        HistoryDeviceList.setHasFixedSize(true);
        HistoryDeviceList.setLayoutManager(new LinearLayoutManager(ctx));
        history_device_list = new ArrayList<DevBluetooth>();
        HistoryDevListAdapter = new DevBluetoothAdapter(ctx, history_device_list, mHandler, search);
        HistoryDeviceList.setAdapter(HistoryDevListAdapter);

        HistoryDeviceList.setVisibility(View.GONE);
        ((View) RecommendDeviceList.getParent()).setVisibility(View.GONE);

        SearchShowBtn = (Button) (view.findViewById(R.id.SearchShowDev));
        RecommendShowBtn = (Button) (view.findViewById(R.id.RecommendShowDev));
        FindShowBtn = (Button) (view.findViewById(R.id.FindShowDev));

        SearchShowBtn.setOnClickListener(new ShowDeviceList());
        RecommendShowBtn.setOnClickListener(new ShowRecommendDeviceList());
        FindShowBtn.setOnClickListener(new ShowHistoryDeviceList());

        device_list_swipe = (SwipeRefreshLayout) view.findViewById(R.id.DL_fresh);
        recomm_list_swipe = (SwipeRefreshLayout) view.findViewById(R.id.RDL_fresh);
        setFreshing();

        OldRecommendList = new ArrayList<DevBluetooth>();

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getActivity();

        intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED);
        intentFilter.addAction(ActivityControlCenter.ACTIVITY_EXIT_ACTION);
        intentFilter.addAction(ActivityControlCenter.ACTION_LAUNCHED);

        overt_user = new Information();
        full_user = new Information();

        OpenBluetooth();
    }

    public void setFreshing() {
        device_list_swipe.setColorSchemeResources(R.color.primary);
        device_list_swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        UpdateDeviceList();
                        device_list_swipe.setRefreshing(false);
                    }
                }
        );
        recomm_list_swipe.setColorSchemeResources(R.color.primary);
        recomm_list_swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        UpdateDeviceList();
                        recomm_list_swipe.setRefreshing(false);
                    }
                }
        );
    }

    public void UpdateDeviceList() {
        listReset(device_list, DeviceListAdapter);
        listReset(recommend_device_list, RecommendDevListAdapter);
        Rename();
        doDiscovery();
    }

    public void addItem(ArrayList<DevBluetooth> lst, RecyclerView.Adapter adapter, String Addr, String Info, Information in, BluetoothDevice device) {
        int size = lst.size();
        for (int i = 0; i < size; i++) {
            if (Addr.equals(lst.get(i).Address)) {
                lst.set(i, new DevBluetooth(Addr, Info, in, device));
                adapter.notifyDataSetChanged();
                return;
            }
        }
        lst.add(new DevBluetooth(Addr, Info, in, device));
        adapter.notifyDataSetChanged();
    }

    public void addHistory(String Addr, String Info, Information in) {

        try {
            int size = history_device_list.size();
            for (int i = 0; i < size; i++) {
                if (Addr.equals(history_device_list.get(i).Address)) {
                    history_device_list.remove(i);
                    size--;
                    i--;
                    break;
                }
            }
            if (size == 30) {
                history_device_list.remove(29);
            }
            history_device_list.add(0, new DevBluetooth(Addr, Info, in, null));

            SharedPreferences sp = ctx.getSharedPreferences(ActivityControlCenter.HISTORY, 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear().commit();

            for (int i = 0; i < history_device_list.size(); i++) {
                editor.putString("" + i, history_device_list.get(i).Address + (char) 1 + history_device_list.get(i).Information);
            }
            editor.commit();

            HistoryDevListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void loadHistory() {

        try {
            SharedPreferences sp = ctx.getSharedPreferences(ActivityControlCenter.HISTORY, Context.MODE_PRIVATE);

            Map<String, ?> history = sp.getAll();
            String[] tmp_history = new String[history.size()];

            for (Map.Entry<String, ?> entry : history.entrySet()) {
                tmp_history[Integer.parseInt(entry.getKey())] = (String) entry.getValue();
            }

            history_device_list.clear();
            for (int i = 0; i < tmp_history.length; i++) {
                String[] str = tmp_history[i].split("" + (char) 1);
                history_device_list.add(new DevBluetooth(str[0], str[1], Format.DeFormat(str[1]), null));
            }
            HistoryDevListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    public void listReset(ArrayList<DevBluetooth> lst, RecyclerView.Adapter adapter) {
        int size = lst.size();
        for (int i = 0; i < size; i++) {
            DevBluetooth dev = lst.get(i);
            if ((new Date()).getTime() - dev.FoundTime.getTime() >= 15000) {
                lst.remove(i);
                size--;
                i--;
            }
        }
        adapter.notifyDataSetChanged();
    }

    private Handler search = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    listReset(device_list, DeviceListAdapter);
                    listReset(recommend_device_list, RecommendDevListAdapter);

                    recommendNotify(getAddition(OldRecommendList, recommend_device_list));
                    OldRecommendList = (ArrayList<DevBluetooth>) recommend_device_list.clone();

                    Rename();
                    doDiscovery();

                    search.sendMessageDelayed(search.obtainMessage(0), 8000);
                    break;
                }
                case 1: {
                    if (mBluetoothAdapter.isDiscovering())
                        mBluetoothAdapter.cancelDiscovery();
                    break;
                }
            }
        }
    };

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TaskService.Task.TASK_CONNECT:
                    final BluetoothDevice device = (BluetoothDevice) msg.obj;
                    Information info = Format.DeFormat(device.getName());
                    if (info == null || info.baseinfo.Nick.equals("")) {
                        TaskService.newTask(new TaskService.Task(mHandler, TaskService.Task.TASK_START_ACCEPT, null));
                        return;
                    }
                    Notify.notifyMessage(ctx, info.baseinfo.Nick+" 和你打招呼快去看看吧","有人和你打招呼，快去看看吧",info.baseinfo.Nick, getActivity());
                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                    builder.setMessage(info.baseinfo.Nick + " 和你打招呼，要建立连接么？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            search.removeMessages(0);
                            Notify.clear(ctx);
                            Intent intent = new Intent(ctx, ChatActivity.class);
                            intent.putExtra("isclient", false);
                            ctx.startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TaskService.newTask(new TaskService.Task(mHandler, TaskService.Task.TASK_CANCEL, null));
                            TaskService.newTask(new TaskService.Task(mHandler, TaskService.Task.TASK_START_ACCEPT, null));
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    builder.create().show();
                    break;

                case TaskService.Task.TASK_CONNECT_FAIL:
                    TaskService.newTask(new TaskService.Task(mHandler, TaskService.Task.TASK_START_ACCEPT, null));
                    builder = new AlertDialog.Builder(ctx);
                    builder.setMessage("很抱歉连接超时，请再试一次");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                    break;

                case TaskService.Task.TASK_RECV_INFO:
                    search.removeMessages(0);
                    Notify.clear(ctx);
                    Intent intent = new Intent(ctx, ChatActivity.class);
                    intent.putExtra("REMOTE_USER", (String) msg.obj);
                    intent.putExtra("isclient", true);
                    ctx.startActivity(intent);
                    break;

                case TaskService.Task.TASK_CANCEL:
                    TaskService.newTask(new TaskService.Task(mHandler, TaskService.Task.TASK_START_ACCEPT, null));
                    search.sendMessage(search.obtainMessage(0));
                    builder = new AlertDialog.Builder(ctx);
                    builder.setMessage("很抱歉，对方取消连接");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                    break;
            }
        }
    };

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String addr = device.getAddress();
                String btname = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);

                System.out.println("********New*********");
                System.out.println("new -> " + btname);
                System.out.println("********End*********");

                Information info = Format.DeFormat(btname);

                if (info != null) {
                    addItem(device_list, DeviceListAdapter, device.getAddress(), device.getName(), info, device);
                    addHistory(device.getAddress(), device.getName(), info);
                    if (Match.isInterest(info, full_user) ||
                            Match.isWanted(info, want1) ||
                            Match.isWanted(info, want2) ||
                            Match.isWanted(info, want3) ||
                            Match.isWanted(info, want4) ||
                            Match.isWanted(info, want5) ||
                            Match.isWanted(info, want6) ||
                            Match.isWanted(info, want7) ||
                            Match.isWanted(info, want8)) {
                        addItem(recommend_device_list, RecommendDevListAdapter, device.getAddress(), device.getName(), info, device);
                    }
                }
            }
            if (action.equals(ActivityControlCenter.ACTIVITY_EXIT_ACTION)) {
                getActivity().finish();
            }
        }
    };

    private void OpenBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(ctx, "本机没有找到蓝牙设备或驱动！", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent Intentenabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(Intentenabler, REQUEST_FOR_ENABLE);
        } else {
            //ActivityControlCenter.savedName = mBluetoothAdapter.getName();
            //ActivityControlCenter.savedBTAdapter = mBluetoothAdapter;

            Rename();

            TaskService.start(ctx, mHandler);
            TaskService.newTask(new TaskService.Task(mHandler, TaskService.Task.TASK_START_ACCEPT, null));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_FOR_ENABLE: {
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        //ActivityControlCenter.savedName = mBluetoothAdapter.getName();
                        //ActivityControlCenter.savedBTAdapter = mBluetoothAdapter;

                        Rename();

                        TaskService.start(ctx, mHandler);
                        TaskService.newTask(new TaskService.Task(mHandler, TaskService.Task.TASK_START_ACCEPT, null));

                        Toast.makeText(ctx, "蓝牙已经开启", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case Activity.RESULT_CANCELED: {
                        Toast.makeText(ctx, "不允许蓝牙开启", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        break;
                    }
                }
            }
        }
    }

    private void Rename() {
        updateBaseInfo();
        updateContactInfo();
        updateEducationInfo();
        updateHobbyInfo();

        String newname = Format.DoFormat(overt_user);
        mBluetoothAdapter.setName(newname);
    }

    protected void doDiscovery() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
    }

    protected void recommendNotify(ArrayList<DevBluetooth> change) {
        if (change.isEmpty())
            return;

        String name = "";
        for (DevBluetooth dev : change) {
            name += dev.Info.baseinfo.Nick + ", ";
        }
        name = name.substring(0, name.length() - 2);

        Notify.notifyMessage(getActivity(),"附近有你可能感兴趣的人哦", "你可能感兴趣的人，快去看看吧", name, getActivity());

        SharedPreferences sp = ctx.getSharedPreferences(ActivityControlCenter.SYSTEM_SETTING, 0);
        if (sp.getBoolean(ActivityControlCenter.IS_SHAKE, true)) {
            Vibrator vibrator = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {100, 400, 100, 400, 100, 400};
            vibrator.vibrate(pattern, -1);
        }
    }

    public static ArrayList<DevBluetooth> getAddition(ArrayList<DevBluetooth> formal, ArrayList<DevBluetooth> later) {
        ArrayList<DevBluetooth> ret;
        ret = new ArrayList<DevBluetooth>();
        if (formal.isEmpty() && later.isEmpty()) {
            return ret;
        }

        for (DevBluetooth dev : later) {
            int status = 0;
            for (DevBluetooth old : formal) {
                if (old.Address.equals(dev.Address))
                    status = 1;
            }
            if (status == 0)
                ret.add(dev);
        }

        return ret;
    }



    @Override
    public void onResume(){
        super.onResume();

        Rename();
        updateWants();
        loadHistory();

        ctx.registerReceiver(receiver, intentFilter);

        TaskService.mActivityHandler = mHandler;

        search.removeMessages(0);
        SharedPreferences sp = ctx.getSharedPreferences(ActivityControlCenter.SYSTEM_SETTING, 0);
        Message message = search.obtainMessage(sp.getInt(ActivityControlCenter.CMD, 0));
        search.sendMessage(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        search.removeMessages(0);
        // Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
            //mBluetoothAdapter.setName(ActivityControlCenter.savedName);
            mBluetoothAdapter.cancelDiscovery();
        }
        // Unregister broadcast listeners
        ctx.unregisterReceiver(receiver);
        TaskService.stop(ctx);
    }


    private void updateBaseInfo(){
        SharedPreferences sp = getActivity().getSharedPreferences(ActivityControlCenter.PERSONAL_BASE_INFO, 0);
        // base information
        if (sp.getBoolean(ActivityControlCenter.KEY_NAME_OVERT, false))
            overt_user.baseinfo.Name = sp.getString(ActivityControlCenter.KEY_NAME, "");
        else
            overt_user.baseinfo.Name = "";
        full_user.baseinfo.Name = sp.getString(ActivityControlCenter.KEY_NAME, "");

        overt_user.baseinfo.Nick = sp.getString(ActivityControlCenter.KEY_NICK, "");
        full_user.baseinfo.Nick = sp.getString(ActivityControlCenter.KEY_NICK, "");

        if (sp.getBoolean(ActivityControlCenter.KEY_GENDER_OVERT, false))
            overt_user.baseinfo.Gender = sp.getString(ActivityControlCenter.KEY_GENDER, "");
        else
            overt_user.baseinfo.Gender = "";
        full_user.baseinfo.Gender = sp.getString(ActivityControlCenter.KEY_GENDER, "");

        if (sp.getBoolean(ActivityControlCenter.KEY_BIRTHDAY_OVERT, false))
            overt_user.baseinfo.BirthDay = sp.getString(ActivityControlCenter.KEY_BIRTHDAY, "");
        else
            overt_user.baseinfo.BirthDay = "";
        full_user.baseinfo.BirthDay = sp.getString(ActivityControlCenter.KEY_BIRTHDAY, "");

        if (sp.getBoolean(ActivityControlCenter.KEY_HOMELAND_OVERT, false))
            overt_user.baseinfo.Homeland = sp.getString(ActivityControlCenter.KEY_HOMELAND, "");
        else
            overt_user.baseinfo.Homeland = "";
        full_user.baseinfo.Homeland = sp.getString(ActivityControlCenter.KEY_HOMELAND, "");

        if (sp.getBoolean(ActivityControlCenter.KEY_LOCATION_OVERT, false))
            overt_user.baseinfo.Location = sp.getString(ActivityControlCenter.KEY_LOCATION, "");
        else
            overt_user.baseinfo.Location = "";
        full_user.baseinfo.Location = sp.getString(ActivityControlCenter.KEY_LOCATION, "");


        if (sp.getBoolean(ActivityControlCenter.KEY_KEYWORDS_OVERT, false)){
            overt_user.keywords = sp.getString(ActivityControlCenter.KEY_KEYWORDS, "");
        }
        else
            overt_user.keywords = "";
        full_user.keywords = sp.getString(ActivityControlCenter.KEY_KEYWORDS, "");

    }

    private void updateContactInfo(){
        SharedPreferences sp = ctx.getSharedPreferences(ActivityControlCenter.PERSONAL_CONTACT_INFO, 0);

        if (sp.getBoolean(ActivityControlCenter.KEY_PHONE_OVERT, false))
            overt_user.contactinfo.Phone = sp.getString(ActivityControlCenter.KEY_PHONE, "");
        else
            overt_user.contactinfo.Phone = "";
        full_user.contactinfo.Phone = sp.getString(ActivityControlCenter.KEY_PHONE, "");

        if (sp.getBoolean(ActivityControlCenter.KEY_QQ_OVERT, false))
            overt_user.contactinfo.QQ = sp.getString(ActivityControlCenter.KEY_QQ, "");
        else
            overt_user.contactinfo.QQ = "";
        full_user.contactinfo.QQ = sp.getString(ActivityControlCenter.KEY_QQ, "");

        if (sp.getBoolean(ActivityControlCenter.KEY_EMAIL_OVERT, false))
            overt_user.contactinfo.E_Mail = sp.getString(ActivityControlCenter.KEY_EMAIL, "");
        else
            overt_user.contactinfo.E_Mail = "";
        full_user.contactinfo.E_Mail = sp.getString(ActivityControlCenter.KEY_EMAIL, "");

        if (sp.getBoolean(ActivityControlCenter.KEY_WEIBO_OVERT, false))
            overt_user.contactinfo.Weibo = sp.getString(ActivityControlCenter.KEY_WEIBO, "");
        else
            overt_user.contactinfo.Weibo = "";
        full_user.contactinfo.Weibo = sp.getString(ActivityControlCenter.KEY_WEIBO, "");

        if (sp.getBoolean(ActivityControlCenter.KEY_WECHAT_OVERT, false))
            overt_user.contactinfo.Wechat = sp.getString(ActivityControlCenter.KEY_WECHAT, "");
        else
            overt_user.contactinfo.Wechat  = "";
        full_user.contactinfo.Wechat = sp.getString(ActivityControlCenter.KEY_WECHAT, "");
    }

    private void updateEducationInfo(){
        SharedPreferences sp = ctx.getSharedPreferences(ActivityControlCenter.PERSONAL_EDUCATION_INFO, 0);

        if (sp.getBoolean(ActivityControlCenter.KEY_COLLEGE_OVERT, false))
            overt_user.edu.College = sp.getString(ActivityControlCenter.KEY_COLLEGE, "");
        else
            overt_user.edu.College = "";
        full_user.edu.College = sp.getString(ActivityControlCenter.KEY_COLLEGE, "");

        if (sp.getBoolean(ActivityControlCenter.KEY_HIGH_OVERT, false))
            overt_user.edu.High_School = sp.getString(ActivityControlCenter.KEY_HIGH, "");
        else
            overt_user.edu.High_School = "";
        full_user.edu.High_School = sp.getString(ActivityControlCenter.KEY_HIGH, "");

        if (sp.getBoolean(ActivityControlCenter.KEY_MIDDLE_OVERT, false))
            overt_user.edu.Middle_School = sp.getString(ActivityControlCenter.KEY_MIDDLE, "");
        else
            overt_user.edu.Middle_School = "";
        full_user.edu.Middle_School = sp.getString(ActivityControlCenter.KEY_MIDDLE, "");

        if (sp.getBoolean(ActivityControlCenter.KEY_PRIMARY_OVERT, false))
            overt_user.edu.Primary_School = sp.getString(ActivityControlCenter.KEY_PRIMARY, "");
        else
            overt_user.edu.Primary_School = "";
        full_user.edu.Primary_School = sp.getString(ActivityControlCenter.KEY_PRIMARY, "");
    }

    private void updateHobbyInfo(){
        SharedPreferences sp = ctx.getSharedPreferences(ActivityControlCenter.PERSONAL_HOBBY_INFO, 0);

        if (sp.getBoolean(ActivityControlCenter.KEY_GAME_OVERT, false))
            overt_user.hobby.Game = sp.getString(ActivityControlCenter.KEY_GAME, "");
        else
            overt_user.hobby.Game = "";

        if (sp.getBoolean(ActivityControlCenter.KEY_SPORT_OVERT, false))
            overt_user.hobby.Sport = sp.getString(ActivityControlCenter.KEY_SPORT, "");
        else
            overt_user.hobby.Sport = "";

        if (sp.getBoolean(ActivityControlCenter.KEY_COMIC_OVERT, false))
            overt_user.hobby.Comic = sp.getString(ActivityControlCenter.KEY_COMIC, "");
        else
            overt_user.hobby.Comic = "";

        if (sp.getBoolean(ActivityControlCenter.KEY_MUSIC_OVERT, false))
            overt_user.hobby.Music = sp.getString(ActivityControlCenter.KEY_MUSIC, "");
        else
            overt_user.hobby.Music = "";

        if (sp.getBoolean(ActivityControlCenter.KEY_BOOKS_OVERT, false))
            overt_user.hobby.Books = sp.getString(ActivityControlCenter.KEY_BOOKS, "");
        else
            overt_user.hobby.Books = "";

        if (sp.getBoolean(ActivityControlCenter.KEY_MOVIE_OVERT, false))
            overt_user.hobby.Movie = sp.getString(ActivityControlCenter.KEY_MOVIE, "");
        else
            overt_user.hobby.Movie = "";

        if (sp.getBoolean(ActivityControlCenter.KEY_OTHER_OVERT, false))
            overt_user.hobby.Other = sp.getString(ActivityControlCenter.KEY_OTHER, "");
        else
            overt_user.hobby.Other = "";

        full_user.hobby.Game = sp.getString(ActivityControlCenter.KEY_GAME, "");
        full_user.hobby.Sport = sp.getString(ActivityControlCenter.KEY_SPORT, "");
        full_user.hobby.Comic = sp.getString(ActivityControlCenter.KEY_COMIC, "");
        full_user.hobby.Music = sp.getString(ActivityControlCenter.KEY_MUSIC, "");
        full_user.hobby.Books = sp.getString(ActivityControlCenter.KEY_BOOKS, "");
        full_user.hobby.Movie = sp.getString(ActivityControlCenter.KEY_MOVIE, "");
        full_user.hobby.Other = sp.getString(ActivityControlCenter.KEY_OTHER, "");
    }

    private void updateWants(){
        SharedPreferences sp = ctx.getSharedPreferences(ActivityControlCenter.WANT_SETTINGS, 0);
        String str;
        str = sp.getString(ActivityControlCenter.KEY_WANT_1, "");
        if (!str.equals(""))
            want1 = Want.parseWant(str);
        else
            want1 = new Want();

        str = sp.getString(ActivityControlCenter.KEY_WANT_2, "");
        if (!str.equals(""))
            want2 = Want.parseWant(str);
        else
            want2 = new Want();

        str = sp.getString(ActivityControlCenter.KEY_WANT_3, "");
        if (!str.equals(""))
            want3 = Want.parseWant(str);
        else
            want3 = new Want();

        str = sp.getString(ActivityControlCenter.KEY_WANT_4, "");
        if (!str.equals(""))
            want4 = Want.parseWant(str);
        else
            want4 = new Want();

        str = sp.getString(ActivityControlCenter.KEY_WANT_5, "");
        if (!str.equals(""))
            want5 = Want.parseWant(str);
        else
            want5 = new Want();

        str = sp.getString(ActivityControlCenter.KEY_WANT_6, "");
        if (!str.equals(""))
            want6 = Want.parseWant(str);
        else
            want6 = new Want();

        str = sp.getString(ActivityControlCenter.KEY_WANT_7, "");
        if (!str.equals(""))
            want7 = Want.parseWant(str);
        else
            want7 = new Want();

        str = sp.getString(ActivityControlCenter.KEY_WANT_8, "");
        if (!str.equals(""))
            want8 = Want.parseWant(str);
        else
            want8 = new Want();
    }

    private class ShowDeviceList implements View.OnClickListener{
        public void onClick(View v){
            SearchShowBtn.setBackgroundColor(ContextCompat.getColor(ctx,R.color.background));
            SearchShowBtn.setTextColor(ContextCompat.getColor(ctx, R.color.actionbar_background));
            RecommendShowBtn.setBackgroundColor(ContextCompat.getColor(ctx, R.color.actionbar_background));
            RecommendShowBtn.setTextColor(Color.WHITE);
            FindShowBtn.setBackgroundColor(ContextCompat.getColor(ctx, R.color.actionbar_background));
            FindShowBtn.setTextColor(Color.WHITE);
            HistoryDeviceList.setVisibility(View.GONE);
            ((View)RecommendDeviceList.getParent()).setVisibility(View.GONE);
            ((View)DeviceList.getParent()).setVisibility(View.VISIBLE);
        }
    }

    private class ShowRecommendDeviceList implements View.OnClickListener{
        public void onClick(View v){
            RecommendShowBtn.setBackgroundColor(ContextCompat.getColor(ctx,R.color.background));
            RecommendShowBtn.setTextColor(ContextCompat.getColor(ctx, R.color.actionbar_background));
            SearchShowBtn.setBackgroundColor(ContextCompat.getColor(ctx, R.color.actionbar_background));
            SearchShowBtn.setTextColor(Color.WHITE);
            FindShowBtn.setBackgroundColor(ContextCompat.getColor(ctx, R.color.actionbar_background));
            FindShowBtn.setTextColor(Color.WHITE);
            HistoryDeviceList.setVisibility(View.GONE);
            ((View)RecommendDeviceList.getParent()).setVisibility(View.VISIBLE);
            ((View)DeviceList.getParent()).setVisibility(View.GONE);
        }
    }

    private class ShowHistoryDeviceList implements View.OnClickListener{
        public void onClick(View v){
            FindShowBtn.setBackgroundColor(ContextCompat.getColor(ctx,R.color.background));
            FindShowBtn.setTextColor(ContextCompat.getColor(ctx, R.color.actionbar_background));
            SearchShowBtn.setBackgroundColor(ContextCompat.getColor(ctx, R.color.actionbar_background));
            SearchShowBtn.setTextColor(Color.WHITE);
            RecommendShowBtn.setBackgroundColor(ContextCompat.getColor(ctx, R.color.actionbar_background));
            RecommendShowBtn.setTextColor(Color.WHITE);
            ((View)DeviceList.getParent()).setVisibility(View.GONE);
            ((View)RecommendDeviceList.getParent()).setVisibility(View.GONE);
            HistoryDeviceList.setVisibility(View.VISIBLE);
        }
    }
}
