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
tbody > tr > td > .wrapzzim{margin:6px 12px 6px 12px;}
</style>
</head>
<body class="updatebody">
   <form class="update"  method="post" >
   <button type="submit" style="display:none"></button>
   <h3>찜한 영화</h3>
	<hr>
	<table style="margin:27px auto"><tbody><tr><td>
<%-- 게시글이 있는 경우 --%>
<c:if test="${listcount > 0 }">
     <c:set var="num" value="${listcount-(page-1)*limit}" />
     <c:forEach var="b" items="${boardlist}">
      <input type="hidden" name="email" value="${email}">
	  <input type="hidden" name="title" value="${b.zzim_title}">
	  <input type="hidden" name="mdate" value="${b.zzim_date}">
	  <div class="wrapzzim" style="float:left;text-align:center;cursor:pointer;" >
	  	   <a href="MovieDetail.io?title=${b.zzim_title}&mdate=${b.zzim_date}">
	  	   <c:if test="${b.zzim_poster.length()>=20}">
	  	    <c:set var='src' value='${b.zzim_poster}' />
	  	   </c:if>
	  	   <c:if test="${b.zzim_poster.length()<20}">
	  	   	<c:set var='src' value='${"movieupload/"}${b.zzim_poster}'/>
	  	   </c:if>
	  	   <img class="poster" src="${src}" style="width:120px;height:180px;border-radius:10px"></a>
	  	 	<br><b style="font-size:13px">
	  	 	<c:if test="${b.zzim_title.length()>=10}">
	  	     <c:out value="${b.zzim_title.substring(0,10)}…" />
	  	   </c:if>
	  	   <c:if test="${b.zzim_title.length()<10}">
	  	     <c:out value="${b.zzim_title}" />
	  	   </c:if>
	  	 	</b>
	  	 	<br>
	  	 		<div style="text-align:center">
						<button type="submit" id="zzim" onclick="javascript:form.action='myZzimDelete.net?email=${email}&title=${b.zzim_title}&date=${b.zzim_date}';"><i class='fa fa-heart'></i></button>
				</div>
	  	 </div>
	  </c:forEach>
</c:if> <%--<c:if test="${listcount > 0}"> end --%>
<%--게시글이 없는 경우--%>
<div style="text-align:center; ">
<c:if test="${listcount == 0 && empty search_word}">
  <h3 style="color:gray; text-align:center; margin:100px auto;">찜한 영화가 없습니다.</h3>
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
<a  href="myZzimList.net?page=${page-1}&search_word=${search_word}"
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
	     <c:url var="go" value="myZzimList.net">
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
		<c:url var="go" value="myZzimList.net">
	       <c:param name="search_word"  value="${search_word}" />
	       <c:param name="page"		    value="${page+1}" />
	     </c:url>
		<li class="page-item">
<a  href="myZzimList.net?page=${page+1}&search_word=${search_word}">&gt;</a>
		</li>
	  </c:if>
	</ul>	 
<!-- 검색 폼 영역 -->
      <div class="my_search">
          <input id='mySearchinput' name="search_word" value="${search_word}"/>
          <input type='submit' class='mySearch1' onclick="javascript:form.action='myZzimList.net';" value="검색">
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
$('button[type="submit"]').keydown(function() {
    if (event.keyCode === 13) {
        event.preventDefault();
    }
});
</script>
</body>
</html>