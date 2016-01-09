package sjtu.se.Ubma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import sjtu.se.Meet.R;

//import sjtu.se.Meet.R;

/**
 * Created by qwordy on 12/8/15.
 * AppListFragment
 */

public class AppListFragment extends Fragment {
	private ListView mListView;
	private AppInfoAdapter mAdapter0, mAdapter1;
	private Spinner mSpinner;
	private Button mButton;
	private boolean inited;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		Log.d("Meet", "AppListFragment onCreate");
		super.onCreate(savedInstanceState);
//		String str = Environment.getUserBehaviourSummary().toString();
//		String sim = Environment.getUserBehaviourSummary().compare(str);
//		Log.d("Meet", sim);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Log.d("Meet", "AppListFragment onCreateView");
		View view = inflater.inflate(R.layout.fragment_app_list, container, false);
		init(view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d("Meet", "AppListFragment onActivityCreated ");
	}

	private void init(View view) {
		if (view == null) return;
		mSpinner = (Spinner) view.findViewById(R.id.spinner);
		mSpinner.setEnabled(false);
		mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.d("Meet", "AppListSpinner " + String.valueOf(id));
				if (!mSpinner.isEnabled())
					return;
				if (id == 0)
					setAppListView(mAdapter0);
				else
					setAppListView(mAdapter1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});

		mListView = (ListView) view.findViewById(R.id.appListView);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mListView.showContextMenu();
//				Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS ,
//						Uri.parse("package:" + ((TextView) view.findViewById(R.id.packageName)).getText()));
//				startActivity(intent);
			}
		});
		mListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("选项");
				menu.add(0, 0, 0, "启动应用");
				menu.add(0, 1, 0, "详细信息");
			}
		});

		new AsyncTask<Object, Object, Object>(){
			@Override
			protected void onPostExecute(Object o) {
				//Log.d("Meet", "AppListFragment onPostExecute");
				if (mSpinner.getSelectedItemId() == 0)
					setAppListView(mAdapter0);
				else
					setAppListView(mAdapter1);
				mSpinner.setEnabled(true);
				mButton.setEnabled(true);
			}

			@Override
			protected Object doInBackground(Object[] params) {
				getAppList4();
				return null;
			}
		}.execute();

		mButton = (Button) view.findViewById(R.id.button);
		mButton.setEnabled(false);
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UserBehaviourSummary userBehaviourSummary = Environment.getUserBehaviourSummary();
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				if (mSpinner.getSelectedItemPosition() == 0)
					builder.setTitle("所有应用分类排名")
							.setMessage(userBehaviourSummary.allAppCategoryAnalyse());
				else
					builder.setTitle("用户应用分类排名")
							.setMessage(userBehaviourSummary.userAppCategoryAnalyse());
				builder.setPositiveButton(R.string.ok, null);
				builder.create().show();
			}
		});
	}

	private void setAppListView(AppInfoAdapter adapter) {
		mListView.setAdapter(adapter);
		if (getView() == null) return;
		((TextView) getView().findViewById(R.id.appListText)).setText(
				adapter.getCount() + "个应用");
	}

	private void getAppList4() {
		mAdapter0 = new AppInfoAdapter(getActivity(), Environment.getAiList());
		mAdapter1 = new AppInfoAdapter(getActivity(), Environment.getUserAiList());
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("Meet", "AppListFragment onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("Meet", "AppListFragment onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d("Meet", "AppListFragment onStop");
	}

//	private void getAppList() {
//		PackageManager pm = mActivity.getPackageManager();
//		List<PackageInfo> piList = pm.getInstalledPackages(0);
//		List<AppInfo> aiList = new ArrayList<>();
//		List<AppInfo> userAiList = new ArrayList<>();
//		for (PackageInfo pi : piList) {
//			AppInfo appInfo = new AppInfo(
//					pi.packageName,
//					pi.versionName,
//					pi.versionCode,
//					pi.applicationInfo.loadLabel(pm).toString(),
//					pi.applicationInfo.loadIcon(pm),
//					pi.applicationInfo.loadLogo(pm));
//			aiList.add(appInfo);
//			if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
//				userAiList.add(appInfo);
//		}
//		mAdapter0 = new AppInfoAdapter(mActivity, aiList);
//		mAdapter1 = new AppInfoAdapter(mActivity, userAiList);
//	}
//
//	private void getAppList2() {
//		Context context = getContext();
//		Intent intent = new Intent(context, MonitorService.class);
//		ServiceConnection conn = new ServiceConnection() {
//			@Override
//			public void onServiceConnected(ComponentName name, IBinder binder) {
//				service = ((MonitorService.MyBinder) binder).getService();
//			}
//
//			@Override
//			public void onServiceDisconnected(ComponentName name) {
//				service = null;
//			}
//		};
//		context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
//
//		mAdapter0 = new AppInfoAdapter(mActivity, service.getAiList());
//		mAdapter1 = new AppInfoAdapter(mActivity, service.getUserAiList());
//
//		context.unbindService(conn);
//	}
//
//	private void getAppList3() {
//		Context context = getContext();
//		Intent intent = new Intent(context, MonitorService.class);
//		Bundle bundle = new Bundle();
//
//		context.startService(intent);
//	}

}
