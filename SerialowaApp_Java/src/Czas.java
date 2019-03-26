import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @author Szymon
 * 
 */
public class Czas extends Thread {

	/**
	 * Zmienna przechowuj�ca aktualn� dat� w symulacji
	 */
	private Calendar czas = Calendar.getInstance();

	/**
	 * Zmienna przechowuj�ca czas zako�czenia ostatniego tygodnia symulacji
	 */
	private Calendar tydzien = Calendar.getInstance();

	/**
	 * Zmienna przechowuj�ca czas zako�czenia ostatniego miesi�ca symulacji
	 */
	private Calendar miesiac = Calendar.getInstance();

	/**
	 * Liczba miesi�cy z rz�du, w kt�ych przych�d systemu by� ujemny
	 */
	private int koniec = 0;

	/**
	 * System, na kt�rym przeprowadzana jest symulacja
	 */
	private SystemAplikacji system;
	/**
	 * Okno g��wne aplikacji
	 */
	private OknoGlowne okno;

	/**
	 * Konstruktor
	 * 
	 * @param system - system, na kt�rym jest symulacja
	 * @param okno   - okno g��wne aplikacji
	 */
	public Czas(SystemAplikacji system, OknoGlowne okno) {
		this.system = system;
		this.okno = okno;
	}

	/*
	 * Dla klasy Czas w�tek wykonywany to liczenie czasu symulacji - 1s = 1 dzie�. W
	 * tej skali cotygodniowo jest sprawdzana ogl�dalno�� produk�w, co miesi�c s�
	 * p�acone pieni�dze dystrybutorom, pobierane pieni�dze od klient�w i sprawdzany
	 * jest doch�d, je�li przez 3 miesi�ce z rz�du przych�d jest ujemny to symulacja
	 * si� ko�czy.
	 * 
	 * 
	 */
	public void run() {
		// Calendar czas = Calendar.getInstance();
		try {
			while (!interrupted()) {
				Thread.sleep((long) 1000); // 1 sekunda jeden dzien
				System.out.println(czas.getTime());
				czas.setTimeInMillis(czas.getTimeInMillis() + 3600 * 24 * 1000);
				long roznica = czas.getTimeInMillis() - miesiac.getTimeInMillis();
				long roznica2 = czas.getTimeInMillis() - tydzien.getTimeInMillis();

				if (TimeUnit.DAYS.convert(roznica2, TimeUnit.MILLISECONDS) >= 7) {
					synchronized (Uzytkownik.class) {
						system.aktualizujOgladalnosc();
					}

					tydzien.setTimeInMillis(czas.getTimeInMillis());
				}
				if (TimeUnit.DAYS.convert(roznica, TimeUnit.MILLISECONDS) >= 30) {
					synchronized (Uzytkownik.class) {
						system.pobierzAbonament();
						system.zaplac();
						if (system.getPrzychod() < 0) {
							koniec++;
						} else
							koniec = 0;
						if (koniec == 3) {
							interrupt(); // jakis napis albo info i zakonczenie wszystkich watkow
							okno.setKoniec(1);
							for (Dystrybutor d : system.getDystrybutorzy()) {
								d.interrupt();
							}
							for (Klient k : system.getKlienci())
								k.interrupt();

						}
						system.negocjujUmowy();
						miesiac.setTimeInMillis(czas.getTimeInMillis());
						okno.setLblPrzy();
						system.setPrzychod(0.0);
					}
				}

			}
		} catch (InterruptedException e) {
			System.out.println("Czas si� zatrzyma� - koniec �wiata.");
		}
	}

	public synchronized Calendar getCzas() {
		return czas;
	}

	public synchronized void setCzas(Calendar czas) {
		this.czas = czas;
	}

	public int getKoniec() {
		return koniec;
	}

	public void setKoniec(int koniec) {
		this.koniec = koniec;
	}

	public SystemAplikacji getSystem() {
		return system;
	}

	public void setSystem(SystemAplikacji system) {
		this.system = system;
	}
}
