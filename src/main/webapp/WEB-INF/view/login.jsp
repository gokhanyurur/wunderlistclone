<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta name="_csrf_parameter" content="_csrf" />
	<meta name="_csrf_header" content="X-CSRF-TOKEN" />
	<meta name="_csrf" content="e62835df-f1a0-49ea-bce7-bf96f998119c" />
<title>Login Page</title>
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
</head>
<body onload='document.loginForm.username.focus();'>

<div class="container" style="margin-top: 150px;">
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="card text-center card  bg-default mb-3">
              <div class="card-header">
                LOGIN
              </div>
              	<c:if test="${not empty error}"> 
					<div class="error">${error}</div>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="msg">${msg}</div>
				</c:if>
               	<form name='loginForm' action="<c:url value='j_spring_security_check' />" method='POST'>
	               	<div class="card-body">
						<input class="form-control input-sm chat-input" type='text' name='username' value='' placeholder="Username">
	                	</br>
						<input class="form-control input-sm chat-input" type='password' name='password' placeholder="Password"/>
	              	</div>
		            <div class="card-footer text-muted">
		           		<input class="btn btn-danger" name="submit" type="submit" value="Login" />
		           		<a href="register" class="btn btn-primary" >Register</a>
		            </div>
               		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
               </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>