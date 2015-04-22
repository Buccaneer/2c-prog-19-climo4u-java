package domein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "parameters")
public class ConstanteParameter extends Parameter {

    @Column(name = "Waarde")
    private double waarde;

    /**
     *
     * @return
     */
    @Override
    public double getWaarde() {
        return this.waarde;
    }

    @Override
    public void setWaarde(double waarde) {
        this.waarde = waarde;
    }

}
