<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lists</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/searchbar.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dropdown.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/listsNavigation.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/texts.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/divs.css">


<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


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
				$("#listsUL").html("");
				jQuery.each(result, function(index, value){
 					$("#listsUL").append("<a href='#' class='list-group-item' onclick='getTasks(\""+ result[index].id +"\",\""+result[index].listName+"\")'>"+result[index].listName+"</a>");
 		        });
			}, 
			error : function(){
				console.log("error");
			}
		});
	}
	
	
	function getTasks(id,name){
		var data = {
				listId: id,
				listName: name 
		}
		
		$.ajax({
			url:"getTasksList",
			data: data,
			success : function(result){
				$("#tasksDiv").html("");
				$("#addTaskBtn").show();
				$('#addTaskBtn').attr('onClick', 'addTaskToList('+data.listId+');');

				$("#addTaskText").show();
				console.log("success");
				jQuery.each(result, function(index, value){
 					$("#tasksDiv").append("<a href='#' onclick='getTaskDetails(\""+ result[index].id +"\")'>"+result[index].task+"</a><br>");
 		        });
				$("#listTitleLabel").text(data.listName);
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
	<div class="row" style="padding: 0;">
		<div class="col-md-3 listsNavDiv" style="padding-right: 0;">
			<div id="custom-search-input">
				<div class="input-group col-md-12">
					<input type="text" class="form-control input-lg" placeholder="Search" />
					<span class="input-group-btn">
						<button class="btn btn-info btn-lg" type="button">
							<i class="glyphicon glyphicon-search"></i>
						</button>
	                </span>
	            </div>
			</div>
			<div class="col-md-12" style="padding: 0; margin: 0;">
				<ul class="nav navbar-nav">
		        	<li class="dropdown" style="">
		          		<a href="#" class="dropdown-toggle" data-toggle="dropdown">${pageContext.request.userPrincipal.name} <span class="glyphicon glyphicon-user pull-right"></span></a>
		          		<ul class="dropdown-menu" style="width: 100%;">
			            	<li><a href="#">Profile <span class="glyphicon glyphicon-cog pull-right"></span></a></li>
			            	<li class="divider"></li>
			            	<li><a href="#">User stats <span class="glyphicon glyphicon-stats pull-right"></span></a></li>
			            	<li class="divider"></li>
			            	<li><a href="#">Messages <span class="badge pull-right"> 42 </span></a></li>
			            	<li class="divider"></li>
			            	<li><a href="#">Favourites Snippets <span class="glyphicon glyphicon-heart pull-right"></span></a></li>
			            	<li class="divider"></li>
			            	<li><a href="login?logout">Sign Out <span class="glyphicon glyphicon-log-out pull-right"></span></a></li>
			          	</ul>
		        	</li>
				</ul>
			</div>
			<div id="custom-search-input">
				<div class="input-group col-md-12">
					<input id="createListText" type="text" class="form-control input-lg" placeholder="Create a list" />
					<span class="input-group-btn">
						<button class="btn btn-info btn-lg" type="button" onclick="createList()" >
							<i class="glyphicon glyphicon-plus-sign"></i>
						</button>
		           	</span>
		       	</div>
			</div>
		  	<div class="panel panel-default">
<!-- 				<div class="panel-heading"> -->
<!-- 					<h3 class="panel-title">Lists</h3> -->
<!-- 		    	</div> -->
		     	<ul class="list-group" id="listsUL">

		       	</ul>
			</div>
		</div>
		<div class="col-md-6" style="padding: 0;">
			<div class="col-md-12" style="padding-top: 10px; padding-bottom: 6px; background-color: #2D71B2;">
				<label class="listTitleText" id="listTitleLabel">No list selected</label>
			</div>
			<div class="col-md-12" style="padding: 0; background-color: #9ebdef;">
				<div id="custom-search-input">
					<div class="input-group col-md-12">
						<input id="addTaskText" type="text" class="form-control input-lg" placeholder="Add a task for this list" />
						<span class="input-group-btn">
							<button class="btn btn-info btn-lg" type="button" id="addTaskBtn">
								<i class="glyphicon glyphicon-plus-sign"></i>
							</button>
			           	</span>
			       	</div>
				</div>
				<div id="tasksDiv" style="">

				</div>
			</div>
		</div>
		<div class="col-md-3">
			<h4>SubTasks</h4>
		</div>
	</div>
</body>
</html>