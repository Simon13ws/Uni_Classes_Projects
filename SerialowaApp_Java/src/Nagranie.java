import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa nadrz�dna dla klas Film i Serial, klasa podrz�dna klasy Produkt
 * 
 * @author Szymon
 * @see Produkt
 * @see Film
 * @see Serial
 */
public class Nagranie extends Produkt { // wideo
	protected enum Gatunek {
		Sensacyjny, Dramat, Komedia, Dla_dzieci, Dokumentalny, Akcja;
	}

	/**
	 * Zmienna do losowania Enuma
	 */
	protected LosowyEnum<Gatunek> los = new LosowyEnum<Gatunek>(Gatunek.class); // do losowania enuma
	/**
	 * Gatunek nagrania - jeden z dost�pnych enum�w
	 */
	protected Gatunek gatunek;
	/**
	 * Lista aktor�w
	 */
	protected ArrayList<String> listaAktorow;

	/**
	 * Dost�pni aktorzy
	 */
	protected String[] aktorzy = { "Jospeh Loftus", "Don Lanctot", "Jackson Lei", "Lon Fetterman", "Ross Oltman",
			"Seth Locicero", "Gerald Hockenberry", "Orlando Briski", "Casey Sarvis", "Harvey Noack", "Ron Soldner",
			"Shelton Colone", "Clyde Schmitt", "Deangelo Stivers", "Darren Brush", "Andreas Hook", "Darrel Munger",
			"Rodolfo Razo", "Eddie Sherry", "Frederic Pritchard", "Ericka Fyffe", "Shelba Sandt", "Britany Francis",
			"Terra Dufault", "Nakisha Krouse", "Hee Galligan", "Felisha Bevan", "Nathalie Barsky", "Lottie Uribe",
			"Jodee Kinzel", "Tiffany Tash", "Louise Okafor", "Jacquelyn Mccorkle", "Apryl Doolin", "Tari Dolan",
			"Christa Bedsole", "Etta Lainez", "Cinda Milbrandt", "Latrice Stumpf", "Isadora Mckinzie", };

	public Nagranie() {

	}

	/**
	 * Konstruktor, kt�ry wype�nia pola klasy Nagranie w spos�b losowy i przypisuje
	 * dystrybutora poprzez wywo�anie konstruktora klasy Produkt
	 * 
	 * @param dys - dystrybutor nagrania
	 */
	public Nagranie(Dystrybutor dys) {
		super(dys);
		Random rand = new Random();
		int iluAktorow = rand.nextInt(8) + 2;
		listaAktorow = new ArrayList<>();
		for (int i = 0; i < iluAktorow; i++) {
			String nazwa;
			do {
				int los = rand.nextInt(40);
				nazwa = aktorzy[los];
			} while (listaAktorow.contains(nazwa));
			listaAktorow.add(nazwa);
		}

		gatunek = los.losuj();
		cena = rand.nextInt(20) + 10;

	}

	// Gettery i Settery
	public Gatunek getGatunek() {
		return gatunek;
	}

	public void setGatunek(Gatunek gatunek) {
		this.gatunek = gatunek;
	}

	public ArrayList<String> getListaAktorow() {
		return listaAktorow;
	}

	public void setListaAktorow(ArrayList<String> listaAktorow) {
		this.listaAktorow = listaAktorow;
	}

}
