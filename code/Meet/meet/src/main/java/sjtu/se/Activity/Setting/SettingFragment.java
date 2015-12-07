package sjtu.se.Activity.Setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import sjtu.se.Activity.ActivityControlCenter;
import sjtu.se.Meet.R;

public class SettingFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_PREF_NICKNAME = "pref_nickname";
    public static final String KEY_ALERT_SWITCH = "pref_alert_switch";
    public static final String KEY_SHAKE_SWITCH = "pref_shake_switch";
    public static final String KEY_SOUND_SWITCH = "pref_sound_switch";
    public static final String KEY_ANALYSIS_SWITCH = "pref_analysis_switch";

    public static SharedPreferences prefs;

    @Override
    public void onCreate(final Bundle savedInstanceState){
        Preference nicknamePref;

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.system);

        prefs = getPreferenceScreen().getSharedPreferences();
        if((nicknamePref = findPreference(KEY_PREF_NICKNAME))!=null){
            nicknamePref.setSummary(prefs.getString(KEY_PREF_NICKNAME, ""));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        SharedPreferences systemSettings = getActivity().getSharedPreferences(ActivityControlCenter.SYSTEM_SETTING, 0);

        if (key.equals(KEY_PREF_NICKNAME)) {
            Preference nicknamePref = findPreference(key);
            nicknamePref.setSummary(sharedPreferences.getString(key, ""));

            SharedPreferences baseinfo = getActivity().getSharedPreferences(ActivityControlCenter.PERSONAL_BASE_INFO, 0);
            SharedPreferences.Editor editor = baseinfo.edit();
            editor.putString(ActivityControlCenter.KEY_NICK, sharedPreferences.getString(key, ""));
            editor.commit();
        }
        if(key.equals(KEY_ALERT_SWITCH)) {
            /*if(sharedPreferences.getBoolean(key,false)) {
                SharedPreferences.Editor editor = systemSettings.edit();
                editor.putInt(ActivityControlCenter.IS_SHAKE, 0);
                editor.commit();
            }
            else {
                SharedPreferences.Editor editor = systemSettings.edit();
                editor.putInt(ActivityControlCenter.IS_SHAKE, 2);
                editor.commit();
            }*/
        }
        if(key.equals(KEY_SHAKE_SWITCH)) {
            if(sharedPreferences.getBoolean(key,false)) {
                SharedPreferences.Editor editor = systemSettings.edit();
                editor.putBoolean(ActivityControlCenter.IS_SHAKE, true);
                editor.commit();
            }
            else {
                SharedPreferences.Editor editor = systemSettings.edit();
                editor.putBoolean(ActivityControlCenter.IS_SHAKE, false);
                editor.commit();
            }
        }
        if(key.equals(KEY_SOUND_SWITCH)) {
            if(sharedPreferences.getBoolean(key,false)) {
                SharedPreferences.Editor editor = systemSettings.edit();
                editor.putBoolean(ActivityControlCenter.IS_SOUND, true);
                editor.commit();
            }
            else {
                SharedPreferences.Editor editor = systemSettings.edit();
                editor.putBoolean(ActivityControlCenter.IS_SOUND, false);
                editor.commit();
            }
        }
        if(key.equals(KEY_ANALYSIS_SWITCH)) {
            if(sharedPreferences.getBoolean(key,false)) {
                SharedPreferences.Editor editor = systemSettings.edit();
                editor.putBoolean(ActivityControlCenter.IS_ANALYSE, true);
                editor.commit();
            }
            else {
                SharedPreferences.Editor editor = systemSettings.edit();
                editor.putBoolean(ActivityControlCenter.IS_ANALYSE, false);
                editor.commit();
            }
        }
    }

}
