package com.antoiovi.serialtalk;

import java.io.*;
import java.net.*;
import java.util.Date;

/**
 *
 */
public class TcpServer implements Runnable {
	int port = 6868;
	Display display;
	/**
	 * 
	 * @param port
     * @param display to send message to calling services
	 */
	public TcpServer(int port,Display display) {
		this.port = port;
		this.display=display;
	}

	String text = null;
	private Socket socket;

	private volatile Thread curentThread;
	private volatile boolean threadSuspended = false;
	private ServerSocket server = null;
	private PrintWriter writer;
	private OutputStream outputstream;

	
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

	public synchronized void closeServer() {
		// These two line are to stop waiting if it is waiting
		threadSuspended = false;
		curentThread = null;

		if (writer != null)
			writer.close();

		try {
			logdebug("closeServer() "+"output closing...");
			if (outputstream != null)
				outputstream.close();
		} catch (IOException e2) {
			logdebug("closeServer() "+"Exception closing output ...");
		} finally {
			logdebug("closeServer() "+"socket closing...");
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					logdebug( "closeServer() "+"Closing serverSocket ...");
					if (server != null)
						try {
							server.close();
						} catch (IOException e) {
							logdebug("Exception closing socket ...");
						}
				}
		}
		notify();
	}

	public synchronized void printText(String str) {
		setText(str);
	}

	public void run() {
		BufferedReader inputStream =null;
		writer = null;
		System.out.println("run");
		/**
		 * To stop this line , if no connecticon is opened call
		 */
		display.display("Listening on port on port "+ port);

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			server = serverSocket;
			// System.out.println("Server is listening on port " + port);
			// System.out.println("serverSocket hascode :" + serverSocket.hashCode());
			// System.out.println("In attesa di client che si connetta ...");
			socket = serverSocket.accept();
			// System.out.println("New client connected");
			// System.out.println("Socket hascode :" + socket.hashCode());
			outputstream = socket.getOutputStream();
			// System.out.println("Outputstream created!");

			writer = new PrintWriter(outputstream, true);
			
			// Buffer input stream
            inputStream = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
			logdebug("Writer created!");

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		/**
		 * For pause, start stop the thread see:
		 * https://docs.oracle.com/javase/7/docs/technotes/guides/concurrency/threadPrimitiveDeprecation.html
		 */
		Thread thisThread = Thread.currentThread();
		curentThread = thisThread;

		if (socket == null)
			curentThread = null;
		this.display.display("Tco conn on port "+port+" estabilished...");
		logdebug("Inizio while loop...");
		// clean the buffer after new connection..
		this.setText(null);
		while (curentThread == thisThread) {
			/*
			 * try { logdebug("Test inputstream...");
			 * 
			 * if(inputStream.read() == -1){ socket.close(); } } catch (IOException e1) { //
			 * TODO Auto-generated catch block e1.printStackTrace(); }
			 */
			//logdebug("thread=ok");
			try {
				Thread.sleep(200);
				synchronized (this) {
					while (threadSuspended && curentThread == thisThread) {
						logdebug("wait");
						this.display.display("Tcp Connecion on port "+port+" suspended.");
						wait();
						this.display.display("Tcp Connecion on port "+port+" restored.");

					}
					// System.out.println("Stop waiting,running....");
					if (getText() != null) {
						writer.println(getText());
						setText(null);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				display.display("Exception in run() "+ port);

			}
		} // while blinker==thisThread
		logdebug("Fine while loop...");
		logdebug("currentThread=null");
		this.display.display("Tcp Connecion on port "+port+" closed.");
	}// run()

	void logdebug(String s) {
		System.out.println(s);
	}
}
