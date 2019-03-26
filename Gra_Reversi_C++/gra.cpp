#include "gra.h"
#include <fstream>
#include <SFML/Graphics.hpp>
#include <windows.h>
#include <iostream>
using namespace std;

Gra::Gra(int s, int l, int o, int w)
{
    p=o;
    x=s;
    y=l;
    wielkosc = w;
    pole = new Mapa* [wielkosc];
    for (int i=0; i<wielkosc; i++)
        pole[i] = new Mapa [wielkosc];

    if (x==0)
    {
        pole[3][3].czyje=1;
        pole[4][4].czyje=1;
        pole[3][4].czyje=2;
        pole[4][3].czyje=2;
    }
    else
    {
        fstream zapis;
        string linia;
        zapis.open("zapis.txt", ios::in);

        if(zapis.good()==false)
        {
            pole[3][3].czyje=1;
            pole[4][4].czyje=1;
            pole[3][4].czyje=2;
            pole[4][3].czyje=2;
        }
        else
        {
            for (int i=0; i<wielkosc; i++)
            {
                getline(zapis, linia);
                for (int j=0; j<wielkosc; j++)
                    pole[j][i].czyje = linia[j] - '0';
            }
            getline(zapis, linia);
            p=linia[0] - '0';
        }
        getline(zapis, linia);
        y=linia[0] - '0';
    }
}


bool Gra::dozwolony(int k1, int w1) //sprawdza czy na danym polu mozna postawic pionka
{
    if (pole[k1][w1].czyje!=0) return false;
    int k2, w2, g;
    if(p==1) g=2;
    else g=1;
    for (int i=-1; i<2; i++)
    {
        for (int j=-1; j<2; j++) //SPRAWDZANIE Pol
        {
            k2=k1+i;
            w2=w1+j;
            if((k1+i>=0) && (k1+i<wielkosc) && (w1+j>=0) && (w1+j<wielkosc) && ((j!=0) || (i!=0)) && (pole[k1+i][w1+j].czyje==p)) //sprawdza czy pole obok ma pionek przeciwnika
            {
                while((k2>=0) && (k2<wielkosc) && (w2>=0) && (w2<wielkosc) && (pole[k2][w2].czyje==p)) //iteruje siê dalej w tym kierunku na ktorym znalazl pionek przeciwnika
                {

                    k2+=i;
                    w2+=j;
                }
                if ((k2>=0) && (k2<wielkosc) && (w2>=0) && (w2<wielkosc) && (pole[k2][w2].czyje==g)) //sprawdza czy za pionkami przeciwnika znajduje sie pionek gracza
                    return true;
            }
        }
    }

    return false;
}


bool Gra::mozliwy_ruch()      // sprawdzenie czy gracz w ogole moze wykonac ruch
{
    for (int i=0; i<wielkosc; i++)
    {
        for (int j=0; j<wielkosc; j++)
        {
            if (dozwolony(i, j)){
                return true;
            }
        }
    }
    return false;
}

void Gra::zamiana(int k1, int w1, sf::Texture& pola, int g) // Zmiana pol przeciwnika. Podobny mechanizm co przy sprawdzaniu czy dany ruch jest dozwolony, jednak gdy znajdzie pionek gracza po przejsciu przez pionki przeciwnika, powraca i zmienia pionki przeciwnika na pionki gracza
{
    int k2, w2;
    for (int i=-1; i<2; i++)
    {
        for (int j=-1; j<2; j++) //SPRAWDZANIE Pol
        {
            if((k1+i>=0) && (k1+i<wielkosc) && (w1+j>=0) && (w1+j<wielkosc) && ((j!=0) || (i!=0)) && (pole[k1+i][w1+j].czyje==p))
            {
                k2=k1+i;
                w2=w1+j;
                while((k2>=0) && (k2<wielkosc) && (w2>=0) && (w2<wielkosc) && (pole[k2][w2].czyje==p))
                {
                    k2+=i;
                    w2+=j;
                }
                if((k2>=0) && (k2<wielkosc) && (w2>=0) && (w2<wielkosc) && (pole[k2][w2].czyje==g))
                {
                    k2-=i;
                    w2-=j;

                    while((k2>=0) && (k2<wielkosc) && (w2>=0) && (w2<wielkosc) && (pole[k2][w2].czyje==p))
                    {
                        pole[k2][w2].czyje=g;
                        pole[k2][w2].load_square(pola, w2, k2);
                        k2-=i;
                        w2-=j;
                    }
                }
            }
        }
    }
}

void Gra::ruch(int i, int j, sf::Texture& pola) //wykonanie ruchu i zmiana aktualnego gracza (p - aktualny przeciwnik)
{
    if (dozwolony(i,j)) //warunek sprawdza czy pole jest puste i czy obok jest przeciwnik
    {
        if (p==2)
        {
            pole[i][j].czyje=1;
            zamiana(i,j,pola,1);
        }
        else
        {
            pole[i][j].czyje=2;
            zamiana(i,j,pola,2);
        }
        pole[i][j].load_square(pola,j,i);

        if (p==1) p=2;
        else p=1;

    }

}

void Gra::ruch_k(sf::Texture& pola) //Ruch komputera
{
    int tmp=0, s=0, l=0, n=1, r=0;
    srand(time(NULL));
    for (int i=0; i<wielkosc; i++)
    {
        for (int j=0; j<wielkosc; j++)
        {
            if (dozwolony(i,j))
            {
                int ile=0;
              for (int k=-1;k<2;k++)
              {
                  for (int w=-1;w<2;w++)
                  {
                    int k1=i;
                    int w1=j;
                    if((k1+k>=0) && (k1+k<wielkosc) && (w1+w>=0) && (w1+w<wielkosc) && ((w!=0) || (k!=0)) && (pole[k1+k][w1+w].czyje==1))
                    {
                        k1+=k;
                        w1+=w;
                        while((k1>=0) && (k1<wielkosc) && (w1>=0) && (w1<8) && (pole[k1][w1].czyje==1))
                        {
                            k1+=k;
                            w1+=w;
                        }
                        if ((k1>=0) && (k1<wielkosc) && (w1>=0) && (w1<8) && (pole[k1][w1].czyje==2))
                        {
                            k1-=k;
                            w1-=w;
                            while(pole[k1][w1].czyje==1){
                                ile++;
                                k1-=k;
                                w1-=w;
                            }
                        }
                    }
                  }
              }
              if (ile>tmp)
              {
                  n=0;
                  tmp=ile;
                  s=i;
                  l=j;
              }
              else if (ile==tmp && ile!=0)
              {
                  n++;
                  r = rand()%n;
                  if (r==0)
                  {
                    tmp=ile;
                    s=i;
                    l=j;
                  }
              }
            }
        }
    }
    ruch(s,l, pola);
}

