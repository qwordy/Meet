package sjtu.se.Util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Meet.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    private SharedPreferences baseInfo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        baseInfo=getActivity().getSharedPreferences(ActivityControlCenter.PERSONAL_BASE_INFO, 0);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker dp, int year, int month, int day) {
        // Do something with the date chosen by the user
        int y = dp.getYear();
        int m = dp.getMonth() + 1;
        int d = dp.getDayOfMonth();
        String mdate;
        if (m < 10) {
            if (d < 10)
                mdate = String.valueOf(y) + "-0" + String.valueOf(m) + "-0" + String.valueOf(d);
            else
                mdate = String.valueOf(y) + "-0" + String.valueOf(m) + "-" + String.valueOf(d);
        } else {
            if (d < 10)
                mdate = String.valueOf(y) + "-" + String.valueOf(m) + "-0" + String.valueOf(d);
            else
                mdate = String.valueOf(y) + "-" + String.valueOf(m) + "-" + String.valueOf(d);
        }
        SharedPreferences.Editor editor = baseInfo.edit();
        editor.putString(ActivityControlCenter.KEY_BIRTHDAY, mdate);
        editor.commit();
        ((TextView)(getActivity().findViewById(R.id.base_info_birthday)))
                .setText(baseInfo.getString(ActivityControlCenter.KEY_BIRTHDAY, ""));
    }
}
