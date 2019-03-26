import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/** Klasa dziedzicz¹ca po klasie Produkt - rozszerzona o datê wydarzenia i promocjê
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

	/** Konstruktor, uzupe³nia pola klasy Livestream w sposób losowy, po uprzednim wywo³aniu konstruktora klasy nadrzêdnej Produkt
	 * @param dys - dystrybutor Livestream'u
	 * @param czas - aktualny czas symulacji, na podstawie którego jest dobierana data wydarzenia
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
		opis = krajProdukcji + " to miejsce, w którym odbêdzie sie niepowtarzalny livestream. Nie przegap tego! - " + dataWydarzenia.getTime().toString() + ". Po wydarzeniu mo¿esz otworzyæ wideo z ca³ej transmisji jeœli Ciê ono ominê³o.";
	}

	/* Zwraca nazwê klasy
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
