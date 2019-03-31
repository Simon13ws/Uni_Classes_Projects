#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <sys/msg.h>

#define MAX 12

struct myBuf{
    long mtype;
    int mvalue;
}

main(){
    int shmid;
    int id = 2;
    volatile int *buf;
    int key = 63500;
    int msgid;
    struct myBuf msgBuf;
    
    shmid = shmget(key, (MAX)*sizeof(int), IPC_CREAT|0640);
    if(shmid == -1){
	perror("Tworzenie segmentu pamieci wspoldzielonej 1");
	exit(1);
    }

    buf = (int*) shmat(shmid, NULL, 0);
    if(buf == NULL){
	perror("Przylaczenie 1 segmentu pamieci wspoldzielonej");
	exit(1);
    }

    msgid = msgget(key, IPC_CREAT|IPC_EXCL|0644);
    if(msgid == -1){
	msgid = msgget(key, IPC_CREAT|0644);
	if(msgid == -1){
	    perror("Utworzenie kolejki komunikatow");
	    exit(1);
	}
    }
    
    buf[id+5] = 0;
    
    int n = 20;
    for(int i = 0; i<n; i++){
	if(msgrcv(msgid, &msgBuf, sizeof(msgBuf.mvalue),id+1, 0) ==-1)
	{
	    perror("Blad odebrania");
	    exit(1);
	}  
	if(i==n-1) buf[id+5]=1;
	buf[id] +=3;
	if(buf[11] == id)
	{
	    msgBuf.mtype = 11; 
	    if(msgsnd(msgid, &msgBuf, sizeof(msgBuf.mvalue), 0) ==-1)
	    {
		perror("Blad wysylania komunikatu");
		exit(1);
	    }
	} 
	else if(buf[10] == id){
	    msgBuf.mtype = 10;
	    buf[10] = -1;
	    if(msgsnd(msgid, &msgBuf, sizeof(msgBuf.mvalue), 0) == -1)
	    {
		perror("Blad wysylania komunikatu");
		exit(1);
	    }
	}
    }
    printf("Glodomor %d skonczyl jesc\n", id+1);
    exit(0);
}