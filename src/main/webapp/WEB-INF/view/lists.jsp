<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lists</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
		
		$.ajax({
			url: "showLists",
			contentType: "application/json",
			dataType: "json",
			data: JSON.stringify(map),
			beforeSend: function(){
				debugger;
				console.log("beforeSend");
			},
			success : function(result){
				debugger;
				console.log("success");
				console.log(result);
			}, 
			error : function(){
				debugger;
				console.log("error");
			},
			complete: function(){
				debugger;
				console.log("complete");
			}
		});
		
	});

 </script>


</head>
<body>

	<h2>This page is protected. It is available only for users.</h2>
	
</body>
</html>