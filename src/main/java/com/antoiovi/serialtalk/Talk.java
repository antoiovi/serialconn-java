/**
 * MIT License
*
* Copyright (c) 2019 Antonello IOvino
* 
*Permission is hereby granted, free of charge, to any person obtaining a copy
*of this software and associated documentation files (the "Software"), to deal
*in the Software without restriction, including without limitation the rights
*to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*copies of the Software, and to permit persons to whom the Software is
*furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.antoiovi.serialtalk;

import jssc.SerialPort;
import jssc.SerialPortList;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import java.awt.GridLayout;
import java.awt.ItemSelectable;

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

public class Talk extends JFrame implements ActionListener, LineRecived, ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5012589979023933959L;

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

	String[] port_names;

	Integer[] data_bits = { SerialPort.DATABITS_5, SerialPort.DATABITS_6, SerialPort.DATABITS_7,
			SerialPort.DATABITS_8 };

	Double[] stop_bits = { (double) SerialPort.STOPBITS_1, (double) SerialPort.STOPBITS_2,
			(double) SerialPort.STOPBITS_1_5 };

	String[] parity_opptions = { "NONE", "ODD", "EVEN", "MARK", "SPACE" };

	Pair[] parity_options = { new Pair("NONE", SerialPort.PARITY_NONE), new Pair("EVEN", SerialPort.PARITY_EVEN),
			new Pair("MARK", SerialPort.PARITY_MARK), new Pair("SPACE", SerialPort.PARITY_SPACE) };

	String[] file_extensions = { "dat", "txt", "csv" };
	String[] line_endings = { "No Line Ending", "NewLine", "Carriage Return", "Both NL &CR" };

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

	private JComboBox cboxAppendToMessage;
	private JComboBox cboxAppendToRecived;

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
	static final String COPY_SEL_TO_CLIPBOARD = "CopySelToClipboard";
	static final String COPY_ALL_TO_CLIPBOARD = "CopyAllToClipboard";
	static final String SEND_STRING_TO_SERIAL = "Send to serial";

	private JPanel panel_7;
	private JButton btnSendString;
	private JTextField textToSend;
	private JPanel panel_8;
	private JPanel panel_9;

	private JCheckBox chckbxWriteToFile;
	private JButton btnOpen;
	private JCheckBox chckbxLogDebug;

	PrintWriter outPrintWriter;

	String WorkingDir;

	String today;

	private JButton btnCheckPorts;

	private JCheckBox chckbxAdvanceConfig;

	private JCheckBox chckbxGenerateFile;

	private JCheckBox chckbxAutoscroll;

	private JTextField txtFilename;

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
					log("windowClosing(WindowEvent e)", "Closing application.. ", logFile);

					if (serial != null)
						serial.dispose();
					if (outPrintWriter != null)
						outPrintWriter.close();
					if (logFile != null)
						logFile.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setBounds(100, 100, 750, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		/**
		 * Panel : configuration components (NORTH)
		 */
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		btnCheckPorts = new JButton("Check ports");
		panel.add(btnCheckPorts);
		btnCheckPorts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.checkPorts();
			}
		});

		JLabel lblPortName = new JLabel("Port Name");
		comboBoxPortname = new JComboBox();
		panel.add(comboBoxPortname);

		JLabel lblNewLabel = new JLabel("Baud Rate");
		comboBoxBaudrate = new JComboBox(new DefaultComboBoxModel(baud_rates));
		panel.add(comboBoxBaudrate);

		chckbxAdvanceConfig = new JCheckBox("Advanced config.");
		chckbxAdvanceConfig.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				boolean test = chckbxAdvanceConfig.isSelected();
				comboBoxDataBits.setEnabled(test);
				comboBoxParityBits.setEnabled(test);
				comboBoxStopBits.setEnabled(test);
				chckbxDTR.setEnabled(test);
				chckbxRTS.setEnabled(test);
				if (test)
					appendMessage("Advanced options DataBits ,Parity bit, stop bit,DTR,RTS, ENABLED");
				else
					appendMessage("Advanced options DataBits ,Parity bit, stop bit,DTR,RTS, DISABLED");

			}

		});
		panel.add(chckbxAdvanceConfig);

		JLabel lblParity = new JLabel("Parity");
		panel.add(lblParity);

		comboBoxParityBits = new JComboBox(new DefaultComboBoxModel(parity_options));
		comboBoxParityBits.setEnabled(false);
		panel.add(comboBoxParityBits);

		JLabel lblDataBits = new JLabel("Data bits");
		panel.add(lblDataBits);
		comboBoxDataBits = new JComboBox(new DefaultComboBoxModel(data_bits));
		comboBoxDataBits.setEnabled(false);
		panel.add(comboBoxDataBits);

		JLabel lblStopBits = new JLabel("Stop bits");
		panel.add(lblStopBits);
		comboBoxStopBits = new JComboBox(new DefaultComboBoxModel(stop_bits));
		comboBoxStopBits.setEnabled(false);
		panel.add(comboBoxStopBits);

		chckbxRTS = new JCheckBox("RTS");
		chckbxRTS.setEnabled(false);
		panel.add(chckbxRTS);

		chckbxDTR = new JCheckBox("DTR");
		chckbxDTR.setEnabled(false);
		panel.add(chckbxDTR);

		/**
		 * PANEL_1: COMMANDS COMPONENTS (SOUTH)
		 */

		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		/**
		 * PANEL_8 : Connection buttons
		 */

		panel_8 = new JPanel();
		panel_1.add(panel_8);

		btnTestConnection = new JButton("Test Conn.");
		btnTestConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.testConnection();
			}
		});
		panel_8.add(btnTestConnection);

		btnOpen = new JButton("Open conn.");
		panel_8.add(btnOpen);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.openConnection();
			}
		});

		btnStop = new JButton("Close conn.");
		panel_8.add(btnStop);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.closeConnection();
			}
		});

		// LINE ENDING MESSAGE RECIVED
		JLabel lblAppendToRec = new JLabel("Append:");
		panel_8.add(lblAppendToRec);
		cboxAppendToRecived = new JComboBox(new DefaultComboBoxModel(line_endings));
		cboxAppendToRecived.setSelectedIndex(0);
		panel_8.add(cboxAppendToRecived);

		chckbxAutoscroll = new JCheckBox("Autoscroll");
		chckbxAutoscroll.setEnabled(true);
		panel_8.add(chckbxAutoscroll);

		/************************************
		 * PANEL 9 : Log out configurations
		 **********************************/

		panel_9 = new JPanel();

		btnClearOutput = new JButton("Clear output");
		panel_9.add(btnClearOutput);
		btnClearOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.clearOutput();
			}
		});

		panel_1.add(panel_9);
		// GENERATE FILE
		chckbxGenerateFile = new JCheckBox("Create output  file");
		chckbxGenerateFile.setSelected(true);
		panel_9.add(chckbxGenerateFile);
		// WRITE TO FILE
		chckbxWriteToFile = new JCheckBox("Write to  file");
		chckbxWriteToFile.setSelected(true);
		chckbxWriteToFile.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				boolean test = chckbxWriteToFile.isSelected();
				if (test)
					appendMessage("The bytes recived will be Sent to SerialaData_xx.dat File");
				else
					appendMessage("The bytes recived will NOT BE Sent to SerialaData_xx.dat File");

			}

		});
		panel_9.add(chckbxWriteToFile);

		// FILE Name
		JLabel lblFileExt = new JLabel("File name");
		panel_9.add(lblFileExt);
		txtFilename = new JTextField();
		panel_9.add(txtFilename);

		// LOGELEVEL DEBUG
		chckbxLogDebug = new JCheckBox("Loglevel=Debug");
		chckbxLogDebug.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				LEVEL_DEBUG = chckbxLogDebug.isSelected();
				if (LEVEL_DEBUG)
					appendMessage("The bytes recived will also been written to log file...");
				else
					appendMessage("The bytes recived will NOT BE written to log file...");

			}

		});

		panel_9.add(chckbxLogDebug);

		/*********************************
		 * PANEL 2 : BOTTONS COMMANDS to serial
		 */

		panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		// PANEL 3 Buttons
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

		// PAnel4 Toggle buttons
		// TODO : to fix loading icons in toogle buttons
		panel_4 = new JPanel();
		panel_2.add(panel_4);
		panel_4.setLayout(new GridLayout(3, 2, 0, 0));

		tglbtnOnoff1 = new JToggleButton("Switch 1");
		// tglbtnOnoff1.setSelectedIcon(new
		// ImageIcon(Talk.class.getResource("/com/antoiovi/serialtalk/ON.png")));
		// tglbtnOnoff1.setIcon(new
		// ImageIcon(Talk.class.getResource("/com/antoiovi/serialtalk/OFF.png")));
		// tglbtnOnoff1.setIcon(new
		// ImageIcon(getClass().getResource("/com/antoiovi/icons/switch-off-icon.png")));

		tglbtnOnoff1.addActionListener(this);
		tglbtnOnoff1.setActionCommand("Switch_1");
		// tglbtnOnoff1.setIcon(new
		// ImageIcon(Talk.class.getResource("/com/antoiovi/icons/switch-on-icon.png")));
		// tglbtnOnoff1.setSelectedIcon(new
		// ImageIcon(Talk.class.getResource("../icons/switch-on-icon.png")));
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

		// PANEL 5 SLIDERS and Send string
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

		// PANEL _7 : SEND STRING
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

		// LINE ENDINGS FOR MESSAGE TO PORT
		JLabel lblAppendTo = new JLabel("Append to port:");
		panel_7.add(lblAppendTo);
		cboxAppendToMessage = new JComboBox(new DefaultComboBoxModel(line_endings));
		panel_7.add(cboxAppendToMessage);
		cboxAppendToMessage.setSelectedIndex(1);

		/*******
		 * PANEL 6 : Text Area Control and Serial arrive
		 */
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

		/*
		 * JPanel panelctrOut=new JPanel(); panelctrOut.add(chckbxAutoscroll);
		 * panelctrOut.add(cboxAppendToRecived); panel_6.add(panelctrOut);
		 */

		/**
		 * PopUp menu to copy serial data to clipboard
		 */

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

	private void checkPorts() {
		int x = 0;
		port_names = this.getPortNames();
		DefaultComboBoxModel<String> defaultComboBoxModel = new DefaultComboBoxModel<String>(port_names);
		DefaultComboBoxModel<String> model = defaultComboBoxModel;
		for (String s : port_names) {
			x++;
			String msg = String.format("%d) Port %s detected", x, s);
			this.appendMessage(msg);
			this.log("checkPorts", msg, logFile);
		}
		comboBoxPortname.setModel(model);
		if (x == 0)
			appendMessage("No port detected ");
	}

	/**
	 * Initialize with default values, and system parameters
	 */
	private void init() {
		WorkingDir = System.getProperty("user.dir");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		today = dateFormat.format(date);

		initLogFile();

		comboBoxBaudrate.setSelectedItem(baud_rates[5]);
		this.appendMessage("Author : Antonello Iovino antoiovi 2019 ");

		// Initialize ports and check box ports
		checkPorts();
		comboBoxDataBits.setSelectedItem(SerialPort.DATABITS_8);
		comboBoxParityBits.setSelectedItem(parity_options[PARITY_NONE]);
		chckbxDTR.setSelected(true);
		chckbxRTS.setSelected(true);
		txtFilename.setText(generateFileName());
		log("init()", "Initilized all.. ", logFile);

	}

	private String[] getPortNames() {
		return SerialPortList.getPortNames();
	}

	PrintWriter logFile;

	void initLogFile() {
		// String logFileName = "log_" + today + (System.currentTimeMillis() + ".txt");
		String logFileName = "log_" + today + ".txt";
		try {
			logFile = new PrintWriter(new FileWriter(logFileName, true));
		} catch (FileNotFoundException e) {
			this.appendMessage("File nOt Found:Unable to create log file....");
		} catch (IOException e) {
			this.appendMessage("IOException : Unable to create log file....");

		}
	}

	/**
	 * GENERATE NEW NAME OF DATA OUTPUT FILE AND OPEN IT
	 */
	void initOutputFile() {
		if (!chckbxGenerateFile.isSelected())
			return;
		String fileName = txtFilename.getText();
		Pattern p = Pattern.compile("[a-zA-Z_0-9\\-.]*");

		Matcher m = p.matcher(fileName);
		boolean isvalidname = m.matches();
		File f = new File(fileName);
		if (!isvalidname || fileName.isEmpty()) {
			app.appendMessage("Invalid file name. New name will be generated");
			fileName = this.generateFileName();
			app.appendMessage("File " + fileName + " will be created.");
			txtFilename.setText(fileName);
		}

		if (f.exists() && !f.isDirectory()) {
			app.appendMessage("File " + fileName + " already exists .New file name will be generated");
			fileName = this.generateFileName();
			app.appendMessage("File " + fileName + " will be created.");
			txtFilename.setText(fileName);
		}
		try {
			outPrintWriter = new PrintWriter(fileName);
			app.fileName = fileName;
			app.appendMessage("OutPut file : " + fileName + "  Created !!");
			log("initOutputFile", "outPut file : " + fileName + "  Created!! ", logFile);
		} catch (FileNotFoundException e) {
			app.appendMessage("Unable to create outPut file....");
			log("initOutputFile", "Unable to create outPut file....", logFile);
			logToConsole(e.toString());
		}
	}

	private boolean testingConnection;

	public boolean isTestingConnection() {
		return testingConnection;
	}

	public void setTestingConnection(boolean testingConnection) {
		this.testingConnection = testingConnection;
	}

	/**
	 * Open and close the selected port , only to verify connectivity
	 */
	private void testConnection() {
		if (app.isConnectionOpend())
			return;
		app.setTestingConnection(true);
		app.openConnection();
		app.closeConnection();
		app.setTestingConnection(false);
	}

	boolean connectionOpend;

	public boolean isConnectionOpend() {
		return connectionOpend;
	}

	public void setConnectionOpend(boolean connectionOpend) {
		this.connectionOpend = connectionOpend;
		btnTestConnection.setEnabled(!connectionOpend);
		btnOpen.setEnabled(!connectionOpend);
		txtFilename.setEnabled(!connectionOpend);
		chckbxWriteToFile.setEnabled(chckbxGenerateFile.isSelected());
		chckbxGenerateFile.setEnabled(!connectionOpend);

		comboBoxBaudrate.setEnabled(!connectionOpend);
		comboBoxPortname.setEnabled(!connectionOpend);
		chckbxAdvanceConfig.setEnabled(!connectionOpend);
		if (chckbxAdvanceConfig.isSelected()) {
			chckbxDTR.setEnabled(!connectionOpend);
			chckbxRTS.setEnabled(!connectionOpend);
			comboBoxDataBits.setEnabled(!connectionOpend);
			comboBoxParityBits.setEnabled(!connectionOpend);
			comboBoxStopBits.setEnabled(!connectionOpend);
		}
		if (connectionOpend)
			app.setTitle(portname);
		else
			app.setTitle("");
	}

	/**
	 * Open serial port
	 */
	private void openConnection() {

		String methodname = "openConnection";

		if (serial != null) {
			try {
				if (serial.portIsOpened())
					app.appendMessage("Serial Port already opened :" + portname);
				else {
					serial.open();
					if (app.isTestingConnection())
						return;
					initOutputFile();
					app.setConnectionOpend(true);
				}
			} catch (SerialException e) {
				app.appendMessage("SerialException opening  :" + portname);
				app.appendMessage("SerialException  :" + e.toString());
				log(methodname, "ERROR :" + "SerialException opening  :" + portname, logFile);
				log(methodname, "ERROR :" + "SerialException opening  :" + e.toString(), logFile);
			} catch (NullPointerException e) {
				app.appendMessage("ERROR :" + "Null pointer , trying to reconnect ...:" + portname);
				initConnection();
				if (serial.portIsOpened()) {
					app.appendMessage("Port opened successfully!!:" + portname);
					if (app.isTestingConnection())
						return;

					initOutputFile();
					app.setConnectionOpend(true);
					log(methodname, "Port opened successfully!!:" + portname, logFile);
				} else {
					app.appendMessage("Failed to initConnection() ...with port :" + portname);
					log(methodname, "ERROR :" + "Failed to initConnection() ...with port :" + portname, logFile);
				}

			}

		} else// if (serial==null) We must call initConnection() :
		{

			app.appendMessage("Serial not initialized ..!! Port :" + portname);

			try {
				initConnection();
				if (serial.portIsOpened()) {
					app.appendMessage("Port opened successfully!!:" + portname);
					if (app.isTestingConnection())
						return;
					initOutputFile();
					app.setConnectionOpend(true);

					log("Open()", " Port opened successfully!!:" + portname, logFile);
				} else {
					app.appendMessage("Failed to initConnection() ...:" + portname);
					log("Open()", "ERROR: Failed to initConnection() ...: ", logFile);
				}
			} catch (NullPointerException e) {
				app.appendMessage("ERROR: Null Pointer eXception  opening port : " + portname);
				log("Open()", "ERROR: Null Pointer eXception opening port ", logFile);
			} catch (Exception e) {
				app.appendMessage("ERROR: Could not open port: " + portname);
				log("Open()", "ERROR: Could not open port .", logFile);
			}

		}

	}

	/***
	 * Initialize the serial port
	 */
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
			portname = name;
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

				app.appendMessage("Port opened :" + portname);
				TimeUnit.SECONDS.sleep(1);
			} else
				app.appendMessage("Port is not opened :" + portname);

		} catch (SerialException e) {
			app.appendMessage("Serial Exception opening port " + portname);
			app.appendMessage(e.getMessage());
			// e.printStackTrace();
		} catch (IOException e) {
			app.appendMessage("IO Exception opening port " + portname);
			app.appendMessage(e.getMessage());
			// e.printStackTrace();
		} catch (InterruptedException e) {
			// Timeunit.Seconds.sleep(..) exception
			app.appendMessage("InterruptedException opening port " + portname);
			app.appendMessage(e.getMessage());
			// e.printStackTrace();
		}
	}

	String fileName = "";

	void openFileToWrite() {
		try {
			outPrintWriter = new PrintWriter(fileName);
			log("openFileToWrite()", " File opened successfully:" + fileName, logFile);
		} catch (FileNotFoundException e) {
			outPrintWriter = null;
			textAreaControl.append(e.toString());
			textAreaControl.append("Error..... Can't open output file !!!");
			log("openFileToWrite()", "ERROR :" + "FileNotFoundException - Can't open output file :" + fileName,
					logFile);
		}

	}

	String generateFileName() {
		String file_ext = "txt";

		final String fileprefix = "SerialData_";
		int x = 1;
		String Dir = WorkingDir;

		File dir = new File(Dir);
		File[] matchingFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(fileprefix);
			}
		});
		String suffix;
		while (true) {
			boolean contains = false;
			suffix = "_" + today + "-" + String.valueOf(x) + "." + file_ext;

			for (int ir = 0; ir < matchingFiles.length; ir++) {
				// appendMessage(matchingFiles[ir].toString());
				String s = matchingFiles[ir].toString();

				if (s.contains(suffix)) {
					// appendMessage("\t FILE PRESENTE");
					x++;
					contains = true;
					break;
				}
				suffix = "_" + today + "-" + String.valueOf(x) + "." + file_ext;
			}
			if (contains)
				continue;
			suffix = "_" + today + "-" + String.valueOf(x) + "." + file_ext;
			break;
		}
		// appendMessage(fileprefix + suffix);
		return fileprefix + suffix;
	}

	/**
	 * Close serial port and output file
	 */
	void closeConnection() {
		// closeFile();
		try {

			if (serial != null) {
				if (serial.portIsOpened()) {
					serial.dispose();
					appendMessage("Port closed :" + portname);
					app.setConnectionOpend(false);
					closeOutputFile();
				} else {
					appendMessage("Port already closed ;");

				}
			}
		} catch (IOException e) {
			app.appendMessage("Cant't close serial ! Maybe it's not opened.");
			log("Stop()", "ERROR: IOException .  Cant't close serial ! Maybe it's not opened.", logFile);
		} catch (NullPointerException e) {
			app.appendMessage("ERROR: Null Pointer eXception :Closing port not opened.  ");
			log("Stop()", "ERROR: Null Pointer eXception, Closing port not opened. ", logFile);
		}

	}

	void closeOutputFile() {
		if (testingConnection)
			return;
		if (outPrintWriter != null) {
			outPrintWriter.close();
			appendMessage("Closed file " + fileName);
			outPrintWriter = null;
		}

	}

	void appendMessage(String str) {
		textAreaControl.append(str);
		textAreaControl.append("\n");
		textAreaControl.selectAll();
	}

	void clearOutput() {
		textAreaControl.setText("");
		textAreaSerial.setText("");

	}

	/**
	 * Write to Srial Port
	 * 
	 * @param str
	 */
	void writeToSerial(String str) {
		String app = "";
		try {
			switch (cboxAppendToMessage.getSelectedIndex()) {
			case 1:
				app += "\n";
				break;
			case 2:
				app += "\r";
				break;
			case 3:
				app += "\r\n";
				break;
			default:
				break;
			}
			serial.write(str + app);
			// Add new line to string
			// serial.write('\n');
			appendMessage("Sent to serial port " + portname + " : " + str);
			log("writeToSErial", str, logFile);

		} catch (Exception e) {
			appendMessage("Error writing to serial port.");
			log("writeToSErial", "Errore scrivendo sulla porta", logFile);
		}
	}

	/**
	 * Parametro ricevuto da lettura porta Impelementation of Interface
	 * com.antoiovi.LineRecived
	 * 
	 * @param line
	 */
	synchronized public void setMessage(String line) {
		String app = "";

		switch (cboxAppendToRecived.getSelectedIndex()) {
		case 1:
			app += "\n";
			break;
		case 2:
			app += "\r";
			break;
		case 3:
			app += "\r\n";
			break;
		default:
			break;
		}
		textAreaSerial.append(line + app);
		if (chckbxAutoscroll.isSelected())
			textAreaSerial.selectAll();
		// appendMessage("Line recived from port :" + portname);

		if (chckbxWriteToFile.isSelected() && chckbxWriteToFile.isSelected())
			if (outPrintWriter != null) {
				outPrintWriter.print(line);
				outPrintWriter.flush();
			}

		logDebug("readFromSerial", line, logFile);

	}

	/**
	 * Utility class
	 * 
	 * @author antoiovi
	 *
	 */
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
			if (msg.indexOf('\n') > -1)
				appendMessage("New line present in Switxh_kjhk");
			if (abstractButton.getModel().isSelected())
				msg = msg + "-ON";
			else
				msg = msg + "-OFF";

			appendMessage(msg);
			writeToSerial(msg);
		} else if (msg.equals(btnA.getActionCommand()) || msg.equals(btnB.getActionCommand())
				|| msg.equals(btnC.getActionCommand()) || msg.equals(btnD.getActionCommand())) {

			writeToSerial(msg);

		} else if (msg.equals(COPY_ALL_TO_CLIPBOARD)) {

			String myString = textAreaSerial.getText();
			StringSelection stringSelection = new StringSelection(myString);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} else if (msg.equals(COPY_SEL_TO_CLIPBOARD)) {

			String myString = textAreaSerial.getSelectedText();
			StringSelection stringSelection = new StringSelection(myString);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} else if (msg.equals(SEND_STRING_TO_SERIAL)) {

			writeToSerial(textToSend.getText());
		}
	}

	/**
	 * Log to system console
	 * 
	 * @param msg
	 */
	void logToConsole(String msg) {
		System.out.println(msg);
	}

	/**
	 * Log To file
	 * 
	 * @param methodname
	 * @param msg
	 * @param logfile
	 */
	void log(String methodname, String msg, PrintWriter logfile) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String day = dateFormat.format(date);
		if (logfile != null) {
			logfile.write(String.format("[%s][%s] %s \n", day, methodname, msg));
		}

	}

	boolean LEVEL_DEBUG = false;

	void logDebug(String methodname, String msg, PrintWriter logfile) {
		if (!LEVEL_DEBUG)
			return;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String day = dateFormat.format(date);
		appendMessage("Line recived from port :" + portname);
		if (logfile != null) {
			logfile.write(String.format("[%s][%s] %s \n", day, methodname, msg));
		}
	}

	/*
	 * ChangeListener : for Sliders
	 */
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		// logToConsole(source.getName());
		if (!source.getValueIsAdjusting()) {
			int value = (int) source.getValue();
			String msg = source.getName() + ":" + String.valueOf(value);
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
