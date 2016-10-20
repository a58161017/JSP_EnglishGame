package kiuno.springframework.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import kiuno.springframework.implement.EnglishGameImplement;
import kiuya.english.game.StaticVariable;

import org.springframework.web.servlet.ModelAndView;

public class MenuController extends MultiActionController {
	private String indexPage, menuPage;

	public ModelAndView goHeadIndex(HttpServletRequest req, HttpServletResponse res) {
		return new ModelAndView(this.getIndexPage());
	}
	
	public ModelAndView goHeadMenu(HttpServletRequest req, HttpServletResponse res) {
		try {
			StaticVariable.realPath = new String((req.getParameter("path")).getBytes("ISO-8859-1"),"Big5");
			(new EnglishGameImplement()).setExternalParam();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ModelAndView(this.getMenuPage(),"maxUsers",StaticVariable.maxUsers);
	}

	public String getIndexPage() {return indexPage;}
	public void setIndexPage(String indexPage) {this.indexPage = indexPage;}

	public String getMenuPage() {return menuPage;}
	public void setMenuPage(String menuPage) {this.menuPage = menuPage;}
}
