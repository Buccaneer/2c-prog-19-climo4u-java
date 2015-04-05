package domein;

import dto.DeterminatieKnoopDto;

public class ResultaatBlad extends DeterminatieKnoop {

    private VegetatieType vegetatieType;
    private String klimaatType;

    public String getKlimaatType() {
        return this.klimaatType;
    }

    public void setKlimaatType(String klimaatType) {
        this.klimaatType = klimaatType;
    }

    @Override
    public void bouwKnoop(DeterminatieKnoopDto knoop) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
