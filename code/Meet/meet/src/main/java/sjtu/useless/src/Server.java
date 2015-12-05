//package sjtu.se.service;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import android.app.Service;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothServerSocket;
//import android.bluetooth.BluetoothSocket;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Binder;
//import android.os.IBinder;
//import android.os.Message;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.io.IOException;
//import java.util.UUID;
//
//import sjtu.se.bluetoothtry.ActivityControlCenter;
//import sjtu.se.bluetoothtry.ChatPlatform;
//import sjtu.se.encryption.Format;
//import sjtu.se.matching.Match;
//import sjtu.se.userInformation.Information;
//
//public class Server extends Service {
//
//	private final IBinder binder = new LocalBinder();
//	private BluetoothServerSocket serverSocket = null;
//	private BluetoothSocket socket = null;
//	private ServerThread mserverThread = null;
//	private readThread mreadThread = null;
//	private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//	private Context ctx;
//
//	@Override
//	public IBinder onBind(Intent intent) {
//		// TODO Auto-generated method stub
//		return binder;
//	}
//
//	public class LocalBinder extends Binder {
//		public Server getService() {
//			return Server.this;
//		}
//	}
//
//	@Override
//	public void onCreate() {
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(ActivityControlCenter.ACTION_ACTIVITY_TO_SERVICE);
//		this.registerReceiver(receiver, filter);
//		System.out.println("Im created!");
//		ctx = this;
//
//		mserverThread = new ServerThread();
//		mserverThread.start();
//	}
//
//	@SuppressWarnings("deprecation")
//	@Override
//	public void onStart(Intent intent, int startId){
//		super.onStart(intent, startId);
//	}
//
//	@Override
//    public void onDestroy() {
//        super.onDestroy();
//        this.unregisterReceiver(receiver);
//        this.shutdownServer();
//    }
//
//	// 服务器线程
//	private class ServerThread extends Thread {
//		public void run(){
//			try{
//				// 创建蓝牙服务器
//				System.out.println("New Thread is created!");
//				while(serverSocket == null)
//					serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(
//							ActivityControlCenter.MPROTOCOL_SCHEME_RFCOMM, UUID.fromString(ActivityControlCenter.MUUID));
//				//System.out.println(serverSocket + "Im waiting........");
//
//				socket = serverSocket.accept();
//
//				sendMessage(ChatPlatform.PREFIX_SYSTEM + "start");
//
//				Intent intent = new Intent();
//				intent.setAction(ActivityControlCenter.ACTION_LAUNCHED);
//				intent.putExtra("address", mBluetoothAdapter.getAddress());
//				ctx.sendBroadcast(intent);
//
//				//System.out.println("I catch you!");
//
//				mreadThread = new readThread();
//				mreadThread.start();
//			}catch(IOException e){
//				e.printStackTrace();
//			}
//		}
//	};
//
//	// 停止服务器
//	private void shutdownServer() {
//		new Thread() {
//			public void run() {
//				if(mserverThread != null)
//				{
//					mserverThread.interrupt();
//					mserverThread = null;
//				}
//				if(mreadThread != null)
//				{
//					mreadThread.interrupt();
//					mreadThread = null;
//				}
//				try {
//					if(socket != null)
//					{
//						socket.close();
//						socket = null;
//					}
//					if (serverSocket != null)
//					{
//						serverSocket.close();/* 关闭服务器 */
//						serverSocket = null;
//					}
//				} catch (IOException e) {
//					Log.e("server", "mserverSocket.close()", e);
//				}
//			};
//		}.start();
//	}
//
//	//发送数据
//	private void sendMessage(String msg)
//		{
//			if (socket == null)
//			{
//				return;
//			}
//			try {
//				OutputStream os = socket.getOutputStream();
//				os.write(msg.getBytes());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//	//读取数据
//    private class readThread extends Thread {
//        public void run() {
//
//            byte[] buffer = new byte[1024];
//            int bytes;
//            InputStream mmInStream = null;
//
//			try {
//				mmInStream = socket.getInputStream();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//            while (true) {
//                try {
//                    // Read from the InputStream
//                    if( (bytes = mmInStream.read(buffer)) > 0 )
//                    {
//	                    byte[] buf_data = new byte[bytes];
//				    	for(int i=0; i<bytes; i++)
//				    	{
//				    		buf_data[i] = buffer[i];
//				    	}
//						String s = new String(buf_data);
//						sendToActivity(s);
//                    }
//                } catch (IOException e) {
//                	try {
//						mmInStream.close();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//                    break;
//                }
//            }
//        }
//    };
//
//    // 与activity通信
//    private void sendToActivity(String str){
//    	Intent intent = new Intent();
//    	intent.setAction(ActivityControlCenter.ACTION_SERVICE_TO_ACTIVITY);
//    	intent.putExtra("msg", str);
//    	sendBroadcast(intent);
//    }
//
//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//			if (action.equals(ActivityControlCenter.ACTION_ACTIVITY_TO_SERVICE)){
//				sendMessage(intent.getStringExtra("msg"));
//			}
//		}
//	};
//}
