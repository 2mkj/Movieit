package com.review.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.move.action.Action;
import com.move.action.ActionForward;

import io.movie.db.Movie;
import net.member.Memberall;

public class ReviewAddAction implements Action {

	ReviewDAO dao = null;

	public ReviewAddAction() {
		dao = new ReviewDAO();
	}

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		HttpSession session = request.getSession();
		Movie movie = (Movie) session.getAttribute("movie");
		Memberall member = (Memberall) session.getAttribute("memberinfo");
		
		System.out.println("");
		System.out.println("<ReviewAddAction> " + member.getUser_name());
		System.out.println("<ReviewAddAction> " + movie.getTitle());
		
		boolean check = dao.isExistReview(member.getUser_name(), movie.getTitle());
		PrintWriter out = response.getWriter();

		if (check == true) {
			System.out.println("이미 등록된 아이디로 리뷰 등록을 시도했습니다.");
			System.out.println("");

			out.println("<script>");
			out.println("alert('이미 등록된 아이디입니다.');");
			out.println("location.href='MovieDetail.io';");
			out.println("</script>");
			return null;
		} else {

			System.out.println("리뷰 등록에 성공했습니다.");
			System.out.println("");

			System.out.println("들어온 reivew 데이터 -> " + member.getUser_name());
			System.out.println("들어온 reivew 데이터 -> " + request.getParameter("review_title"));
			System.out.println("들어온 reivew 데이터 -> " + request.getParameter("review_content"));
			System.out.println("들어온 reivew 데이터 -> " + request.getParameter("review_star"));
			System.out.println("들어온 reivew 데이터 -> " + movie.getTitle());
			System.out.println("");

			Review review = new Review();
			review.setReview_id(member.getUser_name());
			review.setReview_title(request.getParameter("review_title"));
			review.setReview_content(request.getParameter("review_content"));
			// review.setReview_date(request.getParameter("review_date"));
			review.setReview_star(request.getParameter("review_star"));
			// review.setReview_readcount(request.getParameter("review_readcount"));
			review.setReview_move_name(movie.getTitle());
			dao.setReviewItem(review);

			out.println("<script>");
			out.println("alert('등록에 성공했습니다.');");
			out.println("</script>");
		}

		ActionForward forward = new ActionForward();
		forward.setPath("MovieDetail.io");
		forward.setRedirect(false);
		return forward;
	}
}
