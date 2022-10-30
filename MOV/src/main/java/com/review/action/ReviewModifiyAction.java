package com.review.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.move.action.Action;
import com.move.action.ActionForward;

import io.movie.db.Movie;
import net.member.Memberall;
import net.zzim.db.Zzim;

public class ReviewModifiyAction implements Action {

	public ReviewModifiyAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		//현재 영화 정보
		Movie movie = (Movie) session.getAttribute("movie");
		//현재 로그인 정보
		Memberall member = (Memberall) session.getAttribute("memberinfo");
		
		Review review = new Review();
		review.setReview_id(member.getUser_name());
		review.setReview_title(request.getParameter("review_title"));
		review.setReview_content(request.getParameter("review_content"));
		review.setReview_star(request.getParameter("review_star"));
		review.setReview_move_name(movie.getTitle());

		ReviewDAO dao = new ReviewDAO();
		int result = dao.modifiyReview(review);

		ActionForward forward = new ActionForward();

		forward.setPath("MovieDetail.io");
		forward.setRedirect(false);
		return forward;
	}

}
