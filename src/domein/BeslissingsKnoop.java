package domein;

import dto.DeterminatieKnoopDto;

public class BeslissingsKnoop extends DeterminatieKnoop {

	private DeterminatieKnoop juistKnoop;
	private DeterminatieKnoop foutKnoop;
	private Vergelijking vergelijking;

    @Override
    public void wijzigKnoop(DeterminatieKnoopDto knoop) {
       if (knoop.getId() == getId()) {
           // Wijzig mijn knoop (this)
         
       } else
       {
           if (moetOmzetten(juistKnoop, knoop) == -1) {
               // Vervangen afhankelijk van scenario
           } else if (moetOmzetten(foutKnoop, knoop) == -1)
           {
               // Vervangen afhankelijk van scenario
           }
           juistKnoop.wijzigKnoop(knoop);
           foutKnoop.wijzigKnoop(knoop);
       }
    }
        
    @Override
    public void setLinkerKnoop(DeterminatieKnoop knoop) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setRechterKnoop(DeterminatieKnoop knoop) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    DeterminatieKnoopDto maakDtoAan() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
  
    
    /**
     * <pre>Remark: moet private worden maar momenteel public door test.</pre>
     * Kijkt of de ouder een van zijn kinderen moet omzetten.
     * @return 3 gevallen: <ul><li>
     * -1: ResultaatBlad --> BeslissingsKnoop
     * </li></li>0: niet</li>
     * <li>1: BeslissingsKnoop --> ResultaatBlad</li></ul>
     */
    public int moetOmzetten(DeterminatieKnoop kind, DeterminatieKnoopDto knoop) {
       if (kind.getId() == knoop.getId()) {
           if (knoop.isResultaatBlad() && kind instanceof BeslissingsKnoop) 
               return 1;
           
           if (knoop.isResultaatKnoop() && kind instanceof ResultaatBlad) 
               return -1;
           
           
           return 0;
       }
       return 0;
    }
    
    /**
     * Valideert of deze knoop en al zijn kinderen in orde zijn.
     */
    @Override
    public void valideer()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DeterminatieKnoop getJuistKnoop() {
        return juistKnoop;
    }

     void setJuistKnoop(DeterminatieKnoop juistKnoop) {
        this.juistKnoop = juistKnoop;
    }

    public DeterminatieKnoop getFoutKnoop() {
        return foutKnoop;
    }

     void setFoutKnoop(DeterminatieKnoop foutKnoop) {
        this.foutKnoop = foutKnoop;
    }

    public Vergelijking getVergelijking() {
        return vergelijking;
    }

     void setVergelijking(Vergelijking vergelijking) {
        this.vergelijking = vergelijking;
    }
    
    

}
