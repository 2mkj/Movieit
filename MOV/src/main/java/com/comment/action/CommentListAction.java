package com.comment.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.move.action.Action;
import com.move.action.ActionForward;
import com.review.action.Review;
import com.review.action.ReviewDAO;

import io.movie.db.Movie;
import net.member.Memberall;

public class CommentListAction implements Action {

	private int nowPage;
	private int startList;
	private int endList;
	private int limitList;
	private int startPage;
	private int endPage;
	private int commentCount;

	public CommentListAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Movie movie = (Movie) session.getAttribute("movie");
		Memberall member = (Memberall) session.getAttribute("memberinfo");

		System.out.println("<CommentListAction> 현재 영화 이름은 " + movie.getTitle());
		
		int review_number = (int) session.getAttribute("review_number");
		// ArrayList<Comment> comment_list = dao.getComment(comment_review_name);
		ArrayList<Comment> comment_list = getPaging(request, review_number, movie.getTitle());
		
		if (comment_list != null)
			System.out.println("<CommentListAction> 페이징에 따라 가져온 댓글 리스트의 개수는 -> " + comment_list.size());
		else
			System.out.println("등록된 댓글이 존재하지 않습니다.");

		String json_state = request.getParameter("json_state");

		if (json_state != null) {
			System.out.println("<CommentListAction> ajax 실행");
			JsonObject json = new JsonObject();
			json.addProperty("nowPage", nowPage);
			json.addProperty("startList", startList);
			json.addProperty("endList", endList);
			json.addProperty("limitList", limitList);
			json.addProperty("startPage", startPage);
			json.addProperty("endPage", endPage);
			json.addProperty("commentCount", commentCount);
			JsonElement je = new Gson().toJsonTree(comment_list);

			System.out.println(je.toString());
			System.out.println("");

			json.add("comment_list", je);

			response.setContentType("text/html; charset=utf-8");
			response.getWriter().append(json.toString());

			return null;
		} else {
			System.out.println("<CommentListAction> 일반 실행");
			ActionForward forward = new ActionForward();
			forward.setPath("/review_page/reviewDetail.jsp");
			forward.setRedirect(false);
			return forward;
		}

	}

	private ArrayList<Comment> getPaging(HttpServletRequest request, int review_number, String comment_review_name) {

		setPaging(request, review_number, comment_review_name);
		CommentDAO dao = new CommentDAO();
		ArrayList<Comment> commentList = dao.getCommentList(review_number, comment_review_name, startList, endList);

		request.setAttribute(comment_review_name, commentList);

		request.setAttribute("nowPage", nowPage);
		request.setAttribute("startList", startList);
		request.setAttribute("endList", endList);
		request.setAttribute("limitList", limitList);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("commentCount", commentCount);
		request.setAttribute("commentList", commentList);

		return commentList;
	}

	private void setPaging(HttpServletRequest request, int review_number, String comment_review_name) {
		// 30
		// 1 1~10 2 11~20 3 21~30
		// startList = (listLimit * (nowPage -1))+1
		// endList = (startList + 10) - 1
		// startPage = 1
		// endPage = ((reviewCount)/listLimit) + 1;

		CommentDAO dao = new CommentDAO();

		// 현재 영화에 대한 댓글의 개수글 가져옵니다.
		commentCount = dao.getCommentCount(review_number, comment_review_name);

		nowPage = 1;
		startPage = 1;
		limitList = 5;

		if (request.getParameter("nowPage") != null) {
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}

		if (request.getParameter("limitList") != null) {
			limitList = Integer.parseInt(request.getParameter("limitList"));
		}

		startList = (limitList * (nowPage - 1)) + 1;
		endList = (startList + limitList) - 1;
		endPage = ((commentCount - 1) / limitList) + 1;

		// if (endpage > maxpage)
		// endpage = maxpage;

		System.out.println("<nowPage>  " + nowPage);
		System.out.println("<startList>  =" + startList);
		System.out.println("<endList> = " + endList);
		System.out.println("<limitList> = " + limitList);
		System.out.println("<startPage> " + startPage);
		System.out.println("<endPage> " + endPage);
		System.out.println("<reviewCount>" + commentCount);
	}

}
