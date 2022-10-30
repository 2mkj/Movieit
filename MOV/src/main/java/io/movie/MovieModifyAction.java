package io.movie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import io.movie.db.Movie;
import io.movie.db.MovieDAO;

public class MovieModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String realFolder = "";
		String saveFolder = "movieupload";
		int fileSize = 5 * 1024 * 1024;
		ServletContext sc = request.getServletContext();
		realFolder = sc.getRealPath(saveFolder);

		try {
		MultipartRequest multi = new MultipartRequest (request, realFolder, fileSize, "utf-8",
				new DefaultFileRenamePolicy());
		String title = multi.getParameter("title");
		System.out.println("수정할 영화는 "+title);
		String director = multi.getParameter("director");
		String actor = multi.getParameter("actor");
		String mdate = multi.getParameter("mdate");
		String content = multi.getParameter("content");
		String poster = multi.getFilesystemName("poster");

		Movie m = new Movie();
		m.setTitle(title);
		m.setDirector(director);
		m.setActor(actor);
		m.setMdate(mdate);
		m.setContent(content);
		if(poster != null) {
		m.setPoster(poster);
		}
		 MovieDAO mdao = new MovieDAO();
		 int result = mdao.update(m,title);
		 
		 response.setContentType("text/html;charset=utf-8");
		 PrintWriter out = response.getWriter();
		 out.println("<script>");
		 //삽입이 된 경우
		 if(result == 1) {
			 out.println("alert('영화 정보가 수정되었습니다.');");
			 out.println("location.href='MovieDetail.io?title="+title+"&mdate="+mdate+"';");
		 } else {
			 out.println("alert('영화 정보 수정에 실패했습니다.');");
			 out.println("history.back()"); //비밀번호를 제외한 다른 데이터는 유지되어있습니다.
		}
		 out.println("</script>");
		 out.close();
		 return null;
		}catch (IOException ex) {
			ActionForward forward = new ActionForward();
			forward.setPath("error/error.jsp");
			request.setAttribute("message", "포스터 업로드 실패입니다.");
			forward.setRedirect(false);
			return forward;
		}//catch end
	}//execute end

}
