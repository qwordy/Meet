package sjtu.se.bluetoothtry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;

public class ActivityControlCenter {
	public static String savedName;
	public static BluetoothAdapter savedBTAdapter;
	public static String ACTIVITY_EXIT_ACTION = "activity_exit_action";

	public static ColorDrawable dayNormal = new ColorDrawable(0xFF404040);
	public static ColorDrawable dayClicked = new ColorDrawable(0x00FFFFFF);
	public static ColorDrawable nightNormal = new ColorDrawable(0xFF222222);
	public static ColorDrawable nightClicked = new ColorDrawable(0XFF000000);

	public static SimpleDateFormat STD_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static boolean PERSONAL_INFO_MAY_CHANGED = true;
	public static boolean WANTS_MAY_CHANGED = true;
	public static boolean CONTACTS_MAY_CHANGED = true;

	public static String MPROTOCOL_SCHEME_RFCOMM = "bluetoothchat";

	public static String MUUID = "F375BA45-33A6-769E-155A-4006472B03B3";

	public static String ACTION_SERVICE_TO_ACTIVITY = "android.intent.action.servicetoactivity";
	public static String ACTION_ACTIVITY_TO_SERVICE = "android.intent.action.activitytoservice";
	public static String ACTION_LAUNCHED = "android.intent.action.launched";

	// base information and their overt
	public static String PERSONAL_BASE_INFO = "baseinfo";
	public static String KEY_NICK = "keynick";
	public static String KEY_NAME = "keyname";
	public static String KEY_GENDER = "keygender";
	public static String KEY_BIRTHDAY = "keybirthday";
	public static String KEY_HOMELAND = "keyhomeland";
	public static String KEY_LOCATION = "keylocation";
	public static String KEY_KEYWORDS = "keykeywords";
	public static String KEY_NAME_OVERT = "keynameovert";
	public static String KEY_GENDER_OVERT = "keygenderovert";
	public static String KEY_BIRTHDAY_OVERT = "keybirthdayovert";
	public static String KEY_HOMELAND_OVERT = "keyhomelandovert";
	public static String KEY_LOCATION_OVERT = "keylocationovert";
	public static String KEY_KEYWORDS_OVERT = "keykeywordsovert";

	public static String[] genders = {"男", "女", ""};

	// contact information and their overt
	public static String PERSONAL_CONTACT_INFO = "contactinfo";
	public static String KEY_PHONE = "keyphone";
	public static String KEY_QQ = "keyqq";
	public static String KEY_EMAIL = "keyemail";
	public static String KEY_WEIBO = "keyweibo";
	public static String KEY_SOCIALNET = "keysocialnet";
	public static String KEY_PHONE_OVERT = "keyphoneovert";
	public static String KEY_QQ_OVERT = "keyqqovert";
	public static String KEY_EMAIL_OVERT = "keyemailovert";
	public static String KEY_WEIBO_OVERT = "keyweiboovert";
	public static String KEY_SOCIALNET_OVERT = "keysocialnetovert";

	// education information and their overt
	public static String PERSONAL_EDUCATION_INFO = "educationinfo";
	public static String KEY_COLLEGE = "keycollege";
	public static String KEY_HIGH = "keyhigh";
	public static String KEY_MIDDLE = "keymiddle";
	public static String KEY_PRIMARY = "keyprimary";
	public static String KEY_COLLEGE_OVERT = "keycollegeovert";
	public static String KEY_HIGH_OVERT = "keyhighovert";
	public static String KEY_MIDDLE_OVERT = "keymiddleovert";
	public static String KEY_PRIMARY_OVERT = "keyprimaryovert";

	// hobby information and their overt
	public static String PERSONAL_HOBBY_INFO = "hoppyinfo";
	public static String KEY_GAME = "keygame";
	public static String KEY_SPORT = "keysport";
	public static String KEY_COMIC = "keycomic";
	public static String KEY_MUSIC = "keymusic";
	public static String KEY_BOOKS = "keybooks";
	public static String KEY_MOVIE = "keymovie";
	public static String KEY_OTHER = "keyother";
	public static String KEY_GAME_OVERT = "keygameovert";
	public static String KEY_SPORT_OVERT = "keysportovert";
	public static String KEY_COMIC_OVERT = "keycomicovert";
	public static String KEY_MUSIC_OVERT = "keymusicovert";
	public static String KEY_BOOKS_OVERT = "keybooksovert";
	public static String KEY_MOVIE_OVERT = "keymovieovert";
	public static String KEY_OTHER_OVERT = "keyotherovert";

	// want settings
	public static String WANT_SETTINGS = "wantsettings";
	public static String KEY_WANT_1 = "keywant1";
	public static String KEY_WANT_2 = "keywant2";
	public static String KEY_WANT_3 = "keywant3";
	public static String KEY_WANT_4 = "keywant4";
	public static String KEY_WANT_5 = "keywant5";
	public static String KEY_WANT_6 = "keywant6";
	public static String KEY_WANT_7 = "keywant7";
	public static String KEY_WANT_8 = "keywant8";

	// contact card settings
	public static String CONTACT_SETTINGS = "contactsettings";
	public static String KEY_CONTACT_1 = "keycontact1";
	public static String KEY_CONTACT_2 = "keycontact2";
	public static String KEY_CONTACT_3 = "keycontact3";
	public static String KEY_CONTACT_4 = "keycontact4";
	public static String KEY_CONTACT_5 = "keycontact5";
	public static String KEY_CONTACT_6 = "keycontact6";
	public static String KEY_CONTACT_7 = "keycontact7";
	public static String KEY_CONTACT_8 = "keycontact8";

	// details
	public static String DETAIL_INFORMATION = "detailinformation";

	public static void SetOriginName(){
		savedBTAdapter.setName(savedName);
	}

}
