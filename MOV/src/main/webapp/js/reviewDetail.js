window.onpopstate = function(event) {
	// "event" object seems to contain value only when the back button is clicked  
	// and if the pop state event fires due to clicks on a button  
	// or a link it comes up as "undefined"   
	if (event) {
		// Code to handle back button or prevent from navigation  
	}
	else {
		// Continue user action through link or button  
	}
}

$(document).ready(function() {
	getCommentList(page);
	//페이징 이벤트 추가
	$(document).on('click', '.next_page_button', function() {
		console.log("asdfasdf");
		getCommentList(page + 1);
	});
	$(document).on('click', '.previous_page_button', function() {
		getCommentList(page - 1);
	});

	$(document).on('click', '.select_page_button', function() {
		let number = $(this).text().trim();
		getCommentList(number);
	})
});

$(function() {
	$('#showComment').on('click', buttonHideAndShow);
	$('#showComment').on('click', function() {
		getCommentList(page);

	});

	//$(document).on('click', '.modifiyButton', modifiyComment);
	$(document).on('click', '.deleteButton', deleteComment);
	$(document).on('click', '.deleteSubmitButton', deleteSubmitComment);

	$(document).on('click', '.modifiyButton', modifiyComment);
	$(document).on('click', '.modifiySubmitButton', modifiySubmitComment);
});


let check = 0;
function buttonHideAndShow() {
	if (check == 1) {
		$('#showComment').text('댓글 보기');
		$('#commentArea').hide();
		check = 0;
	} else {
		$('#showComment').text('댓글 숨기기');
		$('#commentArea').show();
		check = 1;
	}
}

//댓글을 삭제하는 함수들 모임
let comment_area;
let comment_area_number;
let comment_password;

function deleteComment() {
	$('#delete_index').attr('class', $(this).parent().parent().attr('id'));
	comment_area = $(this).parent().parent();
	comment_area_number = comment_area.attr('id');

	console.log(comment_area);
	console.log(comment_area_number);
}

function deleteSubmitComment() {
	comment_password = $('.delete_input_password').val();
	$.ajax({
		url: "CommentDeleteAction.re",
		method: "post",
		dataType: 'json',
		data: {
			comment_number: comment_area_number,
			comment_password: comment_password,
			json_state: "json"
		},
		success: function(rdata) {
			alert(rdata['message_ajax']);

			if (rdata['result_delete'] > 0) {
				$('.deleteCancelButton').click();
				comment_area.remove();
			}

		},
		error: function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		},
	});
}
//댓글 삭제 함수 종료

//댓글 수정 함수 모임
let comment_modifiy_content;
let comment_content_area;

function modifiyComment() {
	$('#modifiy_index').attr('class', $(this).parent().parent().attr('id'));
	comment_area = $(this).parent().parent();
	comment_area_number = comment_area.attr('id');
	comment_content_area = $(this).parent().parent().children('.comment_content_area');
	console.log(comment_area);
	console.log(comment_area_number);
}
function modifiySubmitComment() {
	comment_password = $('.modifiy_input_password').val();
	comment_modifiy_content = $('.modifiy_input_content').val();
	$.ajax({
		url: "CommentModifiyAction.re",
		method: "post",
		dataType: 'json',
		data: {
			comment_number: comment_area_number,
			comment_password: comment_password,
			comment_modifiy_content: comment_modifiy_content,
			json_state: "json"
		},
		success: function(rdata) {
			alert(rdata['message_ajax']);
			console.log(rdata);
			console.log(rdata['get_modifiy_comment']['comment_content']);
			if (rdata['result_modifiy'] > 0) {
				$('.modifiyCancelButton').click();
				comment_content_area.text(rdata['get_modifiy_comment']['comment_content']);
			}

		},
		error: function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		},
	});
}
//댓글 수정 함수 종료

