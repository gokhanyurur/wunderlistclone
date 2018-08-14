<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<html>
<body>
<h2>Wunderlistclone!</h2>
<br>
<a href="login"><spring:message code="login" text=""/></a> | <a href="register"><spring:message code="createaccount" text=""/></a>
<c:redirect url="/login"/>
</body>
</html>
