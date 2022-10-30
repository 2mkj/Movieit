<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<script src="js/jquery-3.6.0.js"></script>
<script src="js/popper.js"></script>
<script src="js/list.js"></script>
 <jsp:include page="../mainpage/top.jsp" />  
 <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
  <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">

 <style>

table {
  padding:1.5em;
  border: 1px #a39485 solid;
  font-size: .9em;
  box-shadow: 0 2px 5px rgba(0,0,0,.25);
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
}

th {
  text-align: left;
}
  
thead {
  font-weight: bold;
  color: #fff;
  background: #73685d;
}
  
 td, th {
  padding: 1em .5em;
  vertical-align: middle;
}
  
 td {
  border-bottom: 1px solid rgba(0,0,0,.1);
  background: #fff;
}

a {
  color: #73685d;
}
  
 @media all and (max-width: 768px) {
    
  table, thead, tbody, th, td, tr {
    display: block;
  }
  
  th {
    text-align: right;
  }
  
  table {
    position: relative; 
    padding-bottom: 0;
    border: none;
    box-shadow: 0 0 10px rgba(0,0,0,.2);
  }
  
  thead {
    float: left;
    white-space: nowrap;
  }
  
  tbody {
    overflow-x: auto;
    overflow-y: hidden;
    position: relative;
    white-space: nowrap;
  }
  
  tr {
    display: inline-block;
    vertical-align: top;
  }
  
  th {
    border-bottom: 1px solid #a39485;
  }
  
  td {
    border-bottom: 1px solid #e5e5e5;
  }
  
  
  }
 th.btn:link, td.btn:visited {
     background-color: #FFA500;
     color: maroon;
     padding: 15px 25px;
     text-align: center;
     text-decoration: none;
     display: inline-block;
}
 .btn:hover, .btn:active {
     background-color: #FF4500;
}
.id,.name,.btn{
	color:blue;
}
.table{
margin:3.5em;
}
</style>
 <title>MVC 게시판</title>
</head>
<body>
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
	                 <a class="nav-link" href="#"></a>
	               <a class="nav-link collapsed"  data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
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
            
                     
	<div class="container">  
	 	<c:if test="${listcount > 0}">
	 	<%-- 회원이 있는 경우 --%>
	 		<table class="table table-striped">
	 				<tr>
	 					<th colspan="2">회원 정보 list</th>
	   					<th><font size=3>회원 수 : ${listcount}</font><br></th>
	   				</tr>
	 			
	   				<tr>
						<td class='id'>아이디</td>
						<td class='name'>이름</td>
						
						<td >삭제</td>
					</tr>
				
			
	 		<tbody>
				<c:forEach var="m" items="${totallist}">
					<tr>
						<td><a href="managerInfo.mg?email=${m.email}">${m.email}</a></td>
						<td>${m.user_name}</td>
						
						<td><a class='btn' href="managerDelete.mg?email=${m.email}">삭제</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
	<div>
		  <ul class="pagination justify-content-center">		
			 <c:if test="${page <= 1 }">
				<li class="page-item">
				  <a class="page-link gray">이전&nbsp;</a>
				</li>
			 </c:if>
			 <c:if test="${page > 1 }">			
				<li class="page-item">
   <a href="managerList.mg?page=${page-1}&search_field=${search_field}&search_word=${search_word}" 
				      class="page-link">이전&nbsp;</a>
				</li> 
			 </c:if>
					
			<c:forEach var="a" begin="${startpage}" end="${endpage}">
				<c:if test="${a == page}">
					<li class="page-item active" >
					   <a class="page-link">${a}</a>
					</li>
				</c:if>
				<c:if test="${a != page }">
					<c:url var="go" value="managerList.mg">
						<c:param name="search_field"  value="${search_field}"/>
						<c:param name="search_word"   value="${search_word}"/>
						<c:param name="page"          value="${a}"/>
					</c:url>
				    <li class="page-item">
					   <a href="${go}" class="page-link">${a}</a>
				    </li>	
				</c:if>
			</c:forEach>
			
			<c:if test="${page >= maxpage }">
				<li class="page-item">
				   <a class="page-link gray">&nbsp;다음</a> 
				</li>
			</c:if>
			<c:if test="${page < maxpage }">
					<c:url var="next" value="managerList.mg">
						<c:param name="search_field"  value="${search_field}"/>
						<c:param name="search_word"   value="${search_word}"/>
						<c:param name="page"          value="${page+1}"/>
					</c:url>
					  <li class="page-item">
						<a href="${next}" class="page-link">&nbsp;다음</a>
					  </li>	
				</c:if>
		 	</ul>
		</div>
	 </c:if>
	</div>
		
	 <%-- 회원이 없는 경우--%>
	 <c:if test="${listcount == 0 && empty search_word}">
		<h1>회원이 없습니다.</h1>
	 </c:if>
	 
	 <c:if test="${listcount == 0 && !empty search_word}">
		<h1>검색 결과가 없습니다.</h1>
	 </c:if>

</body>
</html>