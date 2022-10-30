<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.js"></script>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<style>
body{background:#e3e3e3 !important;}
/* 프로필 사진 */
#MyPic{display: block; margin:0 auto 8px auto; width:180px; height:180px; border-radius: 50%; border: 1px solid lightgray}
#MyNic{font-size:25px; font-family: 'a시월구일2', Poppins, sans-serif; text-align:center}

/* 사이드 바 */
.side{text-align:center}
.sideList{list-style:none; padding:0px; margin: 0 auto; text-align:left}
.sideList > li {padding: 1px;}
.sideList > li > a > i{text-decoration: none; color:black; font-size: 20px}
body > div.container > div > div > div.col-md-7 > div > div > ul {width: 153px;}

/* hover */
.sideList > li > a {
  box-shadow: inset 0 0 0 0 #404040;
  color: #404040;
  padding: 0 .25rem;
  margin: 0 -.25rem;
  transition: color .3s ease-in-out, box-shadow .3s ease-in-out;
}
.sideList > li > a:hover {color: #fff;box-shadow: inset 200px 0 0 0 #404040;}
.sideList > li > a:hover > .fa{color: #fff;}
.sideList > li > a:active > .fa{color: #fff;background: #404040 !important;}
.sideActive {color: #fff !important;background: #404040!important;;}
.sideActive .fa {color: #fff !important;}
.sideList > li > a {
  display: inline-block;
  width: 170px;
  color: #404040;
  font-family: 'a시월구일2', sans-serif;
  font-size: 18px;
  font-weight: 300;
  line-height: 1.5;
  text-decoration: none;
  padding: 4px 10px;
}
.sideList>li>a>.fa{color:#404040}
</style>
</head>
<body>
<div class="side">
<div class="sidebar">
      <!-- 프로필 사진 -->
      <div class="myinfo">
      
       <c:if test='${empty memberinfo.memberfile}'>
	     <c:set var='src' value='image/mymainimg/profile.png'/>
	   </c:if>
	   <c:if test= '${!empty memberinfo.memberfile}'>
	     <c:set var='src' value='${"memberupload/"}${memberinfo.memberfile}'/>
	   </c:if>
		<img id="MyPic" src="${src}">
      	    <span id="MyNic">${memberinfo.user_name}</span>
      	    <span id="Myinfo">님의</span><br>
      	    <span id="Myinfo">
      	    <i class="fa fa-angle-double-right" style="pointer-events : none;"></i>
      	    마이페이지
      	    <i class="fa fa-angle-double-left" style="pointer-events : none;"></i>
      	    </span><br><br>
      </div>
     
      <!-- 사이드 바 -->
        <ul class="sideList">
          <li><a class="smenu sideActive" href="myProfile.net"><i class="fa fa-user"></i>&emsp;프로필 수정</a></li>
          <li><a class="smenu" href="myZzimList.net"><i class="fa fa-heart"></i>&emsp;찜한 영화</a></li>
          <li><a class="smenu" href="myReview.net"><i class="fa fa-pencil"></i>&emsp;작성한 리뷰</a></li>
          <li><a class="smenu" href="myWriting.net"><i class="fa fa-file-text"></i>&emsp;작성한 글</a></li>
          <li><a class="smenu" href="myComments.net"><i class="fa fa-comments"></i>&emsp;작성한 댓글</a></li>
          <li><a href="logout.me"><i class="fa fa-sign-out"></i>&emsp;로그아웃</a></li>
        </ul>
      </div>
</div>


<script>
$(function () {       
	var url = window.location.pathname,    
	urlRegExp = new RegExp(url.replace(/\/$/, '') + "$");          
	$('.smenu').each(function () {                            
		if (urlRegExp.test(this.href.replace(/\/$/, ''))) {                
			$(this).addClass('sideActive');           
		}else{
			$(this).removeClass('sideActive');          
		}        
	});    
});
</script>

</body>
</html>