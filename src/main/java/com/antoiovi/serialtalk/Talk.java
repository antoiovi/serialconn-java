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

public class Talk extends JFrame {

	static final String[] baud_rates ={
		String.valueOf(SerialPort.BAUDRATE_110),
		String.valueOf(SerialPort.BAUDRATE_300),
		String.valueOf(SerialPort.BAUDRATE_600),
		String.valueOf(SerialPort.BAUDRATE_1200),
		String.valueOf(SerialPort.BAUDRATE_4800),
		String.valueOf(SerialPort.BAUDRATE_9600),
		String.valueOf(SerialPort.BAUDRATE_14400),
		String.valueOf(SerialPort.BAUDRATE_19200),
		String.valueOf(SerialPort.BAUDRATE_38400),

		String.valueOf(SerialPort.BAUDRATE_115200),
		String.valueOf(SerialPort.BAUDRATE_128000),
		String.valueOf(SerialPort.BAUDRATE_256000),
	};
	
	String[] port_names ={
			"/dev/tty0","/dev/tty1","/dev/tty2",
			"/dev/USB0","/dev/USB1","/dev/USB1"
		};
	
	Integer[] data_bits ={
			SerialPort.DATABITS_5,
			SerialPort.DATABITS_6,
			SerialPort.DATABITS_7,
			SerialPort.DATABITS_8
		};
	
	Integer[] stop_bits ={
 			SerialPort.STOPBITS_1,
			SerialPort.STOPBITS_2,
			SerialPort.STOPBITS_1_5
		};

    
	String[] parity_options ={
			"NONE","ODD","EVEN","MARK","SPACE"
		};
    

    public static final int PARITY_NONE = 0;
    public static final int PARITY_ODD = 1;
    public static final int PARITY_EVEN = 2;
    public static final int PARITY_MARK = 3;
    public static final int PARITY_SPACE = 4;

	
	 // Valori di deafult :
	  String  name="/dev/ttyUSB0";
	  int baudRate =9600;
	  int parity=SerialPort.PARITY_NONE;
	  int databits=8;
	  double stopBits =1.0; //stop_bits
	  boolean RTS=true;
	  boolean DTR=true;
	  
	  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Talk frame = new Talk();
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
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		
		
 		

 		
 		JLabel lblPortName = new JLabel("Port Name");
 		JComboBox comboBoxPortname = new JComboBox(new DefaultComboBoxModel(port_names));
 		panel.add(comboBoxPortname);
 		JLabel lblNewLabel = new JLabel("Baud Rate");
 		JComboBox comboBoxBaudrate = new JComboBox(new DefaultComboBoxModel(baud_rates));
 		panel.add(comboBoxBaudrate);
 		
 		JLabel lblParity = new JLabel("Parity");
 		panel.add(lblParity);
 		
 		JComboBox comboBoxParityBits = new JComboBox(new DefaultComboBoxModel(parity_options));
 		panel.add(comboBoxParityBits);
 		
 		JLabel lblDataBits = new JLabel("Data bits");
 		panel.add(lblDataBits);
  		JComboBox comboBoxDataBits = new JComboBox(new DefaultComboBoxModel(data_bits));
 		panel.add(comboBoxDataBits);
 		
 		JLabel lblStopBits = new JLabel("Stop bits");
 		panel.add(lblStopBits);
 		JComboBox comboBoxStopBits = new JComboBox(new DefaultComboBoxModel(stop_bits));
 		panel.add(comboBoxStopBits);
 		
 		JCheckBox chckbxRTS = new JCheckBox("RTS");
 		panel.add(chckbxRTS);
 		
 		JCheckBox chckbxDTR = new JCheckBox("DTR");
 		panel.add(chckbxDTR);
 
	}

}
