<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>teams lid</title>
</head>
<body>
	<h1>Teams van ${lid.naam}</h1>
	
	 
	<c:choose>
		<c:when test="${isLeeg}">
			<p>${lid.naam} zit in geen enkel team</p>
		</c:when>
		<c:otherwise> 
			<ul>
				<c:forEach var="team" items="${teams}">
					<li>${team.omschrijving }</li>
				</c:forEach>
			</ul>
		 </c:otherwise>
	</c:choose>
	
	
	<p><a href="/sport">terug</a> naar overzicht</p>
</body>
</html>