package domein;

import dto.DeterminatieKnoopDto;

public abstract class DeterminatieKnoop {

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
     *
     * @param knoop
     */
    public abstract void setLinkerKnoop(DeterminatieKnoop knoop);

    /**
     *
     * @param knoop
     */
    public abstract void setRechterKnoop(DeterminatieKnoop knoop);

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
