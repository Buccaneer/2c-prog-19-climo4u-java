package domein;

import dto.DeterminatieKnoopDto;

public class BeslissingsKnoop extends DeterminatieKnoop {

	private DeterminatieKnoop juistKnoop;
	private DeterminatieKnoop foutKnoop;
	private Vergelijking vergelijking;

    @Override
    public void bouwKnoop(DeterminatieKnoopDto knoop) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}