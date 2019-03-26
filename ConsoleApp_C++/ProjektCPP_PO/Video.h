#pragma once
#include <iostream>
#include <fstream>
using namespace std;

//KLASA ABSTRAKCYJNA
//KLASY DZIEDZICZACE - SERIE, MOVIE, LIVESTREAM

class Video {
protected:
	string title;
	string genre;
	int ageRestriction=0;
public:
	int watched = 0; // 0 - w ogóle nie ogl¹dany, 1 - aktualnie ogl¹dany, 2 - obejrzany
	virtual void showInfo() = 0;
	virtual string type() = 0; //DO OKRESLENIA JAKI TO OBIEKT W KLASIE GENERYCZNEJ "POOL"
	virtual void save(ofstream&) = 0;
	virtual void load(ifstream&) = 0;
	virtual void edit() = 0;
};