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
        int year,month,day;
        year = 1990;
        month=0;
        day=1;
        baseInfo=getActivity().getSharedPreferences(ActivityControlCenter.PERSONAL_BASE_INFO, 0);
        String birthday = baseInfo.getString(ActivityControlCenter.KEY_BIRTHDAY, "");
        String[] str= birthday.split("-");
        if(str.length==3){
            year = Integer.parseInt(str[0]);
            month=Integer.parseInt(str[1])-1;
            day=Integer.parseInt(str[2]);
        }
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
