package sjtu.se.userInformation;

public class BaseInfo {
	public String Name;
	public String Nick;
	public String Gender;
	public String BirthDay;
	public String Homeland;
	public String Location;
	
	public BaseInfo(){
		this.Name = "";
		this.Nick = "";
		this.Gender = "";
		this.BirthDay = "";
		this.Homeland = "";
		this.Location = "";
	};
	
	public BaseInfo(BaseInfo b){
		this.Name = b.Name;
		this.Nick = b.Nick;
		this.Gender = b.Gender;
		this.BirthDay = b.BirthDay;
		this.Homeland = b.Homeland;
		this.Location = b.Location;
	};
}
