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
				sortAppList(Environment.getUserAiList());
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
	 * @param tag Tag of apps
	 * @return Description of the tag
	 */
	public String appsTagDescription(AppClassifier.Category tag) {
		return tag.toString();
	}

	private List<Map.Entry<AppClassifier.Category, Integer>> sortAppList(List<AppInfo> aiList) {
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

	public String allAppSortToString() {
		return appSort(Environment.getAiList());
	}

	public String userAppSortToString() {
		return appSort(Environment.getUserAiList());
	}

	private String appSort(List<AppInfo> aiList) {
		List<Map.Entry<AppClassifier.Category, Integer>> sortList =
				sortAppList(aiList);
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

	public String activeTimeCategoryDescription(ActiveTimeCategory category) {
		return category.toString();
	}
}
