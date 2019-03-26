#pragma once
#include <SFML/Graphics.hpp>
#include "mapa.h"


class Gra: public Mapa
{
    public:
    int x;
    int y;
    int p=2;
    int wielkosc;
    Mapa **pole;

    Gra(int, int, int, int);
    void ruch(int,int, sf::Texture&);
    void ruch_k(sf::Texture&);
    bool dozwolony(int, int);
    bool mozliwy_ruch();
    void zamiana(int, int, sf::Texture&, int);
    void zapis();

};
