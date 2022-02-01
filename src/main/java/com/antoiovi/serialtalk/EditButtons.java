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

public class EditButtons extends JDialog  {

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

	static int returnval=0;
	

	public static synchronized int getReturnval() {
		return returnval;
	}

	public static synchronized void setReturnval(int returnval) {
		EditButtons.returnval = returnval;
	}






	private String[] propertiestexts;

	private String[] propertiescommands;

	private String[] propertiesvisibles;

	private String[] proptrimtexts;

	private String[]  proptrimcmds;

	private String[]  proptrimmins;

	private String[]  proptrimmaxs;

	private String[]  proptrimvis;
	

	private Properties applicationProps;

	private Properties defaultProps;
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
		
		propertiestexts = new String[] {
				"btn1.text","btn2.text","btn3.text","btn4.text",
				"tglbtn1.text","tglbtn2.text","tglbtn3.text","tglbtn4.text",
				"btnsendmsg.text"	};
		propertiescommands = new String[] {
				"btn1.command","btn2.command","btn3.command","btn4.command",
				"tglbtn1.command","tglbtn2.command","tglbtn3.command","tglbtn4.command",
				"btnsendmsg.command"				
				};
		
		propertiesvisibles = new String[] {
				"btn1.visible","btn2.visible","btn3.visible","btn4.visible",
				"tglbtn1.visible","tglbtn2.visible","tglbtn3.visible","tglbtn4.visible",
				"btnsendmsg.visible"};
		
		proptrimtexts = new String[] {"trim1.text","trim2.text"	};
		
		proptrimcmds = new String[] {"trim1.command",	"trim2.command"};
		
		proptrimmins = new String[] {"trim1.min","trim2.min"	};
		
		proptrimmaxs = new String[] {"trim1.max","trim2.max"};
		
