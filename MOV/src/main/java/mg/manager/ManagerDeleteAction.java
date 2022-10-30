package mg.manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.manager.db.ManagerDAO;


public class ManagerDeleteAction implements ManagerAction {

	@Override
	public ManagerForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ManagerDAO mdao = new ManagerDAO();
		String email = request.getParameter("email");
		System.out.println("이메일값="+email);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = mdao.delete(email);
		
		if(result == 1) {
			out.println("<script>");
			out.println("alert('삭제 성공입니다.');");
			out.println("location.href='managerList.mg'");
			out.println("</script>");
		}else {
			System.out.println(result);
			out.println("<script>");
			out.println("alert('회원 삭제 실패입니다.');");
			out.println("history.back()");
			out.println("</script>");
		}
		out.close();
		return null;
	}

}
