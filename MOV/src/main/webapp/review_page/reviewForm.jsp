<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/reviewForm.css">
<script src="./js/bootstrap.min.js"></script>
<script src="./js/jquery.js"></script>
<script src="./js/reviewForm.js?ver=1"></script>
<meta charset="UTF-8">
<title>Document</title>

<style>
</style>

</head>

<body>
	<div class="container">
		<h3>리뷰 등록 페이지</h3>
		<form action="ReviewAddAction.com" method="post">
			<input type="hidden" value="${review_id }" name="review_id">
			<input type="hidden" value="${review_move_name }" name="review_move_name">
			<div class="form-group mb-3">
				<label for="review_title">리뷰 제목</label>
				<input type="text" class="form-control" id="review_title" name="review_title" placeholder="Enter Title">
			</div>
			<div class="form-group mb-3">
				<label class="col-12">리뷰 별점</label>
				<input type="radio" class="btn-check" name="review_star" id="star1" autocomplete="off" value="1">
				<label class="btn btn-outline-success" for="star1">1점</label>

				<input type="radio" class="btn-check" name="review_star" id="star2" autocomplete="off" value="2">
				<label class="btn btn-outline-success" for="star2">2점</label>

				<input type="radio" class="btn-check" name="review_star" id="star3" autocomplete="off" value="3">
				<label class="btn btn-outline-success" for="star3">3점</label>

				<input type="radio" class="btn-check" name="review_star" id="star4" autocomplete="off" value="4">
				<label class="btn btn-outline-success" for="star4">4점</label>

				<input type="radio" class="btn-check" name="review_star" id="star5" autocomplete="off" value="5">
				<label class="btn btn-outline-success" for="star5">5점</label>
			</div>
			<div class="form-group mb-3">
				<label for="review_comment" class="col-12">리뷰 내용</label>
				<textarea class="form-control" name="review_content" id="review_content" cols="30" rows="10" class="col-12"></textarea>
			</div>

			<div class="d-grid gap-2 d-md-flex justify-content-md-end">
				<input class="btn btn-secondary me-md-2" type="submit" value="리뷰등록">
				<button class="btn btn-light" type="button">취소</button>
			</div>
		</form>
	</div>

</body>

</html>