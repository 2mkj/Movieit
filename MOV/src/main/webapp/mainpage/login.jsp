<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>로그인 화면</title>
<jsp:include page="top.jsp"/>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.js"></script>
<style>
.container1{width:500px; margin:0 auto; margin-top:100px}
body > div.container1 > form > div > label {margin: 10px 0}
</style>
<script>
$(function() {
	var email = '${email}';
	if (email) {
		$("#email").val(email);
		$("#remember").prop('checked',true);
	}
})
</script>
</head>
<div class="container1">
<form name="loginform" action="loginOk.me" method="post" class="border border-Light p-5">
 <p class="h4 mb-4 text-center">login</p>
 	<div class="form-group">
		<b>아이디</b> 
		<input type="text" name="email" placeholder="Enter email" id="email" required>
	</div>
	<div>
		<b>Password</b> 
		<input type="password" name="pass" placeholder="Enter password" required> 
	</div>
		<div class="form-group custom-control custom-checkbox">
           <input type="checkbox" class="custom-control-input" id="remember" name="remember" value="store">
           <label class="custom-control-label" for="remember">id 기억하기</label>
       </div>
       

	<button type="submit" class="btn btn-info my-4 btn-block">Submit</button>

</form>
</div>
</html>