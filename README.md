# serialconn-java
Read and write data to serial port devices (also  Arduino)

La mia pagina di riferimento dove viene spiagto il codice :
  www.antoiovi.com/java/java-leggere-dati-da-porta-seriale-usb

 Programma per provare la libreria jscc (https://github.com/scream3r/java-simple-serial-connector ) per la lettura  e scrittura dati da uan porta seriale porta seriale (USB, Arduino, ecc).
 
 Sono presenti : 
    
  ° L'applicazione Talk.java, che presenta una interfaccia grafica per modificare 
  	le configurazioni di connessione e per visualizzare i dati trasmessi e ricevuti a/da 
  	la porta seriale. Questa applicazione puo` essere utilizzata per testare gli sketch di Arduino.
  	I dati ricevuti vengono salvati in un file che si chiama SerialData-YYYY-MM-dd-n.dat, dove YYYY-MM-dd
  	 sono  la dtata in cui viene creato, ed n il sequenziale del giorno.  Ogni volta che si apre una connessione 
  	 viene creato un file di output, ed ogni volta che si chiude viene chiuso. 
  	 Ogni volta che si apre il programma viene creato anche un Logfile.txt i cuio vengon scritti gli eventi del
  	  giorno.
 
  
 ° La classe Serial.java che utilizza la libreria jssc per connettersi ad una porta seriale e ricevere ed 
 		 inviare  dati ad essa; Le classi del package com.antoiovi.serial possono essere utilizzate per  creare 
 		 programmi personalizzati.
 
 
 
 Da utilizzare per scopo didattico per mostrare la possibilità di connessione tramite java ad una 
 porta seriale, porta usb, e quindi anche Arduino utilizzando la librearia jssc.
 
 E stato testato con successo per ricevere dati da strumentazione professionale che stampava i dati tramite  
 porta seriale RS-232.
  
 
 Compilazione con maven   : 
 		
	serialconn-java$ mvn   package 
 		
 Esecuzione :
		
		
		serialconn-java$ java -jar Talk.jar 	

Per esporatre il programma una vlota compilato come sopra, copiare in una directory il file Talk.jar e la directory con il file di libreria: lib/jscc-2.8.0.jar

Si avara' una struttura di lavoro :

	workdir
	|-Talk.jar
	L_ lib/
		L jscc-2.8.0.jar
				
Aprire un terminale in working dir ed eseguire:

 		`  workingdir$ java -jar target/Talk.jar`
			

#### Utenti Linux
  
 
 In caso di errori con la connessione alla porta verificare i privilegi di accesso;
 Aggiungere l'utente (non root)  al gruppo dialout :
	
	$ sudo  usermod -aG dialout user

  Assegnare i privilegi con chmod 
			
	$sudo chmod 666 /dev/ttyUSB0
 
 
