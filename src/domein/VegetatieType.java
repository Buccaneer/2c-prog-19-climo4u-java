package domein;

public class VegetatieType {

    private String foto;
    private String naam;
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
