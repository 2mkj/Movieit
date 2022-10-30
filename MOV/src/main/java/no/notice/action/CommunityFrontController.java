package no.notice.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("*.no")
public class CommunityFrontController extends javax.servlet.http.HttpServlet {
	
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
		
		//상속관계여야 가능 (Action의 자식들▼)
		switch (command) {
		case "/noticeCommunity.no":
			action = new CommunityAction();
			break;
		case "/noticeWrite.no":
			action = new CommunityWriteAction();
			break;
		case "/noticeAddAction.no":
			action = new CommunityAddAction();
			break;	
		case "/noticeDetail.no":
			action = new CommunityDetailAction();
			break;	
		case "/noticeModifyView.no":
			action = new CommunityModifyView();
			break;	
		case "/noticeModify.no":
			action = new CommunityModifyAction();
			break;	
		case "/noticeDelete.no":
			action = new CommunityDeleteAction();
			break;
//		case "/CommentAdd.no":
//			action = new CommentAdd();
//			break;
//		case "/CommentList.no":
//			action = new CommentList();
//			break;
//		case "/CommentDelete.no":
//			action = new CommentDelete();
//			break;
//		case "/UserCommentDelete.no":
//			action = new UserCommentDelete();
//			break;
//		case "/UserCommentDelete1.no":
//			action = new UserCommentDelete1();
//			break;
//		case "/CommentUpdate.no":
//			action = new CommentUpdate();
//			break;
//		case "/CommentReply.no":
//			action = new CommentReply();
//			break;
		case "/noticeUserView.no":
			action = new UserViewAction();
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

	// doProcess (request, response)메서드를 구현하여 요청이 GET방식이든
	// POST방식으로 전송되어 오든 같은 메서드에서 요청을 처리할 수 있도록 하였습니다.
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
