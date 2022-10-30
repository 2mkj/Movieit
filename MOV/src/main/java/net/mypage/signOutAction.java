package net.mypage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.MemberDAO2;

public class signOutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDAO2 mdao = new MemberDAO2();
		String email = request.getParameter("email");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = mdao.delete(email);
		if (result == 1) {
		out.println("<script>");
		out.println("alert('회원탈퇴가 완료되었습니다.');");
		out.println("location.href='main.net'");
		out.println("</script>");
		HttpSession session = request.getSession();
		// 세션 해제
		session.invalidate();
		} else {
			out.println("<script>");
			out.println("alert('탈퇴할 수 없습니다.');");
			out.println("history.back()");
			out.println("</script>");
		}
		out.close();
		return null;
	}

}
