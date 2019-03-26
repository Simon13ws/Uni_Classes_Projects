#pragma once
#include <iostream>
#include <vector>
#include "Pool.h"
using namespace std;

class Console {
public:
	//Metody wyswietlajace poszczegolne menu i odpowiadajace metody wykonania wyswietlonych opcji 

	void showMenu(Pool<Serie>&, Pool<Movie>&, Pool<Livestream>&);
	void showMenuS(Pool<Serie>&, Pool<Movie>&, Pool<Livestream>&);
	void showMenuM(Pool<Serie>&, Pool<Movie>&, Pool<Livestream>&);
	void showMenuL(Pool<Serie>&, Pool<Movie>&, Pool<Livestream>&);
	void chooseTask(Pool<Serie>&, Pool<Movie>&, Pool<Livestream>&);
	void chooseTaskS(Pool<Serie>&, Pool<Movie>&, Pool<Livestream>&);
	void chooseTaskM(Pool<Serie>&, Pool<Movie>&, Pool<Livestream>&);
	void chooseTaskL(Pool<Serie>&, Pool<Movie>&, Pool<Livestream>&);
	Console();
};
