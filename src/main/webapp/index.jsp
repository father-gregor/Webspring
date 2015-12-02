<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Spring MVC Tutorial Series by Crunchify.com</title>
	<style type="text/css">
	body {
		background-image: url('http://crunchify.com/bg.png');
	}
	</style>
</head>
<body>
	<br>
	<div style="text-align:center">
		<h2>
			Hey You..!! This is your 1st Spring MCV Tutorial..<br> <br>
		</h2>
		<h3>
			<a href='<c:out value="/currency-CAD-UAH-10" />'>CLICK THAT</a>
			<a href='<c:out value="/welcome-2015-12-02" />'>OR THAT</a>
		</h3>
	</div>
</body>
</html>