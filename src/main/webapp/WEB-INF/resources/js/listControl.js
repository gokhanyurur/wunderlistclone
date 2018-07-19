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
					debugger;
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
		
		function showTaskDetails(task){
			var data = {
					taskId: task
			}
			
			if(!isSubTaskPgOpen){
				$("#allTaskDiv").css("width","60%");
				$("#taskDetailsMainDiv").show();
	 			isSubTaskPgOpen = true;
			}else{
				$("#allTaskDiv").css("width","100%");
				$("#taskDetailsMainDiv").hide();
				isSubTaskPgOpen = false;
			}
			
			
			$.ajax({
				url:"getTaskDetailsProcess",
				type: "POST",
				data: data,
				success : function(){
					console.log("Tasks.")

				}
			});
			
		}