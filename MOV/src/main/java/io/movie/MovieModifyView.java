package io.movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import io.movie.db.Movie;
import io.movie.db.MovieDAO;
import net.member.MemberDAO2;
import net.member.Memberall;

public class MovieModifyView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		MemberDAO2 mdao = new MemberDAO2();
		Memberall m = mdao.member_info(email);
		
		// 1. 검색어 가져오기
		String query = request.getParameter("title");
		String mdate = request.getParameter("mdate");
		System.out.println("영화 등록페이지 [영화제목: "+query+", 상영일: "+mdate+"]");
		
		if (query != null && !query.equals("")) {
			// 2. OpenAPI 요청 > 결과 반환(JSON)
			String result = getJSON(query);

			// 3. JSON 분석 > 자바 형태로 변환
			JSONParser parser = new JSONParser();
			try {
				JSONObject obj = (JSONObject) parser.parse(result);

				// total: 검색된 영화의 총 개수
				// System.out.println(obj.get("total"));
				request.setAttribute("total", obj.get("total").toString());

				// 4. 결과 전달 + JSP 호출하기
				JSONArray list = (JSONArray) obj.get("items");
				request.setAttribute("word", query);
				request.setAttribute("list", list);
				request.setAttribute("memberinfo", m);
			} catch (org.json.simple.parser.ParseException e) {
				e.printStackTrace();
			}
		}
		MovieDAO dao = new MovieDAO();
		Movie movie = dao.movie_info(query,mdate);
		request.setAttribute("movie", movie);
		RequestDispatcher dispatcher = request.getRequestDispatcher("movieList/movieModify.jsp");
		dispatcher.forward(request, response);
		return null;
	}

	private static String getJSON(String query) {

		String clientId = "jErD94H2vWQTWfVLrGGY"; // 애플리케이션 클라이언트 아이디값"
		String clientSecret = "CTo1pPlQjy"; // 애플리케이션 클라이언트 시크릿값"

		String text = null;

		try {
			text = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패", e);
		}

		// json 결과
		String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text + "&display=100&start=11";

		// xml 결과
		// String apiURL = "https://openapi.naver.com/v1/search/movie.xml?query="+ text;

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = get(apiURL, requestHeaders);
		return responseBody;
	}

	private static String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 에러 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}
}
