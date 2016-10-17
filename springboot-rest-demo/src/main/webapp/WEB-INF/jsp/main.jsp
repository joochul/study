<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>
	<h1>session_id : ${session_id}</h1>


	<p>Click <a href="<spring:url value='/web/admin' />">[ADMIN]</a> to see a greeting.</p>
    <br>

	<table>
	<tr>
		<td>
			<form action="/login" method="DELETE">
				<input type="submit" value="Sign Out" />
			</form>
		</td>
	</tr>
	</table>
	
</body>
</html>