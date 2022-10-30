$(document).ready(function() {
	getReviewList(page);

	$(document).on('click', '.deleteButton', function() {
		deleteCheck($(this));
	});

	$(document).on('click', '.modifiyButton', function() {
		modifyCheck($(this));
	});

	$(document).on('click', '.next_page_button', function() {
		getReviewList(page + 1)
	});
	$(document).on('click', '.previous_page_button', function() {
		getReviewList(page - 1)
	});

	$(document).on('click', '.select_page_button', function() {
		let number = $(this).text().trim();
		getReviewList(number);
	})
	$(document).on('click', '#review_add_button', function() {
		return addCheck();
	});
});

function addCheck() {
	let now_id = $('#user_id').val();

	if (now_id) {
		location.href = "ReviewAddForm.com";
		return true;
	} else {
		alert("로그인을 하세요.");
	}

	return false;
}


function deleteCheck(button) {
	let review_id = button.attr('class').split(' ')[0];
	let review_number = button.attr('class').split(' ')[1];

	let now_id = $('#user_id').val();

	console.log(review_id + " " + now_id + " " + review_number);

	if (review_id != now_id && now_id != '관리자') {
		alert("작성한 아이디가 아닙니다.");
	} else {
		let check = confirm("삭제하시갰습니까?");
		if (check == true)
			location.href = "ReviewDeleteAction.com?review_number=" + review_number;
	}

	return false;
}

function modifyCheck(button) {

	//글에 적용된 작성자 id
	let review_id = button.attr('class').split(' ')[0];
	let review_number = button.attr('class').split(' ')[1];
	//현재 접속한 유저 아이디
	let now_id = $('#user_id').val();
	//window.open("ReviewModifiyForm.com?user_id="+now_id,"PopupWin","width=500,height=600")

	if (review_id != now_id && now_id != '관리자') {
		//alert("작성한 아이디가 아닙니다.");
	} else {
		let check = confirm("수정하시갰습니까?");
		if (check == true)
			location.href = "ReviewModifiyForm.com?review_number=" + review_number;
	}
	return false;
}

//댓글 리스트 페이징 처리
let page = 1;
function getReviewList(nowPage) {
	$.ajax({
		url: "ReviewListAction.com",
		method: "post",
		dataType: 'json',
		data: {
			nowPage: nowPage,
			ajax_state: "json"
		},
		success: function(rdata) {
			let review_list = rdata['reviewlist'];
			page = rdata['nowPage'];
			console.log("성공s");
			console.log(rdata);
			console.log(rdata['startList']);
			console.log(rdata['endList']);
			console.log(rdata['limitList']);
			console.log(rdata['startPage']);
			console.log(rdata['endPage']);
			console.log(rdata['reviewCount']);
			if (rdata['reviewCount'] == 0) {
				alert("가져온 리뷰가 없습니다.");
				document.getElementById("review_item_area").innerHTML = "<p style='text-align:center'>작성된 리뷰가 없습니다.</p>";
			} else {
				let output = createReviewItem(review_list);
				let output2 = createReviewPagingItem(rdata);
				document.getElementById("review_item_area").innerHTML = output;
				document.getElementById("review_pageitem_area").innerHTML = output2;

			}
		},
		error: function(request, status, error) {
			//alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		},
	});
}
//리뷰 html 생성
function createReviewItem(review_list) {
	let output = "<table class='table' id='review_item_area'>"
		+ "<thead><tr><td>리뷰제목</td><td>리뷰내용</td><td>작성자</td><td>작성일</td><td>별점</td><td>조회수</td></tr></thead>"
		+ "<tbody>";

	for (let i = 0; i < review_list.length; i++) {
		output += "<tr class='reviewItem'>";
		output += "<td>" + review_list[i]['review_title'] + "</td>";
		output += "<td><a href='ReviewDetailAction.com?review_number=" + review_list[i]['review_number'] + "'>" + review_list[i]['review_content'] + "</a></td>";
		output += "<td>" + review_list[i]['review_id'] + "</td>";
		output += "<td>" + review_list[i]['review_date'] + "</td>";
		output += "<td>" + review_list[i]['review_star'] + "</td>";
		output += "<td>" + review_list[i]['review_readcount'] + "</td>";
		output += "<td>";
		output += "<input type='button' class='" + review_list[i]['review_id'] + " " + review_list[i]['review_number'] + " modifiyButton btn btn-sm btn-danger col-auto' style='text-align:center' value='수정'>&nbsp;";
		output += "<input type='button' class='" + review_list[i]['review_id'] + " " + review_list[i]['review_number'] + " deleteButton  btn btn-sm btn-danger col-auto' style='text-align:center' value='삭제'>";
		output += "</td>";
		output += "</tr>";
	}

	output += "</tbody></table>";
	return output;
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

