/**
 * Programma per provare la libreria jscc per la lettura dati da porta seriale
 * Serve solo per mostrare l'utilizzo della libreria, che vien richiamata dalla 
 * classe Serial, che ricopre le chiamate alle funzioni della libreia
 * Viene utilizzata la classe Serial che esegue i copitti sulla porta
 * 
 * Viene inzzializata ed aperta la porta
 * vengono lette un certo numero di righe e poi chiude l'applicazione
 * 
 * Compilazione con maven : 
 * 		
 * 		serial$ mvn clean package
 *  
 * Esecuzione :
 *		
 *		serial$ java -jar target/serial-1.jar 		
 *
 * In caso di errori con la connessione alla porta verificare i privilegi di accesso;
 * 
 * 	I dati di default sono :
 * 
  * String  name="/dev/ttyUSB0";
 *  int baudRate =9600;
 *  int parity=SerialPort.PARITY_NONE;
 *  int databits=8;
 *  double d =1.0;
 *  boolean RTS=true;
 *  boolean DTR=true;
 *  
 *  Per usare altri parametri usare :
 * 		  Per campiare solo il nome della porta  
 *  			serial = new SerialRead(name); 
 * 		  Per campiare tutti i parametri :  
 *  			serial = new  Serial(String iname, int irate, int parityNone, 
 *  			int idatabits, double d, boolean setRTS, boolean setDTR);
 *  
 *    
 * 03/2019 Antonello Iovino 
 * Sito di riferimento www.antoiovi.com; www.worpress.antoiovi.com
 *
 */
package com.antoiovi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.antoiovi.serial.LineRecived;
import com.antoiovi.serial.Serial;
import com.antoiovi.serial.SerialException;

public class App implements LineRecived {

	static final String usage = "Usage :\n java -jar serial-1.jar -p PORTNAME -b BAUDRATE\n"
			+ "If no port name or baudrate is given then default values are used;\n" + "Antonello Iovino 2019";

	final Map<String, List<String>> params = new HashMap<>();

	String name = "/dev/ttyUSB0";
	Serial serial;
	int baudrate = 9600;

	public static void main(String[] args) {
		log(usage);
		App p = new App();

		p.arguments(args);
		p.init();
	}

	private void arguments(String[] args) {
		List<String> options = null;
		for (int i = 0; i < args.length; i++) {
			final String a = args[i];
			if (a.charAt(0) == '-') {
				if (a.length() < 2) {
					System.err.println("Error at argument " + a);
					return;
				}
				options = new ArrayList<>();
				params.put(a.substring(1), options);
			} else if (options != null) {
				options.add(a);
			} else {
				System.err.println("Illegal parameter usage");
				return;
			}
		}

		if (params.get("p") != null) {
			List<String> list = params.get("p");
			name = list.get(0);
			log("Port :" + name);

		} else {
			log("Default port :" + name);
		}
		if (params.get("b") != null) {
			List<String> list = params.get("b");
			String s = list.get(0);
			try {
				int x = Integer.valueOf(s);
				baudrate = x;
				log("Baudrate :" + String.valueOf(baudrate));

			} catch (Exception e) {
				log("Use of default baudrate :" + String.valueOf(baudrate));
			}

		} else {
			log("Default baudrate :" + String.valueOf(baudrate));
		}

	}

	static void log(String s) {
		System.out.println(s); // 1

	}

	private void init() {

		try {

			serial = new Serial(name);
			serial.setLineRecived(this);

			if (serial.portIsOpened()) {
				System.out.println("Porta e apera..");
				TimeUnit.SECONDS.sleep(1);
				System.out.println("Lettura dati ..");

				readString();

				System.out.println("Chiusura porta seriale..");
				serial.dispose();

			} else
				System.out.println("Porta NON e aperta !!!");
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// Timeunit.Seconds.sleep(..) exception
			e.printStackTrace();
		}
	}

	/**
	 * Legge un certo numero di righe e poi chiude l'applicazione
	 * 
	 */
	String message = null;

	private void readString() throws InterruptedException {
		int count = 0;
		do {
			TimeUnit.SECONDS.sleep(2);
			if (this.getMessage() == null)
				continue;
			count++;
			System.out.println(message);
			this.setMessage(null);
		} while (count < 10);

	}

	/**
	 * Recive message from serial
	 */
	public void setMessage(String line) {
		this.message = line;
	}

	public String getMessage() {
		return message;
	}

}
