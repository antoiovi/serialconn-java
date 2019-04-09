package com.antoiovi.serialtalk;


import com.antoiovi.SerialRead;

public class SerialThread   implements Runnable {
   SerialRead serial;
   Talk talk;
	
	public void run() {
       
        try {
           do{

        	   
   			String msg = serial.getFirst();
   			if(msg!=null)
   				talk.readFromSerial(msg);
        	   // Pause for 4 seconds
                Thread.sleep(1000);
                 
            }while(serial.portIsOpened());
   			talk.appendMessage("Comunication closed");
        } catch (InterruptedException e) {
   			talk.appendMessage("Comunication closed");
   			talk.appendMessage(e.getMessage());

        }catch (Exception e) {
   			talk.appendMessage("Comunication closed");
   			talk.appendMessage(e.getMessage());
			
		}
    }
}
