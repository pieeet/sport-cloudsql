<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>nieuw team</title>
</head>
<body>
	<form action="/sport" method="post">
		<table id="nieuwteam">
		<tr>
			<td><label for="teamcode">Teamcode</label></td>
			<td><input type="text" name="teamcode"></td>
		</tr>
		<tr>
			<td><label for="teamomschrijving">Teamomschrijving</label></td>
			<td><input type="text" name="teamomschrijving"></td>
		</tr>
		<tr>
			<td><input type="submit" name="nieuw_team" value="Voeg Toe">
		</tr>
		</table>
	
	
	
	</form>
</body>
</html>