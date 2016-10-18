package kiuya.english.game;

public class StaticVariable {
	public static String realPath = ""; //檔案實際執行位置
	public static GameModel game = null;
	public static String wordArr[][][];
	public static int maxUsers, maxHelp, maxComplementWord; //maxUsers=最多參與遊戲人數，maxHelp=最大救援次數，maxComplementWord=最大印象單字補充次數
	public static boolean historyWord, complementWord; //historyWord=記錄歷史單字，complementWord=單字補充
}
