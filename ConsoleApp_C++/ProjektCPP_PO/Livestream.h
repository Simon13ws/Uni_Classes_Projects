#pragma once
#include "Video.h"
#include <time.h>

class Livestream: public Video {
	struct tm date;
public:
	Livestream(); //Konstruktor, w ktorym uzytkownik jest pytany po kolei o dane do kazdego pola w obiekcie (poza statusem watched, ktory mozna zmienic w edycji)
	Livestream(char); //Do tworzenia pustego obiektu
	void save(ofstream&);
	void load(ifstream&);
	void showInfo(); //Wyswietlanie informacji o livestream'ie
	void edit();
	long calculateTime(); //Do obliczenia czasu do livestream'a
	string type();
};
