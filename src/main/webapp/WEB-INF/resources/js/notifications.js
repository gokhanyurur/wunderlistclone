var seeAllNotif = false;

$(document).ready(function() {
	showUnviewedNotifications();			
	
	//WEB SOCKET STARTS
	var wsUrl = "ws://localhost:8080/wunderlistclone/ws";
	var webSocket;

	function init() {
		
		webSocket = new WebSocket(wsUrl);
		webSocket.onopen = function(evt) {
			onOpen(event)
		};
		webSocket.onclose = function(evt) {
			onClose(event)
		};
		webSocket.onmessage = function(evt) {
			onMessage(event)
		};
		webSocket.onerror = function(evt) {
			onError(event)
		};
	}

	function onOpen(event){
		console.log("OnOpen Event");
	}

	function onClose(event) {
		console.log("OnClose Event");
	}

	function onError(event) {
		console.log("OnError Event");
	}

	function sendMessage() {
		webSocket.send(textBox.value);
	}

	function sendCustomMessage(text) {
		webSocket.send(text);
	}

	function onMessage(event) {
		notify(event.data);
	}
	init();
});

function showUnviewedNotifications(){
	$.ajax({
		url:"getUnviewedNotifications",
		success : function(result){
			//debugger;
			jQuery.each(result, function(index, value){
				console.log(result[index].notification);
				var dateString = new  Date(result[index].notifiedat).toString();
				dateString = dateString.split(' ').slice(0, 5).join(' ');
				
				$("#notificationsBoxDiv").append("<a href='#'>"+
													(result[index].type.type == "REMINDER" ? '<div class="notif-icon notif-danger"> <i class="la la-bell"></i> </div>' : '<div class="notif-icon notif-primary"> <i class="la la-bell"></i> </div>')+
													"<div class='notif-content'>"+
														"<span class='block'>"+result[index].notification+"</span>"+
														"<span class='time'>"+dateString+"</span>"+
													"</div>"+	
												"</a>");
			});
			$("#notificationCountSpan").text(result.length);
			$("#notificationCountDiv").text(youhaveText+" "+result.length+" "+newnotificationText);
		}
	});
}

function seeAllNotifications(){
	seeAllNotif = !seeAllNotif;
	$("#allNotificationsBoxDiv").html("");
	$.ajax({
		url:"seeAllNotifications",
		success : function(result){
			debugger;
			if(seeAllNotif){
				$("#seeAllNotifIcon").removeClass("la la-angle-right").addClass("la la-angle-down");
				jQuery.each(result, function(index, value){
//					var dateHuman = new Date(result[index].notifiedat);
//					var dateString = dateHuman.toUTCString();
//					dateString = dateString.split(' ').slice(0, 5).join(' ');
					
					var dateString = new  Date(result[index].notifiedat).toString();
					dateString = dateString.split(' ').slice(0, 5).join(' ');
					
					$("#allNotificationsBoxDiv").append("<a href='#'>"+
															(result[index].type.type == "REMINDER" ? '<div class="notif-icon notif-danger"> <i class="la la-bell"></i> </div>' : '<div class="notif-icon notif-primary"> <i class="la la-bell"></i> </div>')+
																"<div class='notif-content'>"+
																	"<span class='block'>"+result[index].notification+"</span>"+
																	"<span class='time'>"+dateString+"</span>"+
																"</div>"+	
														"</a>");
				});
				$("#notificationsBoxDiv").html("");
				$("#notificationCountSpan").text("0");
				$("#notificationCountDiv").text(youhaveText+" 0 "+newnotificationText);
			}else{
				$("#seeAllNotifIcon").removeClass("la la-angle-down").addClass("la la-angle-right");
			}			
		}
	});
}

$(function() {
    $('.dropdown').on({
        "click": function(event) {
          if ($(event.target).closest('.dropdown-toggle').length) {
            $(this).data('closable', true);
          } else {
            $(this).data('closable', false);
          }
        },
        "hide.bs.dropdown": function(event) {
          hide = $(this).data('closable');
          $(this).data('closable', true);
          return hide;
        }
    });
});





function notify(text){
	var notificationC = parseInt($("#notificationCountSpan").text());
	
	$.notify({
		icon: 'la la-bell',
		title: reminderText,
		message: text,
	},{
		type: 'danger',
		placement: {
			from: "bottom",
			align: "right"
		},
		time: 1000,
	});
	
	notificationC++;
	$("#notificationCountSpan").text(notificationC.toString());
	$("#notificationCountDiv").text(youhaveText+" "+notificationC.toString()+" "+newnotificationText);
	$("#notificationsBoxDiv").append("<a href='#'>"+
										"<div class='notif-icon notif-danger'> <i class='la la-bell'></i> </div>"+
											"<div class='notif-content'>"+
												"<span class='block'>"+text+"</span>"+
												"<span class='time'>"+afewsecondsagoText+"</span>"+ 
											"</div>"+
											
									"</a>");
	
	
}

//WEB SOCKET ENDS