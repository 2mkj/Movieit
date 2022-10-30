package me.member.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MemberJoinCompleteAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String user_name = request.getParameter("user_name");
		request.setAttribute("user_name", user_name);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("member/join_complete.jsp");
		return forward;
	}
}
