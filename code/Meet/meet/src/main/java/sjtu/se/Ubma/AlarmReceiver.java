package sjtu.se.Ubma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by qwordy on 12/5/15.
 * AlarmReceiver
 */

public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		pm.isScreenOn();
		Toast.makeText(context, "hehe", Toast.LENGTH_LONG).show();
		Log.d("Meet", "alarm");
	}

}
