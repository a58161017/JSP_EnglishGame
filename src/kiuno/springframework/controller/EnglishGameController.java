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
		String gameMode = tp.checkNull(req.getParameter("gameMode"),"1"); //�w�]��H�Ҧ�
		String peopleNum = tp.checkNull(req.getParameter("peopleNum"),"1"); //�w�]1�H
		String param = "&gameMode="+gameMode+"&peopleNum="+peopleNum;
		
		gameImpl = new EnglishGameImplement();
		gameImpl.setGameMode(gameMode); //�]�w�C���Ҧ�
		gameImpl.setPeopleNum(peopleNum); //�]�w�C���H��
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
		String leader = tp.checkNull(req.getParameter("leader"),"1"); //if '0'=>computer, '1'=>player1
		String word = "";
		if("0".equals(leader)){ 
			word = tp.checkNull(req.getParameter("computer"),"");
			StaticVariable.msg = "�q��:";
		}else{ 
			word = tp.checkNull(req.getParameter("player"+leader),"");
			StaticVariable.msg = "���a"+leader+":";
		}
		System.out.println("--------------------����e--------------------");
		System.out.println("gameMode = " + gameMode);
		System.out.println("peopleNum = " + peopleNum);
		System.out.println("leader = " + leader);
		System.out.println("word = " + word);
		System.out.println("-------------------------------------------");
		
		StaticVariable.hisWord[Integer.parseInt(leader)] = word;
		
		gameImpl.setLeader(leader);
		gameImpl.setWord(word);
		gameImpl.checkAlph(); //�ˬd�ϥΪ̿�J���r��
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gameMode", gameMode);
		map.put("peopleNum", String.valueOf(peopleNum));
		map.put("leader", gameImpl.getLeader());
		map.put("msg", gameImpl.getMessage());
		map.put("hisWord", gameImpl.getHisWord());
		System.out.println("--------------------�����--------------------");
		System.out.println("word = " + StaticVariable.word);
		System.out.println("leader = " + StaticVariable.leader);
		System.out.println("peopleNum = " + StaticVariable.peopleNum);
		System.out.println("maxHelp = " + StaticVariable.maxHelp);
		System.out.println("historyWord = " + StaticVariable.historyWord);
		System.out.println("complementWord = " + StaticVariable.complementWord);
		System.out.println("hisWord[0] = " + StaticVariable.hisWord[0]);
		System.out.println("hisWord[1] = " + StaticVariable.hisWord[1]);
		System.out.println("msg = " + gameImpl.getMessage());
		System.out.println("-------------------------------------------");
		return new ModelAndView(this.getGameScreenPage(),"gameInfo",map);
	}
	
	public String getFrameLoaderPage() {return frameLoaderPage;}
	public void setFrameLoaderPage(String frameLoaderPage) {this.frameLoaderPage = frameLoaderPage;}
	
	public String getGameScreenPage() {return gameScreenPage;}
	public void setGameScreenPage(String gameScreenPage) {this.gameScreenPage = gameScreenPage;}
}
