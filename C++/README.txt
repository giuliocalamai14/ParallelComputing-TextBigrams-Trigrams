Per eseguire da terminale:
	- Parallel:
		1) In Parallel_main.cpp immettere il percorso corretto del file text(./text/text50KB.txt). 
		2) $ g++ -o prova Parallel_main.cpp Parallel_thread.cpp Thread.cpp
		3) dopo questo generà un file eseguibile di nome prova, il quale va eseguito con:
		   $ ./prova

	- Sequenziale:
		1) In Sequential_main.cpp immettere il percorso corretto del file text(./text/text50KB.txt). 
		2) $ g++ -o prova Sequential_main.cpp
		3) dopo questo generà un file eseguibile di nome prova, il quale va eseguito con:
		   $ ./prova

Per la versione parallela è possibile anche includere i file Parallel_thread.cpp e Thread.cpp nel file Parallel_main.cpp in modo da poter fare nel punto 1) solo:
	$ g++ -o prova Parallel_main.cpp
----------------------------------------------------------
Per eseguire con CLion
	-Sequenziale: Aprire il progetto Sequential con CLion
	-Parallella: Aprire il progetto Parallel con CLion