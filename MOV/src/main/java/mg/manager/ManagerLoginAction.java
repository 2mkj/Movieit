package mg.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ManagerLoginAction implements ManagerAction {
	public ManagerForward execute(HttpServletRequest request,
						HttpServletResponse response) throws ServletException, IOException {
		String id = "";
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i = 0; i < cookies.length;i++) {
				if(cookies[i].getName().equals("id")) {
					
					id = cookies[i].getValue();
				}
			}
		}
	
		request.setAttribute("id", id);
		ManagerForward forward = new ManagerForward();
		forward.setRedirect(false);// 주소변경없이 jsp페이지의 내용을 보여줍니다.
		forward.setPath("manager/loginForm.jsp");
		return forward;
	}
}
