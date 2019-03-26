#pragma once
#include "Video.h"
class Serie : public Video {
	int episodes=0;
	int seasons=0;
public:
	int IMDB_rank = 0;
	Serie(); //Konstruktor, w ktorym uzytkownik jest pytany po kolei o dane do kazdego pola w obiekcie (poza statusem watched, ktory mozna zmienic w edycji)
	Serie(char); //Do tworzenia pustego obiektu
	void save(ofstream&);
	void load(ifstream&);
	void showInfo(); //Wyswietlanie informacji o serialu
	void edit();
	string type();
	
};
