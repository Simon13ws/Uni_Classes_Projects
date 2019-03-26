import java.util.Calendar;
import java.math.BigDecimal;
import java.util.Random;

/** Klasa dziedzicz�ca po klasie u�ytkownik, poszerzona o dat� urodzenia, nr karty i abonament jaki ma u�ytkownik
 * @author Szymon
 * @see Uzytkownik
 */
public class Klient extends Uzytkownik {
	/**
	 * Data urodzenia u�ytkownika
	 */
	private Calendar dataUrodzenia;
	/**
	 * Nr karty u�ytkownika
	 */
	private BigDecimal nrKarty;
	/**
	 * Abonament, jaki ma klient
	 * @see Abonament
	 */
	private Abonament abonament;

	public Klient() {

	}

	/** Konstruktor - wywo�anie konstruktora klasy nadrz�dnej oraz losowe uzupe�enie p�l, o kt�re zosta�a rozszerzona
	 * @param system - system, z kt�rego korzysta klient
	 */
	public Klient(SystemAplikacji system) {
		super(system);
		LosowaWartosc losowy = new LosowaWartosc();
		nrKarty = losowy.losowyNumer(16);
		Random rand = new Random();
		dataUrodzenia = Calendar.getInstance();
		int rok = rand.nextInt(40) + 1970;
		int miesiac = rand.nextInt(12) + 1;
		int dzien = rand.nextInt(28) + 1;
		dataUrodzenia.set(rok, miesiac, dzien, 0, 0, 0);
		abonament = new Abonament();
		// System.out.println(nazwa + " " + email + " " + nrKarty +
		// dataUrodzenia.getTime());
		synchronized (Uzytkownik.class) {
			system.getKlienci().add(this);
		}
		//System.out.println("Nowy klient!");
		this.start();
	}

	/* W�tek klasy Klient s�u��cy do ogl�dania produkt�w
	 * @see Uzytkownik#run()
	 */
	public void run() { // ma dodawa� wyswietlenia do losowych produktow w losowym czasie
		try {
			while (!interrupted()) {
				Thread.sleep(1000);
				ogladaj();
			}
		} catch (InterruptedException e) {
			System.out.println("W�tek klienta przerwany!");
		}
	}

	/**
	 * Ogl�danie produktu z systemu, losowo wybranego przy uwzgl�dnieniu, �e data nie mo�e by� przed Livestreamem
	 * Je�li klient nie ma abonamentu to p�aci za obejrzenie produktu
	 */
	public void ogladaj() {
		Random rand = new Random();
		synchronized (Uzytkownik.class) {
			if (system.getProdukty().size() > 0) {
				Produkt doObejrzenia = new Produkt();
				do {
					int los = rand.nextInt(system.getProdukty().size());
					int i = 0;
					for (Produkt p : system.getProdukty()) {
						if (i == los) {
							doObejrzenia = p;
							break;
						}
						i++;
					}
				} while (doObejrzenia.toString().equals("Livestream") //Sprawdza czy produkt nie jest livestreamem, ktory jeszcze sie nie odbywa/odbyl
						&& ((Livestream) doObejrzenia).getDataWydarzenia().after(system.getCzas().getCzas()));
				
				Produkt p = doObejrzenia;
				p.setWyswietlenia(p.getWyswietlenia()+1);
				// P�atno�� za livestreamy i produkty, gdy klient nie ma abonamentu
				if (abonament.getJaki().equals("Brak") || p.toString().equals("Livestream")) {
					double dodaj = system.getPrzychod();
					if ((p.toString().equals("Livestream") // SPRAWDZANIE CZY AKTUALNIE JEST PROMOCJA, JAK TAK TO W
															// CENIE UWZGLEDNIA UPUST
							&& ((Livestream) p).czyPromocja(system.getCzas().getCzas()))) {
						dodaj += p.cena * (1 - ((Livestream) p).getPromocja().getUpust());
					} else if ((p.toString().equals("Film") && ((Film) p).czyPromocja(system.getCzas().getCzas()))) {
						dodaj += p.cena * (1 - ((Film) p).getPromocja().getUpust());
					} else
						dodaj += p.cena;
					system.setPrzychod(dodaj);
				}
			}
			
		}
	}


	/* Zwraca nazw� klasy
	 * @see Obiekt#toString()
	 */
	public String toString() {
		return "Klient";
	}

	// Gettery i Settery
	public Calendar getDataUrodzenia() {
		return dataUrodzenia;
	}

	public void setDataUrodzenia(Calendar dataUrodzenia) {
		this.dataUrodzenia = dataUrodzenia;
	}

	public BigDecimal getNrKarty() {
		return nrKarty;
	}

	public void setNrKarty(BigDecimal nrKarty) {
		this.nrKarty = nrKarty;
	}

	public Abonament getAbonament() {
		return abonament;
	}

	public void setAbonament(Abonament abonament) {
		this.abonament = abonament;
	}

}
