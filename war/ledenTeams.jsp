<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>leden+teams</title>
	</head>
<body>
	<h2>Leden</h2>
	<c:choose>
		<c:when test="${admin.ledenIsLeeg}">
			<p>Er zijn nog geen leden ingevoerd</p>
		</c:when>
	
		<c:otherwise>
		
			<table id="ledentabel">
				<tr>
					<th>Spelerscode</th>
					<th>Spelersnr</th>
					<th>Naam</th>
					<th>Adres</th>
					<th>Postcode</th>
					<th>Woonplaats</th>
					<th>Telefoon</th>
					<th>Geboortedatum</th>
					<th>Geslacht</th>
				</tr>
				
			
				<c:forEach var="lid" items="${admin.leden}">
					<tr>
						<td> ${lid.spelerscode}</td>
						<td> ${lid.nr}</td>
			    		<td> ${lid.roepnaam} ${lid.tussenvoegsels} ${lid.achternaam}</td>
			    		<td> ${lid.adres}</td>
			    		<td> ${lid.postcode}</td>
			    		<td> ${lid.woonplaats}</td>
			    		<td> ${lid.telefoon}</td>
			    		<c:choose>
				    		<c:when test="${lid.geboortedatum == '01-01-1900' }">
				    			<td>geen (correcte) datum</td>
				    		</c:when>
				    		<c:otherwise>
				    			<td> ${lid.geboortedatum}</td>
				    		</c:otherwise>
			    		</c:choose>
			    		<td> ${lid.geslacht}</td>
			    		<td><a href="/sport?spelerscode=${lid.spelerscode}&amp;lid_wijzig=+">wijzig<a/></td>
	 		        	<td><a href="/sport?spelerscode=${lid.spelerscode}&amp;lid_verwijder=+">verwijder<a/></td>
	 		        	<td><a href="/sport?spelerscode=${lid.spelerscode}&amp;teams_van_lid=+">Teams<a/></td>
			    	</tr>
				</c:forEach>
			</table>
	
		</c:otherwise>
	</c:choose>

	<form action="invoerlid.jsp" method="get">
	    <input type="submit" value="Lid toevoegen">
	</form>
	
	<h2>Teams</h2>
	<c:choose>
		<c:when test="${admin.teamsIsLeeg}">
			<p>Er zijn nog geen teams ingevoerd</p>
		</c:when>
		<c:otherwise>
			<table id="teamlijst">
				<tr>
					<th>Teamcode</th>
					<th>Teamomschrijving</th>
				</tr>
				<c:forEach var="team" items="${admin.teams}">
					<tr>
						<td>${team.teamcode }</td>
						<td>${team.omschrijving }</td>
						<td><a href="/sport?teamcode=${team.teamcode}&amp;team_wijzig=+">wijzig<a/></td>
						<td><a href="/sport?teamcode=${team.teamcode}&amp;team_verwijder=+">verwijder<a/></td>
						<td><a href="/sport?teamcode=${team.teamcode}&amp;leden_van_team=+">leden<a/></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<form action="invoerteam.jsp" method="get">
	    <input type="submit" value="Team toevoegen">
	</form>
	<a href="https://github.com/pieeet/sport-cloudsql" target="_blank">Code op Github</a>

</body>
</html>