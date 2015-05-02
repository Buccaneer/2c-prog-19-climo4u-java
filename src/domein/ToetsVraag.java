package domein;

public abstract class ToetsVraag {

	private int Id;
	private int teBehalenPunten;
	private String beschrijving;

	public int getTeBehalenPunten() {
		return this.teBehalenPunten;
	}

	public void setTeBehalenPunten(int teBehalenPunten) {
		this.teBehalenPunten = teBehalenPunten;
	}

	public String getBeschrijving() {
		return this.beschrijving;
	}

	public void setBeschrijving(String beschrijving) {
		this.beschrijving = beschrijving;
	}

}