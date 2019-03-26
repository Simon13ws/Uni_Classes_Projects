import java.util.Calendar;
import java.util.Random;


/** Klasa promocja - dla produkt�w zawieraj�cych promocj� (film i livestream'y)
 * @author Szymon
 *	@see  Livestream
 *	@see Film
 */
public class Promocja {


	/**
	 * Data rozpocz�cia promocji
	 */
	private Calendar dataRozpoczecia;
	/**
	 * Data zako�czenia promocji
	 */
	private Calendar dataZakonczenia;
	/**
	 * Upust procentowy na produkt
	 */
	private double upust;
	
	/**
	 * Konstruktor promocji uwzgl�dniaj�c aktualny czas symulacji, aby promocja by�a w przysz�o�ci 
	 * @param czas - aktualna data symulacji
	 */
	public Promocja(Czas czas) {
		dataRozpoczecia = Calendar.getInstance();
		dataZakonczenia = Calendar.getInstance();
		Random rand = new Random();
		int dzien = 3600000*24;
		dataRozpoczecia.setTimeInMillis(czas.getCzas().getTimeInMillis()+rand.nextInt(dzien*365)+dzien*1);
		dataRozpoczecia.set(Calendar.MINUTE,0);
		dataRozpoczecia.set(Calendar.SECOND, 0);
		upust = ((rand.nextInt(11))*5)/100;
		dataZakonczenia.setTimeInMillis(dataRozpoczecia.getTimeInMillis()+rand.nextInt(3600000*24*12)+3600000*24*2);
		dataZakonczenia.set(Calendar.MINUTE,0);
		dataZakonczenia.set(Calendar.SECOND, 0);
		//System.out.println("rozpoczecie promocji: "+ dataRozpoczecia.getTime() + " koniec promocji: " + dataZakonczenia.getTime());
		
	}
	
	// Gettery i Settery
	public Calendar getDataRozpoczecia() {
		return dataRozpoczecia;
	}

	public void setDataRozpoczecia(Calendar dataRozpoczecia) {
		this.dataRozpoczecia = dataRozpoczecia;
	}

	public Calendar getDataZakonczenia() {
		return dataZakonczenia;
	}

	public void setDataZako�czenia(Calendar dataZakonczenia) {
		this.dataZakonczenia = dataZakonczenia;
	}

	public double getUpust() {
		return upust;
	}

	public void setUpust(double upust) {
		this.upust = upust;
	}

}
