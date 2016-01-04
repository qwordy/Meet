package sjtu.se.Ubma;

import java.io.File;
import java.util.List;

/**
 * Created by qwordy on 12/31/15.
 * Environment
 */

public class Environment {

	private static List<AppInfo> aiList;

	private static List<AppInfo> userAiList;

	private static UserBehaviourSummary userBehaviourSummary;

	public static File activeTimeFile;

	public static ActiveTimeData activeTimeData;

	public static void setAiList(List<AppInfo> aiList) {
		Environment.aiList = aiList;
	}

	public static List<AppInfo> getAiList() {
		while (aiList == null)
			Thread.yield();
		return aiList;
	}

	public static void setUserAiList(List<AppInfo> userAiList) {
		Environment.userAiList = userAiList;
	}

	public static List<AppInfo> getUserAiList() {
		while (userAiList == null)
			Thread.yield();
		return userAiList;
	}

	public static void setUserBehaviourSummary(UserBehaviourSummary userBehaviourSummary) {
		Environment.userBehaviourSummary = userBehaviourSummary;
	}

	public static UserBehaviourSummary getUserBehaviourSummary() {
		while (userBehaviourSummary == null)
			Thread.yield();
		return userBehaviourSummary;
	}
}
