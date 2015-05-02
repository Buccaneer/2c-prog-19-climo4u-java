package domein;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "ToetsVragen")
public class LocatieVraag extends ToetsVraag
{

    private Set<Klimatogram> klimatogrammen;

    public LocatieVraag()
    {
        klimatogrammen = new HashSet<>();
    }

    public Set<Klimatogram> getKlimatogrammen()
    {
        return this.klimatogrammen;
    }

    public void setKlimatogrammen(HashSet<Klimatogram> klimatogrammen)
    {
        this.klimatogrammen = klimatogrammen;
    }

    /**
     *
     * @param klimatogram
     */
    public void voegKlimatogramToe(Klimatogram klimatogram)
    {
        klimatogrammen.add(klimatogram);
    }

    /**
     *
     * @param klimatogram
     */
    public void verwijderKlimatogram(Klimatogram klimatogram)
    {
        klimatogrammen.remove(klimatogram);
    }

}
