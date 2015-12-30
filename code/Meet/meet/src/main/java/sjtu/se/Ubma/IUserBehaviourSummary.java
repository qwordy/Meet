package sjtu.se.Ubma;

import java.util.List;

/**
 * Created by qwordy on 12/18/15.
 * IUserBehaviourSummary
 */

public interface IUserBehaviourSummary {
	/**
	 * @return A sorted list of app tags. Length is 3 typically.
	 */
	List<AppClassifier.Category> appsTags();

	/**
	 * @param tag Tag of apps
	 * @return Description of the tag
	 */
	String appsTagDescription(AppClassifier.Category tag);

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

	ActiveTimeCategory activeTimeCategory();

	String activeTimeCategoryDescription(ActiveTimeCategory category);
}
