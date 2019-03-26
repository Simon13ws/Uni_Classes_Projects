import java.util.Calendar;

/** Klasa film, dziedzicz¹ca z klasy Nagranie - rozszerzona o promocjê
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

		opis = "Nietypowy film, którego miejscem akcji jest " + krajProdukcji + ". G³ówny bohater ("
				+ listaAktorow.get(0)
				+ ") prze¿ywa ciekawe przygody, które tylko z pozoru wydaj¹ siê codziennym rutynowym zajêciem...";
		// System.out.println("Promocja: " + promocja.getUpust() + "% Data rozp.: " +
	}

	/* Zwraca nazwê klasy
	 * @see Obiekt#toString()
	 */
	public String toString() {
		return "Film";
	}

	/** Sprawdza czy produkt jest aktualnie w promocji
	 * @param data - aktualna data w symulacji
	 * @return true jeœli produkt jest w promocji, false jeœli produkt nie jest w promocji
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
