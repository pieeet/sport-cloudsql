package sport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import sportIO.DatastoreIO;
import sportIO.IoMethodenInterface;
import sportIO.MySqlIO;

@SuppressWarnings("serial")
public class Administratie implements Serializable, IoMethodenInterface {
//	private DatastoreIO io;
	private MySqlIO io;
	private MemcacheService cache;
	private ArrayList<Lid> leden;
	private ArrayList<Team> teams;
	private ArrayList<TeamSpeler> teamspelers;
	private boolean ledenIsLeeg;
	private boolean teamsIsLeeg;
	
	@SuppressWarnings("unchecked")
	public Administratie() {
//		io = new DatastoreIO;
		io = new MySqlIO();
		cache = MemcacheServiceFactory.getMemcacheService();
		leden = (ArrayList<Lid>) cache.get("leden");
		if (leden == null) {
			leden = io.getLeden();
			cache.put("leden", leden);
		}
		
		if (leden.isEmpty()) {
			ledenIsLeeg = true;
		}
			
		teams = (ArrayList<Team>) cache.get("teams");
		if (teams == null) {
			teams = io.getTeams();
			cache.put("teams", teams);
		}
		
		if (teams.isEmpty()) {
			teamsIsLeeg = true;
		}
		
		teamspelers = (ArrayList<TeamSpeler>) cache.get("teamspelers");
		if (teamspelers == null) {
			teamspelers = io.getTeamspelers();
			cache.put("teamspelers", teamspelers);
		}
	}
	

	public boolean isLedenIsLeeg() {
		return ledenIsLeeg;
	}


	public void setLedenIsLeeg(boolean ledenIsLeeg) {
		this.ledenIsLeeg = ledenIsLeeg;
	}


	public boolean isTeamsIsLeeg() {
		return teamsIsLeeg;
	}


	public void setTeamsIsLeeg(boolean teamsIsLeeg) {
		this.teamsIsLeeg = teamsIsLeeg;
	}


	public ArrayList<Lid> getLeden() {
		Collections.sort(leden, new LidComparator());
		return leden;
	}
	
	public ArrayList<Team> getTeams() {
		return teams;
	}

	public void setLeden(ArrayList<Lid> leden) {
		this.leden = leden;
	}

	public void setTeams(ArrayList<Team> teams) {
		this.teams = teams;
	}
	
	public void voegLidToe(Lid lid) {
		if (leden.isEmpty()) {
			lid.setNr(1);
		}
		else {
			int nr = leden.get(leden.size() - 1).getNr() + 1;
			lid.setNr(nr);
		}
		leden.add(lid);
		cache.put("leden", leden);
		io.voegLidToe(lid);
	}
	
	public Lid getLid(String spelerscode) {
		Lid lid = null;
		for (Lid l: leden) {
			if (l.getSpelerscode().equals(spelerscode)) {
				lid = l;
			}
		}
		if (lid == null) {
			lid = io.getLid(spelerscode);
		}
		
		return lid;
	}
	
	public void verwijderLid(Lid lid) {
		for (Lid l: leden) {
			if (l.getSpelerscode().equals(lid.getSpelerscode())) {
				leden.remove(l);
				cache.put("leden", leden);
				break;
			}
		}
		ArrayList<TeamSpeler> teamspelersNieuw = new ArrayList<TeamSpeler>();
		boolean lijstVeranderd = false;
		for (TeamSpeler ts: teamspelers) {
			if (!ts.getSpelerscode().equals(lid.getSpelerscode())) {
				teamspelersNieuw.add(ts);
			} else {
				lijstVeranderd = true;
			}
		}
		if (lijstVeranderd) {
			teamspelers = teamspelersNieuw;
			cache.put("teamspelers", teamspelers);
		}
		io.verwijderLid(lid);
	}
	
	public void wijzigLid(Lid lid) {
		ArrayList<Lid> ledenNieuw = new ArrayList<Lid>();
		for (Lid l: leden) {
			if (!l.getSpelerscode().equals(lid.getSpelerscode())) {
				ledenNieuw.add(l);
			} else {
				ledenNieuw.add(lid);
			}
		}
		leden = ledenNieuw;
		cache.put("leden", leden);
		io.wijzigLid(lid);
	}
	
	public void voegTeamToe(Team team) {
		teams.add(team);
		cache.put("teams", teams);
		io.voegTeamToe(team);
	}
	
