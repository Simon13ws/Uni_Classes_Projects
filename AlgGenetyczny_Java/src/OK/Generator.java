package OK;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Generator {

	public int n = 50 ;
	public int m=0; // liczba maintenance'ów
	public float procentMaint = 0.2f;
	public ArrayList<Zadanie> zadania;
	public Maintenance przerwy;
	public ArrayList<Zadanie> maszyna1;
	public ArrayList<Zadanie> maszyna2;

	
	
	// Generuje zadania oraz maintenance'y z czasami rozpoczêcia (maintenance'y) i czasami trwania
	public void stworzInstancje() { 
		this.m = (int) Math.ceil(n * procentMaint); // m = 20% z n
		//this.n = ile;
		//this.m = 10;
		zadania = new ArrayList<Zadanie>();
		int czasOp1 = 0, czasOp2 = 0;
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			Zadanie zad = new Zadanie();
			Zadanie.liczbaZad++;
			zad.nrZad = Zadanie.liczbaZad;
			zad.tOp1 = rand.nextInt(15) + 5;
			zad.tOp2 = rand.nextInt(15) + 5;
			czasOp1 += zad.tOp1;
			czasOp2 += zad.tOp2;
			zadania.add(zad);
			//System.out.println(zad.nrZad + ". " + zad.tOp1 + " " + zad.tOp2);
		}
		//System.out.println("Suma czasow: op1: " + czasOp1 + " op2: " + czasOp2);
		przerwy = new Maintenance(m, czasOp1);
	}
		
	//Zapisanie stworzonej instancji
	public void zapiszInstancje(int nrInst) throws IOException
	{
				BufferedWriter zapis = null;

				try {
					zapis = new BufferedWriter(new FileWriter("Inst_" + nrInst + "_WE"));
					zapis.write("****"+nrInst+"****");
					zapis.newLine();
					zapis.write(String.valueOf(n));
					zapis.newLine();
					for(Zadanie zad: zadania) {
						zapis.write(zad.tOp1 + "; " + zad.tOp2 + "; 1; 2;");
						zapis.newLine();
					}
					int m=1;
					for(Maintenance maint: przerwy.przerwy) {
						zapis.write(m + "; 1; " + maint.tTrwa + "; " + maint.tRozp);
						zapis.newLine();
						m++;
					}
					zapis.write("****EOF****");
				} finally {
				    if (zapis != null) {
				    	zapis.close();
				    }
				}
	}
	//Wczytanie stworzonej instancji
	public void wczytajInstancje(int nrInst) throws IOException
	{
		BufferedReader odczyt = null;
		try {
			odczyt = new BufferedReader(new FileReader("Inst_" + nrInst + "_WE_"));
			odczyt.readLine();
			Scanner input = new Scanner(odczyt);
			n = input.nextInt();
			String linia = input.nextLine();
			zadania = new ArrayList<Zadanie>();
			for(int i=0; i<n; i++)
			{
				Zadanie zad = new Zadanie();
				zad.nrZad = i+1;
				linia = input.nextLine();
				linia = linia.replaceAll(";","");
				Scanner input2 = new Scanner(linia);
				zad.tOp1 = input2.nextInt();
				zad.tOp2 = input2.nextInt();
				//System.out.println(zad.nrZad+". " + zad.tOp1 + " " + zad.tOp2);
				zadania.add(zad);
				input2.close();
			}
			przerwy = new Maintenance();
			przerwy.przerwy = new ArrayList<Maintenance>();
			int nic;
			m = (int) Math.ceil(n * procentMaint);
			//this.m = 10;
			for(int i = 0; i<m; i++)
			{
				Maintenance maint = new Maintenance();
				linia = input.nextLine();
				linia = linia.replaceAll(";","");
				Scanner input2 = new Scanner(linia);
				nic = input2.nextInt();
				nic = input2.nextInt();
				maint.tTrwa = input2.nextInt();
				maint.tRozp = input2.nextInt();
				przerwy.przerwy.add(maint);
				//System.out.println("Przerwa nr" + (i+1) + "Czas rozp: "  + maint.tRozp + " Czas trwania: " + maint.tTrwa);
				input2.close();
			}
			input.close();
			
			
		} finally {
		    if (odczyt != null) {
		    	odczyt.close();
		    }
		}
	}

	
}
