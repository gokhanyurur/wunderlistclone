<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Wunderlist Clone</title>
	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui-timepicker-addon.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ready.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/demo.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/userp.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/buttons.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modal.css">
	<link href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" rel="stylesheet" type="text/css">
	
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

	<!-- SELECT2 -->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
	<!-- SELECT2 -->
	
  	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-ui-timepicker-addon.js"></script>
	
	
	<script src="${pageContext.request.contextPath}/resources/js/core/popper.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/core/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/plugin/chartist/chartist.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/plugin/chartist/plugin/chartist-plugin-tooltip.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/plugin/bootstrap-notify/bootstrap-notify.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/plugin/bootstrap-toggle/bootstrap-toggle.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/plugin/jquery-mapael/jquery.mapael.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/plugin/jquery-mapael/maps/world_countries.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/plugin/chart-circle/circles.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/ready.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/demo.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/listControl.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/notifications.js"></script>

</head>
<body>
	<div class="wrapper">
		<div class="main-header">
			<div class="logo-header">
				<a href="#" class="logo">
					Wunderlist Clone
				</a>
				<button class="navbar-toggler sidenav-toggler ml-auto" type="button" data-toggle="collapse" data-target="collapse" aria-controls="sidebar" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<button class="topbar-toggler more"><i class="la la-ellipsis-v"></i></button>
			</div>
			<nav class="navbar navbar-header navbar-expand-lg" style="padding-top:10px;">
				<div class="container-fluid">
					<form class="navbar-left navbar-form nav-search mr-md-3" action="">
						<div class="input-group">
							<input type="text" placeholder="Search a task..." class="form-control" id="searchTextbox" onkeyup="searchTask()">
							<div class="input-group-append">
								<span class="input-group-text">
									<i class="la la-search search-icon"></i>
								</span>
							</div>
						</div>
					</form>
					<ul class="navbar-nav topbar-nav ml-md-auto align-items-center">
						<li class="nav-item dropdown hidden-caret">
							<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<i class="la la-bell"></i>
								<span class="notification" id="notificationCountSpan">0</span>
							</a>
							<ul class="dropdown-menu notif-box" aria-labelledby="navbarDropdown">
								<li>
									<div class="dropdown-title" id="notificationCountDiv">You have 0 new notification</div>
								</li>
								<li>
									<div class="notif-center" id="notificationsBoxDiv">
										
									</div>
								</li>
								<li>
									<a class="see-all" href="#" onclick="seeAllNotifications()"> <strong>See all notifications</strong> <i id="seeAllNotifIcon" class="la la-angle-right"></i> </a>
								</li>
								<li>
									<div class="notif-center" id="allNotificationsBoxDiv" style="overflow-y: scroll; max-height: 200px;">
										<!-- See all notification box -->
									</div>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</nav>
		</div>
			<div class="sidebar">
				<div class="scrollbar-inner sidebar-wrapper">
					<div class="user">
						<div class="photo">
							<p data-letters="${fn:toUpperCase(fn:substring(pageContext.request.userPrincipal.name, 0, 1))}"></p>
						</div>
						<div class="info">
							<a class="" data-toggle="collapse" href="#collapseExample" aria-expanded="true">
								<span>
									Logined as
									<span class="user-level">${pageContext.request.userPrincipal.name}</span>
									<span class="caret"></span>
								</span>
							</a>
							<div class="clearfix"></div>

							<div class="collapse in" id="collapseExample" aria-expanded="true" style="">
								<ul class="nav">
