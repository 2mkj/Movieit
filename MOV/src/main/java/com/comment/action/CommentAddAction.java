package com.comment.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.AudioFormat.Encoding;

import com.move.action.Action;
import com.move.action.ActionForward;

import io.movie.db.Movie;
import net.member.Memberall;

public class CommentAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		CommentDAO dao = new CommentDAO();
		ActionForward action = new ActionForward();

		HttpSession session = request.getSession();
		Movie movie = (Movie) session.getAttribute("movie");
		Memberall member = (Memberall) session.getAttribute("memberinfo");

		String comment_content = request.getParameter("comment_content");
		String comment_password = request.getParameter("comment_password");

		System.out.println("<CommentAddAction>에 들어온 password => " + comment_password);
		System.out.println("");

		Comment comment = new Comment();
		comment.setComment_id(member.getUser_name());
		comment.setComment_content(comment_content);
		comment.setComment_password(comment_password);
		comment.setComment_review_name(movie.getTitle());
		System.out.println("<CommentAddAction>들어온 리뷰 번호 : " + (int) session.getAttribute("review_number"));
		dao.setComment(comment, (int) session.getAttribute("review_number"));

		request.setAttribute("json_state", null);

		System.out.println("<CommentAddAction> 들어온 id = " + member.getUser_name());
		System.out.println("<CommentAddAction> 들어온 영화이름 = " + movie.getTitle());
		System.out.println("");

		action.setPath("CommentListAction.re");
		action.setRedirect(true);
		return action;
	}

}
