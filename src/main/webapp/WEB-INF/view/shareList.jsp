<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
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
		<label>Lists to be shared: </label>

		<form:select path="sharedList">
			<c:forEach var="list" items="${allLists}">
				<form:option value="${list.id}">${list.listName}</form:option>
			</c:forEach>
		</form:select>
		<br>
		<label>User to be shared: </label>
		<form:select path="sharedUser_ID">
			<c:forEach var="user" items="${allUsers}">
				<form:option value="${user.id}">${user.username}</form:option>
			</c:forEach>
		</form:select>
		<br>
		<button type="submit">Share List</button>
	</form:form>
<%-- 	<form action="shareListProcess" method="POST"> --%>
<!-- 		<label>Lists to be shared: </label> -->
<!-- 		<input name = "listid" id="listid" > -->
<!-- 		<br> -->
<!-- 		<label>User to be shared: </label> -->
<!-- 		<input name = "userid" id ="userid"> -->
<!-- 		<br> -->
<!-- 		<button type="submit">Share List</button> -->
<%-- 	</form> --%>
</body>
</html>