package domein;

import java.util.HashSet;
import java.util.Set;

public class DerdeGraadVraag extends ToetsVraag {

	private Set<Klimatogram> klimatogrammen;

    public DerdeGraadVraag() {
        klimatogrammen=new HashSet<>();
    }
        
	public Set<Klimatogram> getKlimatogrammen() {
		return this.klimatogrammen;
	}

	public void setKlimatogrammen(HashSet<Klimatogram> klimatogrammen) {
		this.klimatogrammen = klimatogrammen;
	}

	/**
	 * 
	 * @param klimatogram
	 */
	public void voegKlimatogramToe(Klimatogram klimatogram) {
		klimatogrammen.add(klimatogram);
	}

	/**
	 * 
	 * @param klimatogram
	 */
	public void verwijderKlimatogram(Klimatogram klimatogram) {
		klimatogrammen.remove(klimatogram);
	}

}