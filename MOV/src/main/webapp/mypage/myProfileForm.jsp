<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link href="css/mymaincss/my.css" rel="stylesheet" type="text/css">
<script src="js/jquery-3.6.0.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<style>
input[type=file]{display:none;}
#dep{margin: 0 5px;outline: none;border: none;color: red;}
.chkprofile{font-size:10px;margin:0;padding:0;}
</style>
</head>
<body class="updatebody">
   <form class="update" action="updateProfile.net" method="post" enctype="multipart/form-data" onsubmit="return validate()">
	<h3>프로필 수정</h3>
	<hr>
	
	<table class="updatep">
	<tr>
	<td>E-Mail</td>
	<td><input type="hidden" name="email" value="${memberinfo.email}">${memberinfo.email}</td>
	</tr>
	
	<tr>
	<td>비밀번호</td>
	<td><input type="password" id="pass" name="pass" value="${memberinfo.pass}" required><br>
	</td>
	</tr>
	
	<tr>
	<td>비밀번호 확인</td>
	<td><input type="password" id="pass_check" name="pass_check" value="${memberinfo.pass}" required><br>
	</td>
	</tr>
	
	<tr>
	<td>닉네임</td>
	<td><input type="text" id="user_name" name="user_name" value="${memberinfo.user_name}" required><br>
	</td>
	</tr>
	
	<tr>
	<td>이름</td>
	<td><input type="text" id="name" name="name" value="${memberinfo.name}" required><br>
	</td>
	</tr>
	
	<tr>
	<td>주민번호</td>
	<td>${memberinfo.jumin1}-${memberinfo.jumin2}</td>
	</tr>
	
	<tr>
	 <td>성별</td>
	<td>
	<div class="container2">
	<input type="radio" name="gender" value="남" onclick="return(false);">&ensp;남자
&emsp;&emsp;
	<input type="radio" name="gender" value="여" onclick="return(false);">&ensp;여자
	</div>
	</td>
	</tr>
	
	<tr>
	<td>전화번호</td>
	<td><input type="text" id="phone" name="phone" value="${memberinfo.phone}" required><br>
	</td>
	</tr>
	
	<tr>
	<td>우편번호</td>
	<td><input type="text" size="5" maxlength="5" style="width:250px" name="post1" id="post1" value="${memberinfo.post}" required>
		<input class="button-5 postb" type="button" value="우편검색"  id="postcode"><br>
	 	</td>
	</tr>
	
	<tr>
	<td>주소</td>
	<td>
	 <input type="text"  id="address"  name="address" value="${memberinfo.address}" required><br>
	 </td>
	</tr>
	
	<tr>
	<td>프로필 사진</td>
	<td>
	<label>
	  <img src="image/mymainimg/attach.png" width="20px">
	  <span id="filename">${memberinfo.memberfile}</span>
	  <span id="showImage">
	    <c:if test='${empty memberinfo.memberfile}'>
	     <c:set var='src' value='image/mymainimg/profile.png'/>
	    </c:if>
	    <c:if test= '${!empty memberinfo.memberfile}'>
	     <c:set var='src' value='${"memberupload/"}${memberinfo.memberfile}'/>
	    </c:if>
		<img class="profilep" src="${src}" width="20px" alt="profile">
	  </span>
		<input type="file" name="memberfile" accept="image/*">
	</label>
	</td>
	</tr>

	</table>
	<div class="mybutton">
	<input type="submit" class="button-5 updateb" value="수정">
	<input type="button" class="button-5 signoutb" value="탈퇴">
	</div>
	</form>
	
<script>

