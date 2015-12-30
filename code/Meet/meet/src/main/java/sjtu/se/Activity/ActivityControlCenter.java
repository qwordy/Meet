package sjtu.se.Activity;

import android.bluetooth.BluetoothAdapter;

public class ActivityControlCenter {
	public static String savedName;
	public static BluetoothAdapter savedBTAdapter;
	public static String ACTIVITY_EXIT_ACTION = "activity_exit_action";

	public static boolean PERSONAL_INFO_MAY_CHANGED = true;
	public static boolean WANTS_MAY_CHANGED = true;

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
	public static String KEY_WECHAT = "keywechat";

	public static String KEY_PHONE_OVERT = "keyphoneovert";
	public static String KEY_QQ_OVERT = "keyqqovert";
	public static String KEY_EMAIL_OVERT = "keyemailovert";
	public static String KEY_WEIBO_OVERT = "keyweiboovert";
	public static String KEY_WECHAT_OVERT = "keywechatovert";

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

    // system settings
    public static String SYSTEM_SETTING = "systemsetting";
    public static String CMD = "cmd";
    public static String IS_ANALYSE = "isanalyse";
    public static String IS_SHAKE = "isshake";
    public static String IS_SOUND = "issound";

	public static void SetOriginName(){
		savedBTAdapter.setName(savedName);
	}
}
