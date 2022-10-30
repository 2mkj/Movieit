<%@page import="java.sql.Connection"%>
<%@page import="com.jdbc.connect.ConnectionPool"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<%
	ConnectionPool.Test();
	%>
</body>
</html>