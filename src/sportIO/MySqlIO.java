package sportIO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.appengine.api.utils.SystemProperty;

import sport.Lid;
import sport.Team;
import sport.TeamSpeler;

public class MySqlIO implements IoMethodenInterface {
	String url;
	Connection dbc;
	PreparedStatement ps;
	ResultSet rs;
	
	/**
	 * 
	 */
	public MySqlIO() {
		this.url = getDbUrl();
	}

	private String getDbUrl() {
		String dbUrl = null;
	    try {
	      // check op productie of development omgeving
	      if (SystemProperty.environment.value() ==
	          SystemProperty.Environment.Value.Production) {
	        // Load the class that provides the new "jdbc:google:mysql://" prefix.
	        Class.forName("com.mysql.jdbc.GoogleDriver");
	        dbUrl = "jdbc:google:mysql://pdvsport:mysql/sport?user=root";
	      } else {
	        // Local MySQL instance to use during development.
	        Class.forName("com.mysql.jdbc.Driver");
	        dbUrl = "jdbc:mysql://127.0.0.1:3306/sport?user=root";

	        // Alternatively, connect to a Google Cloud SQL instance using:
	        // jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/sport?user=root
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return dbUrl;
	}
	
	private void maakConnectie() throws SQLException {
		this.dbc = DriverManager.getConnection(url);
	}
	
	private void sluitAlles() throws SQLException {
		if (ps != null) {
			ps.close();
		}
		if (rs != null) {
			rs.close();
		}
		if (dbc != null) {
			dbc.close();
		}
	}

	@Override
	public void voegLidToe(Lid lid) {
		String preparedQuery = "INSERT INTO speler (spelerscode, spelersnr, roepnaam, " +
                "tussenvoegsels, achternaam, adres, postcode, woonplaats, telefoon, geboortedatum) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
         try {
            maakConnectie();
            this.ps = dbc.prepareStatement(preparedQuery);
            ps.setString(1, lid.getSpelerscode());
            ps.setInt(2, lid.getNr());
            ps.setString(3, lid.getRoepnaam());
            ps.setString(4, lid.getTussenvoegsels());
            ps.setString(5, lid.getAchternaam());
            ps.setString(6, lid.getAdres());
            ps.setString(7, lid.getPostcode());
            ps.setString(8, lid.getWoonplaats());
            ps.setString(9, lid.getTelefoon());
            ps.setDate(10, java.sql.Date.valueOf(lid.getGeboortedatum()));
            ps.executeUpdate();
            sluitAlles();
        } 
        catch (SQLException ex) {
        	ex.printStackTrace();
        }
    }

	@Override
	public void verwijderLid(Lid lid) {
		String preparedQuery = "DELETE FROM speler WHERE spelerscode = ?";
        try {
            maakConnectie();
            this.ps = dbc.prepareStatement(preparedQuery);
            ps.setString(1, lid.getSpelerscode());
            ps.executeUpdate();
            sluitAlles();
 
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
	}

	@Override
	public void wijzigLid(Lid lid) {
		try {
            String preparedSQL = "UPDATE speler SET " +
                    " roepnaam = ?, " +          //1
                    " tussenvoegsels = ?, " +    //2
                    " achternaam = ?, " +        //3
                    " adres = ?, " +             //4
                    " postcode = ?, " +          //5
                    " woonplaats = ?" +          //6
                    " telefoon = ?" +            //7
                    " geboortedatum = ?" +       //8
                    " geslacht = ?" +            //9
                    "WHERE spelerscode = ?";     //10
            
            maakConnectie();
            this.ps = dbc.prepareStatement(preparedSQL);
            ps.setString(1, lid.getRoepnaam());
            ps.setString(2, lid.getTussenvoegsels());
            ps.setString(3, lid.getAchternaam());
            ps.setString(4, lid.getAdres());
            ps.setString(5, lid.getPostcode());
            ps.setString(6, lid.getWoonplaats());
            ps.setString(7, lid.getTelefoon());
            ps.setString(8, lid.getGeboortedatum());
            ps.setString(9, lid.getGeslacht());
            ps.setString(10, lid.getSpelerscode());
            ps.executeUpdate();
            sluitAlles();
            
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
	}

	@Override
	public Lid getLid(String spelerscode) {
		Lid lid = new Lid();
		lid.setSpelerscode(spelerscode);
		String preparedSQL = "SELECT * FROM speler WHERE spelerscode = ?";
		try {
			maakConnectie();
            this.ps = dbc.prepareStatement(preparedSQL);
            ps.setString(1, spelerscode);
            this.rs = ps.executeQuery();
            rs.first();
            lid.setNr(rs.getInt("spelersnr"));
            lid.setRoepnaam(rs.getString("roepnaam"));
            lid.setTussenvoegsels(rs.getString("tussenvoegsels"));
            lid.setAchternaam(rs.getString("achternaam"));
            lid.setAdres(rs.getString("adres"));
            lid.setPostcode(rs.getString("postcode"));
            lid.setWoonplaats(rs.getString("woonplaats"));
            lid.setGeboortedatum(rs.getString("geboortedatum"));
            lid.setTelefoon(rs.getString("telefoon"));
            sluitAlles();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
		return lid;
	}

	@Override
	public ArrayList<Lid> getLeden() {
		ArrayList<Lid> ledenlijst = new ArrayList<>();
        String preparedQuery = "SELECT spelerscode, spelersnr, roepnaam," +
                "tussenvoegsels, achternaam, adres, postcode, woonplaats, "
                + "telefoon, geboortedatum FROM speler";
        try {
            maakConnectie();
            this.ps = dbc.prepareStatement(preparedQuery);
            this.rs = ps.executeQuery();
            while (rs.next()){
                Lid lid = new Lid();
                lid.setSpelerscode(rs.getString("spelerscode"));
                lid.setNr(rs.getInt("spelersnr"));
                lid.setRoepnaam(rs.getString("roepnaam"));
                lid.setTussenvoegsels(rs.getString("tussenvoegsels"));
                lid.setAchternaam(rs.getString("achternaam"));
                lid.setAdres(rs.getString("adres"));
                lid.setPostcode(rs.getString("postcode"));
                lid.setWoonplaats(rs.getString("woonplaats"));
                lid.setTelefoon(rs.getString("telefoon"));
                lid.setGeboortedatum(rs.getString("geboortedatum"));
                ledenlijst.add(lid);
            }
            sluitAlles();
         }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return ledenlijst;
	}

	@Override
	public void voegTeamToe(Team team) {
		String preparedSQL = "INSERT INTO team (teamcode, teamomschrijving)" + 
	               "values (?, ?)";
	   try {
	       maakConnectie();
	       this.ps = dbc.prepareStatement(preparedSQL);
	       ps.setString(1, team.getTeamcode());
	       ps.setString(2, team.getOmschrijving());
	       ps.executeUpdate();
	       sluitAlles();
	   } catch (SQLException ex) {
		   ex.printStackTrace();
	   }
	}

	@Override
	public void setTeamspeler(Team team, Lid lid) {
	        String preparedQuery = "INSERT INTO teamspeler (teamcode, spelerscode)"
	                + " VALUES (?, ?)";
        try {
            maakConnectie();
            this.ps = dbc.prepareStatement(preparedQuery);
            ps.setString(1, team.getTeamcode());
            ps.setString(2, lid.getSpelerscode());
            ps.executeUpdate();
            sluitAlles();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
	}

	@Override
	public void verwijderTeamspeler(Team team, Lid lid) {
        String preparedQuery = "DELETE FROM teamspeler WHERE spelerscode = ?";
        try {
        	maakConnectie();
            this.ps = dbc.prepareStatement(preparedQuery);
            ps.setString(1, lid.getSpelerscode());
            ps.executeUpdate();
            sluitAlles();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
	}

	@Override
	public ArrayList<TeamSpeler> getTeamspelers() {
		ArrayList<TeamSpeler> teamspelers = new ArrayList<>();
		String preparedQuery = "SELECT * FROM teamspeler";
        try {
        	maakConnectie();
            this.ps = dbc.prepareStatement(preparedQuery);
            this.rs = ps.executeQuery();
            while (rs.next()) {
                TeamSpeler ts = new TeamSpeler(rs.getString("spelerscode"), 
                		rs.getString("teamcode"));
                teamspelers.add(ts);
             }
            sluitAlles();
        }
         catch (SQLException ex) {
        	 ex.printStackTrace();
        }
        return teamspelers;
	}

	@Override
	public ArrayList<Lid> getSpelersVanTeam(Team team) {
		ArrayList<Lid> teamspelers = new ArrayList<>();
		ArrayList<String> spelerscodes = new ArrayList<>();
		String preparedQuery = "SELECT spelerscode FROM teamspeler WHERE teamcode = ?";
        try {
            maakConnectie();
            this.ps = dbc.prepareStatement(preparedQuery);
            ps.setString(1, team.getTeamcode());
            this.rs = ps.executeQuery();
            while (rs.next()) {
                spelerscodes.add(rs.getString("spelerscode"));
             }
            sluitAlles();
            for (String spelerscode: spelerscodes) {
            	teamspelers.add(this.getLid(spelerscode));
            }
        }
         catch (SQLException ex) {
        	 ex.printStackTrace();
        }
        return teamspelers;
	}

	@Override
	public ArrayList<Team> getTeamsVanSpeler(Lid lid) {
		ArrayList<Team> teams = new ArrayList<>();
		ArrayList<String> teamcodes = new ArrayList<>();
		String preparedQuery = "SELECT teamcode FROM teamspeler WHERE spelerscode = ?";
        try {
            maakConnectie();
            this.ps = dbc.prepareStatement(preparedQuery);
            ps.setString(1, lid.getSpelerscode());
            this.rs = ps.executeQuery();
            while (rs.next()) {
                teamcodes.add(rs.getString("teamcode"));
             }
            sluitAlles();
            for (String teamcode: teamcodes) {
            	teams.add(this.getTeam(teamcode));
            }
        }
         catch (SQLException ex) {
        	 ex.printStackTrace();
        }
        return teams;
	}

	@Override
	public void verwijderTeam(Team team) {
		String preparedQuery = "DELETE FROM team WHERE teamcode = ?";
        try {
        	maakConnectie();
        	this.ps = dbc.prepareStatement(preparedQuery);
            ps.setString(1, team.getTeamcode());
            ps.executeUpdate();
            ps.close();
            dbc.close();
         }
        catch(SQLException e) {
        	e.printStackTrace();
        }
	}

	@Override
	public void wijzigTeam(Team team) {
		String preparedQuery = "UPDATE team " +
                "SET teamomschrijving = ? " +
                "WHERE teamcode = ?";
        try {   
            maakConnectie();
            this.ps = dbc.prepareStatement(preparedQuery);
            ps.setString(1, team.getOmschrijving());
            ps.setString(2, team.getTeamcode());
            ps.executeUpdate();
            ps.close();
            dbc.close();
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
	}

	@Override
	public Team getTeam(String teamcode) {
		Team team = new Team();
		String preparedQuery = "SELECT * FROM team WHERE teamcode = ?";
        try {
            maakConnectie();
            this.ps = dbc.prepareStatement(preparedQuery);
            ps.setString(1, teamcode);
            this.rs = ps.executeQuery();
            rs.first();
            team.setOmschrijving(rs.getString("teamomschrijving"));
            team.setTeamcode(rs.getString("teamcode"));
            ps.close();
            rs.close();
            dbc.close();

        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        return team;
	}

	@Override
	public ArrayList<Team> getTeams() {
		ArrayList<Team> teamlijst = new ArrayList<>();
        String preparedQuery = "SELECT * FROM team";
        
        try {
            maakConnectie();
            this.ps = dbc.prepareStatement(preparedQuery);
            this.rs = ps.executeQuery();
            while (rs.next()) {
                Team team = new Team();
                team.setTeamcode(rs.getString("teamcode"));
                team.setOmschrijving(rs.getString("teamomschrijving"));
                teamlijst.add(team);
            }
            ps.close();
            rs.close();
            dbc.close();
        }
        catch (SQLException e) {
        	e.printStackTrace();
        }
        return teamlijst;
	}
}
