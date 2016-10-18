package kiuno.springframework.implement;

import kiuya.english.game.*;

public class EnglishGameImplement {
	public void setGameMode(String mode){
		if("1".equals(mode)) StaticVariable.game = new UserVsCom();
		else if("2".equals(mode)) StaticVariable.game = new UserVsUser();;
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
}
