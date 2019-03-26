import java.util.Random;

/**
 * @author Szymon
 * @see Klient
 */
public class Abonament {

	private enum Wersja {
		BRAK, BASIC, FAMILY, PREMIUM;
	}

	/**
	 * Wersja abonamentu
	 */
	private Wersja rodzaj;
	/**
	 * Liczba urz¹dzeñ, na których mo¿na ogl¹daæ z jednego konta
	 */
	private int liczbaUrzadzen;
	/**
	 * Maksymalna dostêpna rozdzielczoœæ
	 */
	private String maksRozdz;
	/**
	 * Cena abonamentu
	 */
	private Double cena;
	/**
	 * S³owna nazwa rodzaju abonamentu
	 */
	private String jaki;

	/**
	 * Cena miesiêczna abonamentu w wersji Basic
	 */
	private static Double basic = 30.0;

	/**
	 * Cena miesiêczna abonamentu w wersji Family
	 */
	private static Double family = 80.0;

	/**
	 * Cena miesiêczna abonamentu w wersji Premium
	 */
	private static Double premium = 50.0;

	/**
	 * Konstruktor losowo wybieraj¹cy wersje abonamentu i dobieraj¹cy odpowiednie
	 * wartoœci do pó³ klasy
	 */
	public Abonament() {

		Random rand = new Random();
		int los = rand.nextInt(10);
		if (los < 2) {
			rodzaj = Wersja.BRAK;
			jaki = "Brak";
			liczbaUrzadzen = 1;
			maksRozdz = "4K (3840x2160)";
			cena = 0.0;
		} else if (los < 6) {
			rodzaj = Wersja.BASIC;
			jaki = "Basic";
			liczbaUrzadzen = 2;
			maksRozdz = "HD 720p (1280x720)";
			cena = basic;
		} else if (los < 8) {
			rodzaj = Wersja.FAMILY;
			jaki = "Family";
			liczbaUrzadzen = 6;
			maksRozdz = "FHD 1080p (1920x1080)";
			cena = family;
		} else {
			rodzaj = Wersja.PREMIUM;
			liczbaUrzadzen = 2;
			jaki = "Premium";
			maksRozdz = "4K (3840x2160)";
			cena = premium;
		}
	}

	// Gettery i Settery
	public Wersja getRodzaj() {
		return rodzaj;
	}

	public void setRodzaj(Wersja rodzaj) {
		this.rodzaj = rodzaj;
	}

	public int getLiczbaUrzadzen() {
		return liczbaUrzadzen;
	}

	public void setLiczbaUrzadzen(int liczbaUrzadzen) {
		this.liczbaUrzadzen = liczbaUrzadzen;
	}

	public String getMaksRozdz() {
		return maksRozdz;
	}

	public void setMaksRozdz(String maksRozdz) {
		this.maksRozdz = maksRozdz;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public String getJaki() {
		return jaki;
	}

	public void setJaki(String jaki) {
		this.jaki = jaki;
	}

	public static Double getBasic() {
		return basic;
	}

	public static void setBasic(Double basic) {
		Abonament.basic = basic;
	}

	public static Double getFamily() {
		return family;
	}

	public static void setFamily(Double family) {
		Abonament.family = family;
	}

	public static Double getPremium() {
		return premium;
	}

	public static void setPremium(Double premium) {
		Abonament.premium = premium;
	}

}
