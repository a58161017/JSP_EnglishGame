package kiuno.springframework.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import kiuno.springframework.implement.EnglishGameImplement;
import kiuno.utility.TextProcessor;
import kiuya.english.game.StaticVariable;

public class EnglishGameController extends MultiActionController {
	private String frameLoaderPage,gameScreenPage;
	private TextProcessor tp = new TextProcessor();
	private EnglishGameImplement gameImpl = null;

	public ModelAndView startGame(HttpServletRequest req, HttpServletResponse res) {
		String gameMode = tp.checkNull(req.getParameter("gameMode"),"1"); //預設單人模式
		String peopleNum = tp.checkNull(req.getParameter("peopleNum"),"1"); //預設1人
		String param = "&gameMode="+gameMode+"&peopleNum="+peopleNum;
		
		gameImpl = new EnglishGameImplement();
		gameImpl.setGameMode(gameMode); //設定遊戲模式
		gameImpl.setPeopleNum(peopleNum); //設定遊戲人數
		gameImpl.setGameData(); //設定遊戲參數和資料
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		map.put("paga", "game.do");
		map.put("method", "goHeadGameScreen");
		return new ModelAndView(this.getFrameLoaderPage(),"pageInfo",map);
	}
	
	public ModelAndView goHeadGameScreen(HttpServletRequest req, HttpServletResponse res) {
		String gameMode = tp.checkNull(req.getParameter("gameMode"),"1"); //預設單人模式
		String peopleNum = tp.checkNull(req.getParameter("peopleNum"),"1"); //預設1人
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("gameMode", gameMode);
		map.put("peopleNum", peopleNum);
		map.put("leader", "1"); //第一位玩家
		map.put("isFirstEntry", "Y"); //第一次進入遊戲畫面
		map.put("isFirstInput", "Y"); //第一次輸入英文單字
		return new ModelAndView(this.getGameScreenPage(),"gameInfo",map);
	}
	
	public ModelAndView checkAlph(HttpServletRequest req, HttpServletResponse res) {
		String gameMode = tp.checkNull(req.getParameter("gameMode"),"1");
		int peopleNum = Integer.parseInt(tp.checkNull(req.getParameter("peopleNum"),"1"));
		String leader = tp.checkNull(req.getParameter("leader"),"1"); //if '0'=>computer, '1'=>player1
		String complementWord = tp.checkNull(req.getParameter("complementWord"),"");
		String word = "";
		if("0".equals(leader)){ 
			word = tp.checkNull(req.getParameter("computer"),"");
			StaticVariable.msg = "電腦:";
		}else{ 
			word = tp.checkNull(req.getParameter("player"+leader),"");
			StaticVariable.msg = "玩家"+leader+":";
		}
		
		StaticVariable.hisWord[Integer.parseInt(leader)] = word;
		
		gameImpl.setLeader(leader);
		gameImpl.setWord(word);
		gameImpl.setComplementWord(complementWord);
		gameImpl.checkAlph(); //檢查使用者輸入的字串
		
		if(StaticVariable.isExit) gameImpl.saveRecordToFile(); //遊戲對局結束後檢查是否需要記錄單字
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gameMode", gameMode);
		map.put("peopleNum", String.valueOf(peopleNum));
		map.put("leader", gameImpl.getLeader());
		map.put("msg", gameImpl.getMessage());
		map.put("hisWord", gameImpl.getHisWord());
		map.put("playersSurrender", gameImpl.getPlayersSurrender()); //多人模式使用
		map.put("isExit", StaticVariable.isExit?"Y":"N");
		
		return new ModelAndView(this.getGameScreenPage(),"gameInfo",map);
	}
	
	public String getFrameLoaderPage() {return frameLoaderPage;}
	public void setFrameLoaderPage(String frameLoaderPage) {this.frameLoaderPage = frameLoaderPage;}
	
	public String getGameScreenPage() {return gameScreenPage;}
	public void setGameScreenPage(String gameScreenPage) {this.gameScreenPage = gameScreenPage;}
}
