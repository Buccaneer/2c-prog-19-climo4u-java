package domein;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import javax.persistence.*;

/**
 *
 * @author Jasper De Vrient
 */
@Entity
@Table(name = "graden")
public class Graad {
    @Id
    @Column(name = "Nummer")
    private int nummer;
    @Id
    @Column(name= "Jaar")
    private int jaar;
    @JoinColumn(name="DeterminatieTabel_DeterminatieTabelId")
    @OneToOne(fetch = FetchType.EAGER)
    private DeterminatieTabel actieveTabel;
    
    private Collection<Klas> klassen = new TreeSet<>(new Comparator<Klas>() {

        @Override
        public int compare(Klas o1, Klas o2) {
          int jaar = o1.getLeerjaar() - o2.getLeerjaar();
          if (jaar == 0)
              return o1.getNaam().compareTo(o2.getNaam());
          return jaar;
        }
        
    });
    
    public void voegKlasToe(Klas klas) {
        klassen.add(klas);
    }
    
    public Collection<Klas> getKlassen() {
        return klassen;
    }
    
    public void verwijderKlas(Klas klas) {
        klassen.remove(klas);
    }

    public DeterminatieTabel getActieveTabel() {
        return actieveTabel;
    }

    public void setActieveTabel(DeterminatieTabel actieveTabel) {
        this.actieveTabel = actieveTabel;
    }
    
    public int getNummer() {
        return nummer;
    }

    public Integer getJaar() {
        return jaar;
    }

    @Override
    public String toString() {
       return "Graad " + nummer + (jaar != 0 ? " jaar " + jaar : "" );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Graad other = (Graad) obj;
        if (this.nummer != other.nummer) {
            return false;
        }
        if (this.jaar != other.jaar) {
            return false;
        }
        return true;
    }

    public int getGraad()
    {
        return nummer;
    }
    
    
}
