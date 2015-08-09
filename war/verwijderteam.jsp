<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>verwijder team</title>
    </head>
    <body>
        <h1>Verwijder Team: ${team.teamcode}</h1>
        <p>Het team: ${team.omschrijving } wordt permanent verwijderd. Dit kan niet ongedaan worden gemaakt. Is dat wat u wilt?</p>
        <form action="/sport" method="post">
            <input type="submit" name="verwijder_team" value="Verwijder permanent">
            <input type="hidden" name="teamcode" value="${team.teamcode}">
        </form>
        
        
    </body>
</html>