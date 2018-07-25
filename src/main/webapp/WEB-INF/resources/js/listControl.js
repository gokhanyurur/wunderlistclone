var isSubTaskPgOpen;
		$(document).ready(function() {
			getAllLists();
			getAllSharedLists();
			$("#tasksOfList").hide();
 			$("#taskDetailsMainDiv").hide();
 			isSubTaskPgOpen = false;
		});

		
		$(window).on('load resize', function() {
		    width = this.innerWidth;
		    height = this.innerHeight;
		    if(width<=820){
		    	windowSmall();
		    }else if(isSubTaskPgOpen && width>820){
		    	windowLarge();
		    }
		});
		
		function windowSmall(){
			$("#allTaskDiv").css("width","100%");
			$("#taskDetailsMainDiv").css("width","100%");
		}
		
		function windowLarge(){
			$("#allTaskDiv").css("width","60%");
			$("#taskDetailsMainDiv").css("width","35%");
		}
		
		function getAllLists(){
			$.ajax({
				url: "getLists",
				success : function(result){
					//console.log("success");
					$('#createListText').val("");
					$("#listsUL").html("");
		        
	 		        jQuery.each(result, function(index, value){
 						$("#listsUL").append("<li>"+
 												"<a href='#' onclick='getTasks(\""+ result[index].id +"\",\""+result[index].listName+"\")'>"+
 													"<span class='link-collapse'>"+result[index].listName+"</span>"+
 												"</a>"+
 											"</li>");
 		            });
	 		        
				}, 
				error : function(){
					console.log("error");
				}
			});
		}
		
		function getAllSharedLists(){
			$.ajax({
				url: "getSharedLists",
				success : function(result){
					$("#sharedListsUL").html("");
					
					var sizeOfArrray = Array.isArray(result) ? result.length : Object.keys(result).length;
					if(sizeOfArrray > 0){
						jQuery.each(result, function(index, value){
	 						$("#sharedListsUL").append("<li>"+
	 												"<a href='#' onclick='getTasks(\""+ result[index].id +"\",\""+result[index].listName+"\")'>"+
	 													"<span class='link-collapse'>"+result[index].listName+"</span>"+
	 												"</a>"+
	 											"</li>");
	 		            });
					}else{
						$("#sharedListsUL").append("<li>"+
									"<a href='#'>"+
										"<span class='link-collapse'>There is no list shared with you.</span>"+
									"</a>"+
								"</li>");
					}
	 		        
	 		        
				}, 
				error : function(){
					console.log("error");
				}
			});
		}
		function isLoginedUserOwnerOf(lid){

			var bool = false;
			data = {
					listId: lid
			}
			
			$.ajax({
				url:"isLoginedUserOwnerOf",
				data: data,
				async :false,
				success : function(result){

					if(result){
						bool = true;
					}
				}
			});
			debugger;
			return bool;
		}
		
		
			
		function getTasks(id,name){
			var data = {
					listId: id,
					listName: name 
			}
			windowSmall();
			$.ajax({
				url:"getTasksList",
				data: data,
				success : function(result){
					$("#tasksOfList").show();
					$("#tasksDiv").html("");
					$('#addTaskBtn').attr('onClick', 'addTaskToList('+data.listId+');');	
					$('#shareListBtn').attr('onClick', 'shareList('+data.listId+',\''+data.listName+'\');');
					$("#taskDetailsMainDiv").hide();
					isSubTaskPgOpen = false;
					//debugger;
					$("#refreshButtonDiv").html("");
//					$("#refreshButtonDiv").append("<button alt='Refresh the list' onclick='refreshTasks(\""+data.listId+"\",\""+data.listName+"\")' class='btn btn-danger btn-round' style='margin-right: 10px;'>"+
//													"<i class='la la-refresh'></i>"+
// 												  "</button>"+
// 												  (isLoginedUserOwnerOf(data.listId) ? '<button alt="Share the list" data-toggle="modal" data-target="#shareListModal" onclick=\'fillSharedListModal(\''+data.listId+'\',\''+data.listName+'\')\' class="btn btn-primary btn-round" style="margin-right: 10px;"><i class="la la-share-alt"></i></button>' : '')+
//// 												  "<button alt='Share the list' data-toggle='modal' data-target='#shareListModal' onclick='fillSharedListModal(\""+data.listId+"\",\""+data.listName+"\")' class='btn btn-primary btn-round' style='margin-right: 10px;'>"+
////													"<i class='la la-share-alt'></i>"+
////												  "</button>"+
//												  "<button alt='Delete the list' onclick='removeList(\""+data.listId+"\",\""+data.listName+"\")' class='btn btn-danger btn-round' style='margin-right: 10px;'>"+
//													"<i class='la la-remove'></i>"+
//												  "</button>");
					
					$("#refreshButtonDiv").append("<button alt='Refresh the list' onclick='refreshTasks(\""+data.listId+"\",\""+data.listName+"\")' class='btn btn-danger btn-round' style='margin-right: 10px;'>"+
							"<i class='la la-refresh'></i>"+
							  "</button>");
							debugger;
							var check=isLoginedUserOwnerOf(data.listId);
							  if(check){
								  console.log("true geldi");
								  debugger;
								  $("#refreshButtonDiv").append("<button alt='Share the list' data-toggle='modal' data-target='#shareListModal' onclick='fillSharedListModal(\""+data.listId+"\",\""+data.listName+"\")' class='btn btn-primary btn-round' style='margin-right: 10px;'>"+
											"<i class='la la-share-alt'></i>"+
											  "</button>"+
											  "<button alt='Delete the list' onclick='removeList(\""+data.listId+"\",\""+data.listName+"\")' class='btn btn-danger btn-round' style='margin-right: 10px;'>"+
												"<i class='la la-remove'></i>"+
											  "</button>");
							  }else{
								  console.log(isLoginedUserOwnerOf(data.listId));
							  }

						  
					jQuery.each(result, function(index, value){
						$("#tasksDiv").append("<tr>"+
	 		        							"<td>"+
	 		        								"<div class='form-check'>"+
	 		        									"<label class='form-check-label'>"+
	 		        										"<input class='form-check-input task-select' type='checkbox'>"+
	 		        											"<span class='form-check-sign'></span>"+
	 		        									"</label>"+
	 		        								"</div>"+
	 		        							"</td>"+
	 		        							"<td>"+result[index].task+"</td>"+
	 		        							"<td class='td-actions text-right'>"+
	 		        								"<div class='form-button-action'>"+
	 		        									"<button onclick='removeTask(\""+result[index].id+"\",\""+data.listId+"\",\""+data.listName+"\")' type='button' data-toggle='tooltip' title='Remove' class='btn btn-link btn-simple-danger'>"+
	 		        										"<i class='la la-times'></i>"+
	 		        									"</button>"+
	 		        									(result[index].stared ? '<button type="button" data-toggle="tooltip" title="Star" class="btn btn-link btn-simple-warning" onclick="starTheTask(\''+result[index].id+'\',\''+data.listId+'\',\''+data.listName+'\')"><i class="la la-star"></i></button>' : '<button type="button" data-toggle="tooltip" title="Star" class="btn btn-link btn-simple-warning" onclick="starTheTask(\''+result[index].id+'\',\''+data.listId+'\',\''+data.listName+'\')"><i class="la la-star-o"></i></button>')+
	 		        									"<button onclick='showTaskDetails(\""+data.listId+"\",\""+ result[index].id +"\",\""+data.listName+"\")' type='button' data-toggle='tooltip' title='Edit' class='btn btn-link btn-simple-primary'>"+
	 		        										"<i class='la la-edit'></i>"+
	 		        									"</button>"+
	 		        								"</div>"+
												"</td>"+
											"</tr>");
								
					});
					$("#listTitleLabel").text(data.listName);
				}, 
				error : function(){
					console.log("error");
				}
			});
		}
		
