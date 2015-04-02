package domein;

public abstract class DeterminatieKnoop {

	private int id;

	/**
	 * 
	 * @param knoop
	 */
	public void setLinkerKnoop(DeterminatieKnoop knoop) {
		// TODO - implement DeterminatieKnoop.setLinkerKnoop
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param knoop
	 */
	public void setRechterKnoop(DeterminatieKnoop knoop) {
		// TODO - implement DeterminatieKnoop.setRechterKnoop
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param knoop
	 */
	public abstract void bouwKnoop(DeterminatieKnoopDto knoop);

}