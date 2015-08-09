<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>nieuw lid</title>
</head>
<body>
	<body>
        <h1>Nieuw lid</h1>
        <form action="/sport" method="post">
            <table id="nieuwlidform">
                
                <tr>
                    <td><label for="roepnaam">roepnaam:</label></td>
                    <td><input type="text" name="roepnaam"></td>
                </tr>
                <tr>
                    <td><label for="tussenvoegsels">tussenvoegsels</label></td>
                    <td><input type="text" name="tussenvoegsels"></td>
                </tr>
                <tr>
                    <td><label for="achternaam">achternaam</label></td>
                    <td><input type="text" name="achternaam"></td>
                </tr>
                <tr>
                    <td><label for="adres">adres</label></td>
                    <td><input type="text" name="adres"></td>
                </tr>
                <tr>
                    <td><label for="postcode">postcode</label></td>
                    <td><input type="text" name="postcode"></td>
                </tr>
                <tr>
                    <td><label for="woonplaats">woonplaats</label></td>
                    <td><input type="text" name="woonplaats"></td>
                </tr>
                <tr>
                    <td><label for="telefoon">telefoon</label></td>
                    <td><input type="text" name="telefoon"></td>
                </tr>
                <tr>
                    <td><label for="dag">geboortedatum<br>(jjjj-mm-dd*)</label></td>
                    <td><input type="date" name="gebDatum"></td>
            
                </tr>
                <tr>
                	<td><label for="geslacht">Geslacht</label></td>
                	<td><select name="geslacht">
                		<option value="man">Man</option>
                		<option value="vrouw">Vrouw</option>
                		</select>
                	</td>
                	
                </tr>
                <tr>
                    <td><input type="submit" name="knop_nieuw_lid" value="Voer gegevens in">
                    
                </tr>
                
                
            </table>
            <p>* Indien u gebruik maakt van Internet Explorer of Firefox browser</p>
            
        </form>
</body>
</html>