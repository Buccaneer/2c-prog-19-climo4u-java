package domein;

import org.junit.Test;
import static org.junit.Assert.*;

public class VergelijkingTest
{

    @Test
    public void linkerParameterIsNullGooitExecption()
    {
        Vergelijking eq = new Vergelijking();
        eq.setLinkerParameter(null);
        eq.setOperator(VergelijkingsOperator.GELIJKAAN);
        eq.setRechterParameter(new Parameter());
        eq.valideer();
    }

    @Test
    public void rechterParameterIsNullGooitException()
    {
        Vergelijking eq = new Vergelijking();
        eq.setLinkerParameter(new Parameter());
        eq.setOperator(VergelijkingsOperator.GELIJKAAN);
        eq.setRechterParameter(null);
        eq.valideer();
    }
    
    @Test
    public void vergelijkingsOperatorIsNullGooitException()
    {
        Vergelijking eq = new Vergelijking();
        eq.setLinkerParameter(new Parameter());
        eq.setOperator(null);
        eq.setRechterParameter(new Parameter());
        eq.valideer();
    }
}
