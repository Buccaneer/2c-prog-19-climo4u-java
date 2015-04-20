package domein;

import java.util.LinkedList;
import java.util.List;
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
    @OneToOne()
    private DeterminatieTabel actieveTabel;

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
    
    
}
