import java.util.Random;

import java.util.Set;
import java.util.HashSet;
import java.math.BigDecimal;


/** Klasa do tworzenia losowych nazw, wybierania losowych wartoœci do pól
 * @author Szymon
 *
 */
public class LosowaWartosc {
	private String litery = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String cyfry = "0123456789";
	private Random rand = new Random();
	private String[] kraje = { "Polska", "Niemcy", "Francja", "Hiszpania", "W³ochy", "Rosja", "Stany Zjednoczone",
			"Kanada", "Nowa Zelandia", "Australia", "Brazylia", "Indie", "Meksyk", "Chiny", "Japonia", "Egipt",
			"Argentyna", "RPA" };

	Set<String> nazwy = new HashSet<>();
	Set<String> numery = new HashSet<>();

	/** Tworzy losow¹ nazwê z liter
	 * @return - losowa nazwa
	 */
	public String losowaNazwa() {
		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = rand.nextInt(3) + 9;
			for (int i = 0; i < length; i++)
				builder.append(litery.charAt(rand.nextInt(litery.length())));
			if (nazwy.contains(builder.toString()))
				builder = new StringBuilder();
		}
		return builder.toString();
	}

	public BigDecimal losowyNumer(int n) {
		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = n;
			for (int i = 0; i < length; i++)
				builder.append(cyfry.charAt(rand.nextInt(cyfry.length())));
			if (numery.contains(builder.toString()))
				builder = new StringBuilder();
		}
		BigDecimal numer = new BigDecimal(builder.toString());
		return numer;
	}

	public String[] getKraje() {
		return kraje;
	}

	public void setKraje(String[] kraje) {
		this.kraje = kraje;
	}
}
