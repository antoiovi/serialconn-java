# serialconn-java
Read and write data to serial port devices (also  Arduino)

La mia pagina di riferimento dove viene spiagto il codice :
  www.antoiovi.com/java/java-leggere-dati-da-porta-seriale-usb

 Programma per provare la libreria jscc (https://github.com/scream3r/java-simple-serial-connector ) per la lettura  e scrittura dati da uan porta seriale porta seriale (USB, Arduino, ecc).
 
 Sono presenti : 
    
 ° L'applicazione di prova App.java, modificabile per provare diverse configurazioni
 
 ° La classe Serial.java utilizza la libreria jssc per connettersi ad una porta seriale e ricevere ed inviare dati ad essa;
 
  ° L'applicazione Talk.java, che presenta una interfaccia grafica per modificare 
  	le configurazioni di connessione eper visualizzare i dati trasmessi e ricevuti a/da 
  	la porta seriale. Questa applicazione puo` essere utilizzata per testare gli sketch di Arduino.
 
 
 Da utilizzare per scopo didattico per mostrare la possibilità di connessione tramite java ad una 
 porta seriale, porta usb, e quindi anche Arduino utilizzando la librearia jssc
  
 Compilazione con maven per l'applicazione di prova : 
 			
	serialconn-java$ mvn   package -P readserial
 
 Compilazione con maven per l'applicazione con gui Talk : 
 		
	serialconn-java$ mvn   package -P talk
 		
 Esecuzione :
		
		serialconn-java$ java -jar target/Readserial.jar 	
		
		serialconn-java$ java -jar target/Talk.jar 	
			

#### Utenti Linux
  
 
 In caso di errori con la connessione alla porta verificare i privilegi di accesso;
 Aggiungere l'utente (non root)  al gruppo dialout :
	
	$ sudo  usermod -aG dialout user

  Assegnare i privilegi con chmod 
			
	$sudo chmod 666 /dev/ttyUSB0
 
 
