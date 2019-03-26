#include <SFML/Graphics.hpp>
#include <iostream>
#include <windows.h>
#include "mapa.h"
#include <time.h>
#include "gra.h"
#include <fstream>

using namespace std;

int wielkosc=8;

void punkty(int p1, int p2, sf::Texture& cyfry, sf::Sprite *pkt) //FUNKCJA DO POMOCY WYSWIETLANIA PKT
{
    int tab[4];
    tab[0] = p1/10;
    tab[1] = p1 - tab[0]*10;
    tab[2] = p2/10;
    tab[3] = p2 - tab[2]*10;

    for (int i=0; i<4; i++)
    {
        pkt[i].setTexture(cyfry);
        pkt[i].setTextureRect(sf::IntRect(tab[i]*93.3,0,93.3,119));
    }
}

void zapis(Mapa **pole, int p, int y)
{
    fstream zapis;
    zapis.open("zapis.txt", ios::out);
    for (int i=0; i<wielkosc; i++)
    {
        for (int j=0; j<wielkosc; j++)
            zapis << pole[j][i].czyje;
        zapis << endl;
    }
    zapis << p << endl;
    zapis << y;
}

void koniec(int pkt1, int pkt2)
{
    sf::RenderWindow okno_koniec(sf::VideoMode(500, 300, 32), "Koniec gry");
    sf::Texture koniec_t;
    sf::Sprite koniec_s;

    if (pkt1>pkt2) koniec_t.loadFromFile("tekstury/koniec1.png");
    else if (pkt2>pkt1) koniec_t.loadFromFile("tekstury/koniec2.png");
    else koniec_t.loadFromFile("tekstury/koniec3.png");

    koniec_s.setTexture(koniec_t);

    while(okno_koniec.isOpen())
    {
        sf::Event event;
        while (okno_koniec.pollEvent(event))
        {
            if (event.type == sf::Event::Closed)
            {
                Sleep(1000);
                okno_koniec.close();
            }
        }
        okno_koniec.draw(koniec_s);
        okno_koniec.display();
    }
}

