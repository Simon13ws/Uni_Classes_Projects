#pragma once
#include "pch.h"
#include <vector>
#include <fstream>
using namespace std;

//KLASA GENERYCZNA

template <class myClass>
class Pool {
	vector <myClass> thePool;
	int howMuchInPool=0; //Zmienna z liczba elementow w puli
public:
	void showPool() //Wyswietlanie kazdego elementu z puli
	{
		for (unsigned int i = 0; i < thePool.size(); i++)
		{
			cout << i + 1 << ". ";
			thePool.at(i).showInfo();
		}
	}
	myClass& operator+=(myClass& vid) //Dodanie elementu do puli
	{
		 thePool.push_back(vid);
		 howMuchInPool++;
		 return vid;
	}
	myClass& operator-=(int i) //Usuniecie elementu z puli
	{
		myClass nothing('s');
		thePool.erase(thePool.begin() + i-1);
		howMuchInPool--;
		return nothing;
	}
	void editVideo(int i) //Edycja elementu z puli - odwolanie do funkcji edytujacej dany obiekt
	{
		thePool.at(i - 1).edit();
	}
	void showPoolStats() //Wyswietlenie informacji zbiorczych puli - ilosci elementow w puli, ilosc nieobejrzanych, ogladanych i obejrzanych
	{
		int in_Pool = thePool.size();
		int not_Watched = 0, currently_Watched = 0, watched = 0;
		for (unsigned int i = 0; i < thePool.size(); i++)
		{
			if (thePool.at(i).watched == 0) not_Watched++;
			else if (thePool.at(i).watched == 1) currently_Watched++;
			else if (thePool.at(i).watched == 2) watched++;
		}
		cout << "Items in pool: " << in_Pool << endl;
		cout << "Not watched: " << not_Watched << endl;
		cout << "Currently watched: " << currently_Watched << endl;
		cout << "Watched: " << watched << endl;
	}

	void sort() //Sortowanie seriali/filmow po randze IMDB algorytmem insertion sort
	{
		if (!thePool.empty())
		{
			int j;
			for (int i = 0; i < thePool.size(); i++)
			{
				myClass tmp = thePool.at(i);
				j = i - 1;
				while ((j >= 0) && (thePool.at(j).IMDB_rank > thePool.at(i).IMDB_rank))
				{
					thePool.at(j + 1) = thePool.at(j);
					j--;
				}
				thePool.at(j + 1) = tmp;
			}
		}
	}

	void savePool() //Zapis puli do do osobnych plikow dla kazdego rodzaju tresci
	{

		ofstream save;
		myClass nothing('s');
		string type = nothing.type();
		save.open("PoolSave" + type + ".txt", ofstream::out);
		save << howMuchInPool << endl;
		for (unsigned int i = 0; i < thePool.size(); i++)
		{
			thePool.at(i).save(save);
		}
		save.close();
	}

	void loadPool(Pool <myClass> &pool) //Wczytanie puli z plikow
	{
		ifstream load;
		myClass newElement('s');
		string type = newElement.type();
		load.open("PoolSave" + type + ".txt", ifstream::in);
		load >> howMuchInPool;
		if (load)
		{
			for (int i = 0; i < howMuchInPool; i++)
			{
				newElement.load(load);
				pool += (newElement);
				howMuchInPool--;
			}
		}
		load.close();
	}
	int size()
	{
		return thePool.size();
	}
	void showUpcomingLivestream() //Wyswietlanie najblizszego nadchodzacego wydarzenia na zywo
	{
		double seconds, another;
		Livestream upcoming = thePool.at(0);
		seconds = thePool.at(0).calculateTime();
		for (unsigned int i = 1; i < thePool.size(); i++)
		{
			another = thePool.at(i).calculateTime();
			if ((seconds < 0 || seconds > another) && another>0)
			{
				upcoming = thePool.at(i);
				seconds = another;
			}
		}
		if (seconds > 0)
		{
			cout << "Next upcoming livestream: ";
			upcoming.showInfo();
		}
		else cout << "There is not an upcoming livestream in the pool." << endl;
	}
};


