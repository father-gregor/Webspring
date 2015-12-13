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
    		<div class="form-inline">
					<select class="show-curr1" title="Choose base currency">
						<c:forEach var="currency" items="${currencyList}">
							<c:choose>
								<c:when test="${currency.currencyId == 'USD'}">
									<option value="${currency.currencyId}" selected>${currency.country}</option>
								</c:when>
								<c:otherwise>
									<option value="${currency.currencyId}">${currency.country}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
					<select class="show-curr2" title="Choose counter currency">
						<c:forEach var="currency" items="${currencyList}">
							<c:choose>
								<c:when test="${currency.currencyId == 'EUR'}">
									<option value="${currency.currencyId}" selected>${currency.country}</option>
								</c:when>
								<c:otherwise>
									<option value="${currency.currencyId}">${currency.country}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
    			<a href="#placeholder" class="show-btn">SHOW</a>
    		</div>
    	</div>
    </header>
	<div class="container">
		<div class="row chart-row">
			<div class="col-lg-12 col-md-12">
				<div id="placeholder"></div>
			</div>
		</div>
	</div>
	<div id="tests" style="text-align:center">
		<h2>
			Hey You..!! This is your 1st Spring MCV Tutorial..<br> <br>
		</h2>
	</div>
	<div id="footer">
		<div class="container">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				© 2015 Spring Currency Converter. All rights reserved.
			</div>
			<div class="pull-right" id="footer-href"><a href="#">Back to top</a></div>
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