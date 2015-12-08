package sjtu.se.Ubma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import sjtu.se.Meet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwordy on 12/8/15.
 *
 */

public class AppListFragment extends Fragment {
	private Activity mActivity;
	private ListView listView;
	private AppInfoAdapter adapter, adapter1;
	Spinner spinner;
	boolean firstTime = true;

	/*public static AppListFragment newInstance(Context context) {
		AppListFragment fragment = new AppListFragment();
		Bundle bundle = new Bundle();
	}*/

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_app_list, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
		spinner = (Spinner) getView().findViewById(R.id.spinner);
		spinner.setEnabled(false);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.d("Meet", String.valueOf(id));
				if (id == 0) {
					if (!firstTime) {
						listView.setAdapter(adapter);
						((TextView) getView().findViewById(R.id.appListText)).setText(
								adapter.getCount() + " applications");
					}
					firstTime = false;
				} else {
					listView.setAdapter(adapter1);
					((TextView) getView().findViewById(R.id.appListText)).setText(
							adapter1.getCount() + " applications");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		listView = (ListView) getView().findViewById(R.id.appListView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
				listView.setAdapter(adapter);
				((TextView) getView().findViewById(R.id.appListText)).setText(
						adapter.getCount() + " applications");
				spinner.setEnabled(true);
				super.onPostExecute(o);
			}

			@Override
			protected Object doInBackground(Object[] params) {
				getAppList();
				return null;
			}
		}.execute();
	}

	private void getAppList() {
		PackageManager pm = mActivity.getPackageManager();
		List<PackageInfo> piList = pm.getInstalledPackages(0);
		List<AppInfo> aiList = new ArrayList();
		List<AppInfo> userAiList = new ArrayList();
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
		adapter = new AppInfoAdapter(mActivity, aiList);
		adapter1 = new AppInfoAdapter(mActivity, userAiList);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((UbmaDrawerActivity)activity).setTitle(getString(R.string.title_section2));
	}
}
