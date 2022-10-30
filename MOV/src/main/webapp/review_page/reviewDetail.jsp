<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/reviewDetail.css?ver=1">
<script src="./js/bootstrap.min.js"></script>
<script src="./js/jquery-3.6.0.js"></script>
<script src="./js/reviewDetail.js?ver=3"></script>
<meta charset="UTF-8">
<title>Document</title>

<style>
</style>

</head>

<body>
	<div class="container">
		<h2>상세 리뷰 페이지</h2>
		<div class="mb-3 card col-12">
			<div class="review_move_name">${review.review_move_name }</div>
			<hr>
			<div class="fs-4">${review.review_title }</div>
			<div>
				<nav aria-label="breadcrumb">
					<ol class="breadcrumb">
						<li class="breadcrumb-item review_id">${review.review_id }</li>
						<li class="breadcrumb-item active" aria-current="page">${review.review_date}</li>
						<li class="breadcrumb-item active" aria-current="page">${review.review_star}</li>
					</ol>
				</nav>
			</div>
		</div>

		<div class="mb-3 card col-12">
			<div class="fs-8">${review.review_content}</div>
			<hr>
			<div class="d-md-flex justify-content-md-end">
				<button id="showComment" class="btn btn-primary" type="button">댓글보기</button>
			</div>
		</div>

		<div id="commentArea" class="card col-12" style='display: none'>
			<form action="CommentAddAction.re" method="post">
				<input type="hidden" name="review_id" value="${review.review_id }">
				<input type="hidden" name="review_move_name" value="${review.review_move_name }">
				<div class="form-group mb-3">
					<label for="comment" class="col-12">리뷰 내용</label>
					<textarea class="form-control" name="comment_content" id="comment_content" cols="30" rows="5" class="col-12"></textarea>
				</div>
				<div class="form-group mb-3">
					<label for="comment_password">비밀번호</label>
					<input type="text" class="form-control" id="comment_password" name="comment_password" placeholder="Enter Title">
				</div>
				<input class="btn btn-primary" type="submit">
			</form>
		</div>
	</div>

	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<input type="hidden" id="delete_index" class="">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-body">
					<form>
						<div class="mb-3">
							<label for="delete_input_password" class="col-form-label">비밀번호</label>
							<input type="text" class="delete_input_password form-control" id="delete_input_password" name="delete_input_password">
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="deleteCancelButton btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button type="button" class="deleteSubmitButton btn btn-primary">Send message</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<input type="hidden" id="modifiy_index" class="">
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-body">
					<form>
						<div class="mb-3">
							<label for="modifiy_input_content" class="col-form-label">수정할 내용</label>
							<textarea class="modifiy_input_content form-control" id="modifiy_input_content" name="modifiy_input_content"></textarea>
						</div>

						<div class="mb-3">
							<label for="modifiy_input_password" class="col-form-label">비밀번호</label>
							<input type="text" class="modifiy_input_password form-control" id="modifiy_input_password" name="modifiy_input_password">
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="modifiyCancelButton btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button type="button" class="modifiySubmitButton btn btn-primary">Send message</button>
				</div>
			</div>
		</div>
	</div>
</body>
</body>

</html>