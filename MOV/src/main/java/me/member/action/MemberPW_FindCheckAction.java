package me.member.action;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.member.db.MemberDAO;

public class MemberPW_FindCheckAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		MemberDAO mdao = new MemberDAO();		
		String email = request.getParameter("email");
		String name = request.getParameter("name2");
		String phone = request.getParameter("phone2");
		int result = mdao.isPW(email, name, phone);
		System.out.println("결과는 " + result);
		
		
		if (result == 1) { 
			String newpass = getRamdomPassword(10);
			System.out.println("임시비밀번호: " + newpass);
			
			result = mdao.newPass(email, newpass);
			
			String pass = mdao.isPass(email, name, phone);
			request.setAttribute("pass", pass);
			forward.setRedirect(false);
			forward.setPath("member/pw_find_ok.jsp");
			return forward;
		} else if(result == 0) {
			forward.setPath("member/idpw_check_error.jsp");
			forward.setRedirect(false);
			return forward;	
		}
		return null;
		
	}
	
	
	public String getRamdomPassword(int size) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }
}
