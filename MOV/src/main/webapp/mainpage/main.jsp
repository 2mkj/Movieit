<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영화 리뷰 사이트 - 무빗!</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<style>
#myCarousel{margin:0}
body{
	overflow-x:hidden; 
	overflow-y:auto;
	line-height:none;
	background:#e3e3e3 !important;}
	
footer {
  bottom: 0px;
  height: 3rem;
  background-color: #ccc;
  width: 100%;
  text-align: center;
  line-height: 3rem;
  }
</style>
<body>
<!-- 상단 메뉴 -->
<div class="header">
<jsp:include page="top.jsp"/>
</div>
<!-- 슬라이드 배너 -->
<jsp:include page="slide.jsp"/>
<br>
<!-- 메인 프리뷰 -->
<jsp:include page="main_preview.jsp"/>
<br><br>
<jsp:include page="upup.jsp"/>
<!-- 하단 -->
<footer>
	<jsp:include page="bottom.jsp"/>
</footer>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>