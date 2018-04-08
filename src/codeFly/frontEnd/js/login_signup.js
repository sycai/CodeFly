$(document).ready(function(){

	$('#login').click(function(){

		$.post("#url",       //$.post(URL,data,callback)
			{
				username: $("#exampleInputEmail1").val(),
				password: $("#exampleInputPassword1").val()
			},
			function(data,status){
				//process the data
				if(status ==="200"){
					$("").innerHTML(data);        //not sure, after get the html from server, how to refresh the page, or use window.location.  method
				}
				else{
					//handle ecxeptions
				}
			}
		});
	});
});

$(document).ready(function(){

	$('#signup').click(function(){

		$.post("#url",       //$.post(URL,data,callback)
			{
				username: $("#exampleInputEmail1").val(),
				password: $("#exampleInputPassword1").val()
			},
			function(data,status){
				//process the data
				if(status ==="200"){
					$("").innerHTML(data);        //not sure, after get the html from server, how to refresh the page, or use window.location.  method
				}
				else{
					//handle ecxeptions
				}
			}
		});
	});
});