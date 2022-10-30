<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>프로필 수정</title>
<link href="css/mymaincss/my.css" rel="stylesheet" type="text/css">
<script src="js/jquery-3.6.0.js"></script>
<style>
.fa-heart-o,.fa-heart{font-size:24px;color:#f72840;}
#zzim{border: none;background: transparent;}
tbody > tr > td > .wrapzzim{position:relative;word-break:break-all;margin:10px 8px;border:1.2px solid #cfcfcf; padding:4px;border-radius:10px;box-shadow:1px 1px 3px #e8e8e8;}
#explain{position: absolute; opacity: 0; color:black;font-size:13px;padding:7px} 
.wrapzzim:hover #explain{opacity: 1;}
.wrapzzim:hover .poster{opacity: 0.2;}
</style>
</head>
<body class="updatebody">
   <form class="update"  method="post" >
   <button type="submit" style="display:none"></button>
   <h3>작성한 리뷰</h3>
	<hr>
	<table style="margin:27px auto"><tbody><tr><td>
<%-- 게시글이 있는 경우 --%>
<c:if test="${listcount > 0 }">
     <c:set var="num" value="${listcount-(page-1)*limit}" />
     <c:forEach var="b" items="${boardlist}">
      <input type="hidden" name="email" value="${email}">
	  <input type="hidden" name="title" value="${b.review_title}">
	  <div class="wrapzzim" style="float:left;text-align:center;cursor:pointer;" >
	  	  <a href="ReviewDetailAction.com?review_id=${email}&review_move_name=${b.review_move_name}">
	  	   <!-- 내용 -->
	  	   <span id = 'explain'>
	  	   <c:if test="${b.review_content.length()>=60}">
	  	     <c:out value="${b.review_content.substring(0,60)}…" />
	  	   </c:if>
	  	   <c:if test="${b.review_content.length()<60}">
	  	     <c:out value="${b.review_content}" />
	  	   </c:if>
	  	  </span> 
	  	   <!-- 포스터 -->
	  	   <c:if test="${b.review_poster.length()>=20}">
	  	    <c:set var='src' value='${b.review_poster}' />
	  	   </c:if>
	  	   <c:if test="${b.review_poster.length()<20}">
	  	   	<c:set var='src' value='${"movieupload/"}${b.review_poster}'/>
	  	   </c:if>
	  	   <img class="poster" src="${src}" style="width:120px;height:160px;border-radius:8px;"></a>
	  	 	<br>
	  	 	  <!-- 제목 -->
	  	 	<b style="font-size:13px">
	  	 	<c:if test="${b.review_title.length()>=8}">
	  	     <c:out value="${b.review_title.substring(0,8)}…" />
	  	   </c:if>
	  	   <c:if test="${b.review_title.length()<8}">
	  	     <c:out value="${b.review_title}" />
	  	   </c:if>
	  	 	</b>
	  	 	<br>
	  	 	<span style="font-size:12px;padding:0"><c:out value="${b.review_date.substring(2,10)}" /></span>
	  	 </div>
	  </c:forEach>
</c:if> <%--<c:if test="${listcount > 0}"> end --%>
<%--게시글이 없는 경우--%>
<div style="text-align:center; ">
<c:if test="${listcount == 0 && empty search_word}">
  <h3 style="color:gray; text-align:center; margin:100px auto;">작성된 리뷰가 없습니다.</h3>
</c:if>

<c:if test="${listcount == 0 && !empty search_word}">
  <h3 style="color:gray; text-align:center; margin:100px auto;">검색 결과가 없습니다</h3>
  </c:if>	
  </div>
  </td></tr>
	</tbody></table>
<div class="center-block">
	<ul class="pagination justify-content-center">
  <c:if test="${page <= 1 }">
		<li class="page-item hovernone">
		 <a>&lt;</a>
		</li>
	  </c:if>
 	  <c:if test="${page > 1 }">
		<li class="page-item">
<a  href="myReview.net?page=${page-1}&search_word=${search_word}"
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
	     <c:url var="go" value="myReview.net">
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
		<c:url var="go" value="myReview.net">
	       <c:param name="search_word"  value="${search_word}" />
	       <c:param name="page"		    value="${page+1}" />
	     </c:url>
		<li class="page-item">
<a  href="myReview.net?page=${page+1}&search_word=${search_word}">&gt;</a>
		</li>
	  </c:if>
	</ul>	 
<!-- 검색 폼 영역 -->
      <div class="my_search">
          <input id='mySearchinput' name="search_word" value="${search_word}"/>
          <input type='submit' class='mySearch1' onclick="javascript:form.action='myReview.net';" value="검색">
      </div>	 
</div>
  </form>
<script>
//페이징 active 처리
$(function(){
    var pageBtn = $(".pagination > li");    
     pageBtn.find(".page-Link").click(function(){   
     pageBtn.removeClass("pageActive");    
     $(this).parent().addClass("pageActive"); 
    })
});
$(".deletechk").click(function () {
    var answer = confirm('정말 삭제하시겠습니까?');
    if (!answer) {// 취소를 클릭한 경우
		event.preventDefault(); //이동하지 않습니다.	    	
    }
});
</script>
</body>
</html>