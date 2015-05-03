package controller;

import domein.*;
import dto.*;
import javafx.collections.ObservableList;

public class ToetsController {

	private Toets geselecteerdeToets;
	private Graad graad;
	private ToetsVraag geselecteerdeVraag;
	private ObservableList<KlasDto> klassenVanToets;

	/**
	 * 
	 * @param toetsDto
	 */
	public void maakNieuweToets(ToetsDto toetsDto) {
		geselecteerdeToets = new Toets();
	}

	/**
	 * 
	 * @param toets
	 */
	public void verwijderToets(ToetsDto toets) {
		// TODO - implement ToetsController.verwijderToets
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param toets
	 */
	public void wijzigToets(ToetsDto toets) {
		// TODO - implement ToetsController.wijzigToets
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param toets
	 */
	public void selecteerToets(ToetsDto toets) {
		// TODO - implement ToetsController.selecteerToets
		throw new UnsupportedOperationException();
	}

	public ObservableList<GraadDto> geefAlleGraden() {
		// TODO - implement ToetsController.geefAlleGraden
		throw new UnsupportedOperationException();
	}

	public ObservableList<KlasDto> geefAlleKlassen() {
		// TODO - implement ToetsController.geefAlleKlassen
		throw new UnsupportedOperationException();
	}

	public ObservableList<KlasDto> geefKlassenVanToets() {
		// TODO - implement ToetsController.geefKlassenVanToets
		throw new UnsupportedOperationException();
	}

	public ObservableList<VraagDto> geefVragen() {
		// TODO - implement ToetsController.geefVragen
		throw new UnsupportedOperationException();
	}
        
        public void voegKlasToe(KlasDto klas)
        {
            klassenVanToets.add(klas);
        }
        
        public void verwijderKlas(KlasDto klas)
        {
            klassenVanToets.remove(klas);
        }

	/**
	 * 
	 * @param vraag
	 */
	public void voegVraagToe(VraagDto vraag) {
		// TODO - implement ToetsController.voegVraagToe
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param vraag
	 */
	public void verwijderVraag(VraagDto vraag) {
		// TODO - implement ToetsController.verwijderVraag
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param vraag
	 */
	public void wijzigVraag(VraagDto vraag) {
		// TODO - implement ToetsController.wijzigVraag
		throw new UnsupportedOperationException();
	}

	/**
	 * Toetscontroller is geristreerd bij klassenVAnTeots�
	 * Indien die observable list wordt aangepast dan dient hij de relaties in het domein goed te stellen.
	 * Als deze lijst wordt opgevuld dien je jezelf effen uit te schrijven totdat of totop de lijst weer gevuld is en dan dien je jezelf weer te abboneren�
	 * @param object
	 */
	public void update(Object object) {
		// TODO - implement ToetsController.update
		throw new UnsupportedOperationException();
	}

	public ObservableList<KlimatogramDto> geefKlimatogrammen() {
		// TODO - implement ToetsController.geefKlimatogrammen
		throw new UnsupportedOperationException();
	}

	public ObservableList<ToetsDto> geefToetsen() {
		// TODO - implement ToetsController.geefToetsen
		throw new UnsupportedOperationException();
	}

}