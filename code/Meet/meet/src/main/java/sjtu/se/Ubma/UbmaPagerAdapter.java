package sjtu.se.Ubma;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by qwordy on 1/6/16.
 * UbmaPagerAdapter
 */

public class UbmaPagerAdapter extends FragmentPagerAdapter {
	private static AppListFragment appListFragment;
	private static ActiveTimeFragment activeTimeFragment;
	private static AboutFragment aboutFragment;

	public UbmaPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0)
			return appListFragment == null ?
					appListFragment = new AppListFragment() :
					appListFragment;
		if (position == 1)
			return activeTimeFragment == null ?
					activeTimeFragment = new ActiveTimeFragment() :
					activeTimeFragment;
		return aboutFragment == null ?
				aboutFragment = new AboutFragment() :
				aboutFragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position >= getCount()) return null;
		String[] titles = {"应用", "时间", "关于"};
		return titles[position];
	}
}
