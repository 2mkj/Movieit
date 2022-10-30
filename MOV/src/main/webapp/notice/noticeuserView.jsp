<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	  String name = (String)request.getAttribute("name");
%>
<html>
<head>
<title>게시글 보기</title>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="js/jquery-3.6.0.js"></script>
<link rel="stylesheet" href="css/communitycss/view.css">
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<style>
html {overflow:hidden;}
#comm{height:500px!important;background:white;}
.container{padding-bottom:30px;margin:0;text-align:center;}
.commut{width:550px}
.page-Link{font-size:16px;}
</style>
</head>
<body>
<!-- 게시판 -->
<form id="comm" method="post" action="noticeUserView.no">
<input type="hidden" id="email" name="email" value="${memberinfo.email}">
<div class="container community1">
<div class="user_info">
<span style="font-size:25px;font-family:'a시월구일2'"><i class="fa fa-user"></i>&nbsp;
<span id="user"></span>님의 게시글</span>
<span style="font-size:14px;">&#40;총 ${listcount}글&#41;</span>
</div>
<table class="table commut">
<!-- 테이블 간격 -->
<colgroup>
<col width="15%" />
<col width="55%" />
<col width="15%" />
<col width="15%" />
</colgroup>
<!-- 게시판 내용 -->
 <thead>
    <tr>
    	<th><div style="font-size:12px!important;text-align:center">번호</div></th>
    	<th><div style="font-size:12px!important;">제목</div></th>
    	<th><div style="font-size:12px!important;text-align:center">글쓴이</div></th>
    	<th><div style="font-size:12px!important;text-align:center">날짜</div></th>
    </tr>
    </thead>
    <tbody>
<%-- 게시글이 있는 경우 --%>
<c:if test="${listcount > 0 }">
     <c:set var="num" value="${listcount-(page-1)*limit}" />
     <c:forEach var="b" items="${boardlist}">
     <tr>
      <td><%-- 번호 --%>
       <c:out value="${num}"/><%--출력 --%>
       <c:set var="num" value="${num-1}" /> <%--num=num-1;의미 --%>
	  </td>
	 <td><%--제목 --%>
	  <div style="font-size:15px">
	  	 <a href="#" onclick="opener.top.location='noticeDetail.no?num=${b.board_num}'">
	  	   <c:if test="${b.board_subject.length()>=20}">
	  	     <c:out value="${b.board_subject.substring(0,20)}..." />
	  	   </c:if>
	  	   <c:if test="${b.board_subject.length()<20}">
	  	     <c:out value="${b.board_subject}" />
	  	   </c:if>
	  	 </a>
	  </div>
	  </td>
	   <td><div class="user_name">${b.board_user_name}<input type="hidden" id="user_name" name="name" value="${b.board_user_name}"></div></td>
	  <td><div>${b.board_date}</div></td>
	  </tr>
	  </c:forEach>
</c:if><%--<c:if test="${listcount > 0}"> end --%>
<%--게시글이 없는 경우--%>
<c:if test="${listcount == 0}">
  <td colspan="4"><h2 style="color:gray; text-align:center; margin:100px 0;">등록된 글이 없습니다.</h2>
  </td>
</c:if>
	  </tbody>
	 </table>

<!-- 페이징 -->
<div class="center-block">
	<ul class="commup justify-content-center">
 	  <c:if test="${page <= 1 }">
		<li class="page-item hovernone">
		 <a>&lt;</a>
		</li>
	  </c:if>
 	  <c:if test="${page > 1 }">
		<li class="page-item">
		 <a  href="noticeUserView.no?board_name=<%=name%>&page=${page-1}">&lt;</a>
		</li>
	  </c:if>

	 <c:forEach var="a" begin="${startpage}" end="${endpage}">
	   <c:if test="${a == page}">
		 <li class="page-item ">
			<a class="page-Link pageActive">${a}</a>
		 </li>
	   </c:if>
	   <c:if test="${a != page }"> 
	 	 <li class="page-item">
			<a href="noticeUserView.no?board_name=<%=name%>&page=${a}" 
			   class="page-Link">${a}</a>
		 </li>
	   </c:if>
	 </c:forEach>
	 
<c:if test="${page >= maxpage }">
		<li class="page-item">
		 <a>&gt;</a>
		</li>
	  </c:if>
 	  <c:if test="${page < maxpage }">
		<li class="page-item">
		 <a  href="noticeUserView.no?board_name=<%=name%>&page=${page+1}">&gt;</a>
		</li>
	  </c:if>
	</ul>
</div>
</div>
</form>
<script>
const user_name = $('#user_name').val();
$('#user').text(user_name);
</script>
</body>
</html>