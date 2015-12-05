window.onload = function() {
	$.plot($("#placeholder"), [ [[0, 0], [1, 1]] ], { yaxis: { max: 1 } });
}

$(document).ready(function() {});

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
			$.each(data, function(index, currEmp) {
	             console.log(currEmp.currency); //to print name of employee
	         }); 
			$.plot($("#placeholder"), [ [[15, 6], [14, 32]] ], { yaxis: { max: 1 } });
		},
		error:function(data,status,er) { 
	        alert("error: "+data+" status: "+status+" er:"+er);
	    }
	});
});