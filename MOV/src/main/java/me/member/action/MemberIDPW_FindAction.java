package me.member.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberIDPW_FindAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("member/idpw_find.jsp");
		return forward;
	}
}
