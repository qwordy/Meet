package sjtu.se.Ubma;

import java.util.*;

/**
 * Created by qwordy on 12/26/15.
 * AppClassifier
 */

public class AppClassifier {
	public enum Category {
		VIDEO, PHOTO, MUSIC, GAME, CHAT, SHOPPING, TRIP,
		LIFE, EDU, READING, OFFICE, SYSTEM, OTHER;

		@Override
		public String toString() {
			if (name().equals("VIDEO"))
				return "视频";
			if (name().equals("PHOTO"))
				return "图片";
			if (name().equals("MUSIC"))
				return "音乐";
			if (name().equals("GAME"))
				return "游戏";
			if (name().equals("CHAT"))
				return "聊天";
			if (name().equals("SHOPPING"))
				return "购物";
			if (name().equals("TRIP"))
				return "出行";
			if (name().equals("LIFE"))
				return "生活";
			if (name().equals("EDU"))
				return "教育";
			if (name().equals("READING"))
				return "阅读";
			if (name().equals("OFFICE"))
				return "办公";
			if (name().equals("SYSTEM"))
				return "系统";
			if (name().equals("OTHER"))
				return "其他";
			return "";
		}
	}

	private static List<Pair<String, Category>> rules;

	private AppClassifier() {}

	public static Category classify(String label) {
		if (rules == null) initRules();
		for (Pair<String, Category> pair : rules)
			if (label.contains(pair.first))
				return pair.second;
		return Category.OTHER;
	}

