<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<script src="js/jquery-3.6.0.js"></script>
<script src="js/popper.js"></script>
<script src="js/bootstrap.js"></script>
 <jsp:include page="../mainpage/top.jsp" />  
 <%--
 1. 검색어를 입력한 후 다시 memberList.net으로 온 경우 검색 필드와 검색어가 나타나도록 합니다.
 
 2. 검색 필드를 변경하면 검색어 입력창에 placeholder 나타나도록 합니다.
    예로 아이디를 선택하면 placeholder로 "아이디 입력하세요"라고 나타납니다.
    예로 이름을 선택하면 placeholder로 "이름 입력하세요"라고 나타납니다.
    예로 나이를 선택하면 placeholder로 "나이 입력하세요"라고 나타납니다.
    예로 성별을 선택하면 placeholder로 "여 또는 남 입력하세요"라고 나타납니다.
    
 3. 검색 버튼 클릭시 다음을 체크합니다.
 	1) 검색어를 입력하지 않은 경우 "검색어를 입력하세요" 라고 대화상자가 나타나게 합니다.
 	2) 나이는 두 자리 숫자가 아닌 경우 "나이는 형식에 맞게 입력하세요(두자리 숫자)" 라고 대화상자가 나타나게 합니다.
 	3) 성별은 "남" 또는 "여"가 아닌 경우 "남 또는 여를 입력하세요" 라고 대화상자가 나타나게 합니다.
 	
 4. 회원 목록의 삭제를 클릭한 경우
 	confirm("정말 삭제하시겠습니까?")를 실행해서 취소를 클릭하면 "memberDelete.net"로 이동하지 않습니다. 
  --%>
  <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">

 <style>

</style>
 <title>MVC 게시판</title>
</head>
<body>
<c:if test="${email=='admin' && !empty email}">
 <div id="layoutSidenav">
   <div id="layoutSidenav_nav">
     <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
       <div class="sb-sidenav-menu">
         <div class="nav">
		   <div class="sb-sidenav-menu-heading">회원 관리</div>
           <a class="nav-link" href="managerList.mg">회원 정보 관리</a>                        
           <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
             <br></a>
           <div class="sb-sidenav-menu-heading">게시판</div>
           <a class="nav-link" href=" Community.co">게시판 관리</a><br><br>                     
	                 <a class="nav-link" href="noticeCommunity.no">공지</a>
	               <a class="nav-link collapsed"data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
	                 </a>
				 <div class="sb-sidenav-menu-heading">영화 참고 </div>
	             <a class="nav-link" href="https://www.cgv.co.kr/"> CGV</a>
	               <a class="nav-link collapsed"  data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
	                </a>
	                 <a class="nav-link" href="https://www.lottecinema.co.kr/NLCHS">롯데시네마</a>
	               <a class="nav-link collapsed" data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
	                 </a>
	                 
	                
	               
	                 <a class="nav-link" href="#"></a>
	               <a class="nav-link collapsed" data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
	                 </a>
			 </div> 
			   <br>
			   <br>
               <div class="nav">
                <div class="sb-sidenav-menu-heading"></div>
                 <a class="nav-link" href="#"> </a>
                 <a class="nav-link" href="#"> </a>
                           
                 <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
	                 <a class="navbar-brand" href="main.net" style="text-align:center;">무빗!</a>
	      			 <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample07" aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
	        		 	<span class="navbar-toggler-icon"></span>
	     			 </button>  
                 </a>
                 </div>
                 <div class="collapse" id="pagesCollapseError" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                  <nav class="sb-sidenav-menu-nested nav">
                    <a class="nav-link" href="401.html">401 Page</a>
                    <a class="nav-link" href="404.html">404 Page</a>
                    <a class="nav-link" href="500.html">500 Page</a>
                  </nav>
                </div>
              </nav>
            </div>
       </c:if>
           



</body>
</html>