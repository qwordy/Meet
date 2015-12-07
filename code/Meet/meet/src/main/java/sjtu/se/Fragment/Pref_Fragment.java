package sjtu.se.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import sjtu.se.Meet.R;

public class Pref_Fragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_PREF_NICKNAME = "pref_nickname";
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
        if (key.equals(KEY_PREF_NICKNAME)) {
            Preference nicknamePref = findPreference(key);
            // Set summary to be the user-description for the selected value
            nicknamePref.setSummary(sharedPreferences.getString(key, ""));
        }
    }

}
