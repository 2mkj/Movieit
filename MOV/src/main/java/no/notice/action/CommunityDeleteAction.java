package no.notice.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import no.noticeboard.db.NoticeDAO;



public class CommunityDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean result = false;
		
		int num=Integer.parseInt(request.getParameter("num"));
		
		NoticeDAO cdao=new NoticeDAO();
		//비밀번호 일치하는 경우 삭제 처리 합니다.

		result=cdao.boardDelete(num);
		
		// 삭제 처리 실패한 경우
		if(result== false) {
			System.out.println("");
			ActionForward forward = new ActionForward();
			forward.setRedirect(false);
			request.setAttribute("message", "데이터를 삭제하지 못했습니다.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		
		// 삭제 처리 성공한 경우 글 목록 보기 요청을 전송하는 부분입니다.
		System.out.println("게시판 삭제 성공");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println("<script>");
		out.println("alert('삭제 되었습니다.');");
		out.println("location.href='noticeCommunity.no';");
		out.println("</script>");
		out.close();
		return null;
	}

}
