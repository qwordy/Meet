package sjtu.se.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import sjtu.se.Activity.ChatPlatform.ChatListViewAdapter;

/**
 * 任务处理服务
 */
public class TaskService extends Service {

    public static class Task {
        public static final int TASK_CANCEL = -3;
        public static final int TASK_CONNECT_FAIL = -2;
        public static final int TASK_DISCONNECT = -1;
        public static final int TASK_CONNECT = 0;

        public static final int TASK_START_ACCEPT = 1;
        public static final int TASK_START_CONN_THREAD = 2;
        public static final int TASK_SEND_MSG = 3;
        public static final int TASK_RECV_MSG = 4;
        public static final int TASK_GET_REMOTE_STATE = 5;

        public static final int TASK_RECV_FILE = 6; /** mParam[0]：path */
        public static final int TASK_SEND_FILE = 7;
        public static final int TASK_PROGRESS = 8;

        public static final int TASK_SEND_CARD = 9;
        public static final int TASK_RECV_CARD = 10;

        public static final int TASK_SEND_INFO = 11;
        public static final int TASK_RECV_INFO = 12;

        public static final int TASK_ASK_ANALYSIS = 13;
        public static final int TASK_SEND_ANALYSIS = 14;
        public static final int TASK_RECV_ANALYSIS = 15;

        // 任务ID
        private int mTaskID;
        // 任务参数列表
        public Object[] mParams;

        private Handler mH;

        public Task(Handler handler, int taskID, Object[] params){
            this.mH = handler;
            this.mTaskID = taskID;
            this.mParams = params;
        }

        public Handler getHandler(){
            return this.mH;
        }

        public int getTaskID(){
            return mTaskID;
        }
    }

    public static final int BT_STAT_WAIT = 0;
    public static final int BT_STAT_CONN = 1;
    public static final int BT_STAT_ONLINE = 2;
    public static final int BT_STAT_UNKNOWN = 3;

    private final String TAG = "TaskService";
    private TaskThread mThread;

    private BluetoothAdapter mBluetoothAdapter;

    private static AcceptThread mAcceptThread;
    private static ConnectThread mConnectThread;
    private static ConnectedThread mCommThread;

    public static Handler mActivityHandler;

    // 任务队列
    private static ArrayList<Task> mTaskList = new ArrayList<Task>();

