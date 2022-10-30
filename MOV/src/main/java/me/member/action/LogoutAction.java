package me.member.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 세션 해제
		session.invalidate();
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("main.net");

		return forward;
	}

}
