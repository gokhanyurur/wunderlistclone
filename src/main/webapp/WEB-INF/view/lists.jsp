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
		getAllLists();
		$("#addTaskBtn").hide();
		$("#addTaskText").hide();
	});

	function getAllLists(){
		$.ajax({
			url: "getLists",
			success : function(result){
				console.log("success");
				$('#createListText').val("");
				$("#listsDiv").html("");
				jQuery.each(result, function(index, value){
 					$("#listsDiv").append("<a href='#' onclick='getTasks(\""+ result[index].id +"\")'>"+result[index].listName+"</a><br>");
 		        });
			}, 
			error : function(){
				console.log("error");
			}
		});
	}
	
	
	function getTasks(id){
		var data = {
				listId: id
		}
		
		$.ajax({
			url:"getTasksList",
			data: data,
			success : function(result){
				$("#tasksDiv").html("");
				$("#addTaskBtn").show();
				$("#addTaskBtn").click(function(){
					addTaskToList(data.listId);
				});
				$("#addTaskText").show();
				console.log("success");
				jQuery.each(result, function(index, value){
 					$("#tasksDiv").html("<a href='#' onclick='getTaskDetails(\""+ result[index].id +"\")'>"+result[index].task+"</a><br>");
 		        });
			}, 
			error : function(){
				console.log("error");
			}
		});
	}
	
	function addTaskToList(listid){
		var data = {
				listId : listid,
				taskName: $("#addTaskText").val()
		}
		
		$.ajax({
			url:"addTaskProcess",
			type: "POST",
			data: data,
			success : function(){
				$('#addTaskText').val("");
				console.log("Task added to the list.");
				getTasks(data.listId);			
			}
		});
	}
	
	function createList(){
		var data = {
				listname: $("#createListText").val()
		}
		
		$.ajax({
			url:"createListProcess",
			type: "POST",
			data: data,
			success : function(){
				console.log("List created.")
				getAllLists();
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
					<input type="text" id="createListText" placeholder="Create a list"/>
					<input type="button" value="Create" onclick="createList()">
					<div id="listsDiv" style="">

					</div>
				</div>
			</div>
			<div class="col-md-5">
				<h4>List content</h4>
				<input type="text" id="addTaskText" placeholder="Add a task"/>
				<input type="button" id="addTaskBtn" value="Create">
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