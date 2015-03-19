/**
 * 
 */
package ernhofer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

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
	private JPanel menuepanel;
	private JButton[] menuebutton;
	private JLabel menueueberschrift;
	private Font menuefont;
	private AListener al;
	private HashMap<String,JTextField> felder;

	/**
	 * Konstruktor
	 * @param m Eine Klasse mit den Methoden
	 */
	public GUI(Model m){
		super("Segelverein");
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.m = m;
		this.addWindowListener(new WListener(m.getC()));
	}

	/*
	 * Initialisiert das Fenster
	 */
	public void init(){
		this.setLayout(new BorderLayout());
		this.jtp = new JTabbedPane();
		this.menuepanel = new JPanel();
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
		
		//Initialisieren der Schriftart für die 
		this.menuefont = new Font("Calibri",Font.PLAIN,27);
		
		//Einstellungen für die Überschrift
				this.menueueberschrift = new JLabel("Menü");
				this.menueueberschrift.setFont(menuefont);
		this.menueueberschrift.setHorizontalAlignment(getWidth()/2);
		setMenuePanelHaupt();
		
		//Hinzufügen der Elemente zum Fenter
		this.add(jtp,BorderLayout.CENTER);
		this.add(menuepanel, BorderLayout.EAST);

		//Fensteroptionen
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void setMenuePanelHaupt(){

		menuepanel.removeAll();
		
		this.menuebutton = new JButton[3];
		this.al = new AListener(this,this.m);

		//Layout fürs Menü
		this.menuepanel.setLayout(new GridLayout(10,1));

		//Initialisieren der Buttons
		for(int i = 0; i < this.menuebutton.length;++i){
			this.menuebutton[i] = new JButton();
			this.menuebutton[i].addActionListener(al);
		}

		//Beschriften der Buttons
		this.menuebutton[0].setText("Neu");
		this.menuebutton[0].setActionCommand("neu");
		this.menuebutton[1].setText("Ändern");
		this.menuebutton[1].setActionCommand("aendern");
		this.menuebutton[2].setText("Löschen");
		this.menuebutton[2].setActionCommand("loeschen");

		//Hinzufügen der einzelnen Elemente
		this.menuepanel.add(new JPanel());
		this.menuepanel.add(menueueberschrift);
		for(int i = 0; i < this.menuebutton.length;++i){
			this.menuepanel.add(menuebutton[i]);
		}
		
		menuepanel.revalidate();
	}

	public void setMenuePanelNeu(String table){
		menuepanel.removeAll();
		menuepanel.setLayout(new GridLayout(16,1));
		menuepanel.add(new JPanel());
		menuepanel.add(menueueberschrift);
		
		menuebutton = new JButton[2];
		
		for(int i = 0; i < this.menuebutton.length;++i){
			this.menuebutton[i] = new JButton();
			this.menuebutton[i].addActionListener(al);
		}
		
		this.menuebutton[0].setText("Speichern");
		this.menuebutton[0].setActionCommand("speichern");
		this.menuebutton[1].setText("Abbrechen");
		this.menuebutton[1].setActionCommand("abbrechen");
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,2));
		
		buttons.add(menuebutton[0]);
		buttons.add(menuebutton[1]);
		
		felder = new HashMap();
		String attribut[] = m.getAttributes(table);
		
		for(int i = 0; i < attribut.length; ++i){
			JLabel label = new JLabel(attribut[i] + ": ");
			
			JTextField tfield = new JTextField();
			//tfield.setAlignmentX(100);
			
			menuepanel.add(label);
			menuepanel.add(tfield);
			
			felder.put(attribut[i], tfield);
		}
		
		menuepanel.add(new JPanel());
		menuepanel.add(buttons);

		menuepanel.revalidate();
	}
	
	public void SetMenuePanelAendern(){
		
	}
	
	public void useMenuePanelNeu(){
		setMenuePanelNeu(getActiveTable());
		this.repaint();
	}
	
	public void useMenuepanelHaupt(){
		setMenuePanelHaupt();
		this.repaint();
	}
	
	public void useMenuePanelAendern(){
		
	}
	
	public String getActiveTable(){
		return jtp.getTitleAt(jtp.getSelectedIndex());
	}
	
	public HashMap getMap(){
		return this.felder;
	}
}