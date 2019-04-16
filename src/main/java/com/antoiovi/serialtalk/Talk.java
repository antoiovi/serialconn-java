package com.antoiovi.serialtalk;

import jssc.SerialPort;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import java.awt.GridLayout;

import javax.swing.JSlider;

import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.antoiovi.serial.LineRecived;
import com.antoiovi.serial.Serial;
import com.antoiovi.serial.SerialException;

import javax.swing.event.ChangeEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;

//Added to copy to clipboard jpanel content
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

public class Talk extends JFrame implements ActionListener, LineRecived, ChangeListener  {

	Pair[] baud_rates = { new Pair(String.valueOf(SerialPort.BAUDRATE_110), SerialPort.BAUDRATE_110),
			new Pair(String.valueOf(SerialPort.BAUDRATE_300), SerialPort.BAUDRATE_300),
			new Pair(String.valueOf(SerialPort.BAUDRATE_600), SerialPort.BAUDRATE_600),
			new Pair(String.valueOf(SerialPort.BAUDRATE_1200), SerialPort.BAUDRATE_1200),
			new Pair(String.valueOf(SerialPort.BAUDRATE_4800), SerialPort.BAUDRATE_4800),
			new Pair(String.valueOf(SerialPort.BAUDRATE_9600), SerialPort.BAUDRATE_9600),
			new Pair(String.valueOf(SerialPort.BAUDRATE_14400), SerialPort.BAUDRATE_14400),
			new Pair(String.valueOf(SerialPort.BAUDRATE_19200), SerialPort.BAUDRATE_19200),
			new Pair(String.valueOf(SerialPort.BAUDRATE_38400), SerialPort.BAUDRATE_38400),
			new Pair(String.valueOf(SerialPort.BAUDRATE_115200), SerialPort.BAUDRATE_115200),
			new Pair(String.valueOf(SerialPort.BAUDRATE_128000), SerialPort.BAUDRATE_128000),
			new Pair(String.valueOf(SerialPort.BAUDRATE_256000), SerialPort.BAUDRATE_256000) };

	String[] port_names = { "/dev/tty0", "/dev/tty1", "/dev/tty2", "/dev/ttyUSB0", "/dev/ttyUSB1", "/dev/ttyUSB2" };

	Integer[] data_bits = { SerialPort.DATABITS_5, SerialPort.DATABITS_6, SerialPort.DATABITS_7,
			SerialPort.DATABITS_8 };

	Double[] stop_bits = { (double) SerialPort.STOPBITS_1, (double) SerialPort.STOPBITS_2,
			(double) SerialPort.STOPBITS_1_5 };

	String[] parity_opptions = { "NONE", "ODD", "EVEN", "MARK", "SPACE" };

	Pair[] parity_options = { new Pair("NONE", SerialPort.PARITY_NONE), new Pair("EVEN", SerialPort.PARITY_EVEN),
			new Pair("MARK", SerialPort.PARITY_MARK), new Pair("SPACE", SerialPort.PARITY_SPACE) };

	public static final int PARITY_NONE = 0;
	public static final int PARITY_ODD = 1;
	public static final int PARITY_EVEN = 2;
	public static final int PARITY_MARK = 3;
	public static final int PARITY_SPACE = 4;

	// Valori di deafult :
	String name = "/dev/ttyUSB0";
	int baudRate = 9600;
	int parity = SerialPort.PARITY_NONE;
	int databits = 8;
	double stopBits = 1.0; // stop_bits
	boolean RTS = true;
	boolean DTR = true;
	private JComboBox comboBoxPortname;
	private JComboBox comboBoxBaudrate;
	private JComboBox comboBoxParityBits;
	private JComboBox comboBoxDataBits;
	private JComboBox comboBoxStopBits;
	private JCheckBox chckbxRTS;
	private JCheckBox chckbxDTR;
	private JPanel panel_1;
	private JButton btnTestConnection;

	static Talk app;
	private JTextArea textAreaControl;
	private JButton btnClearOutput;
	private JScrollPane scrollPane;
	private JPanel panel_2;
	private JButton btnA;
	private JButton btnB;
	private JButton btnC;
	private JButton btnD;
	private JToggleButton tglbtnOnoff1;
	private JToggleButton tglbtnOnoff2;
	private JToggleButton tglbtnOnoff3;
	private JToggleButton tglbtnOnoff4;
	private JPanel panel_3;
	private JPanel panel_4;
	private JSlider slider_1;
	private JPanel panel_5;
	private JLabel lblPotenziometro;
	private JLabel lblPotenziometro_1;

	private JTextArea textAreaSerial;

	private JButton btnStop;
	private JPanel panel_6;
	private JScrollPane scrollPane_1;
	private JSlider slider_2;

