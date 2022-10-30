<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<script src="./js/bootstrap.min.js"></script>
<title>영화 상세페이지</title>
<script src="http://code.jquery.com/jquery-3.6.0.min.js"></script>
<jsp:include page="../mainpage/top.jsp" />
<link href="${pageContext.request.contextPath}/css/movie/movieForm.css" rel="stylesheet">
<style>
.profilep{width:200px;height:300px}
</style>
</head>
<body>
	<form action="MovieAddAction.io" method="post"  enctype="multipart/form-data" >
	<div class="container">
		<h3>${word} - 등록 페이지 </h3>
		<div class="row">
	
	<!-- 포스터 -->
	<c:forEach items="${ list }" var="items" begin="0" end="0">
			<div class="col-5">
	  <span id="filename" style="display:none">${movie.poster}</span>
	  <span id="showImage">
	    <c:if test='${empty movie.poster && empty items.image}'>
	     <c:set var='src' value='image/mymainimg/noimage.png'/>
	    </c:if>
	    <c:if test='${empty movie.poster && !empty items.image}'>
	     <c:set var='src' value='${items.image}'/>
	    </c:if>
	    <c:if test= '${!empty movie.poster && !empty items.image}'>
	     <c:set var='src' value='${"movieupload/"}${movie.poster}'/>
	    </c:if>
		<img class="profilep" src="${src}" width="20px" alt="poster">
	  </span><br>
		<input type="file" name="poster" accept="image/*">
			
			</div>
	
	<!-- 영화 정보 -->
			<div class="col-lg">
				<div class="d-flex flex-column justify-content-between">
					<div class="col ">
						<label for="title" class="form-label ">영화제목</label>
						<div class="title"><input type="text" id="minput" name="title" value="${word}"></div>
					</div>
					<div class="col ">
						<label for="mdate" class="form-label">개봉년도</label>
						<div class="mdate"><input type="text" id="minput" name="mdate" value="${ items.pubDate }"></div>
					</div>
					<div class="col ">
						<label for="director" class="form-label">영화감독</label>
						<div class="director"><input type="text" id="minput" name="director" value="${ items.director}"></div>
					</div>
					<div class="col ">
						<label for="actor" class="form-label">출연진</label>
						<div class="actor"><input type="text" id="minput" name="actor" value="${ items.actor }"></div>
					</div>
				</div>
			</div>
</c:forEach>
			<div class="col-12 mt-3">
				<div class="col">
					<label for="content" class="form-label">영화 줄거리</label>
					<div class="content"><textarea class="inputcon" name="content"></textarea></div>
				</div>
			</div>
			<div class="mbtndiv">
<input class="mbtn" type="submit" value="영화등록">
		</div>

		</div>
	</div>
</form>
<script>
$("input:file").change(function() {
	var inputfile = $(this).val().split('\\');
	var filename = inputfile[inputfile.length - 1];
	
	var pattern = /(gif|jpg|jpeg|png)$/i; //i(ignore case)는 대소문자 무시를 의미
	
	if(pattern.test(filename)){
		$('#filename').text(filename);
		
		var reader = new FileReader(); // 파일을 읽기 위한 객체 생성
		reader.readAsDataURL(event.target.files[0]);
		reader.onload = function(){//읽기에 성공했을 때 실행되는 이벤트 핸들러
		$('#showImage > img').attr('src',this.result);
		};
	}else{
		alert('이미지 파일(gif,jpg,jpeg,png)이 아닌 경우는 무시됩니다.');
		$('#filename').text('');
		$('#showImage > img').attr('src','image/mymainimg/noimage.png');
		$(this).val(''); //안하면 내가 선택한 파일이 올라가버림
	}
  });//change()
</script>
</body>

</html>