package mg.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ManagerLogoutAction implements ManagerAction {

	@Override
	public ManagerForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ManagerForward forward = new ManagerForward();
		HttpSession session = request.getSession();
		session.invalidate();
		forward.setPath("login.net");
		forward.setRedirect(true);
		return forward;
	}
}
