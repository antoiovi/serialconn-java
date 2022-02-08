package com.antoiovi.serialtalk;

/**
 * 
 * @author antoiovi for test in linux use : $ netcat 127.0.0.1 6868
 *
 */
public class Tcpconnection {

	int port = 6868;
	String msg = "";
	Display display;
	private TcpServer server = null;

	public synchronized String getMsg() {
		return msg;
	}

	/**
	 * 
	 * @param port
	 * @param display : used to send message to calling services
	 */
	public Tcpconnection(int port, Display display) {
		super();
		this.port = port;
		this.display = display;
		server = new TcpServer(port, display);

		Thread thread = new Thread(server);
		thread.start();

	}

	public void closeTcpconn() {
		// System.out.println("Button close server pressed..");
		if (server != null)
			server.closeServer();
		else
			System.out.println("Server thread is null..");
	}

	public void print(String str) {
		if (server != null)
			server.printText(str);
	}

}
