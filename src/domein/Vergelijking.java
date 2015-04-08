package domein;

public class Vergelijking {

    private Parameter rechterParameter;
    private Parameter linkerParameter;
    private VergelijkingsOperator operator;
    private int id;

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

}