$(".signoutb").click(function () {
    var answer = confirm('정말 탈퇴하시겠습니까?');
    if (answer == true) {
    	$(location).attr('href','signout.net?email=${memberinfo.email}');
    }
});
	//성별 체크해주는 부분	
   $("input[value='${memberinfo.gender}']").prop("checked",true);

	$("input:file").change(function() {
	var inputfile = $(this).val().split('\\');
	var filename = inputfile[inputfile.length - 1];
	
	var pattern = /(gif|jpg|jpeg|png)$/i; //i(ignore case)는 대소문자 무시를 의미
	
	if(pattern.test(filename)){
		$('#filename').text(filename);
		
		var reader = new FileReader(); // 파일을 읽기 위한 객체 생성
		//DataURL 형식(접두사 data:가 붙은 URL이며 바이너리 파일을 Base64로 인코딩하여 ASCI문자열 형식으로 변환 것)으로
		//파일을 읽어옵니다. (참고-Bast64 인코딩은 바이너리 데이터를 Text로 변경하는 Encoding입니다.)
		//네트워크탭에서 실행 후 Headers 확인해 보세요
		
		//읽어온 결과는 reader객체의 result 속성에 저장됩니다.
		//event.target.files[0] : 선택한 그림의 파일객체에서 첫번째 객체를 가져옵니다.
		reader.readAsDataURL(event.target.files[0]);
		
		reader.onload = function(){//읽기에 성공했을 때 실행되는 이벤트 핸들러
		// $('#showImage').html('<img width="20px" src="'+ this.result +'">');
		$('#showImage > img').attr('src',this.result);
		};
	}else{
		alert('이미지 파일(gif,jpg,jpeg,png)이 아닌 경우는 무시됩니다.');
		$('#filename').text('');
		$('#showImage > img').attr('src','image/profile.png');
		$(this).val(''); //안하면 내가 선택한 파일이 올라가버림
	}
  });//change()
  $("#pass").on('keyup',function() {
	$("#pass_check").val('');
  });
function validate(){
	//비밀번호 유효성 검사
	var passok = $("#pass_check").val();
	if (passok != $("#pass").val()){
		alert("비밀번호가 일치하지 않습니다.")
		$("#pass").val('');$("#pass_check").val('');
		$("#pass").focus();
		$("#pass").css('border','1px solid red');
		$("#pass_check").css('border','1px solid red');
		return false;
	}
	var pass = $("#pass").val();
	var pattern = /^[\w`~!@#$%^&*()-_=+]{8,16}$/;
	if (!pattern.test(pass)){
		alert("비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
		$("#pass").val('');$("#pass_check").val('');
		$("#pass").focus();
		$("#pass").css('border','1px solid red');
		$("#pass_check").css('border','1px solid red');
		return false;
	}
	// 닉네임 유효성 검사
	var pattern = /^[\w가-힣]{2,10}$/;
	var user_name = $("#user_name").val();
	if (!pattern.test(user_name)){
		alert("닉네임은 2~10자 한글과 영문 대 소문자, 숫자를 사용하세요.(특수문자 사용 불가)");
		$("#user_name").val('');
		$("#user_name").focus();
		$("#user_name").css('border','1px solid red');
		return false;
	}
  	//이름 유효성 검사
	var pattern = /^[\w가-힣]+$/;
	var name = $("#name").val();
	if (!pattern.test(name)){
		alert("이름은 한글과 영문 대 소문자를 사용하세요.(특수문자 사용 불가)");
		$("#name").val('');$("#name").focus();
		$("#name").css('border','1px solid red');
		return false;
	}
  	//전화번호 유효성 검사
	var pattern = /^\d{2,3}-\d{3,4}-\d{4}$/;
	var phone = $("#phone").val();
	if (!pattern.test(phone)){
		alert("전화번호는 형식에 맞게 작성해주세요.");
		$("#phone").val('');$("#phone").focus();
		$("#phone").css('border','1px solid red');
		return false;
	}
  	//우편번호 유효성 검사
	var pattern = /^\d{5}$/;
	var post = $("#post1").val();
	if (!pattern.test(post)){
		alert("우편번호는 5자 숫자입니다.");
		$("#post1").val('');$("#post1").focus();
		$("#post1").css('border','1px solid red');
		return false;
	}
  
  }

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

</script>
</body>
</html>