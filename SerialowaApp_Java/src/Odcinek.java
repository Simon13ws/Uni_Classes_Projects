import java.util.Calendar;

/** Klasa do tworzenia odcinków dla seriali
 * @author Szymon
 * @see Serial
 */
public class Odcinek {
	/**
	 * Data premiery odcinka
	 */
	private Calendar dataPremiery;
	/**
	 * Dlugoœæ odcinka
	 */
	private int dlugosc;

	/** Konstruktor
	 * @param dlugosc - d³ugoœæ odcinka w minutach
	 * @param dataPremiery - data premiery danego odcinka
	 */
	public Odcinek(int dlugosc, Calendar dataPremiery)
	{
		this.dlugosc = dlugosc;
		this.dataPremiery = dataPremiery;
	}
	// Gettery i Settery
	public Calendar getDataPremiery() {
		return dataPremiery;
	}

	public void setDataPremiery(Calendar dataPremiery) {
		this.dataPremiery = dataPremiery;
	}

	public int getDlugosc() {
		return dlugosc;
	}

	public void setDlugosc(int dlugosc) {
		this.dlugosc = dlugosc;
	}

}
