package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import persistentie.GenericDao;
import persistentie.GenericDaoJpa;

/**
 *
 * @author Jasper De Vrient
 */
public class ParameterFactory {

    private static final GenericDao<Parameter, String> parameterRepository = new GenericDaoJpa<>(Parameter.class);
   
    
    
    public static ConstanteParameter maakConstanteParameter(double waarde) {
        ConstanteParameter p = new ConstanteParameter();
        p.setWaarde(waarde);
        p.setNaam(UUID.randomUUID().toString()); // Stel de naam in op een Globally Unique IDentifier (alleen noemt dat weer anders in java).
        return p;
    }

    public static List<Parameter> geefParameters() {
        List<Parameter> parameters = parameterRepository.getAll();
        if (!parameters.stream().anyMatch(p -> p.getNaam().equals("Warmste Maand")))
        parameters.add(new ParameterWarmsteMaand("Warmste Maand"));
        
        if (!parameters.stream().anyMatch(p -> p.getNaam().equals("Koudste Maand")))
        parameters.add(new ParameterKoudsteMaand("Koudste Maand"));
        
        if (!parameters.stream().anyMatch(p -> p.getNaam().equals("Tw")))
        parameters.add(new ParameterTemperatuurWarmsteMaand("Tw"));
        
        if (!parameters.stream().anyMatch(p -> p.getNaam().equals("Tk")))
        parameters.add(new ParameterTemperatuurKoudsteMaand("Tk"));
        
        if (!parameters.stream().anyMatch(p -> p.getNaam().equals("Nz")))
        parameters.add(new ParameterNeerslagZomer("Nz"));
        
        if (!parameters.stream().anyMatch(p -> p.getNaam().equals("Nw")))
        parameters.add(new ParameterNeerslagWinter("Nw"));
        
        if (!parameters.stream().anyMatch(p -> p.getNaam().equals("D")))
        parameters.add(new ParameterAantalDrogeMaanden("D"));
        
        if (!parameters.stream().anyMatch(p -> p.getNaam().equals("Tj")))
        parameters.add(new ParameterGemiddeldeTemperatuurJaar("Tj"));
        
        if (!parameters.stream().anyMatch(p -> p.getNaam().equals("Nj")))
        parameters.add(new ParameterTotaleNeerslagJaar("Nj"));
        
        if (!parameters.stream().anyMatch(p -> p.getNaam().equals("T 4de Warmste Maand")))
        parameters.add(new TemperatuurVierdeWarmsteMaandParameter("T 4de Warmste Maand"));
        return parameters;
    }
    
    
}
