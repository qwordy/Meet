package sjtu.se.UserInformation;

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
		String strs[] = str.split( "" + (char)1 );
		//System.out.println(strs.length);
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
