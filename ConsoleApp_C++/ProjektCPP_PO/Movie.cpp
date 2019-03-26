#pragma once
#include "pch.h"
#include "Movie.h"
#include <iostream>
#include <string>
#include "CheckInput.h"
using namespace std;

Movie::Movie()
{
	CheckInput check; //OBIEKT DO SPRAWDZANIA POPRAWNOSCI WPROWADZONYCH DANYCH DO ZMIENNYCH TYPU INT
	 
	cout << "Title: ";
	cin.ignore();
	getline(cin, title);
	cout << "Genre: ";
	getline(cin, genre);
	cout << "Age restriction: ";
	check.CheckIfInt(ageRestriction);
	cout << "IMDB_rank: ";
	check.CheckIfInt(IMDB_rank);
	
}
Movie::Movie(char s) {} //DLA PUSTEGO OBIEKTU
void Movie::edit()
{
	int choice = 0;
	CheckInput check;
	while (choice != 6)
	{
		cout << "Choose what you want to edit: " << endl;
		cout << "1. Title - " << title << endl;
		cout << "2. Genre - " << genre << endl;
		cout << "3. Age restriction - " << ageRestriction << endl;
		cout << "4. IMDB_rank - " << IMDB_rank << endl;
		cout << "5. Watched status - " << watched << endl;
		cout << "6. Exit edition" << endl;

		cin >> choice;
		switch (choice)
		{
		case 1:
			cout << "New title: ";
			cin.ignore();
			getline(cin, title);
			break;
		case 2:
			cout << "New genre: ";
			cin.ignore();
			getline(cin, genre);
			break;
		case 3:
			cout << "New age restriction: ";
			check.CheckIfInt(ageRestriction);
			break;
		case 4:
			cout << "New IMDB_rank: ";
			check.CheckIfInt(IMDB_rank);
			break;
		case 5: cout << "New watched status (0 - not watched, 1 - is currently watched, 2 - has been watched): ";
			check.Check_Watched_Status(watched);
			break;
		case 6: break;
		default: cout << "You gave wrong number!" << endl;
		}
	}

}
void Movie::showInfo() 
{
	cout << "\"" << title << "\" " << "Age restriction: " << ageRestriction << " Genre: " << genre << " IMDB rank: " << IMDB_rank;
	if (watched == 1) cout << "  CURRENTLY WATCHED" << endl;
	else if (watched == 2) cout << "  WATCHED" << endl;
	else cout << endl;
}
void Movie::save(ofstream &save)
{
	save << title << endl << genre << endl << IMDB_rank << " " << ageRestriction << " " << watched <<  endl;
}
void Movie::load(ifstream &load)
{
	string trash;
	getline(load, trash);
	getline(load, title);
	getline(load, genre);
	load >> IMDB_rank >> ageRestriction >> watched;
}
string Movie::type()
{
	return "Movie";
}