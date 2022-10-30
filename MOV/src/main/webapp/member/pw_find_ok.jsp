<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>무빗! 비밀번호 재설정</title>
<link href="${pageContext.request.contextPath}/css/member/idpw_check_ok.css" type="text/css" rel="stylesheet">
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
.logo {text-align: center !important;}
.clearfix {text-align: center !important;}

.newpass{color:white;}
.submitbtn {width:50% !important;}
</style>
<script>
	<%-- 버튼 클릭시 해당페이지로 이동 --%>
	$(function(){
		$("#login").click(function() {
			location.href = "login.me";
		});
	})
</script>
</head>
<body>
<div class="header">
<jsp:include page="../mainpage/top.jsp"/>
</div>
	<form class=login name="pwok" method="post">	<!-- 클래스 이름 바꾸고 css 조정 -->
		<div class="logo">
			<span class="navbar-brand">무빗!</span>
		</div>
		<br><br>
		
		<h5>임시 비밀번호가 발급되었습니다.</h5>
		<h5>로그인 후 변경해주세요.</h5><br>
		<h4>임시 비밀번호: <span class="newpass">${pass}</span></h4><br>
		
		<div class="clearfix">
			<input type="button" class="submitbtn" id="login" value="로그인">
		</div>
	</form>
</body>
</html>