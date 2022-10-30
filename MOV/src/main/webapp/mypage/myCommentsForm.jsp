<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>프로필 수정</title>
<link href="css/mymaincss/my.css" rel="stylesheet" type="text/css">
<script src="js/jquery-3.6.0.js"></script>
</head>
<body class="updatebody">
   <form class="update">
   <h3>작성한 댓글</h3>
	<hr>
	<input type="hidden" name="email" value="${memberinfo.email}">
<%-- 게시글이 있는 경우 --%>
<c:if test="${listcount > 0 }">
<table class="mytable">
  <tbody>
     <c:set var="num" value="${listcount-(page-1)*limit}" />
     <c:forEach var="c" items="${boardlist}">
     <tr>
	 <td><%--제목 --%>
	  <div>
	<input type="hidden" class="num" name="num" value="${c.num}">
	  	 <a href="CommunityDetail.co?num=${c.comment_board_num}">
	  	   <c:if test="${c.content.length()>=20}">
	  	     <c:out value="${c.content.substring(0,20)}..." />
	  	   </c:if>
	  	   <c:if test="${c.content.length()<20}">
	  	     <c:out value="${c.content}" />
	  	   </c:if>
	  	 </a>
	  </div>
	  </td>
	  <td><div>${c.reg_date.substring(2,10)}</div></td>
	  <td><div>
	  <a id="deletebtn1" href="UserCommentDelete1.co?num=${c.num}">
	  <img src="image/mymainimg/trash.png" style="width:16px;height:17px;margin-left:10px">
	  </a></div></td>
	  </tr>
	  </c:forEach>
	  </tbody>
	 </table>
</c:if> <%--<c:if test="${listcount > 0}"> end --%>
<%--게시글이 없는 경우--%>
<c:if test="${listcount == 0 && empty search_word}">
  <h3 style="color:gray; text-align:center; margin:100px 0;">등록된 글이 없습니다.</h3>
</c:if>

<c:if test="${listcount == 0 && !empty search_word}">
  <h3 style="color:gray; text-align:center; margin:100px 0;">검색 결과가 없습니다</h3>
  </c:if>	 
<div class="center-block">
	<ul class="pagination justify-content-center">
  <c:if test="${page <= 1 }">
		<li class="page-item hovernone">
		 <a>&lt;</a>
		</li>
	  </c:if>
 	  <c:if test="${page > 1 }">
		<li class="page-item">
<a  href="myComments.net?page=${page-1}&search_word=${search_word}"
		     >&lt;</a>
		</li>
	  </c:if>
	  
	<c:forEach var="a" begin="${startpage}" end="${endpage}">
	   <c:if test="${a == page}">
		 <li class="page-item ">
			<a class="page-Link pageActive">${a}</a>
		 </li>
	   </c:if>
	   <c:if test="${a != page }"> 
	     <c:url var="go" value="myComments.net">
	       <c:param name="search_word"  value="${search_word}" />
	       <c:param name="page"		    value="${a}" />
	     </c:url>
	 	 <li class="page-item">
			<a href="${go}" class="page-Link">${a}</a>
		 </li>
	   </c:if>
	 </c:forEach>
	 
<c:if test="${page >= maxpage }">
		<li class="page-item">
		 <a>&gt;</a>
		</li>
	  </c:if>
	  
 	  <c:if test="${page < maxpage }">
		<c:url var="go" value="myWriting.net">
	       <c:param name="search_word"  value="${search_word}" />
	       <c:param name="page"		    value="${page+1}" />
	     </c:url>
		<li class="page-item">
<a  href="myComments.net?page=${page+1}&search_word=${search_word}">&gt;</a>
		</li>
	  </c:if>
	</ul>	 
<!-- 검색 폼 영역 -->
      <div class="my_search">
          <input id='mySearchinput' name="search_word" value="${search_word}"/>
          <button type='submit' class='mySearch1'>검색</button>
      </div>	 
</div>
  </form>
<script>
$(function(){
	//페이징 active 처리
    var pageBtn = $(".pagination > li");    
     pageBtn.find(".page-Link").click(function(){   
     pageBtn.removeClass("pageActive");    
     $(this).parent().addClass("pageActive"); 
    })
});    
</script>
</body>
</html>