package com.antoiovi.serialtalk;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TcpServer implements Runnable {
	int port = 6868;
	Display display;
	List<Tcpsocket> socketlist;

	ServerSocket serverSocket = null;

	/**
	 * 
	 * @param port
	 * @param display to send message to calling services
	 */
	public TcpServer(int port, Display display) {
		this.port = port;
		this.display = display;
		socketlist = new ArrayList<Tcpsocket>();
	}

	String text = null;

	private volatile Thread curentThread;
	private volatile boolean threadSuspended = false;
	private ServerSocket server = null;

	public synchronized ServerSocket getServer() {
		return server;
	}

	public synchronized String getText() {
		return text;
	}

	public synchronized void setText(String text) {
		this.text = text;
	}

	public synchronized void pauseServer() {
		threadSuspended = true;
		this.setText(null);
	}

	/**
	 * Resume after pause
	 */
	public synchronized void resumeServer() {
		// If not paused do nothing
		if (threadSuspended == false)
			return;
		this.setText(null);
		threadSuspended = false;
		if (!threadSuspended)
			notify();
	}
	public void closeServer() {
		for (Tcpsocket s : socketlist) {
			s.closeConnection();
		}
	}
	
	
	public synchronized void printText(String str) {
		logdebug("printtsext  " + str);
		for (Tcpsocket s : socketlist) {
			s.setText(str);
		}
	}

	
	public void run() {
		System.out.println("run");
		/**
		 * To stop this line , if no connecticon is opened call
		 */
		display.display("Listening on port on port " + port);

		/**
		 * For pause, start stop the thread see:
		 * https://docs.oracle.com/javase/7/docs/technotes/guides/concurrency/threadPrimitiveDeprecation.html
		 */
		Thread thisThread = Thread.currentThread();
		curentThread = thisThread;
		logdebug("Inizio while loop...");

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (serverSocket != null) {
			try {
				this.display.display("Waiting  on" + port + " ....");
				Socket socket = serverSocket.accept();
				Tcpsocket conn = new Tcpsocket(socket, display);
				Thread thread = new Thread(conn);
				thread.start();
				socketlist.add(conn);
				this.display.display("Connection  on" + port + " estabilished...");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}

		logdebug("Fine while loop...");
		logdebug("currentThread=null");
		this.display.display("Tcp Connecion on port " + port + " closed.");
	}// run()

	void logdebug(String s) {
		System.out.println(s);
	}

}
