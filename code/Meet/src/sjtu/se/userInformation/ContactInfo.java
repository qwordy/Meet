package sjtu.se.userInformation;

public class ContactInfo {
	public String Phone;
	public String QQ;
	public String E_Mail;
	public String Weibo;
	public String Social_Network;
	
	public ContactInfo(){
		this.Phone = "";
		this.QQ = "";
		this.E_Mail = "";
		this.Weibo = "";
		this.Social_Network = "";
	};
	
	public ContactInfo(ContactInfo c){
		this.Phone = c.Phone;
		this.QQ = c.QQ;
		this.E_Mail = c.E_Mail;
		this.Weibo = c.Weibo;
		this.Social_Network = c.Social_Network;
	};
}
