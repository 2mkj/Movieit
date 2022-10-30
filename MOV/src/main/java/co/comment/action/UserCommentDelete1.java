package co.comment.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.comment.db.Comment;
import co.comment.db.CommentDAO;
import co.community.Action;
import co.community.ActionForward;

public class UserCommentDelete1 implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		CommentDAO dao = new CommentDAO();
		Comment c = new Comment();
		c.setNum(Integer.parseInt(request.getParameter("num")));
		int result = dao.userCommentsDel(c);
		
		if(result==0) {
			System.out.println("댓글 삭제 실패");
			forward.setPath("error/error.jsp");
			request.setAttribute("message", "댓글 삭제 실패입니다.");
			forward.setRedirect(false);
			return forward;
		}
		System.out.println("댓글 삭제 완료");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println("<script>");
		out.println("alert('댓글 삭제가 완료되었습니다.');");
		out.println("location.href=document.referrer;"); // 뒤로 가서 새로고침
		out.println("</script>");
		out.close();
		return null;
	}

}
