package domein;

import javax.persistence.*;

@Entity
@Table(name = "ToetsVragen")
@DiscriminatorColumn(name = "Discriminator")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ToetsVraag
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int teBehalenPunten;
    private String beschrijving;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getTeBehalenPunten()
    {
        return this.teBehalenPunten;
    }

    public void setTeBehalenPunten(int teBehalenPunten)
    {   
        this.teBehalenPunten = teBehalenPunten;
    }

    public String getBeschrijving()
    {
        return this.beschrijving;
    }

    public void setBeschrijving(String beschrijving)
    {
        this.beschrijving = beschrijving;
    }

}
