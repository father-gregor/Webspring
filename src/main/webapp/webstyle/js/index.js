window.onload = function() {
	$.plot($("#placeholder"), [ [[0, 0], [1, 1]] ], { yaxis: { max: 1 } });
}

$(document).ready(function() {});

$(".show-btn").click(function() {
	var jscurr1 = $(".show-curr1").text();
	var jscurr2 = $(".show-curr2").text();
	var date = new Date();
	var dateUTC = date.getUTCFullYear() + "-" + ("0" + (date.getUTCMonth() + 1)).slice(-2) +  "-" + ("0" + date. getUTCDay()).slice(-2);
	var json = {"countryid1": jscurr1, "countryid2": jscurr2, "date": dateUTC};
	alert(json.countryid1);
	alert(json.date) ;
	$.ajax ({
		type: "POST",
		url: "/getcurrencies",
		data: JSON.stringify(json),
		dataType: "json",
		success: function(json) {
			alert("almost");
			if(jQuery.isEmptyObject(json) == false) {
				//$.plot($("#placeholder"), );
				alert("OK");
			}
		},
		error:function(data,status,er) { 
	        alert("error: "+data+" status: "+status+" er:"+er);
	    }
	});
});