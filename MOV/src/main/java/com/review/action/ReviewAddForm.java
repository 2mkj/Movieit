package com.review.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.move.action.Action;
import com.move.action.ActionForward;

public class ReviewAddForm implements Action {

	private ReviewDAO dao = null;

	public ReviewAddForm() {
		dao = new ReviewDAO();
	}

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		ActionForward forward = new ActionForward();
		request.setAttribute("review_id", request.getParameter("review_id"));
		request.setAttribute("review_move_name", request.getParameter("review_move_name"));
		System.out.println("<ReviewAddForm> 폼 jsp 에서 넘어온 ID " + request.getParameter("review_id"));
		System.out.println("<ReviewAddForm> 폼 jsp 에서 넘어온 move Name" + request.getParameter("review_move_name"));
		
		forward.setPath("/review_page/reviewForm.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
