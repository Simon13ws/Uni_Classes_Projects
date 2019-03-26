#pragma once
#include "Video.h"

class WatchedMovie : private Video {
	int watcher_id;
	int currentMinute;
public:
	void giveMinute();
};