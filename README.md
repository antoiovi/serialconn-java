# serialconn-java
Read and write data to serial port devices (also  Arduino)

La mia pagina di riferimento dove viene spiagto il codice :
  www.antoiovi.com/java/java-leggere-dati-da-porta-seriale-usb

 Programma per provare la libreria jscc (https://github.com/scream3r/java-simple-serial-connector ) per la lettura  e scrittura dati da uan porta seriale porta seriale (USB, Arduino, ecc).
 
Branch serialtalk :
    
 ° L'applicazione di prova App.java, senza interfaccia grafica 
 
 ° La classe Serial.java utilizza la libreria jssc per connettersi ad una porta seriale e ricevere ed inviare dati ad essa;
 
 
 
 Da utilizzare per scopo didattico per mostrare la possibilità di connessione tramite java ad una 
 porta seriale, porta usb, e quindi anche Arduino utilizzando la librearia jssc
  
 Compilazione con maven : 
 			
	serialconn-java$ mvn clean package
 
 
 
 Esecuzione :
		
		serialconn-java$ java -jar target/serial-1.jar 		
		

Per esportare l'applicazione creare una cartella con il file serial-1.jar e una directory
	 /lib con dentro il file jssc-2.8.0.jar (che si trova nella cartella /target/lib )
	 
	 

#### Utenti Linux
  
 
 In caso di errori con la connessione alla porta verificare i privilegi di accesso;
 Aggiungere l'utente (non root)  al gruppo dialout :
	
	$ sudo  usermod -aG dialout user

  Assegnare i privilegi con chmod 
			
	$sudo chmod 666 /dev/ttyUSB0
 
 
