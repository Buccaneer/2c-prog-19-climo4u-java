package domein;

import dto.DeterminatieKnoopDto;
import java.io.Serializable;

public abstract class DeterminatieKnoop implements Serializable{

    private int id;

    public int getId() {
        return id;
    }

    public DeterminatieKnoop() {
    }

    public DeterminatieKnoop(int id) {
        this.id = id;
    }

    
    
    // Omzetten naar dto.
    public abstract DeterminatieKnoopDto maakDtoAan();

    /**
     * Wijzigt de meegegeven knoop.
     *
     * @param knoop
     */
    public abstract void wijzigKnoop(DeterminatieKnoopDto knoop);

    /**
     * Valideert of deze knoop en al zijn kinderen in orde zijn.
     */
    public abstract void valideer();
}
