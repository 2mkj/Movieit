package net.mypage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import co.commuboard.Community;
import co.commuboard.CommunityDAO;
import net.member.MemberDAO2;
import net.member.Memberall;

public class myWritingAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		System.out.println(email);
		MemberDAO2 mdao = new MemberDAO2();
		CommunityDAO boarddao = new CommunityDAO();
		List<Community> boardlist = new ArrayList<Community>();
		
		int page = 1; //보여줄 page
		int limit = 10; //한 페이지에 보여줄 게시판 목록의 수
		
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}
		Memberall m = mdao.member_info(email);
		int listcount = 0;
		String search_word="";
		
		//(member_list.net?page=2&search_field=-1&search_word=)
		if (request.getParameter("search_word") == null
				|| request.getParameter("search_word").equals("")) {
			// 총 리스트 수를 받아옵니다.
			listcount = boarddao.getmyListCount(email);
			boardlist = boarddao.getmyWriting(email, page, limit);
		} else { // 검색을 클릭한 경우
			search_word = request.getParameter("search_word");
			listcount = boarddao.getmyListCount(email, search_word);
			boardlist = boarddao.getmyWriting(email, search_word , page, limit);
		}
		
		
		int maxpage = (listcount + limit - 1) / limit;
		System.out.println("총 페이지수 =" + maxpage);
		
		int startpage = ((page - 1) / 10) * 10 + 1;
		int endpage = startpage + 10 - 1;
		System.out.println("현재 페이지에 보여줄 마지막 페이지 수 :" + endpage);
		System.out.println("현재 페이지에 보여줄 시작 페이지 수 :" + startpage);
		if (endpage > maxpage) endpage = maxpage;
		
		String state = request.getParameter("state");
		
		if(state == null) {
			System.out.println("state==null");
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
			
			forward.setPath("mypage/myWriting.jsp");
			return forward; 
			
		} else {
			System.out.println("state=ajax");
			
			JsonObject object = new JsonObject();
			object.addProperty("page", page); //{"page": 변수 page의 값} 형식으로 저장 
			object.addProperty("maxpage", maxpage);
			object.addProperty("startpage", startpage);
			object.addProperty("endpage", endpage);
			object.addProperty("listcount", listcount);
			object.addProperty("limit", limit);
			JsonElement je = new Gson().toJsonTree(boardlist);
			System.out.println("boardlist="+je.toString());
			object.add("boardlist", je);
			
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().append(object.toString());
			System.out.println(object.toString());
			return null;
		}//if end
		
	}//execute end
}
