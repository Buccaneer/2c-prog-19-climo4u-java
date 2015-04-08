package domein;

import org.junit.Test;

public class VergelijkingTest
{

    @Test(expected = DomeinException.class)
    public void linkerParameterIsNullGooitExecption()
    {
        Vergelijking eq = new Vergelijking();
        eq.setLinkerParameter(null);
        eq.setOperator(VergelijkingsOperator.GELIJKAAN);
        eq.setRechterParameter(new Parameter());
        eq.valideer();
    }

    @Test(expected = DomeinException.class)
    public void rechterParameterIsNullGooitException()
    {
        Vergelijking eq = new Vergelijking();
        eq.setLinkerParameter(new Parameter());
        eq.setOperator(VergelijkingsOperator.GELIJKAAN);
        eq.setRechterParameter(null);
        eq.valideer();
    }
    
    @Test(expected = DomeinException.class)
    public void vergelijkingsOperatorIsNullGooitException()
    {
        Vergelijking eq = new Vergelijking();
        eq.setLinkerParameter(new Parameter());
        eq.setOperator(null);
        eq.setRechterParameter(new Parameter());
        eq.valideer();
    }
}
