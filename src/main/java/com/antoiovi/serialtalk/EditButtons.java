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

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.Dimension;

public class EditButtons extends JDialog implements ActionListener {

	static EditButtons app;

	private JPanel panel_2;

	private JPanel panel;

	private JTable table;

	private String WorkingDir;
	private JLabel lblNewLabel;
	private JPanel panel_1;
	private JPanel panel_3;
	private JButton btnSave;
	private JButton btnAbort;
	private JButton btnLoadDef;
	private JScrollPane scrollPane;
	private JTable table_1;

	private PropPair properties[];

	private PropPair propertiestexts[];

	private PropPair propertiescommands[];

	private PropPair propertiesvisibles[];

	private PropPair proptrimtexts[];

	private PropPair proptrimcmds[];

	private PropPair proptrimmins[];

	private PropPair proptrimmaxs[];

	private PropPair proptrimvis[];
	HashMap<String,String> propertiesMap;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditButtons frame = new EditButtons();
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
	public EditButtons() {
		propertiesMap = new HashMap<String,String>();
		properties = new PropPair[] {
		new PropPair("btn1.text"),new PropPair("btn2.text"),new PropPair("btn3.text"),new PropPair("btn4.text"),
		new PropPair("tglbtn1.text"),new PropPair("tglbtn2.text"),new PropPair("tglbtn3.text"),new PropPair("tglbtn4.text"),
		new PropPair("btnsendmsg.text"),
		new PropPair("btn1.command"),new PropPair("btn2.command"),new PropPair("btn3.command"),new PropPair("btn4.command"),
		new PropPair("tglbtn1.command"),new PropPair("tglbtn2.command"),new PropPair("tglbtn3.command"),new PropPair("tglbtn4.command"),
		new PropPair("btnsendmsg.command"),
		new PropPair("btn1.visible","boolean"),new PropPair("btn2.visible","boolean"),
		new PropPair("btn3.visible","boolean"),new PropPair("btn4.visible","boolean"),
		new PropPair("tglbtn1.visible","boolean"),new PropPair("tglbtn2.visible","boolean"),
		new PropPair("tglbtn3.visible","boolean"),new PropPair("tglbtn4.visible","boolean"),
		new PropPair("btnsendmsg.visible","boolean"),
		new PropPair("trim1.text"),	new PropPair("trim2.text"),	new PropPair("trim1.command"),	new PropPair("trim2.command"),
		new PropPair("trim1.min","double"),new PropPair("trim1.max","double"),
		new PropPair("trim2.min","double"),new PropPair("trim2.max","double"),
		new PropPair("trim1.visible","boolean"),new PropPair("trim1.max","boolean"),
		
		};
		
		
		
		propertiestexts = new PropPair[] {
				new PropPair("btn1.text"),new PropPair("btn2.text"),new PropPair("btn3.text"),new PropPair("btn4.text"),
				new PropPair("tglbtn1.text"),new PropPair("tglbtn2.text"),new PropPair("tglbtn3.text"),new PropPair("tglbtn4.text"),
				new PropPair("btnsendmsg.text"),

				};
		propertiescommands = new PropPair[] {
				new PropPair("btn1.command"),new PropPair("btn2.command"),new PropPair("btn3.command"),new PropPair("btn4.command"),
				new PropPair("tglbtn1.command"),new PropPair("tglbtn2.command"),new PropPair("tglbtn3.command"),new PropPair("tglbtn4.command"),
				new PropPair("btnsendmsg.command"),				
				};
		
		propertiesvisibles = new PropPair[] {
		new PropPair("btn1.visible","boolean"),new PropPair("btn2.visible","boolean"),
		new PropPair("btn3.visible","boolean"),new PropPair("btn4.visible","boolean"),
		new PropPair("tglbtn1.visible","boolean"),new PropPair("tglbtn2.visible","boolean"),
		new PropPair("tglbtn3.visible","boolean"),new PropPair("tglbtn4.visible","boolean"),
		new PropPair("btnsendmsg.visible","boolean")};
		
		proptrimtexts = new PropPair[] {
				new PropPair("trim1.text"),	new PropPair("trim2.text")	};
		
		proptrimcmds = new PropPair[] {
				new PropPair("trim1.command"),	new PropPair("trim2.command")};
		proptrimmins = new PropPair[] {
				new PropPair("trim1.min","double"),	new PropPair("trim2.min","double")	};
		proptrimmaxs = new PropPair[] {
					new PropPair("trim1.max","double"),new PropPair("trim2.max","double")};
		proptrimvis = new PropPair[] {	new PropPair("trim1.visible","boolean"),new PropPair("trim1.max","boolean")	};
		

		setBounds(100, 100, 400, 300);
		setSize(638, 407);
		setPreferredSize(new Dimension(800, 400));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(new BorderLayout(0, 0));

		/*********************************
		 * PANEL 2 : BOTTONS COMMANDS to serial
		 */

//		tglbtnOnoff2.setSelectedIcon(new ImageIcon(getImage("icons/ON.png")));

		ImageIcon imageIcon = new ImageIcon(getImage("icons/editbuttons.png"));
		ImageIcon imageOn = new ImageIcon(getImage("icons/ON.png"));

		panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		// JLabel label = new JLabel(imageIcon);
		lblNewLabel = new JLabel(imageIcon);
		panel_2.add(lblNewLabel);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] { { "A", null, null, null }, { "B", null, null, null }, { "C", null, null, null },
						{ "D", null, null, null }, { "Switch1", null, null, null }, { "Switch2", null, null, null },
						{ "Switch3", null, null, null }, { "Switch4", null, null, null },
						{ "Send  message", null, null, null }, },
				new String[] { "Component", "Text", "Command", "Visible" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, Boolean.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, true, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(177);
		panel.add(new JScrollPane(table));

		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
				new Object[][] { { "Trimmer 1", null, null, null, null, null },
						{ "Trimmer 2", null, null, null, null, null }, },
				new String[] { "Component", "Text", "Command", "Min", "Max", "Visible" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, Double.class, Double.class,
					Boolean.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		panel.add(new JScrollPane(table_1));

		panel_1 = new JPanel();
		panel.add(panel_1);

		panel_3 = new JPanel();
		panel.add(panel_3);

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int input = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "Select an Option...",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
				// 0=yes, 1=no, 2=cancel
				if (input == 0)
					saveProperties();
			}
		});
		panel_3.add(btnSave);

		btnAbort = new JButton("Abort");
		btnAbort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int input = JOptionPane.showConfirmDialog(null, "Do you want to proceed?",
						"Current modifies will be lost.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
				// 0=yes, 1=no, 2=cancel
				System.out.println(String.valueOf(input));
			}
		});
		panel_3.add(btnAbort);

		btnLoadDef = new JButton("Load default");
		btnLoadDef.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int input = JOptionPane.showConfirmDialog(null, "Do you want to proceed?",
						"Current modifies will be lost.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
				// 0=yes, 1=no, 2=cancel
				if (input == 0)
					initDefaultProps();
			}
		});
		panel_3.add(btnLoadDef);
		// panel.add(table);
		init();
		pack();
		// restoreDefaults();
	}

	/**
	 * Initialize with default values, and system parameters
	 */
	private void init() {
		WorkingDir = System.getProperty("user.dir");

		initProperties();

	}

	Properties properties() {
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(WorkingDir + "/config.properties")) {
			// load a properties file
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			prop = null;
		}
		return prop;
	}

	Properties propertiesDefault() {
		Properties prop = new Properties();
		URL url = ClassLoader.getSystemResource("config/config.properties");
		if (url != null) {
			try {
				prop.load(url.openStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				prop = null;
			}
		}
		return prop;
	}

	/**
	 * Inizzializza le properies se sono state salvate
	 */
	void initProperties() {
		Properties p = properties();
		if (p == null)
			p = propertiesDefault();
		if (p != null)
			initProps(p);
	}

	void initDefaultProps() {
		Properties p = propertiesDefault();
		if (p != null)
			initProps(p);
	}

	void initProps(Properties propd) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int r=0;
		for (PropPair p: propertiestexts) {
			this.setPropertiToTable(model,p,propd,  r++, 1);
		}
		r=0;
		for (PropPair p: propertiescommands) {
			this.setPropertiToTable(model, p,propd, r++,2);
		}
		r=0;
		for (PropPair p: propertiesvisibles) {
			this.setPropertiToTable(model, p,propd, r++,3);
		}	
		//--------------------------------------------------------
		
		model = (DefaultTableModel) table_1.getModel();
		for (PropPair p: proptrimtexts) {
			this.setPropertiToTable(model,p,propd,  r++, 1);
		}
		r=0;
		for (PropPair p: proptrimcmds) {
			this.setPropertiToTable(model, p,propd, r++,2);
		}
		r=0;
		for (PropPair p: proptrimmins) {
			this.setPropertiToTable(model, p,propd, r++,3);
		}	
		r=0;
		for (PropPair p: proptrimmaxs) {
			this.setPropertiToTable(model, p,propd, r++,3);
		}	
		r=0;
		for (PropPair p: proptrimvis) {
			this.setPropertiToTable(model, p,propd, r++,3);
		}	

	}

	void saveProperties() {
		try (OutputStream output = new FileOutputStream(WorkingDir + "/config.properties")) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			Properties prop = new Properties();
			int r = 0;
			int c = 1;
			prop.setProperty("btn1.text", (String) model.getValueAt(r, c));
			prop.setProperty("btn2.text", (String) model.getValueAt(r++, c));
			prop.setProperty("btn3.text", (String) model.getValueAt(r++, c));
			prop.setProperty("btn4.text", (String) model.getValueAt(r++, c));

			prop.setProperty("tglbtn1.text", (String) model.getValueAt(r++, c));
			prop.setProperty("tglbtn2.text", (String) model.getValueAt(r++, c));
			prop.setProperty("tglbtn3.text", (String) model.getValueAt(r++, c));
			prop.setProperty("tglbtn4.text", (String) model.getValueAt(r++, c));
			prop.setProperty("btnsendmsg.text", (String) model.getValueAt(r++, c));
			
			prop.setProperty("btnsendmsg.visible",String.valueOf( model.getValueAt(8,3)));
			/*
			 * this.setPropertiToTable(model, propd.getProperty("btn1.command"), 0, 2);
			 * this.setPropertiToTable(model, propd.getProperty("btn1.command"), 1, 2);
			 * this.setPropertiToTable(model, propd.getProperty("btn1.command"), 2, 2);
			 * this.setPropertiToTable(model, propd.getProperty("btn1.command"), 3, 2);
			 * this.setPropertiToTable(model, propd.getProperty("tglbtn1.command"), 4, 2);
			 * this.setPropertiToTable(model, propd.getProperty("tglbtn2.command"), 5, 2);
			 * this.setPropertiToTable(model, propd.getProperty("tglbtn3.command"), 6, 2);
			 * this.setPropertiToTable(model, propd.getProperty("tglbtn4.command"), 7, 2);
			 * this.setPropertiToTable(model, propd.getProperty("btnsendmsg.command"), 8,
			 * 2);
			 * 
			 * this.setPropertiToTable(model,
			 * propd.getProperty("btn1.visible").equals("true"), 0, 3);
			 * this.setPropertiToTable(model,
			 * propd.getProperty("btn2.visible").equals("true"), 1, 3);
			 * this.setPropertiToTable(model,
			 * propd.getProperty("btn3.visible").equals("true"), 2, 3);
			 * this.setPropertiToTable(model,
			 * propd.getProperty("btn4.visible").equals("true"), 3, 3);
			 * this.setPropertiToTable(model,
			 * propd.getProperty("tglbtn1.visible").equals("true"), 4, 3);
			 * this.setPropertiToTable(model,
			 * propd.getProperty("tglbtn2.visible").equals("true"), 5, 3);
			 * this.setPropertiToTable(model,
			 * propd.getProperty("tglbtn3.visible").equals("true"), 6, 3);
			 * this.setPropertiToTable(model,
			 * propd.getProperty("tglbtn4.visible").equals("true"), 7, 3);
			 * this.setPropertiToTable(model,
			 * propd.getProperty("btnsendmsg.visible").equals("true"), 8, 3);
			 */

			// save properties to project root folder
			prop.store(output, null);

			System.out.println(prop);

		} catch (IOException io) {
			io.printStackTrace();
		}

	}
	boolean retriveBooleanProp(Properties p,String pname) {
		String val=p.getProperty(pname);
		if(val!=null)
			return val.equals("true");
		 else
			 return false;
	}
	
	double retriveDoubleProp(Properties p,String pname) {
		String val=p.getProperty(pname);
		try {
			double x=Double.valueOf(val);
			return x;
		}catch(Exception e) {
			return 0.0;
		}
		
	}
	
	void setPropertiToTable(DefaultTableModel model, PropPair pp,Properties pr, int r, int c) {
		pp.setValue(pr);
		if(pp.type.equals(pp.STRING))
			model.setValueAt(pp.svalue, r, c);
		else if(pp.type.equals(pp.BOOLEAN))
			model.setValueAt(pp.bvalue, r, c);
		else if(pp.type.equals(pp.DOUBLE))
			model.setValueAt(pp.dvalue, r, c);
	
	}
	void setPropertiToTable(DefaultTableModel model, String value, int r, int c) {
		model.setValueAt(value, r, c);

	}

	void setPropertiToTable(DefaultTableModel model, Boolean value, int r, int c) {
		model.setValueAt(value, r, c);
	}

	void setPropertiToTable(DefaultTableModel model, Double value, int r, int c) {
		model.setValueAt(value, r, c);
	}
	
	Object getPropertiFromTable(DefaultTableModel model, String value, int r, int c) {
		
		return model.getValueAt(r++, c);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Per recuperare le immagini archiviate nel file jar
	 * 
	 * @param pathAndFileName
	 * @return
	 */
	public static Image getImage(final String pathAndFileName) {
		final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
		return Toolkit.getDefaultToolkit().getImage(url);
	}
private class PropPair{
	String STRING="string";
	String DOUBLE="double";
	String BOOLEAN="boolean";

	String name;
	String type;
	String svalue;
	double dvalue;
	boolean bvalue;
	
	public PropPair(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	public PropPair(String name) {
		super();
		this.name = name;
		this.type = "string";
	}
	
	void setValue(Properties prop) {
		String val=prop.getProperty(name);
		if(val!=null) {
			if(type.equals(STRING)) {
				this.setValue(val);
			}else if(type.equals(BOOLEAN)) {
				this.setValue(val.equals("true"));
			}else if(type.equals(DOUBLE)) {
				try {
					double x=Double.valueOf(val);
				this.setValue(x);
				}catch(Exception e) {
				this.setValue(0.0);
				}
			}
			
			}
		 else {
			 if(type.equals(STRING)) {
					this.setValue("");
				}else if(type.equals(BOOLEAN)) {
					this.setValue(false);

				}else if(type.equals(DOUBLE)) {
					this.setValue(0.0);
					
				}
				
				}			 
	}
	
	
	void setValue(String val) {
		this.svalue=val;
	}
	void setValue(double val) {
		this.dvalue=val;
	}
	void setValue(boolean val) {
		this.bvalue=val;
	}
	
}
}
