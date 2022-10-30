<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>회원 정보 보기</title>
 <jsp:include page="listbody.jsp" />  
<style>
  tr>td:nth-child(odd){font-weight: bold}
  td,h3{text-align:center}
  h3{color:green;}
  .w-btn {
    position: relative;
    border: none;
    display: inline-block;
    padding: 15px 30px;
    border-radius: 15px;
    font-family: "paybooc-Light", sans-serif;
    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
    text-decoration: none;
    font-weight: 600;
    transition: 0.25s;
}
.w-btn:hover {
    letter-spacing: 2px;
    transform: scale(1.2);
    cursor: pointer;
}
</style>
<script>

function back(){
     history.back();
}

</script>
</head>
<body>
	<div class="container">
	<h3>회원 정보 조회</h3>
		<table class="table table-boardered">
			<tr>
				<td>아이디</td>
				<td>${managerinfo.email}</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>${managerinfo.pass}</td>
			</tr>
			<tr>
				<td>닉네임</td>
				<td>${managerinfo.user_name}</td>
			</tr>
			<tr>
				<td>이름</td>
				<td>${managerinfo.name}</td>
			</tr>
			
			<tr>
				<td>성별</td>
				<td>${managerinfo.gender}</td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td>${managerinfo.phone}</td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td>${managerinfo.phone}</td>
			</tr>
			<tr>
				<td>post</td>
				<td>${managerinfo.post}</td>
			</tr>
			<tr>
				<td>주소</td>
				<td>${managerinfo.address}</td>
			</tr>
			<tr>
					
				<td colspan=2>
				    <button onclick="back()" class="w-btn w-btn-gra3 w-btn-gra-anim" type="button">리스트로 돌아가기</button></td>
			</tr>
		</table>
	</div>	
</body>
</html>