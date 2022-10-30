package net.mypage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.comment.db.Comment;
import co.comment.db.CommentDAO;
import net.member.MemberDAO2;
import net.member.Memberall;

public class myCommentsAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		System.out.println(email);
		MemberDAO2 mdao = new MemberDAO2();
		Memberall m = mdao.member_info(email);

		
		CommentDAO boarddao = new CommentDAO();
		List<Comment> boardlist = new ArrayList<Comment>();

		
		int page = 1; //보여줄 page
		int limit = 10; //한 페이지에 보여줄 게시판 목록의 수
		
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}

		int listcount = 0;
		String search_word="";
		
		if (request.getParameter("search_word") == null
				|| request.getParameter("search_word").equals("")) {
			// 총 리스트 수를 받아옵니다.
			listcount = boarddao.getmyCommentListCount(email);
			boardlist = boarddao.getmyCommentWriting(email, page, limit);
		} else { // 검색을 클릭한 경우
			search_word = request.getParameter("search_word");
			listcount = boarddao.getmyCommentListCount(email, search_word);
			boardlist = boarddao.getmyCommentWriting(email, search_word , page, limit);
		}
		System.out.println("boardlist =" + boardlist);
		
		int maxpage = (listcount + limit - 1) / limit;
		int startpage = ((page - 1) / 10) * 10 + 1;
		int endpage = startpage + 10 - 1;
		if (endpage > maxpage) endpage = maxpage;
		
		System.out.println("총 페이지수 =" + maxpage);
		System.out.println("현재 페이지에 보여줄 마지막 페이지 수 :" + endpage);
		System.out.println("현재 페이지에 보여줄 시작 페이지 수 :" + startpage);

		
			request.setAttribute("page", page); // 현재 페이지 수
			request.setAttribute("maxpage", maxpage); //최대 페이지 수
			request.setAttribute("startpage", startpage);
			request.setAttribute("endpage", endpage);
			request.setAttribute("listcount", listcount); //총 글의 수
			request.setAttribute("boardlist", boardlist);
			request.setAttribute("limit", limit);
			request.setAttribute("memberinfo", m);
			request.setAttribute("search_word", search_word);
			ActionForward forward = new ActionForward();
			forward.setRedirect(false);
			
			forward.setPath("mypage/myComments.jsp");
			return forward; 
	}//execute end
}
