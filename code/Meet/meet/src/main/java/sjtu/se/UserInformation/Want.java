package sjtu.se.UserInformation;

public class Want {
	public String tableName;
	public String gender;   // 0男 1女
	public String ageRange; // 20-22
	public String Homeland;
	public String Location;
	
	public String College;
	public String High_School;
	public String Middle_School;
	public String Primary_School;	
	
	public String keywords;
	
	public Want(){
		this.tableName = "";
		this.gender = "";
		this.ageRange = "";
		this.Homeland = "";
		this.Location = "";
		this.College = "";
		this.High_School = "";
		this.Middle_School = "";
		this.Primary_School = "";
		this.keywords = "";
	}
	
	public boolean isEmpty(){
		return (tableName.isEmpty() &&
				gender.isEmpty() && ageRange.isEmpty() && Homeland.isEmpty() &&
				Location.isEmpty() && College.isEmpty() && High_School.isEmpty() &&
				Middle_School.isEmpty() && Primary_School.isEmpty() && keywords.isEmpty());
	}
	
	public String toString(){
		String str = "";
		char dot = 1;
		str += this.tableName + dot + this.gender + dot + this.ageRange + dot + this.Homeland + dot + this.Location + dot;
		str += this.College + dot + this.High_School + dot + this.Middle_School + dot + this.Primary_School + dot;
		str += this.keywords + dot + "e" + dot;
		return str;
	}
	
	public static Want parseWant(String str){
		if (str == null)
			return null;

		char dot = 1;
		String strs[] = str.split( "" + (char)1 );
		System.out.println(strs.length);
		if (strs.length < 11)
			return null;
		
		Want ret = new Want();
		
		ret.tableName 		= strs[0];
		ret.gender	 		= strs[1];
		ret.ageRange 		= strs[2];
		ret.Homeland		= strs[3];
		ret.Location 		= strs[4];
		ret.College			= strs[5];
		ret.High_School		= strs[6];
		ret.Middle_School	= strs[7];
		ret.Primary_School	= strs[8];
		ret.keywords		= strs[9];
		
		return ret;
	}
}