//		function isLoginedUserOwnerOf(lid){
//			var bool = false;
//			data = {
//					listId: lid
//			}
//			
//			$.ajax({
//				url:"isLoginedUserOwnerOf",
//				data: data,
//				success : function(result){
//					debugger;
//					if(result){
//						bool = true;
//					}
//					return bool;
//				}
//			});
//		}
		
		
		
		function refreshTasks(id,name){
			var data = {
					listId: id,
					listName: name 
			}
			$("#tasksDiv").html("");
			$.ajax({
				url:"getTasksList",
				data: data,
				success : function(result){
					jQuery.each(result, function(index, value){
						$("#tasksDiv").append("<tr>"+
	 		        							"<td>"+
	 		        								"<div class='form-check'>"+
	 		        									"<label class='form-check-label'>"+
	 		        										"<input class='form-check-input task-select' type='checkbox'>"+
	 		        											"<span class='form-check-sign'></span>"+
	 		        									"</label>"+
	 		        								"</div>"+
	 		        							"</td>"+
	 		        							"<td>"+result[index].task+"</td>"+
	 		        							"<td class='td-actions text-right'>"+
	 		        								"<div class='form-button-action'>"+
	 		        									"<button onclick='removeTask(\""+result[index].id+"\",\""+data.listId+"\",\""+data.listName+"\")' type='button' data-toggle='tooltip' title='Remove' class='btn btn-link btn-simple-danger'>"+
	 		        										"<i class='la la-times'></i>"+
	 		        									"</button>"+
	 		        									(result[index].stared ? '<button type="button" data-toggle="tooltip" title="Star" class="btn btn-link btn-simple-warning" onclick="starTheTask(\''+result[index].id+'\',\''+data.listId+'\',\''+data.listName+'\')"><i class="la la-star"></i></button>' : '<button type="button" data-toggle="tooltip" title="Star" class="btn btn-link btn-simple-warning" onclick="starTheTask(\''+result[index].id+'\',\''+data.listId+'\',\''+data.listName+'\')"><i class="la la-star-o"></i></button>')+
//	 		        									"<input type='checkbox' id='testcb' name='testcb' value='testcb'/>"+
	 		        									"<button onclick='showTaskDetails(\""+data.listId+"\",\""+ result[index].id +"\",\""+data.listName+"\")' type='button' data-toggle='tooltip' title='Edit' class='btn btn-link btn-simple-primary'>"+
	 		        										"<i class='la la-edit'></i>"+
	 		        									"</button>"+
	 		        								"</div>"+
												"</td>"+
											"</tr>");
								
					});
					$("#listTitleLabel").text(data.listName);
				}
			});
		}
				
		function addTaskToList(listid){
			var data = {
					listId : listid,
					taskName: $("#addTaskText").val()
			}
			if ( $.trim( $('#addTaskText').val() ) != '' ){
				$.ajax({
					url:"addTask",
					type: "POST",
					data: data,
					success : function(){
						$('#addTaskText').val("");
						console.log("Task added to the list.");
						getTasks(data.listId);			
					}
				});
			}else{
				alert('You have to write a task.');
			}
		}
		
		function createList(){
			var data = {
					listname: $("#createListText").val()
			}
			if ( $.trim( $('#createListText').val() ) != '' ){
				$.ajax({
					url:"createList",
					type: "POST",
					data: data,
					success : function(){
						console.log("List created.")
						getAllLists();
					}
				});
			}else{
				alert('You have to write the name of the list.');
			}
			
		}
		
		function showTaskDetails(list,task,listN){
			var data = {
					listId: list,
					taskId: task,
					listName: listN
			}
			
			if(!isSubTaskPgOpen){
				
				if(this.innerWidth>820){
					windowLarge();
				}else{
					windowSmall();
				}
				
				$("#taskDetailsMainDiv").show();
				$("#updateTaskNameDiv").html("");
				$("#subTasksDiv").html("");	
				$("#commentsDiv").html("");	
				$("#updateTaskNameDiv").append("<input type='text' class='form-control form-control-lg' id='taskDetailsTaskText' onblur='updateTaskName(\""+data.listId+"\",\""+data.taskId+"\",\""+data.listName+"\")' placeholder='Selected Task'>");
	 			isSubTaskPgOpen = true;
	 			$('#addSubTaskBtn').attr('onClick', 'addSubTaskToTask('+data.taskId+');');
	 			$('#writeCommentBtn').attr('onClick', 'addCommentToTask('+data.taskId+');');
				$.ajax({
					url:"getTaskDetails",
					type: "POST",
					data: data,
					success : function(result){
						$('#taskDetailsTaskText').val(result.task);
						getSubTasksFromTaskId(result.id);
						getCommentsFromTaskId(result.id);
						
						$("#taskNotesDiv").html("");
						$("#taskNotesDiv").append("<label for='notes'>Notes</label>"+
												"<textarea class='form-control' id='notes' rows='3' onblur='saveTaskNotes(\""+data.taskId+"\")'></textarea>");
						
						$("#notes").val(result.notes);
					}
				});
			}else{
				$("#allTaskDiv").css("width","100%");
				$("#taskDetailsMainDiv").hide();
				isSubTaskPgOpen = false;
			}
						
		}
		
		function addSubTaskToTask(task){
			var data = {
					taskId : task,
					subTaskName: $("#addSubTaskText").val()
			}
			if ( $.trim( $('#addSubTaskText').val() ) != '' ){
				$('#addSubTaskText').val("");
				$.ajax({
					url:"addSubTask",
					type: "POST",
					data: data,
					success : function(){
						$('#addSubTaskText').val("");
						console.log("SubTask added to the task.");	
					},
					complete : function(){
						getSubTasksFromTaskId(data.taskId);	
					}	
				});
			}else{
				alert('You have to write a task.');
			}
		}
		
		function addCommentToTask(task){
			var data = {
					taskId : task,
					commentContent: $("#writeCommentText").val()
			}
			if ( $.trim( $('#writeCommentText').val() ) != '' ){
				$('#writeCommentText').val("");
				$.ajax({
					url:"writeComment",
					type: "POST",
					data: data,
					success : function(result){
						$('#writeCommentText').val("");
						console.log("Comment is written to the task.");
						
					},
					complete : function(){
						getCommentsFromTaskId(data.taskId);	
					}	
				});
			}else{
				alert('You have to write a task.');
			}
		}
		
		function getSubTasksFromTaskId(task){
			var data = {
					taskId: task
			}
			
			$.ajax({
				url:"getSubTasks",
				type: "POST",
				data: data,
				success : function(result){
					
					var sizeOfArrray = Array.isArray(result) ? result.length : Object.keys(result).length;
					//debugger;
					if(sizeOfArrray > 0){
						$('#addSubTaskText').val("");
						$("#subTasksDiv").html("");	
						jQuery.each(result, function(index, value){
							$("#subTasksDiv").append("<tr>"+
					        							"<td>"+
					        								"<div class='form-check'>"+
					        									"<label class='form-check-label'>"+
					        										"<input class='form-check-input subtask-select' type='checkbox'>"+
					        											"<span class='form-check-sign'></span>"+
					        									"</label>"+
					        								"</div>"+
					        							"</td>"+
					        							"<td>"+result[index].subTask+"</td>"+
					        							"<td class='td-actions text-right'>"+
					        								"<div class='form-button-action'>"+
					        									"<button type='button' data-toggle='tooltip' title='Remove' onclick='removeSubTask(\""+data.taskId+"\",\""+result[index].id+"\")' class='btn btn-link btn-simple-danger'>"+
																"<i class='la la-times'></i>"+
															"</button>"+
															"</div>"+
														"</td>"+
													"</tr>");
						});
					}		
				}
			});

		}
		
		function updateTaskName(list,task,listN){
			
			var data = {
					listId: list,
					taskId : task,
					listName: listN,
					taskName : $("#taskDetailsTaskText").val()
			}
			
			if ( $.trim( $('#taskDetailsTaskText').val() ) != '' ){
				//debugger;
				$.ajax({
					url:"updateTask",
					type: "POST",
					data: data,
					success : function(){
						//$('#addTaskText').val("");		
						console.log("Task is updated.");
						refreshTasks(data.listId,data.listName);
					}
				});
			}else{
				alert('Task name can not be empty.');
			}
			
		}
		
		function removeSubTask(task,subTask){
			data = {
				taskId: task,
				subTaskId: subTask
			}
			if(confirm('Are you sure you want to remove this subtask?')){
				$.ajax({
					url:"removeSubTask",
					type: "POST",
					data: data,
					success : function(result){
						$("#subTasksDiv").html("");
						getSubTasksFromTaskId(data.taskId);
					}
				});
			}else{
				//Do nothing
			}
			
		}
		
		function removeTask(task,list,name){
			data = {
				taskId: task,
				listId: list,
				listName: name
			}
			
			if (confirm('Are you sure you want to remove this task?')) {
				$.ajax({
					url:"removeTask",
					type: "POST",
					data: data,
					success : function(result){
						$("#allTasksDiv").html("");
						getTasks(data.listId, data.listName);
					}
				});
			} else {
			    // Do nothing!
			}
			
		}
		
		function starTheTask(task,list,name){
			data = {
				taskId: task,
				listId: list,
				taskName: name
			}
			
			$.ajax({
				url:"starTheTask",
				type: "POST",
				data: data,
				success : function(result){
					$("#allTasksDiv").html("");
					getTasks(data.listId, data.listName);
				}
			});
		}
		
		function saveTaskNotes(task){
			data = {
				taskId: task,
				taskNotes: $("#notes").val()
			}
			
			$.ajax({
				url:"saveTaskNotes",
				type: "POST",
				data: data,
				success : function(result){
					console.log("Notes are saved.");
				}
			});
		}
		
		function getCommentsFromTaskId(task){
			data = {
				taskId: task
			}
			
			$.ajax({
				url:"getAllComments",
				type: "POST",
				data: data,
				success : function(result){
					$("#commentsDiv").html("");	
					var sizeOfArrray = Array.isArray(result) ? result.length : Object.keys(result).length;
					if(sizeOfArrray > 0){
						$("#commentsDiv").html("");	
						debugger;
						jQuery.each(result, function(index, value){
							$("#commentsDiv").append("<div class='card'>"+
														"<div class='card-body'>"+
															"<div class='row'>"+
																"<div class='col-md-3 photo'>"+
																	"<p data-letters='"+result[index].writtenBy.charAt(0).toUpperCase()+"'></p>"+
																"</div>"+
																"<div class='col-md-9'>"+
																	"<p>"+
																		"<a class='float-left' href='#'><strong>"+result[index].writtenBy+"</strong></a>"+
																	"</p>"+
																	"<div class='clearfix'></div>"+
																	"<p class='text-secondary' style='font-size: 12px;'>15 Minutes Ago</p>"+
																	"<p>"+result[index].comment+"</p>"+
																"</div>"+
															"</div>"+
														"</div>"+
													"</div>");
						});
					}
				}
			});
			
		}
		
		function removeList(id,name){
			data = {
					listId: id,
					listName: name
			}
			
			if(confirm('Are you sure you want to remove this list: '+data.listName)){
				$.ajax({
					url:"removeList",
					type: "POST",
					data: data,
					success : function(){
						$("#allTasksDiv").hide();
						$("#listTitleLabel").text("No list selected.");
						getAllLists();
						window.location.href = "http://localhost:8080/wunderlistclone/lists";
					}
				});
			}else{
				//Do nothing
			}
		}
		
		function fillSharedListModal(id,name){
			data = {
					listId: id,
					listName: name
			}
			
			$("#listTitleText").val(data.listName);
			$("#userSelect").html("");
			$.ajax({
				url:"getAllUsers",
				type: "POST",
				data: data,
				success : function(result){
					//debugger;
					jQuery.each(result, function(index, value){
						var option = document.createElement("option");
						option.text = result[index].username;
						option.value = result[index].id;
						var select = document.getElementById("userSelect");
						select.appendChild(option);
					});
					fillSharedListUserTable(data.listId, data.listName);
				}
			});
			
		}
		
		function fillSharedListUserTable(id,name){
			data = {
					listId: id,
					listName: name
			}
			$.ajax({
				url: "getSharedUsers",
				type: "POST",
				data: data,
				success: function(result){
					$("#sharedListUserTable").html("");
					var sizeOfArrray = Array.isArray(result) ? result.length : Object.keys(result).length;
					if(sizeOfArrray > 0){
						jQuery.each(result, function(index, value){
							$("#sharedListUserTable").append("<tr>"+
																"<td>"+result[index].username+"</td>"+
																	"<td align='right'>"+
																		"<button onclick='removeSharedUser(\""+data.listId+"\",\""+data.listName+"\",\""+result[index].username+"\")' class='btn btn-danger' style='padding: 4px;'>Remove</button>"+
																	"</td>"+
															"</tr>");
						});
					}else{
						$("#sharedListUserTable").append("<tr>"+
								"<td>You are not sharing this list with anyone</td>"+
									"<td align='right'></td>"+
							"</tr>");
					}				
				}
			});
		}
		
		
		function shareList(id,name){
			var Array = $("#userSelect").val();
			data = {
					listId: id,
					listName: name,
					userIdList: JSON.stringify(Array)
			}
			console.log(data.listId+" will be shared");
			debugger;
			$.ajax({
				url: "shareList",
				type: "POST",
				data: data,
				success: function(){
					$("#userSelect").empty();
					fillSharedListUserTable(data.listId, data.listName);
					fillSharedListModal(data.listId, data.listName);
				}
			});
		}
		
		function removeSharedUser(lid,lname,uname){
			data = {
					listId: lid,
					listName: lname,
					userName: uname
			}
			
			$.ajax({
				url: "removeSharedUser",
				type: "POST",
				data: data,
				success: function(){
					fillSharedListUserTable(data.listId, data.listName);
					fillSharedListModal(data.listId, data.listName);
				}
			});
		}
		
		
		
		
		
		
		