package sportIO;

import java.util.ArrayList;


import sport.Lid;
import sport.Team;
import sport.TeamSpeler;

public interface IoMethodenInterface {
	public void voegLidToe(Lid lid);
	public void verwijderLid(Lid lid);
	public void wijzigLid(Lid lid);
	public Lid getLid(String spelerscode);
	public ArrayList<Lid> getLeden();
	public void voegTeamToe(Team team);
	public void verwijderTeam(Team team);
	public void wijzigTeam(Team team);
	public Team getTeam(String teamcode);
	public ArrayList<Team> getTeams();
	public void setTeamspeler(Team team, Lid lid);
	public void verwijderTeamspeler(Team team, Lid lid);
	public ArrayList<TeamSpeler> getTeamspelers();
	public ArrayList<Lid> getSpelersVanTeam(Team team);
	public ArrayList<Team> getTeamsVanSpeler( Lid lid);
	
	
	
	
	
}
