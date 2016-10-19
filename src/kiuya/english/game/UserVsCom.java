package kiuya.english.game;

public class UserVsCom extends GameModel {
	void StartGame(){
		findword = false;	//是否找到單字的變數
		if(StaticVariable.leader == 1){
			System.out.println("檢查玩家單字");
			userPlay();
		}else{
			System.out.println("檢查電腦單字");
		  	CheckWord("noinput");
		}
	}
	
	boolean CheckCmd(String word){
		if(word.equals("\\r")){
			if(hasRecord){
				hasRecord = false;
				StaticVariable.msg = "記錄電腦使用單字=Off";
			}else{
				hasRecord = true;
				StaticVariable.msg = "記錄電腦使用單字=On";
			}
			return true;
		}else if(word.equals("\\c")){
			if(StaticVariable.maxHelp > 0){
				if(CheckHead("noinput") || firstInput)
					CheckWord("noinput");
				if(findword){
					StaticVariable.maxHelp--; //扣掉一次救援次數
					if(firstInput)
						firstInput=false;
				}
			}else{
				StaticVariable.msg = "救援次數已用完!!";
			}
			return true;
		}else if(word.equals("\\s")){
			StaticVariable.msg = "您已向電腦認輸!!\n離開對局~~";
			hasExit = true;
			return true;
		}else if(word.equals("\\e")){
			StaticVariable.msg = "離開對局~~";
			hasExit = true;
			return true;
		}else if(word.equals("\\z")){
			if(StaticVariable.maxComplementWord > 0){
				StaticVariable.msg = "進入到印象單字補充系統!!\n本回合剩餘可用次數" + StaticVariable.maxComplementWord + "次!!\n請輸入印象單字(最少5個字以上)：";
				CheckComplementWord();
				StaticVariable.maxComplementWord--;
			}else{
				StaticVariable.msg = "印象單字補充次數已用完!!";
			}
			return true;
		}else if(word.substring(0,1).equals("\\")){
			StaticVariable.msg = "並非正確指令，可使用\\h查看指令";
			return true;
		}else{
			return false;
		}
	}
	
	void CheckFindWord(int alphword, int alphindex, boolean errMsg){
		if(findword){	//判斷是否找到單字
   			HisWord = u_word;	//將目前單字存入歷史單字
   			StaticVariable.wordArr[alphindex][alphword][2] = "y";	//設定此單字被使用過
   			if(StaticVariable.leader==0){
   				StaticVariable.wordArr[alphindex][alphword][3] = "0";  //設定此單字為玩家1所用
   				StaticVariable.leader=1;  //輪到玩家1
   			}else{
   				StaticVariable.wordArr[alphindex][alphword][3] = "1";  //設定此單字為電腦所用
   				StaticVariable.leader=0;  //輪到電腦
   			}
   			StaticVariable.msg = StaticVariable.wordArr[alphindex][alphword][0] + "(字尾：" + GetLastChar(u_word) +") " + StaticVariable.wordArr[alphindex][alphword][1];  //印出單字
   		}else if(!errMsg){
   			StaticVariable.msg = "查不到這個單字!!";
   		}
	}
}