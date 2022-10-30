<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>영화 상세페이지</title>
<script src="js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="js/movieDetail.js"></script>
<jsp:include page="../mainpage/top.jsp" />
<link href="${pageContext.request.contextPath}/css/movie/movieDetail.css" rel="stylesheet">
<link rel="stylesheet" href="./css/review.css">
</head>
<body>
	<form id="comm" method="post" name="form">
		<div class="wrapp">
			<div class="container community1">
				<div class="row">
					<!-- 영화 정보 -->
					<c:forEach items="${ list }" var="items" begin="0" end="0">
	<input type="hidden" id="email" name="email"
		value="${memberinfo.email}">
	<input type="hidden" id="user_name" name="user_name"
		value="${memberinfo.user_name}">
	<!-- 타이틀값 -->
	<input type="hidden" name="title" value="${word}">
	<!-- 감독 -->
	<c:if test="${ not empty movie.director }">
		<input type="hidden" name="director" value="${movie.director}">
	</c:if>
	<c:if test="${empty movie.director }">
		<input type="hidden" name="director" value="${items.director}">
	</c:if>
	<!-- 배우 -->
	<c:if test="${ not empty movie.actor }">
		<input type="hidden" name="actor" value="${movie.actor}">
	</c:if>
	<c:if test="${empty movie.actor }">
		<input type="hidden" name="actor" value="${items.actor}">
	</c:if>
	<!-- 날짜 -->
	<c:if test="${ not empty movie.mdate }">
		<input type="hidden" name="mdate" value="${movie.mdate}">
	</c:if>
	<c:if test="${empty movie.mdate }">
		<input type="hidden" name="mdate" value="${items.pubDate}">
	</c:if>
	<!-- 포스터 -->
	<c:if test="${ not empty movie.poster }">
		<input type="hidden" name="poster" value="${movie.poster}">
	</c:if>
	<c:if test="${empty movie.poster }">
		<input type="hidden" name="poster" value="${items.image}">
	</c:if>
	<input type="hidden" name="num" value="${zzim.zzim_num}">

	<!-- 포스터 -->
	<div class="col-4">
		<span id="showImage"> <c:if
				test="${ empty items.image && empty movie.poster }">
				<c:set var='src' value='image/mymainimg/noimage.png' />
			</c:if> <c:if test="${ not empty items.image && empty movie.poster  }">
				<c:set var='src' value='${items.image}' />
			</c:if> <c:if test="${ not empty movie.poster  }">
				<c:if test="${movie.poster.length()>=20}">
					<c:set var='src' value='${movie.poster}' />
				</c:if>
				<c:if test="${movie.poster.length()<20}">
					<c:set var='src' value='${"movieupload/"}${movie.poster}' />
				</c:if>
			</c:if> <img class="profilep" src="${src}" alt="여기는 영화 이미지"
						 style="width: 220px;height:304px;border-radius:10px;margin-left:25px" class="shadow bg-body rounded">
		</span>
	</div>

	<!-- 영화 정보 -->
	<div class="col-lg">
		<div class="d-flex flex-column justify-content-between">
			<div class="col title">
				<c:if test="${ not empty movie.title }">
					<h2 class="title1">${movie.title}</h2>
				</c:if>
				<c:if test="${empty movie.title }">
					<h2 class="title1">${ items.title }</h2>
				</c:if>
			</div>
			
			<div class="col ">
			<span id="mlabel">개봉&emsp;&nbsp;&nbsp;<span id="sero">|</span>&emsp;</span>
				<c:if test="${ not empty movie.mdate }">
					<span class="minfo mdate">${movie.mdate }</span>
				</c:if>
				<c:if test="${empty movie.mdate }">
					<span class="minfo mdate">${ items.pubDate }</span>
				</c:if>
			</div>
			
			<div class="col ">
			<span id="mlabel">감독&emsp;&nbsp;&nbsp;<span id="sero">|</span>&emsp;</span>
				<c:if test="${ not empty movie.director }">
					<span class="minfo director">${movie.director }</span>
				</c:if>
				<c:if test="${empty movie.director }">
					<c:if test="${items.director.length()>=30}">
						<span class="minfo director">${ items.director.replaceAll("[|]", " | ").substring(0,30)}…</span>
					</c:if>
					<c:if test="${items.director.length()<30}">
						<span class="minfo director">${ items.director.replaceAll("[|]", " | ") }</span>
					</c:if>
				</c:if>
			</div>
			<div class="col ">
				<span id="mlabel">배우&emsp;&nbsp;&nbsp;<span id="sero">|</span>&emsp;</span>
				<c:if test="${ not empty movie.actor }">
					<span class="minfo actor">${movie.actor }</span>
				</c:if>
				<c:if test="${empty movie.actor }">
				 	<c:if test="${items.actor.length()>=38}">
						<span class="minfo actor">${ items.actor.replaceAll("[|]", " | ").substring(0,38)}…</span>
					</c:if>
					<c:if test="${items.actor.length()<38}">
						<span class="minfo actor">${ items.actor.replaceAll("[|]", " | ") }</span>
					</c:if>
				</c:if>
			</div>
			
			<div class="col ">
				<span id="mlabel">별점&emsp;&nbsp;&nbsp;<span id="sero">|</span>&emsp;</span>
				<span><i class="fa fa-star"></i></span>
			</div>
			<div class="col zzim">
				<span id="mlabel">찜하기&nbsp;&nbsp;<span id="sero">|</span>&emsp;</span>
					<c:if test="${empty zzim.zzim_title}">
						<button type="submit" id="zzim"
							onclick="javascript:form.action='myZzimAdd.net';">
							<i class='fa fa-heart-o'></i>
						</button>
					</c:if>
					<c:if test="${!empty zzim.zzim_title}">
						<button type="submit" id="zzim"
							onclick="javascript:form.action='myZzimDelete.net';">
							<i class='fa fa-heart'></i>
						</button>
					</c:if>
			</div>
			<div class="col zzim1">
			</div>
		</div>
	</div>
</c:forEach>
<div class="col-12 mt-3">
	<div class="col">
		<div class="content" style="white-space: pre-wrap;">${ movie.content }</div>
	</div>
</div>
					
					<!-- 관리자만 등록 & 편집 가능 -->
					<div class="addmovie">
					<c:if test="${email=='admin' && empty movie.title}">
						<input class="moviebtn" type="submit" onclick="javascript: form.action='MovieAddView.io?title=${word}&mdate=${mdate}';" value="영화등록">
					</c:if>
					<c:if test="${email=='admin' && not empty movie.title }">
						<input class="moviebtn" type="submit" onclick="javascript: form.action='MovieModifyView.io?title=${word}&mdate=${mdate}';" value="영화편집">
					</c:if>
					</div>
					
					<hr>
					<!-- 리뷰 아이템 리스트가 등록되는곳. moveDetail.js -->
					<input type="hidden" id="user_id" value="${memberinfo.email}">
					<div class="col-12">
						<div class="col">
							<table class="table" id="review_item_area">

							</table>

							<div class="d-grid gap-2 d-md-flex justify-content-md-end">
								<!-- 	<input id="review_add_button" class="btn btn-primary" type="submit" onclick="javascript: form.action='ReviewAddForm.com';" value="리뷰작성"> -->
								<input id="review_add_button" class="btn btn-primary" type="button" value="리뷰작성">
							</div>

							<div id="review_pageitem_area">
							
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<!-- 하단 -->
	<jsp:include page="../mainpage/upup.jsp" />
	<footer>
		<jsp:include page="../mainpage/bottom.jsp" />
	</footer>
</body>
</html>