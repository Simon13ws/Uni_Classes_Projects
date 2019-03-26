package OK;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Rozwiazanie {

	public ArrayList<Zadanie> maszyna1;
	public ArrayList<Zadanie> maszyna2;
	public Rozwiazanie rodzic1;
	public Rozwiazanie rodzic2;
	public int czas1;
	public int czas2;

	public Rozwiazanie(Rozwiazanie r1, Rozwiazanie r2) {
		this.rodzic1 = r1;
		this.rodzic2 = r2;
		maszyna1 = new ArrayList<Zadanie>();
		maszyna2 = new ArrayList<Zadanie>();
	}

	public Rozwiazanie(ArrayList<Zadanie> m1, ArrayList<Zadanie> m2) {
		maszyna1 = m1;
		maszyna2 = m2;
		czas1 = m1.get(m1.size() - 1).tKoniecOp1;
		czas2 = m2.get(m2.size() - 1).stOp2 + m2.get(m2.size() - 1).tOp2;

	}

	// Zapisanie najlepszego rozwi¹zania dla podanej instancji
	public void zapiszRozwiazanie(Generator gen, int czasPoczatkowy, int nrInst) throws IOException {
		BufferedWriter zapis = null;

		try {
			zapis = new BufferedWriter(new FileWriter("Inst_" + nrInst + "_WY"));
			zapis.write("****" + nrInst + "****");
			zapis.newLine();
			zapis.write(czas2 + ", " + czasPoczatkowy);
			zapis.newLine();
			zapis.write("M1: ");
			int start = 0, m = 0, idle1 = 0, sumaM = 0, sumaIdle1 = 0;
			for (int i = 0; i < gen.n; i++) {
				Zadanie zad = maszyna1.get(i);
				while (start != zad.stOp1) {
					if (gen.przerwy.przerwy.get(m).tRozp != start) {
						zapis.write("idle" + (idle1 + 1) + "_M1, " + start + ", "
								+ (gen.przerwy.przerwy.get(m).tRozp - start) + "; ");
						sumaIdle1 += gen.przerwy.przerwy.get(m).tRozp - start;
						start = gen.przerwy.przerwy.get(m).tRozp;
						idle1++;
					}
					zapis.write("maint" + (m + 1) + "_M1, " + start + ", " + gen.przerwy.przerwy.get(m).tTrwa + "; ");
					start += gen.przerwy.przerwy.get(m).tTrwa;
					sumaM += gen.przerwy.przerwy.get(m).tTrwa;
					m++;
				}
				zapis.write("op1_" + (zad.nrZad + 1) + ", " + start + ", " + zad.tOp1 + ", " + zad.ftOp1 + "; ");
				start += zad.ftOp1;
			}

			zapis.newLine();
			zapis.write("M2: ");
			start = 0;
			int idle2 = 0;
			int sumaIdle2 = 0;
			for (int i = 0; i < gen.n; i++) {
				Zadanie zad = maszyna2.get(i);
				while (start != zad.stOp2) {
					zapis.write("idle" + (idle2 + 1) + "_M2, " + start + ", " + (maszyna2.get(i).stOp2 - start) + "; ");
					sumaIdle2 += maszyna2.get(i).stOp2 - start;
					start = maszyna2.get(i).stOp2;
					idle2++;
				}
				zapis.write("op2_" + (zad.nrZad + 1) + ", " + start + ", " + zad.tOp2 + "; ");
				start += zad.tOp2;
			}
			zapis.newLine();
			zapis.write(m + ", " + sumaM);
			zapis.newLine();
			zapis.write("0, 0");
			zapis.newLine();
			zapis.write(idle1 + ", " + sumaIdle1);
			zapis.newLine();
			zapis.write(idle2 + ", " + sumaIdle2);
			zapis.newLine();
			zapis.write("****EOF****");
		} finally {
			if (zapis != null) {
				zapis.close();
			}
		}
	}

	public static void main(String[] args) throws IOException {

		Generator generator = new Generator();
		AlgGenetyczny metaheurystyka = new AlgGenetyczny();
		
		
	/*	// Wartoœci do testów
		//int [] iteracje = {10,110,210,310,410,510,610,710,810,910,1010};
		 int [] ile = {10,20,30,40,50,60,70,80,90,140,160};
		 int [] od = {59,28,17,12,9,7,5,4,3,1,1};
		 int [] dot = {8,7,8,7,7,7,8,8,8,7,6};
		//int [] odm = {9,7,6,6,5,5,4,4,3,3,2};
		//int [] dom = {7,8,8,7,8,7,8,7,8,7,8};
		//int [] maint = {10,11,12,13,14,15,16,17,18,19,20};
		int[] srednia = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		//int[] mutacje = {0,9,18,27,36,45,54,63,72,81,90};
		//int [] populacja = {200,300,400,600,800,1200,1600,2400,3200,4800,6400};
		int x = 691;
		
		for (int j = 0; j < 11; j++) {
			for (int i = 0; i < 30; i++) {
				Generator generator = new Generator();
				 generator.wczytajInstancje(x, ile[j], od[j], dot[j]);
				 //generator.wczytajInstancje(x, maint[j], odm[j],dom[j]);
				 //generator.stworzInstancje();
				 //generator.zapiszInstancje();
				 
				 //x++;
			
			*/
		 		generator.stworzInstancje();
				AlgGenetyczny algorytm = new AlgGenetyczny();
				algorytm.stworzPoczatkowaPopulacje(generator); //tworzy pierwsze pokolenie
				int minimum = algorytm.populacja.get(0).czas2;
				for (Rozwiazanie rozw : algorytm.populacja)
					if (minimum < rozw.czas2)
						minimum = rozw.czas2;

				for (int k = 0; k < 410; k++) { //410 - liczba iteracji (liczba pokoleñ o jeden wiêksza)
					while (algorytm.populacja.size() < 2400) //2400 maksymalny rozmiar populacji
						algorytm.krzyzuj(generator);
					for (Rozwiazanie rozw : algorytm.populacja)
						algorytm.mutuj(rozw);
					while (algorytm.populacja.size() > 100) {
						algorytm.selekcjonuj(algorytm.populacja.size()); //
						// System.out.println(algorytm.populacja.size());
					}
				}

				//Wybranie najlepszego rozwi¹zania
				Rozwiazanie najlepsze = algorytm.populacja.get(0);
				for (Rozwiazanie rozw : algorytm.populacja)
					if (najlepsze.czas2 < rozw.czas2)
						najlepsze = rozw;

				algorytm.ustawCzas1(najlepsze);
				algorytm.ustawCzas2(najlepsze);
				System.out.println("Najlepsze losowe rozwi¹zanie: " + minimum + "\nNalepsze rozwi¹zanie po zastosowaniu algorytmu: " + najlepsze.czas2);
				/*
				//Zapis tego rozwi¹zania
  				najlepsze.zapiszRozwiazanie(generator, minimum, x, ile[j],od[j],dot[j]);
				srednia[j] += najlepsze.czas2;
				x++;
			}
			srednia[j] /= 30;
		}
		BufferedWriter zapis = null;
		try {
			zapis = new BufferedWriter(new FileWriter("Œrednia wyników zadañ i czasów"));
			for (int i = 0; i < 11; i++) {
				zapis.write(od[i]+"-"+(dot[i]+od[i])+ " " + ile[i] + " " + srednia[i]);
				zapis.newLine();
			}
		} finally {
			if (zapis != null) {
				zapis.close();
			}
		}*/
	}
}
