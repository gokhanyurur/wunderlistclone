<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
	<h3>Register Page!</h3><br>
	<form:form action="registerUser" modelAttribute = "user" method="POST">
		<label>Username: </label>
		<form:input path="username"/>
		<br>
		<label>Email: </label>
		<form:input path="email"/>
		<br>
		<label>Password: </label>
		<form:password path="password"/>
		<button type="submit">Register</button>
	</form:form>
</body>
</html>