	public static void initRules() {
		rules = new ArrayList<>();

		rules.add(new Pair<>("视频", Category.VIDEO));
		rules.add(new Pair<>("优酷", Category.VIDEO));
		rules.add(new Pair<>("影音", Category.VIDEO));
		rules.add(new Pair<>("视频", Category.VIDEO));
		rules.add(new Pair<>("美拍", Category.VIDEO));
		rules.add(new Pair<>("TV", Category.VIDEO));
		rules.add(new Pair<>("直播", Category.VIDEO));

		rules.add(new Pair<>("相机", Category.PHOTO));
		rules.add(new Pair<>("美图", Category.PHOTO));
		rules.add(new Pair<>("快图", Category.PHOTO));
		rules.add(new Pair<>("图库", Category.PHOTO));
		rules.add(new Pair<>("Camera", Category.PHOTO));
		rules.add(new Pair<>("Instagram", Category.PHOTO));
		rules.add(new Pair<>("LOFTER", Category.PHOTO));
		rules.add(new Pair<>("Photo", Category.PHOTO));

		rules.add(new Pair<>("音乐", Category.MUSIC));
		rules.add(new Pair<>("FM", Category.MUSIC));
		rules.add(new Pair<>("唱吧", Category.MUSIC));
		rules.add(new Pair<>("K歌", Category.MUSIC));

		rules.add(new Pair<>("游戏", Category.GAME));
		rules.add(new Pair<>("飞车", Category.GAME));
		rules.add(new Pair<>("赛车", Category.GAME));
		rules.add(new Pair<>("捕鱼", Category.GAME));
		rules.add(new Pair<>("梦幻", Category.GAME));
		rules.add(new Pair<>("天龙八部", Category.GAME));
		rules.add(new Pair<>("斗地主", Category.GAME));
		rules.add(new Pair<>("棋", Category.GAME));
		rules.add(new Pair<>("扑克", Category.GAME));
		rules.add(new Pair<>("神庙逃亡", Category.GAME));
		rules.add(new Pair<>("Temple Run", Category.GAME));
		rules.add(new Pair<>("疯狂猜词", Category.GAME));
		rules.add(new Pair<>("谁是卧底", Category.GAME));
		rules.add(new Pair<>("别踩白块", Category.GAME));
		rules.add(new Pair<>("保卫萝卜", Category.GAME));
		rules.add(new Pair<>("部落战争", Category.GAME));
		rules.add(new Pair<>("多玩", Category.GAME));

		rules.add(new Pair<>("QQ", Category.CHAT));
		rules.add(new Pair<>("微信", Category.CHAT));
		rules.add(new Pair<>("易信", Category.CHAT));
		rules.add(new Pair<>("陌陌", Category.CHAT));
		rules.add(new Pair<>("飞信", Category.CHAT));

		rules.add(new Pair<>("美团", Category.SHOPPING));
		rules.add(new Pair<>("淘宝", Category.SHOPPING));
		rules.add(new Pair<>("京东", Category.SHOPPING));
		rules.add(new Pair<>("唯品会", Category.SHOPPING));
		rules.add(new Pair<>("支付宝", Category.SHOPPING));
		rules.add(new Pair<>("购物", Category.SHOPPING));
		rules.add(new Pair<>("商城", Category.SHOPPING));

		rules.add(new Pair<>("携程", Category.TRIP));
		rules.add(new Pair<>("打车", Category.TRIP));
		rules.add(new Pair<>("地图", Category.TRIP));
		rules.add(new Pair<>("导航", Category.TRIP));
		rules.add(new Pair<>("旅行", Category.TRIP));
		rules.add(new Pair<>("出行", Category.TRIP));
		rules.add(new Pair<>("路路通", Category.TRIP));

		rules.add(new Pair<>("天气", Category.LIFE));
		rules.add(new Pair<>("闹钟", Category.LIFE));
		rules.add(new Pair<>("日历", Category.LIFE));
		rules.add(new Pair<>("录音机", Category.LIFE));
		rules.add(new Pair<>("收音机", Category.LIFE));
		rules.add(new Pair<>("饿了么", Category.LIFE));
		rules.add(new Pair<>("同去", Category.LIFE));
		rules.add(new Pair<>("菜谱", Category.LIFE));
		rules.add(new Pair<>("营业厅", Category.LIFE));

		rules.add(new Pair<>("Coursera", Category.EDU));
		rules.add(new Pair<>("公开课", Category.EDU));
		rules.add(new Pair<>("单词", Category.EDU));
		rules.add(new Pair<>("英语", Category.EDU));
		rules.add(new Pair<>("词霸", Category.EDU));
		rules.add(new Pair<>("词典", Category.EDU));

		rules.add(new Pair<>("阅读", Category.READING));
		rules.add(new Pair<>("新闻", Category.READING));
		rules.add(new Pair<>("头条", Category.READING));
		rules.add(new Pair<>("贴吧", Category.READING));
		rules.add(new Pair<>("漫画", Category.READING));

		rules.add(new Pair<>("笔记", Category.OFFICE));
		rules.add(new Pair<>("WPS", Category.OFFICE));
		rules.add(new Pair<>("PDF", Category.OFFICE));
		rules.add(new Pair<>("Office", Category.OFFICE));
		rules.add(new Pair<>("Reader", Category.OFFICE));
		rules.add(new Pair<>("邮件", Category.OFFICE));
		rules.add(new Pair<>("邮箱", Category.OFFICE));
		rules.add(new Pair<>("百度云", Category.OFFICE));
		rules.add(new Pair<>("备忘录", Category.OFFICE));
		rules.add(new Pair<>("领英", Category.OFFICE));
		rules.add(new Pair<>("文档", Category.OFFICE));
		rules.add(new Pair<>("扫描", Category.OFFICE));
		rules.add(new Pair<>("全能王", Category.OFFICE));

		rules.add(new Pair<>("系统", Category.SYSTEM));
		rules.add(new Pair<>("android", Category.SYSTEM));
		rules.add(new Pair<>("浏览器", Category.SYSTEM));
		rules.add(new Pair<>("文件", Category.SYSTEM));
		rules.add(new Pair<>("输入法", Category.SYSTEM));
		rules.add(new Pair<>("安全", Category.SYSTEM));
		rules.add(new Pair<>("管家", Category.SYSTEM));
		rules.add(new Pair<>("卫士", Category.SYSTEM));
		rules.add(new Pair<>("桌面", Category.SYSTEM));
		rules.add(new Pair<>("壁纸", Category.SYSTEM));
		rules.add(new Pair<>("助手", Category.SYSTEM));
		rules.add(new Pair<>("Flash", Category.SYSTEM));
		rules.add(new Pair<>("计算器", Category.SYSTEM));
		rules.add(new Pair<>("Installer", Category.SYSTEM));
		rules.add(new Pair<>("屏保", Category.SYSTEM));
		rules.add(new Pair<>("屏幕保护", Category.SYSTEM));
		rules.add(new Pair<>("搜索", Category.SYSTEM));
		rules.add(new Pair<>("联系人", Category.SYSTEM));
		rules.add(new Pair<>("帮助程序", Category.SYSTEM));
		rules.add(new Pair<>("HTML", Category.SYSTEM));
		rules.add(new Pair<>("Log", Category.SYSTEM));
		rules.add(new Pair<>("GPS", Category.SYSTEM));
		rules.add(new Pair<>("键盘", Category.SYSTEM));
		rules.add(new Pair<>("输入法", Category.SYSTEM));
		rules.add(new Pair<>("密钥", Category.SYSTEM));
		rules.add(new Pair<>("定位", Category.SYSTEM));
		rules.add(new Pair<>("信息", Category.SYSTEM));
		rules.add(new Pair<>("安装", Category.SYSTEM));
		rules.add(new Pair<>("手机", Category.SYSTEM));
		rules.add(new Pair<>("下载", Category.SYSTEM));
		rules.add(new Pair<>("设置", Category.SYSTEM));
		rules.add(new Pair<>("存储", Category.SYSTEM));
		rules.add(new Pair<>("服务", Category.SYSTEM));
		rules.add(new Pair<>("管理", Category.SYSTEM));
		rules.add(new Pair<>("备份", Category.SYSTEM));
		rules.add(new Pair<>("锁屏", Category.SYSTEM));
		rules.add(new Pair<>("主题", Category.SYSTEM));
		rules.add(new Pair<>("WLAN", Category.SYSTEM));
		rules.add(new Pair<>("蓝牙", Category.SYSTEM));
		rules.add(new Pair<>("手电", Category.SYSTEM));
		rules.add(new Pair<>("压缩", Category.SYSTEM));
		rules.add(new Pair<>("豌豆荚", Category.SYSTEM));
		rules.add(new Pair<>("薄暮微光", Category.SYSTEM));
		rules.add(new Pair<>("Firefox", Category.SYSTEM));
		rules.add(new Pair<>("安兔兔", Category.SYSTEM));
		rules.add(new Pair<>("Monitor", Category.SYSTEM));
		rules.add(new Pair<>("拨号", Category.SYSTEM));
		rules.add(new Pair<>("光束", Category.SYSTEM));
		rules.add(new Pair<>("网络", Category.SYSTEM));
		rules.add(new Pair<>("设备", Category.SYSTEM));
		rules.add(new Pair<>("SIM", Category.SYSTEM));
	}
}

class Pair<F, S> {

	public F first;

	public S second;

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}
}
