package no.notice.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.member.MemberDAO2;
import net.member.Memberall;
import no.noticeboard.db.Notice;
import no.noticeboard.db.NoticeDAO;

public class CommunityAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		MemberDAO2 mdao = new MemberDAO2();
		Memberall m = mdao.member_info(email);
		request.setAttribute("memberinfo", m);
		
		NoticeDAO cdao = new NoticeDAO();
		Notice c = new Notice();
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
		forward.setPath("noticeCommunity.no");//이동할 경로를 지정합니다.
		return forward;
		
	}
}
