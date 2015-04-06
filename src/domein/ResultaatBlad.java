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

    @Override
    public void wijzigKnoop(DeterminatieKnoopDto knoop) {
       if (knoop.getId() == getId()) {
           // Wijzig mijn knoop.
       }
    }

    @Override
    DeterminatieKnoopDto maakDtoAan() {
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
     * Valideert of deze knoop in orde is.
     */
    @Override
    public void valideer()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
