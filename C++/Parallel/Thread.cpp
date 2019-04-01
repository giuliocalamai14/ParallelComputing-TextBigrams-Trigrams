#include "Thread.h"

using namespace std;


pthread_t Thread::self(){};  // return the ID of the thread in which it is invoked.

Thread::~Thread() // Thread destructor. Destroys the thread object.
{  
	if(running && !detached)
		pthread_detach(tid); // indicate to the implementation that storage for the thread can be reclaimed when that thread terminates.
		
	if(running)
		pthread_cancel(tid); // The pthread_cancel() function shall request that thread be canceled.
}

void Thread::join(){
	pthread_join(tid, (void**)&ptr); // suspends execution of the calling thread until the target thread terminates. 
}                                    // tid identifies the targeted thread, &ptr is a pointer to a pointer that point the location where the exit status of the joined thread is stored.

int Thread::start(){

	int result = pthread_create(&tid, NULL, runThread, this); // used to create a new thread. NULL means that it will be used the default attributes.
                                                                  // RUNTHREAD is the address of the function executed by the thread.
		                                                          // THIS is the address of the argument we want to pass to the thread function.
	if(result == 0)
		running = true;

	return result;
}

void* Thread::runThread(void* arg)
{
	Thread* aThread = static_cast<Thread*>(arg);
	return aThread->run();
	// return (static_cast<Thread*>(arg))->run();
}