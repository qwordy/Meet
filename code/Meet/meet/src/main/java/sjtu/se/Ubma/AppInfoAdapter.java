package sjtu.se.Ubma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import sjtu.se.Meet.R;

/**
 * Created by qwordy on 11/22/15.
 * Application information adapter for a list view
 */

public class AppInfoAdapter extends BaseAdapter {
	List<AppInfo> list;
	final LayoutInflater inflater;

	public AppInfoAdapter(Context context, List<AppInfo> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = inflater.inflate(R.layout.app_item, null);
		} else {
			view = convertView;
		}
		AppInfo ai = (AppInfo) getItem(position);
		((ImageView) view.findViewById(R.id.icon)).setImageDrawable(ai.appIcon);
		((TextView) view.findViewById(R.id.packageName)).setText(ai.packageName);
		((TextView) view.findViewById(R.id.appLabel))
				.setText(ai.appLabel + " -> " + ai.category.toString());
		((TextView) view.findViewById(R.id.version))
				.setText(ai.versionName + ' ' + ai.versionCode);
		return view;
	}
}
