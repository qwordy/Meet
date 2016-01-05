package sjtu.se.Ubma;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import sjtu.se.Meet.R;

/**
 * Created by qwordy on 1/5/16.
 * PreferencedAppFragment
 */

public class PreferencedAppFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_preferenced_app, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((UbmaDrawerActivity)activity).setTitle(getString(R.string.preferenced_app));
	}
}
