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
		String gameMode = tp.checkNull(req.getParameter("gameMode"),"1"); //�w�]��H�Ҧ�
		String peopleNum = tp.checkNull(req.getParameter("peopleNum"),"1"); //�w�]1�H
		String param = "&gameMode="+gameMode+"&peopleNum="+peopleNum;
		
		gameImpl = new EnglishGameImplement();
		gameImpl.setGameMode(gameMode); //�]�w�C���Ҧ�
		gameImpl.setGameData(); //�]�w�C���ѼƩM���
		System.out.println("��Ƴ]�w����");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		map.put("paga", "game.do");
		map.put("method", "goHeadGameScreen");
		return new ModelAndView(this.getFrameLoaderPage(),"pageInfo",map);
	}
	
	public ModelAndView goHeadGameScreen(HttpServletRequest req, HttpServletResponse res) {
		String gameMode = tp.checkNull(req.getParameter("gameMode"),"1"); //�w�]��H�Ҧ�
		String peopleNum = tp.checkNull(req.getParameter("peopleNum"),"1"); //�w�]1�H
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("gameMode", gameMode);
		map.put("peopleNum", peopleNum);
		map.put("leader", "1"); //�Ĥ@�쪱�a
		map.put("isFirstEntry", "Y"); //�Ĥ@���i�J�C���e��
		map.put("isFirstInput", "Y"); //�Ĥ@����J�^���r
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
		gameImpl.checkAlph(); //�ˬd�ϥΪ̿�J���r��
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("gameMode", gameMode);
		map.put("peopleNum", String.valueOf(peopleNum));
		map.put("leader", leader);
		map.put("msg", gameImpl.getMessage());
		System.out.println("msg = " + gameImpl.getMessage());
		System.out.println("��r�ˬd����");
		return new ModelAndView(this.getGameScreenPage(),"gameInfo",map);
	}
	
	public String getFrameLoaderPage() {return frameLoaderPage;}
	public void setFrameLoaderPage(String frameLoaderPage) {this.frameLoaderPage = frameLoaderPage;}
	
	public String getGameScreenPage() {return gameScreenPage;}
	public void setGameScreenPage(String gameScreenPage) {this.gameScreenPage = gameScreenPage;}
}
