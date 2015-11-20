package sjtu.se.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sjtu.se.UserInformation.Information;
import sjtu.se.UserInformation.Want;

public class Match {

	public static List<Information> findInterest(List<Information> infos, Information user){
		List<Information> results = new ArrayList<Information>();
		for(Information info : infos){
			if(!user.baseinfo.Homeland.isEmpty()
					&& isMatch(info.baseinfo.Homeland, user.baseinfo.Homeland)){
				//System.out.println(user.baseinfo.Homeland);
				results.add(info);
				continue;
			}
			if(!user.edu.College.isEmpty()
					&& isMatch(info.edu.College, user.edu.College)) {
				//System.out.println(user.edu.College);
				results.add(info);
				continue;
			}
			if(!user.edu.High_School.isEmpty()
					&& isMatch(info.edu.High_School, user.edu.High_School)) {
				//System.out.println(user.edu.High_School);
				results.add(info);
				continue;
			}
			if(!user.edu.Middle_School.isEmpty()
					&& isMatch(info.edu.Middle_School, user.edu.Middle_School)) {
				//System.out.println(user.edu.Middle_School);
				results.add(info);
				continue;
			}
			if(!user.edu.Primary_School.isEmpty()
					&& isMatch(info.edu.Primary_School, user.edu.Primary_School)) {
				//System.out.println(user.edu.Primary_School);
				results.add(info);
				continue;
			}
			String newKeywords = user.keywords + " " + user.hobby.Books + " " + user.hobby.Game
					+ " " + user.hobby.Comic + " " + user.hobby.Movie + " " + user.hobby.Music
					+ " " + user.hobby.Other + " " + user.hobby.Sport;
			if(!newKeywords.isEmpty() && isMatch(info.keywords, newKeywords)){
				//System.out.println(newKeywords);
				results.add(info);
				continue;
			}
		}
		return results;
	}

	public static List<Information> findWants(List<Information> infos, Want want){
		List<Information> results = new ArrayList<Information>();
		for(Information info : infos){
			if(!isMatch(info.baseinfo.Gender, want.gender)) continue;
			if(!isMatch(info.baseinfo.Homeland, want.Homeland)) continue;
			if(!isMatch(info.baseinfo.Location, want.Location)) continue;
			if(!isMatchAge(info.baseinfo.BirthDay, want.ageRange)) continue;
			if(!isMatch(info.edu.College, want.College)) continue;
			if(!isMatch(info.edu.High_School, want.High_School)) continue;
			if(!isMatch(info.edu.Middle_School, want.Middle_School)) continue;
			if(!isMatch(info.edu.Primary_School, want.Primary_School)) continue;
			if(!isMatch(info.keywords, want.keywords)) continue;
			results.add(info);
		}
		return results;
	}

	public static boolean isInterest(Information info, Information user){
		if(!user.baseinfo.Homeland.isEmpty() 
				&& isMatch(info.baseinfo.Homeland, user.baseinfo.Homeland)){
			return true;
		}
		if(!user.edu.College.isEmpty() 
				&& isMatch(info.edu.College, user.edu.College)) {
			return true;
		}
		if(!user.edu.High_School.isEmpty() 
				&& isMatch(info.edu.High_School, user.edu.High_School)) {
			return true;
		}
		if(!user.edu.Middle_School.isEmpty() 
				&& isMatch(info.edu.Middle_School, user.edu.Middle_School)) {
			return true;
		}
		if(!user.edu.Primary_School.isEmpty() 
				&& isMatch(info.edu.Primary_School, user.edu.Primary_School)) {
			return true;
		}
		String userKeywords = user.keywords + ";" + user.hobby.Books + ";" + user.hobby.Game
				+ ";" + user.hobby.Comic + ";" + user.hobby.Movie + ";" + user.hobby.Music
				+ ";" + user.hobby.Other + ";" + user.hobby.Sport;
		if(!userKeywords.isEmpty() && isMatch(info.keywords, userKeywords)){
			return true;
		}
		return false;
	}
	
	public static boolean isWanted(Information info, Want want){
		if (want == null)  return false;
		if(want.isEmpty()) return false;
		if(!isMatchGender(info.baseinfo.Gender, want.gender)) return false;
		if(!isMatch(info.baseinfo.Homeland, want.Homeland)) return false;
		if(!isMatch(info.baseinfo.Location, want.Location)) return false;
		if(!isMatchAge(info.baseinfo.BirthDay, want.ageRange)) return false;
		if(!isMatch(info.edu.College, want.College)) return false;
		if(!isMatch(info.edu.High_School, want.High_School)) return false;
		if(!isMatch(info.edu.Middle_School, want.Middle_School)) return false;
		if(!isMatch(info.edu.Primary_School, want.Primary_School)) return false;
		if(!isMatch(info.keywords, want.keywords)) return false;
		return true;
	}
	
	public static boolean isMatch(String info, String want){
		if(want.equals("")) return true;
		String[] strs = want.split(";");
		if(strs.length == 1){
			if(info.contains(strs[0]))
				return true;
			return false;
		}
		else{
			for(String str : strs){
				if(!str.isEmpty() && info.contains(str)){
					//System.out.println("str:" + str);
					return true;
				}
			}
			return false;
		}
	}
	
	public static boolean isMatchAge(String info, String want){
		String[] ageRange = want.split("-");
		if(want.equals("")) return true;
		if(info.isEmpty()) return false;
		String[] birthDay = info.split("-");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int age = year-Integer.parseInt(birthDay[0]);
		int from = 0;
		int to = 999;
		if (!ageRange[0].equals(""));
			from = Integer.parseInt(ageRange[0]);
		if (!ageRange[1].equals(""));
			to = Integer.parseInt(ageRange[1]);
		if(age >= from && age <= to)
			return true;
		return false;
	}
	
	private static boolean isMatchGender(String info, String want){
		if (want.equals("")) return true;
		else if(info.equals(want)) return true;
		else return false;
	}
}
