package sjtu.se.Ubma;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import sjtu.se.Meet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwordy on 12/8/15.
 *
 */

public class AppListFragment extends Fragment {
	private Activity mActivity;
	private ListView mListView;
	private AppInfoAdapter mAdapter0, mAdapter1;
	Spinner mSpinner;
	MonitorService service;

	/*public static AppListFragment newInstance(Context context) {
		AppListFragment fragment = new AppListFragment();
		Bundle bundle = new Bundle();
	}*/

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		Log.d("Meet", "AppListFragment create");
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_app_list, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
		View view = getView();
		if (view == null) return;
		mSpinner = (Spinner) view.findViewById(R.id.spinner);
		mSpinner.setEnabled(false);
		mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.d("Meet", String.valueOf(id));
				if (!mSpinner.isEnabled())
					return;
				if (id == 0)
					setAppListView(mAdapter0);
				else
					setAppListView(mAdapter1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		mListView = (ListView) getView().findViewById(R.id.appListView);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS ,
						Uri.parse("package:" + ((TextView) view.findViewById(R.id.packageName)).getText()));
				startActivity(intent);
			}
		});
		new AsyncTask<Object, Object, Object>(){
			@Override
			protected void onPostExecute(Object o) {
				if (getView() == null) return;
				if (mSpinner.getSelectedItemId() == 0)
					setAppListView(mAdapter0);
				else
					setAppListView(mAdapter1);
				mSpinner.setEnabled(true);
			}

			@Override
			protected Object doInBackground(Object[] params) {
				getAppList4();
				return null;
			}
		}.execute();
	}

	private void setAppListView(AppInfoAdapter adapter) {
		mListView.setAdapter(adapter);
		if (getView() == null) return;
		((TextView) getView().findViewById(R.id.appListText)).setText(
				"共" + adapter.getCount() + "个应用");
	}

	private void getAppList() {
		PackageManager pm = mActivity.getPackageManager();
		List<PackageInfo> piList = pm.getInstalledPackages(0);
		List<AppInfo> aiList = new ArrayList<>();
		List<AppInfo> userAiList = new ArrayList<>();
		for (PackageInfo pi : piList) {
			AppInfo appInfo = new AppInfo(
					pi.packageName,
					pi.versionName,
					pi.versionCode,
					pi.applicationInfo.loadLabel(pm).toString(),
					pi.applicationInfo.loadIcon(pm),
					pi.applicationInfo.loadLogo(pm));
			aiList.add(appInfo);
			if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
				userAiList.add(appInfo);
		}
		mAdapter0 = new AppInfoAdapter(mActivity, aiList);
		mAdapter1 = new AppInfoAdapter(mActivity, userAiList);
	}

	private void getAppList2() {
		Context context = getContext();
		Intent intent = new Intent(context, MonitorService.class);
		ServiceConnection conn = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder binder) {
				service = ((MonitorService.MyBinder) binder).getService();
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				service = null;
			}
		};
		context.bindService(intent, conn, Context.BIND_AUTO_CREATE);

		mAdapter0 = new AppInfoAdapter(mActivity, service.getAiList());
		mAdapter1 = new AppInfoAdapter(mActivity, service.getUserAiList());

		context.unbindService(conn);
	}

	private void getAppList3() {
		Context context = getContext();
		Intent intent = new Intent(context, MonitorService.class);
		Bundle bundle = new Bundle();

		context.startService(intent);
	}

	private void getAppList4() {
		mAdapter0 = new AppInfoAdapter(mActivity, Environment.getAiList());
		mAdapter1 = new AppInfoAdapter(mActivity, Environment.getUserAiList());
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((UbmaDrawerActivity)activity).setTitle(getString(R.string.title_section2));
	}
}
