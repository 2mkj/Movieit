package com.review.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletData {

	private final String review_id = "review_id";
	private final String review_move_name = "review_move_name";
	private final String ajax_state = "ajax_state";
	private final String comment_number = "comment_number";
	private final String comment_password = "comment_password";

	// 현재 로그인된 아이디를 가져옵니다.(request)
	public String getLoginIdRequest(HttpServletRequest request) {
		return request.getParameter(review_id);
	}

	// 현재 로그인된 아이디를 설정합니다.(request)
	public void setLoginIdRequest(HttpServletRequest request, String value) {
		request.setAttribute(review_id, value);
	}

	// 현재 선택된 영화 이름을 가져옵니다.(request)
	public String getMoveNameRequest(HttpServletRequest request) {
		return request.getParameter(review_move_name);
	}

	// 현재 선택된 영화를 설정합니다.(session)
	public void setMoveNameRequest(HttpServletRequest request, String value) {
		request.setAttribute(review_move_name, value);
	}

	// 현재 로그인된 아이디를 가져옵니다.(session)
	public String getLoginIdSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(review_id);
	}

	// 현재 로그인된 아이디를 설정합니다.(session)
	public void setLoginIdSession(HttpServletRequest request, String value) {
		HttpSession session = request.getSession();
		session.setAttribute(review_id, value);
	}

	// 현재 선택된 영화 이름을 가져옵니다.(session)
	public String getMoveNameSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(review_move_name);
	}

	// 현재 선택된 영화 이름을 설정합니다.(session)
	public void setMoveNameSession(HttpServletRequest request, String value) {
		HttpSession session = request.getSession();
		session.setAttribute(review_move_name, value);
	}

	// 넘어온 댓글의 번호를 가져옵니다.(request)
	public int getCommentNumber(HttpServletRequest request) {
		return Integer.parseInt(request.getParameter(comment_number));
	}

	// 댓글의 번호를 설정합니다.(request)
	public void setCommentNumber(HttpServletRequest request, int value) {
		request.setAttribute(comment_number, value);
	}

	// 넘어온 댓글의 비밀번호를 가져옵니다.(request)
	public String getCommentPassword(HttpServletRequest request) {
		return request.getParameter(comment_password);
	}

	// 넘어온 댓글의 비밀번호를 설정합니다.(request)
	public void setCommentPassword(HttpServletRequest request, String value) {
		request.setAttribute(comment_password,value);
	}

	public String getAjaxState(HttpServletRequest request) {
		return request.getParameter(ajax_state);
	}

	public String getAjaxStateSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(ajax_state);
	}
}
