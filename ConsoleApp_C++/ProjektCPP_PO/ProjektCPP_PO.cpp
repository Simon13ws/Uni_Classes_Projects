#pragma once
#include "pch.h"
#include <iostream>

using namespace std;

int main()
{
	Console c1;
	
	//OBIEKTY KLASY GENERYCZNEJ DLA KAZDEGO Z RODZAJOW TRESCI
	Pool<Serie> poolS;
	Pool<Movie> poolM;
	Pool<Livestream> poolL;
		
	//WCZYTYWANIE PULI Z PLIKU
	poolS.loadPool(poolS);
	poolM.loadPool(poolM);
	poolL.loadPool(poolL);
	
	c1.showMenu(poolS, poolM, poolL);
	
	return 0;
}
