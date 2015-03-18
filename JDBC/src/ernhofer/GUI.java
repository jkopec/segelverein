/**
 * 
 */
package ernhofer;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Graphische Oberflaeche fuer das Programm
 * @author Andi Ernhofer
 * @version 2015-03-18
 */
public class GUI extends JFrame{
	
	private Model m;
	private JTabbedPane jtp;
	private JTable[] table;
	private JScrollPane[] jsp;
	
	/**
	 * Konstruktor
	 * @param m Eine Klasse mit den Methoden
	 */
	public GUI(Model m){
		super("Segelverein");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.m = m;
	}
	
	/*
	 * Initialisiert das Fenster
	 */
	public void init(){
		this.setLayout(new BorderLayout());
		this.jtp = new JTabbedPane();
		String[] tables = m.getTables(); //speichern der Tabellennamen
		this.table = new JTable[tables.length];
		this.jsp = new JScrollPane[tables.length];
		
		//Erzeugen und befuellen der tabs (und der JTabels)
		for(int i = 0; i < tables.length; ++i){
			String[] attribute = m.getAttributes(tables[i]);
			String[][] data = m.getData(tables[i]);
			
			DefaultTableModel tabletmp = new DefaultTableModel(data, attribute);
			table[i] = new JTable(tabletmp);
			
			jsp[i] = new JScrollPane(table[i]);
			
			jtp.add(tables[i], jsp[i]);
		}
		
		this.add(jtp,BorderLayout.CENTER);
		
		//Fensteroptionen
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}