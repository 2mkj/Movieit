<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>프로필 수정</title>
<link href="css/mymaincss/my.css" rel="stylesheet" type="text/css">
<script src="js/jquery-3.6.0.js"></script>
</head>
<body class="updatebody">
   <form class="update" action="myWriting.net" method="post">
   <h3>작성한 글</h3>
	<hr>
<%-- 게시글이 있는 경우 --%>
<c:if test="${listcount > 0 }">
<table class="mytable">
     <tbody>
     <c:set var="num" value="${listcount-(page-1)*limit}" />
     <c:forEach var="b" items="${boardlist}">
     <tr>
	 <td><%--제목 --%>
	  <div>
	  	 <a href="CommunityDetail.co?num=${b.board_num}">
	  	   <c:if test="${b.board_subject.length()>=20}">
	  	     <c:out value="${b.board_subject.substring(0,20)}…" />
	  	   </c:if>
	  	   <c:if test="${b.board_subject.length()<20}">
	  	     <c:out value="${b.board_subject}" />
	  	   </c:if>
	  	 </a><span style="color: #f80">[${b.cnt}]</span>
	  </div>
	  </td>
	  <td><div><input type="hidden" name="num" value="${b.board_num}">${b.board_date}</div></td>
	  <td><div>
	  <a href="CommunityModifyView.co?num=${b.board_num}"><img id="modifybtn" src="image/mymainimg/modify.png" style="width:17px;height:17px"></a>
	  <a  class="deletechk" href="myWritingDelete.net?num=${b.board_num}"><img id="deletebtn1" src="image/mymainimg/trash.png" style="width:16px;height:17px"></a></div></td>
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
<a  href="myWriting.net?page=${page-1}&search_word=${search_word}"
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
	     <c:url var="go" value="myWriting.net">
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
<a  href="myWriting.net?page=${page+1}&search_word=${search_word}">&gt;</a>
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