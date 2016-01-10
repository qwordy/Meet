package sjtu.se.Ubma;

import java.util.*;

import android.util.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

/**
 * Created by qwordy on 12/30/15.
 * UserBehaviourSummary
 */

public class UserBehaviourSummary {

	/**
	 * @return A sorted list of app tags. Length is 3 typically.
	 */
	public List<AppClassifier.Category> appsTags() {
		List<Map.Entry<AppClassifier.Category, Integer>> sortList =
				sortAppListByCategory(Environment.getUserAiList());
		List<AppClassifier.Category> list = new ArrayList<>();
		if (sortList.size() > 0)
			list.add(sortList.get(0).getKey());
		if (sortList.size() > 1)
			list.add(sortList.get(1).getKey());
		if (sortList.size() > 2)
			list.add(sortList.get(2).getKey());
		return list;
	}

	/**
	 * @param aiList An AppInfo list
	 * @return A sorted list of pair(category, count)
	 */
	private List<Map.Entry<AppClassifier.Category, Integer>> sortAppListByCategory(List<AppInfo> aiList) {
		Map<AppClassifier.Category, Integer> map = new HashMap<>();
		for (AppInfo appInfo : aiList) {
			//if (appInfo.category == AppClassifier.Category.OTHER)
			//	continue;
			Integer v = map.get(appInfo.category);
			if (v == null)
				map.put(appInfo.category, 1);
			else
				map.put(appInfo.category, v + 1);
		}

		List<Map.Entry<AppClassifier.Category, Integer>> sortList = new ArrayList<>(map.entrySet());
		Collections.sort(sortList, new Comparator<Map.Entry<AppClassifier.Category, Integer>>() {
			@Override
			public int compare(Map.Entry<AppClassifier.Category, Integer> lhs, Map.Entry<AppClassifier.Category, Integer> rhs) {
				return rhs.getValue() - lhs.getValue();
			}
		});

		return sortList;
	}

	public String allAppCategoryAnalyse() {
		return appCategoryAnalyse(Environment.getAiList());
	}

	public String userAppCategoryAnalyse() {
		return appCategoryAnalyse(Environment.getUserAiList());
	}

	/**
	 * @param aiList An AppInfo list
	 * @return Description of category rank
	 */
	private String appCategoryAnalyse(List<AppInfo> aiList) {
		List<Map.Entry<AppClassifier.Category, Integer>> sortList =
				sortAppListByCategory(aiList);
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<AppClassifier.Category, Integer> entry : sortList) {
			stringBuilder.append(entry.getKey().toString())
					.append(": ")
					.append(entry.getValue())
					.append("个\n");
		}
		return stringBuilder.toString();
	}

	enum ActiveTimeCategory {
		LIGHT, MEDIUM, HEAVY;

		@Override
		public String toString() {
			if (name().equals("LIGHT"))
				return "轻度";
			if (name().equals("MEDIUM"))
				return "中度";
			if (name().equals("HEAVY"))
				return "重度";
			return "";
		}
	}

	public ActiveTimeCategory activeTimeCategory() {
		return ActiveTimeCategory.MEDIUM;
	}

	public String activeTimeAnalyse() {
		int i, j;
		double[] times;
		double sum;
		String[] dayStr = {"今日", "昨日", "2日前", "3日前",
				"4日前", "5日前", "6日前", "平均"};
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("每日使用手机时间(分钟)\n");
		ActiveTimeData activeTimeData = Environment.getActiveTimeData();
		for (i = 0; i <= 7; i++) {
			if (i < 7)
				times = activeTimeData.dayActiveTime(i);
			else
				times = activeTimeData.averageActiveTime();
			sum = 0;
			for (j = 0; j < times.length; j++)
				sum += times[j];
			stringBuilder.append(dayStr[i])
					.append(": ")
					.append(String.format("%.2f\n", sum));
		}
		return stringBuilder.toString();
	}

	public String toJsonString() {
		JSONObject obj = new JSONObject();
		obj.put("apps", appListJson());
		obj.put("times", timeListJson());
		Log.d("Meet", obj.toString());
		return obj.toString();
	}

	private JSONArray appListJson() {
		JSONArray list = new JSONArray();
		List<AppInfo> appList = Environment.getUserAiList();
		for (AppInfo info : appList)
			list.add(info.packageName);
		return list;
	}

	private JSONArray timeListJson() {
		JSONArray list = new JSONArray();
		double[] times = Environment.getActiveTimeData().averageActiveTime();
		for (double time : times)
			list.add(time);
		return list;
	}

	public String compare(String summary) {
		double appSim, timeSim, sim;
		JSONParser parser = new JSONParser();
		try {
			JSONObject obj = (JSONObject) parser.parse(summary);
			JSONArray appList = (JSONArray) obj.get("apps");
			JSONArray timeList = (JSONArray) obj.get("times");
			appSim = compareApps(appList);
			timeSim = compareTimes(timeList);
			sim = (appSim + timeSim) / 2;
			String str = String.format(
					"相似度：%d%%\n手机应用相似度：%d%%\n活跃时间相似度：%d%%",
					Math.round(sim * 100),
					Math.round(appSim * 100),
					Math.round(timeSim * 100));
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "获取相似度失败";
	}

	private double compareApps(JSONArray list) {
		JSONArray list0 = appListJson();
		HashSet<String> set = new HashSet<>();
		for (Object pkg : list)
			set.add((String) pkg);
		int count = 0;
		for (Object pkg : list0)
			if (set.contains(pkg)) count++;
		return (double) count / (list0.size() + list.size() - count);
	}

	private double compareTimes(JSONArray list) {
		int i;
		double product, model0, model;
		JSONArray list0 = timeListJson();
		product = model0 = model = 0;
		for (i = 0; i < 24; i++)
			product += (double) list0.get(i) * (double) list.get(i);
		for (Object time : list0)
			model0 += Math.pow((double) time, 2);
		model0 = Math.sqrt(model0);
		for (Object time : list)
			model += Math.pow((double) time, 2);
		model = Math.sqrt(model);
		return product / model0 / model;
	}
}
