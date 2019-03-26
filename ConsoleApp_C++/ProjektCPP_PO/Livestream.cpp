#pragma once
#include "pch.h"
#include <iostream>
#include <string>
#include <stdio.h>
#include <time.h>

Livestream::Livestream()
{
	CheckInput check;
	cout << "Hour: ";
	check.CheckIfTime(date.tm_hour, 0, date.tm_mon, date.tm_year);
	cout << "Minute: ";
	check.CheckIfTime(date.tm_min, 1, date.tm_mon, date.tm_year);
	cout << "Year: ";
	check.CheckIfTime(date.tm_year, 2, date.tm_mon, date.tm_year);
	cout << "Month: ";
	check.CheckIfTime(date.tm_mon, 3, date.tm_mon, date.tm_year);
	cout << "Day of month: ";
	check.CheckIfTime(date.tm_mday, 4, date.tm_mon, date.tm_year);
	cout << "Title: ";
	cin.ignore();
	getline(cin, title);
	//cin.clear();
	cout << "Genre: ";
	getline(cin, genre);
	//cin.clear();
	cout << "Age restriction: ";
	check.CheckIfInt(ageRestriction);
}

Livestream::Livestream(char s) {} //DLA PUSTEGO OBIEKTU

void Livestream::edit(){

	CheckInput check;
	int choice = 0;
	while (choice != 10)
	{
		cout << "Choose what you want to edit: " << endl;
		cout << "1. Title - " << title << endl;
		cout << "2. Genre - " << genre << endl;
		cout << "3. Hour - " << date.tm_hour << endl;
		cout << "4. Minute - " << date.tm_min << endl;
		cout << "5. Day - " << date.tm_mday << endl;
		cout << "6. Month - " << date.tm_mon << endl;
		cout << "7. Year - " << date.tm_year << endl;
		cout << "8. Age restriction - " << ageRestriction << endl;
		cout << "9. Watched status - " << watched << endl;
		cout << "10. Exit edition" << endl;

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
			cout << "New hour: ";
			check.CheckIfTime(date.tm_hour, 0, date.tm_mon, date.tm_year);
			break;
		case 4:
			cout << "New minute: ";
			check.CheckIfTime(date.tm_min, 1, date.tm_mon, date.tm_year);
			break;
		case 5: cout << "New day of month: ";
			check.CheckIfTime(date.tm_mday, 4, date.tm_mon, date.tm_year);
			break;
		case 6: cout << "New month: ";
			check.CheckIfTime(date.tm_mon, 3, date.tm_mon, date.tm_year);
			break;
		case 7: cout << "New year: ";
			check.CheckIfTime(date.tm_year, 2, date.tm_mon, date.tm_year);
			break;
		case 8: cout << "New age restriction: ";
			check.CheckIfInt(ageRestriction);
			break;
		case 9: cout << "New watched status (0 - not watched, 1 - is currently watched, 2 - has been watched): ";
			cin >> watched;
			break;
		case 10: break;
		default: cout << "You gave wrong number!" << endl;
		}
	}
}

void Livestream::showInfo()
{
	cout << date.tm_mday << "/" << date.tm_mon << "/" << date.tm_year << " " << date.tm_hour << ":";
	if (date.tm_min < 10) cout << "0";
	cout << date.tm_min << " \"" << title << "\" Genre: " << genre << " Age: +" << ageRestriction;
	if (watched == 1) cout << "  CURRENTLY WATCHED" << endl;
	else if (watched == 2) cout << "  WATCHED" << endl;
	else cout << endl;
}

void Livestream::save(ofstream &save)
{
	save << title << endl << genre << endl << date.tm_mday << " " << date.tm_mon << " " << date.tm_year << " " << date.tm_hour << " " << date.tm_min << " " << ageRestriction << " " << watched << endl;
}

void Livestream::load(ifstream &load)
{
	string trash; //ZMIENNA TRASH, ABY PRZEJSC DO KOLEJNEJ LINII
	getline(load, trash);
	getline(load, title);
	getline(load, genre);
	load >> date.tm_mday >> date.tm_mon >> date.tm_year >> date.tm_hour >> date.tm_min >> ageRestriction >> watched;
}

string Livestream::type()
{
	return "Livestream";
}

long Livestream::calculateTime()
{
	time_t now;
	struct tm thedate = { 0 };
	double seconds;

	thedate.tm_hour = date.tm_hour; 
	thedate.tm_min = date.tm_min;
	thedate.tm_sec = 0;
	thedate.tm_year = date.tm_year-1900;
	thedate.tm_mon = date.tm_mon-1;
	thedate.tm_mday = date.tm_mday;

	time(&now);

	seconds = difftime(mktime(&thedate), now);
	return seconds;
}