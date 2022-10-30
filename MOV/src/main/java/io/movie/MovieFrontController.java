package io.movie;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.io")
public class MovieFrontController extends javax.servlet.http.HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doProcess (HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		System.out.println("RequestURI = " + RequestURI);
		String contextPath = request.getContextPath();
		System.out.println("contextPath = " + contextPath);
		String command = RequestURI.substring(contextPath.length());
		System.out.println("command = " + command);
		
		//초기화
		ActionForward forward = null;
		Action action = null;
		
		switch (command) {
		case "/MovieSearch.io":
			action = new MovieSearch();
			break;
		case "/NewMovieList.io":
			action = new NewMovieList();
			break;
		case "/MovieDetail.io":
			action = new MovieDetail();
			break;
		case "/MovieAddView.io":
			action = new MovieAddView();
			break;
		case "/MovieAddAction.io":
			action = new MovieAddAction();
			break;
		case "/MovieModifyView.io":
			action = new MovieModifyView();
			break;
		case "/MovieModifyAction.io":
			action = new MovieModifyAction();
			break;
			
		}// switch end
		forward = action.execute(request, response);
		
		if(forward != null) {
			if(forward.isRedirect()) { //리다이렉트 됩니다.
				response.sendRedirect(forward.getPath());
			} else { //포워딩됩니다.
				RequestDispatcher dispatcher = 
						request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
				
			}
		}
		
	}//doProcess() end

	protected void doGet (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess (request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doProcess (request, response);
	}
}
