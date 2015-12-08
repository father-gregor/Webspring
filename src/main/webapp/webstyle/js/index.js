
$(function() {
	var chart = $("#placeholder");
	$.plot(chart, []);
	$(".show-btn").click(function() {
		var jscurr1 = $(".show-curr1").text();
		var jscurr2 = $(".show-curr2").text();
		var date = new Date();
		var dateUTC = date.getUTCFullYear() + "-" + ("0" + (date.getUTCMonth() + 1)).slice(-2) +  "-" + ("0" + date.getUTCDate()).slice(-2);
		var json = {
				"countryid1": jscurr1, 
				"countryid2": jscurr2, 
				"date": dateUTC
		};
		
		$("#loading-cont").append("<div class='circle1'></div>").append("<div class='circle2'></div>");
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
				var minCurrency = 0;
				var maxCurrency = 0;
				var minDate = new Date(data[0].date).getTime();
				var maxDate = new Date(data[0].date).getTime();
				$.each(data, function(index, currCountry) {
					var timestamp = new Date(currCountry.date).getTime();
					dataTemp.push([new Date(currCountry.date).getTime(), parseFloat(currCountry.currency)]);
					if(minDate > timestamp) {
						minDate = timestamp;
					}
					if(maxDate < timestamp) {
						maxDate = timestamp;
					}
					if(maxCurrency < currCountry.currency) {
						maxCurrency = currCountry.currency;
					}
		        }); 
				var dataChart = [dataTemp];
				var options = {
					series: { lines: { show: true }, shadowSize: 0 },
					xaxis: { 
						mode: "time",
						minTickSize: [1, "day"],
						zoomRange: [560000000, null],
						panRange: [minDate, maxDate] 
					},
					yaxis: {
						zoomRange:  false,
						panRange: false 
					},
					zoom: {
						interactive: true
					},
					pan: {
						interactive: true
					}
				};
				$.plot(chart, dataChart, options).fadeIn(5000);
				chart.bind("plotzoom", function(event, plot){
					var axes = plot.getAxes();
					
				});
			},
			complete: function(data) {
				$("#loading-cont").delay(1500).fadeOut("slow");
			},
			error:function(data,status,er) { 
		        alert("error: "+data+" status: "+status+" er:"+er);
		    }
		});
		
	});
});
