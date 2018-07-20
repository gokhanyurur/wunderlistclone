var isSubTaskPgOpen;
		$(document).ready(function() {
			getAllLists();
			$("#tasksOfList").hide();
 			$("#taskDetailsMainDiv").hide();
 			isSubTaskPgOpen = false;
		});
	
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
			
		function getTasks(id,name){
			var data = {
					listId: id,
					listName: name 
			}
			
			$.ajax({
				url:"getTasksList",
				data: data,
				success : function(result){
					$("#tasksOfList").show();
					$("#tasksDiv").html("");
					$('#addTaskBtn').attr('onClick', 'addTaskToList('+data.listId+');');	
					//console.log("success");
					$("#allTaskDiv").css("width","100%");
					$("#taskDetailsMainDiv").hide();
					isSubTaskPgOpen = false;
					//debugger;
					$("#refreshButtonDiv").html("");
					$("#refreshButtonDiv").append("<button onclick='getTasks(\""+data.listId+"\",\""+data.listName+"\")' class='btn btn-danger'>"+
													"<i class='la la-refresh'></i>"+
 												  "</button>");
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
	 		        									"<button type='button' data-toggle='tooltip' title='Remove' class='btn btn-link btn-simple-danger'>"+
															"<i class='la la-times'></i>"+
														"</button>"+
	 		        									"<button type='button' data-toggle='tooltip' title='Star' class='btn btn-link btn-simple-warning'>"+
	 		        										"<i class='la la-star-o'></i>"+
	 		        									"</button>"+
														"<button onclick='showTaskDetails(\""+ result[index].id +"\")' type='button' data-toggle='tooltip' title='Edit' class='btn btn-link btn-simple-primary'>"+
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
		
		function showTaskDetails(task){
			var data = {
					taskId: task
			}
			
			if(!isSubTaskPgOpen){
				$("#allTaskDiv").css("width","60%");
				$("#taskDetailsMainDiv").show();
				$("#updateTaskNameDiv").html("");
				$("#updateTaskNameDiv").append("<input type='text' class='form-control form-control-lg' id='taskDetailsTaskText' onblur='updateTaskName(\""+data.taskId+"\")' placeholder='Selected Task'>");
	 			isSubTaskPgOpen = true;
	 			$('#addSubTaskBtn').attr('onClick', 'addSubTaskToTask('+data.taskId+');');	
				$.ajax({
					url:"getTaskDetails",
					type: "POST",
					data: data,
					success : function(result){
						console.log("Tasks Details 1");
						$('#taskDetailsTaskText').val(result.task);
						//debugger;
						getSubTasksFromTaskId(result.id);
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
					debugger;
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
					        									"<button type='button' data-toggle='tooltip' title='Remove' class='btn btn-link btn-simple-danger'>"+
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
		
		function updateTaskName(task){
			
			var data = {
					taskId : task,
					taskName : $("#taskDetailsTaskText").val()
			}
			
			if ( $.trim( $('#taskDetailsTaskText').val() ) != '' ){
				debugger;
				$.ajax({
					url:"updateTask",
					type: "POST",
					data: data,
					success : function(){
						//$('#addTaskText').val("");
						
						console.log("Task is updated.");		
					}
				});
			}else{
				alert('Task name can not be empty.');
			}
			
		}
		
		