package sport;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Lid implements Serializable {
	private int nr;
	private String spelerscode, roepnaam, tussenvoegsels, achternaam, naam,
	        adres, postcode, woonplaats, telefoon, geboortedatum, geslacht;
	
	public Lid() {
		this.nr = 0;
		this.spelerscode = "";
		this.roepnaam = "";
		this.tussenvoegsels = "";
		this.achternaam = "";
		this.adres = "";
		this.postcode = "";
		this.woonplaats = "";
		this.telefoon = "";
		this.geboortedatum = "";
		this.geslacht = "";
		this.naam = "";
	}

	/**
	 * maakt een nieuw lid-object obv invoer
	 * @param spelersnr
	 * @param roepnaam
	 * @param tussenvoegsels
	 * @param achternaam
	 * @param adres
	 * @param postcode
	 * @param woonplaats
	 * @param telefoon
	 * @param geboortedatum
	 * @param geslacht
	 */
	public Lid (String roepnaam, String tussenvoegsels,
            String achternaam, String adres, String postcode, String woonplaats, String telefoon, 
            String geboortedatum, String geslacht) {
        
        
        //check op geldige datum
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        Date gebdat;
        try {
            gebdat = sdf.parse(geboortedatum);
            this.geboortedatum = sdf.format(gebdat);
        }
        catch(ParseException e) {
            this.geboortedatum = "1900-01-01";
        }
 
        //maak automatisch spelerscode aan
        String sc;
        if (achternaam.length() > 4) {
            sc = this.geboortedatum + roepnaam.substring(0,1) + achternaam.substring(0, 4);
        }
        else {
            sc = geboortedatum + roepnaam.substring(0, 2) + achternaam;
        }
        this.spelerscode = sc;
        this.roepnaam = roepnaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        if (tussenvoegsels.equals("")) {
        	naam = roepnaam + " " + achternaam; 
        }
        else {
        	naam = roepnaam + " " + tussenvoegsels + " " + achternaam; 
        }
        this.adres = adres;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
        this.telefoon = telefoon;
        this.geslacht = geslacht;
    }
	
	
	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public String getSpelerscode() {
		return spelerscode;
	}

	public void setSpelerscode(String spelerscode) {
		this.spelerscode = spelerscode;
	}

	public String getRoepnaam() {
		return roepnaam;
	}

	public void setRoepnaam(String roepnaam) {
		this.roepnaam = roepnaam;
	}

	public String getTussenvoegsels() {
		return tussenvoegsels;
	}

	public void setTussenvoegsels(String tussenvoegsels) {
		this.tussenvoegsels = tussenvoegsels;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}
	
	public String getNaam() {
		return this.naam;
	}
	
	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getWoonplaats() {
		return woonplaats;
	}

	public void setWoonplaats(String woonplaats) {
		this.woonplaats = woonplaats;
	}

	public String getTelefoon() {
		return telefoon;
	}

	public void setTelefoon(String telefoon) {
		this.telefoon = telefoon;
	}

	public String getGeboortedatum() {
		return geboortedatum;
	}

	public void setGeboortedatum(String geboortedatum) {
		this.geboortedatum = geboortedatum;
	}
	
	public String getGeslacht() {
		return geslacht;
	}
	
	public void setGeslacht(String geslacht) {
		this.geslacht = geslacht;
	}

}
