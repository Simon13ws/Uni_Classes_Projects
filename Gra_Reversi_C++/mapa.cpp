#include <iostream>
#include <SFML/Graphics.hpp>
#include "mapa.h"


void Mapa::load_square(sf::Texture& pola, int i, int j)
{
    if(czyje==0){
            sprajt.setTexture(pola);
            sprajt.setTextureRect(sf::IntRect(0,0,150,150));
            sprajt.setPosition(450+(75*j),130+(75*i));
            sprajt.setScale(0.5,0.5);
            }
        else if(czyje==2){
            sprajt.setTexture(pola);
            sprajt.setTextureRect(sf::IntRect(150,0,150,150));
            sprajt.setPosition(450+(75*j),130+(75*i));
            sprajt.setScale(0.5,0.5);
        }
        else{
            sprajt.setTexture(pola);
            sprajt.setTextureRect(sf::IntRect(300,0,150,150));
            sprajt.setPosition(450+(75*j),130+(75*i));
            sprajt.setScale(0.5,0.5);
        }

}


