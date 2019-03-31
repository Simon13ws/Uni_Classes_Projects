#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <sys/msg.h>

#define MAX 12

static struct sembuf buf;

struct myBuf{
    long mtype;
    int mvalue;
}

main(){
    int shmid;
    volatile int *buf;
    int key = 63500;
    int msgid;
    struct myBuf msgBuf;

    shmid = shmget(key, (MAX)*sizeof(int), IPC_CREAT|0640);
    if(shmid == -1){
	perror("Tworzenie 1 segmentu pamieci wspoldzielonej");
	exit(1);
    }

    buf = (int*) shmat(shmid, NULL, 0);
    if(buf == NULL){
	perror("Przylaczenie 1 segmentu pamieci wspoldzielonej");
	exit(1);
    }

    msgid = msgget(key, IPC_CREAT|IPC_EXCL|0600);
    if(msgid == -1){
	msgid = msgget(key, IPC_CREAT|0600);
	if(msgid == -1){
	    perror("Utworzenie kolejki komunikatow");
	    exit(1);
	}
    }
    else{
	msgBuf.mtype = 10;
	msgBuf.mvalue = 10;
	if(msgsnd(msgid, &msgBuf, sizeof(msgBuf.mvalue), 0) == -1){
	    perror("Blad wysylania wiadomosci");
	    exit(1);
	}
    }
    buf[10] = -1;
    buf[11] = -1;
    while(buf[5]==0 || buf[6] ==0 || buf[7] == 0 || buf[8]==0 || buf[9] == 0){
	if(msgrcv(msgid, &msgBuf, sizeof(msgBuf.mvalue), 10, 0)==-1)
	{
	    perror("Blad odczytu");
	    exit(1);
	}
	if(buf[11]!=-1){
	    msgrcv(msgid, &msgBuf, sizeof(msgBuf.mvalue),11,0);
    	    buf[11] = -1;
	}
	int min = 0;
	for(int k = 0; k<5; k++)
	    if(buf[min+5]==1) min++;
	for(int k = 0; k < 5; k++)
	{
	    if(buf[k]<buf[min] && buf[k+5]==0) min = k; 
	}
	buf[10] = min;

	if(buf[buf[10]+5] == 1 || min > 4) continue;
	for(int k=0; k<5; k++)
	    printf(" %d ", buf[k]);

	msgBuf.mtype = buf[10]+1;
	printf("\nGlodomor %d je \n", buf[10]+1);
	if(msgsnd(msgid, &msgBuf, sizeof(msgBuf.mvalue),0)==-1){
	    perror("Blad wysylania");
	    exit(1);
	}
    
	if(buf[(min+2)%5+5] == 1 && buf[(min+3)%5+5] == 1)
	    buf[11] = -1;
	else if (buf[(min+2)%5+5] == 0 && buf[(min+3)%5+5] == 1)
	    buf[11] = (min+2)%5;
	else if (buf[(min+2)%5+5] == 1 && buf[(min+3)%5+5] == 0)
	    buf[11] = (min+3)%5;
	else
	{
	    if(buf[(min+2)%5] <=  buf[(min+3)%5])
	        buf[11] = (min+2)%5;
 	    else
	        buf[11] = (min+3)%5;
	}
	    
	if(buf[11]!=-1){
	    msgBuf.mtype = buf[11]+1;
	    printf("Glodomor %d je \n", buf[11]+1);
	    if(msgsnd(msgid, &msgBuf, sizeof(msgBuf.mvalue),0) == -1){
		perror("Blad wysylania");
		exit(1);
	    }
	}
	
    }
    exit(0);
}