package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Jasper De Vrient
 */
public class ParameterFactory {
    public static ConstanteParameter maakConstanteParameter(double waarde) {
        ConstanteParameter p = new ConstanteParameter();
        p.setWaarde(waarde);
        p.setNaam(UUID.randomUUID().toString()); // Stel de naam in op een Globally Unique IDentifier (alleen noemt dat weer anders in java).
        return p;
    }
    
    public static List<Parameter> geefParameters() {
        // lijst van alle custom niet constante parameters.
        return new ArrayList<>();
    }
}
