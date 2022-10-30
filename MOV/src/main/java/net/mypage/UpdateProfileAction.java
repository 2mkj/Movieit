package net.mypage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import co.commuboard.Community;
import co.commuboard.CommunityDAO;
import net.member.MemberDAO2;
import net.member.Memberall;

public class UpdateProfileAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String realFolder = "";
		String saveFolder = "memberupload";
		int fileSize = 5 * 1024 * 1024;
		ServletContext sc = request.getServletContext();
		realFolder = sc.getRealPath(saveFolder);
		
		try {
		MultipartRequest multi = new MultipartRequest (request, realFolder, fileSize, "utf-8",
				new DefaultFileRenamePolicy());
		String email = multi.getParameter("email");
		String pass = multi.getParameter("pass");
		String user_name = multi.getParameter("user_name");
		String name = multi.getParameter("name");
		String phone = multi.getParameter("phone");
		String post = multi.getParameter("post1");
		String address = multi.getParameter("address");
		String memberfile = multi.getFilesystemName("memberfile");
		System.out.println("수정값=" 
		+email+", "+pass+", "+user_name+", "+phone+", "+post+", "+address+", "+memberfile);
		Memberall m = new Memberall();
		m.setEmail(email);
		m.setPass(pass);
		m.setUser_name(user_name);
		m.setName(name);
		m.setPhone(phone);
		m.setPost(post);
		m.setAddress(address);
		if(memberfile != null) {
		m.setMemberfile(memberfile);
		}
		Community c = new Community();
		c.setBoard_user_name(user_name);
		CommunityDAO cdao = new CommunityDAO();
		cdao.username_Update(email,user_name);
		
		 MemberDAO2 mdao = new MemberDAO2();
		 int result = mdao.update(m);
		 
		 response.setContentType("text/html;charset=utf-8");
		 PrintWriter out = response.getWriter();
		 out.println("<script>");
		 //삽입이 된 경우
		 if(result == 1) {
			 out.println("alert('수정되었습니다.');");
			 out.println("location.href='myProfile.net';");
		 } else {
			 out.println("alert('회원 정보 수정에 실패했습니다.');");
			 out.println("history.back()"); //비밀번호를 제외한 다른 데이터는 유지되어있습니다.
		}
		 out.println("</script>");
		 out.close();
		 return null;
		}catch (IOException ex) {
			ActionForward forward = new ActionForward();
			forward.setPath("error/error.jsp");
			request.setAttribute("message", "프로필 사진 업로드 실패입니다.");
			forward.setRedirect(false);
			return forward;
		}//catch end
	}//execute end

}
