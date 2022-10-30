package net.mypage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.commuboard.Community;
import co.commuboard.CommunityDAO;
import net.member.MemberDAO2;
import net.member.Memberall;
import net.zzim.db.Zzim;
import net.zzim.db.ZzimDAO;


public class MainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		MemberDAO2 mdao = new MemberDAO2();
		Memberall m = mdao.member_info(email);
		CommunityDAO cdao = new CommunityDAO();
		ZzimDAO zdao = new ZzimDAO();
		List<Zzim> z = new ArrayList<Zzim>();
		z = zdao.preZzim();
		
		List<Community> boardlist = new ArrayList<Community>();
		int page = 1; //보여줄 page
		int limit = 5; //한 페이지에 보여줄 게시판 목록의 수
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		// 추가
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}
		int listcount = cdao.getListCount();
		boardlist = cdao.getBoardList(page, limit);
		
		int maxpage = (listcount + limit - 1) / limit;
		int startpage = ((page - 1) / 10) * 10 + 1;
		int endpage = startpage + 10 - 1;
		if (endpage > maxpage) endpage = maxpage;
		
			request.setAttribute("page", page); // 현재 페이지 수
			request.setAttribute("maxpage", maxpage); //최대 페이지 수
			request.setAttribute("startpage", startpage);
			request.setAttribute("endpage", endpage);
			request.setAttribute("listcount", listcount); //총 글의 수
			request.setAttribute("boardlist", boardlist);
			request.setAttribute("memberinfo", m);
			request.setAttribute("limit", limit);
			request.setAttribute("memberinfo", m);
			request.setAttribute("zzim", z);
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/mainpage/main.jsp");
		return forward;
	}

}
