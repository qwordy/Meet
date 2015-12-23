package sjtu.se.UserInformation;

import android.os.Parcel;
import android.os.Parcelable;

public class Information implements Parcelable {
	public BaseInfo baseinfo;
	public ContactInfo contactinfo;
	public Education edu;
	public Hobby hobby;
	public String keywords;
	
	public Information(){
		super();
		this.baseinfo = new BaseInfo();
		this.contactinfo = new ContactInfo();
		this.edu = new Education();
		this.hobby = new Hobby();
		this.keywords = "";
	}
	
	public Information(Information i){
		this.baseinfo = new BaseInfo(i.baseinfo);
		this.contactinfo = new ContactInfo(i.contactinfo);
		this.edu = new Education(i.edu);
		this.hobby = new Hobby(i.hobby);
		this.keywords = i.keywords;
	}
	
	public Information(BaseInfo b, ContactInfo c, Education e, Hobby h, String k){
		this.baseinfo = new BaseInfo(b);
		this.contactinfo = new ContactInfo(c);
		this.edu = new Education(e);
		this.hobby = new Hobby(h);
		this.keywords = k;
	}
	
	public String toString(){
		String str = "";
		char dot = 1;
		
		str += this.baseinfo.Name + dot + this.baseinfo.Nick + dot + this.baseinfo.Gender + dot;
		str += this.baseinfo.BirthDay + dot + this.baseinfo.Homeland + dot + this.baseinfo.Location + dot;
		str += this.contactinfo.Phone + dot + this.contactinfo.QQ + dot + this.contactinfo.E_Mail + dot;
		str += this.contactinfo.Weibo + dot + this.contactinfo.Wechat + dot;
		str += this.edu.College + dot + this.edu.High_School + dot + this.edu.Middle_School + dot + this.edu.Primary_School + dot;
		str += this.hobby.Game + dot + this.hobby.Sport + dot + this.hobby.Comic + dot + this.hobby.Music + dot;
		str += this.hobby.Books + dot + this.hobby.Movie + dot + this.hobby.Other + dot;
		str += this.keywords + dot + "e" + dot;
		
		return str;
	}
	
	static public Information parseInformation(String str){
		if (str == null)
			return null;
		
		char dot = 1;
		String strs[] = str.split( "" + dot );
		//System.out.println(strs.length);
		if (strs.length < 24)
			return null;
		
		Information info = new Information();
		
		info.baseinfo.Name				= strs[0];
		info.baseinfo.Nick				= strs[1];
		info.baseinfo.Gender			= strs[2];
		info.baseinfo.BirthDay 			= strs[3];
		info.baseinfo.Homeland			= strs[4];
		info.baseinfo.Location			= strs[5];
		
		info.contactinfo.Phone			= strs[6];
		info.contactinfo.QQ				= strs[7];
		info.contactinfo.E_Mail			= strs[8];
		info.contactinfo.Weibo 			= strs[9];
		info.contactinfo.Wechat			= strs[10];
		
		info.edu.College				= strs[11];
		info.edu.High_School			= strs[12];
		info.edu.Middle_School			= strs[13];
		info.edu.Primary_School			= strs[14];
		
		info.hobby.Game					= strs[15];
		info.hobby.Sport				= strs[16];
		info.hobby.Comic				= strs[17];
		info.hobby.Music				= strs[18];
		info.hobby.Books				= strs[19];
		info.hobby.Movie				= strs[20];
		info.hobby.Other				= strs[21];
		
		info.keywords 					= strs[22];
		
		return info;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		// base information
		dest.writeString(this.baseinfo.Name);
		dest.writeString(this.baseinfo.Nick);
		dest.writeString(this.baseinfo.Gender);
		dest.writeString(this.baseinfo.BirthDay);
		dest.writeString(this.baseinfo.Homeland);
		dest.writeString(this.baseinfo.Location);
		// contact information
		dest.writeString(this.contactinfo.Phone);
		dest.writeString(this.contactinfo.QQ);
		dest.writeString(this.contactinfo.E_Mail);
		dest.writeString(this.contactinfo.Weibo);
		dest.writeString(this.contactinfo.Wechat);
		// education information
		dest.writeString(this.edu.College);
		dest.writeString(this.edu.High_School);
		dest.writeString(this.edu.Middle_School);
		dest.writeString(this.edu.Primary_School);
		// hobby information
		dest.writeString(this.hobby.Game);
		dest.writeString(this.hobby.Sport);
		dest.writeString(this.hobby.Comic);
		dest.writeString(this.hobby.Music);
		dest.writeString(this.hobby.Books);
		dest.writeString(this.hobby.Movie);
		dest.writeString(this.hobby.Other);
		// keywords
		dest.writeString(this.keywords);
	}
	
	public final static Parcelable.Creator<Information> CREATOR = new Creator<Information>() {

		@Override
		public Information createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Information info = new Information();
			info.baseinfo.Name = source.readString();
			info.baseinfo.Nick = source.readString();
			info.baseinfo.Gender = source.readString();
			info.baseinfo.BirthDay = source.readString();
			info.baseinfo.Homeland = source.readString();
			info.baseinfo.Location = source.readString();

			info.contactinfo.Phone= source.readString();
			info.contactinfo.QQ = source.readString();
			info.contactinfo.E_Mail = source.readString();
			info.contactinfo.Weibo = source.readString();
			info.contactinfo.Wechat = source.readString();
			
			info.edu.College = source.readString();
			info.edu.High_School = source.readString();
			info.edu.Middle_School = source.readString();
			info.edu.Primary_School = source.readString();
			
			info.hobby.Game = source.readString();
			info.hobby.Sport = source.readString();
			info.hobby.Comic = source.readString();
			info.hobby.Music = source.readString();
			info.hobby.Books = source.readString();
			info.hobby.Movie = source.readString();
			info.hobby.Other = source.readString();
			
			info.keywords = source.readString();
			
			return info;
		}

		@Override
		public Information[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Information[size];
		}
	};
}
