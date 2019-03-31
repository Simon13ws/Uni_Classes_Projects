#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

main(){
    if(fork()==0){
	execl("./G1","./G1",NULL);
	perror("Blad wywolania G1");
    }
    else{
	if(fork()==0){
	    execl("./G2","./G2",NULL);
	    perror("Blad wywolania G2");
        }
	else{
	    if(fork()==0){
		printf("Co jest 3");
		execl("./G3","./G3",NULL);
		perror("Blad wywolania G3");
	    }
	    else{
		if(fork()==0){
		    execl("./G4","./G4",NULL);
			perror("Blad wywolania G4");
		}
	        else{
		    if(fork()==0){
			execl("./G5","./G5",NULL);
			perror("Blad wywolania G5");
		    }
		    else{
		        execl("./Kelner","./Kelner",NULL);
			perror("Blad wywolania Kelnera");
			
		    }
		}
	    }
        }
    }
}

    