	Serial serial;
	String portname;
	private JPopupMenu popupMenu;
	private JMenuItem mntmCopyAllToClipboard;

	private JMenuItem mntmCopySelToClipboard;
	static final String COPY_SEL_TO_CLIPBOARD="CopySelToClipboard";
	static final String COPY_ALL_TO_CLIPBOARD="CopyAllToClipboard";
	static final String SEND_STRING_TO_SERIAL="Send to serial";

	private JPanel panel_7;
	private JButton btnSendString;
	private JTextField textToSend;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Talk frame = new Talk();
					app = frame;
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Talk() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if (serial != null)
						serial.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setBounds(100, 100, 550, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		JLabel lblPortName = new JLabel("Port Name");
		comboBoxPortname = new JComboBox(new DefaultComboBoxModel(port_names));
		panel.add(comboBoxPortname);
		JLabel lblNewLabel = new JLabel("Baud Rate");
		comboBoxBaudrate = new JComboBox(new DefaultComboBoxModel(baud_rates));
		panel.add(comboBoxBaudrate);

		JLabel lblParity = new JLabel("Parity");
		panel.add(lblParity);

		comboBoxParityBits = new JComboBox(new DefaultComboBoxModel(parity_options));
		panel.add(comboBoxParityBits);

		JLabel lblDataBits = new JLabel("Data bits");
		panel.add(lblDataBits);
		comboBoxDataBits = new JComboBox(new DefaultComboBoxModel(data_bits));
		panel.add(comboBoxDataBits);

		JLabel lblStopBits = new JLabel("Stop bits");
		panel.add(lblStopBits);
		comboBoxStopBits = new JComboBox(new DefaultComboBoxModel(stop_bits));
		panel.add(comboBoxStopBits);

		chckbxRTS = new JCheckBox("RTS");
		panel.add(chckbxRTS);

		chckbxDTR = new JCheckBox("DTR");
		panel.add(chckbxDTR);

		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		btnTestConnection = new JButton("Test Connection");
		btnTestConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.testConnection();
			}
		});
		panel_1.add(btnTestConnection);

		btnClearOutput = new JButton("Clear output");
		btnClearOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.clearOutput();
			}
		});
		panel_1.add(btnClearOutput);

		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.Stop();
			}
		});
		panel_1.add(btnStop);

		panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new GridLayout(3, 3, 0, 0));

		btnA = new JButton("A");
		btnA.addActionListener(this);
		panel_3.add(btnA);
		btnB = new JButton("B");
		btnB.addActionListener(this);
		panel_3.add(btnB);
		btnC = new JButton("C");
		btnC.addActionListener(this);
		panel_3.add(btnC);
		btnD = new JButton("D");
		panel_3.add(btnD);
		btnD.addActionListener(this);

		panel_4 = new JPanel();
		panel_2.add(panel_4);
		panel_4.setLayout(new GridLayout(3, 2, 0, 0));

		tglbtnOnoff1 = new JToggleButton("Switch 1");
	//	tglbtnOnoff1.setSelectedIcon(new ImageIcon(Talk.class.getResource("/com/antoiovi/serialtalk/ON.png")));
	//	tglbtnOnoff1.setIcon(new ImageIcon(Talk.class.getResource("/com/antoiovi/serialtalk/OFF.png")));
 		//tglbtnOnoff1.setIcon(new ImageIcon(getClass().getResource("/com/antoiovi/icons/switch-off-icon.png")));
		
		tglbtnOnoff1.addActionListener(this);
		tglbtnOnoff1.setActionCommand("Switch_1");
	//	 tglbtnOnoff1.setIcon(new ImageIcon(Talk.class.getResource("/com/antoiovi/icons/switch-on-icon.png")));
		// tglbtnOnoff1.setSelectedIcon(new ImageIcon(Talk.class.getResource("../icons/switch-on-icon.png")));
		panel_4.add(tglbtnOnoff1);

		tglbtnOnoff2 = new JToggleButton("Switch  2");
		
		 
		tglbtnOnoff2.setActionCommand("Switch_2");
		// tglbtnOnoff2.setIcon(new
		// ImageIcon(Talk.class.getResource("../icons/switch-off-icon.png")));
		// tglbtnOnoff2.setSelectedIcon(new
		// ImageIcon(Talk.class.getResource("../icons/switch-on-icon.png")));
		tglbtnOnoff2.addActionListener(this);
		panel_4.add(tglbtnOnoff2);

		tglbtnOnoff3 = new JToggleButton("Switch 3");
		
		tglbtnOnoff3.setActionCommand("Switch_3");
		// tglbtnOnoff3.setIcon(new
		// ImageIcon(Talk.class.getResource("../icons/switch-off-icon.png")));
		// tglbtnOnoff3.setSelectedIcon(new
		// ImageIcon(Talk.class.getResource("../icons/switch-on-icon.png")));
		tglbtnOnoff3.addActionListener(this);
		panel_4.add(tglbtnOnoff3);
		tglbtnOnoff4 = new JToggleButton("Switch 4");
	
		tglbtnOnoff4.setActionCommand("Switch_4");
		// tglbtnOnoff4.setIcon(new
		// ImageIcon(Talk.class.getResource("../icons/switch-off-icon.png")));
		// tglbtnOnoff4.setSelectedIcon(new
		// ImageIcon(Talk.class.getResource("../icons/switch-on-icon.png")));
		tglbtnOnoff4.addActionListener(this);
		panel_4.add(tglbtnOnoff4);

		panel_5 = new JPanel();
		panel_2.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));

		lblPotenziometro = new JLabel("Trimmer 1");
		panel_5.add(lblPotenziometro);

		slider_1 = new JSlider();
		slider_1.setName("TRIM1");
		slider_1.addChangeListener(this);
		panel_5.add(slider_1);

		lblPotenziometro_1 = new JLabel("Trimmer 2");
		panel_5.add(lblPotenziometro_1);

		slider_2 = new JSlider();
		slider_2.setName("TRIM2");
		slider_2.addChangeListener(this);

		panel_5.add(slider_2);
		
		panel_7 = new JPanel();
		panel_5.add(panel_7);
		panel_7.setLayout(new GridLayout(4, 1, 0, 0));
		
		btnSendString = new JButton("Send string");
		btnSendString.addActionListener(this);
		btnSendString.setActionCommand(SEND_STRING_TO_SERIAL);
		panel_7.add(btnSendString);
		
		textToSend = new JTextField();
		panel_7.add(textToSend);
		textToSend.setColumns(10);
		
		
		panel_6 = new JPanel();
 		getContentPane().add(panel_6, BorderLayout.CENTER);
 		panel_6.setLayout(new GridLayout(2, 1, 0, 0));
 		
 		textAreaControl = new JTextArea();
 		textAreaControl.setEditable(false);
 		
 		scrollPane = new JScrollPane(textAreaControl);
 		panel_6.add(scrollPane);
 		scrollPane.setAutoscrolls(true);
 		
 		textAreaSerial = new JTextArea();
		textAreaSerial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
						app.popupMenu.show(textAreaSerial, e.getX(), e.getY());
				}
			}
		});
 		scrollPane_1 = new JScrollPane(textAreaSerial);
 		panel_6.add(scrollPane_1);
 		scrollPane_1.setAutoscrolls(true);

 		//PopUp menu to copy serial data to clipboard 		
 		mntmCopyAllToClipboard = new JMenuItem("Copy all");
 		mntmCopyAllToClipboard.addActionListener(this);
 		mntmCopyAllToClipboard.setActionCommand(COPY_ALL_TO_CLIPBOARD);
 		mntmCopySelToClipboard = new JMenuItem("Copy selected");
 		mntmCopySelToClipboard.addActionListener(this);
 		mntmCopySelToClipboard.setActionCommand(COPY_SEL_TO_CLIPBOARD);
 		
 		popupMenu = new JPopupMenu();
 		popupMenu.add(mntmCopyAllToClipboard);
 		popupMenu.add(mntmCopySelToClipboard);

 	    popupMenu.addPopupMenuListener(new PopupPrintListener());
 		
 	    init();

	}

	private void init() {
		comboBoxBaudrate.setSelectedItem(baud_rates[5]);
		comboBoxPortname.setSelectedItem(port_names[3]);
		comboBoxDataBits.setSelectedItem(SerialPort.DATABITS_8);
		comboBoxParityBits.setSelectedItem(parity_options[PARITY_NONE]);
		chckbxDTR.setSelected(true);
		chckbxRTS.setSelected(true);
		this.appendMessage("Author : antoiovi 2019 ");
	}

	private void testConnection() {
		boolean test = false;

		if (serial != null) {
			try {
				if (serial.portIsOpened())
					test = true;
				app.appendMessage("Serial Port already opened :" + portname);

			} catch (Exception e) {
				test = false;
			}
		}
		if (test) {
			app.appendMessage("Serial port is openend :" + portname);
		} else {
			app.appendMessage("Serial port NON opened:");
			initConnection();
		}

	}

	private void initConnection() {
		String name;
		int baudrate;
		int parity;
		int databits;
		double stopbit;
		boolean setRTS;
		boolean setDTR;

		try {
			name = (String) comboBoxPortname.getSelectedItem();
			Pair p = (Pair) comboBoxBaudrate.getSelectedItem();
			baudrate = p.value.intValue();

			p = (Pair) comboBoxParityBits.getSelectedItem();
			parity = p.value.intValue();

			databits = (Integer) comboBoxDataBits.getSelectedItem();
			stopbit = (Double) comboBoxStopBits.getSelectedItem();

			setRTS = chckbxDTR.isSelected();
			setDTR = chckbxRTS.isSelected();

			serial = new Serial(name, baudrate, parity, databits, stopbit, setRTS, setDTR);
			// serial = new SerialReadT( name);
			serial.setLineRecived(app);
			// throws SerialException {

			if (serial.portIsOpened()) {

				portname = name;
				app.appendMessage("Porta aperta :");
				TimeUnit.SECONDS.sleep(1);
				app.appendMessage("Lettura dati ... :");

			} else
				System.out.println("Porta NON e aperta !!!");
		} catch (SerialException e) {
			app.appendMessage("Serial Exception :");
			app.appendMessage(e.getMessage());
			// e.printStackTrace();
		} catch (IOException e) {
			app.appendMessage("IO Exception :");
			app.appendMessage(e.getMessage());
			// e.printStackTrace();
		} catch (InterruptedException e) {
			// Timeunit.Seconds.sleep(..) exception
			app.appendMessage("InterruptedException :");
			app.appendMessage(e.getMessage());
			// e.printStackTrace();
		}
	}

	void Stop() {
		try {
			serial.dispose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void appendMessage(String str) {
		textAreaControl.append(str);
		textAreaControl.append("\n");

	}

	void printLineRecived(String str) {
		textAreaSerial.append(str);
		textAreaSerial.append("\n");
		//Added to force Autoscroll of JPanel
		textAreaSerial.selectAll();

	}

	void clearOutput() {
		textAreaControl.setText("");
		textAreaSerial.setText("");

	}

	void writeToSerial(String str) {
		try {
			serial.write(str);
			appendMessage("Scritto con succcesso");

		} catch (Exception e) {
			appendMessage("Errore scrivendo sulla porta");
		}
	}

	synchronized void readFromSerial(String msg) {
		printLineRecived(msg);

	}

	private class Pair {
		String name;
		Integer value;

		public Pair(String name, Integer value) {
			super();
			this.name = name;
			this.value = value;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	/**
	 * Buttons , ToggleButtons, Popup menu (right mouse click)
	 */
	public void actionPerformed(ActionEvent e) {
		String msg = e.getActionCommand();
		if (msg.equals("Switch_1") || msg.equals("Switch_2") || msg.equals("Switch_3") || msg.equals("Switch_4")) {
			AbstractButton abstractButton = (AbstractButton) e.getSource();
			if (abstractButton.getModel().isSelected())
				msg = msg + "-ON";
			else
				msg = msg + "-OFF";
		app.appendMessage("Write to serial : " + msg);
		writeToSerial(msg);
		}else if
		(     msg.equals(btnA.getActionCommand()) || msg.equals(btnB.getActionCommand()) ||
				msg.equals(btnC.getActionCommand()) ||	msg.equals(btnD.getActionCommand() )
		)
		{
			app.appendMessage("Write to serial : " + msg);
			writeToSerial(msg);
		}
		else if(msg.equals(COPY_ALL_TO_CLIPBOARD)) {
	
			String myString =textAreaSerial.getText();
			StringSelection stringSelection = new StringSelection(myString);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}else if(msg.equals(COPY_SEL_TO_CLIPBOARD)) {
	
			String myString =textAreaSerial.getSelectedText();
			StringSelection stringSelection = new StringSelection(myString);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}else if(msg.equals(SEND_STRING_TO_SERIAL)) {
			
			app.appendMessage("Writing to serial : " + textToSend.getText());
			writeToSerial(textToSend.getText());
		}
	}

	/**
	 * Parametro ricevuto da lettura porta
	 * 
	 * @param line
	 */
	synchronized public void setMessage(String line) {
		readFromSerial(line);
	}

	void log(String msg) {
	//	System.out.println(msg);
	}

	/*
	 * ChangeListener : for Sliders
	 */
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		log(source.getName());
		if (!source.getValueIsAdjusting()) {
			int value = (int) source.getValue();
			String msg=source.getName() + ":" + String.valueOf(value);
			app.appendMessage("Writing to serial : " + msg);
			writeToSerial(msg);
 		}
	}

	  // An inner class to show when popup events occur
	  class PopupPrintListener implements PopupMenuListener {

		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
 			
		}

		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
 			
		}

		public void popupMenuCanceled(PopupMenuEvent e) {
 			
		}
	  
	  }
}
