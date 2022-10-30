package mg.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.member.MemberDAO2;
import net.member.Memberall;


public class ManagerInfoAction implements ManagerAction {

	@Override
	public ManagerForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ManagerForward forward = new ManagerForward();
		String email = request.getParameter("email");
		MemberDAO2 mdao = new MemberDAO2();
		Memberall m = mdao.member_info(email);
		System.out.println("m = "+m);
		if(m==null) {
			forward.setPath("error/error.jsp");
			forward.setRedirect(false);
			request.setAttribute("message","아이디에 해당하는 정보가 없습니다.");
			return forward;
		}
		forward.setPath("manager/managerInfo.jsp");
		forward.setRedirect(false);
		request.setAttribute("managerinfo", m);
		return forward;
	}

}
