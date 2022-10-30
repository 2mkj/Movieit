<%@page import="me.member.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>무빗! 아이디 및 비밀번호 찾기(아이디)</title>
<link href="${pageContext.request.contextPath}/css/member/idpw_check.css" type="text/css" rel="stylesheet">
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
	.logo {text-align: center !important;}
	.clearfix {text-align: center !important;}
	
	body{
	margin-top: 100px;
	line-height: 1.6
	}
	
	.container{
		width: 500px;
		margin: 0 auto;
	}
</style>
<script>
	<%-- 탭 클릭시 화면전환 --%>
	$(document).ready(function(){
		
		$('ul.tabs li').click(function(){
			var tab_id = $(this).attr('data-tab');
	
			$('.login').removeClass('current');
	
			$(this).addClass('current');
			$("#"+tab_id).addClass('current');
		})
	})
</script>
</head>
<body>
<div class="header">
<jsp:include page="../mainpage/top.jsp"/>
</div>
<form id=tab1 class="login current" name="id_find" action="idFindCheck.me" method="post">
	<div class="logo">
		<span class="navbar-brand">무빗!</span>
	</div>
	<br><br>

	<ul class="tabs">
		<li class="tab-link1" data-tab="tab1">아이디찾기</li>
		<li class="tab-link2" data-tab="tab2">비밀번호찾기</li>
	</ul>
	<br>
	
	<div id="tab1" class="tab-content current">
		<b>이름</b>
		<input type="text" name="name1" placeholder="이름을 입력하세요." id="name1" required>
		<span id="name1_message"></span>
		
		<b>전화번호</b>
		<input type="text" name="phone1" placeholder="전화번호를 입력하세요." id="phone1" required>
		<span id="phone1_message"></span>
		
		<div class="clearfix">
			<!--<input type="submit" class="submitbtn" id="id" value="다음">-->
			<button class="signupbtn" type="submit" value="다음">다음</button>
		</div>
	</div>
</form>

<form id=tab2 class="login" name="pw_find" action="pwFindCheck.me" method="post">
	<div class="logo">
		<span class="navbar-brand">무빗!</span>
	</div>
	<br><br>
	
	<ul class="tabs">
		<li class="tab-link2" data-tab="tab1">아이디찾기</li>
		<li class="tab-link1" data-tab="tab2">비밀번호찾기</li>
	</ul>
	<br>
	
	<div id="tab2" class="tab-content">
		<b>아이디</b>
		<input type="text" name="email" placeholder="이메일을 입력하세요." id="email" required>
		
		<b>이름</b>
		<input type="text" name="name2" placeholder="이름을 입력하세요." id="name2" required>
		
		<b>전화번호</b>
		<input type="text" name="phone2" placeholder="전화번호를 입력하세요." id="phone2" required>
		
		<div class="clearfix">
			<button class="signupbtn" type="submit" value="다음">다음</button>
		</div>
	</div>
</form>
</body>
</html>