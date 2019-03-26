#include "pch.h"
#include <iostream>
#include <string>

using namespace std;

Serie::Serie()
{
	CheckInput check;
	cout << "Title: ";
	cin.ignore();
	getline(cin, title);
	cout << "Genre: ";
	getline(cin, genre);
	cout << "Age restriction: ";
	check.CheckIfInt(ageRestriction);
	cout << "IMDB_rank: ";
	check.CheckIfInt(IMDB_rank);
	cout << "Amount of seasons: ";
	check.CheckIfInt(seasons);
	cout << "Amount of episodes: ";
	check.CheckIfInt(episodes);

}
Serie::Serie(char s) {} //DLA PUSTEGO OBIEKTU
void Serie::edit()
{
	CheckInput check;
	int choice=0;
	while(choice!=8)
	{
		cout << "Choose what you want to edit: " << endl;
		cout << "1. Title - " << title << endl;
		cout << "2. Genre - " << genre << endl;
		cout << "3. Age restriction - " << ageRestriction << endl;
		cout << "4. IMDB_rank - " << IMDB_rank << endl;
		cout << "5. Amount of seasons - " << seasons << endl;
		cout << "6. Amount of episodes - " << episodes << endl;
		cout << "7. Watched status - " << watched << endl;
		cout << "8. Exit edition" << endl;
			   
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
		case 5: cout << "New amount of seasons: ";
			check.CheckIfInt(seasons);
			break;
		case 6: cout << "New amount of episodes: ";
			check.CheckIfInt(episodes);
			break;
		case 7: cout << "New watched status (0 - not watched, 1 - is currently watched, 2 - has been watched): ";
			check.Check_Watched_Status(watched);
			break;
		case 8: break;
		default: cout << "You gave wrong number!" << endl;
		}
	}

}

void Serie::showInfo()
{
	cout << "\"" << title << "\"" << " Genre: " << genre << " Age restriction: +" << ageRestriction << "  Seasons: " << seasons << " Episodes: " << episodes << " IMDB_rank: " << IMDB_rank;
	if (watched == 1) cout << "  CURRENTLY WATCHED" << endl;
	else if (watched == 2) cout << "  WATCHED" << endl;
	else cout << endl;
}

void Serie::save(ofstream &save)
{
	save << title << endl << genre << endl << ageRestriction << " " << seasons << " " << episodes << " " << IMDB_rank << " " << watched  << endl;
}

void Serie::load(ifstream &load)
{
	string trash;   //do przejscia do nowej linii pliku
	getline(load, trash);
	getline(load, title);
	getline(load, genre);
	load >> ageRestriction >> seasons >> episodes >> IMDB_rank >> watched;
}

string Serie::type()
{
	return "Serie";
}