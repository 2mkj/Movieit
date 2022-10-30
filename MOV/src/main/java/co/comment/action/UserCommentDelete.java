package co.comment.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.comment.db.CommentDAO;
import co.community.Action;
import co.community.ActionForward;

public class UserCommentDelete implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CommentDAO dao = new CommentDAO();
		co.comment.db.Comment co = new co.comment.db.Comment();
		co.setContent(request.getParameter("content"));
		System.out.println("content=" + co.getContent());
		co.setNum(Integer.parseInt(request.getParameter("num")));
		System.out.println("num=" + co.getNum());
		int ok = dao.userCommentsDel(co);
		response.getWriter().print(ok);

		return null;
	}

}
