package kiuya.english.game;

public class StaticVariable {
	public static String realPath = ""; //�ɮ׹�ڰ����m
	public static String msg = ""; //�O���C���T��
	public static String word = ""; //�ϥΪ̿�J�r��
	public static int leader = 1; //�ثe�ޱ���
	public static int peopleNum = 1; //�C���H��
	public static String hisWord[] = null; //�O���C�@��ާ@�̪���e��r
	public static boolean isExit = false; //�O�_���}�C��
	public static int playersSurrender[] = null; //�O���뭰��(��=0��ܩ|���{��A�Ȭ�1��ܤw�{��)
	public static GameModel game = null; //�C���Ҧ�
	public static String wordArr[][][]; //�Ҧ���r��T
	public static int maxUsers, maxHelp, maxComplementWord; //maxUsers=�̦h�ѻP�C���H�ơAmaxHelp=�̤j�ϴ����ơAmaxComplementWord=�̤j�L�H��r�ɥR����
	public static boolean historyWord, complementWord; //historyWord=�O�����v��r�AcomplementWord=��r�ɥR
}
