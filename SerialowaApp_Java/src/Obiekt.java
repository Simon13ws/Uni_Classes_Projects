
/**
 * Nadrz�dna klasa dla u�ykownik�w i produkt�w systemu, aby przy wybieraniu
 * obiektu z listy (podczas symulacji) mo�na by�o sprawdzi�, kt�ry z obiekt�w
 * zosta� wybrany
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

	/* Zwraca nazw� klasy
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
