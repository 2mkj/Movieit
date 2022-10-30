<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>무빗! - 커뮤니티 게시판</title>
<jsp:include page="../mainpage/top.jsp" />
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script src="js/jquery-3.6.0.js"></script>
<script src="js/view.js"></script>
<link rel="stylesheet" href="css/communitycss/view2.css">
<link rel="stylesheet" href="//cdn.jsdelivr.net/font-nanum/1.0/nanumbarungothic/nanumbarungothic.css">
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<style>
body{background:#e3e3e3}
.container {width: 102%!important;margin:0 auto;font-family: 'Nanum Barun Gothic';}
#comm{min-height:100vh;}
.community1{width:1325px;background:#fafafa;border-radius:20px; padding: 60px 0px 160px 0px; margin: 40px auto;}
.communityview{widgh:600px !important;}
.viewp1, .comment-area{width: 700px;margin: 0 auto;}
.text-comment{font-size:15px;font-family: 'Nanum Barun Gothic'}
.comment-nickname{font-size:15px;font-family: 'Nanum Barun Gothic';color:#444444}
footer {
  bottom: 0px;
  height: 3rem;
  background-color: #ccc;
  width: 100%;
  text-align: center;
  line-height: 3rem;
}
@media (max-width: 576px){.container{max-width: 540px !important;margin:60px 0;}}
</style>
</head>
<body>
<div id="comm">
   <input type="hidden" id="loginid" value="${memberinfo.email}" name="loginid">
   <input type="hidden" id="email" value="${memberinfo.email}" name="email">
   <input type="hidden" name="num" value="${c.board_num}" id="comment_board_num">
   <input type="hidden" id="board_user_name" name="board_user_name" value="${c.board_user_name}">
  <div class="container community1">
    <div class="communityview">
     <table class="table viewp1">
      	<tr>
     	  <td colspan="2" style="text-align:center!important;background:#303030;color:white;font-size: 22px;
    font-weight: bold;">
     	  <c:out value="${c.board_subject}" /></td>
     	</tr>
     	<tr>
     	  <td style="text-align:left!important;font-size: 15px;color: #4a4c4b;">
     	 <div title="게시글 보기" style="cursor:pointer"><input type="hidden" id="name" name="name" value="${c.board_name}">
     	 <input type="hidden" id="user_name" name="user_name" value="${c.board_user_name}">
     	 <i style="color:#4a4c4b;"class="fa fa-user"></i>&nbsp; <span class="userdoc" >${c.board_user_name}</span></div>
     	 </td>
        <td style="text-align:right!important;font-size: 14px;color: #4a4c4b;">
     	  <i style="color:#4a4c4b;"class="fa fa-clock-o"></i>&nbsp;&nbsp;${c.board_date}&nbsp; <span style="font-size:12px">|</span>&nbsp;  
     	  <i style="color:#4a4c4b;"class="fa fa-eye"></i>&nbsp;&nbsp;${c.board_readcount}</td>
     	</tr>
    
     	<tr>
     	 <td colspan="2" style="text-align:left!important;height:200px;border:none;padding-top:20px">
     	 <div style="white-space:pre-wrap;font-size:16px;padding:10px 15px"><c:out value="${c.board_content}" /></div></td>
     	</tr>
     	
		<tr>
		<td  colspan="2" >
		<div style="text-align:right;">
		<c:if test="${c.board_name == memberinfo.email || email == 'admin' }">
			<a style="color:red;font-size:16px" href="noticeModifyView.no?num=${c.board_num}">수정</a>
			&nbsp;
			<a style="color:red;font-size:16px;cursor:pointer" class="deletechk">삭제</a>
			&nbsp;&nbsp;
			</c:if>
			<a href="noticeCommunity.no" style="padding-right:10px;">
			 <i class="fa fa-list-ul" style="color:#444444;font-weight:lighter;font-size:16.5px"></i>
			 <span style="color:#444444;font-size:16px;font-weight:bold;">목록</span></a>
		</div>	
		</td>
		</tr>
 	</table>
 	</div>
			
</div>
</div>
	<!-- 하단 -->
<jsp:include page="../mainpage/upup.jsp"/>
<footer>
	<jsp:include page="../mainpage/bottom.jsp"/>
</footer>
<script>
$(function(){
$(".deletechk").click(function () {
    var answer = confirm('정말 삭제하시겠습니까?');
    if (answer) {
    	$(location).attr('href','noticeDelete.no?num=${c.board_num}'); 	
    }
});
//해당 글쓴이가 작성한 다른 글 보기
$(".userdoc").click(function () {
	var url = "noticeUserView.no?board_name="+$('#name').val()+"&board_user_name="+$('#user_name').val();
	var name = "게시글 보기"
	var option = "width=650,height=525,top=100,left=200,location=no,";
 	window.open(url,name,option);
});
})
</script>
</body>
</html>