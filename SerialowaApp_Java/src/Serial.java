import java.util.ArrayList;
import java.util.Random;
import java.util.Calendar;

/** Klasa serial, dziedzicz�ca po klasie Nagranie - rozszerzona o odcinki i liczb� sezon�w
 * @author Szymon
 *	@see Nagranie
 */
public class Serial extends Nagranie {
	/**
	 * Lista odcink�w
	 * @see Odcinek
	 */
	private ArrayList<Odcinek> odcinki;
	/**
	 * Liczba sezon�w
	 */
	private int sezony;

	public Serial() {
	}

	/** Konstruktor, losowo wype�nia pola klasy Serial, po uprzednim wywo�aniu konstruktora klas Nagraniei podaniu w�asnego dystrybutora
	 * @param dys - dystrybutor serialu
	 */
	public Serial(Dystrybutor dys) {
		super(dys);
		odcinki = new ArrayList<Odcinek>();
		Random rand = new Random();
		sezony = rand.nextInt(10) + 1;
		int ileOdcinkow = (rand.nextInt(14) + 2) * sezony;
		int dlugoscOdcinka = rand.nextInt(40) + 20;
		Calendar dataPremiery = Calendar.getInstance();
		for (int i = 0; i < ileOdcinkow; i++) {
			if (i == 0) {
				int rok = rand.nextInt(22) + 2000;
				int miesiac = rand.nextInt(12) + 1;
				int dzien = rand.nextInt(28) + 1;
				dataPremiery.set(rok, miesiac, dzien, 0, 0, 0);
			} else {
				dataPremiery.setTimeInMillis(
						dataPremiery.getTimeInMillis() + rand.nextInt(3600000 * 24 * 20) + 3600000 * 24);
			}
			Odcinek odc = new Odcinek(dlugoscOdcinka, dataPremiery);
			odcinki.add(odc);
			opis = "Nietypowy serial, kt�rego miejscem akcji jest " + krajProdukcji + ". G��wny bohater ("
					+ listaAktorow.get(0)
					+ ") prze�ywa ciekawe przygody, kt�re tylko z pozoru wydaj� si� codziennym rutynowym zaj�ciem... Serial sk�ada si� z "
					+ ileOdcinkow + " odcink�w.";
			// System.out.println("Odcinek nr " + (i+1) + ". Data premiery - " +
			// odc.getDataPremiery().getTime());
		}

		// System.out.println("Konstruktor serialu: Nazwa - " + nazwa + "\n Gatunek - "
		// + gatunek + "\n Cena - "
		// + cena + " \n Aktorzy: " + listaAktorow);

		// Nie wiem czy dobrze wyswietla czas
		// for (Odcinek odc : odcinki)
		// System.out.println(odc.getDataPremiery());
	}

	/* Zwraca nazw� klasy
	 * @see Obiekt#toString()
	 */
	public String toString() {
		return "Serial";
	}

	// Gettery i Settery
	public ArrayList<Odcinek> getOdcinki() {
		return odcinki;
	}

	public void setOdcinki(ArrayList<Odcinek> odcinki) {
		this.odcinki = odcinki;
	}

	public int getSezony() {
		return sezony;
	}

	public void setSezony(int sezony) {
		this.sezony = sezony;
	}

}
