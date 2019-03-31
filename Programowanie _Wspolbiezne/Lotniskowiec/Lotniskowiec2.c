#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

#define MAX 15

int zajeteMiejsca = 0;
int jeszcze[2];
int liczbaW = 2;
pthread_mutex_t pas = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t c1 = PTHREAD_COND_INITIALIZER;
pthread_cond_t c2 = PTHREAD_COND_INITIALIZER;

struct arg{
    int id;
};

void* ladowanie(void *p){
    int id = *((int *) p);
    for(int i=0; i<30; i++){
	pthread_mutex_lock(&pas);
	do
	{
	    if(zajeteMiejsca < 15)
	    {
		zajeteMiejsca++;
		printf("Ladowanie: Samolot laduje. %d samolotow na lotniskowcu.\n", zajeteMiejsca);
		break;
	    } 
	    else
	    {
		printf("Ladowanie: Samolot czeka na miejsce na lotniskowcu, aby wyladowac.\n");
		pthread_cond_wait(&c1, &pas);
		printf("Ladowanie: Zwolnilo sie miejsce.\n");	
	    }
	} while(1);
	pthread_mutex_unlock(&pas);
	pthread_cond_signal(&c2);
    }
    jeszcze[id/2] = 0;
    pthread_cond_signal(&c2);
    free(p);
}

void* wylatywanie(void *p){
    for(int i=0; i<30; i++){
	pthread_mutex_lock(&pas);
	do
	{
	    if(zajeteMiejsca == 15  || ((jeszcze[0]==0 && jeszcze[1]==0) && zajeteMiejsca>0))
	    {
		zajeteMiejsca--;
		printf("Wylot: Samolot wylatuje. %d samolotow na lotniskowcu.\n", zajeteMiejsca);
		break;
	    }
	    else if(zajeteMiejsca == 0)
	    {
		pthread_cond_wait(&c2, &pas);
	    } 
	    else
	    {
		printf("Wylot: Samolot czeka az pas startowy sie zwolni.\n");
		pthread_cond_wait(&c2, &pas);
		printf("Wylot: Pas startowy zostal zwolniony.\n");
	    }
	} while(1);
	pthread_mutex_unlock(&pas);
	pthread_cond_signal(&c1);
    }
    free(p);
}

int main(){
    jeszcze[0] = 1;
    jeszcze[1] = 1;
   pthread_t pth[liczbaW*2];
   for(int i=0; i<liczbaW; i++)
   {	
	int *arg = malloc(sizeof(*arg));
	int *arg2 = malloc(sizeof(*arg));
	*arg = i;
	*arg = i+1;
	pthread_create(&pth[i], NULL, ladowanie, arg);
	pthread_create(&pth[i+1], NULL, wylatywanie, arg2);
   }
    for(int i=0; i<liczbaW; i++){
	pthread_join(pth[i], NULL);
	pthread_join(pth[i+1], NULL);
    }
}
