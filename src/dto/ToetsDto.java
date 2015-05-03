package dto;

import java.util.*;

public class ToetsDto {

	private List<VraagDto> vragen;
	private int id;
        private String naam;
	private String titel;
	private Date aanvang;
	private Date eind;
	private int aantalPuntenTeBehalen;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
        
        public List<VraagDto> getVragen()
        {
            return vragen;
        }
        
        public void vroegVraagToe(VraagDto vraag)
        {
            vragen.add(vraag);
        }
        
        public void verwijderVraag(VraagDto vraag)
        {
            vragen.remove(vraag);
        }
        
        public String getNaam()
        {
            return naam;
        }
        
        public void setNaam(String naam)
        {
            this.naam = naam;
        }

	public String getTitel() {
		return this.titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public Date getAanvang() {
		return this.aanvang;
	}

	public void setAanvang(Date aanvang) {
		this.aanvang = aanvang;
	}

	public Date getEind() {
		return this.eind;
	}

	public void setEind(Date eind) {
		this.eind = eind;
	}

}