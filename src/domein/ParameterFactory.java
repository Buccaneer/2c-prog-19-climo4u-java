package domein;

import java.util.ArrayList;
import java.util.Arrays;
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
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterWarmsteMaand("Warmste Maand"));
        parameters.add(new ParameterKoudsteMaand("Koudste Maand"));
        parameters.add(new ParameterTemperatuurWarmsteMaand("Tw"));
        parameters.add(new ParameterTemperatuurKoudsteMaand("Tk"));
        parameters.add(new ParameterNeerslagZomer("Nz"));
        parameters.add(new ParameterNeerslagWinter("Nw"));
        parameters.add(new ParameterAantalDrogeMaanden("D"));
        parameters.add(new ParameterGemiddeldeTemperatuurJaar("Tj"));
        parameters.add(new ParameterTotaleNeerslagJaar("Nj"));
        parameters.add(new TemperatuurVierdeWarmsteMaand("T 4de Warmste Maand"));
        return parameters;
    }
}
