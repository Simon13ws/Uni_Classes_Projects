
/**
 * Nadrzêdna klasa dla u¿ykowników i produktów systemu, aby przy wybieraniu
 * obiektu z listy (podczas symulacji) mo¿na by³o sprawdziæ, który z obiektów
 * zosta³ wybrany
 * 
 * @author Szymon
 *
 */
public class Obiekt extends Thread {
	/**
	 * Nazwa danego obiektu
	 */
	protected String nazwa;
	
	public Obiekt() {
		LosowaWartosc losowa = new LosowaWartosc();
		nazwa = losowa.losowaNazwa();
	}

	/* Zwraca nazwê klasy
	 * 	 */
	public String toString() {
		return "Obiekt";	
	}
	
	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
}
