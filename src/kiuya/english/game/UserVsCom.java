package kiuya.english.game;

public class UserVsCom extends GameModel {
	void StartGame(){
		findword = false;	//�O�_����r���ܼ�
		if(StaticVariable.leader == 1){
			userPlay();
		}else{
		  	CheckWord("noinput");
		}
	}
	
	boolean CheckCmd(String word){
		if(word.equals("\\r")){
			if(hasRecord){
				hasRecord = false;
				StaticVariable.msg += "�O���q���ϥγ�r=Off";
			}else{
				hasRecord = true;
				StaticVariable.msg += "�O���q���ϥγ�r=On";
			}
			return true;
		}else if(word.equals("\\c")){
			if(playerHelp > 0){
				if(CheckHead("noinput") || firstInput)
					CheckWord("noinput");
				if(findword){
					playerHelp--; //�����@���ϴ�����
					StaticVariable.msg += "\\n�ϥαϴ���r�\��A�ٳѾl"+playerHelp+"���i�H�ϥ�~~";
					if(firstInput) firstInput=false;
				}
			}else{
				StaticVariable.msg += "�ϴ����Ƥw�Χ�!!";
			}
			return true;
		}else if(word.equals("\\s")){
			StaticVariable.msg += "�w�V�q���{��!!\\n���}�什~~";
			StaticVariable.isExit = true;
			return true;
		}else if(word.equals("\\e")){
			StaticVariable.msg += "���}�什~~";
			StaticVariable.isExit = true;
			return true;
		}else if(word.equals("\\z")){
			if(StaticVariable.maxComplementWord > 0){
				StaticVariable.msg += "�i�J��L�H��r�ɥR�t��!!\\n���^�X�Ѿl�i�Φ���" + StaticVariable.maxComplementWord + "��!!\\n�п�J�L�H��r(�̤�5�Ӧr�H�W)�G";
				CheckComplementWord();
				StaticVariable.maxComplementWord--;
			}else{
				StaticVariable.msg += "�L�H��r�ɥR���Ƥw�Χ�!!";
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
   			if(StaticVariable.leader==0){
   				StaticVariable.wordArr[alphindex][alphword][3] = "0";  //�]�w����r���q���ҥ�
   				StaticVariable.leader=1;  //���쪱�a1
   			}else{
   				StaticVariable.wordArr[alphindex][alphword][3] = "1";  //�]�w����r�����a1�ҥ�
   				StaticVariable.leader=0;  //����q��
   			}
   			StaticVariable.msg += StaticVariable.wordArr[alphindex][alphword][0] + "(�r���G" + GetLastChar(u_word) +") " + StaticVariable.wordArr[alphindex][alphword][1];  //�L�X��r
   		}else if(!errMsg){
   			StaticVariable.msg += "�d����o�ӳ�r!!";
   		}
	}
}