package me.member.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.member.db.MemberDAO;

public class MemberEmailCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		int result = dao.isId(request.getParameter("email"));
		response.getWriter().append(Integer.toString(result));
		System.out.println(result);
		return null;
	}
}
