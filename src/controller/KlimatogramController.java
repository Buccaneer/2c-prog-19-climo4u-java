package controller;

import persistentie.*;
import domein.*;
import dto.*;
import javafx.collections.ObservableList;

public class KlimatogramController {

	private GenericDao continentenRepository;
	protected Continent geselecteerdContinent;
	protected Land geselecteerdLand;
	protected Klimatogram geselecteerdKlimatogram;

	/**
	 * 
	 * @param continent
	 */
	public void voegContinentToe(ContinentDto continent) {
		// TODO - implement KlimatogramController.voegContinentToe
		throw new UnsupportedOperationException();
	}

	public ObservableList<ContinentDto> getContinenten() {
		// TODO - implement KlimatogramController.getContinenten
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param continent
	 */
	public void selecteerContinent(ContinentDto continent) {
		// TODO - implement KlimatogramController.selecteerContinent
		throw new UnsupportedOperationException();
	}

	public ObservableList<LandDto> getLanden() {
		// TODO - implement KlimatogramController.getLanden
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param land
	 */
	public void selecteerLand(LandDto land) {
		// TODO - implement KlimatogramController.selecteerLand
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param klimatogram
	 */
	public void voegKlimatogramToe(KlimatogramDto klimatogram) {
		// TODO - implement KlimatogramController.voegKlimatogramToe
		throw new UnsupportedOperationException();
	}

	public ObservableList<MaandDto> getMaanden() {
		// TODO - implement KlimatogramController.getMaanden
		throw new UnsupportedOperationException();
	}


	/**
	 * 
	 * @param land
	 */
	public void voegLandToe(LandDto land) {
		// TODO - implement KlimatogramController.voegLandToe
		throw new UnsupportedOperationException();
	}

	

	/**
	 * 
	 * @param continentDao
	 */
	public void setContinentRepository(GenericDao<Continent, String> continentDao) {
		// TODO - implement KlimatogramController.setContinentRepository
		throw new UnsupportedOperationException();
	}

}