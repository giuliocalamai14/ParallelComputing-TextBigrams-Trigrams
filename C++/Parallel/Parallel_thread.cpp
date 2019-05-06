#include <thread>
#include <unordered_map>
#include <string>

#include "Parallel_thread.h"

using namespace std;


Parallel_thread::Parallel_thread(int id, int n, int begin, int stop, string fileString){

	this->id = id;
	this->n = n;
	this->begin = begin;
	this->stop = stop;
	this->fileString = fileString;
}


int* Parallel_thread::run(){	 

	unordered_map<string, int> map;
	fileString.erase(remove(fileString.begin(), fileString.end(), '\n'), fileString.end());
	if (stop > fileString.length() - 1){
		stop = fileString.length() - 1;
	}
	for (int i = this->begin + this->n - 1; i <= this->stop; i++){
		string key = "";
		for(int j = this->n - 1; j >= 0; j--){
			key = key + this->fileString[i-j];
		}
		if(map.count(key) == 0){
			pair<string,int> pair (key,1);
	        map.insert(pair);
	        }
	        else{
	            if(map.count(key) >= 1){
	                 map[key] += 1;
	            }
	        }
	    }
	this->map = map;
	return 0;
}

 unordered_map<string, int> Parallel_thread::getMap(){
    return this->map;
 }
