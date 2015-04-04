package domein;

import dto.DeterminatieKnoopDto;

public abstract class DeterminatieKnoop {

	private int id;

	/**
	 * 
	 * @param knoop
	 */
	public abstract void setLinkerKnoop(DeterminatieKnoop knoop);

	/**
	 * 
	 * @param knoop
	 */
	public abstract void setRechterKnoop(DeterminatieKnoop knoop);

	/**
	 * 
	 * @param knoop
	 */
	public abstract void bouwKnoop(DeterminatieKnoopDto knoop);

}