package domein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vergelijkingen")
public class Vergelijking implements Valideerbaar {
    private Parameter rechterParameter;
    private Parameter linkerParameter;
    private VergelijkingsOperator operator;
    @Id
    @Column(name = "VergelijkingId")
    private int id;

    public Vergelijking() {
        rechterParameter = ParameterFactory.maakConstanteParameter(0.0);
        linkerParameter = ParameterFactory.maakConstanteParameter(0.0);
        operator=VergelijkingsOperator.GELIJKAAN;
    }

    
    
    public int getId() {
        return this.id;
    }

    public Parameter getRechterParameter() {
        return rechterParameter;
    }

    public void setRechterParameter(Parameter rechterParameter) {
        this.rechterParameter = rechterParameter;
    }

    public Parameter getLinkerParameter() {
        return linkerParameter;
    }

    public void setLinkerParameter(Parameter linkerParameter) {
        this.linkerParameter = linkerParameter;
    }

    public VergelijkingsOperator getOperator() {
        return operator;
    }

    public void setOperator(VergelijkingsOperator operator) {
        this.operator = operator;
    }

    @Override
    public void valideer()
    {
        rechterParameter.valideer();
        linkerParameter.valideer();
        if (operator == null)
            throw new DomeinException();
    }
}
