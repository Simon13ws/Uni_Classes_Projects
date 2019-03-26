#pragma once
#include "console.h"
#include "pch.h"

//METODY WYSWIETLAJACE MENU I PRZECHODZACE DO METODY W KTOREJ WYBIERANA JEST OPCJA
//NA KONCU METOD LITERA OZNACZAJACA JAKIEGO ZAKRESU DOTYCZY : S - SERIES, M - MOVIES, L - LIVESTREAMS 

void Console::showMenu(Pool<Serie>& poolS, Pool<Movie>& poolM, Pool<Livestream>& poolL) {
	cout << "-----MENU-----" << endl;
	poolL.showUpcomingLivestream();
	cout << "1. Series" << endl;
	cout << "2. Movies" << endl;
	cout << "3. Livestreams" << endl;
	cout << "4. Exit" << endl;
	chooseTask(poolS, poolM, poolL);
	
}
void Console::showMenuS(Pool<Serie>& poolS, Pool<Movie>& poolM, Pool<Livestream>& poolL)
{
	cout << "----------SERIES----------" << endl;
	cout << "1. Show series pool." << endl;
	cout << "2. Show stats of series pool." << endl;
	cout << "3. Add serie to the pool." << endl;
	cout << "4. Delete serie from pool." << endl;
	cout << "5. Edit serie from pool." << endl;
	cout << "6. Sort pool by IMDB rank." << endl;
	cout << "7. Go back." << endl;
	cout << "8. Exit" << endl;
	chooseTaskS(poolS, poolM, poolL);
}
void Console::showMenuM(Pool<Serie>& poolS, Pool<Movie>& poolM, Pool<Livestream>& poolL)
{
	cout << "----------MOVIES----------" << endl;
	cout << "1. Show movies pool." << endl;
	cout << "2. Show stats of movies pool." << endl;
	cout << "3. Add movie to the pool." << endl;
	cout << "4. Delete movie from the pool." << endl;
	cout << "5. Edit movie from the pool." << endl;
	cout << "6. Sort pool by IMDB rank." << endl;
	cout << "7. Go back." << endl;
	cout << "8. Exit" << endl;
	chooseTaskM(poolS, poolM, poolL);
}
void Console::showMenuL(Pool<Serie>& poolS, Pool<Movie>& poolM, Pool<Livestream>& poolL)
{
	cout << "----------LIVESTREAMS----------" << endl;
	cout << "1. Show upcoming livestreams." << endl;
	cout << "2. Show stats of livestream pool." << endl;
	cout << "3. Add an upcoming livestream to the pool." << endl;
	cout << "4. Delete livestream from the pool." << endl;
	cout << "5. Edit livestream from pool." << endl;
	cout << "6. Go back." << endl;
	cout << "7. Exit" << endl;
	chooseTaskL(poolS, poolM, poolL);
}

// METODY WYBORU OPCJI I WYKONANIA CZYNNOSCI DLA KAZDEGO Z 4 MENU, PO WYKONANIU CZYNNOSCI, KTORA NIE JEST POWROTEM DO
// PIERWSZEGO MENU ALBO WYJSCIEM Z PROGRAMU PONOWNIE JEST ODTWARZANE DANE MENU I WYBOR OPCJI

void Console::chooseTask(Pool<Serie>& poolS, Pool<Movie>& poolM, Pool<Livestream>& poolL)
{
	int wybor=0;
	cout << "Choose option: ";
	cin >> wybor;
	cout << endl;
	switch (wybor)
	{
	case 1: 
		showMenuS(poolS, poolM, poolL);
		break;
	case 2: 
		showMenuM(poolS, poolM, poolL);
		break;
	case 3:	
		showMenuL(poolS, poolM, poolL);
		break;
	case 4: exit(0); break;
	default: cout << "You gave an invalid number!" << endl;
	}
	showMenu(poolS, poolM, poolL); // POPRAWNOSC DANYCH
}
void Console::chooseTaskS(Pool<Serie>& poolS, Pool<Movie>& poolM, Pool<Livestream>& poolL)
{
	int wybor = 0;
	cout << "Choose option: ";
	cin >> wybor;
	cout << endl;
	switch (wybor)
	{
	case 1: poolS.showPool(); break;
	case 2: poolS.showPoolStats();  break;
	case 3:
	{
		Serie newS = Serie();
		poolS += (newS);
		break;
	}
	case 4: 
	{
		int i;
		cout << "Which serie do you want to delete? (Give number of item in pool)" << endl;
		cin >> i;
		poolS -= (i);
		break;
	}
	case 5:
	{
		int i;
		cout << "Which serie do you want to edit?" << endl;
		cin >> i;
		poolS.editVideo(i);
		break;
	}
	case 6: 
	{
		poolS.sort();
		poolS.showPool();
		break;
	}
	case 7: showMenu(poolS, poolM, poolL); break;
	case 8: exit(0); break;
	default: cout << "You gave an invalid number!" << endl;
	}
	poolS.savePool();
	poolM.savePool();
	poolL.savePool();
	cout << endl;
	showMenuS(poolS, poolM, poolL);
}
void Console::chooseTaskM(Pool<Serie>& poolS, Pool<Movie>& poolM, Pool<Livestream>& poolL)
{
	int wybor = 0;
	cout << "Choose option: ";
	cin >> wybor;
	cout << endl;
	switch (wybor) {
	case 1: poolM.showPool(); break;
	case 2: poolM.showPoolStats(); break;
	case 3:
	{
		Movie newM = Movie();
		poolM += (newM);
		break;
	}
	case 4:
	{
		int i;
		cout << "Which movie do you want to delete? (Give number of item in pool)" << endl;
		cin >> i;
		poolM -= (i);
		break;
	}
	case 5:
	{
		int i;
		cout << "Which movie do you want to edit?" << endl;
		cin >> i;
		poolM.editVideo(i);
		break;
	}
	case 6:
	{
		poolM.sort();
		poolM.showPool();
		break;
	}
	case 7: showMenu(poolS, poolM, poolL); break;		
		cout << "Pools were saved!" << endl;
		break;
	case 8: exit(0); break;
	default: cout << "You gave an invalid number!" << endl;
	}
	poolS.savePool();
	poolM.savePool();
	poolL.savePool();
	cout << endl;
	showMenuM(poolS, poolM, poolL);
}

void Console::chooseTaskL(Pool<Serie>& poolS, Pool<Movie>& poolM, Pool<Livestream>& poolL)
{
	int wybor = 0;
	cout << "Choose option: ";
	cin >> wybor;
	cout << endl;
	switch (wybor)
	{
	case 1: poolL.showPool(); break;
	case 2: poolL.showPoolStats(); break;
	case 3:
	{
		Livestream newLS = Livestream();
		poolL += (newLS);
		break;
	}
	case 4:
	{
		int i;
		cout << "Which livestream do you want to delete? (Give number of item in pool)" << endl;
		cin >> i;
		poolL -= (i);
		break;
	}
	case 5:
	{
		int i;
		cout << "Which livestream do you want to edit?" << endl;
		cin >> i;
		poolL.editVideo(i);
		break;
	}
	case 6: showMenu(poolS, poolM, poolL); break;		
	case 7: exit(0); break;
	default: cout << "You gave an invalid number!" << endl;
	}
	poolS.savePool();
	poolM.savePool();
	poolL.savePool();
	cout << endl;
	showMenuL(poolS, poolM, poolL);

}

Console::Console() {}