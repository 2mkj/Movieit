package co.community;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.commuboard.Community;
import co.commuboard.CommunityDAO;
import net.member.MemberDAO2;
import net.member.Memberall;

public class CommunityModifyAction implements Action {

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
		
		int num = Integer.parseInt(request.getParameter("num"));
		System.out.println(num);
		String name = request.getParameter("name");
		String user_name = request.getParameter("user_name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		c.setBoard_num(num);
		c.setBoard_name(name);
		c.setBoard_user_name(user_name);
		c.setBoard_subject(subject);
		c.setBoard_content(content);
		boolean result = false;
		result = cdao.boardModify(c);
		
		if(result==false) {
			System.out.println("게시판 수정 실패");
			forward.setPath("error/error.jsp");
			request.setAttribute("message", "게시판 수정 실패입니다.");
			forward.setRedirect(false);
			return forward;
		}
		System.out.println("게시판 수정 완료");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println("<script>");
		out.println("alert('게시글이 수정 되었습니다.');");
		out.println("location.href='CommunityDetail.co?num="+ num +"';");
		out.println("</script>");
		out.close();
		return null;
	}

}
