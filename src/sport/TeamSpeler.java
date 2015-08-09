package sport;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TeamSpeler implements Serializable {
	private String teamSpelerCode;
	private String spelerscode;
	private String teamcode;

	/**
	 * 
	 */
	public TeamSpeler() {
	}
	
	/**
	 * @param spelerscode
	 * @param teamcode
	 */
	public TeamSpeler(String spelerscode, String teamcode) {
		this.spelerscode = spelerscode;
		this.teamcode = teamcode;
		this.teamSpelerCode = spelerscode + teamcode;
	}
	public String getTeamSpelerCode() {
		return teamSpelerCode;
	}
	public void setTeamSpelerCode(String teamSpelerCode) {
		this.teamSpelerCode = teamSpelerCode;
	}
	public String getSpelerscode() {
		return spelerscode;
	}
	public void setSpelerscode(String spelerscode) {
		this.spelerscode = spelerscode;
	}
	public String getTeamcode() {
		return teamcode;
	}
	public void setTeamcode(String teamcode) {
		this.teamcode = teamcode;
	}
	
	
	
	
}
