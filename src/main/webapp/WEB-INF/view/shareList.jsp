<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Share List</title>
</head>
<body>
	<h3>Share List!</h3><br>
	<form:form action="shareListProcess" modelAttribute = "theSharedList" method="POST">
		<label>SharedList Id: </label>
		<form:input path="sharedList"/>
		<br>
		<label>Shared User Id: </label>
		<form:input path="sharedWith"/>
		<br>
		<button type="submit">Share List</button>
	</form:form>
</body>
</html>