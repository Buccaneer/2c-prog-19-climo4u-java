package domein;

public class Vergelijking {

    private Parameter rechterParameter;
    private Parameter linkerParameter;
    private VergelijkingsOperator operator;
    private int id;

    public int getId() {
        return this.id;
    }

    public Parameter getLinkerParameter() {
        return linkerParameter;
    }

    public VergelijkingsOperator getOperator() {
        return operator;
    }

    public Parameter getRechterParameter() {
        return rechterParameter;
    }

    public void setLinkerParameter(Parameter linkerParameter) {
        this.linkerParameter = linkerParameter;
    }

    public void setOperator(VergelijkingsOperator operator) {
        this.operator = operator;
    }

    public void setRechterParameter(Parameter rechterParameter) {
        this.rechterParameter = rechterParameter;
    }
}
