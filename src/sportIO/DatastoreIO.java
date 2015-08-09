package sportIO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sport.Lid;
import sport.Team;
import sport.TeamSpeler;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class DatastoreIO implements IoMethodenInterface {
	private DatastoreService datastore;
	
	public DatastoreIO() {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	@Override
	public void voegLidToe(Lid lid) {
		Entity e = new Entity("Lid", lid.getSpelerscode());
		e.setProperty("spelerscode", lid.getSpelerscode());
		e.setProperty("lidnr", lid.getNr());
		e.setProperty("roepnaam", lid.getRoepnaam());
		e.setProperty("tussenvoegsels", lid.getTussenvoegsels());
		e.setProperty("achternaam", lid.getAchternaam());
		e.setProperty("adres", lid.getAdres());
		e.setProperty("postcode", lid.getPostcode());
		e.setProperty("woonplaats", lid.getWoonplaats());
		e.setProperty("telefoon", lid.getTelefoon());
		e.setProperty("geslacht", lid.getGeslacht());
		Date gebDatum = null;
		try {
			gebDatum = new SimpleDateFormat("yyyy-MM-dd").parse(lid.getGeboortedatum());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.setProperty("geboortedatum", gebDatum);
		datastore.put(e);
	}
	
	@Override
	public void verwijderLid(Lid lid){
		Key lidKey = KeyFactory.createKey("Lid", lid.getSpelerscode());
		datastore.delete(lidKey);
	}
	
	// wijzigen in datastore == toevoegen
	@Override
	public void wijzigLid(Lid lid) {
		voegLidToe(lid);
	}

	@Override
	public Lid getLid(String spelerscode)  {
		Key k = KeyFactory.createKey("Lid", spelerscode);
		Entity ent;
		Lid lid = null;
		try {
			ent = datastore.get(k);
			lid = this.maakLid(ent);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return lid;
	}
	
	//hulpmethode maakt van Entity een object Lid
	private Lid maakLid(Entity ent) {
		Lid lid = new Lid();
		lid.setSpelerscode((String) ent.getProperty("spelerscode"));
		long l = (long) ent.getProperty("lidnr"); //int wordt opgeslagen als long in datastore :-/
		int lidnummer = (int) l;
		lid.setNr(lidnummer);
		String roepnaam = (String) ent.getProperty("roepnaam");
		String tussenvoegsels = (String) ent.getProperty("tussenvoegsels");
		String achternaam = (String) ent.getProperty("achternaam");
		lid.setRoepnaam(roepnaam);
		lid.setTussenvoegsels(tussenvoegsels);
		lid.setAchternaam(achternaam);
		if (tussenvoegsels.equals("")) {
			lid.setNaam(roepnaam + " " + achternaam);
		}
		else {
			lid.setNaam(roepnaam + " " + tussenvoegsels + " " + achternaam);
		}
		lid.setAdres((String) ent.getProperty("adres"));
		lid.setPostcode((String) ent.getProperty("postcode"));
		lid.setWoonplaats((String) ent.getProperty("woonplaats"));
		lid.setTelefoon((String) ent.getProperty("telefoon"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date gd = (Date) ent.getProperty("geboortedatum");
		lid.setGeboortedatum(sdf.format(gd));
		lid.setGeslacht((String) ent.getProperty("geslacht"));
		
		return lid;
	}
	
	@Override
	public ArrayList<Lid> getLeden() {
		ArrayList<Lid> ledenlijst = new ArrayList<Lid>();
		Query q = new Query("Lid");
		PreparedQuery pq = datastore.prepare(q);
		for (Entity ent: pq.asIterable()) {
			Lid lid = maakLid(ent);
			ledenlijst.add(lid);
		}
		return ledenlijst;
	}
	
	@Override
	public void voegTeamToe(Team team) {
		Entity ent = new Entity("Team", team.getTeamcode());
		ent.setProperty("teamcode", team.getTeamcode());
		ent.setProperty("teamomschrijving", team.getOmschrijving());
		datastore.put(ent);
	}
	
	@Override
	public void setTeamspeler(Team team, Lid lid) {
		Entity e = new Entity("Teamspeler", team.getTeamcode() + lid.getSpelerscode());
		e.setProperty("teamcode", team.getTeamcode());
		e.setProperty("spelerscode", lid.getSpelerscode());
		datastore.put(e);
	}
	
	@Override
	public void verwijderTeamspeler(Team team, Lid lid) {
		Key k = KeyFactory.createKey("Teamspeler", team.getTeamcode() + lid.getSpelerscode());
		datastore.delete(k);
	}
	
	@Override
	public ArrayList<TeamSpeler> getTeamspelers() {
		ArrayList<TeamSpeler> teamleden = new ArrayList<TeamSpeler>();
		
		Query q = new Query("Teamspeler");
		PreparedQuery pq = datastore.prepare(q);
		
		for (Entity result: pq.asIterable()) {
				String spelerscode = (String) result.getProperty("spelerscode");
				String teamcode = (String) result.getProperty("teamcode");
				TeamSpeler ts = new TeamSpeler();
				ts.setTeamSpelerCode(spelerscode + teamcode);
				ts.setSpelerscode(spelerscode);
				ts.setTeamcode(teamcode);
				teamleden.add(ts);
		}
		return teamleden;
	}
	
	@Override
	public ArrayList<Lid> getSpelersVanTeam(Team team) {
		ArrayList<Lid> teamleden = new ArrayList<Lid>();
		Filter teamcode =  new FilterPredicate("teamcode", FilterOperator.EQUAL, team.getTeamcode());
		Query q = new Query("Teamspeler").setFilter(teamcode);
		PreparedQuery pq = datastore.prepare(q);
		
		for (Entity result: pq.asIterable()) {
				Lid lid = this.getLid( (String) result.getProperty("spelerscode"));
				teamleden.add(lid);
		}
		return teamleden;
	}
	
	@Override
	public ArrayList<Team> getTeamsVanSpeler( Lid lid) {
		ArrayList<Team> spelerteams = new ArrayList<Team>();
		Filter spelerscode =  new FilterPredicate("spelerscode", FilterOperator.EQUAL, lid.getSpelerscode());
		Query q = new Query("Teamspeler").setFilter(spelerscode);
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result: pq.asIterable()) {
				Team team = this.getTeam( (String) result.getProperty("teamcode"));
				spelerteams.add(team);
		}
		return spelerteams;
	}

	@Override
	public void verwijderTeam(Team team) {
		Key teamKey = KeyFactory.createKey("Team", team.getTeamcode());
		datastore.delete(teamKey);
	}
	
	@Override
	public void wijzigTeam(Team team) {
		voegTeamToe(team);
	}
	
	@Override
	public Team getTeam(String teamcode) {
		Team team = new Team();
		Key k = KeyFactory.createKey("Team", teamcode);
		Entity res = null;
		try {
			res = datastore.get(k);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			team.setTeamcode((String) res.getProperty("teamcode"));
			team.setOmschrijving((String) res.getProperty("teamomschrijving"));
		return team;
	}
	
	@Override
	public ArrayList<Team> getTeams() {
		ArrayList<Team> teams = new ArrayList<Team>();
		Query q = new Query("Team").addSort("teamcode", SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(q);
		
		for (Entity result: pq.asIterable()) {
			Team team = new Team();
			String tc = (String) result.getProperty("teamcode");
			team.setTeamcode(tc);
			String to = (String) result.getProperty("teamomschrijving");
			team.setOmschrijving(to);
			teams.add(team);
		}
		return teams;
	}

}
