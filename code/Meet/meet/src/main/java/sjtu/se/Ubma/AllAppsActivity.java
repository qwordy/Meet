package sjtu.se.Ubma;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import sjtu.se.Meet.R;

import java.util.ArrayList;
import java.util.List;

public class AllAppsActivity extends AppCompatActivity {
	ListView listView;
	AppInfoAdapter adapter, adapter1;
	Spinner spinner;
	boolean firstTime = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_list);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setEnabled(false);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.d("Meet", String.valueOf(id));
				if (id == 0) {
					if (!firstTime) {
						listView.setAdapter(adapter);
						((TextView) findViewById(R.id.appListText)).setText(
								adapter.getCount() + " applications");
					}
					firstTime = false;
				} else {
					listView.setAdapter(adapter1);
					((TextView) findViewById(R.id.appListText)).setText(
							adapter1.getCount() + " applications");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		listView = (ListView) findViewById(R.id.appListView);
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
				((TextView) findViewById(R.id.appListText)).setText(
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

	void getAppList() {
		PackageManager pm = getPackageManager();
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
		adapter = new AppInfoAdapter(this, aiList);
		adapter1 = new AppInfoAdapter(this, userAiList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.menu_all_apps, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
