package OK;

import java.util.ArrayList;
import java.util.Random;

public class AlgGenetyczny {

	public Generator gen;

	public ArrayList<Rozwiazanie> populacja;

	public AlgGenetyczny() {
		populacja = new ArrayList<>();
	}

	//Tworzenie pocz¹tkowej populacji - 100 losowych rozwi¹zañ
	public void stworzPoczatkowaPopulacje(Generator gen) {
		this.gen = gen;
		while (populacja.size() < 100) {
			GeneratorLosowy genlos = new GeneratorLosowy(gen, gen.przerwy.przerwy);
			genlos.uzupelnijM2(gen);
			Rozwiazanie nowe = new Rozwiazanie(gen.maszyna1, gen.maszyna2);
			nowe.rodzic1 = null;
			nowe.rodzic2 = null;
			nowe.czas1 = genlos.czasM1;
			nowe.czas2 = genlos.czasM2;
			populacja.add(nowe);
		}
	}

	// Mutacja dla pojedynczego osobnika - pojedyncza mutacja polega na zamianie miejscami 2 operacji na maszynie 1 i dostosowaniu maszyny 2
	public void mutuj(Rozwiazanie osobnik) {
		Random rand = new Random();
		int p = 45;	 //p = prawdopodobienstwo mutacji
		if (rand.nextInt(100) + 1 <= p) {
			int los1, los2;
			los1 = rand.nextInt(osobnik.maszyna1.size());
			do {
				los2 = rand.nextInt(osobnik.maszyna1.size());
			} while (los1 == los2);
			Zadanie temp = osobnik.maszyna1.get(los1);
			osobnik.maszyna1.set(los1, osobnik.maszyna1.get(los2));
			osobnik.maszyna1.set(los2, temp);
			ustawCzas1(osobnik);
			uzupelnijM2(osobnik);
			ustawCzas2(osobnik);
			mutuj(osobnik);
		}
	}
	//Uszeregowanie maszyny 2 po mutacji - szeregowanie w zale¿noœci od najszybciej dostêpnych operacji
	public void uzupelnijM2(Rozwiazanie osobnik) {
		ArrayList<Zadanie> zadania2 = new ArrayList<Zadanie>();
		for (Zadanie zad : osobnik.maszyna1)
			zad.wykonane = false;
		osobnik.maszyna2.clear();
		int i = 0, start2 = 0, los2 = 0;
		Random rand = new Random();
		while (i < gen.n) {
			for (Zadanie zad : osobnik.maszyna1) {
				if (!(zad.wykonane) && zad.tKoniecOp1 <= start2) {
					zad.wykonane = true;
					zadania2.add(zad);
				}
			}
			int roznica = 100;
			if (zadania2.isEmpty()) {
				for (Zadanie zad : osobnik.maszyna1) {
					if (!(zad.wykonane) && zad.tKoniecOp1 - start2 < roznica) {
						roznica = zad.tKoniecOp1 - start2;
					}
				}
				start2 += roznica;
				continue;
			}

			los2 = rand.nextInt(zadania2.size());
			zadania2.get(los2).stOp2 = start2;
			osobnik.maszyna2.add(zadania2.get(los2));
			start2 += zadania2.get(los2).tOp2;
			zadania2.remove(los2);
			zadania2.trimToSize();
			i++;
		}
	}

