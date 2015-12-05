package sjtu.se.UserInformation;

public class ContactCard {
	public String tableName;
	public String name;
	public String phone;
	public String email;
	
	public ContactCard(){
		tableName = "";
		name = "";
		phone = "";
		email = "";
	}
	
	public String toString(){
		String str = "";
		char dot = 1;
		str += this.tableName + dot + this.name + dot + this.phone + dot + this.email + dot + "e" + dot;
		return str;
	}
	
	public static ContactCard parseContactCard(String str){
		if (str == null)
			return null;

		char dot = 1;
		String strs[] = str.split( "" + (char)1 );
		//System.out.println(strs.length);
		if (strs.length < 5)
			return null;
		
		ContactCard ret = new ContactCard();
		
		ret.tableName   = strs[0];
		ret.name 		= strs[1];
		ret.phone	 	= strs[2];
		ret.email 		= strs[3];
		
		return ret;
	}
}
