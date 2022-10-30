package net.mypage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.MemberDAO2;
import net.member.Memberall;
import net.zzim.db.Zzim;
import net.zzim.db.ZzimDAO;


public class myZzimAdd implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		MemberDAO2 mdao = new MemberDAO2();
		Memberall m = mdao.member_info(email);
		request.setAttribute("memberinfo", m);
		
		ZzimDAO zdao = new ZzimDAO();
		Zzim z = new Zzim();
		boolean result = false;
		
		String title = request.getParameter("title");
		String mdate = request.getParameter("mdate");
		String poster = request.getParameter("poster");
		
		z.setZzim_name(email);
		z.setZzim_title(title);
		z.setZzim_date(mdate);
		z.setZzim_poster(poster);
		result = zdao.insert(z);
		PrintWriter out=response.getWriter();
		if(result==false) {
			System.out.println("찜 실패");
			out.println("<script>");
			out.println("alert('로그인 후 이용 가능합니다.');");
			out.println("location.href=document.referrer;"); // 뒤로 가서 새로고침
			out.println("</script>");
			return null;
		}
		System.out.println("찜 완료");
		out.println("<script>");
		out.println("location.href=document.referrer;"); // 뒤로 가서 새로고침
		out.println("</script>");
		out.close();
		return null;
	}

}
