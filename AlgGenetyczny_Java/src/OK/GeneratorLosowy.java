package OK;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

public class GeneratorLosowy {
	private Generator gen;
	public int czasM1 = 0; // Koniec wszystkich operacji na maszynie1
	public int czasM2 = 0; // Koniec wszystkich operacji na maszynie2
	public ArrayList<Zadanie> zadania1;
	public ArrayList<Zadanie> zadania2;
	Random rand = new Random();

	// Losowo uk³ada zadania uwzglêdniaj¹c maintenance'y
	public GeneratorLosowy(Generator generator, ArrayList<Maintenance> maint) {
		this.gen = generator;
		gen.maszyna1 = new ArrayList<>();
		czasM1 = 0;
		for (Zadanie zad : gen.zadania)
			zad.wykonane = false;
		zadania1 = new ArrayList<Zadanie>(gen.zadania);

		int start1 = 0;
		int i = 0;
		int m = 0;
		int skracanie = 0;
		int los = 0;
		while (i < gen.n) {
			los = rand.nextInt(zadania1.size());

			// Jeœli operacja nie zmieœci siê przed maintenancem to wstawiana jest za nim
			if (m != gen.m
					&& maint.get(m).tRozp < (int) Math.ceil(zadania1.get(los).tOp1 * (1 - 0.01 * skracanie)) + start1) {
				start1 = maint.get(m).tRozp + maint.get(m).tTrwa;
				m++;
				skracanie = 0;
				continue;
			}

			
			//Ustawianie parametrów zadania (czasów) i dodanie go na maszynê 1
			zadania1.get(los).stOp1 = start1;
			zadania1.get(los).ftOp1 = (int) Math.ceil(zadania1.get(los).tOp1 * (1 - 0.01 * skracanie));
			start1 += zadania1.get(los).ftOp1;
			zadania1.get(los).tKoniecOp1 = start1;
			gen.maszyna1.add(zadania1.get(los));
			zadania1.remove(los);
			zadania1.trimToSize();
			i++;
			if (skracanie < 25)
				skracanie += 5;
		}
		i = 0;

		czasM1 = start1;
		for (Zadanie zad : gen.zadania)
			zad.wykonane = false;
		/*
		 * System.out.println("Czas trwania wszystkich operacji: M1: " + czasM1 +
		 * " M2: " + czasM2); for (int k = 0; k < i; k++) {
		 * System.out.println(maszyna1.get(k).nrZad + ". Pocz¹tek op1: " +
		 * maszyna1.get(k).stOp1 + " Koniec: " + maszyna1.get(k).tKoniecOp1); } for (int
		 * k = 0; k < i; k++) { System.out.println(maszyna2.get(k).nrZad +
		 * ". Pocz¹tek op2: " + maszyna2.get(k).stOp2 + " Koniec: " +
		 * (maszyna2.get(k).stOp2 + maszyna2.get(k).tOp2)); }
		 */
	}

	public void uzupelnijM2(Generator gen) {
		this.gen = gen;
		gen.maszyna2 = new ArrayList<>();
		// zadania2 = new ArrayList<Zadanie>(gen.zadania);
		zadania2 = new ArrayList<Zadanie>();
		int i = 0, start2 = 0, los2 = 0;
		while (i < gen.n) {

			// Sprawdza czy dla aktualnego czasu maszyny 2 s¹ operacje, które ju¿ wykona³y
			// siê na maszynie 1
			for (Zadanie zad : gen.zadania) {
				if (!(zad.wykonane) && zad.tKoniecOp1 <= start2) {
					zadania2.add(zad);
					zad.wykonane = true;
				}
			}
			int roznica = 100;
			if (zadania2.isEmpty()) { // Jeœli nie ma to czeka na najszybciej dostêpn¹ operacjê 2
				for (Zadanie zad : gen.zadania) {
					if (!(zad.wykonane) && zad.tKoniecOp1 - start2 < roznica) {
						roznica = zad.tKoniecOp1 - start2;
					}
				}
				start2 += roznica;
				continue;
			}

			los2 = rand.nextInt(zadania2.size());
			// System.out.println(zadania2.get(los2).nrZad + ".");

			// if(start2 < gen.zadania.get(zadania2.get(los2).nrZad-1).tKoniecOp1)
			// start2 = gen.zadania.get(zadania2.get(los2).nrZad-1).tKoniecOp1;
			
			//Ustawianie parametrów zadania (czasów) i dodanie go na maszynê 2
			zadania2.get(los2).stOp2 = start2;
			gen.maszyna2.add(zadania2.get(los2));
			start2 += zadania2.get(los2).tOp2;
			zadania2.remove(los2);
			zadania2.trimToSize();
			i++;
		}
		czasM2 = start2;
		// System.out.println("Czas m1: " + czasM1 + " Czas m2: " + czasM2);
	}
}
