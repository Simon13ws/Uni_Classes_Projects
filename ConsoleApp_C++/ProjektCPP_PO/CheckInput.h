#pragma once
#include <iostream>
using namespace std;
class CheckInput { //Klasa obslugujaca wyjatki (zle wprowadzone dane)
public:
	void CheckIfInt(int &);
	void Check_Watched_Status(int &);
	void CheckIfTime(int&, int, int, int);
};