<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Spring Currency Converter</title>
    <link href="<c:url value='webstyle/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='webstyle/css/custom.css' />" rel="stylesheet">
</head>
<body>
    <div class="navbar navbar-default">
    	
    </div>
    <header>
    	<div class="container intro-cont">
    		<div class="intro-text">
    			<div class="intro-welcome">
    				Welcome to the Spring Currency Converter!
    			</div>
    			<div class="intro-choice">
    				Select currencies and press Show
    			</div>
    		</div>
    		<div class="row">
				<div class="col-md-4 show-curr1">EUR</div>
				<div class="col-md-4 show-curr2">UAH</div>
    			<a href="#" class="col-md-4 show-btn">SHOW</a>
    		</div>
    	</div>
    </header>
	<div class="container">
		<div class="row chart-row">
			<div class="col-lg-8 col-md-10">
				<div id="placeholder">
					
				</div>
				<div id="loading-cont"></div>
			</div>
		</div>
	</div>
	<div id="tests" style="text-align:center">
		<h2>
			Hey You..!! T his is your 1st Spring MCV Tutorial..<br> <br>
		</h2>
		<h3>
			<a href='<c:out value="/currency-CAD-UAH-10" />'>CLICK THAT</a>
			<a href='<c:out value="/welcome-2015-12-02" />'>OR THAT</a>
			
		</h3>
	</div>
	<div id="footer">
		<div class="container">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				© 2015 Spring Currency Converter. All rights reserved.
			</div>
			<div class="pull-right">Back to top</div>
		</div>
	</div>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="<c:url value='webstyle/js/index.js' />"></script>
	<script src="<c:url value='webstyle/js/flot/jquery.flot.js' />"></script>
	<script src="<c:url value='webstyle/js/flot/jquery.flot.time.js' />"></script>
	<script src="<c:url value='webstyle/js/flot/jquery.flot.navigate.js' />"></script>
	<script src="<c:url value='webstyle/js/bootstrap.min.js' />"></script>
</body>
</html>