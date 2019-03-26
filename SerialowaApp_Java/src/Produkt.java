
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Produkt extends Obiekt {

	/**
	 * Œcie¿ka do zdjêcia
	 */
	protected String zdjecie;
	/**
	 * Opis produktu
	 */
	protected String opis;
	/**
	 * Data produkcji
	 */
	protected Calendar data;
	/**
	 * Dystrybutor, do którego nale¿y produkt
	 */
	protected Dystrybutor dys;
	/**
	 * Cena produktu
	 */
	protected double cena;
	/**
	 * Kraj, w którym wyprodukowano produkt
	 */
	protected String krajProdukcji;
	/**
	 * Aktualna liczba wyœwietleñ produktu
	 */
	protected volatile int wyswietlenia = 0;

	/**
	 * Ocena produktu
	 */
	protected double ocena;
	/**
	 * Liczba wyœwietleñ dla kolejnych tygodni po wypuszczeniu produktu
	 */
	protected ArrayList<Integer> ogladalnosc;
	/**
	 * Data koñca pierwszego tygodnia, przy którym by³a liczona liczba wyswietleñ
	 */
	protected Calendar pierwszyTydzien;

	public Produkt() {
	}

	/**
	 * Konstruktor produktu, losuje wartoœci wiêkszoœci pól (poza liczb¹ wyœwietleñ
	 * i dystrybutorem, którego przypisuje z parametru funkcji)
	 * 
	 * @param dys dystrybutor tego produktu
	 */
	public Produkt(Dystrybutor dys) {
		super();
		// System.out.println("Nowy produkt!");
		ogladalnosc = new ArrayList<Integer>();
		this.dys = dys;
		LosowaWartosc los = new LosowaWartosc();

		Random rand = new Random();

		zdjecie = "Zdjecia/zdj" + rand.nextInt(10) + ".jpeg";

		data = Calendar.getInstance();
		synchronized (Czas.class) {
			data.setTimeInMillis(dys.getSystem().getCzas().getCzas().getTimeInMillis());
		}
		ocena = rand.nextDouble();
		while (ocena < 1)
			ocena *= 10;

		krajProdukcji = los.getKraje()[rand.nextInt(los.getKraje().length - 1)];
	}

	
	

	// Gettery i Settery
	public String getZdjecie() {
		return zdjecie;
	}

	public void setZdjecie(String zdjecie) {
		this.zdjecie = zdjecie;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Dystrybutor getDys() {
		return dys;
	}

	public void setDys(Dystrybutor dys) {
		this.dys = dys;
	}

	public synchronized Double getCena() {
		return cena;
	}

	public synchronized void setCena(Double cena) {
		this.cena = cena;
	}

	public String getKrajProdukcji() {
		return krajProdukcji;
	}

	public void setKrajProdukcji(String krajProdukcji) {
		this.krajProdukcji = krajProdukcji;
	}

	public synchronized int getWyswietlenia() {
		return wyswietlenia;
	}

	public synchronized void setWyswietlenia(int wyswietlenia) {
		this.wyswietlenia = wyswietlenia;
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}

	public ArrayList<Integer> getOgladalnosc() {
		return ogladalnosc;
	}

	public void setOgladalnosc(ArrayList<Integer> ogladalnosc) {
		this.ogladalnosc = ogladalnosc;
	}

	public Calendar getPierwszyTydzien() {
		return pierwszyTydzien;
	}

	public void setPierwszyTydzien(Calendar pierwszyTydzien) {
		this.pierwszyTydzien = pierwszyTydzien;
	}
}
