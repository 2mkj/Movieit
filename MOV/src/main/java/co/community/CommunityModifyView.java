package co.community;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.commuboard.Community;
import co.commuboard.CommunityDAO;
import net.member.MemberDAO2;
import net.member.Memberall;

public class CommunityModifyView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		MemberDAO2 mdao = new MemberDAO2();
		Memberall m = mdao.member_info(email);
		request.setAttribute("memberinfo", m);

		CommunityDAO cdao = new CommunityDAO();
		Community c = new Community();
		ActionForward forward = new ActionForward();

		// 파라미터로 전달받은 수정할 글 번호를 num변수에 저장합니다.
		int num = Integer.parseInt(request.getParameter("num"));
		System.out.println(num);
		// 글 내용을 불러와서 boarddata객체에 저장합니다.
		c = cdao.getDetail(num);

		// 글 내용 불러오기 실패한 경우입니다.
		if (c == null) {
			System.out.println("(수정)상세보기 실패");
			forward = new ActionForward();
			forward.setRedirect(false);
			request.setAttribute("message", "게시판 수정 상세 보기 실패입니다.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		System.out.println("(수정)상세보기 성공");

		// 수정 폼 페이지로 이동할 때 원문 글 내용을 보여주기 때문에 boarddata 객체를
		// request 객체에 저장합니다.
		request.setAttribute("c", c);
		forward.setRedirect(false);
		// 글 수정 폼 페이지로 이동하기위해 경로를 설정합니다.
		forward.setPath("community/boardModify.jsp");
		return forward;
	}

}