<!-- 									<li> -->
<!-- 										<a href="#profile"> -->
<!-- 											<span class="link-collapse">My Profile</span> -->
<!-- 										</a> -->
<!-- 									</li> -->
									<li>
										<a href="#edit">
											<span class="link-collapse">Edit Profile</span>
										</a>
									</li>
									<li>
										<a href="#settings">
											<span class="link-collapse">About Us</span>
										</a>
									</li>
									<li>
										<a href="login?logout">
											<span class="link-collapse">Logout</span>
										</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<!-- Lists -->
					<ul class="nav">
						<li class="user">
							<div class="info">
								<a class="" data-toggle="collapse" href="#collapseExample2" aria-expanded="false">
									<span class="pull-left">
										<i class="la la-list-alt" style="margin-top: 3px; width: 25px;"></i>
									</span>
									<span>
										Lists
									</span>
								</a>
								<div class="clearfix"></div>

								<div class="collapse in" id="collapseExample2" aria-expanded="true" style="">
									<ul class="nav" id="listsUL">
										<!-- Lists of users go here -->
									</ul>
								</div>
							</div>
						</li>
					</ul>
					<!-- Shared Lists -->
					<ul class="nav">
						<li class="user">
							<div class="info">
								<a class="" data-toggle="collapse" href="#collapseExample3" aria-expanded="true">
									<span class="pull-left" style="margin-top: 3px; width: 25px;">
										<i class="la la-share-square"></i>
									</span>
									<span>
										Shared Lists
									</span>
								</a>
								<div class="clearfix"></div>

								<div class="collapse in" id="collapseExample3" aria-expanded="true" style="">
									<ul class="nav" id="sharedListsUL">
										<!-- Shared Lists for logined user go here -->
									</ul>
								</div>
							</div>
						</li>
					</ul>
					<!-- Create list form go here -->
					<div class="input-group" style="margin-left: 5px; margin-top: 5px;">
						<input type="text" class="form-control" id="createListText" placeholder="Create a list">
						<span class="input-group-btn">
							<button onclick="createList()" class="btn btn-danger" style="height: 38px; margin-right: 10px;">
								<i class="la la-plus"></i>	
							</button>
						</span>
					</div>	
				</div>
			</div>
			<div class="main-panel">
				<div class="content" id="mainContentDiv">
					<div class="container-fluid">
						<div style="padding-left: 15px;">
							<h4 class="page-title" id="listTitleLabel">No List Selected</h4>
						</div>		
						<div class="col-md-12">
							<div  id="allTaskDiv" style="float:left; width:100%">
								<!-- Tasks -->
								<div class="card card-tasks" id="tasksOfList">
									<div class="card-header ">
										<div style="float:left">
											<h4 class="card-title">Tasks</h4>
											<p class="card-category">To Do List</p>
										</div>
										<div style="float:right;" id="refreshButtonDiv">
											<!-- Refresh Button -->
										</div>
									</div>
									<div class="card-body ">
										<div class="table-full-width">
											<table class="table">
												<thead>
													<tr>
														<th>
<!-- 															<div class="form-check"> -->
<!-- 																<label class="form-check-label"> -->
<!-- 																	<input class="form-check-input  select-all-checkbox" type="checkbox" data-select="checkbox" data-target=".task-select"> -->
<!-- 																	<span class="form-check-sign"></span> -->
<!-- 																</label> -->
<!-- 															</div> -->
														</th>
														<th>Task</th>
														<th><!-- Action --></th>
													</tr>
												</thead>
												<tbody id="tasksDiv">
													
												</tbody>
											</table>
										</div>
									</div>
									<div class="card-footer ">
										<div class="stats">
											<i class="now-ui-icons loader_refresh spin"></i>
											<div class="input-group">
												<input class="form-control" id="addTaskText" placeholder="Add a task to this list">
												<span class="input-group-btn">
													<button id="addTaskBtn" class="btn btn-danger" style="height: 38px; margin-right: 10px;">
														<i class="la la-plus"></i>
													</button>
												</span>
											</div>
										</div>
									</div>
								</div>	
							</div>
							<div style="float: right;" id="taskDetailsMainDiv">
								<!-- SubTasks -->
								<div class="card card-tasks" id="tasksOfList">
									<div class="card-header ">
										<div class="input-group" id="updateTaskNameDiv">

										</div>
									</div>
									<div class="input-group" style="padding: 15px 15px 0 15px;">
										<input type="text" class="form-control" id="myDateTimePicker"  placeholder="Select the last date"/>
										<div  id="setTimeTaskDiv">
										
										</div>
									</div>
									<div class="card-body ">
										<div class="table-full-width">
											<table class="table">
												<thead>
													<tr>
														<th>
