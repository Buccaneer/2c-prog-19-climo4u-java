package domein;

import dto.*;

public class DeterminatieTabel {

    private DeterminatieKnoop beginKnoop;
    private int id;
    private String naam;

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getId() {
        return id;
    }

    public void setBeginKnoop(DeterminatieKnoop beginKnoop) {
        this.beginKnoop = beginKnoop;
    }
    
    public DeterminatieKnoop getBeginKnoop() {
        return beginKnoop;
    }
    
    public DeterminatieKnoopDto maakDtoAan() {
        throw new UnsupportedOperationException();
    }

    /**
     * Wijzigt de meegegeven knoop. Meerdere Gevallen:
     * <p>
     * <strong>Speciaal geval:</strong> Deze knoop is de root knoop.</p>
     * <ol>
     * <li><p>
     * Het <var>type</var> van de knoop blijft het zelfde: Wijzig de attributen
     * van die knoop.
     * </p>
     * </li>
     * <li><p>
     * Het <var>type</var> van de knoop verandert van resultaatblad naar
     * beslissingsknoop: <ol><li>Ouder beseft dat zijn kind verwijdert moet
     * worden.</li>
     * <li>Ouder verwijdert zijn juiste kind.</li>
     * <li>Ouder maakt een nieuwe <var>BeslissingsKnoop</var> aan en voegt deze
     * toe op de juiste plaats.</li>
     * <li>Ouder roept <code>wijzigKnoop(knoop)</code> aan van dit kind. <em>Om
     * attributen in te stellen.</em></li>
     * </ol></p></li>
     * <li><p>
     * Het <var>type</var> van de knoop verandert van beslissingsknoop naar
     * resultaatblad: Enkele gevallen:<ol><li>
     * <p>
     * <strong>BeginKnoop</strong>: Reset de determinatietabel zie
     * begingeval.</p>
     * </li>
     * <li><p>
     * <strong>Tussenknoop</strong>: Verander de knoop naar een resultaatblad,
     * als gevolg verdwijnen alle kinderen.
     * </p></li>
     * </ol></p></li>
     * </ol>
     *
     */
    public void wijzigKnoop(DeterminatieKnoopDto knoop) {

        if (beginKnoop.getId() == knoop.getId() && knoop.isResultaatBlad()) // Boom met 'gereset' worden?
            beginKnoop = new BeslissingsKnoop();

        beginKnoop.wijzigKnoop(knoop);
    }

    /**
     *
     *
     * /**
     * Valideert of de determinatietabel in orde is. Indien dit niet het geval
     * is wordt er een exception gegooid.
     */
    public void valideer() {
        throw new UnsupportedOperationException();
    }

}
