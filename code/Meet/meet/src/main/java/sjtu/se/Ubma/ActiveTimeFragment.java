package sjtu.se.Ubma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import sjtu.se.Meet.R;

/**
 * Created by qwordy on 12/8/15.
 * ActiveTimeFragment
 */

public class ActiveTimeFragment extends Fragment {
	private LineChartView mLineChartView;
	private boolean timeValueVisible = false;
	private AlertDialog.Builder mAnalysisDialogBuilder, mTagDialogBuilder;
	private AlertDialog mClearDataDialog, mClearDataSuccessDialog;
	private PopupMenu mPopupMenu;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Meet", "ActiveTimeFragment onCreate");

		mAnalysisDialogBuilder = new AlertDialog.Builder(getActivity())
				.setTitle("数据统计")
				.setPositiveButton(R.string.ok, null);

		mTagDialogBuilder = new AlertDialog.Builder(getActivity())
				.setTitle("查看标签")
				.setPositiveButton(R.string.ok, null);

		mClearDataDialog = new AlertDialog.Builder(getActivity())
				.setTitle(R.string.hint)
				.setMessage("确认清除数据？")
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ActiveTimeData activeTimeData = Environment.getActiveTimeData();
						if (activeTimeData == null)
							activeTimeData = new ActiveTimeData();
						activeTimeData.clear();
						mLineChartView.invalidate();
						mClearDataSuccessDialog.show();
					}
				})
				.setNegativeButton(R.string.cancel, null)
				.create();

		mClearDataSuccessDialog = new AlertDialog.Builder(getActivity())
				.setTitle(R.string.hint)
				.setMessage("清除数据成功！")
				.setPositiveButton(R.string.ok, null)
				.create();

		Log.d("Meet", "ActiveTimeFragment onCreateDone");
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d("Meet", "ActiveTimeFragment onCreateView");
		return inflater.inflate(R.layout.fragment_active_time, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View view = getView();
		if (view == null) return;
		mLineChartView = (LineChartView) view.findViewById(R.id.lineChartView);
		mLineChartView.timeValueVisible = timeValueVisible;
		Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.d("Meet", "ActiveTimeSpinner "+ String.valueOf(position));
				mLineChartView.whichDay = position - 1;
				mLineChartView.invalidate();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
		spinner.setSelection(1);

		Button button = (Button) view.findViewById(R.id.button);
		mPopupMenu = new PopupMenu(getActivity(), button);
		Menu menu = mPopupMenu.getMenu();
		menu.add(0, 0, 0, "数据统计");
		menu.add(0, 1, 0, "查看标签");
		if (timeValueVisible)
			menu.add(0, 2, 0, "隐藏数值");
		else
			menu.add(0, 2, 0, "显示数值");
		menu.add(0, 3, 0, "清除数据");
		mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				//Log.d("Meet", "popupMenu" + item.getItemId());
				switch (item.getItemId()) {
					case 0:
						analyse();
						break;
					case 1:
						tag();
						break;
					case 2:
						showOrHideTimeValue();
						break;
					case 3:
						clearData();
				}
				return true;
			}
		});
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupMenu.show();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		mLineChartView.invalidate();
	}

	public void analyse() {
		mAnalysisDialogBuilder
				.setMessage(Environment.getUserBehaviourSummary().activeTimeAnalyse())
				.show();
	}

	public void tag() {
		mTagDialogBuilder
				.setMessage(Environment.getUserBehaviourSummary().activeTimeTag())
				.show();
	}

	public void showOrHideTimeValue() {
		mLineChartView.timeValueVisible = timeValueVisible = !timeValueVisible;
		mLineChartView.invalidate();
		if (timeValueVisible)
			mPopupMenu.getMenu().getItem(2).setTitle("隐藏数值");
		else
			mPopupMenu.getMenu().getItem(2).setTitle("显示数值");
	}

	public void clearData() {
		mClearDataDialog.show();
	}
}