	public void verwijderTeam(Team team)  {
		for (Team t: teams) {
			if (t.getTeamcode().equals(team.getTeamcode())) {
				teams.remove(t);
				cache.put("teams", teams);
				break;
			}
		}
		ArrayList<TeamSpeler> teamspelersNieuw = new ArrayList<TeamSpeler>();
		boolean lijstVeranderd = false;
		for (TeamSpeler ts: teamspelers) {
			if (!ts.getTeamcode().equals(team.getTeamcode())) {
				teamspelersNieuw.add(ts);
			} else {
				lijstVeranderd = true;
			}
		}
		if (lijstVeranderd) {
			teamspelers = teamspelersNieuw;
			cache.put("teamspelers", teamspelers);
		}
		
		ArrayList<Lid> teamspelers = this.getSpelersVanTeam(team);
		for (Lid lid : teamspelers) {
			this.verwijderTeamspeler(team, lid);
			io.verwijderTeamspeler(team, lid);
		}
		io.verwijderTeam(team);
	}
	
	public void wijzigTeam(Team team) {
		ArrayList<Team> teamsNieuw = new ArrayList<Team>();
		for (Team t: teams) {
			if (!t.getTeamcode().equals(team.getTeamcode())) {
				teamsNieuw.add(t);
			} else {
				teamsNieuw.add(team);
			}
		}
		teams = teamsNieuw;
		cache.put("teams", teams);
		io.wijzigTeam(team);
	}
	
	public Team getTeam(String teamcode) {
		Team team = null;
		for (Team t: teams) {
			if (t.getTeamcode().equals(teamcode)) {
				team = t;
				break;
			}
		}
		if (team == null) {
			team = io.getTeam(teamcode);
		}
		return team;
	}
	
	@Override
	public ArrayList<Lid> getSpelersVanTeam(Team team) {
		ArrayList<Lid> spelersVanTeam = new ArrayList<Lid>();
		for (TeamSpeler ts: teamspelers) {
			if (ts.getTeamcode().equals(team.getTeamcode())) {
				for (Lid l: leden) {
					if (ts.getSpelerscode().equals(l.getSpelerscode())) {
						spelersVanTeam.add(l);
					}
				}
			}
		}
		Collections.sort(spelersVanTeam, new LidComparator());
		return spelersVanTeam;
	}
	
	public ArrayList<Lid> getNietTeamspelers(Team team) {
		ArrayList<Lid> nietTeamspelers = new ArrayList<Lid>();
		ArrayList<Lid> teamspelers = this.getSpelersVanTeam(team);
		for (Lid lid: leden) {
			boolean isTeamlid = false;
			for (Lid teamlid: teamspelers) {
				if (lid.getSpelerscode().equals(teamlid.getSpelerscode())) {
					isTeamlid = true;
				}
			}
			if (!isTeamlid) {
				nietTeamspelers.add(lid);
			}
		}
		Collections.sort(nietTeamspelers, new LidComparator());
		return nietTeamspelers;
	}
	
	@Override
	public ArrayList<TeamSpeler> getTeamspelers() {
		return teamspelers;
	}
	
	@Override
	public ArrayList<Team> getTeamsVanSpeler(Lid lid) {
		ArrayList<Team> teamsVanSpeler = new ArrayList<Team>();
		for (TeamSpeler teamspeler: teamspelers) {
			if (teamspeler.getSpelerscode().equals(lid.getSpelerscode())) {
				for (Team t: teams) {
					if (teamspeler.getTeamcode().equals(t.getTeamcode())) {
						teamsVanSpeler.add(t);
					}
				}
			}
		}
		return teamsVanSpeler;
	}
	
	public void verwijderTeamspeler(Team team, Lid lid) {
		for (TeamSpeler ts: teamspelers) {
			if (ts.getTeamSpelerCode().equals(lid.getSpelerscode() + team.getTeamcode()) ) {
				teamspelers.remove(ts);
				cache.put("teamspelers", teamspelers);
				break;
			}
		}
		io.verwijderTeamspeler(team, lid);
	}
	
	public void setTeamspeler(Team team, Lid lid) {
		TeamSpeler ts = new TeamSpeler(lid.getSpelerscode(), team.getTeamcode());
		teamspelers.add(ts);
		cache.put("teamspelers", teamspelers);
		io.setTeamspeler(team, lid);
	}
	
	class LidComparator implements Comparator<Lid> {
	    @Override
	    public int compare(Lid o1, Lid o2) {
	        return o1.getAchternaam().toLowerCase().compareTo(o2.getAchternaam().toLowerCase());
	    }
	}



	


	

}
