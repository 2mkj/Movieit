package me.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.member.db.Member;
import me.member.db.MemberDAO;

public class MemberJoinProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String user_name = request.getParameter("user_name");
		String name = request.getParameter("name");
		String jumin1 = request.getParameter("jumin1");
		String jumin2 = request.getParameter("jumin2");
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		String post = request.getParameter("post1");
		String address = request.getParameter("address");
		String memberfile = request.getParameter("memberfile");
		
		Member m = new Member ();
		m.setEmail(email);
		m.setPass(pass);
		m.setUser_name(user_name);
		m.setName(name);
		m.setJumin1(jumin1);
		m.setJumin2(jumin2);
		m.setGender(gender);
		m.setPhone(phone);
		m.setPost(post);
		m.setAddress(address);
		m.setMemberfile(memberfile);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		MemberDAO mdao = new MemberDAO();
		
		int result = mdao.insert(m);
		
		if(result==0) {
			System.out.println("회원 가입 실패입니다.");
			ActionForward forward = new ActionForward();
			forward.setRedirect(false);
			request.setAttribute("message", "회원 가입 실패입니다.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		
		out.println("<script>");
		if (result == 1) {//삽입이 된 경우
			ActionForward forward = new ActionForward();
			forward.setRedirect(false);
			request.setAttribute("user_name", user_name);
			forward.setPath("joinComplete.me");
			return forward;
		} else if (result == -1) {
			out.println("alert('아이디가 중복되었습니다. 다시 입력하세요');");
			out.println("history.back()");//비밀번호를 제외한 다른 데이터는 유지되어 있습니다.
		}
		out.println("</script>");
		out.close();
		return null;
	}
	

}
