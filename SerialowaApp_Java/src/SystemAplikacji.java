
import java.util.LinkedHashSet;
import java.util.Random;

/**
 * Klasa zawieraj¹ca informacje o systemie - posiada zbiory klientów,
 * dystrybutorów i produtków, na których wykonywane s¹ akcje przez u¿ytkownika
 * 
 * @author Szymon
 * @see Czas
 */
public class SystemAplikacji {
	/**
	 * Zbiór produktów
	 */
	private LinkedHashSet<Produkt> produkty;
	/**
	 * Zbiór klientów korzystaj¹cych z systemu
	 */
	private LinkedHashSet<Klient> klienci;
	/**
	 * Zbiór dystrybutorów, którzy wypuszczaj¹ produkty w systemie
	 */
	private LinkedHashSet<Dystrybutor> dystrybutorzy;
	/**
	 * Miesiêczny przychód systemu - resetowany przez klasê Czas, która jest
	 * odpowiedzialna za symulacjê
	 */
	private double przychod = 0;
	/**
	 * Obiekt klasy Czas odpowiedzialny za symulacjê
	 */
	private Czas czas;
	/**
	 * Dostêpne umowy dla dystrybutorów
	 */
	private int[] umowy = { 0, 300, 400, 500, 600, 700 };

	/**
	 * Konstrktor
	 */
	public SystemAplikacji() {
		produkty = new LinkedHashSet<>();
		klienci = new LinkedHashSet<>();
		dystrybutorzy = new LinkedHashSet<>();
	}

	/**
	 * Usuwa podany element z systemu - sprawdza, z którego zbioru jest dany element
	 * i go usuwa - jeœli usuwa dystrybutora to usuwa równie¿ jego produkty z
	 * sytemu, jeœli usuwa produkty to usuwa równie¿ losow¹ liczbê klientów systemu
	 * 
	 * @param obiekt - element do usuniêcia z systemu
	 */
	public <T extends Obiekt> void UsunElement(T obiekt) {

		if (obiekt.toString().equals("Serial") || obiekt.toString().equals("Livestream")
				|| obiekt.toString().equals("Film")) {
			produkty.remove(obiekt);
		} else if (obiekt.toString().equals("Klient")) {
			klienci.remove(obiekt);
			((Klient) obiekt).interrupt();
		} else { // Zanim usunie dystrybutora usuwa jego produkty z systemu i kilku losowych
					// klientow
			Random rand = new Random();
			for (int l = 0; l < ((Dystrybutor) obiekt).getWlasneProdukty().size(); l++) {
				int los = rand.nextInt(10) + 1; // usunie klientow proporcjonalnie do liczby usunietych produktow
				for (int i = 0; i < los; i++) {
					if (klienci.size() > 0) {
						int los2 = rand.nextInt(klienci.size());
						int j = 0;
						Klient kl = new Klient();
						for (Klient k : klienci) {
							if (j == los2) {
								kl = k;
								break;
							}
							j++;
						}
						kl.interrupt();
						klienci.remove(kl);
					}
				}
			}
			// usuwanie wszystkich produktow
			produkty.removeAll(((Dystrybutor) obiekt).getWlasneProdukty());
			dystrybutorzy.remove(obiekt);
			((Dystrybutor) obiekt).interrupt();

		}
	}

	/**
	 * Pobiera pieni¹dze za abonament od klientów - co miesi¹c, co jest kontrolowane przez obiekt klasy Czas
	 */
	public void pobierzAbonament() {
		synchronized (Uzytkownik.class) {
			for (Klient k : klienci)
				przychod += k.getAbonament().getCena();
		}
	}

	/**
	 * Negocjuje umowy z dystrybutorami - wybiera losow¹ wartoœæ z tablicy umowy
	 */
	public void negocjujUmowy() {
		Random rand = new Random();
		int los = rand.nextInt(6);
		if (los > 0) {
			synchronized (Uzytkownik.class) {
				for (Dystrybutor d : dystrybutorzy) {
					d.setUmowa(umowy[los]);
				}
			}
		}
	}

	/**
	 * P³aci dystrybutorom pieni¹dze na podstawie umowy (zmniejsza wartoœæ przychodu z danego miesi¹ca)
	 */
	public void zaplac() {
		synchronized (Uzytkownik.class) {
			for (Dystrybutor d : dystrybutorzy)
				przychod -= d.getUmowa() * d.getWlasneProdukty().size();
		}
	}

	/**
	 * Dla ka¿dego produktu aktualizuje ogl¹dalnoœæ - co tydzieñ co jest kontrolowane przez obiekt klasy Czas
	 */
	public void aktualizujOgladalnosc() {
		for (Produkt p : produkty) {
			p.ogladalnosc.add(p.getWyswietlenia());
			if (p.getPierwszyTydzien() == null)
				p.setPierwszyTydzien(czas.getCzas());
		}
	}

	// Gettery i Settery
	public LinkedHashSet<Produkt> getProdukty() {
		synchronized (this) {
			return produkty;
		}
	}

	public void setProdukty(LinkedHashSet<Produkt> produkty) {
		synchronized (this) {
			this.produkty = produkty;
		}
	}

	public synchronized LinkedHashSet<Klient> getKlienci() {
		return klienci;
	}

	public void setKlienci(LinkedHashSet<Klient> klienci) {
		this.klienci = klienci;
	}

	public LinkedHashSet<Dystrybutor> getDystrybutorzy() {
		return dystrybutorzy;
	}

	public void setDystrybutorzy(LinkedHashSet<Dystrybutor> dystrybutorzy) {
		this.dystrybutorzy = dystrybutorzy;
	}

	public double getPrzychod() {
		return przychod;
	}

	public void setPrzychod(double przychod) {
		this.przychod = przychod;

	}

	public Czas getCzas() {
		return czas;
	}

	public void setCzas(Czas czas) {
		this.czas = czas;
	}

}
