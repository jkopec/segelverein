/**
 * 
 */
package ernhofer;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author Andi Ernhofer
 *
 */
public class GUI extends JFrame{
	
	private Model m;
	private JTabbedPane jtp;
	private JTable[] table;
	private JScrollPane[] jsp;
	
	public GUI(Model m){
		super("Segelverein");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.m = m;
	}
	
	public void init(){
		this.setLayout(new BorderLayout());
		this.jtp = new JTabbedPane();
		String[] tables = m.getTables();
		this.table = new JTable[tables.length];
		this.jsp = new JScrollPane[tables.length];
		
		for(int i = 0; i < tables.length; ++i){
			String[][] data = m.getData(tables[i]);
			String[] attribute = m.getAttributes(tables[i]);
			
			DefaultTableModel tabletmp = new DefaultTableModel(data, attribute);
			table[i] = new JTable(tabletmp);
			
			jsp[i] = new JScrollPane(table[i]);
			
			jtp.add(tables[i], jsp[i]);
		}
		
		this.add(jtp);
		
		/*
		String[][] werte = m.getData("person");
		for(int i = 0; i < werte.length; ++i){
			for(int j = 0; j < werte[i].length;++j){
			System.out.print(werte[i][j] + " ");
			}
			System.out.println();
		}
		*/
		
	}
}