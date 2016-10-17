<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>
	<h1>totalBooks : ${totalBooks}</h1>

	<br>
	<p>Click <a href="<spring:url value='/web/main' />">[MAIN]</a> to see a greeting.</p>

	<br>
	
	<hr>
	<table border="1">
		<tr>
			<td>num</td>
			<td>id</td>
			<td>name</td>
			<td>isbn</td>
			<td>author</td>
			<td>pages</td>
		</tr>

		<c:forEach items="${list}" var="book" varStatus="status">
			<tr>
				<td><c:out value="${status.count}" /></td>
				<td><c:out value="${book.id}" escapeXml="false" /></td>
				<td>${book.name}</td>
				<td>${book.isbn}</td>
				<td>${book.author}</td>
				<td>${book.pages}</td>
			</tr>
		</c:forEach>
	</table>
	<hr>

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