//회원가입 유효성검사
$(function() {
	var checkemail=false;
	var checkpass=false;
	var checkpassok=false;
	var checkusername=false;
	var checkname=false;
	var checkjumin1=false;
	var checkjumin2=false;
	var checkphone=false;
	var checkpost=false;
	var checkaddress=false;
	
	$("#email").on('keyup',
			function() {
				$("#email_message").empty();
				var pattern = /^\w+@\w+[.]\w{3}$/;
				var email = $("#email").val();
				if (email == '') {
					$("#email_message").css('color', 'red').html("이메일을 입력하세요.<br><br>");
					checkemail=false;
				} else if(!pattern.test(email)) {
					$("#email_message").css('color', 'red').html("올바른 이메일 형식이 아닙니다.<br><br>");
					checkemail=false;
				} else {
					//이메일 중복체크
					$.ajax({
						url : "emailcheck.me",
						data : {"email" : email},
						success : function(resp) {
							if (resp == -1) {
								$("#email_message").css('color', 'green').html("사용가능한 이메일입니다.<br><br>");
								checkemail=true;
							} else {
								$("#email_message").css('color', 'red').html("사용중인 이메일입니다.<br><br>");
								checkemail=false;
							}
						}
					});//ajax end
				}
	});//email keyup 이벤트 처리 끝
	
	
	$("#pass").on('keyup',
			function() {
				$("#pass_message").empty();
	
				var pattern = /^[\w`~!@#$%^&*()-_=+]{8,16}$/;
				var pass = $("#pass").val();
				if (pass == '') {
					$("#pass_message").css('color', 'red').html("비밀번호를 입력하세요.<br><br>");
					checkpass=false;
				} else if (!pattern.test(pass)) {
					$("#pass_message").css('color', 'red').html("8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.<br><br>");
					checkpass=false;
				} else {
					checkpass=true;
				}
	});//pass keyup 이벤트 처리 끝
	
	
	$("#pass_check").on('keyup',
			function() {
				$("#passok_message").empty();

				var passok = $("#pass_check").val();
				if ($("#pass").val() == '') {
					$("#passok_message").html("");
					checkpassok=false;
				} else if (passok != $("#pass").val()) {
					$("#passok_message").css('color', 'red').html("비밀번호가 일치하지 않습니다.<br><br>");
					checkpassok=false;
				} else {
					checkpassok=true;
				}
	});//pass_check keyup 이벤트 처리 끝
	
	
	$("#user_name").on('keyup',
			function() {
				$("#username_message").empty();
	
				var pattern = /^[\w가-힣]{2,10}$/;
				var user_name = $("#user_name").val();
				if (user_name == '') {
					$("#username_message").css('color', 'red').html("닉네임을 입력하세요.<br><br>");
					checkusername=false;
				} else if (!pattern.test(user_name)) {
					$("#username_message").css('color', 'red').html("2~10자 한글과 영문 대 소문자, 숫자를 사용하세요. (특수문자 사용 불가)<br><br>");
					checkusername=false;
				} else {
					checkusername=true;
				}
	});//user_name keyup 이벤트 처리 끝
	
	
	$("#name").on('keyup',
			function() {
				$("#name_message").empty();
	
				var pattern = /^[\w가-힣]+$/;
				var name = $("#name").val();
				if (name == '') {
					$("#name_message").css('color', 'red').html("이름을 입력하세요.<br><br>");
					checkname=false;
				} else if (!pattern.test(name)) {
					$("#name_message").css('color', 'red').html("한글과 영문 대 소문자를 사용하세요. (특수문자 사용 불가)<br><br>");
					checkname=false;
				} else {
					checkname=true;
				}
	});//name keyup 이벤트 처리 끝
	
	
	$("#jumin1").on('keyup',
			function() {
				$("#jumin_message").empty();
	
				var pattern = /^[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])$/;
				var jumin1 = $("#jumin1").val();
				if (jumin1 == '') {
					$("#jumin_message").css('color', 'red').html("주민등록번호를 입력하세요.<br><br>");
					checkjumin1=false;
				} else if ($.trim(jumin1).length == 6) {
					if (!pattern.test(jumin1)) {
						$("#jumin_message").css('color', 'red').html("주민등록번호를 정확하게 입력하세요.<br><br>");
						checkjumin1=false;
					} else {
						checkjumin1=true;
					}
				}
	});//jumin1 keyup 이벤트 처리 끝
	
	
	$("#jumin2").on('keyup',
			function() {
				$("#jumin_message").empty();
	
				var pattern = /^[1234][0-9]{6}$/;
				var jumin2 = $("#jumin2").val();
				if (jumin2 == '') {
					checkjumin2=false;
				} else if ($.trim(jumin2).length == 7) {
					if (!pattern.test(jumin2)) {
						$("#jumin_message").css('color', 'red').html("주민등록번호를 정확하게 입력하세요.<br><br>");
						checkjumin2=false;
					} else {
						var c = $('#jumin2').val().substring(0,1);
						var index=(c-1)%2;
						   
						$('input[type=radio]').eq(index).prop('checked', true);
						checkjumin2=true;
					}
				}
	});//jumin2 keyup 이벤트 처리 끝
	
	
	$("#phone").on('keyup',
			function() {
				$("#phone_message").empty();
	
				var pattern = /^\d{2,3}-\d{3,4}-\d{4}$/;
				var phone = $("#phone").val();
				if (phone == '') {
					$("#phone_message").css('color', 'red').html("전화번호를 입력하세요.<br><br>");
					checkphone=false;
				} else if (!pattern.test(phone)) {
					$("#phone_message").css('color', 'red').html("형식에 맞지 않는 번호입니다.<br><br>");
					checkphone=false;
				} else {
					checkphone=true;
				}
	});//phone keyup 이벤트 처리 끝
	
	
	$("#post1").on('keyup',
			function() {
				$("#post_message").empty();
	
				var pattern = /^\d{5}$/;
				var post = $("#post1").val();
				if (post == '') {
					$("#post_message").css('color', 'red').html("우편번호를 입력하세요.<br><br>");
					checkpost=false;
				} else if (!pattern.test(post)) {
					$("#post_message").css('color', 'red').html("우편번호는 5자 숫자입니다<br><br>");
					checkpost=false;
				} else {
					checkpost=true;
				}
	});//post1 keyup 이벤트 처리 끝
	
	
	$("#address").on('keyup',
			function() {
				$("#address_message").empty();
	
				var address = $("#address").val();
				if (address == '') {
					$("#address_message").css('color', 'red').html("주소를 입력하세요.<br><br>");
					checkaddress=false;
				} else {
					checkaddress=true;
				}
	});//address keyup 이벤트 처리 끝
	
	
	//폼 제출시 공백확인
	$('form').submit(function() {
		if(!checkemail){
			if($("#email").val() == '') {
				$("#email_message").css('color', 'red').html("이메일을 입력하세요.<br><br>");
			}
			$("#email").focus();
			return false;
		}
		
		if(!checkpass){
			if($("#pass").val() == '') {
				$("#pass_message").css('color', 'red').html("비밀번호를 입력하세요.<br><br>");
			}
			$("#pass").focus();
			return false;
		}
		
		if(!checkpassok){
			if($("#pass_check").val() == '') {
				$("#passok_message").css('color', 'red').html("비밀번호 확인 문구를 입력하세요.<br><br>");
			}
			$("#pass_check").focus();
			return false;
		}
		
		if(!checkusername){
			if($("#user_name").val() == '') {
				$("#username_message").css('color', 'red').html("닉네임을 입력하세요.<br><br>");
			}
			$("#user_name").focus();
			return false;
		}
		
		if(!checkname){
			if($("#name").val() == '') {
				$("#name_message").css('color', 'red').html("이름을 입력하세요.<br><br>");
			}
			$("#name").focus();
			return false;
		}
		
		if(!checkjumin1){
			if($("#jumin1").val() == '') {
				$("#jumin_message").css('color', 'red').html("주민등록번호를 입력하세요.<br><br>");
			}
			$("#jumin1").focus();
			return false;
		}
		
		if(!checkjumin2){
			if($("#jumin2").val() == '') {
				$("#jumin_message").css('color', 'red').html("주민등록번호를 입력하세요.<br><br>");
			}
			$("#jumin2").focus();
			return false;
		}
		
		if(!checkphone){
			if($("#phone").val() == '') {
				$("#phone_message").css('color', 'red').html("전화번호를 입력하세요.<br><br>");
			}
			$("#phone").focus();
			return false;
		}
		
		if(!checkpost){
			if($("#post1").val() == '') {
				$("#post_message").css('color', 'red').html("우편번호를 입력하세요.<br><br>");
			}
			$("#post1").focus();
			return false;
		}
		
		if(!checkaddress){
			if($("#address").val() == '') {
				$("#address_message").css('color', 'red').html("주소를 입력하세요.<br><br>");
			}
			$("#address").focus();
			return false;
		}
		
		
	})
	
	
	//우편번호 주소 찾기
	$('#postcode').click(function(){
			//window.open("post.html", "post", "width=400, height=500, scrollbars=yes");
			Postcode();
			$("#post_message").html("");
			checkpost=true;
			$("#address_message").html("");
			checkaddress=true;
		});	
		
		function Postcode() {
	        new daum.Postcode({
	            oncomplete: function(data) {
	            	console.log(data.zonecode)
	                var fullRoadAddr = data.roadAddress;
	                var extraRoadAddr = '';
	 
	                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                    extraRoadAddr += data.bname;
	                }
	                
	                if(data.buildingName !== '' && data.apartment === 'Y'){
	                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                }
	                
	                if(extraRoadAddr !== ''){
	                    extraRoadAddr = ' (' + extraRoadAddr + ')';
	                }
	                
	                if(fullRoadAddr !== ''){
	                    fullRoadAddr += extraRoadAddr;
	                }
	 
	                $('#post1').val(data.zonecode);
	                $('#address').val(fullRoadAddr);
	            }
	        }).open();
	    }//function Postcode()
})
