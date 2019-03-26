import java.util.Set;
import java.util.HashSet;
import java.math.BigDecimal;
import java.util.Random;

/**
 * Klasa dystrybutor
 * 
 * @author Szymon
 *
 */
public class Dystrybutor extends Uzytkownik {

	/**
	 * Nr konta bankowego dystrybutora
	 */
	private BigDecimal nrKonta;
	/**
	 * Kwota, któr¹ dystrybutor dostaje co miesi¹c za jeden produkt
	 */
	private int umowa = 300;
	/**
	 * Zbiór produktów dystrybutora
	 */
	private Set<Produkt> wlasneProdukty;

	/*
	 * W¹tek, w którym dysttrybutor w losowych odstêpach czasu wypuszcza produkt -
	 * przy ka¿dym nowym produkcie do systemu dodawana jest losowa liczba klientów
	 */
	public void run() {
		Random rand = new Random();
		try {
			while (!interrupted()) {
				// losowy sleep i po nim wypuszczenie produktu i dodaniu losowej liczby klientow
				Thread.sleep(1000 * rand.nextInt(9) + 1);
				wypuscProdukt();
				int los = rand.nextInt(7) + 1;
				for (int i = 0; i < los; i++) {
					Klient k = new Klient(system);
					// k.start();
				}
			}
		} catch (InterruptedException e) {
			System.out.println("W¹tek dystrybutora przerwany!");
			system.UsunElement(this);
		}
	}

	
	/**
	 * Wypuszczenie produkt losowego rodzaju (Film, Serial lub Livestream) i dodanie go do zbioru w³asnych produktów
	 */
	public void wypuscProdukt() {
		Random rand = new Random();
		int los;
		// z niewiadomego mi powodu program siê crashuje, gdy pierwszym dodajnym
		// produktem jest Livestream
		synchronized (Uzytkownik.class) {
			if (system.getProdukty().size() == 0)
				los = rand.nextInt(2);
			else
				los = rand.nextInt(3);
		}
		Produkt nowy;
		if (los == 0) {
			nowy = new Serial(this);
		} else if (los == 1) {
			nowy = new Film(this, system.getCzas());
		} else {
			nowy = new Livestream(this, system.getCzas());
		}
		wlasneProdukty.add(nowy);
		synchronized (Uzytkownik.class) {
			system.getProdukty().add(nowy);
		}
	};

	
	/** Konstruktor - rozpoczyna nowy w¹tek dystrybutora 
	 * @param system - system, w którym znajduje siê dystrybutor
	 
	 */
	public Dystrybutor(SystemAplikacji system) {
		super(system);
		wlasneProdukty = new HashSet<>();
		LosowaWartosc losowa = new LosowaWartosc();
		nrKonta = losowa.losowyNumer(26);
		// jeszcze umowa

		synchronized (this) {
			system.getDystrybutorzy().add(this);
		}
		// System.out.println("Nowy dystrybutor!");
		this.start();

	}

	
	/* Zwraca nazwê klasy
	 * @see Obiekt#toString()
	 */
	public String toString() {
		return "Dystrybutor";
	}

	// Gettery i Settery
	public BigDecimal getNrKonta() {
		return nrKonta;
	}

	public void setNrKonta(BigDecimal nrKonta) {
		this.nrKonta = nrKonta;
	}

	public int getUmowa() {
		return umowa;
	}

	public void setUmowa(int umowa) {
		this.umowa = umowa;
	}

	public Set<Produkt> getWlasneProdukty() {
		return wlasneProdukty;
	}

	public void setWlasneProdukty(Set<Produkt> wlasneProdukty) {
		this.wlasneProdukty = wlasneProdukty;
	}
}
