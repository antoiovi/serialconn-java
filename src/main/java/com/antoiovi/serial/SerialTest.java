/**
 * Questa classe è stata realizzata adattando alle proproe esigenze la classe processin.app.Serial
 * che si trova nella realizzazione dell'ide Arduino con licenza GNU
 * Le classi liberamentre estratte sono Serial.java, SerialException.java, SerialRead.java
 * 
 * Utilizza la la libreria jssc per connetersi ad una porta seriale e ricevere ed inviare dati ad essa;
 * 
 * Da utilizzare per scopo didattico per mostrare la possibilità di connessione tramite java ad una 
 * porta seriale, porta usb, e quindi anche Arduino utilizzando la librearia jssc
 * 
 * Sito di riferimento www.antoiovi.com; www.worpress.antoiovi.com
 * Antonello Iovino 
 * 03/2019
 * 
 */


package com.antoiovi.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
 
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

import com.antoiovi.SerialException;
import com.antoiovi.SerialNotFoundException;
 

public class SerialTest implements SerialPortEventListener {

  private SerialPort port;

  private CharsetDecoder bytesToStrings;
  private static final int IN_BUFFER_CAPACITY = 128;
  private static final int OUT_BUFFER_CAPACITY = 128;
  private ByteBuffer inFromSerial = ByteBuffer.allocate(IN_BUFFER_CAPACITY);
  private CharBuffer outToMessage = CharBuffer.allocate(OUT_BUFFER_CAPACITY);
  
  // VDeafult Values:
  String  name="/dev/ttyUSB0";
  int baudRate =9600;
  int parity=SerialPort.PARITY_NONE;
  int databits=8;
  double stopbit =1.0; 
  boolean RTS=true;
  boolean DTR=true;
  
  
  public static void main(String[] args) {
		String name;
		int rate;
	   for(int x=0;x<args.length;x++) {
		if(args[x].equals("-p")){
			name=args[x+1];
		}else if(args[x].equals("-r")) {
			rate=Integer.valueOf(args[x+1]);
		}
	  }
		try {
			if(args.length==0) {
				System.out.println("Inizilize port with default values: ");
				SerialTest serialtest=new SerialTest();
			}
		} catch (SerialException e) {
			System.out.println("Can't initialize  port : \n closing app.");
			e.printStackTrace();
			System.exit(0);
		
		}
	}

  
  
/**
 * Inizzializza con parametri di default
 * @throws SerialException
 */
  public SerialTest() throws SerialException {
	  init(name, baudRate, parity,  databits,  stopbit,  RTS, DTR);
  }
/**
 *   Inizzializza con parametri di default tranne Nome Porta
 * @param iname
 * @throws SerialException
 */
  public SerialTest(String iname) throws SerialException {
	  init(iname, baudRate, parity,  databits,  stopbit,  RTS, DTR);
  }

/**
 * 
 * @param iname
 * @param irate
  
 * @throws SerialException
 */
public SerialTest(String iname, int irate) throws SerialException {
		init(iname, irate,  parity,  databits,  stopbit,  RTS, DTR);
    }


/**
   * Inzzializza la porta
   * @throws SerialException
   */
  private void init(String iname, int irate, int parityNone, int idatabits, double stopbit, boolean setRTS, boolean setDTR) throws SerialException 
 {

	  bytesToStrings = StandardCharsets.UTF_8.newDecoder()
              .onMalformedInput(CodingErrorAction.REPLACE)
              .onUnmappableCharacter(CodingErrorAction.REPLACE)
              .replaceWith("\u2e2e");

	    int parity = SerialPort.PARITY_NONE;
	    if (parityNone == 'E') parity = SerialPort.PARITY_EVEN;
	    if (parityNone == 'O') parity = SerialPort.PARITY_ODD;

	    int stopbits = SerialPort.STOPBITS_1;
	    if (stopbit == 1.5f) stopbits = SerialPort.STOPBITS_1_5;
	    if (stopbit == 2) stopbits = SerialPort.STOPBITS_2;

	    try {
	      port = new SerialPort(iname);
	      port.openPort();
	      boolean res = port.setParams(irate, idatabits, stopbits, parity, setRTS, setDTR);
	      if (!res) {
	        System.err.println(String.format("Error while setting serial port parameters: %d %d %d %f}",
	                                  irate, parityNone, idatabits, stopbit));
	      }
	      port.addEventListener(this);
	    } catch (SerialPortException e) {
	      if (e.getPortName().startsWith("/dev") && SerialPortException.TYPE_PERMISSION_DENIED.equals(e.getExceptionType())) {
	        throw new SerialException(String.format("Error opening serial port %s. ", iname));
	      }
	      throw new SerialException(String.format("Error opening serial port %s ", iname), e);
	    }
    if (port == null) {
      throw new SerialNotFoundException(String.format("Serial port %s not found. ", iname));
    }
  
	    if (port == null) {
	      throw new SerialNotFoundException(String.format("Serial port %s not found. ", iname));
	    }
	  
  }
  
