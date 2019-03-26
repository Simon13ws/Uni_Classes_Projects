#pragma once
#include "Serie.h"

class WatchedSerie: private Serie {
	int watcher_ID;
	int currentEP;
	int currentMinute;
public: 
	void giveMinute();
};
