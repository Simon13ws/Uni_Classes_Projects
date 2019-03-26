import java.util.Calendar;

/** Klasa film, dziedzicz�ca z klasy Nagranie - rozszerzona o promocj�
 * @author Szymon
 *@see Promocja
 *@see Nagranie
 */
public class Film extends Nagranie {
	/**
	 * Promocja
	 */
	private Promocja promocja;

	public Film(Dystrybutor dys, Czas czas) {
		super(dys);
		promocja = new Promocja(czas);

		opis = "Nietypowy film, kt�rego miejscem akcji jest " + krajProdukcji + ". G��wny bohater ("
				+ listaAktorow.get(0)
				+ ") prze�ywa ciekawe przygody, kt�re tylko z pozoru wydaj� si� codziennym rutynowym zaj�ciem...";
		// System.out.println("Promocja: " + promocja.getUpust() + "% Data rozp.: " +
	}

	/* Zwraca nazw� klasy
	 * @see Obiekt#toString()
	 */
	public String toString() {
		return "Film";
	}

	/** Sprawdza czy produkt jest aktualnie w promocji
	 * @param data - aktualna data w symulacji
	 * @return true je�li produkt jest w promocji, false je�li produkt nie jest w promocji
	 */
	public boolean czyPromocja(Calendar data) {
		if (data.after(promocja.getDataRozpoczecia()) && data.before(promocja.getDataZakonczenia()))
			return true;
		else
			return false;
	}

	// Gettery i Settery
	public Promocja getPromocja() {
		return promocja;
	}

	public void setPromocja(Promocja promocja) {
		this.promocja = promocja;
	}

}
