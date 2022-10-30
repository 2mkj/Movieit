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

public class ReviewDeleteAction implements Action {

	public ReviewDeleteAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Movie movie = (Movie) session.getAttribute("movie");
		Memberall member = (Memberall) session.getAttribute("memberinfo");
		int review_number = Integer.parseInt(request.getParameter("review_number"));
		System.out.println("<ReviewDeleteAction>에 들어온 리뷰 번호 : " + review_number);
		int result = -1;

		ReviewDAO dao = new ReviewDAO();

		if (member.getUser_name().equals("관리자")) {

			result = dao.deleteReview(review_number);
		} else {
			result = dao.deleteReview(member.getUser_name(), movie.getTitle());
		}
		if (result != 0) {
			System.out.println("<ReviewDeleteAction> 리뷰 삭제가 완료되었습니다.");
		} else {
			System.out.println("<ReviewDeleteAction> 리뷰 삭제에 실패했습니다.");
		}
		ActionForward forward = new ActionForward();

		forward.setPath("MovieDetail.io");
		return forward;
	}

}
