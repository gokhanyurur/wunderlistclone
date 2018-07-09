<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Task to the List</title>
</head>
<body>
	<h3>Add Task!</h3><br>
	<form:form action="addTaskProcess" modelAttribute = "theTask" method="POST">
		<label>Task: </label>
		<form:input path="task"/>
		<br>
		<button type="submit">Add Task</button>
	</form:form>
</body>
</html>