int gra(int x, int y)
{
    sf::RenderWindow okno_gry(sf::VideoMode(1500, 800, 32), "Reversi - Gra");
    sf::Texture pola, zapisz_t, od_nowa_t, wyjdz_t, zapisz_over_t, od_nowa_over_t, wyjdz_over_t, cyfry, gracz1_t, gracz2_t, gracz1_r_t, gracz2_r_t;
    pola.loadFromFile("tekstury/pola.png");
    int n=0, g=0;
    Gra gra1(x,y,2,wielkosc);

    for (int i=0; i<wielkosc; i++)
    {
        for (int j=0; j<wielkosc; j++)
            gra1.pole[j][i].load_square(pola,i,j);
    }

    zapisz_t.loadFromFile("tekstury/zapisz.png");
    od_nowa_t.loadFromFile("tekstury/od_nowa.png");
    wyjdz_t.loadFromFile("tekstury/wyjdz.png");

    zapisz_over_t.loadFromFile("tekstury/zapisz_over.png");
    od_nowa_over_t.loadFromFile("tekstury/od_nowa_over.png");
    wyjdz_over_t.loadFromFile("tekstury/wyjdz_over.png");

    gracz1_t.loadFromFile("tekstury/gracz1.png");
    gracz1_r_t.loadFromFile("tekstury/gracz1_r.png");

    if (gra1.y==0)
    {
        gracz2_t.loadFromFile("tekstury/gracz2.png");
        gracz2_r_t.loadFromFile("tekstury/gracz2_r.png");
    }
    else
    {
        gracz2_t.loadFromFile("tekstury/gracz2_k.png");
        gracz2_r_t.loadFromFile("tekstury/gracz2_k_r.png");
    }

    sf::Sprite zapisz_s;
    zapisz_s.setTexture(zapisz_t);
    zapisz_s.setPosition(450,30);
    zapisz_s.setScale(0.5,0.5);

    sf::Sprite od_nowa_s;
    od_nowa_s.setTexture(od_nowa_t);
    od_nowa_s.setPosition(685,30);
    od_nowa_s.setScale(0.5,0.5);

    sf::Sprite wyjdz_s;
    wyjdz_s.setTexture(wyjdz_t);
    wyjdz_s.setPosition(920,30);
    wyjdz_s.setScale(0.5,0.5);

    sf::Sprite gracz1_s, gracz2_s;

    gracz1_s.setTexture(gracz1_t);
    gracz1_s.setPosition(107,170);
    gracz1_s.setScale(0.7,0.7);

    gracz2_s.setTexture(gracz2_t);
    gracz2_s.setPosition(1265,170);
    gracz2_s.setScale(0.7,0.7);

    cyfry.loadFromFile("tekstury/cyfry.png");

    sf::Sprite pkt[4];
    pkt[0].setPosition(235,170);
    pkt[0].setScale(0.7,0.7);
    pkt[1].setPosition(300,170);
    pkt[1].setScale(0.7,0.7);
    pkt[2].setPosition(1135,170);
    pkt[2].setScale(0.7,0.7);
    pkt[3].setPosition(1200,170);
    pkt[3].setScale(0.7,0.7);

    while (okno_gry.isOpen())
    {
        sf::Event event;
        int pkt1=0, pkt2=0;
        if(gra1.y==0 || gra1.p==2){
            while (okno_gry.pollEvent(event))
            {

                if (event.type == sf::Event::Closed){
                    //delete [] gra1.pole;
                    okno_gry.close();
                }

                sf::Vector2f mysz = okno_gry.mapPixelToCoords(sf::Mouse::getPosition(okno_gry));

                sf::FloatRect granice_zapisz = zapisz_s.getGlobalBounds();
                sf::FloatRect granice_od_nowa = od_nowa_s.getGlobalBounds();
                sf::FloatRect granice_wyjdz = wyjdz_s.getGlobalBounds();

                if(granice_zapisz.contains(mysz))
                {
                    zapisz_s.setTexture(zapisz_over_t);
                    if (sf::Mouse::isButtonPressed(sf::Mouse::Left)) //DO ZROBIENIA PROCEDURA ZAPISU
                    {
                        zapis(gra1.pole, gra1.p, gra1.y);
                    }
                }
                else zapisz_s.setTexture(zapisz_t);

                if(granice_od_nowa.contains(mysz))
                {
                    od_nowa_s.setTexture(od_nowa_over_t);
                    if (sf::Mouse::isButtonPressed(sf::Mouse::Left))
                    {
                        okno_gry.clear();
                        okno_gry.close();
                        Sleep(1000);
                        gra(0, gra1.y);
                    }
                }
                else od_nowa_s.setTexture(od_nowa_t);

                if(granice_wyjdz.contains(mysz))
                {
                    wyjdz_s.setTexture(wyjdz_over_t);
                    if (sf::Mouse::isButtonPressed(sf::Mouse::Left))
                    {
                        okno_gry.close();
                        okno_gry.clear();
                        delete [] gra1.pole;
                        Sleep(500);
                        return 1;
                    }
                }
                else wyjdz_s.setTexture(wyjdz_t);
                if(gra1.mozliwy_ruch()) //NIE PRZECHODZI WARUNKU
                {

                    n=0;
                    for(int i=0; i<wielkosc; i++)
                    {
                        for (int j=0; j<wielkosc; j++)
                        {
                            if (sf::Mouse::isButtonPressed(sf::Mouse::Left))
                            {
                                sf::FloatRect granica_pola = gra1.pole[i][j].sprajt.getGlobalBounds();
                                if (granica_pola.contains(mysz))
                                {
                                    gra1.ruch(i, j, pola);
                                }
                            }
                        }
                    }
                }
                else          //W razie braku mo¿liwoœci ruchu gracza, pomijany jest ruch
                {
                    n++;
                    if (n==1){
                    if (gra1.p==1) gra1.p=2;
                    else gra1.p=1;
                    }
                }
            }
        }
        else // RUCH KOMPUTERA
        {   if (gra1.mozliwy_ruch())
            {
                n=0;
                Sleep(1000);
                gra1.ruch_k(pola);
            }
            else if (n==0 && !gra1.mozliwy_ruch())
            {
                n++;
                if (n==1) gra1.p=2;
            }
            else if (n==1)
                n++;
        }

        if (gra1.p==2)
        {
            gracz1_s.setTexture(gracz1_r_t);
            gracz2_s.setTexture(gracz2_t);
        }
        else
        {
            gracz1_s.setTexture(gracz1_t);
            gracz2_s.setTexture(gracz2_r_t);
        }
        for (int i=0; i<wielkosc; i++)
        {
            for (int j=0; j<wielkosc; j++)
            {
                if (gra1.pole[i][j].czyje==1) pkt1++;
                if (gra1.pole[i][j].czyje==2) pkt2++;
            }
        }
        punkty(pkt1, pkt2, cyfry, pkt);
        if (n==2)       //KONIEC GRY
        {
            koniec(pkt1, pkt2);
            Sleep(500);
            okno_gry.close();
            delete [] gra1.pole;
        }
        for(int i=0; i<wielkosc; i++)
        {
            for (int j=0; j<wielkosc; j++)
                okno_gry.draw(gra1.pole[j][i].sprajt);

        }
        for (int i=0; i<4; i++)
        {
            okno_gry.draw(pkt[i]);
        }

        okno_gry.draw(zapisz_s);
        okno_gry.draw(od_nowa_s);
        okno_gry.draw(wyjdz_s);
        okno_gry.draw(gracz1_s);
        okno_gry.draw(gracz2_s);
        okno_gry.display();
    }
}

