package kiuno.springframework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.ModelAndView;

public class MenuController extends MultiActionController {
	private String indexPage, menuPage;

	public ModelAndView goHeadIndex(HttpServletRequest req, HttpServletResponse res) {
		return new ModelAndView(this.getIndexPage());
	}
	
	public ModelAndView goHeadMenu(HttpServletRequest req, HttpServletResponse res) {
		return new ModelAndView(this.getMenuPage());
	}

	public String getIndexPage() {return indexPage;}
	public void setIndexPage(String indexPage) {this.indexPage = indexPage;}

	public String getMenuPage() {return menuPage;}
	public void setMenuPage(String menuPage) {this.menuPage = menuPage;}
}
