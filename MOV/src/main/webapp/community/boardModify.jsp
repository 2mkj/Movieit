<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>커뮤니티 게시판 글수정</title>
<jsp:include page="../mainpage/top.jsp"/>
<link rel="stylesheet" href="css/communitycss/view.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<%--게시판 수정 --%>
<form action="CommunityModify.co" method="post" name="modifyform" id="comm">
  <input type="hidden" name="num" value="${c.board_num}">
<div class="container community1 community2">
<table class="table commut">
<!-- 테이블 간격 -->
<colgroup>
<col width="15%" />
<col width="85%" />
</colgroup>
<!-- 작성 페이지 -->
 <thead>
   <tr>
     <th colspan="4">
     <img src="${pageContext.request.contextPath}/image/mymainimg/writing.png" width=32 height=32 id="commu_icon">
     커뮤니티 게시판</th>
     <th><input type="hidden" name="name" value="${email}"></th>
    </tr> 
    </thead>
    <tbody class="write">
    <tr>
    	<td>글쓴이</td>
    	<td colspan="4"><input class="inputname" type="text" name="user_name" value="${c.board_user_name}" readOnly></td>
    </tr>
    <tr>
    	<td>제목</td>
    	<td colspan="4"><input class="inputsub" type="text" name="subject" value="${c.board_subject}" required></td>
    </tr>
    <tr>
    	<td>내용</td>
    	<td colspan="4"><textarea class="inputcon" name="content">${c.board_content}</textarea></td>
    </tr>
 	<tr>
 	<td colspan="5">
 		<button type="submit" class="Modifycommu button-5">수정</button>
 		<button type="button" class="cancommu button-5">취소</button></td>
 	</tr>
 	</tbody>
    </table>
    </div>
</form>
<!-- 하단 -->
<jsp:include page="../mainpage/upup.jsp"/>
<footer>
	<jsp:include page="../mainpage/bottom.jsp"/>
</footer>
<script>
$(document).ready(function() {
	
	//submit 버튼 클릭할 때 이벤트 부분
  $('form').submit(function() {
	
	if ($.trim($(".inputsub").val()) == "") {
		alert("제목을 입력하세요");
		$(".inputsub").focus();
		return false;
	}
	if($.trim($(".inputcon").val()) == "") {
		alert("내용을 입력하세요");
		$(".inputcon"). focus();
		return false;
	}
  });//submit end
});
$(".cancommu").click(function(){
	var answer = confirm("글 수정을 취소하시겠습니까?")
	if(answer == true)
		history.go(-1);
})
</script>
</body>
</html>