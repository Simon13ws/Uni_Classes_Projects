#pragma once
#include <iostream>
#include "Video.h"
using namespace std;

class Movie: public Video{

	
public:
	int IMDB_rank = 0;
	Movie(); //Konstruktor, w ktorym uzytkownik jest pytany po kolei o dane do kazdego pola w obiekcie (poza statusem watched, ktory mozna zmienic w edycji)
	Movie(char); //Do tworzenia pustego obiektu
	void showInfo(); //Wyswietlanie informacji o filmie
	void save(ofstream&);
	void load(ifstream&);
	void edit();
	string type();
};
