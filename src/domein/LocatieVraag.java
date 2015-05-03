package domein;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "ToetsVragen")
public class LocatieVraag extends ToetsVraag {

    private HashSet<Klimatogram> klimatogrammen;

    public LocatieVraag() {
        klimatogrammen = new HashSet<>();
    }

    public Set<Klimatogram> getKlimatogrammen() {
        return this.klimatogrammen;
    }

    public void setKlimatogrammen(HashSet<Klimatogram> klimatogrammen) {
        if (klimatogrammen == null) {
            throw new IllegalArgumentException();
        }
        this.klimatogrammen = klimatogrammen;
    }

    /**
     *
     * @param klimatogram
     */
    public void voegKlimatogramToe(Klimatogram klimatogram) {
        if (klimatogram == null) {
            throw new IllegalArgumentException();
        }
        klimatogrammen.add(klimatogram);
    }

    /**
     *
     * @param klimatogram
     */
    public void verwijderKlimatogram(Klimatogram klimatogram) {
        if (klimatogram == null) {
            throw new IllegalArgumentException();
        }
        klimatogrammen.remove(klimatogram);
    }

}
