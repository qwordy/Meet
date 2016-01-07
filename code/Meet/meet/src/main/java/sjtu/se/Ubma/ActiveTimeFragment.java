package sjtu.se.Ubma;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import sjtu.se.Meet.R;

/**
 * Created by qwordy on 12/8/15.
 * ActiveTimeFragment
 */

public class ActiveTimeFragment extends Fragment {
	LineChartView mLineChartView;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Meet", "ActiveTimeFragment create");
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_active_time, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View view = getView();
		if (view == null) return;
		mLineChartView = (LineChartView) view.findViewById(R.id.lineChartView);
		Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.d("Meet", String.valueOf(position));
				mLineChartView.whichDay = position - 1;
				mLineChartView.invalidate();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		spinner.setSelection(1);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		//((UbmaDrawerActivity)activity).setTitle(getString(R.string.active_time));
	}
}
