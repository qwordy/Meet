package sjtu.se.UserInformation;

import android.content.Context;
import android.content.SharedPreferences;
import sjtu.se.Activity.ActivityControlCenter;

public class ContactCard {
	public String name;
	public String phone;
	public String email;
	public String qq;
	public String weibo;
	public String wechat;
	
	public ContactCard(){
		name = "";
		phone = "";
		email = "";
		qq = "";
		weibo = "";
		wechat = "";
	}

	public void setContactCard(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences(ActivityControlCenter.PERSONAL_BASE_INFO, 0);
		name 	= sp.getString(ActivityControlCenter.KEY_NAME, "");

		sp = ctx.getSharedPreferences(ActivityControlCenter.PERSONAL_CONTACT_INFO, 0);
		phone 	= sp.getString(ActivityControlCenter.KEY_PHONE, "");
		email 	= sp.getString(ActivityControlCenter.KEY_EMAIL, "");
		qq 	  	= sp.getString(ActivityControlCenter.KEY_QQ, "");
		weibo 	= sp.getString(ActivityControlCenter.KEY_WEIBO, "");
		wechat 	= sp.getString(ActivityControlCenter.KEY_WECHAT, "");
	}

	public String toString(){
		String str = "";
		char dot = 1;
		str += this.name + dot + this.phone + dot + this.email + dot +
				this.qq + dot +this.weibo + dot +this.wechat + dot +"e" + dot;
		return str;
	}
	
	public static ContactCard parseContactCard(String str){
		if (str == null)
			return null;

		char dot = 1;
		String strs[] = str.split( "" + dot );

		if (strs.length < 7)
			return null;
		
		ContactCard ret = new ContactCard();

		ret.name 		= strs[0];
		ret.phone	 	= strs[1];
		ret.email 		= strs[2];
		ret.qq 			= strs[3];
		ret.weibo 		= strs[4];
		ret.wechat 		= strs[5];

		return ret;
	}
}
