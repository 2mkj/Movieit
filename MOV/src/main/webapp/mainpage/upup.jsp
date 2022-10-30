<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#topButton{cursor: pointer;position: fixed; right: 10%; bottom: 50px; display: none; z-index: 999;}
#topButtonImg{width:100px;height:100px;}
</style>
<script src="js/jquery-3.6.0.js"></script>
<!-- 맨위로 버튼 -->
<div id="topButton"><img src="${pageContext.request.contextPath}/image/mymainimg/upup.png" id="topButtonImg"></div>

<script>
$(document).ready(function() {

	$(window).scroll(function() {
	    // top button controll
	    if ($(this).scrollTop() > 500) {
	        $('#topButton').fadeIn();
	    } else {
	        $('#topButton').fadeOut();
	    }
	});

	$("#topButtonImg").click(function() {
		$('html, body').animate({scrollTop:0}, '300');
	});

});
</script>