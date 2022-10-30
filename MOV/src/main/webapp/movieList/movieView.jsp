<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영화 검색</title>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<jsp:include page="../mainpage/top.jsp" />
<script src="js/jquery-3.6.0.js"></script>
<style>
body{background:#e3e3e3 !important;}
.main-section{margin:40px; 30px;}
.community1{height:6500px;background:#fafafa;border-radius:20px; padding: 30px 20px 160px 20px; margin:  10px auto;}
strong{font-size:35px;font-family: "a시월구일2",sans-serif;padding: 30px 32px 22px 26px;}
.ff{margin:20px auto;width:500px; height:40px; border:2px solid lightgrey; outline:2px solid lightgrey;border-radius:4px }
.button-5 {align-items: center;background-clip: padding-box;background-color: #53ca93;border: 1px solid transparent;border-radius: .25rem;box-shadow: rgba(0, 0, 0, 0.02) 0 1px 3px 0;
  box-sizing: border-box;color: #fff;cursor: pointer;display: inline-flex;font-family: "a시월구일2",sans-serif;font-size: 26px;justify-content: center;line-height: 1;margin: 0 17px;min-height: 44px;
  padding: 10px;position: relative;text-decoration: none;transition: all 250ms;user-select: none;-webkit-user-select: none;touch-action: manipulation;vertical-align: baseline;width: 120px;}
.button-5:hover {transform: translateY(-1px);}
.cowrt{font-size:20px;width:94px;height:18px!important; margin:0!important;background-color: #f80;border:none;}
.cowrt:hover,.cowrt:focus {background-color: #ffa033 !important;box-shadow: rgba(0, 0, 0, 0.1) 0 4px 12px;}
.cowrt:active {background-color: #ffa033;box-shadow: rgba(0, 0, 0, .06) 0 2px 4px;transform: translateY(0);}
.ffff{margin: 26px 26px 24px 36px;}
#serr{font-size:50px;font-family: "a시월구일2",sans-serif;padding: 30px 32px 22px 46px;color:grey}
img{border-radius:10px;}
footer {bottom: 0px;height: 3rem;background-color: #ccc;width: 100%;text-align: center;line-height: 3rem;}
</style>
</head>
<body>
<div class="moviewrap">
	<div class="container community1">
		<section class="main-section">
			<strong>&#34;${word}&#34;의 검색결과</strong><hr>
			<div class="well">
				<form method="GET" class="ffff" action="MovieSearch.io" onsubmit="return validate(this);">
					<input type="text" name="movie_title" class="ff" placeholder="영화 제목을 입력하세요. "  onkeyup="noSpaceForm(this);" onchange="noSpaceForm(this);"  required>
					<input type="submit" class="button-5 cowrt" value="검색">
				</form>
			</div>
			<table class="searchff">
				<c:if test="${ empty total && empty movie.title }">
					<tr>
						<td id="serr" colspan="5">영화 제목를 입력하세요.</td>
					</tr>
				</c:if>

				<c:if test="${ not empty total && total == 0 && empty movie.title }">
					<tr>
						<td id="serr" colspan="5">검색 결과가 없습니다.</td>
					</tr>
				</c:if>
				
				<!-- db 검색결과 -->
				
				<c:if test="${ not empty movie.title}">
				<tr><td style="padding-bottom:30px">
				 <h3 style="padding-left:20px;font-family:'a시월구일2';"><i class="fa fa-caret-down" style="color:black"></i>&nbsp;가장 근접한 검색 결과</h3>
				<div style="float:left;text-align : center;">
							 <a href='MovieDetail.io?title=${movie.title}&mdate=${movie.mdate}'>
							  <c:if test="${movie.poster.length()>=20}">
	  	    							<c:set var='src' value='${movie.poster}' />
	  	  					  </c:if>
	  	   					  <c:if test="${movie.poster.length()<20}">
	  	   								<c:set var='src' value='${"movieupload/"}${movie.poster}'/>
	  	   					</c:if>
							 <img style="width:150px;height:210px;margin:30px 35px 10px 35px;" src="${src}"></a>
							 <br><span style="font-size:13px;margin:0 auto;">&lt;${movie.title}&gt;</span>
							 <br><span style="font-size:12px;margin:0 auto;color:grey">${movie.mdate}</span>
				</div>
				</td></tr>
				<br>
				</c:if>
				
				<!-- 네이버 api 검색결과 -->
				<c:if test="${ not empty total && total > 0 }">
				<tr style="border-top:1px solid silver;"><td>
					<c:forEach items="${ list }" var="items">
					  <c:if test="${ items.pubDate != movie.mdate }">
							<div class="gomovie" style="float:left;text-align : center;">
								
							<a href='MovieDetail.io?title=${items.title.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "")}&mdate=${ items.pubDate }'>
							<c:if test="${ not empty items.image }">
							   <img style="width:150px;height:210px;margin:30px 35px 10px 35px;" src="${ items.image }">
							</c:if>
							<c:if test="${ empty items.image }">
							   <img style="width:150px;height:210px;margin:30px 35px 10px 35px;" src="image/mymainimg/noimage.png">
							</c:if>
							</a>
							<br>
							<c:if test="${items.title.length()>=16}">
	  	    					<span style="font-size:13px;margin:0 auto;">&lt;${ items.title.substring(0,16) }…</span>
	  	  					 </c:if>
							<c:if test="${items.title.length()<16}">
	  	    					<span style="font-size:13px;margin:0 auto;">&lt;${ items.title }&gt;</span>
	  	  					 </c:if>
							
							<br><span style="font-size:12px;margin:0 auto;color:grey">${ items.pubDate }</span>
							</div>
						</c:if>
					</c:forEach>
					</td></tr>
				</c:if>
			
			</table>
		</section>
	</div>
</div>
	<!-- 하단 -->
<jsp:include page="../mainpage/upup.jsp"/>
<footer>
	<jsp:include page="../mainpage/bottom.jsp"/>
</footer>
 <script>
 function noSpaceForm(obj) { // 공백사용못하게
	    var str_space = /\s/;  // 공백체크
	    if(str_space.exec(obj.value)) { //공백 체크
	        obj.value = obj.value.replace(' ',''); // 공백제거
	        return false;
	    }
	}
 </script>
</body>
</html>