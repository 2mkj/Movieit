package com.review.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.move.action.Action;
import com.move.action.ActionForward;

public class ReviewModifiyForm implements Action {

	public ReviewModifiyForm() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		int review_number =Integer.parseInt(request.getParameter("review_number"));
		
		ReviewDAO dao = new ReviewDAO();
		Review review = dao.getReview(review_number);
		
		String review_id = review.getReview_id();
		String review_move_name = review.getReview_move_name();
		String review_content = review.getReview_content();
		String review_date = review.getReview_date();
		String review_star = review.getReview_star();
		int review_readcount = review.getReview_readcount();
		String review_title = review.getReview_title();
		
		request.setAttribute("review_id", review_id);
		request.setAttribute("review_move_name", review_move_name);
		request.setAttribute("review_content", review_content);
		request.setAttribute("review_date", review_date);
		request.setAttribute("review_star", review_star);
		request.setAttribute("review_readcount", review_readcount);
		request.setAttribute("review_title", review_title);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/review_page/reviewModifiyForm.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
