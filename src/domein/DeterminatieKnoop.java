package domein;

import dto.DeterminatieKnoopDto;

public abstract class DeterminatieKnoop
{

    private int id;

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
     *
     * @param knoop
     */
    public abstract void bouwKnoop(DeterminatieKnoopDto knoop);

    /**
     * Wijzigt de meegegeven knoop in een beslissingsknoop en voegt twee nieuwe
     * resultaatknopen als kinderen toe.
     *
     * @param ouder
     */
    public void voegKnopenToe(DeterminatieKnoopDto ouder)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Wijzigt de meegegeven knoop.
     *
     * @param knoop
     */
    public void wijzigKnoop(DeterminatieKnoopDto knoop)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Probeert de meegegeven knoop te verwijderen. Heeft 3 mogelijke gevallen.
     * <p>
     * 1. Indien het een blad is worden de knoop en zijn broer verwijderd en
     * wordt de ouder een blad.
     * <p>
     * 2. Indien het een tussenknoop is worden de kinderen verwijderd en wordt
     * de tussenknoop een blad.
     * <p>
     * 3. Indien het de beginknoop is wordt de tabel verwijderd en wordt opnieuw
     * begonnen op dezelfde manier als bij een nieuwe tabel.
     *
     * @param knoop
     */
    public void verwijderKnoop(DeterminatieKnoopDto knoop)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Valideert of deze knoop en al zijn kinderen in orde zijn.
     */
    public abstract void valideer();
}
