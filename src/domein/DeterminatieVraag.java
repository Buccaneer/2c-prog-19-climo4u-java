package domein;

import javax.persistence.*;

@Entity
@Table(name = "ToetsVragen")
public class DeterminatieVraag extends ToetsVraag
{

    @OneToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Klimatogram klimatogram;

    public Klimatogram getKlimatogram()
    {
        return this.klimatogram;
    }

    public void setKlimatogram(Klimatogram klimatogram)
    {
        this.klimatogram = klimatogram;
    }

}