	public void krzyzuj(Generator gen) {
		this.gen = gen;
		Random rand = new Random();
		int los1 = rand.nextInt(populacja.size());
		int los2;
		do {
			los2 = rand.nextInt(populacja.size());
		} while (los2 == los1);
		ArrayList<Zadanie> m1 = new ArrayList<Zadanie>();
		ArrayList<Zadanie> m2 = new ArrayList<Zadanie>();
		Rozwiazanie r1 = populacja.get(los1);
		Rozwiazanie r2 = populacja.get(los2);

		Rozwiazanie nowy = new Rozwiazanie(r1, r2);
		// KOPIOWANIE 1 i 3 czêœci z rodzica1 na potomka, reszta elementów w kolejnoœci
		// takiej jak wzglêdem siebie u rodzica2
		for (int i = 0; i < Math.ceil(r1.maszyna1.size() / 3); i++)
			m1.add(r1.maszyna1.get(i));
		for (int i = 0; i < r2.maszyna1.size(); i++) {
			for (int j = (int) Math.ceil(r1.maszyna1.size() / 3); j < Math.ceil(2 * (r1.maszyna1.size() / 3)); j++) {
				if (r1.maszyna1.get(j).equals(r2.maszyna1.get(i))) {
					m1.add(r1.maszyna1.get(j));
					break;
				}
			}
		}
		for (int i = (int) Math.ceil(2 * (r1.maszyna1.size() / 3)); i < r1.maszyna1.size(); i++)
			m1.add(r1.maszyna1.get(i));

		for (int i = 0; i < Math.ceil(r1.maszyna2.size() / 3); i++)
			m2.add(r1.maszyna2.get(i));
		for (int i = 0; i < r2.maszyna2.size(); i++) {
			for (int j = (int) Math.ceil(r1.maszyna2.size() / 3); j < Math.ceil(2 * (r1.maszyna2.size() / 3)); j++) {
				if (r1.maszyna2.get(j).equals(r2.maszyna2.get(i))) {
					m2.add(r1.maszyna2.get(j));
					break;
				}
			}
		}
		for (int i = (int) Math.ceil(2 * (r1.maszyna2.size() / 3)); i < r1.maszyna2.size(); i++)
			m2.add(r1.maszyna2.get(i));

		nowy.maszyna1 = m1;
		nowy.maszyna2 = m2;

		ustawCzas1(nowy);
		ustawCzas2(nowy);

		populacja.add(nowy);
		// System.out.println("Czas pracy rodzica 1: " + "m1 - " + nowy.rodzic1.czas1 +
		// " m2 - " + nowy.rodzic1.czas2);
		// System.out.println("Czas pracy rodzica 2: " + "m1 - " + nowy.rodzic2.czas1 +
		// " m2 - " + nowy.rodzic2.czas2);
		//System.out.println("Czas pracy u potmoka: " + "m1 - " + nowy.czas1 + " m2 - " + nowy.czas2);
		/*
		 * for (Zadanie zad : nowy.maszyna1) System.out.print(zad.nrZad + " ");
		 * System.out.println(); for (Zadanie zad : nowy.maszyna2)
		 * System.out.print(zad.nrZad + " "); System.out.println(); for (Zadanie zad :
		 * nowy.rodzic1.maszyna1) System.out.print(zad.nrZad + " ");
		 * System.out.println(); for (Zadanie zad : nowy.rodzic1.maszyna2)
		 * System.out.print(zad.nrZad + " "); System.out.println(); for (Zadanie zad :
		 * nowy.rodzic2.maszyna1) System.out.print(zad.nrZad + " ");
		 * System.out.println(); for (Zadanie zad : nowy.rodzic2.maszyna2)
		 * System.out.print(zad.nrZad + " "); System.out.println();
		 */
	}

	//Liczenie czasu wykonania uszeregowania na maszynie 1 dla osobnika
	public void ustawCzas1(Rozwiazanie rozw) {

		int start = 0;
		int m = 0;
		int skracanie = 0;
		int i = 0;
		while (i < gen.n) {
			if (m != gen.m
					&& gen.przerwy.przerwy.get(m).tRozp < (int) Math.ceil(rozw.maszyna1.get(i).tOp1 * (1 - 0.01 * skracanie)) + start) {
				// jesli operacja nie zmiesci sie przed maintenancem to jest dodawana po nim -
				// przesuniecie czasu startu za maintenance
				start = gen.przerwy.przerwy.get(m).tRozp + gen.przerwy.przerwy.get(m).tTrwa;
				m++;
				skracanie = 0;
				continue;
			}

			rozw.maszyna1.get(i).stOp1 = start;
			rozw.maszyna1.get(i).ftOp1 = (int) Math.ceil(rozw.maszyna1.get(i).tOp1 * (1 - 0.01 * skracanie));
			start += rozw.maszyna1.get(i).ftOp1;
			rozw.maszyna1.get(i).tKoniecOp1 = start;
			if (skracanie < 25)
				skracanie += 5;
			i++;
		}
		rozw.czas1 = start;
	}

	//Liczenie czasu wykonania uszeregowania na maszynie 2 dla osobnika
	public void ustawCzas2(Rozwiazanie rozw) {

		int start = 0;
		start = 0;
		for (Zadanie zad : rozw.maszyna2) {
			if (zad.tKoniecOp1 > start)
				start += zad.tKoniecOp1 - start;

			zad.stOp2 = start;
			start += zad.tOp2;

		}
		rozw.czas2 = start;
	}

	//Selekcja turniejowa - z aktualnej populacji (n) zostawia po³owê osobników
	public void selekcjonuj(int n) {
		Random rand = new Random();
		ArrayList<Rozwiazanie> populacja2 = new ArrayList<Rozwiazanie>(this.populacja);

		while (populacja.size() > n / 2) {
			int los1 = rand.nextInt(populacja2.size());
			int los2;
			do {
				los2 = rand.nextInt(populacja2.size());
			} while (los2 == los1);
			if (populacja2.get(los1).czas2 < populacja2.get(los2).czas2)
				populacja.remove(populacja2.get(los2));
			else
				populacja.remove(populacja2.get(los1));
			populacja2.remove(populacja2.get(los1));
			if (los2 > los1)
				los2--; // jesli usuniete rozwiazanie (los1) by³o wczeseniej na liscie
			populacja2.remove(populacja2.get(los2));
			populacja2.trimToSize();
			populacja.trimToSize();
			// System.out.println(populacja.size());
		}

	}
}
