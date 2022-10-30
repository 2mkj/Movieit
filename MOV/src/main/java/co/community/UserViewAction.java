package co.community;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.commuboard.Community;
import co.commuboard.CommunityDAO;

public class UserViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CommunityDAO boarddao = new CommunityDAO();
		List<Community> boardlist = new ArrayList<Community>();
		
		String name = request.getParameter("board_name");
		String user_name = request.getParameter("user_name");
		System.out.println("유저아이디="+name+", 유저닉="+user_name);
		
		int page = 1; //보여줄 page
		int limit = 10; //한 페이지에 보여줄 게시판 목록의 수
		int listcount = 0;
		if (request.getParameter("page") != null) {page = Integer.parseInt(request.getParameter("page"));}
		if (request.getParameter("limit") != null) {limit = Integer.parseInt(request.getParameter("limit"));}
		
		listcount = boarddao.getuserListCount(name);
		boardlist = boarddao.getuserWriting(name, page, limit);
		
		int maxpage = (listcount + limit - 1) / limit;
		int startpage = ((page - 1) / 10) * 10 + 1;
		int endpage = startpage + 10 - 1;
		if (endpage > maxpage) endpage = maxpage;
			request.setAttribute("name", name);
			request.setAttribute("page", page); // 현재 페이지 수
			request.setAttribute("maxpage", maxpage); //최대 페이지 수
			request.setAttribute("startpage", startpage);
			request.setAttribute("endpage", endpage);
			request.setAttribute("listcount", listcount); //총 글의 수
			request.setAttribute("boardlist", boardlist);
			request.setAttribute("limit", limit);
			ActionForward forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("community/userView.jsp");
			return forward; 
	}
}

