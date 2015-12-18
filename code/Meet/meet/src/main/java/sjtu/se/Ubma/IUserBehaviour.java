package sjtu.se.Ubma;

/**
 * Created by qwordy on 12/18/15.
 * IUserBehaviour
 */

public interface IUserBehaviour {

	int appsCategory();

	String appsCategoryDescription(int category);

	int activeTimeCategory();

	String activeTimeCategoryDescription(int category);
}
