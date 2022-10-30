package co.community;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.commuboard.Community;
import co.commuboard.CommunityDAO;
import net.member.MemberDAO2;
import net.member.Memberall;

public class CommunityAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		MemberDAO2 mdao = new MemberDAO2();
		Memberall m = mdao.member_info(email);
		request.setAttribute("memberinfo", m);
		
		CommunityDAO cdao = new CommunityDAO();
		Community c = new Community();
		ActionForward forward = new ActionForward();
		boolean result = false;
		
		String name = request.getParameter("name");
		String user_name = request.getParameter("user_name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		c.setBoard_name(name);
		c.setBoard_user_name(user_name);
		c.setBoard_subject(subject);
		c.setBoard_content(content);
		
		result = cdao.boardInsert(c);
		
		if(result==false) {
			System.out.println("게시판 등록 실패");
			forward.setPath("error/error.jsp");
			request.setAttribute("message", "게시판 등록 실패입니다.");
			forward.setRedirect(false);
			return forward;
		}
		System.out.println("게시판 등록 완료");
		
		forward.setRedirect(true);
		forward.setPath("Community.co");//이동할 경로를 지정합니다.
		return forward;
		
	}
}
