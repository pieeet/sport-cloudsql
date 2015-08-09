package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sport.Administratie;
import sport.Lid;
import sport.Team;



@SuppressWarnings("serial")
public class SportServlet extends HttpServlet {
	private Administratie admin;
		
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		admin = new Administratie();
		
		//voeg een nieuw lid toe
		if (req.getParameter("knop_nieuw_lid") != null) {
			this.voegNieuwLidToe(req);
			resp.sendRedirect("/sport");
		}
		//verwijder een lid. Param verwijder_lid = definitief
		else if (req.getParameter("verwijder_lid") != null || req.getParameter("lid_verwijder") != null) {
			this.verwijderLid(req, resp);
		}
		//wijzig lid. Param wijzig_lid = definitief
		else if (req.getParameter("wijzig_lid") != null || req.getParameter("lid_wijzig") != null) {
			this.wijzigLid(req, resp);
		}
		//voeg nieuw team toe
		else if (req.getParameter("nieuw_team") != null) {
			this.voegNieuwTeamToe(req, resp);
		}
		//verwijder team Param verwijder_team = definitief
		else if (req.getParameter("team_verwijder") != null || req.getParameter("verwijder_team") != null) {
			this.verwijderTeam(req, resp);
		}
		//wijzig team Param wijzig_team = def
		else if (req.getParameter("team_wijzig") != null || req.getParameter("wijzig_team") != null) {
			this.wijzigTeam(req, resp);
		}
		//geef de teams van een bepaald lid
		else if (req.getParameter("teams_van_lid") != null) {
			this.getTeamsVanLid(req, resp);
		}
		//geef de leden van een team
		else if (req.getParameter("leden_van_team") != null) {
			this.getLedenVanTeam(req, resp);
		}
		//verwijder een teamspeler
		else if (req.getParameter("verwijder_teamspeler") != null) {
			this.verwijderTeamspeler(req, resp);
		}
		//voeg een teamspeler toe
		else if (req.getParameter("voeg_teamspeler_toe") != null) {
			this.voegTeamspelerToe(req, resp);
		}
		