void menu()
{
    sf::RenderWindow okno(sf::VideoMode(1500, 800, 32), "Menu Reversi");

    sf::Texture plansza_t, zagraj_g_t, zagraj_k_t, kontynuuj_t, wyjdz_t, zagraj_g_over_t, zagraj_k_over_t, kontynuuj_over_t, wyjdz_over_t;

    plansza_t.loadFromFile("tekstury/plansza.png");
    zagraj_g_t.loadFromFile("tekstury/zagraj_g.png");
    zagraj_k_t.loadFromFile("tekstury/zagraj_k.png");
    kontynuuj_t.loadFromFile("tekstury/kontynuuj.png");
    wyjdz_t.loadFromFile("tekstury/wyjdz.png");

    zagraj_g_over_t.loadFromFile("tekstury/zagraj_g_over.png");
    zagraj_k_over_t.loadFromFile("tekstury/zagraj_k_over.png");
    kontynuuj_over_t.loadFromFile("tekstury/kontynuuj_over.png");
    wyjdz_over_t.loadFromFile("tekstury/wyjdz_over.png");

    sf::Sprite plansza_s;
    plansza_s.setTexture(plansza_t);
    plansza_s.setPosition(800,100);
    plansza_s.setScale(0.5,0.5);

    sf::Sprite zagraj_g_s;
    zagraj_g_s.setTexture(zagraj_g_t);
    zagraj_g_s.setPosition(300,100);

    sf::Sprite zagraj_k_s;
    zagraj_k_s.setTexture(zagraj_k_t);
    zagraj_k_s.setPosition(300,250);

    sf::Sprite kontynuuj_s;
    kontynuuj_s.setTexture(kontynuuj_t);
    kontynuuj_s.setPosition(300,400);

    sf::Sprite wyjdz_s;
    wyjdz_s.setTexture(wyjdz_t);
    wyjdz_s.setPosition(300,550);

    while (okno.isOpen())
    {
        sf::Event event;

        while (okno.pollEvent(event))
        {
            if (event.type == sf::Event::Closed)
                okno.close();

            sf::Vector2f mysz = okno.mapPixelToCoords(sf::Mouse::getPosition(okno));

            sf::FloatRect granice_zagraj_g = zagraj_g_s.getGlobalBounds();
            sf::FloatRect granice_zagraj_k = zagraj_k_s.getGlobalBounds();
            sf::FloatRect granice_kontynuuj = kontynuuj_s.getGlobalBounds();
            sf::FloatRect granice_wyjdz = wyjdz_s.getGlobalBounds();

            if(granice_zagraj_g.contains(mysz))
            {
                zagraj_g_s.setTexture(zagraj_g_over_t);
                if (sf::Mouse::isButtonPressed(sf::Mouse::Left))
                {
                    okno.clear();
                    okno.close();
                    Sleep(1000);
                    int g = gra(0, 0);
                    if (g==1)
                    {
                        okno.clear();
                        okno.close();
                        Sleep(1000);
                        menu();
                    }
                }
            }
            else zagraj_g_s.setTexture(zagraj_g_t);

            if(granice_zagraj_k.contains(mysz))
            {
                zagraj_k_s.setTexture(zagraj_k_over_t);
                if (sf::Mouse::isButtonPressed(sf::Mouse::Left))
                {
                    okno.clear();
                    okno.close();
                    Sleep(1000);
                    int g=0;
                    g = gra(0, 1);
                    if (g==1)
                    {
                        okno.clear();
                        okno.close();
                        Sleep(1000);
                        menu();
                    }
                }
            }
            else zagraj_k_s.setTexture(zagraj_k_t);

            if(granice_kontynuuj.contains(mysz))
            {
                kontynuuj_s.setTexture(kontynuuj_over_t);
                if (sf::Mouse::isButtonPressed(sf::Mouse::Left))
                {
                    okno.clear();
                    okno.close();
                    Sleep(1000);
                    int g=0;
                    g = gra(1,0);
                    if (g==1)
                    {
                        okno.clear();
                        okno.close();
                        Sleep(1000);
                        menu();
                    }
                }
            }
            else kontynuuj_s.setTexture(kontynuuj_t);

            if(granice_wyjdz.contains(mysz))
            {
                wyjdz_s.setTexture(wyjdz_over_t);
                if (sf::Mouse::isButtonPressed(sf::Mouse::Left)){
                     okno.close();
                     exit(0);
                }
            }
            else wyjdz_s.setTexture(wyjdz_t);
        }

        okno.draw(plansza_s);
        okno.draw(zagraj_g_s);
        okno.draw(zagraj_k_s);
        okno.draw(kontynuuj_s);
        okno.draw(wyjdz_s);

        okno.display();
    }
}
int main()
{
    menu();

    return 0;
}

