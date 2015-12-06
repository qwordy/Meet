package sjtu.se.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import sjtu.se.Meet.R;

public class Pref_Fragment extends PreferenceFragment {
    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.system);
    }
}
