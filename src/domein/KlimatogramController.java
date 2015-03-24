package domein;

public class KlimatogramController {

	private ContinentRepository continentRepository;
	protected KlimatogramToevoegenState state;
	protected Continent geselecteerdContinent;
	protected Land geselecteerdLand;
	protected Klimatogram geselecteerdKlimatogram;

	/**
	 * 
	 * @param naam
	 */
	public void voegContinentToe(String naam) {
		// TODO - implement KlimatogramController.voegContinentToe
		throw new UnsupportedOperationException();
	}

	public ObservableList<Continent> getContinenten() {
		// TODO - implement KlimatogramController.getContinenten
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param naam
	 */
	public void selecteerContinent(String naam) {
		// TODO - implement KlimatogramController.selecteerContinent
		throw new UnsupportedOperationException();
	}

	public ObservableList<Land> getLanden() {
		// TODO - implement KlimatogramController.getLanden
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param naam
	 */
	public void selecteerLand(String naam) {
		// TODO - implement KlimatogramController.selecteerLand
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param locatie
	 * @param beginJaar
	 * @param eindJaar
	 * @param latitude
	 * @param longitute
	 * @param station
	 */
	public void voegKlimatogramToe(String locatie, int beginJaar, int eindJaar, double latitude, double longitute, String station) {
		// TODO - implement KlimatogramController.voegKlimatogramToe
		throw new UnsupportedOperationException();
	}

	public ObservableList<Maand> getMaanden() {
		// TODO - implement KlimatogramController.getMaanden
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param naam
	 * @param neerslag
	 * @param temperatuur
	 */
	public void wijzigMaand(String naam, int neerslag, double temperatuur) {
		// TODO - implement KlimatogramController.wijzigMaand
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param naam
	 */
	public void voegLandToe(String naam) {
		// TODO - implement KlimatogramController.voegLandToe
		throw new UnsupportedOperationException();
	}

	public void slaWijzigingenOp() {
		// TODO - implement KlimatogramController.slaWijzigingenOp
		throw new UnsupportedOperationException();
	}

}