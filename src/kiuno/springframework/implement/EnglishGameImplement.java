package kiuno.springframework.implement;

import kiuya.english.game.*;

public class EnglishGameImplement {
	public void setGameMode(String mode){
		if("1".equals(mode)) StaticVariable.game = new UserVsCom();
		else if("2".equals(mode)) StaticVariable.game = new UserVsUser();;
	}
	
	public void setGameData(){
		StaticVariable.game.iniGame(); //��l�ƹC�����
	}
	
	public void checkAlph(){
		StaticVariable.game.runGame(); //����C��
	}
	
	public void saveRecordToFile(){
		StaticVariable.game.saveRecord(); //�O�_�N�C����r�����ɮ�
	}
}