//댓글 리스트 가져오기
let check_get_list = false;
let page = 1;
function getCommentList(nowPage) {

	$.ajax({
		url: "CommentListAction.re",
		method: "post",
		dataType: 'json',
		data: {
			nowPage: nowPage,
			json_state: "json"
		},
		success: function(rdata) {
			console.log("데이터 도착");
			console.log({ rdata });
			let comment_list = rdata["comment_list"];
			let write = "";
			page = rdata['nowPage'];

			//let str = JSON.stringify(rdata); // <> parse()
			//alert(str);
			if (comment_list == null) {
				write = "등록된 댓글이 존재하지 않습니다.";
				write += "<hr>";
				write += createCommentAddFormItem();
			} else {
				for (var i = 0; i < comment_list.length; i++) {
					console.log(comment_list[i]["comment_number"]);
					write += "<div id=" + comment_list[i]["comment_number"] + " class='mb-3 card col-12'>"
						+ "<div class='row justify-content-md-start mb-1'>"
						+ "<div class='col-10'>" + comment_list[i]["comment_id"] + "</div>"
						+ "<div class='modifiyButton btn btn-sm btn-primary col-auto' style='text-align: center' data-bs-toggle='modal' data-bs-target='#exampleModal2'> 수정</div>"
						+ "&nbsp;"
						+ "<button class='deleteButton btn btn-sm btn-danger col-auto' style='text-align: center' data-bs-toggle='modal' data-bs-target='#exampleModal'>삭제</button>"
						+ "</div>"
						+ "<hr style = 'margin-top: 0px' >"
						+ "<div class='comment_content_area'> " + comment_list[i]["comment_content"] + "</div > "
						+ "</div>"
				}

				write += createReviewPagingItem(rdata);
				write += createCommentAddFormItem();
			}

			document.getElementById('commentArea').innerHTML = write;
			//$("#commentArea").prepend(write);
		},
		error: function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		},
	});
}

//리뷰 페이징 버튼 생성
function createReviewPagingItem(rdata) {

	let output2 = "<nav aria-label='Page navigation example'>";
	output2 += "<ul class='pagination justify-content-center'>";
	output2 += "<li class='page-item'>";

	if (rdata['nowPage'] <= 1) {
		output2 += "<button class='page-link text-black-50' aria-label='Previous'>";
		output2 += "<span aria-hidden='true'>&laquo;</span>";
		output2 += "</button>";
	} else {
		output2 += "<button  type='button' class='previous_page_button page-link' aria-label='Previous'>";
		output2 += "<span aria-hidden='true'>&laquo;</span>";
		output2 += "</button>";
	}
	output2 += "</li>";

	for (let i = 0; i < rdata['endPage']; i++) {
		output2 += "<li class='page-item'>";
		if (rdata['nowPage'] == (i + 1)) {
			output2 += "<button type='button' class='select_page_button btn btn-primary'>" + (i + 1) + "</button>";
		} else {
			output2 += "<button  type='button' class='select_page_button btn'>" + (i + 1) + "</button > ";
		}
		output2 += "</li>"
	}

	output2 += "<li class='page-item'>";
	if (rdata['nowPage'] >= rdata['endPage']) {
		output2 += "<button type='button' class='page-link text-black-50' aria-label='Next'>";
		output2 += "<span aria-hidden='true'>&raquo;</span>";
		output2 += "</button>";
	} else {
		output2 += "<button  type='button' class='next_page_button page-link" + " aria-label='Next'>";
		output2 += "<span aria-hidden='true'>&raquo;</span>";
		output2 += "</button>";
	}
	output2 += "</li></ul></nav>";
	return output2;
}

function createCommentAddFormItem() {
	let write = "";
	write += "<form action='CommentAddAction.re' method='post'>";
	write += "<input type='hidden'  name='review_id' value='${ review.review_id }'>";
	write += "<input type='hidden' name='review_move_name' value='${ review.review_move_name }'>";
	write += "<div class='form-group mb-3'>";
	write += "<label for='comment' class='col-12'>리뷰 내용</label>";
	write += "<textarea class='form-control' name='comment_content' id='comment_content' cols='30' rows='5' class='col-12'></textarea>";
	write += "</div>";
	write += "<div class='form-group mb-3'>";
	write += "<label for='comment_password'>비밀번호</label>";
	write += "<input type='text' class='form-control' id='comment_password' name='comment_password' placeholder='Enter Title'>";
	write += "</div>";
	write += "<input class='btn btn-primary' type='submit'>";
	write += "</form>";
	return write;
}