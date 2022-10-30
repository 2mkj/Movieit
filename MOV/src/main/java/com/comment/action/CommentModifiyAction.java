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

public class CommentModifiyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		CommentDAO dao = new CommentDAO();
		ServletData data = new ServletData();

		int comment_number = data.getCommentNumber(request);
		String password = data.getCommentPassword(request);
		String comment_content = request.getParameter("comment_modifiy_content");
		int result = dao.modifiyComment(comment_number, password, comment_content);
		Comment comment = null;
		
		if(result > 0) {
			comment = dao.getComment(comment_number);
		}
		
		PrintWriter out = response.getWriter();
		if (result == -2) {
			System.out.println("state = ajax");
			JsonObject object = new JsonObject();
			object.addProperty("message_ajax", "비밀번호가 일치하지 않습니다.");
			object.addProperty("result_modifiy", result);
			response.getWriter().print(object);
			System.out.println(object.toString());
			return null;
		} else if (result == -1) {
			JsonObject object = new JsonObject();
			object.addProperty("message_ajax", "댓글 수정에 실패했습니다.");
			object.addProperty("result_modifiy", result);
			response.getWriter().print(object);
			System.out.println(object.toString());
			return null;
		} else {
			JsonObject object = new JsonObject();
			JsonElement je = new Gson().toJsonTree(comment);

			object.addProperty("message_ajax", "댓글이 수정되었습니다.");
			object.addProperty("result_modifiy", result);
			object.add("get_modifiy_comment", je);
			
			response.getWriter().print(object);
			
			System.out.println(object.toString());
			return null;
		}
	}

}
