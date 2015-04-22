package domein;

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
@Table(name = "vergelijkingen")
public class Vergelijking implements Valideerbaar {

    @OneToOne(optional = true, fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "RechterParameter_ParameterId")
    private Parameter rechterParameter;
    @OneToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "LinkerParameter_ParameterId")
    private Parameter linkerParameter;
    private VergelijkingsOperator operator;
    @Id
    @Column(name = "VergelijkingId")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Vergelijking() {
        rechterParameter = ParameterFactory.maakConstanteParameter(0.0);
        linkerParameter = ParameterFactory.maakConstanteParameter(0.0);
        operator = VergelijkingsOperator.GELIJKAAN;
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
    public void valideer() {
    
        rechterParameter.valideer();
        linkerParameter.valideer();
        if (operator == null) {
            throw new DomeinException();
        }
    }
}
