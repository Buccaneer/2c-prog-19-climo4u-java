package domein;

import dto.*;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "determinatietabellen")
public class DeterminatieTabel implements Valideerbaar, Serializable {

    @JoinColumn(name = "BeginKnoop_DeterminatieKnoopId")
    @OneToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private DeterminatieKnoop beginKnoop;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DeterminatieTabelId")
    private int id;
    @Column(length = 128)
    private String naam;

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setId(int id) {
        this.id = id;
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
        return beginKnoop.maakDtoAan();
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

        if (beginKnoop.getId() == knoop.getId() && knoop.isResultaatBlad()) { // Boom mpet 'gereset' worden?
            beginKnoop = new BeslissingsKnoop();
            ((BeslissingsKnoop) beginKnoop).wijzigAttributen(knoop);
        }

        beginKnoop.wijzigKnoop(knoop);
    }

    /**
     *
     *
     * /**
     * Valideert of de determinatietabel in orde is. Indien dit niet het geval
     * is wordt er een exception gegooid.
     */
    @Override
    public void valideer() throws IllegalArgumentException {
        if (this.naam == null) {
            throw new DomeinException();
        }
        beginKnoop.valideer();
    }

}
