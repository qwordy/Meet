package sjtu.se.Ubma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qwordy on 12/26/15.
 * appClassifier
 */

public class appClassifier {
	public enum Category {
		CHAT, VIDEO, PHOTO, MUSIC, SHOPPING,LIFE,
		EDU, READING, OFFICE, SYSTEM, TRIP, OTHER
	}

	private static HashMap<String, Category> rules;

	private appClassifier() {}

	public static Category classify(String label) {
		if (rules == null)
			initRules();
		for ()
	}

	public static void initRules() {
		rules = new HashMap<>();

		rules.put("QQ", Category.CHAT);
		rules.put("微信", Category.CHAT);
		rules.put("易信", Category.CHAT);
		rules.put("陌陌", Category.CHAT);

		rules.put("视频", Category.VIDEO);
		rules.put("优酷", Category.VIDEO);
		rules.put("影音", Category.VIDEO);
		rules.put("视频", Category.VIDEO);
		rules.put("美拍", Category.VIDEO);
		rules.put("TV", Category.VIDEO);

		rules.put("相机", Category.PHOTO);
		rules.put("美图", Category.PHOTO);

		rules.put("音乐", Category.MUSIC);
		rules.put("FM", Category.MUSIC);

		rules.put("美团", Category.SHOPPING);
		rules.put("淘宝", Category.SHOPPING);
		rules.put("京东", Category.SHOPPING);
		rules.put("唯品会", Category.SHOPPING);
		rules.put("支付宝", Category.SHOPPING);
		rules.put("购物", Category.SHOPPING);

		rules.put("天气", Category.LIFE);
		rules.put("闹钟", Category.LIFE);
		rules.put("日历", Category.LIFE);

		rules.put("Coursera", Category.EDU);
		rules.put("公开课", Category.EDU);
		rules.put("单词", Category.EDU);
		rules.put("英语", Category.EDU);

		rules.put("阅读", Category.READING);
		rules.put("新闻", Category.READING);
		rules.put("头条", Category.READING);

		rules.put("笔记", Category.OFFICE);
		rules.put("WPS", Category.OFFICE);
		rules.put("PDF", Category.OFFICE);

		rules.put("浏览器", Category.SYSTEM);
		rules.put("文件", Category.SYSTEM);
		rules.put("输入法", Category.SYSTEM);
		rules.put("安全", Category.SYSTEM);
		rules.put("管家", Category.SYSTEM);
		rules.put("卫士", Category.SYSTEM);
		rules.put("桌面", Category.SYSTEM);

		rules.put("携程", Category.TRIP);
		rules.put("打车", Category.TRIP);
		rules.put("地图", Category.TRIP);
		rules.put("导航", Category.TRIP);
		rules.put("旅行", Category.TRIP);
		rules.put("出行", Category.TRIP);

	}
}
