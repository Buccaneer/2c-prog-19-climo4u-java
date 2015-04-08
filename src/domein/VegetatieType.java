package domein;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vegetatieType")
public class VegetatieType {

    private String foto;
    private String naam;
    @Id
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
}
