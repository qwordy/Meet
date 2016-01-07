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

	private ViewPager mViewPager;

	private UbmaPagerAdapter mPagerAdapter;

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
		mPagerAdapter = new UbmaPagerAdapter(getChildFragmentManager());
		mViewPager = (ViewPager) view.findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);

//		TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
//		tabLayout.addTab(tabLayout.newTab().setText("tab 0"));
//		tabLayout.addTab(tabLayout.newTab().setText("tab 1"));
//		tabLayout.addTab(tabLayout.newTab().setText("tab 2"));


	}
}
