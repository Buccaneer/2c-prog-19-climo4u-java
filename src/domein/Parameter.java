package domein;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "parameters")
@DiscriminatorColumn(name="Discriminator")
@Inheritance(strategy = InheritanceType.JOINED)
public class Parameter implements Valideerbaar {

    /**
     * TODO : Overeenkomsten vastleggen tussen parameters in DB en domein.
     * Constante parameters gaan we moeten kunnen aanmaken en in DB stoppen. Let
     * op: hoe kunnen we duplicate constante parameters in DB vermijden?
     * Niet-constante parameters kunnen we enkel uit DB halen en kunnen in de
     * GUI geselecteerd worden.
     *
     * Naam = id;
     */
    @Id
    @Column(name = "ParameterId", length = 128)
    private String naam;
    private double waarde;

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public double getWaarde() {
        return waarde;
    }

    public void setWaarde(double waarde) {
        this.waarde = waarde;
    }

    @Override
    public void valideer()
    {
        if (this.naam == null)
            throw new DomeinException();
    }

}

class ParameterWarmsteMaand extends Parameter {

    public ParameterWarmsteMaand() {
    }

    public ParameterWarmsteMaand(String naam) {
        setNaam(naam);
    }
}

class ParameterKoudsteMaand extends Parameter {

    public ParameterKoudsteMaand() {
    }
    public ParameterKoudsteMaand(String naam){
        setNaam(naam);
    }
}

class ParameterTemperatuurWarmsteMaand extends Parameter {

    public ParameterTemperatuurWarmsteMaand() {
    }
    public ParameterTemperatuurWarmsteMaand(String naam){
        setNaam(naam);
    }
}

class ParameterTemperatuurKoudsteMaand extends Parameter {

    public ParameterTemperatuurKoudsteMaand() {
    }
    public ParameterTemperatuurKoudsteMaand(String naam){
        setNaam(naam);
    }
}

class ParameterNeerslagZomer extends Parameter {

    public ParameterNeerslagZomer() {
    }
    public ParameterNeerslagZomer(String naam){
        setNaam(naam);
    }
}

class ParameterNeerslagWinter extends Parameter {

    public ParameterNeerslagWinter() {
    }
    public ParameterNeerslagWinter(String naam){
        setNaam(naam);
    }
}

class ParameterAantalDrogeMaanden extends Parameter {

    public ParameterAantalDrogeMaanden() {
    }
    public ParameterAantalDrogeMaanden(String naam){
        setNaam(naam);
    }
}

class ParameterGemiddeldeTemperatuurJaar extends Parameter {

    public ParameterGemiddeldeTemperatuurJaar() {
    }
    public ParameterGemiddeldeTemperatuurJaar(String naam){
        setNaam(naam);
    }
}

class ParameterTotaleNeerslagJaar extends Parameter {

    public ParameterTotaleNeerslagJaar() {
    }
    public ParameterTotaleNeerslagJaar(String naam){
        setNaam(naam);
    }
}

class TemperatuurVierdeWarmsteMaand extends Parameter {

    public TemperatuurVierdeWarmsteMaand() {
    }
    public TemperatuurVierdeWarmsteMaand(String naam){
        setNaam(naam);
    }
}
