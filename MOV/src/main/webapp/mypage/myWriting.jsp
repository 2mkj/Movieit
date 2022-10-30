<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>마이페이지 - 작성한 글</title>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<jsp:include page="../mainpage/top.jsp"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
.header {position:relative;z-index:2; margin:0;padding:0;} 

.col-md-7{margin-top:70px;width:30%}
.col-md-5{margin-top:40px;width:700px;height:1000px;}
@media screen and (max-width:1500px) {
.col-md-7{margin-top:70px;float:left; margin-left:0;}
.col-md-5{margin-top:40px;float:right;width:500px}
} 
@media screen and (max-width:770px) {
.col-md-7 {margin: 0 auto;}
.col-md-5 {position:relative; float: left;}
} 
footer {
  bottom: 0px;
  height: 3rem;
  background-color: #ccc;
  width: 100%;
  text-align: center;
  line-height: 3rem;
}
</style>
</head>
<body>
<div class="header">

</div>
<div class="container">
<div class="wrap">
    <div class="row featurette">
      
      <div class="col-md-7">
        <jsp:include page="sidebar.jsp"/>
      </div>
      
      <div class="col-md-5">
       <jsp:include page="myWritingForm.jsp"/>
	  </div>
</div>
</div>
</div>
<jsp:include page="../mainpage/upup.jsp"/>
<!-- 하단 -->
<footer>
	<jsp:include page="../mainpage/bottom.jsp"/>
</footer>



</body>
</html>