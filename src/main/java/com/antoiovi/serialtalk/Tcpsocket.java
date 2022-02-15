package com.antoiovi.serialtalk;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Tcpsocket implements Runnable {

	private Display display;
	String text = null;
	private Socket socket;

	private volatile Thread curentThread;
	private volatile boolean threadSuspended = false;
	private PrintWriter writer;
	private OutputStream outputstream;

	public Tcpsocket(Socket socket,Display display) {
		super();
		this.socket = socket;
		this.display=display;

	}

	public synchronized String getText() {

		return text;
	}

	public synchronized void setText(String text) {
		logdebug("Settext in socket..");

		this.text = text;
	}

	public synchronized void closeConnection() {
		// These two line are to stop waiting if it is waiting
		threadSuspended = false;
		curentThread = null;

		if (writer != null)
			writer.close();

		try {
			logdebug("closeServer() " + "output closing...");
			if (outputstream != null)
				outputstream.close();
		} catch (IOException e2) {
			logdebug("closeServer() " + "Exception closing output ...");
		} finally {
			logdebug("closeServer() " + "socket closing...");
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
		}
		notify();
	}

	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		curentThread = thisThread;
		try {
			logdebug("opening outputstream");

			outputstream = socket.getOutputStream();
			logdebug("outptstream opend");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		writer = new PrintWriter(outputstream, true);

		this.display.display("Tco conn   estabilished...");
		logdebug("Inizio while loop...");
		this.setText(null);
		while (curentThread == thisThread) {
			try {
				//logdebug("Test inputstream...");
				outputstream.write(0);
				//logdebug("output stream ok..");

				Thread.sleep(200);
				synchronized (this) {
					while (threadSuspended && curentThread == thisThread) {
						logdebug("wait");
						this.display.display("Tcp Connecion  suspended.");
						wait();
						this.display.display("Tcp Connecion  restored.");
					}
					// System.out.println("Stop waiting,running....");
					if (getText() != null) {
						writer.println(getText());
						setText(null);
					}
				}

			} catch (IOException | InterruptedException e1) {
				logdebug("output stream Not ok..or thread error..");
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
				// TODO Auto-generated catch block
				e1.printStackTrace();
				break;
			}

		} // while blinker==thisThread
		logdebug("Fine while loop...");
		logdebug("currentThread=null");
		this.display.display("Tcp Connecion closed.");
	}// run()

	void logdebug(String s) {
		System.out.println(s);
	}

}