<!-- 															<div class="form-check"> -->
<!-- 																<label class="form-check-label"> -->
<!-- 																	<input class="form-check-input  select-all-checkbox" type="checkbox" data-select="checkbox" data-target=".subtask-select"> -->
<!-- 																	<span class="form-check-sign"></span> -->
<!-- 																</label> -->
<!-- 															</div> -->
														</th>
														<th>Sub-task</th>
														<th><!-- Action --></th>
													</tr>
												</thead>
												<tbody id="subTasksDiv">
													
												</tbody>
											</table>
											<div class="input-group" style="padding-left: 20px; padding-right: 20px;">
												<input class="form-control" id="addSubTaskText" placeholder="Add a subtask to this task">
												<span class="input-group-btn">
													<button id="addSubTaskBtn" class="btn btn-danger" style="height: 38px; margin-right: 0px;">
														<i class="la la-plus"></i>
													</button>
												</span>
											</div>
										</div>
										<div class="form-group" id="taskNotesDiv">
											
										</div>
									</div>
									<div class="card-footer">
										<!-- COMMENT TEST -->
										<div id="commentsDiv">
											<!-- Comments go here -->													
										</div>
										<div class="stats">
											<div class="input-group">
												<input class="form-control" id="writeCommentText" placeholder="Write a comment for this task">
												<span class="input-group-btn">
													<button id="writeCommentBtn" class="btn btn-danger" style="height: 38px; margin-right: 0px;">
														<i class="la la-comment"></i>
													</button>
												</span>
											</div>											
										</div>
									</div>
								</div>	
							</div>
						</div>
					</div>
				</div>
				<div class="content" id="searchContentDiv">
					<div class="container-fluid">
						<div style="padding-left: 15px;">
							<h4 class="page-title" id="searchTitleLabel">-</h4>
						</div>		
						<div class="col-md-12">
							<div id="searchResultDiv" style="float:left; width:100%">
									
							</div>
						</div>
					</div>
				</div>
				<footer class="footer">
					<div class="container-fluid">
						<nav class="pull-left">
							<ul class="nav">
								<li class="nav-item">
									<a class="nav-link" href="http://www.github.com/gokhanyurur">
										Github
									</a>
								</li>
								<li class="nav-item">
									<a class="nav-link" href="#">
										Help
									</a>
								</li>
								<li class="nav-item">
									<a class="nav-link" href="#">
										Licenses
									</a>
								</li>
							</ul>
						</nav>
						<div class="copyright ml-auto">
							2018, Wunderlist Clone by <a href="https://www.linkedin.com/in/gokhanyurur/">Gokhan Yurur</a>
						</div>				
					</div>
				</footer>
			</div>
	</div>
 	<!-- Modal -->
<!-- 	<div class="modal fade" id="modalUpdate" tabindex="-1" role="dialog" aria-labelledby="modalUpdatePro" aria-hidden="true"> -->
<!-- 		<div class="modal-dialog modal-dialog-centered" role="document"> -->
<!-- 			<div class="modal-content"> -->
<!-- 				<div class="modal-header bg-primary"> -->
<!-- 					<h6 class="modal-title"><i class="la la-frown-o"></i> Under Development</h6> -->
<!-- 					<button type="button" class="close" data-dismiss="modal" aria-label="Close"> -->
<!-- 						<span aria-hidden="true">&times;</span> -->
<!-- 					</button> -->
<!-- 				</div> -->
<!-- 				<div class="modal-body text-center">									 -->
<!-- 					<p>Currently the pro version of the <b>Ready Dashboard</b> Bootstrap is in progress development</p> -->
<!-- 					<p> -->
<!-- 						<b>We'll let you know when it's done</b></p> -->
<!-- 				</div> -->
<!-- 				<div class="modal-footer"> -->
<!-- 					<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	
	<div class="modal fade" id="shareListModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <div class="col-md-12">
	        	<h5 style="font-size: 15px; font-weight: bold; text-align: center;" class="modal-title" id="exampleModalLabel">Edit the List</h5>
	        </div>
	      </div>
	      <div class="modal-body">
	      	<div id="editListNameDiv">
	      		
	      	</div>
	      	<h6 style="padding: 10px 0 0 3px;">Share</h6>
	      	<div class="input-group">
	      		<select id="userSelect" class="shareListSelect" name="states[]" multiple="multiple" style="width: 90%;">
	      			<!-- List of all users for sharing a list -->
				</select>
 	      		<button id="shareListBtn" class="btn btn-danger" style="padding: 4px;">Share</button>
	      	</div>
	      </div>
	      <div class="modal-body" style="padding-top: 0;">
	      	<h6 style="padding: 0 0 0 3px;">Shared With</h6>
	      	<table class="table table-head-bg-primary mt-4">
				<thead>
					<tr>
						<th scope="col">Username</th>
						<th scope="col" style="text-align: right;">Action</th>
					</tr>
				</thead>
				<tbody id="sharedListUserTable">
					
				</tbody>
			</table>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
	<script type="text/javascript">
		
		$(document).ready(function(){
			$('#myDateTimePicker').datetimepicker();
		});
		$(".shareListSelect").select2({
		    placeholder: "Select a user"
		});
			
	</script>
</body>
</html>