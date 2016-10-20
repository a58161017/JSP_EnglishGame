package kiuno.springframework.implement;

import kiuya.english.game.*;

public class EnglishGameImplement {
	public void setGameMode(String mode){
		if("1".equals(mode)) StaticVariable.game = new UserVsCom();
		else if("2".equals(mode)) StaticVariable.game = new UserVsUser();
	}
	
	public void setExternalParam(){
		(new Setting()).setConfig(); //呼叫Setting的Method匯入外部設定檔
    }
	
	public void setGameData(){
		StaticVariable.game.iniGame(); //初始化遊戲資料
	}
	
	public void checkAlph(){
		StaticVariable.game.runGame(); //執行遊戲
	}
	
	public void saveRecordToFile(){
		StaticVariable.game.saveRecord(); //是否將遊戲單字紀錄檔案
	}
	
	/*****************************參數存取**********************************/
	public String getMessage(){
		return StaticVariable.msg; //回傳遊戲訊息
	}
	
	public void setComplementWord(String ComplementWord){
		StaticVariable.playerComplementWord = ComplementWord; //設定使用者輸入印象單字
	}
	
	public void setWord(String word){
		StaticVariable.word = word; //設定使用者輸入字串
	}
	
	public void setLeader(String leader){
		StaticVariable.leader = Integer.parseInt(leader); //設定目前操控者
	}
	
	public String getLeader(){
		return String.valueOf(StaticVariable.leader); //回傳下一回合操控者
	}
	
	public void setPeopleNum(String peopleNum){
		StaticVariable.peopleNum = Integer.parseInt(peopleNum); //設定遊戲人數
		StaticVariable.hisWord = new String[StaticVariable.peopleNum+1]; //index=0 給電腦使用
	}
	
	public String[] getHisWord(){
		return StaticVariable.hisWord; //回傳所有操作者當前的單字
	}
	
	public int[] getPlayersSurrender(){
		return StaticVariable.playersSurrender; //回傳所有操作者當前的投降狀態
	}
}
