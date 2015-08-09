package sport;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Team implements Serializable {
	private String teamcode;
	private String omschrijving;
	
	public Team(String teamcode, String omschrijving) {
		this.teamcode = teamcode;
		this.omschrijving = omschrijving;
		
	}
	
	public Team() {
		this.teamcode = "";
		this.omschrijving = "";
	}
	

	public String getTeamcode() {
		return teamcode;
	}

	public void setTeamcode(String teamcode) {
		this.teamcode = teamcode;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}
	
	
	
	
}

