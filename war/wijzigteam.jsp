<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>wijzig team</title>
</head>
<body>
	<form action="/sport">
		<table id="wijzigteam">
		
			<tr>
				<th>Teamcode</th>
				<th>Teamomschrijving</th>
			</tr>
			<tr>
				<td><label for="teamomschrijving" >${team.teamcode}</label>
				<td><input type="text" name="teamomschrijving" value="${team.omschrijving}"></td>
				<input type="hidden" name ="teamcode" value="${team.teamcode}">
			</tr>

		</table>
		<input type="submit" name="wijzig_team" value="Wijzig">

	</form>

</body>
</html>