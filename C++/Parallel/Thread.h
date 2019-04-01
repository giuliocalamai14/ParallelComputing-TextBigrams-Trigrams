#ifndef THREAD_H
#define THREAD_H


#include <pthread.h>
#include <string>
#include <unordered_map>

using namespace std;

class Thread{

public:

	int *ptr;
	virtual int *run() = 0;

	pthread_t self();  // return the ID of the thread in which it is invoked.

	~Thread(); // Thread destructor. Destroys the thread object.
	
	void join();    // tid identifies the targeted thread, &ptr is a pointer to a pointer that point the location where the exit status of the joined thread is stored.

	int start();

	static void* runThread(void* arg);
	
private:

	pthread_t tid;
	bool running, detached;
};

#endif