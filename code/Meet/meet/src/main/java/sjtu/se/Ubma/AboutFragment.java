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
 * Created by qwordy on 12/10/15.
 * AboutFragment
 */

public class AboutFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_about, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		//((UbmaDrawerActivity)activity).setTitle(getString(R.string.ubma_about));
	}
}
