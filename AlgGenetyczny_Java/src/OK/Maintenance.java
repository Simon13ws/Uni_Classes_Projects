package OK;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

//obiekt tej klasy ma listê maintenance'ow


public class Maintenance {
	public int tRozp = 0;
	public int tTrwa = 0;
	public int tKoniec = 0;
	public ArrayList<Maintenance> przerwy;

	public Maintenance() {

	}

	public Maintenance(int t1, int t2, int nic) {
		this.tRozp = t1;
		this.tTrwa = t2;
		this.tKoniec = t1 + t2;
	}

	public Maintenance(int m, int maks) {
		przerwy = new ArrayList<Maintenance>();
		boolean check = true;
		Random rand = new Random();
		int los1, los2;
		//System.out.println("Czas trwania wszystkich zadan na maszynie 1: " + maks);
		for (int i = 0; i < m; i++) {
			do {
				los1 = rand.nextInt(maks);
				los2 = rand.nextInt(15) + 5;
				for (Maintenance przerwa : przerwy) {
					if (((los1 >= przerwa.tRozp-5) && (los1 <= (przerwa.tKoniec+1)) // minimalny czas pomiedzy maintenancami to 1 jednostek
																				// MAINTENENCAMI
							|| ((los1 + los2 >= przerwa.tRozp-1) && (los1 + los2 <= (przerwa.tKoniec+1))
									|| ((los1 < przerwa.tRozp-1) && (los1 + los2 > przerwa.tKoniec+1))))) {
						check = false;
						break;
					} else
						check = true;
				}
			} while (!check);
			Maintenance nowa = new Maintenance(los1, los2, 0);
			przerwy.add(nowa);
			//System.out.println("Przerwa nr " + (i + 1) + " Pocz¹tek: " + nowa.tRozp + " Koniec: " + nowa.tKoniec);
		}
		Collections.sort(przerwy, new Comparator<Maintenance>() { // sortowanie listy maintenanców po czasie rozpoczêcia
			public int compare(Maintenance a, Maintenance b) {
				return a.tRozp - b.tRozp;
			}
		});

	}

}
