package com.comment.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.move.action.Action;
import com.move.action.ActionForward;
import com.review.action.ServletData;

public class CommentDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		CommentDAO dao = new CommentDAO();
		ServletData data = new ServletData();

		// request로 넘어온 댓글의 번호를 가져옵니다.
		int comment_number = data.getCommentNumber(request);

		// 사용자가 입력한 비밀번호를 가져옵니다.
		String password = data.getCommentPassword(request);

		int result = dao.deleteComment(comment_number, password);

		if (result == -2) {
			System.out.println("state = ajax");
			JsonObject object = new JsonObject();
			object.addProperty("message_ajax", "비밀번호가 일치하지 않습니다.");
			object.addProperty("result_delete", result);
			response.getWriter().print(object);
			System.out.println(object.toString());
			return null;
		} else if (result == -1) {
			JsonObject object = new JsonObject();
			object.addProperty("message_ajax", "댓글 삭제에 했습니다.");
			object.addProperty("result_delete", result);
			response.getWriter().print(object);
			System.out.println(object.toString());
			return null;
		} else {
			JsonObject object = new JsonObject();
			object.addProperty("message_ajax", "댓글이 삭제되었습니다.");
			object.addProperty("result_delete", result);
			response.getWriter().print(object);
			System.out.println(object.toString());
			return null;
		}
	}

}
