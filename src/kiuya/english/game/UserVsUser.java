package kiuya.english.game;

public class UserVsUser extends GameModel {
	void StartGame(){
		findword = false;	//是否找到單字的變數
		userPlay();
		if(findword) nextUser(); //呼叫下一位玩家
	}
	
	void CheckWinner(){
		int playerWinner = 0; //記錄獲勝的玩家
		int count = 0; //記錄已經投降的玩家數量
		
		for(int i=0; i<StaticVariable.peopleNum; i++){
			if(StaticVariable.playersSurrender[i] == 0)
				playerWinner = i+1;
			else
				count++;
		}
		if(count == StaticVariable.peopleNum-1){
			StaticVariable.msg += "\\n由於其他玩家已認輸，由玩家" + playerWinner + "獲得勝利!!";
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
					playersHelp[StaticVariable.leader-1]--; //扣掉一次救援次數
					StaticVariable.msg += "\\n使用救援單字功能，還剩餘"+playersHelp[StaticVariable.leader-1]+"次可以使用~~";
					if(firstInput) firstInput=false;
				}
			}else{
				StaticVariable.msg += "玩家" + StaticVariable.leader + "的救援次數已用完!!";
			}
			return true;
		}else if(word.equals("\\s")){
			StaticVariable.msg += "玩家" + StaticVariable.leader + "已經認輸";
			StaticVariable.playersSurrender[StaticVariable.leader-1] = 1;
			CheckWinner();
			return true;
		}else if(word.equals("\\e")){
			StaticVariable.msg += "離開對局~~";
			StaticVariable.isExit = true;
			return true;
		}else if(word.equals("\\z")){
			if(playersComplementWord[StaticVariable.leader-1] > 0){
				System.out.print("進入到印象單字補充系統!!\\n本回合剩餘可用次數" + playersComplementWord[StaticVariable.leader-1] + "次!!\\n請輸入印象單字(最少5個字以上)：");
				CheckComplementWord();
				playersComplementWord[StaticVariable.leader-1]--;
			}else{
				StaticVariable.msg += "玩家" + StaticVariable.leader + "的印象單字補充次數已用完!!";
			}
			return true;
		}else if(word.substring(0,1).equals("\\")){
			StaticVariable.msg += "並非正確指令!!";
			return true;
		}else{
			return false;
		}
	}
	
	void CheckFindWord(int alphword, int alphindex, boolean errMsg){ 		
   		if(findword){	//判斷是否找到單字
   			HisWord = u_word;	//將目前單字存入歷史單字
   			StaticVariable.wordArr[alphindex][alphword][2] = "y";	//設定此單字被使用過
   			StaticVariable.hisWord[StaticVariable.leader] = StaticVariable.wordArr[alphindex][alphword][0];	//在畫面上顯示電腦使用的單字
   			StaticVariable.wordArr[alphindex][alphword][3] = String.valueOf(StaticVariable.leader);  //設定此單字為玩家x所用
   			StaticVariable.msg += StaticVariable.wordArr[alphindex][alphword][0] + "(字尾：" + GetLastChar(u_word) +") " + StaticVariable.wordArr[alphindex][alphword][1]; //印出單字
   		}else if(!errMsg){
   			StaticVariable.msg += "查不到這個單字!!";
   		}
	}
}