package sjtu.se.Ubma;

import java.util.*;

/**
 * Created by qwordy on 12/30/15.
 * UserBehaviourSummary
 */

public class UserBehaviourSummary implements IUserBehaviourSummary {
	private MonitorService mContext;

	public UserBehaviourSummary(MonitorService context) {
		mContext = context;
	}

	@Override
	public List<AppClassifier.Category> appsTags() {
		List<AppInfo> userAiList = mContext.getUserAiList();
		Map<AppClassifier.Category, Integer> map = new HashMap<>();
		for (AppInfo appInfo : userAiList) {
			if (appInfo.category == AppClassifier.Category.OTHER)
				continue;
			Integer v = map.get(appInfo.category);
			if (v == null)
				map.put(appInfo.category, 1);
			else
				map.put(appInfo.category, v + 1);
		}

		List<Map.Entry<AppClassifier.Category, Integer>> entryList = new ArrayList<>(map.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry<AppClassifier.Category, Integer>>() {
			@Override
			public int compare(Map.Entry<AppClassifier.Category, Integer> lhs, Map.Entry<AppClassifier.Category, Integer> rhs) {
				return rhs.getValue() - lhs.getValue();
			}
		});

		List<AppClassifier.Category> list = new ArrayList<>();
		if (entryList.size() > 0)
			list.add(entryList.get(0).getKey());
		if (entryList.size() > 1)
			list.add(entryList.get(1).getKey());
		if (entryList.size() > 2)
			list.add(entryList.get(2).getKey());
		return list;
	}

	@Override
	public String appsTagDescription(AppClassifier.Category tag) {
		return tag.toString();
	}

	@Override
	public ActiveTimeCategory activeTimeCategory() {
		return ActiveTimeCategory.MEDIUM;
	}

	@Override
	public String activeTimeCategoryDescription(ActiveTimeCategory category) {
		return category.toString();
	}
}
