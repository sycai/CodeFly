$(document).ready(function(){

	$('#login').click(function(){

		$.post("http://localhost:8080/login",       //$.post(URL,data,callback)
			{
				username: $("#exampleInputEmail1").val(),
				password: $("#exampleInputPassword1").val()
			},
			function(data){
				//process the data
				var newDoc = document.open("text/html", "replace");
				newDoc.write(data);
				newDoc.close();
			});
	});
});

$(document).ready(function(){

	$('#register').click(function(){

		$.post("http://localhost:8080/register",       //$.post(URL,data,callback)
			{
				username: $("#exampleInputEmail1").val(),
				password: $("#exampleInputPassword1").val()
			},
			function(data){
				var newDoc = document.open("text/html", "replace");
				newDoc.write(data);
				newDoc.close();
			});
	});
});