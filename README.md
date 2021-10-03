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
 ![alt text]( https://github.com/antoiovi/serialconn-java/blob/master/Talk-1.2.png?raw=true)
  
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
		
		
		serialconn-java$ java -jar target/Talk-1.1.jar 	

Per esporatre il programma una volta compilato come sopra, copiare in una directory il file Talk-x.x.jar la directory con il file di libreria: lib/jscc-2.8.0.jar

Si avara' una struttura di lavoro :

	workdir
	|-Talk-x.xjar
	L_ lib/
		L jscc-2.8.0.jar
				
Aprire un terminale in working dir ed eseguire:

 		`  workingdir$ java -jar Talk-x.x.jar`
			

Per esportare il programma creare un file tar ( per utenti Linux ) ed un file zip (per utenti Windows)
con la struttura dei file indicata sopra .


'E presente uno script per utenti Linux che crea automaticamente i file zip e tar.

	Talk_zip_tar-x.x.sh


#### Utenti Linux
  
 
 In caso di errori con la connessione alla porta verificare i privilegi di accesso;
 Aggiungere l'utente (non root)  al gruppo dialout :
	
	$ sudo  usermod -aG dialout user

  Assegnare i privilegi con chmod 
			
	$ sudo chmod 666 /dev/ttyUSB0
 
#### Revisioni

### Versione 1 revisione 1
 	
 
 * Sono visualizzati solo i nomi delle porte effettivamente presenti sul sistema
 * Vengono resi opzionali le configurazione avanzate
 * Risolto alcuni piccoli bachi
 
### Versione 1 revisione 2

 * Aggiunta la possibilita' di cambiare l'estensione del nome del file
 * Aggiunte le opzioni di fine linea nelle stringhe di invio e di arrivo
 
 ### Versione 1 revisione 3
 
 * Messo versione java 1.7
 * Aggiunto l'opzione per nascondere i pulsanti 
 * Aggiunto properties

