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
				<div id="swap-btn"><a href="#" id="swap-btn"></a></div>
				<select class="show-curr2" title="Choose counter currency">
					<c:forEach var="currency" items="${currencyList}">
						<c:choose>
							<c:when test="${currency.currencyId == 'EUR'}">
								<option value="${currency.currencyId}" selected>${currency.country}</option>
							</c:when>
							<c:when test="${(currency.currencyId != 'XAU') && (currency.currencyId !='XAG')}">
								<option value="${currency.currencyId}">${currency.country}</option>
							</c:when>
						</c:choose>
					</c:forEach>
				</select>
    			<a href="#currency-title" class="show-btn text-center">Show</a>
    		</div>
    	</div>
    </header>
	<div class="container chart-cont">
		<div class="row chart-row">
			<div class="col-lg-12 col-md-12 chart-div" >
				<div class="text-center" id="currency-title"></div>
				<div id="placeholder"></div>
				<div id="currency-info">
					<table class="center-block" style="font-size:15px;width:500px;">
						<tr>
							<td>
								<div id="curr-column1-newest"></div>
								<div id="curr-column1-diff"></div>
								<div id="curr-column1-percent"></div>
							</td>
						</tr>
						<tr>
							<td>
								<div id="curr-column1-date"></div>
							</td>
							<td>
								<div id="curr-column2-days"></div>
							</td>
						</tr>
						<tr>
							<td>
								<div id="curr-column1-pair"></div>
							</td>
							<td>
								<div id="curr-column2-range"></div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div id="news-title">FINANCE NEWS</div>
		<div style="display:inline-block; width: 100%; text-align:center;">
			<div class="news-item" id="ni-0">
				<div class="link-div"><a class="news-link" href="" target="_blank"></a></div>
				<div class="src-date">
					<span class="news-src"></span>
					<span class="news-date"></span>
				</div>
			</div>
			<div class="news-item" id="ni-1">
				<div class="link-div"><a class="news-link" href="" target="_blank"></a></div>
				<div class="src-date">
					<span class="news-src"></span>
					<span class="news-date"></span>
				</div>
			</div>
			<div class="news-item" id="ni-2">
				<div class="link-div"><a class="news-link" href="" target="_blank"></a></div>
				<div class="src-date">
					<span class="news-src"></span>
					<span class="news-date"></span>
				</div>
			</div>
			<div class="news-item" id="ni-3">
				<div class="link-div"><a class="news-link" href="" target="_blank"></a></div>
				<div class="src-date">
					<span class="news-src"></span>
					<span class="news-date"></span>
				</div>
			</div>
		</div>
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
	<script src="<c:url value='webstyle/js/flot/jquery.flot.resize.js' />"></script>
	<script src="<c:url value='webstyle/js/flot/jquery.flot.crosshair.js' />"></script>
	<script src="<c:url value='webstyle/js/flot/jquery.flot.touch.js' />"></script>
	<script src="<c:url value='webstyle/js/bootstrap.min.js' />"></script>
</body>
</html>