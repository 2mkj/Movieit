package co.community;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.MemberDAO2;
import net.member.Memberall;

public class CommunityWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		MemberDAO2 mdao = new MemberDAO2();
		Memberall m = mdao.member_info(email);
		request.setAttribute("memberinfo", m);
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("community/boardWrite.jsp");
		return forward;
	}

}
