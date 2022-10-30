package me.member.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.member.db.MemberDAO;

public class MemberID_FindCheckAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		MemberDAO mdao = new MemberDAO();
		String name = request.getParameter("name1");
		String phone = request.getParameter("phone1");
		int result = mdao.isID(name, phone);
		System.out.println("결과는 " + result);
		
		
		if (result == 1) {
			String email = mdao.isEmail(name, phone);
			request.setAttribute("email1", email);
			forward.setRedirect(false);
			forward.setPath("member/id_find_ok.jsp");
			return forward;
		} else if(result == 0) {
			forward.setPath("member/idpw_check_error.jsp");
			forward.setRedirect(false);
			return forward;	
		}
		return null;
	}
		
}