		//geen params, ga naar overzicht leden en teams pagina
		else {
			req.setAttribute("admin", admin);
			RequestDispatcher disp = req.getRequestDispatcher("/ledenTeams.jsp");
			disp.forward(req, resp);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	/******************************************************************************
	 					hulpmethoden
	 *****************************************************************************/
	
	private void voegNieuwLidToe(HttpServletRequest req) throws IOException {
         String roepnaam = req.getParameter("roepnaam");
         String tussenvoegsels = req.getParameter("tussenvoegsels");
         String achternaam = req.getParameter("achternaam");
         String adres = req.getParameter("adres");
         String postcode = req.getParameter("postcode");
         String woonplaats = req.getParameter("woonplaats");
         String telefoon = req.getParameter("telefoon");
         String gebDatum = req.getParameter("gebDatum");
         String geslacht = req.getParameter("geslacht");
         Lid lid = new Lid(roepnaam, tussenvoegsels, 
             achternaam, adres, postcode, woonplaats, telefoon, gebDatum, geslacht);
		 admin.voegLidToe(lid);
	}
	
	private void verwijderLid(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String spelerscode = req.getParameter("spelerscode");
		Lid lid = null;
		lid = admin.getLid(spelerscode);

		//ga naar bevestigingspagina
		if (req.getParameter("lid_verwijder") != null) {
			req.setAttribute("lid", lid);
			RequestDispatcher disp = getServletContext().getRequestDispatcher("/verwijderlid.jsp");
			disp.forward(req, resp);
		}
		
		//verwijder lid definitief en terug naar overzicht
		else if (req.getParameter("verwijder_lid") != null) {
			 ArrayList<Team> teams = admin.getTeamsVanSpeler(lid);
			for (Team team: teams) {
				admin.verwijderTeamspeler(team, lid);
			}
			admin.verwijderLid(lid);
			resp.sendRedirect("/sport");
		}
	}
	
	private void wijzigLid(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//wijzig lid en ga terug naar overzicht
		if (req.getParameter("wijzig_lid") != null) {
			Lid lid = admin.getLid(req.getParameter("spelerscode"));
			lid.setNr(lid.getNr());
			lid.setRoepnaam(req.getParameter("roepnaam"));
			lid.setTussenvoegsels(req.getParameter("tussenvoegsels"));
			lid.setAchternaam(req.getParameter("achternaam"));
			lid.setAdres(req.getParameter("adres"));
			lid.setPostcode(req.getParameter("postcode"));
			lid.setWoonplaats(req.getParameter("woonplaats"));
			lid.setTelefoon(req.getParameter("telefoon"));
			lid.setGeboortedatum(req.getParameter("geboortedatum"));
			lid.setGeslacht(req.getParameter("geslacht"));
			admin.wijzigLid(lid);
			resp.sendRedirect("/sport");
		}
		//ga naar invoerscherm wijzigLid.jsp
		else {
			Lid lid = admin.getLid(req.getParameter("spelerscode"));
			req.setAttribute("lid", lid);
			RequestDispatcher disp = req.getRequestDispatcher("/wijziglid.jsp");
			disp.forward(req, resp);
		}
	}
	
	private void voegNieuwTeamToe(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String teamcode = req.getParameter("teamcode");
		String teamomschrijving = req.getParameter("teamomschrijving");
		
		Team team = new Team(teamcode, teamomschrijving);
		admin.voegTeamToe(team);
		
		resp.sendRedirect("/sport");
	}
	
	//verwijdert een team na bevestiging
	private void verwijderTeam(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Team team = admin.getTeam(req.getParameter("teamcode"));
		//leid naar bevestiging
		if (req.getParameter("team_verwijder") != null) {
			req.setAttribute("team", team);
			RequestDispatcher disp = req.getRequestDispatcher("/verwijderteam.jsp");
			try {
				disp.forward(req, resp);
			}
			catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//verwijder Team en terug naar overzicht
		else if (req.getParameter("verwijder_team") != null){
			ArrayList<Lid> teamspelers = admin.getSpelersVanTeam(team);
			for (Lid lid: teamspelers) {
				admin.verwijderTeamspeler(team, lid);
			}
			admin.verwijderTeam(team);
			resp.sendRedirect("/sport");
		}
	}
	
	private void wijzigTeam(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Team team = admin.getTeam(req.getParameter("teamcode"));
		
		//ga naar wijzigteam.jsp
		if (req.getParameter("team_wijzig") != null) {
			req.setAttribute("team", team);
			RequestDispatcher disp = req.getRequestDispatcher("/wijzigteam.jsp");
			disp.forward(req, resp);
		}
		
		//wijzig team en ga terug naar overzicht
		else {
			team.setOmschrijving(req.getParameter("teamomschrijving"));
			admin.wijzigTeam(team);
			resp.sendRedirect("/sport");
		}
	}
	
	//geeft overzicht van teams van speler
	private void getTeamsVanLid(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String spelerscode = req.getParameter("spelerscode");
		Lid lid = admin.getLid(spelerscode);
		ArrayList<Team> teamsLid = admin.getTeamsVanSpeler(lid);
		boolean isLeeg = false;
		if (teamsLid.isEmpty()) {
			isLeeg = true;
		}
		req.setAttribute("isLeeg", isLeeg);
		req.setAttribute("teams", teamsLid);
		req.setAttribute("lid", lid);
		RequestDispatcher disp = req.getRequestDispatcher("/teamslid.jsp");
		disp.forward(req, resp);
	}
	
	
	//geeft leden van een bepaald team
	private void getLedenVanTeam(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Team team = admin.getTeam(req.getParameter("teamcode"));
		req.setAttribute("team", team);
		ArrayList<Lid> teamleden = admin.getSpelersVanTeam(team);
		req.setAttribute("teamleden", teamleden);
		
		//maak lijst van niet-teamleden
		ArrayList<Lid> nietTeamleden = admin.getNietTeamspelers(team);
		req.setAttribute("niet_teamleden", nietTeamleden);
		RequestDispatcher disp = req.getRequestDispatcher("/ledenvanteam.jsp");
		disp.forward(req, resp);
	}
	
	//voeg een speler toe aan een team in ledenvanteam.jsp.  Blijf in zelfde scherm
	private void voegTeamspelerToe(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Lid lid = admin.getLid(req.getParameter("spelerscode"));
		Team team = admin.getTeam(req.getParameter("teamcode"));
		admin.setTeamspeler(team, lid);
		resp.sendRedirect("/sport?teamcode=" + team.getTeamcode() + "&leden_van_team=" );	
	}
	
	//verwijder een speler uit een team Blijf in zelfde scherm
	private void verwijderTeamspeler(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Lid lid = admin.getLid(req.getParameter("spelerscode"));
		Team team = admin.getTeam(req.getParameter("teamcode"));
		admin.verwijderTeamspeler(team, lid);
		resp.sendRedirect("/sport?teamcode=" + team.getTeamcode()+ "&leden_van_team=" );
	}
}
