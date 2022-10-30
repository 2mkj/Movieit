<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>  
<head>
<title>무빗! 회원가입</title>
<link href="${pageContext.request.contextPath}/css/member/join.css" type="text/css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="${pageContext.request.contextPath}/js/join.js"></script>
<style>
	.logo {text-align: center !important;}
</style>
</head>
<body>
<div class="header">
<jsp:include page="../mainpage/top.jsp"/>
</div>
	<form class=join name="joinform" method="post" action="joinProcess.me"> 
		<div class="container">
			<div class="logo">
				<span class="navbar-brand">무빗!</span>
			</div>
			<br><br>
	       	
	       	<div class="email">
	       		<label for="email">E-Mail</label><br>
		       		<input type="text" name="email" id="email" placeholder="이메일을 입력하세요.">
	       		<span id="email_message"></span>
	       	</div>

	       	<div>
	       		<label for="pass">비밀번호</label>
	        	<input type="password" placeholder="비밀번호를 입력하세요." name="pass" id="pass" maxLength="16">
	        	<span id="pass_message"></span>
	       	</div>
	       	<div>
	       		<label for="pass_check">비밀번호 확인</label>
	        	<input type="password" placeholder="비밀번호 확인" name="pass_check" id="pass_check" maxLength="16">
	        	<span id="passok_message"></span>
	       	</div>
	       	
	       	<div>
	       		<label for="user_name">닉네임</label>
	        	<input type="text" placeholder="닉네임을 입력하세요." name="user_name" id="user_name" maxLength="10">
	        	<span id="username_message"></span>
	       	</div>
	       	
	       	<div>
	       		<label for="name">이름</label>
	        	<input type="text" placeholder="이름을 입력하세요." name="name" id="name">
	        	<span id="name_message"></span>
	       	</div>
	       	
	        <div class="jumin">
	        	<label for="jumin1">주민번호</label><br>
		        <input  type="text" placeholder="주민번호 앞자리" maxLength="6" 
		        		name="jumin1" id="jumin1">
		        <b> - </b>
		        <input type="text" placeholder="주민번호 뒷자리" maxLength="7"
		        		name="jumin2" id="jumin2"><br>
		        <span id="jumin_message"></span>
	        </div>
			<br>
	        
	        <label>성별</label><br>
	        <div class="container2">
	        	<input type="radio" name="gender" value="남" id="gender1" onclick="return false">&nbsp;남자&nbsp;&nbsp;&nbsp;&nbsp;
	        	<input type="radio" name="gender" value="여" id="gender2" onclick="return false">여자
	        </div>
	        <br>
	        
	        <div>
	       		<label for="phone">전화번호</label>
	        	<input type="text" placeholder="전화번호를 입력하세요." name="phone" id="phone">
	        	<span id="phone_message"></span>
	       	</div>
	        
	        <div class="post">
	        	<label for="post1">우편번호</label><br>
		        <input type="text" size=3 maxLength=5 name="post1" id="post1" placeholder="우편번호를 입력하세요.">    
		        <input type="button" value="우편번호 검색" id=postcode><br>
		        <span id="post_message"></span>
	        </div>
	       
	        <div>
	        	<label for="address">주소</label>
	        	<input type="text" size=50 name="address" id="address" placeholder="주소를 입력하세요.">
	        	<span id="address_message"></span>
	        </div>
	        
	     	
	     	<div class="clearfix">
	     		<button class="signupbtn" type="submit" value="회원가입">회원가입</button>
	     		<button class="cancelbtn" type="reset" value="취소">취소</button>
	     	</div>
		</div>
	</form>
</body>
</html>