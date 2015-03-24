package domein;

public abstract class KlimatogramToevoegenState {

	protected KlimatogramController controller;

	/**
	 * 
	 * @param naam
	 */
	protected abstract void voegContinentToe(String naam);

	protected abstract ObservableList<Continent> getContinenten();

	/**
	 * 
	 * @param naam
	 */
	protected abstract void selecteerContinent(String naam);

	protected abstract ObservableList<Land> getLanden();

	/**
	 * 
	 * @param naam
	 */
	protected abstract void selecteerLand(String naam);

	/**
	 * 
	 * @param locatie
	 * @param beginJaar
	 * @param eindJaar
	 * @param latitude
	 * @param longitude
	 * @param station
	 */
	protected abstract void voegKlimatogramToe(String locatie, int beginJaar, int eindJaar, double latitude, double longitude, String station);

	protected abstract ObservableList<Maand> getMaanden();

	/**
	 * 
	 * @param naam
	 * @param neerslag
	 * @param temperatuur
	 */
	protected abstract void wijzigMaand(String naam, int neerslag, double temperatuur);

	/**
	 * 
	 * @param controller
	 */
	public KlimatogramToevoegenState(KlimatogramController controller) {
		// TODO - implement KlimatogramToevoegenState.KlimatogramToevoegenState
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param naam
	 */
	protected abstract void voegLandToe(String naam);

	protected abstract void slaWijzigingenOp();

}