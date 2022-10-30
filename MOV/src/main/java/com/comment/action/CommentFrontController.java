package com.comment.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.move.action.Action;
import com.move.action.ActionForward;
import com.review.action.ReviewAddAction;
import com.review.action.ReviewAddForm;
import com.review.action.ReviewDeleteAction;
import com.review.action.ReviewDetailAction;
import com.review.action.ReviewListAction;
import com.review.action.ReviewModifiyAction;

@WebServlet("*.re")
public class CommentFrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command = getCommand(request);
		Action action = getAction(command);
		ActionForward forward = action.execute(request, response);

		if (forward != null) {
			System.out.println("<CommentFrontController>가 연결될 주소 " + forward.getPath());
			System.out.println("");
			if (forward.isRedirect()) {
				System.out.println("");
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	private String getCommand(HttpServletRequest request) throws ServletException, IOException {
		String command = "";
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();

		System.out.println("RequestURI -> " + requestURI);
		System.out.println("ContextPath -> " + contextPath);
		System.out.println("");
		command = requestURI.substring(contextPath.length());
		System.out.println(command);
		return command;
	}

	private Action getAction(String command) {
		switch (command) {
		case "/CommentListAction.re":
			return new CommentListAction();
		case "/CommentAddAction.re":
			return new CommentAddAction();
		case "/CommentDeleteAction.re":
			return new CommentDeleteAction();
		case "/CommentModifiyAction.re":
			return new CommentModifiyAction();
		default:
			System.out.println("<CommentFrontController> getCommand에서 연결할 주소가 없습니다. " + command);
			return null;
		}
	}

}
