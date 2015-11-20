package sjtu.se.UserInformation;

public class Education {
	public String College;
	public String High_School;
	public String Middle_School;
	public String Primary_School;
	
	public Education(){
		this.College = "";
		this.High_School = "";
		this.Middle_School = "";
		this.Primary_School = "";
	}
	
	public Education(Education e){
		this.College = e.College;
		this.High_School = e.High_School;
		this.Middle_School = e.Middle_School;
		this.Primary_School = e.Primary_School;
	}
}
