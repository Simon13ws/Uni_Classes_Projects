#pragma once
#include <SFML/Graphics.hpp>

class Mapa
{
    public:
    int czyje=0;
    sf::Sprite sprajt;

    void load_square(sf::Texture&, int, int);
    void ruch(Mapa, int, int);
    bool obok(Mapa, int, int);
};
