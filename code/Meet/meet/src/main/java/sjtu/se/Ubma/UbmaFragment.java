package sjtu.se.Ubma;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import sjtu.se.Meet.R;

/**
 * Created by qwordy on 1/6/16.
 * UbmaFragment
 */

public class UbmaFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_ubma, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View view = getView();
		if (view == null) return;
		UbmaPagerAdapter pagerAdapter = new UbmaPagerAdapter(getChildFragmentManager());
		final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
		viewPager.setAdapter(pagerAdapter);

		TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
		tabLayout.addTab(tabLayout.newTab().setText("应用"));
		tabLayout.addTab(tabLayout.newTab().setText("时间"));
		tabLayout.addTab(tabLayout.newTab().setText("关于"));

		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				viewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});
	}
}
