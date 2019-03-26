
/**Klasa, z której rozpoczynamy aplikacjê
 * @author Szymon
 *
 */
public class PanelKontrolny {

	/**
	 * System, na którym bazuje
	 */
	private SystemAplikacji system;
	
	/**
	 * Konstruktor, rozpoczyna pracê systemu
	 */
	public PanelKontrolny() {
		system = new SystemAplikacji();
	}
	
	/**
	 * Dodanie dystrybutora do systemu
	 */
	public void dodajDystrybutora(){
		Dystrybutor dys = new Dystrybutor(system);
		system.getDystrybutorzy().add(dys);
		if(!dys.isAlive())
			dys.start();
	}
	
	public synchronized SystemAplikacji getSystem() {
		return system;
	}
	public void setSystem(SystemAplikacji system) {
		this.system = system;
	}
	
	public static void main(String[] args)
	{
		PanelKontrolny panel = new PanelKontrolny();	
		try {
			OknoApki1 window = new OknoApki1();
			window.setPanel(panel);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*while(true)
		{	int wybor = 0;
			System.out.println("Nacisnij 1 by dodac dystrybutora.");
			Scanner input = new Scanner(System.in);
			wybor = input.nextInt();
			if (wybor == 1) 
				panel.dodajDystrybutora();
			
		}*/
	}
	
	
	
}
