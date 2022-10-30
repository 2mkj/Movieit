package net.mypage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zzim.db.ZzimDAO;

public class myZzimDelete implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean result = false;
		String email = request.getParameter("email");
		String title = request.getParameter("title");
		String mdate = request.getParameter("mdate");
		System.out.println("찜삭제 들어온 값= "+email+", "+title+", "+mdate);	
		ZzimDAO dao = new ZzimDAO(); 
		result=dao.zzimDelete(email,title,mdate);
		
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
		System.out.println("찜 제거 완료");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println("<script>");
		out.println("alert('찜을 취소하였습니다.');");
		out.println("location.href=document.referrer;"); // 뒤로 가서 새로고침
		out.println("</script>");
		out.close();
		return null;
	}

}
