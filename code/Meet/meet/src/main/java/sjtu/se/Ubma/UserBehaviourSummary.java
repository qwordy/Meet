package sjtu.se.Ubma;

import java.util.*;

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
		ActiveTimeData activeTimeData = Environment.activeTimeData;
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
}
