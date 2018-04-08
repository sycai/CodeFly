$(document).ready(function(){
	$("#signup").click(function(){
		$.get("#url", function(data, status){
			//process the data
			if(status ==="200"){
				$("").innerHTML(data);        //not sure, after get the html from server, how to refresh the page, or use window.location.  method
			}
			else{
				//handle ecxeptions
			}
		});
	});
});


$(document).ready(function(){
	$("#login").click(function(){
		$.get("#url", function(data, status){
			//process the data
			if(status ==="200"){
				$("").innerHTML(data);      //not sure, after get the html from server, how to refresh the page, or use window.location.  method
			}
			else{
				//handle ecxeptions
			}
		});
	});
});


$(document).ready(function(){
	$("#about").click(function(){
		$.get("#url", function(data, status){
			//process the data
			if(status ==="200"){
				$("").innerHTML(data);        //not sure, after get the html from server, how to refresh the page, or use window.location.  method
			}
			else{
				//handle ecxeptions
			}
		});
	});
});


$(document).ready(function(){
	$("#question").click(function(){
		$.get("#url", function(data, status){
			//process the data
			if(status ==="200"){
				$("").innerHTML(data);        //not sure, after get the html from server, how to refresh the page, or use window.location.  method
			}
			else{
				//handle ecxeptions
			}
		});
	});
});