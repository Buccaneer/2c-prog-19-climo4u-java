package domein;

import dto.DeterminatieKnoopDto;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "determinatieknopen")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DeterminatieKnoop implements Valideerbaar {
    @Id
    @Column(name = "DeterminatieKnoopId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public DeterminatieKnoop() {
        id = new Random().nextInt();
    }

    public DeterminatieKnoop(int id) {
        this.id = id;
    }

    
    
    // Omzetten naar dto.
    public abstract DeterminatieKnoopDto maakDtoAan();

    /**
     * Wijzigt de meegegeven knoop.
     *
     * @param knoop
     */
    public abstract void wijzigKnoop(DeterminatieKnoopDto knoop);

}
