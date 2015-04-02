package controller;

import domein.*;
import persistentie.*;

public class DeterminatieController {

	private DeterminatieTabel geselecteerdeDeterminatieTabel;
	private GenericDaoJpa<DeterminatieTabel, Integer> determinatieTabelDao;
	private GenericDaoJpa<DeterminatieKnoop, Integer> determinatieKnoopDao;

	public ObservableList<GraadDto> getGraden() {
		// TODO - implement DeterminatieController.getGraden
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param graad
	 */
	public void selecteerGraad(GraadDto graad) {
		// TODO - implement DeterminatieController.selecteerGraad
		throw new UnsupportedOperationException();
	}

	public ObservableList<DeterminatieTabelDto> getDeterminatieTabellen() {
		// TODO - implement DeterminatieController.getDeterminatieTabellen
		throw new UnsupportedOperationException();
	}

	public void maakNieuweDeterminatieTabel() {
		// TODO - implement DeterminatieController.maakNieuweDeterminatieTabel
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tabel
	 */
	public void verwijderDeterminatieTabel(DeterminatieTabelDto tabel) {
		// TODO - implement DeterminatieController.verwijderDeterminatieTabel
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tabel
	 */
	public void selecteerDeterminatieTabel(DeterminatieTabelDto tabel) {
		// TODO - implement DeterminatieController.selecteerDeterminatieTabel
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param observer
	 */
	public void addObserver(Observer observer) {
		// TODO - implement DeterminatieController.addObserver
		throw new UnsupportedOperationException();
	}

	private void notifyObservers() {
		// TODO - implement DeterminatieController.notifyObservers
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tabel
	 */
	public void wijzigDeterminatieTabel(DeterminatieTabelDto tabel) {
		// TODO - implement DeterminatieController.wijzigDeterminatieTabel
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param graad
	 * @param tabel
	 */
	public void koppelGraadMetDeterminatieTabel(GraadDto graad, DeterminatieTabelDto tabel) {
		// TODO - implement DeterminatieController.koppelGraadMetDeterminatieTabel
		throw new UnsupportedOperationException();
	}

}