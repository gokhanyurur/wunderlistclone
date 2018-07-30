<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WS</title>
</head>
<body>
	<script type="text/javascript">
		
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
			//sendCustomMessage("Welcome");
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
			alert(event.data);
		}

		window.addEventListener("load", init, false);
	</script>
	<textarea id="textBox" rows="5" cols="20"></textarea>
	<button onclick="sendMessage();">Send Message</button>
</body>
</html>