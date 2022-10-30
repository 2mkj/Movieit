<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>무빗!- 영화 목록</title>
<jsp:include page="../mainpage/top.jsp"/>
<script src="http://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="${pageContext.request.contextPath}/css/movie/movielist.css" rel="stylesheet">
<script>
	$(function() {
		var today = new Date();
		var mday = new Date(today.setDate(today.getDate() - 1));
		var year = today.getFullYear();
		var month = ('0' + (today.getMonth() + 1)).slice(-2);
		var day = ('0' + today.getDate()).slice(-2);
		var dateString = year + '-' + month + '-' + day
		console.log("▼ "+dateString+" 일자 박스오피스 순위");
		//날짜 넣기
		let d = dateString;//YYYY-MM-dd
		const regex = /-/g;
		let d_str = d.replace(regex, "")//YYYYMMdd 

		let url = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt="
				+ d_str
				$.getJSON(url, function(data) {
                    let movieList = data.boxOfficeResult.dailyBoxOfficeList;
                    $(".moo").empty();
                    for(let i in movieList){
                    	 $(".moo").append(
                    			 "<div style='float:left;text-align:left'>"
                    			+"<a href='MovieDetail.io?title="+movieList[i].movieNm+"&mdate="+movieList[i].openDt.substring(0,4)+"'>"
                    			+"<img class='poster' src='image/movie/"+movieList[i].movieCd+".jpg' style='width:190px;height:270px;margin:35px 32px 16px 32px;' ></a>"
                    			+"<input name='title' type='hidden' value='"+movieList[i].movieNm+"'>"
                    			+"<input name='mdate' type='hidden' value='"+movieList[i].openDt.substring(0,4)+"' >"
                    			+"<br><span style='font-size:15px;display:block'><strong>"+(parseInt(i)+1)+"</strong>"+movieList[i].movieNm.substring(0,13)+"</span></div>");
                         console.log(movieList[i].movieNm+":"+movieList[i].movieCd);
                    }
               });
	
	});
</script>
</head>
<body>
<form id="comm">
<div class="loading">
      <span></span>   
      <span></span>
      <span></span>
    </div>
    
    <!-- 최신 박스 오피스 -->
<div class="moviewrap">
<div class="container community1">
<strong id='boxh'><i class='fa fa-film'></i> 최신 박스 오피스 순위</strong><br><hr>
	
	<div class="moo" style="magin:0 auto">
	</div>
	
	<!-- 추천 영화 리스트 -->
<div class="zzimlist" id='boxh'>
<strong id='boxh'><i class='fa fa-star'></i> 무빗! 추천 영화</strong><br><hr></div>
	<c:forEach var="z" items="${zzim}" begin="0" end="14">
<div style='float:left;text-align:center'>
	  	 <a href="MovieDetail.io?title=${z.zzim_title}&mdate=${z.zzim_date}">
	  	   <c:if test="${z.zzim_poster.length()>=20}">
	  	    <c:set var='src' value='${z.zzim_poster}' />
	  	   </c:if>
	  	   <c:if test="${z.zzim_poster.length()<20}">
	  	   	<c:set var='src' value='${"movieupload/"}${z.zzim_poster}'/>
	  	   </c:if>
	  	   <img class="poster" src="${src}"   style='width:190px;height:270px;margin:35px 32px 16px 32px;'></a>
	  	 	<br><b style="font-size:17px">
	  	 	<c:if test="${z.zzim_title.length()>=10}">
	  	     <span style='font-size:15px;display:block'><c:out value="${z.zzim_title.substring(0,10)}…" /></span>
	  	   </c:if>
	  	   <c:if test="${z.zzim_title.length()<10}">
	  	     <span style='font-size:15px;display:block'><c:out value="${z.zzim_title}" /></span>
	  	   </c:if>
	  	 	</b>
	  	 	<span style='font-size:15px;color:#292929'><c:out value="${z.zzim_date}" /></span>
	  	 </div>
	  </c:forEach>
</div>
</div>
</form>
<!-- 하단 -->
<jsp:include page="../mainpage/upup.jsp"/>
<footer>
	<jsp:include page="../mainpage/bottom.jsp"/>
</footer>
<script>
$(function() {
	$('.loading').delay(1500).fadeOut('slow');
});
</script>
</body>
</html>