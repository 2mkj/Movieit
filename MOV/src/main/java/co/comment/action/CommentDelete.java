package co.comment.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.community.ActionForward;

public class CommentDelete implements co.community.Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("num"));
		
		co.comment.db.CommentDAO dao = new co.comment.db.CommentDAO();
		
		int result = dao.commentsDelete(num);
		PrintWriter out = response.getWriter();
		out.print(result);
		
		return null;
	}

}
