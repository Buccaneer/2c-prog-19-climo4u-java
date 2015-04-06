package domein;

public class Parameter {

    /**
     * TODO : Overeenkomsten vastleggen tussen parameters in DB en domein.
     * Constante parameters gaan we moeten kunnen aanmaken en in DB stoppen. Let
     * op: hoe kunnen we duplicate constante parameters in DB vermijden?
     * Niet-constante parameters kunnen we enkel uit DB halen en kunnen in de
     * GUI geselecteerd worden.
     *
     * Naam = id;
     */
    private String naam;

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

}

class ParameterNeerslagWinter extends Parameter {
    
}

// Voor alle nietconstante parameters die bestaan in dotnet.


