
/**
 * Klasa nadrzêdna dla klasy Klient oraz Dystrybutor
 * @author Szymon
 *@see Klient
 *@see Dystrybutor
 */
public class Uzytkownik extends Obiekt{
	
	/**
	 * Email u¿ytkownika
	 */
	protected String email;
	/**
	 * System, w którym u¿ytkownik siê znajduje
	 */
	protected SystemAplikacji system;
	
	public Uzytkownik() {
		
	}


	/**
	 * Konstruktor przypisuj¹cy system i daj¹cy losowe wartoœci pozosta³ym polom
	 * @param system  - system, z którego korzysta u¿ytkownik(klient/dystrybutor)
	 */
	public Uzytkownik(SystemAplikacji system){
		this.system = system;
		email = nazwa + "@mail.com";
		//System.out.println(nazwa + " " + email);
	}
	
	public void run() {
		
	}
	
	
	// Gettery i Settery
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SystemAplikacji getSystem() {
		return system;
	}

	public void setSystem(SystemAplikacji system) {
		this.system = system;
	}
}
