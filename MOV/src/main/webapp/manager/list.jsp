<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*, boo.board.db.BoardBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>list.jsp</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</head>
<style>
	.container{margin-top:3em}
	table, h4{text-align:center;}
</style>
<body>
	<jsp:include page="../mainpage/top.jsp" />
	<div class="container">
		<c:if test="${!empty list }"/>
		<table class="table">
			<thead>
				<tr>
					<td>번호</td>
					<td>아이디</td>
					<td>성별</td>
					<td>취미</td>
					<td>자기소개</td>
					<td>가입일</td>
					<td>삭제</td>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${list}" var="temp" varStatus="status">
					<tr>
						<td>${status.count }</td>
						<td><a href="managerlist?id=${temp.id }">${temp.id }</a></td>
						<td>${temp.ganderView }</td>
						<td>${temp.hobby }</td>
						<td><c:out value="${temp.intro }"/></td>
						<td>${temp.register_date }</td>
						<td><button class="btn btn-danger btn-sm">삭제</button></td>
					</tr>
				
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${empty list}">
			<h4>조회된 데이터가 없습니다.</h4>
		</c:if>
		
	</div>
	<script>
		$("td > button").click(function(){
			var answer = confirm("정말 삭제하시겠습니까?");
		/*
		<form action=delete method=post><input name=id value=Java1><input type=submit></form>
		*/
		if(answer){
			output = "<form action=delete method=post>";
			output += "<input name=id value=" + $(this).parent().parent().find(":nth-child(2)").text() + ">";
			output += "<input type=submit>"
			output += "</form>";
			
			console.log(output);
			
			$("body").append(output);
			$("form").submit(); // 폼을 submit 합니다. 어떤 형식으로 실행되는 지 확인 하고 싶으면 아래 꺼 까지 주석으로
			$('form').css('display','none');
		}else{
			alert('취소를 선택하셨습니다.');
		}
	})
	
	var result = '${del_result}';
	if(result != ''){
		if(result == 1){
			alert('삭제 성공입니다.');
		}else{
			alert('삭제 실패입니다.');
		}
		<%session.removeAttribute("del_result");%>
	}
	</script>
</body>
</html>