  /**
   * Chiude la porta e libera le risorse
   * @throws IOException
   */

  public void dispose() throws IOException {
    if (port != null) {
      try {
        if (port.isOpened()) {
          port.closePort();  // close the port
        }
      } catch (SerialPortException e) {
        throw new IOException(e);
      } finally {
        port = null;
      }
    }
  }

 // @Override
   /**
   * Resta in ascolto della porta e riceve gli eventi;
   * Trasmette le stringhe lette  al metodo   message(char[] chars, int length) 
   */
  public synchronized void serialEvent(SerialPortEvent serialEvent) {
	  log("Serial event ...");
	  if (serialEvent.isRXCHAR()) {
    	log("Serial event isRXCHAR");
      try {
        byte[] buf = port.readBytes(serialEvent.getEventValue());
       
        log("serialEventGetValue= "+String.valueOf(buf));
        int next = 0;
        while(next < buf.length) {
          while(next < buf.length && outToMessage.hasRemaining()) {
            int spaceInIn = inFromSerial.remaining();
            int copyNow = buf.length - next < spaceInIn ? buf.length - next : spaceInIn;
            inFromSerial.put(buf, next, copyNow);
            next += copyNow;
            inFromSerial.flip();
            bytesToStrings.decode(inFromSerial, outToMessage, false);
            inFromSerial.compact();
          }
          outToMessage.flip();
          log("outToMessage= "+String.valueOf(outToMessage));
          if(outToMessage.hasRemaining()) {
            char[] chars = new char[outToMessage.remaining()];
            outToMessage.get(chars);
            message(chars, chars.length);
          }
          outToMessage.clear();
        }
      } catch (SerialPortException e) {
        errorMessage("serialEvent", e);
      }
    }
  }

  /**
   * pRINT READED LINE 
   * @param chars
   * @param length
   */
  protected void message(char[] chars, int length) {
		String line = String.valueOf(chars);
		System.out.println(line);  
  }



  /**
   * This will handle both ints, bytes and chars transparently.
   */
  public void write(int what) {  // will also cover char
    try {
      port.writeInt(what & 0xff);
    } catch (SerialPortException e) {
      errorMessage("write", e);
    }
  }


  public void write(byte bytes[]) {
    try {
      port.writeBytes(bytes);
    } catch (SerialPortException e) {
      errorMessage("write", e);
    }
  }


  /**
   * Write a String to the output. Note that this doesn't account
   * for Unicode (two bytes per char), nor will it send UTF8
   * characters.. It assumes that you mean to send a byte buffer
   * (most often the case for networking and serial i/o) and
   * will only use the bottom 8 bits of each char in the string.
   * (Meaning that internally it uses String.getBytes)
   * <p>
   * If you want to move Unicode data, you can first convert the
   * String to a byte stream in the representation of your choice
   * (i.e. UTF8 or two-byte Unicode data), and send it as a byte array.
   */
  public void write(String what) {
    write(what.getBytes());
  }

  
  

  /**
   */
  private static void errorMessage(String where, Throwable e) {
    System.err.println(String.format("Error inside Serial.%s()",where));
    e.printStackTrace();
  }
 
  
  void log(String msg){
	//  System.out.println(msg);
  }
  
}
