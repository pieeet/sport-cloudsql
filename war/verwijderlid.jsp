<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verwijder lid</title>
    </head>
    <body>
        <h1>Verwijder lid: ${lid.naam}</h1>
        <p>Het lid: ${lid.naam} wordt permanent verwijderd. Dit kan niet ongedaan worden gemaakt. Is dat wat u wilt?</p>
        <form action="/sport" method="post">
            <input type="submit" name="verwijder_lid" value="Verwijder permanent">
            <input type="hidden" name="spelerscode" value="${lid.spelerscode}">
        </form>
        
        
    </body>
</html>