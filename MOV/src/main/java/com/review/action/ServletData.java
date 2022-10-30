package com.review.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletData {

	private final String review_id = "review_id";
	private final String review_move_name = "review_move_name";
	private final String ajax_state = "ajax_state";
	private final String comment_number = "comment_number";
	private final String comment_password = "comment_password";

	// ���� �α��ε� ���̵� �����ɴϴ�.(request)
	public String getLoginIdRequest(HttpServletRequest request) {
		return request.getParameter(review_id);
	}

	// ���� �α��ε� ���̵� �����մϴ�.(request)
	public void setLoginIdRequest(HttpServletRequest request, String value) {
		request.setAttribute(review_id, value);
	}

	// ���� ���õ� ��ȭ �̸��� �����ɴϴ�.(request)
	public String getMoveNameRequest(HttpServletRequest request) {
		return request.getParameter(review_move_name);
	}

	// ���� ���õ� ��ȭ�� �����մϴ�.(session)
	public void setMoveNameRequest(HttpServletRequest request, String value) {
		request.setAttribute(review_move_name, value);
	}

	// ���� �α��ε� ���̵� �����ɴϴ�.(session)
	public String getLoginIdSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(review_id);
	}

	// ���� �α��ε� ���̵� �����մϴ�.(session)
	public void setLoginIdSession(HttpServletRequest request, String value) {
		HttpSession session = request.getSession();
		session.setAttribute(review_id, value);
	}

	// ���� ���õ� ��ȭ �̸��� �����ɴϴ�.(session)
	public String getMoveNameSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(review_move_name);
	}

	// ���� ���õ� ��ȭ �̸��� �����մϴ�.(session)
	public void setMoveNameSession(HttpServletRequest request, String value) {
		HttpSession session = request.getSession();
		session.setAttribute(review_move_name, value);
	}

	// �Ѿ�� ����� ��ȣ�� �����ɴϴ�.(request)
	public int getCommentNumber(HttpServletRequest request) {
		return Integer.parseInt(request.getParameter(comment_number));
	}

	// ����� ��ȣ�� �����մϴ�.(request)
	public void setCommentNumber(HttpServletRequest request, int value) {
		request.setAttribute(comment_number, value);
	}

	// �Ѿ�� ����� ��й�ȣ�� �����ɴϴ�.(request)
	public String getCommentPassword(HttpServletRequest request) {
		return request.getParameter(comment_password);
	}

	// �Ѿ�� ����� ��й�ȣ�� �����մϴ�.(request)
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
