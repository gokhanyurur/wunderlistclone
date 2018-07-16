<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lists</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">

	$(document).ready(function() {

		$.ajax({
			url: "getLists",
			success : function(result){
				console.log("success");
				//debugger;
				jQuery.each(result, function(index, value){
 					$("#listsDiv").append("<a href='#' onclick='getTasks(\""+ result[index].id +"\")'>"+result[index].listName+"</a><br>");
 		        });
			}, 
			error : function(){
				console.log("error");
			}
		});
		
	});

	function getTasks(id){
		debugger;

		var data = {
				listId: id
		}
		
		$.ajax({
			url:"getTasksList",
			data: data,
			success : function(result){
				console.log("success");
				jQuery.each(result, function(index, value){
					//returns undefined ----------------------------------------------------------------------------------------------------------
 					$("#tasksDiv").append("<a href='#' onclick='getTaskDetails(\""+ result[index].id +"\")'>"+result[index].listName+"</a><br>");
 		        });
			}, 
			error : function(){
				console.log("error");
			}
		});
	}
 </script>


</head>
<body>
	<h2 align="center">This page is protected. It is available only for users.</h2>
	
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<div class="col-md-12">
					<h4>Lists</h4>
				</div>
				<div id="listsDiv" style="">

				</div>
			</div>
			<div class="col-md-5">
				List content
				<div id="tasksDiv" style="">

				</div>
			</div>
			<div class="col-md-3">
				Subtasks-Comments-Shared People
			</div>
		</div>
	</div>
</body>
</html>