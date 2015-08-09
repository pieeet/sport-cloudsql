<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Leden van team: ${team.omschrijving }</h1>
		<table id="teamspelers">
			
				<h2>Teamspelers<h2>
			
			<c:forEach var="lid" items="${teamleden }">
				<tr>
					<td>${lid.naam }</td>
					<td><a href="/sport?spelerscode=${lid.spelerscode}&amp;teamcode=${team.teamcode}&amp;verwijder_teamspeler=+">Verwijder speler</a>
			</c:forEach>
		</table>
		
		<h2>Voeg leden toe</h2>
		<table id="ledentabel">
			<c:forEach var="lid" items="${niet_teamleden}">
				<tr>
					<td>${lid.naam}</td>
					<td><a href="/sport?spelerscode=${lid.spelerscode}&teamcode=${team.teamcode}&amp;voeg_teamspeler_toe=+">Voeg Toe</a></td>
				</tr>
			</c:forEach>
			
		</table>
		<p><a href="/sport">Terug naar overzicht</a>

</body>
</html>