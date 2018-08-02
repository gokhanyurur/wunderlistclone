<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<!-- 	<div id="login-box"> -->
<!-- 		<h2>Register!</h2> -->
		
<%-- 		<c:if test="${not empty error}"> --%>
<%-- 			<div class="error">${error}</div> --%>
<%-- 		</c:if> --%>
<%-- 		<c:if test="${not empty msg}"> --%>
<%-- 			<div class="msg">${msg}</div> --%>
<%-- 		</c:if> --%>
		
<%-- 		<form:form action="registerUser" modelAttribute = "user" method="POST"> --%>
<!-- 			<table> -->
<!-- 				<tr> -->
<!-- 					<td>Username:</td> -->
<%-- 					<td> <form:input path="username"/><td> --%>
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td>Email:</td> -->
<%-- 					<td><form:input path="email"/></td> --%>
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td>Password:</td> -->
<%-- 					<td><form:password path="password"/></td> --%>
<!-- 				</tr> -->
<!-- 				<tr> -->
<!-- 					<td><button type="submit">Register</button></td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
<%-- 		</form:form> --%>
<!-- 	</div> -->
<div class="container" style="margin-top: 150px;">
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="card text-center card  bg-default mb-3">
              <div class="card-header">
                REGISTER
              </div>
              	<c:if test="${not empty error}"> 
					<div class="error">${error}</div>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="msg">${msg}</div>
				</c:if>
               	<form:form action="registerUser" modelAttribute = "user" method="POST">
	               	<div class="card-body">
						<form:input path="username" cssClass="form-control input-sm chat-input" placeholder="Username" required="required"/>
	                	<br>
						<form:input path="email" cssClass="form-control input-sm chat-input" placeholder="Email" required="required"/>
						<br>
						<form:input path="emailConf" cssClass="form-control input-sm chat-input" placeholder="Email Again" required="required"/>
						<br>
						<form:password path="password" cssClass="form-control input-sm chat-input" placeholder="Password" required="required"/>
						<br>
						<form:password path="passwordConf" cssClass="form-control input-sm chat-input" placeholder="Password Again" required="required"/>
	              	</div>
		            <div class="card-footer text-muted">
		           		<input class="btn btn-danger" name="submit" type="submit" value="Register" />
		           		<a href="login" class="btn btn-primary">Login</a>
		            </div>
               		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
               </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>