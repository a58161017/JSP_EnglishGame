package kiuno.springframework.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import kiuno.springframework.implement.EnglishGameImplement;
import kiuno.utility.TextProcessor;

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
		gameImpl.setGameData(); //設定遊戲參數和資料
		System.out.println("資料設定完成");
		
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
		String leader = tp.checkNull(req.getParameter("leader"),"1"); //if '1'=>player1, '0'=>computer
		String word = "";
		if("0".equals(leader)) word = tp.checkNull(req.getParameter("computer"),"");
		else word = tp.checkNull(req.getParameter("player"+leader),"");
		System.out.println("leader = " + leader);
		System.out.println("word = " + word);
		
		gameImpl.setLeader(leader);
		gameImpl.setWord(word);
		gameImpl.checkAlph(); //檢查使用者輸入的字串
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("gameMode", gameMode);
		map.put("peopleNum", String.valueOf(peopleNum));
		map.put("leader", leader);
		map.put("msg", gameImpl.getMessage());
		System.out.println("msg = " + gameImpl.getMessage());
		System.out.println("單字檢查完成");
		return new ModelAndView(this.getGameScreenPage(),"gameInfo",map);
	}
	
	public String getFrameLoaderPage() {return frameLoaderPage;}
	public void setFrameLoaderPage(String frameLoaderPage) {this.frameLoaderPage = frameLoaderPage;}
	
	public String getGameScreenPage() {return gameScreenPage;}
	public void setGameScreenPage(String gameScreenPage) {this.gameScreenPage = gameScreenPage;}
}
