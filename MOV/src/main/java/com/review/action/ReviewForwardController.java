package com.review.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.move.action.Action;
import com.move.action.ActionForward;

@WebServlet("*.com")
public class ReviewForwardController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String command = getCommand(request);
		Action action = getAction(command);
		ActionForward forward = action.execute(request, response);

		if (forward != null) {
			System.out.println("<ReviewForwardController> 이동할 경로 " + forward.getPath());
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
		command = requestURI.substring(contextPath.length());
		System.out.println(command);
		return command;
	}

	private Action getAction(String command) {
		switch (command) {
		case "/ReviewListAction.com":
			return new ReviewListAction();
		case "/ReviewAddForm.com":
			return new ReviewAddForm();
		case "/ReviewAddAction.com":
			return new ReviewAddAction();
		case "/ReviewModifiyForm.com":
			return new ReviewModifiyForm();
		case "/ReviewModifiyAction.com":
			return new ReviewModifiyAction();
		case "/ReviewDeleteAction.com":
			return new ReviewDeleteAction();
		case "/ReviewDetailAction.com":
			return new ReviewDetailAction();
		default:
			System.out.println("<ReviewForwardController> getAction forward에서 에러. " + command);
			return null;
		}
	}
}
