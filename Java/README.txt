Per eseguire da Terminale:
	-Sequential
		1) Creare directory con all'intero:
			-Main.java
			-Sequential.java
			-text50KB.txt
			-text500KB.txt
			-text10MB.txt
			-text50MB.txt
			-text100MB.txt
			-text150MB.txt
		2) Recarsi all'interno della directory creata
		3) $ javac Main.class -d out
		4) $ cd ./out
		5) java Main

	-Parallel
		1) Creare directory con all'intero:
			-Main.java
			-Parallel.java
			-Main.java
			-Sequential.java
			-text50KB.txt
			-text500KB.txt
			-text10MB.txt
			-text50MB.txt
			-text100MB.txt
			-text150MB.txt
		2) Recarsi all'interno della directory creata
		3) $ javac Main.class -d out
		4) $ cd ./out
		5) java Main
----------------------------------------------------------
Per eseguire con IntelliJ
	-Sequenziale: Aprire il progetto Sequential con ItelliJ
	-Parallella: Aprire il progetto Parallel con ItelliJ