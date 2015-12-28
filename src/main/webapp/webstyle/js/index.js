
$(function() {
	var chart = $("#placeholder");
	var $root = $('html, body');
	var plotObject;
	var dataObject;
	var jscurr1 = "";
	var jscurr2 = "";
	$.plot(chart, [], {grid: {borderColor: 'transparent'}});
	$(".show-btn").click(function() {
		jscurr1 = $(".show-curr1").val();
		jscurr2 = $(".show-curr2").val();
		var date = new Date();
		var dateUTC = date.getUTCFullYear() + "-" + ("0" + (date.getUTCMonth() + 1)).slice(-2) +  "-" + ("0" + date.getUTCDate()).slice(-2);
		var json = {
				"countryid1": jscurr1, 
				"countryid2": jscurr2, 
				"date": dateUTC
		};
		//$("#loading-cont").append("<div class='circle1'></div>").append("<div class='circle2'></div>");
		$.ajax ({
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			type: "POST",
			url: "/getcurrencies",
			data: JSON.stringify(json),
			dataType: "json",
			success: function(data) {
				var dataTemp = [];
				dataObject = data;
				//variables for flot minimum and maximum currency on the first plot's drawing.
				var baseMinCurrency = data[data.length-1].currency;
				var baseMaxCurrency = 0;
				//variables for plot minimum and maximum currency overall
				var minCurrency = data[data.length-1].currency;
				var maxCurrency = 0;
				//variables for flot minimum and maximum dates on the first plot's drawing.
				//dateOffset represent 30 days in milliseconds; using for subtract 30 days from baseMaxDate
				var baseMinDate = new Date(data[0].date).getTime();
				var baseMaxDate = new Date(data[0].date).getTime();
				var dateOffset = 30*24*3600*1000;
				//variables for plot minimum and maximum dates overall
				var minDate = new Date(data[0].date).getTime();
				var maxDate = new Date(data[0].date).getTime();
				$.each(data, function(index, currCountry) {
					var timestamp = new Date(currCountry.date).getTime();
					dataTemp.push([new Date(currCountry.date).getTime(), parseFloat(currCountry.currency)]);
					console.log(currCountry.currency);
					if(minDate > timestamp) {
						minDate = timestamp;
					}
					if(maxDate < timestamp) {
						maxDate = timestamp;
						baseMaxDate = timestamp;
					}
					if(minCurrency > currCountry.currency) {
						minCurrency = currCountry.currency;
					}
					if(maxCurrency < currCountry.currency) {
						maxCurrency = currCountry.currency;
					}
					if((index >= (data.length-30)) && (baseMinCurrency > currCountry.currency)) {
						baseMinCurrency = currCountry.currency;
					}
					if((index >= (data.length-30)) && (baseMaxCurrency < currCountry.currency)) {
						baseMaxCurrency = currCountry.currency;
					}
		        }); 
				baseMinDate = baseMaxDate - dateOffset;
				var dataChart = [dataTemp];
				var options = {
					series: { 
						lines: { 
							show: true, 
							fill: true
						}, 
						points: { 
							show:false,
							radius: 3,
							fillColor: '#72c459'
						},
						color: '#72c459',
						shadowSize: 0 
					},
					grid: {
						color: '#646464',
						borderColor: 'transparent',
						hoverable: true,
						mouseActiveRadius: 10
					},
					xaxis: { 
						mode: "time",
						tickColor: 'transparent',
						minTickSize: [1, "day"],
						min: baseMinDate,
						max: baseMaxDate,
						zoomRange: [560000000, null],
						panRange: [minDate, maxDate] 
					},
					yaxis: {
						min: baseMinCurrency/1.01,
						max: baseMaxCurrency,
						zoomRange:  false,
						panRange: false 
					},
					zoom: {
						interactive: true,
						amount: 0.5
					},
					pan: {
						interactive: true
					}
				};
				$("#currency-title").html(jscurr1 + " — " + jscurr2);
				plotObject = $.plot(chart, dataChart, options);
				showCurrencyInfo(minCurrency, maxCurrency).fadeIn();
			},
			error:function(data,status,er) { 
		        alert("error: "+data+" status: "+status+" er:"+er);
		    }
		});
		$root.animate({
	        scrollTop: $( $.attr(this, 'href') ).offset().top
	    }, 700);
		return false;
	});
	var previousPoint = null;
	chart.bind('plothover', function (event, pos, item) {
	    if (item) {
	    	if (previousPoint != item.dataIndex) {
	    		var itemDate = new Date(item.datapoint[0]);
	    		previousPoint = item.dataIndex;
	            $('#tooltip').remove();
	            var x = itemDate.getUTCFullYear() + "-" + ("0" + (itemDate.getUTCMonth() + 1)).slice(-2) +  "-" + ("0" + itemDate.getUTCDate()).slice(-2),
                	y = item.datapoint[1];
	            showTooltip(item.pageX, item.pageY, y + ' ' + jscurr2 + ' at ' + x);
	    	}
	        /*if (previousPoint != item.dataIndex) {
		    	var itemDate = new Date(item.datapoint[0]);
		    	itemDate.setUTCHours(0,0,0,0);
		    	var pointDate = new Date(pos.x);
		    	pointDate.setUTCHours(0,0,0,0);
		    	previousPoint = item.dataIndex;
	            $('#tooltip').remove();
		    	console.log(itemDate.getUTCDate() + " " + pointDate.getUTCDate());
	        	if(itemDate.getTime() == pointDate.getTime()) {
		        	item.series.points.radius = 3;
		            var x = itemDate.getUTCFullYear() + "-" + ("0" + (itemDate.getUTCMonth() + 1)).slice(-2) +  "-" + ("0" + itemDate.getUTCDate()).slice(-2),
		                y = item.datapoint[1];
		            showTooltip(item.pageX, item.pageY, y + ' ' + jscurr2 + ' at ' + x);
	        	}
	        }*/
	    } else {
	    	//item.series.points.radius = 0;
	        $('#tooltip').remove();
	        previousPoint = null;
	    }
	});
	function showTooltip(x, y, contents) {
	    $('<div id="tooltip">' + contents + '</div>').css({
	        top: y - 16,
	        left: x + 20
	    }).appendTo('body').fadeIn();
	};
	chart.bind("plotpan plotzoom", function(event, plot) {
		var minDateXaxis = new Date(plot.getAxes().xaxis.min);
		minDateXaxis.setUTCHours(0,0,0,0);
		var maxDateXaxis = new Date(plot.getAxes().xaxis.max);
		maxDateXaxis.setUTCHours(0,0,0,0);
		var indexMin = null;
		var indexMax = null;
		minCurrency = 0;
		maxCurrency = 0;
		$.each(dataObject, function(index, currCountry) {
			var currDate = new Date(currCountry.date);
			currDate.setUTCHours(0,0,0,0);
			if(currDate.getTime() == minDateXaxis.getTime()) {
				indexMin = index;
				minCurrency = currCountry.currency;
			}
			if((indexMin != null) & (indexMax == null) & (minCurrency > currCountry.currency)) {
				minCurrency = currCountry.currency;
			}
			if((indexMin != null) & (indexMax == null) & (maxCurrency < currCountry.currency)) {
				maxCurrency = currCountry.currency;
			}
			if(currDate.getTime() == maxDateXaxis.getTime()) {
				indexMax = index;
			}
		});
		plotObject.getOptions().yaxes[0].min = minCurrency/1.01;
		plotObject.getOptions().yaxes[0].max = maxCurrency;
		plotObject.setupGrid();
		plotObject.draw();
	});
	$("#swap-btn").click(function() {
		var currValue1 = $(".show-curr1").val();
		var currValue2 = $(".show-curr2").val();
		if((currValue1 != "XAU") && (currValue1 != "XAG")) {
			$(".show-curr1").val(currValue2);
			$(".show-curr2").val(currValue1);
		}
	});
	function showCurrencyInfo(minC, maxC) {
		var currDynamic = dataObject[dataObject.length-1].currency - dataObject[dataObject.length-2].currency;
		var procentDynamic = Math.abs(currDynamic/dataObject[dataObject.length-1].currency) * 100;
		var colorDynamic = null;
		if(currDynamic >= 0) {
			colorDynamic = "green";
		} else {
			colorDynamic = "red";
		}
		$("#curr-column1-newest").html(dataObject[dataObject.length-1].currency);
		$("#curr-column1-diff").html(Math.ceil(currDynamic * 100)/100).css("color", colorDynamic);
		$("#curr-column1-percent").html("(" + Math.ceil(procentDynamic * 100)/100 + "%)").css("color", colorDynamic);
		
		var monthNames = ["January", "February", "March", "April", "May", "June",
		                  "July", "August", "September", "October", "November", "December"];
		var month = monthNames[new Date(dataObject[dataObject.length-1].date).getUTCMonth()];
		var day = ("0" + new Date(dataObject[dataObject.length-1].date).getUTCDate()).slice(-2);
		var year = new Date(dataObject[dataObject.length-1].date).getUTCFullYear()
		$("#curr-column1-date").html(month + " " + day + ", " + year);
		$("#curr-column1-pair").html("Pair " + jscurr1 + " - " + jscurr2);
		$("#curr-column2-days").html("<div style='display:table-cell;color:#8c8c8c;padding-right:3px'>Total in chart: </div>" +
				"<div style='display:table-cell'>" + dataObject.length + " days</div>");
		$("#curr-column2-range").html("<div style='display:table-cell;color:#8c8c8c;padding-right:3px'>Range: </div>" + 
				"<div style='display:table-cell'>" + minC + " - " + maxC + "</div>");
		
		/*$("#exch-1").html("1 " + jscurr1 + " = " + dataObject[dataObject.length-1].currency + " " + jscurr2);
		$("#exch-10").html("10 " + jscurr1 + " = " + (dataObject[dataObject.length-1].currency*10) + " " + jscurr2);
		$("#exch-100").html("100 " + jscurr1 + " = " + (dataObject[dataObject.length-1].currency*100) + " " + jscurr2);
		$("#exch-1000").html("1000 " + jscurr1 + " = " + (dataObject[dataObject.length-1].currency*1000) + " " + jscurr2);*/
	}
});
