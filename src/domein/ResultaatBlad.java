package domein;

import dto.DeterminatieKnoopDto;
import dto.VegetatieTypeDto;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="determinatieknopen")
public class ResultaatBlad extends DeterminatieKnoop {
   
    private VegetatieType vegetatieType;
    private String klimaatType;

    public String getKlimaatType() {
        return this.klimaatType;
    }

    void setKlimaatType(String klimaatType) {
        this.klimaatType = klimaatType;
    }

    public ResultaatBlad() {
        super();
        vegetatieType = new VegetatieType();
    }

    public ResultaatBlad(int id) {
        super(id);
        vegetatieType = new VegetatieType();
    }
     
     

    @Override
    public void wijzigKnoop(DeterminatieKnoopDto knoop) {
        if (knoop.getId() == getId()) {
            setKlimaatType(knoop.getKlimaattype());
            vegetatieType.setFoto(knoop.getVegetatieType().getFoto());
            vegetatieType.setNaam(knoop.getVegetatieType().getNaam());
        }
    }

    @Override
    public DeterminatieKnoopDto maakDtoAan() {
        DeterminatieKnoopDto dto = new DeterminatieKnoopDto();
        VegetatieTypeDto vegetatieType = new VegetatieTypeDto();
        vegetatieType.setId(this.vegetatieType.getId());
        vegetatieType.setFoto(this.vegetatieType.getFoto());
        vegetatieType.setNaam(this.vegetatieType.getNaam());
        dto.setId(getId());
        dto.setVegetatieType(vegetatieType);
        dto.setKlimaattype(klimaatType);
        return dto;
    }

    public VegetatieType getVegetatieType() {
        return vegetatieType;
    }

    void setVegetatieType(VegetatieType vegetatieType) {
        this.vegetatieType = vegetatieType;
    }

    /**
     * Valideert of deze knoop in orde is. Zijn er null velden die niet mogen?
     */
    @Override
    public void valideer() {
        if (this == null || vegetatieType == null || vegetatieType.getFoto() == null || vegetatieType.getFoto().isEmpty() || vegetatieType.getNaam().isEmpty() || vegetatieType.getNaam() == null || klimaatType == null || klimaatType.isEmpty()) {
            throw new IllegalArgumentException("Knoop " + getId() + " moet correct ingevuld zijn");
        }
    }

}
