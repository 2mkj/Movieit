<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>무빗! 회원가입 성공</title>
<link href="${pageContext.request.contextPath}/css/member/join_complete.css" type="text/css" rel="stylesheet">
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
.logo {text-align: center !important;}
	
.clearfix {
	text-align: center !important;
	white-space:nowrap;
}
</style>
<script>
	<%-- 버튼 클릭시 해당페이지로 이동 --%>
	$(function(){
		$("#login").click(function() {
			location.href = "login.me";
		});
		
		$("#main").click(function() {
			location.href = "main.net";
		});
	})
	
</script>
</head>
<body>
<div class="header">
<jsp:include page="../mainpage/top.jsp"/>
</div>
	<form class=login id=okform name="joincomplete" method="post"> <!-- 클래스 이름 바꾸고 css 조정 -->
		<div class=img>
			<img src="${pageContext.request.contextPath}/image/member/confetti.png">
		</div>
		<br><br>
		<h2>환영합니다!</h2><br>
		<h5><%= request.getParameter("user_name") %>님의 회원가입이 완료되었습니다.<br>
		로그인 후 서비스를 이용 하실 수 있습니다.</h5><br><br>
		
		<div class="clearfix">
			<input type="button" class="submitbtn" id="main" value="메인으로 돌아가기">
			<input type="button" class="submitbtn" id="login" value="로그인">
		</div>
		
	</form>
</body>
</html>