package sjtu.se.UserInformation;

public class ContactInfo {
	public String Phone;
	public String QQ;
	public String E_Mail;
	public String Weibo;
	public String Wechat;
	
	public ContactInfo(){
		this.Phone = "";
		this.QQ = "";
		this.E_Mail = "";
		this.Weibo = "";
		this.Wechat = "";
	};
	
	public ContactInfo(ContactInfo c){
		this.Phone = c.Phone;
		this.QQ = c.QQ;
		this.E_Mail = c.E_Mail;
		this.Weibo = c.Weibo;
		this.Wechat = c.Wechat;
	};
}
