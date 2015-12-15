
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
				var minCurrency = data[data.length-1].currency; //data[data.length].currency;
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
					if((index >= (data.length-30)) && (minCurrency > currCountry.currency)) {
						minCurrency = currCountry.currency;
					}
					if((index >= (data.length-30)) && (maxCurrency < currCountry.currency)) {
						maxCurrency = currCountry.currency;
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
						min: minCurrency/1.01,
						max: maxCurrency,
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
				plotObject = $.plot(chart, dataChart, options);
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
});