		proptrimvis = new String[] {"trim1.visible","trim2.visible"};
		

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
			new Object[][] {
				{"Trimmer 1", null, null, null, null, null},
				{"Trimmer 2", null, null, null, null, null},
			},
			new String[] {
				"Component", "Text", "Command", "Min", "Max", "Visible"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Integer.class, Integer.class, Boolean.class
			};
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
				int input = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				// 0=yes, 1=no, 2=cancel
				if (input == 0) {
					saveProperties();
					setReturnval(1);

				dispose();
				}
			}
		});
		panel_3.add(btnSave);

		btnAbort = new JButton("Abort");
		btnAbort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int input = JOptionPane.showConfirmDialog(null, "Do you want to proceed?\nCurrent modifies will be lost.", 
						"",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				// 0=yes, 1=no, 2=cancel
				System.out.println(String.valueOf(input));
				if (input == 0) {
					setReturnval(-1);
					dispose();
				}
			}
		});
		panel_3.add(btnAbort);

		btnLoadDef = new JButton("Load default");
		btnLoadDef.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int input = JOptionPane.showConfirmDialog(null, "Do you want to proceed?\nCurrent modifies will be lost.", "",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				// 0=yes, 1=no, 2=cancel
				if (input == 0)
					initProperties();
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


	/**
	 * Inizzializza le properies se sono state salvate
	 */
	/**
	 * Inizzializza le properies
	 */
	void initProperties() {
		defaultProps = new Properties();
		URL url = ClassLoader.getSystemResource("config/config.properties");
		if (url != null) {
			try {
				defaultProps.load(url.openStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		applicationProps = new Properties(defaultProps);

		try (InputStream input = new FileInputStream(WorkingDir + "/config.properties")) {
			// load a properties file
			applicationProps.load(input);
			input.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		initProps();
	}

	
/***
 * Inizzializza i valori nella tabella
 */
	void initProps() {
		Properties propd=applicationProps;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int r=0;
		int col=1;
		for (String p: propertiestexts) {
			String val=retriveProp(propd, p,"_");
			model.setValueAt(val, r++,col);
		}
		r=0;
		col++;
		for (String p: propertiescommands) {
			String val=retriveProp(propd, p,"_");
			model.setValueAt(val, r++,col);
		}
		r=0;
		col++;
		for (String p: propertiesvisibles) {
			Boolean val=Boolean.valueOf( retriveProp(propd, p,false));
			model.setValueAt(val, r++,col);			}
			
		//--------------------------------------------------------
		
		model = (DefaultTableModel) table_1.getModel();
		r=0;
		col=1;
		for (String p: proptrimtexts) {
			String val=retriveProp(propd, p,"_");
			model.setValueAt(val, r++,col);	
			}		
		r=0;
		col++;
		for (String p: proptrimcmds) {
			String val=retriveProp(propd, p,"_");
			model.setValueAt(val, r++,col);
		}
		r=0;
		col++;
		for (String p: proptrimmins) {
			Integer val=Integer.valueOf( retriveProp(propd, p,0));
			model.setValueAt(val, r++,col);		
			
		}	
		r=0;
		col++;
		for (String p: proptrimmaxs) {
			Integer val=Integer.valueOf( retriveProp(propd, p,100));
			model.setValueAt(val, r++,col);		
			
		}	
		r=0;
		col++;
		for (String p: proptrimvis) {
			Boolean val=Boolean.valueOf( retriveProp(propd, p,false));
			model.setValueAt(val, r++,col);			}	

	}

	void saveProperties() {
		try (OutputStream output = new FileOutputStream(WorkingDir + "/config.properties")) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			Properties prop = applicationProps;
			int r=0;
			int col=1;
			for (String p: propertiestexts) {
				prop.setProperty(p, (String) model.getValueAt(r++, col));
			}
			r=0;
			col++;
			for (String p: propertiescommands) {
				prop.setProperty(p, (String) model.getValueAt(r++, col));
			}
			r=0;
			col++;
			for (String p: propertiesvisibles) {
				prop.setProperty(p,String.valueOf( model.getValueAt(r++,col)));

			}
			//--------------------------------------------------------
			
			model = (DefaultTableModel) table_1.getModel();
			r=0;
			col=1;
			for (String p: proptrimtexts) {
				prop.setProperty(p, (String) model.getValueAt(r++, col));

				}		
			r=0;
			col++;
			for (String p: proptrimcmds) {
				prop.setProperty(p, (String) model.getValueAt(r++, col));
			}
			r=0;
			col++;
			for (String p: proptrimmins) {
				prop.setProperty(p,String.valueOf( model.getValueAt(r++,col)));
			}	
			r=0;
			col++;
			for (String p: proptrimmaxs) {
				print("maxs col"+String.valueOf(col));
				
				Integer i= (Integer) model.getValueAt(r++, col);
				print("maxs prop "+p);
				print("maxs val "+i.toString());

				prop.setProperty(p,i.toString());
			}	
			r=0;
			col++;
			for (String p: proptrimvis) {
				prop.setProperty(p,String.valueOf( model.getValueAt(r++,col)));

			}	


			// save properties to project root folder
			applicationProps.store(output, "---No Comment---");
			output.close();
			System.out.println(prop);

		} catch (IOException io) {
			io.printStackTrace();
		}

	}
	
	void print(String s) {
		System.out.println(s);
	}
	
	
/***
 * 
 * @param p
 * @param pname
 * @param def
 * @return
 */
	String retriveProp(Properties p, String pname, String def) {
		String val = p.getProperty(pname, def);
		return val;
	}
/***
 * 
 * @param p
 * @param pname
 * @param def
 * @return
 */
	boolean retriveProp(Properties p, String pname, Boolean def) {
		String val = p.getProperty(pname, def.toString());
		return val.equals("true");
	}
/***
 * 
 * @param p
 * @param pname
 * @param def
 * @return
 */
	Double retriveProp(Properties p, String pname, Double def) {
		String val = p.getProperty(pname, def.toString());
		return Double.valueOf(val);
	}
/***
 * 
 * @param p
 * @param pname
 * @param def
 * @return
 */
	Integer retriveProp(Properties p, String pname, Integer def) {
		String val = p.getProperty(pname, def.toString());
		return Integer.valueOf(val);
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

}
