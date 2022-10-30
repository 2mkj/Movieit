<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>main_preview</title>
<link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/navbars/">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/mymaincss/main_preview.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Dodum&family=Noto+Sans+KR:wght@400&display=swap" rel="stylesheet">
<link rel="stylesheet" href="//cdn.jsdelivr.net/font-nanum/1.0/nanumbarungothic/nanumbarungothic.css">
<script src="js/jquery-3.6.0.js"></script>
<script>
$(function() {
	var today = new Date();
	var mday = new Date(today.setDate(today.getDate() - 1));
	var year = today.getFullYear();
	var month = ('0' + (today.getMonth() + 1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);
	var dateString = year + '-' + month + '-' + day
	//날짜 넣기
	let d = dateString;//YYYY-MM-dd
	const regex = /-/g;
	let d_str = d.replace(regex, "")//YYYYMMdd 
	let url = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&itemPerPage=5&targetDt="
			+ d_str
         $.getJSON(url, function(data) {
             let movieList = data.boxOfficeResult.dailyBoxOfficeList;
             $("#boxoffice").empty();
             for(let i in movieList){
                 $("#boxoffice").append(
                		 "<td><a href='MovieDetail.io?title="+movieList[i].movieNm+"&mdate="+movieList[i].openDt.substring(0,4)+"'>"
                		 +"<img src='image/movie/"+movieList[i].movieCd+".jpg' style='width:150px;height:210px;margin: 2px auto 12px auto;border-radius:10px' ></a>"
                		 +"<br><span style='font-size:17px;font-weight:bolder'>"+(parseInt(i)+1)+"</span><span style='font-size:15px;font-weight:bolder;padding:10px'>"+movieList[i].movieNm+"</span></td>");
             }
            });
});//ready
</script>
</head>
<body>
 <div class="container marketing">
    <div class="row">
  
<!-- 박스오피스 TOP 1-5위 -->
  <div style="height:346px;">
   <div>
   <img src="${pageContext.request.contextPath}/image/mymainimg/hitfilm.png" width=44 height=44 id="film_icon">
   <p class="mainsub m1"> 박스오피스 TOP 1-5위</p>
   </div>
   <div class="table-box" style="height:300px;overflow-y: hidden">
<div class="loading">
      <span></span>   
      <span></span>
      <span></span>
    </div>
<table  class="table table--min" style="margin:0">
<tr id="boxoffice" ></tr>
</table>
    </div>
    </div>

<!-- 추천 영화 -->
 <div style="height:346px;">
   <div>
   <img src="${pageContext.request.contextPath}/image/mymainimg/bestfilm.png" width=44 height=44 id="film_icon">
   <p class="mainsub m1"> 추천영화</p>
   </div>
   <div class="table-box" style="height:300px;overflow-y: hidden">
<table  class="table table--min" style="margin:0">
<tr>
<c:forEach var="z" items="${zzim}" begin="0" end="4">
	  <td>
	  	 <a href="MovieDetail.io?title=${z.zzim_title}&mdate=${z.zzim_date}">
	  	   <c:if test="${z.zzim_poster.length()>=20}">
	  	    <c:set var='src' value='${z.zzim_poster}' />
	  	   </c:if>
	  	   <c:if test="${z.zzim_poster.length()<20}">
	  	   	<c:set var='src' value='${"movieupload/"}${z.zzim_poster}'/>
	  	   </c:if>
	  	   <img class="poster" src="${src}"  style='width:150px;height:210px;margin: 2px auto 12px auto;border-radius:10px'></a>
	  	 	<br><b style="font-size:17px">
	  	 	<c:if test="${z.zzim_title.length()>=10}">
	  	     <span style='font-weight:bolder;padding:10px'><c:out value="${z.zzim_title.substring(0,10)}…" /></span>
	  	   </c:if>
	  	   <c:if test="${z.zzim_title.length()<10}">
	  	     <span style='font-weight:bolder;padding:10px'><c:out value="${z.zzim_title}" /></span>
	  	   </c:if>
	  	 	</b>
	  	 	<br>
	  	 </td>
	  </c:forEach>
</tr>
    </table>
    </div>
    </div>
	
<!-- 커뮤니티 -->
   <div style=" width:8000px; height:400px;border-radius: 30px;">
   <div>
   <img src="${pageContext.request.contextPath}/image/mymainimg/commu.png" width=40 height=40 id="commu_icon">
   <p class="mainsub m2">커뮤니티</p>
   </div>
   <div class="table-box">
    <table class="table--min commu">
    
    
    <c:set var="num" value="${listcount-(page-1)*limit}" />
     <c:forEach var="b" items="${boardlist}">
     <tr>
      <td><%-- 번호 --%>
       <c:out value="${num}"/><%--출력 --%>
       <c:set var="num" value="${num-1}" /> <%--num=num-1;의미 --%>
	  </td>
	 <td><%--제목 --%>
	  	 <a href="CommunityDetail.co?num=${b.board_num}">
	  	   <c:if test="${b.board_subject.length()>=20}">
	  	     <c:out value="${b.board_subject.substring(0,20)}..." />
	  	   </c:if>
	  	   <c:if test="${b.board_subject.length()<20}">
	  	     <c:out value="${b.board_subject}" />
	  	   </c:if>
	  	 </a>[${b.cnt}]&nbsp;<span class="blink">NEW</span>
	  </td>
	  <td><div>${b.board_user_name}</div></td>
	  <td><div>${b.board_date}</div></td>
	  <td><div>${b.board_readcount}</div></td>
	  </tr>
	  </c:forEach>
    </table>
    </div>
   </div>
    </div><!-- /.row -->
</div>
<script>
$(function() {
	$('.loading').delay(2500).fadeOut('slow');
});
</script>
</body>
</html>