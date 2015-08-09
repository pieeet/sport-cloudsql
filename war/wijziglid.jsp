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
	<form action="/sport" method="get">
		<table id="wijziglid">
			<tr>
				<td>Roepnaam</td>
				<td><input type="text" name="roepnaam" value="${lid.roepnaam}"></td>
			</tr>
			<tr>
				<td>Tussenvoegsels</td>
				<td><input type="text" name="tussenvoegsels" value="${lid.tussenvoegsels}"></td>
			</tr>
			<tr>
				<td>Achternaam</td>
				<td><input type="text" name="achternaam" value="${lid.achternaam}"></td>
			</tr>
			<tr>
				<td>Adres</td>
				<td><input type="text" name="adres" value="${lid.adres}"></td>
			</tr>
			<tr>
				<td>Postcode</td>
				<td><input type="text" name="postcode" value="${lid.postcode}"></td>
			</tr>
			<tr>
				<td>Woonplaats</td>
				<td><input type="text" name="woonplaats" value="${lid.adres}"></td>
			</tr>
			<tr>
				<td>Telefoon</td>
				<td><input type="text" name="telefoon" value="${lid.telefoon}"></td>
			</tr>
			<tr>
				<td>Geboortedatum</td>
				<td><input type="date" name="geboortedatum" value="${lid.geboortedatum}"></td>
			</tr>
			<tr>
				<td>Geslacht</td>
				<c:choose>
					<c:when test="${lid.geslacht eq 'man'}">
						<td><input type="radio" name="geslacht" value="man" checked>Man<br>
						<input type="radio" name="geslacht" value="vrouw">Vrouw</td>
					</c:when>
					<c:otherwise>
						<td><input type="radio" name="geslacht" value="man">Man<br>
						<input type="radio" name="geslacht" value="vrouw" checked>Vrouw</td>
					</c:otherwise>
				</c:choose>
			</tr>
			
		</table>
		<input 	type="hidden" 
				name="spelerscode" 
				value="${lid.spelerscode }">
		<input type="submit" name="wijzig_lid" value="wijzig">
	
	
	</form>

</body>
</html>