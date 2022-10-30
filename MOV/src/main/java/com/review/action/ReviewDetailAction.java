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

public class ReviewDetailAction implements Action {

	public ReviewDetailAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		HttpSession session = request.getSession();
		Movie movie = (Movie) session.getAttribute("movie");
		Memberall member = (Memberall) session.getAttribute("memberinfo");

		ReviewDAO dao = new ReviewDAO();
		Review review = dao.getReview(Integer.parseInt(request.getParameter("review_number")));
		
		dao.setReadCount(Integer.parseInt(request.getParameter("review_number")));
		System.out.println(request.getParameter("review_number"));
		session.setAttribute("review", review);
		session.setAttribute("review_number", Integer.parseInt(request.getParameter("review_number")));

		ActionForward forward = new ActionForward();
		forward.setPath("/review_page/reviewDetail.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
