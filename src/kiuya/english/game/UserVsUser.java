package kiuya.english.game;

public class UserVsUser extends GameModel {
	void StartGame(){
		findword = false;	//�O�_����r���ܼ�
		userPlay();
		if(findword) nextUser(); //�I�s�U�@�쪱�a
	}
	
	void CheckWinner(){
		int playerWinner = 0; //�O����Ӫ����a
		int count = 0; //�O���w�g�뭰�����a�ƶq
		
		for(int i=0; i<StaticVariable.peopleNum; i++){
			if(StaticVariable.playersSurrender[i] == 0)
				playerWinner = i+1;
			else
				count++;
		}
		if(count == StaticVariable.peopleNum-1){
			StaticVariable.msg += "\\n�ѩ��L���a�w�{��A�Ѫ��a" + playerWinner + "��o�ӧQ!!";
			StaticVariable.isExit = true;
		}
		if(!StaticVariable.isExit) nextUser();
	}
	
	void nextUser(){
		boolean findPeople = false;
		while(!findPeople){
			if(StaticVariable.leader == StaticVariable.peopleNum)
				StaticVariable.leader = 0;
			StaticVariable.leader++;
			if(StaticVariable.playersSurrender[StaticVariable.leader-1] != 1)
				findPeople = true;
		}
	}

	boolean CheckCmd(String word){
		if(word.equals("\\c")){
			if(playersHelp[StaticVariable.leader-1] > 0){
				if(CheckHead("noinput") || firstInput)
					CheckWord("noinput");
				if(findword){
					playersHelp[StaticVariable.leader-1]--; //�����@���ϴ�����
					StaticVariable.msg += "\\n�ϥαϴ���r�\��A�ٳѾl"+playersHelp[StaticVariable.leader-1]+"���i�H�ϥ�~~";
					if(firstInput) firstInput=false;
				}
			}else{
				StaticVariable.msg += "���a" + StaticVariable.leader + "���ϴ����Ƥw�Χ�!!";
			}
			return true;
		}else if(word.equals("\\s")){
			StaticVariable.msg += "���a" + StaticVariable.leader + "�w�g�{��";
			StaticVariable.playersSurrender[StaticVariable.leader-1] = 1;
			CheckWinner();
			return true;
		}else if(word.equals("\\e")){
			StaticVariable.msg += "���}�什~~";
			StaticVariable.isExit = true;
			return true;
		}else if(word.equals("\\z")){
			if(playersComplementWord[StaticVariable.leader-1] > 0){
				System.out.print("�i�J��L�H��r�ɥR�t��!!\\n���^�X�Ѿl�i�Φ���" + playersComplementWord[StaticVariable.leader-1] + "��!!\\n�п�J�L�H��r(�̤�5�Ӧr�H�W)�G");
				CheckComplementWord();
				playersComplementWord[StaticVariable.leader-1]--;
			}else{
				StaticVariable.msg += "���a" + StaticVariable.leader + "���L�H��r�ɥR���Ƥw�Χ�!!";
			}
			return true;
		}else if(word.substring(0,1).equals("\\")){
			StaticVariable.msg += "�ëD���T���O!!";
			return true;
		}else{
			return false;
		}
	}
	
	void CheckFindWord(int alphword, int alphindex, boolean errMsg){ 		
   		if(findword){	//�P�_�O�_����r
   			HisWord = u_word;	//�N�ثe��r�s�J���v��r
   			StaticVariable.wordArr[alphindex][alphword][2] = "y";	//�]�w����r�Q�ϥιL
   			StaticVariable.hisWord[StaticVariable.leader] = StaticVariable.wordArr[alphindex][alphword][0];	//�b�e���W��ܹq���ϥΪ���r
   			StaticVariable.wordArr[alphindex][alphword][3] = String.valueOf(StaticVariable.leader);  //�]�w����r�����ax�ҥ�
   			StaticVariable.msg += StaticVariable.wordArr[alphindex][alphword][0] + "(�r���G" + GetLastChar(u_word) +") " + StaticVariable.wordArr[alphindex][alphword][1]; //�L�X��r
   		}else if(!errMsg){
   			StaticVariable.msg += "�d����o�ӳ�r!!";
   		}
	}
}