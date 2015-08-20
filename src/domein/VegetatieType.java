package domein;

import gui.determinatie.Fotos;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vegetatieType")
public class VegetatieType implements Valideerbaar {

    private String foto = "";
    private String naam = "";
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="VegetatieTypeId")
    private int id;

    public int getId() {
        return id;
    }
    public String getFoto() {
        return this.foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public void valideer()
    {
        if (foto == null || naam == null)
            throw new DomeinException();
        for (Fotos value : Fotos.values()) {
            
        }
    }
}
