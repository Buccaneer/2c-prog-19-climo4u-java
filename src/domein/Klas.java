package domein;

import java.util.*;

public class Klas {
    
    private Graad graad;
    private Collection<Leerling> leerlingen = new TreeSet<>(new Comparator<Leerling>() {
        @Override
        public int compare(Leerling o1, Leerling o2) {
            int naam = o1.getNaam().compareTo(o2.getNaam());
            if (naam == 0)
                return o1.getVoornaam().compareTo(o2.getVoornaam());
            return naam;
        }
    });
    
    private int id;
    private String naam;
    private int leerjaar;

    public int getId() {
        return id;
    }
    
    
    
    public String getNaam() {
        return this.naam;
    }
    
    public void setNaam(String naam) {
        this.naam = naam;
    }
    
    public int getLeerjaar() {
        return this.leerjaar;
    }
    
    public void setLeerjaar(int leerjaar) {
        this.leerjaar = leerjaar;
    }

    /**
     *
     * @param leerling
     */
    public void voegLeerlingToe(Leerling leerling) {
        leerlingen.add(leerling);
    }
    
    public void verwijderLeerling(Leerling leerling) {
        leerlingen.remove(leerling);
    }
    
    public Collection<Leerling> getLeerlingen() {
        return leerlingen;
    }
    
    public Graad getGraad() {
        return this.graad;
    }

    /**
     *
     * @param graad
     */
    public void setGraad(Graad graad) {
        this.graad = graad;
    }
    
}