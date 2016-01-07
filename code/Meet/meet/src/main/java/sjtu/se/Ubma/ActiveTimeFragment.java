package sjtu.se.Ubma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import sjtu.se.Meet.R;

/**
 * Created by qwordy on 12/8/15.
 * ActiveTimeFragment
 */

public class ActiveTimeFragment extends Fragment {
	private LineChartView mLineChartView;
	private Button mButton;
	private boolean timeValueVisible = false;
	private DialogInterface.OnClickListener clearDataDialogOnClickListener;

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

		mButton = (Button) view.findViewById(R.id.button);
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mButton.showContextMenu();
			}
		});

		mButton.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("选项");
				menu.add(0, 0, 0, "数据分析");
				if (timeValueVisible)
					menu.add(0, 1, 0, "隐藏数值");
				else
					menu.add(0, 1, 0, "显示数值");
				menu.add(0, 2, 0, "清除数据");
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
				analyse();
				break;
			case 1:
				showOrHideTimeValue();
				break;
			case 2:
				clearData();
		}
		return true;
	}

	public void analyse() {
		UserBehaviourSummary userBehaviourSummary = Environment.getUserBehaviourSummary();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("数据分析")
				.setMessage(userBehaviourSummary.activeTimeAnalyse())
				.setPositiveButton("确定", null);
		builder.create().show();
	}

	public void showOrHideTimeValue() {
		mLineChartView.timeValueVisible = timeValueVisible = !timeValueVisible;
		mLineChartView.invalidate();
	}

	public void clearData() {
		if (clearDataDialogOnClickListener == null)
			clearDataDialogOnClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == DialogInterface.BUTTON_POSITIVE) {
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setTitle(R.string.hint)
								.setMessage("清除数据成功！")
								.setPositiveButton(R.string.ok, null);
						builder.create().show();
					}
				}
			};
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.hint)
				.setMessage("确认清除数据？")
				.setPositiveButton(R.string.ok, clearDataDialogOnClickListener)
				.setNegativeButton(R.string.cancel, null);
		builder.create().show();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		//((UbmaDrawerActivity)activity).setTitle(getString(R.string.active_time));
	}
}
