import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/** Klasa dziedzicz�ca po klasie Produkt - rozszerzona o dat� wydarzenia i promocj�
 * @author Szymon
 * @see Produkt
 */
public class Livestream extends Produkt {
	/**
	 * Data wydarzenia
	 */
	private Calendar dataWydarzenia;
	/**
	 * Promocja
	 * @see Promocja
	 */
	private Promocja promocja;

	/** Konstruktor, uzupe�nia pola klasy Livestream w spos�b losowy, po uprzednim wywo�aniu konstruktora klasy nadrz�dnej Produkt
	 * @param dys - dystrybutor Livestream'u
	 * @param czas - aktualny czas symulacji, na podstawie kt�rego jest dobierana data wydarzenia
	 */
	public Livestream(Dystrybutor dys, Czas czas) {
		super(dys);
		Random rand = new Random();
		this.dys = dys;
		promocja = new Promocja(czas);
		cena = rand.nextInt(10)+5;
		dataWydarzenia = Calendar.getInstance();
		long dzien = 3600000*24;
		dataWydarzenia.setTimeInMillis(dys.getSystem().getCzas().getCzas().getTimeInMillis()+(ThreadLocalRandom.current().nextLong(dzien*1, dzien*30)));
		dataWydarzenia.set(Calendar.MINUTE,0);
		dataWydarzenia.set(Calendar.SECOND, 0);
		//System.out.println("Konstruktor livestream'u: " + nazwa + " Data wydarzenia: " + dataWydarzenia.getTime());
		opis = krajProdukcji + " to miejsce, w kt�rym odb�dzie sie niepowtarzalny livestream. Nie przegap tego! - " + dataWydarzenia.getTime().toString() + ". Po wydarzeniu mo�esz otworzy� wideo z ca�ej transmisji je�li Ci� ono omin�o.";
	}

	/* Zwraca nazw� klasy
	 * @see Obiekt#toString()
	 */
	public String toString() {
		return "Livestream";
	}

	public boolean czyPromocja(Calendar data)
	{
		if(data.after(promocja.getDataRozpoczecia()) && data.before(promocja.getDataZakonczenia()))
			return true;
		else return false;
	}
	// Gettery i Settery
	public Calendar getDataWydarzenia() {
		return dataWydarzenia;
	}

	public void setDataWydarzenia(Calendar dataWydarzenia) {
		this.dataWydarzenia = dataWydarzenia;
	}

	public Promocja getPromocja() {
		return promocja;
	}

	public void setPromocja(Promocja promocja) {
		this.promocja = promocja;
	}

}
