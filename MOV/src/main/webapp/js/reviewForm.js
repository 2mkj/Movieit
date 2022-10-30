
$(function() {
	function check_blank() {
		let $review_title = $('#review_title');
		let $review_content= $('#review_content');
		let $review_star = $('input[name=review_star]:checked');
		
		if ($.trim($review_title.val()) == "") {
			alert("리뷰 제목을 입력해주세요.");
			$review_title.focus();
			return false;
		}

		if ($.trim($review_content.val()) == "") {
			alert(`리뷰 내용을 입력해주세요. ${$review_title}`);
			$review_content.focus();
			return false;
		}

		if ($review_star.length <=0) {
			console.log({$review_star});
			alert(`별점을 선택해주세요.`);
			return false;
		}


		console.log($review_star.val());
		return true;
	}
	
	$('.cancelButton').on('click', function(){
		location.href="MovieDetail.io";
	});
		
	$('form').submit(function() {
		let check = check_blank();
		return check;
	});
});





