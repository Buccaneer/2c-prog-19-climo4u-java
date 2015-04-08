package domein;

import dto.DeterminatieKnoopDto;

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
    }

    public ResultaatBlad(int id) {
        super(id);
    }
     
     

    @Override
    public void wijzigKnoop(DeterminatieKnoopDto knoop) {
       if (knoop.getId() == getId()) {
          
           // Wijzig mijn knoop (attributen).
       }
    }

    @Override
    public DeterminatieKnoopDto maakDtoAan() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    public VegetatieType getVegetatieType() {
        return vegetatieType;
    }

     void setVegetatieType(VegetatieType vegetatieType) {
        this.vegetatieType = vegetatieType;
    }
    
    

    @Override
    public void setLinkerKnoop(DeterminatieKnoop knoop) {
    }

    @Override
    public void setRechterKnoop(DeterminatieKnoop knoop) {
    }

    /**
     * Valideert of deze knoop in orde is. Zijn er null velden die niet mogen?
     */
    @Override
    public void valideer()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
