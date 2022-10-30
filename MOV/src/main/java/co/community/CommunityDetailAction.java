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

public class CommunityDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		MemberDAO2 mdao = new MemberDAO2();
		Memberall m = mdao.member_info(email);
		CommunityDAO cdao = new CommunityDAO();
		Community c = new Community();
		
		int num=Integer.parseInt(request.getParameter("num"));
		cdao.setReadCountUpdate(num);
		c = cdao.getDetail(num);
		if(c==null){
			System.out.println("상세보기 실패");
			ActionForward forward = new ActionForward();
			forward.setRedirect(false);
			request.setAttribute("message", "데이터를 읽지 못했습니다.");
			forward.setPath("error/error.jsp");
			return forward;
			}
			System.out.println("상세보기 성공");
		request.setAttribute("c", c);
		request.setAttribute("memberinfo", m);
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("community/boardView.jsp");
		return forward;
	}

}
