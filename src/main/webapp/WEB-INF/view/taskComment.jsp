<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Write comment to the Task</title>
</head>
<body>
	<h3>Write comment!</h3><br>
	<form:form action="taskCommentProcess" modelAttribute = "theComment" method="POST">
		<label>Comment: </label>
		<form:input path="comment"/>
		<br>
		<button type="submit">Write comment</button>
	</form:form>
</body>
</html>