    @Override
    public void onCreate() {
        super.onCreate();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Your device is not support Bluetooth!");
            return;
        }
        mThread = new TaskThread();
        mThread.start();
    }

    private Handler mServiceHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case Task.TASK_GET_REMOTE_STATE:
                    try {
                        if (mAcceptThread == null && mConnectThread == null && mCommThread == null)
                            mActivityHandler.sendMessage(mActivityHandler.obtainMessage(Task.TASK_DISCONNECT));
                    }catch(Exception e){}

                    /*if (mCommThread != null && mCommThread.isAlive()) {
                        mActivityHandler.sendMessage(mActivityHandler.obtainMessage(111));
                    }*/
                    /*android.os.Message activityMsg = mActivityHandler
                            .obtainMessage();
                    activityMsg.what = msg.what;
                    if (mAcceptThread != null && mAcceptThread.isAlive()) {
                        activityMsg.obj = "等待连接...";
                        activityMsg.arg1 = BT_STAT_WAIT;
                    } else if (mCommThread != null && mCommThread.isAlive()) {
                        activityMsg.obj = mCommThread.getRemoteName() + "[在线]";
                        activityMsg.arg1 = BT_STAT_ONLINE;
                    } else if (mConnectThread != null && mConnectThread.isAlive()) {
                        SoundEffect.getInstance(TaskService.this).play(3);
                        activityMsg.obj = "正在连接："
                                + mConnectThread.getDevice().getName();
                        activityMsg.arg1 = BT_STAT_CONN;
                    } else {
                        activityMsg.obj = "未知状态";
                        activityMsg.arg1 = BT_STAT_UNKNOWN;
                        SoundEffect.getInstance(TaskService.this).play(2);
                        // 重新等待连接
                        mAcceptThread = new AcceptThread();
                        mAcceptThread.start();
                    }
                    mActivityHandler.sendMessage(activityMsg);*/
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public static void start(Context c, Handler handler){
        mActivityHandler = handler;
        Intent intent = new Intent(c, TaskService.class);
        c.startService(intent);
    }

    public static void stop(Context c){
        if (mAcceptThread != null && mAcceptThread.isAlive()) {
            mAcceptThread.cancel();
        }
        if(mConnectThread!=null && mConnectThread.isAlive()){
            mConnectThread.cancel();
        }
        if (mCommThread != null && mCommThread.isAlive()) {
            mCommThread.cancel();
        }
        Intent intent = new Intent(c, TaskService.class);
        c.stopService(intent);
    }

    public static void newTask(Task target) {
        synchronized (mTaskList) {
            mTaskList.add(target);
        }
    }

    private class TaskThread extends Thread {
        private boolean isRun = true;
        private int mCount = 0;

        public void cancel() {
            isRun = false;
        }

        @Override
        public void run() {
            Task task;
            while (isRun) {

                // 有任务
                if (mTaskList.size() > 0) {
                    synchronized (mTaskList) {
                        // 获得任务
                        task = mTaskList.get(0);
                        doTask(task);
                    }
                } else {
                    try {
                        Thread.sleep(200);
                        mCount++;
                    } catch (InterruptedException e) {
                    }
                    // 每过1秒钟进行一次状态检查
                    if (mCount >= 5) {
                        mCount = 0;
                        // 检查远程设备状态
                        android.os.Message handlerMsg = mServiceHandler
                                .obtainMessage();
                        handlerMsg.what = Task.TASK_GET_REMOTE_STATE;
                        mServiceHandler.sendMessage(handlerMsg);
                    }
                }
            }
        }

    }

    private void doTask(Task task) {
        boolean success = false;
        switch (task.getTaskID()) {
            case Task.TASK_START_ACCEPT:
                if (mAcceptThread != null && mAcceptThread.isAlive()) {
                    mAcceptThread.cancel();
                }
                if(mConnectThread!=null && mConnectThread.isAlive()){
                    mConnectThread.cancel();
                }
                if (mCommThread != null && mCommThread.isAlive()) {
                    mCommThread.cancel();
                }
                mAcceptThread = new AcceptThread();
                mAcceptThread.start();
                break;

            case Task.TASK_START_CONN_THREAD:
                if (task.mParams == null || task.mParams.length == 0) {
                    break;
                }
                if (mAcceptThread != null && mAcceptThread.isAlive()) {
                    mAcceptThread.cancel();
                }
                if(mConnectThread!=null && mConnectThread.isAlive()){
                    mConnectThread.cancel();
                }
                if (mCommThread != null && mCommThread.isAlive()) {
                    mCommThread.cancel();
                }
                BluetoothDevice remote = (BluetoothDevice) task.mParams[0];
                mConnectThread = new ConnectThread(remote);
                mConnectThread.start();
                break;

            case Task.TASK_SEND_MSG:
                success = false;
                if (mCommThread == null || !mCommThread.isAlive()
                        || task.mParams == null || task.mParams.length == 0) {
                    Log.e(TAG, "mCommThread or task.mParams null");
                }else{
                    byte[] msg = null;
                    try {
                        msg = DataProtocol.packMsg((String) task.mParams[0]);
                        success = mCommThread.write(msg);
                    } catch (UnsupportedEncodingException e) {
                        success = false;
                    }
                }
                if (!success) {
                    try {
                        android.os.Message returnMsg = mActivityHandler.obtainMessage();
                        returnMsg.what = Task.TASK_SEND_MSG;
                        returnMsg.obj = "消息发送失败";
                        mActivityHandler.sendMessage(returnMsg);
                    }catch(Exception e){}
                }
                break;

            case Task.TASK_SEND_CARD:
                success = false;
                if (mCommThread == null || !mCommThread.isAlive()
                        || task.mParams == null || task.mParams.length == 0) {
                    Log.e(TAG, "mCommThread or task.mParams null");
                }else{
                    byte[] card = null;
                    try {
                        card = DataProtocol.packCard((String) task.mParams[0]);
                        success = mCommThread.write(card);
                    } catch (UnsupportedEncodingException e) {
                        success = false;
                    }
                }
                try {
                    android.os.Message returnMsg = mActivityHandler.obtainMessage();
                    returnMsg.what = Task.TASK_SEND_CARD;
                    if (success) returnMsg.obj = "名片已发送";
                    else returnMsg.obj = "名片发送失败";
                    mActivityHandler.sendMessage(returnMsg);
                }catch(Exception e){}
                break;

            case Task.TASK_SEND_INFO:
                if (mCommThread == null || !mCommThread.isAlive()
                        || task.mParams == null || task.mParams.length == 0) {
                    Log.e(TAG, "mCommThread or task.mParams null");
                }else{
                    byte[] info = null;
                    try {
                        info = DataProtocol.packInfo((String) task.mParams[0]);
                        success = mCommThread.write(info);
                    } catch (UnsupportedEncodingException e) {
                        success = false;
                    }
                }
                if (!success) {
                    try {
                        android.os.Message msg = mActivityHandler.obtainMessage();
                        msg.what = Task.TASK_DISCONNECT;
                        mActivityHandler.sendMessage(msg);
                    }catch(Exception e){}
                }
                break;

            case Task.TASK_CANCEL:
                if (mCommThread == null || !mCommThread.isAlive())
                    Log.e(TAG, "mCommThread null");
                else{
                    try {
                        byte[] cancel = {DataProtocol.HEAD, DataProtocol.TYPE_END};
                        mCommThread.write(cancel);
                    }
                    catch(Exception e) {}
                }
                break;

            case Task.TASK_ASK_ANALYSIS:
                success = false;
                if (mCommThread == null || !mCommThread.isAlive())
                    Log.e(TAG, "mCommThread null");
                else{
                    try {
                        byte[] analysis = {DataProtocol.HEAD, DataProtocol.TYPE_ASK_ANALYSIS};
                        success = mCommThread.write(analysis);
                    }
                    catch(Exception e) {
                        success = false;
                    }
                }
                if(!success) {
                    try {
                        android.os.Message returnMsg = mActivityHandler.obtainMessage();
                        returnMsg.what = Task.TASK_ASK_ANALYSIS;
                        returnMsg.obj = "请求失败。";
                        mActivityHandler.sendMessage(returnMsg);
                    } catch (Exception e) {}
                }
                break;

            case Task.TASK_SEND_ANALYSIS:
                if (mCommThread == null || !mCommThread.isAlive()
                        || task.mParams == null || task.mParams.length == 0) {
                    Log.e(TAG, "mCommThread or task.mParams null");
                }else{
                    byte[] analysis = null;
                    try {
                        analysis = DataProtocol.packAnalysis((String) task.mParams[0]);
                        mCommThread.write(analysis);
                    } catch (UnsupportedEncodingException e) {}
                }
                break;
        }

        // 移除任务
        mTaskList.remove(task);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThread.cancel();
    }

    private final String UUID_STR = "00001101-0000-1000-8000-00805F9B34FB";

    /**
     * 等待客户端连接线程
     *
     * @author Administrator
     */
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;
        private boolean isCancel = false;

        public AcceptThread() {
            Log.d(TAG, "AcceptThread");

            BluetoothServerSocket tmp = null;
            try {
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(
                        "MT_Chat_Room", UUID.fromString(UUID_STR));
            } catch (Exception e) {
            }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            while (true) {
                try {
                    // 阻塞等待
                    socket = mmServerSocket.accept();
                } catch (Exception e) {
                    if (!isCancel) {
                        try {
                            mmServerSocket.close();
                        } catch (Exception e1) {
                        }
                        try {
                            mAcceptThread = new AcceptThread();
                            mAcceptThread.start();
                        }catch (Exception e2) {
                        }
                    }
                    break;
                }
                if (socket != null) {
                    //---------------------

                    try {
                        android.os.Message handlerMsg = mActivityHandler.obtainMessage(Task.TASK_CONNECT);
                        handlerMsg.obj = socket.getRemoteDevice();
                        mActivityHandler.sendMessage(handlerMsg);
                    }catch(Exception e){}

                    manageConnectedSocket(socket);
                    //---------------------
                    try {
                        mmServerSocket.close();
                    } catch (Exception e) {
                    }
                    mAcceptThread = null;
                    break;
                }
            }
        }

        public void cancel() {
            try {
                Log.d(TAG, "AcceptThread canceled");
                isCancel = true;
                mmServerSocket.close();
                mAcceptThread = null;
            } catch (Exception e) {
            }
        }
    }

    /**
     * 作为客户端连接指定的蓝牙设备线程
     *
     * @author Administrator
     */
    private class ConnectThread extends Thread {
        private  BluetoothSocket mmSocket;
        private  BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {

            Log.d(TAG, "ConnectThread");

            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(UUID
                        .fromString(UUID_STR));
            } catch (Exception e) {
                Log.d(TAG, "createRfcommSocketToServiceRecord error!");
            }

            mmSocket = tmp;
        }

        public BluetoothDevice getDevice() {
            return mmDevice;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            mBluetoothAdapter.cancelDiscovery();
            int cnt = 0;
            while(true) {
                try {
                    // Connect the device through the socket. This will block
                    // until it succeeds or throws an exception
                    mmSocket.connect();
                } catch (Exception connectException) {
                    // Unable to connect; close the socket and get out
                    Log.e(TAG, "Connect server failed");
                    try {
                        mmSocket.close();
                    } catch (Exception closeException) {
                    }
                    //---------------------
                    if(cnt == 5) {
                        cancel();
                        try {
                            mActivityHandler.sendMessage(mActivityHandler.obtainMessage(Task.TASK_CONNECT_FAIL));
                        } catch (Exception e) {}
                        return;
                    }
                    else{
                        try {
                            mmSocket = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString(UUID_STR));
                        }catch( Exception e){}
                        cnt++;
                        continue;
                    }
                    //---------------------
                }
                break;
            }
            // Do work to manage the connection (in a separate thread)
            manageConnectedSocket(mmSocket);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (Exception e) {
            }
            mConnectThread = null;
        }
    }

    private void manageConnectedSocket(BluetoothSocket socket) {
        // 启动子线程来维持连接
        mCommThread = new ConnectedThread(socket);
        mCommThread.start();
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private BufferedOutputStream mmBos;
        private byte[] buffer;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (Exception e) {
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            mmBos = new BufferedOutputStream(mmOutStream);
        }

        public OutputStream getOutputStream() {
            return mmOutStream;
        }

        public boolean write(byte[] msg) {
            if (msg == null)
                return false;
            try {
                mmBos.write(msg);
                mmBos.flush();
                System.out.println("Write:" + msg);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        public String getRemoteName() {
            return mmSocket.getRemoteDevice().getName();
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (Exception e) {
            }
            mCommThread = null;
        }

        public void run() {
            int size;
            DataProtocol.Message msg;
            android.os.Message handlerMsg;
            buffer = new byte[1024];

            BufferedInputStream bis = new BufferedInputStream(mmInStream);
            HashMap<String, Object> data;
            while (true) {
                try {
                    size = bis.read(buffer);
                    msg = DataProtocol.unpackData(buffer);
                    if (msg == null)
                        continue;

                    if (mActivityHandler == null) {
                        return;
                    }

                    msg.remoteDevName = mmSocket.getRemoteDevice().getName();
                    if (msg.type == DataProtocol.TYPE_FILE) {
                        // 文件接收处理忽略

                    } else if (msg.type == DataProtocol.TYPE_MSG) {
                        data = new HashMap<String, Object>();
                        System.out.println("Read data.");
                        data.put(ChatListViewAdapter.KEY_ROLE,
                                ChatListViewAdapter.ROLE_TARGET);
                        data.put(ChatListViewAdapter.KEY_NAME,
                                msg.remoteDevName);
                        data.put(ChatListViewAdapter.KEY_TEXT, msg.msg);
                        // 通过Activity更新到UI上
                        handlerMsg = mActivityHandler.obtainMessage();
                        handlerMsg.what = Task.TASK_RECV_MSG;
                        handlerMsg.obj = data;
                        mActivityHandler.sendMessage(handlerMsg);

                    }else if (msg.type == DataProtocol.TYPE_CARD) {
                        handlerMsg = mActivityHandler.obtainMessage();
                        handlerMsg.what = Task.TASK_RECV_CARD;
                        handlerMsg.obj = msg.msg;
                        mActivityHandler.sendMessage(handlerMsg);

                    }else if (msg.type == DataProtocol.TYPE_INFO) {
                        handlerMsg = mActivityHandler.obtainMessage();
                        handlerMsg.what = Task.TASK_RECV_INFO;
                        handlerMsg.obj = msg.msg;
                        mActivityHandler.sendMessage(handlerMsg);

                    }else if(msg.type == DataProtocol.TYPE_END){
                        handlerMsg = mActivityHandler.obtainMessage();
                        handlerMsg.what = Task.TASK_CANCEL;
                        mActivityHandler.sendMessage(handlerMsg);

                    }else if(msg.type == DataProtocol.TYPE_ASK_ANALYSIS){
                        handlerMsg = mActivityHandler.obtainMessage();
                        handlerMsg.what = Task.TASK_ASK_ANALYSIS;
                        mActivityHandler.sendMessage(handlerMsg);

                    }else if (msg.type == DataProtocol.TYPE_ANALYSIS) {
                        handlerMsg = mActivityHandler.obtainMessage();
                        handlerMsg.what = Task.TASK_RECV_ANALYSIS;
                        handlerMsg.obj = msg.msg;
                        mActivityHandler.sendMessage(handlerMsg);

                    }
                } catch (Exception e) {
                    try {
                        mmSocket.close();
                    } catch (Exception e1) {
                    }
                    mCommThread = null;
                    //-----------------------------------
                    try {
                        mActivityHandler.sendMessage(mActivityHandler.obtainMessage(Task.TASK_DISCONNECT));
                    }catch(Exception e2){}
                    //-----------------------------------
                    break;
                }
            }
        }
    }

    // ================================================================

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

}
