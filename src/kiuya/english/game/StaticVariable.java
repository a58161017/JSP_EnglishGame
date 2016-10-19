package kiuya.english.game;

public class StaticVariable {
	public static String realPath = ""; //檔案實際執行位置
	public static String msg = ""; //記錄遊戲訊息
	public static String word = ""; //使用者輸入字串
	public static int leader = 1; //目前操控者
	public static GameModel game = null; //遊戲模式
	public static String wordArr[][][]; //所有單字資訊
	public static int maxUsers, maxHelp, maxComplementWord; //maxUsers=最多參與遊戲人數，maxHelp=最大救援次數，maxComplementWord=最大印象單字補充次數
	public static boolean historyWord, complementWord; //historyWord=記錄歷史單字，complementWord=單字補充
}
