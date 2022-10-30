package com.review.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.move.action.Action;
import com.move.action.ActionForward;

import io.movie.db.Movie;

public class ReviewListAction implements Action {
	private ReviewDAO dao = null;

	private int nowPage;
	private int startList;
	private int endList;
	private int limitList;
	private int startPage;
	private int endPage;
	private int reviewCount;

	public ReviewListAction() {
		dao = new ReviewDAO();
	}

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		// 영화 제목 가져오기
		HttpSession session = request.getSession();
		Movie movie = (Movie) session.getAttribute("movie");
		String title = movie.getTitle();

		// Ajax인지 확인
		ServletData data = new ServletData();
		String state = data.getAjaxState(request);

		// 페이징 처리
		setPaging(request, title);

		// 페이징처리에 대한 리뷰 리스트 가져오기
		ArrayList<Review> reviewList = dao.getReviewList(title, startList, endList);

		if (state == null) {
			System.out.println("state = null");
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("startList", startList);
			request.setAttribute("endList", endList);
			request.setAttribute("limitList", limitList);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("reviewCount", reviewCount);
			request.setAttribute("reviewList", reviewList);

			ActionForward forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/review_page/review.jsp");
			return forward;
		} else {
			System.out.println("state = ajax");
			JsonObject object = new JsonObject();
			object.addProperty("nowPage", nowPage);
			object.addProperty("startList", startList);
			object.addProperty("endList", endList);
			object.addProperty("limitList", limitList);
			object.addProperty("startPage", startPage);
			object.addProperty("endPage", endPage);
			object.addProperty("reviewCount", reviewCount);

			JsonElement je = new Gson().toJsonTree(reviewList);
			System.out.println("reviewlist = " + je.toString());
			object.add("reviewlist", je);

			response.setContentType("text/html; charset=utf-8");
			response.getWriter().print(object);
			System.out.println(object.toString());
			return null;
		}
	}

	private void setPaging(HttpServletRequest request, String title) {
		// 30
		// 1 1~10 2 11~20 3 21~30
		// startList = (listLimit * (nowPage -1))+1
		// endList = (startList + 10) - 1
		// startPage = 1
		// endPage = ((reviewCount)/listLimit) + 1;

		nowPage = 1;
		startPage = 1;
		limitList = 5;

		nowPage = Integer.parseInt(request.getParameter("nowPage"));

		if (request.getParameter("limitList") != null) {
			limitList = Integer.parseInt(request.getParameter("limitList"));
		}

		reviewCount = dao.getReviewCount(title);

		startList = (limitList * (nowPage - 1)) + 1;
		endList = (startList + limitList) - 1;
		endPage = ((reviewCount - 1) / limitList) + 1;

		// if (endpage > maxpage)
		// endpage = maxpage;

		System.out.println("<nowPage>  " + nowPage);
		System.out.println("<startList>  =" + startList);
		System.out.println("<endList> = " + endList);
		System.out.println("<limitList> = " + limitList);
		System.out.println("<startPage> " + startPage);
		System.out.println("<endPage> " + endPage);
		System.out.println("<reviewCount>" + reviewCount);
	}
}
