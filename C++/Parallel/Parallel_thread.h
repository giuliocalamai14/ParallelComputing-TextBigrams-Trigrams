#ifndef PARALLEL_THREAD_H
#define PARALLEL_THREAD_H

#include <thread>
#include <unordered_map>
#include <string>

#include "Thread.h"

using namespace std;

class Parallel_thread: public Thread{

private:
	string fileString;

public:
	int n, begin, stop, id;
	unordered_map<string, int> map;
	Parallel_thread(int id, int n, int begin, int stop, string fileString);
    int *run();
    unordered_map<string, int> getMap();

};

#endif