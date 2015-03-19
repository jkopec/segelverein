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
	private JPanel haupt;
	private JButton[] menuebutton;
	private JLabel menueueberschrift;
	private Font menuefont;
	private AListener al;
	private HashMap<String,JTextField> felder;
	private HashMap<String,String> old;

	/**
	 * Konstruktor, der die wichtigsten Variablen initialisiert
	 * @param m Eine Klasse mit den Methoden
	 */
	public GUI(Model m){
		super("Segelverein");
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.m = m;
		this.addWindowListener(new WListener(m.getC()));
		
		//Initialisieren der Variablen
		this.jtp = new JTabbedPane();
		this.menuepanel = new JPanel();
		this.haupt = new JPanel();
		
		//Initialisieren der Schriftart für die 
		this.menuefont = new Font("Calibri",Font.PLAIN,25);
		
		//Einstellungen für die Überschrift
		this.menueueberschrift = new JLabel("Menü");
		this.menueueberschrift.setFont(menuefont);
		this.menueueberschrift.setHorizontalAlignment(getWidth()/2);
	}

	/*
	 * Initialisiert das Fenster
	 * @param index Der Tab der selectiert werden soll
	 */
	public void init(int index){
		
		//Erzeugt die JTables
		createView();
		
		//erzeugt das Hauptmenue
		setMenuePanelHaupt();
		if(index >-1 && index < jtp.getTabCount()){
			jtp.setSelectedIndex(index);
		}
		
		//Hinzufügen der Elemente zum Fenter
		this.add(haupt,BorderLayout.CENTER);
		this.add(menuepanel, BorderLayout.EAST);

		//Fensteroptionen
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Erzeugt die JTables in Tabs n einem Panel
	 */
	public void createView(){
		
		haupt.remove(jtp);
		jtp.removeAll();
		
		haupt.setLayout(new BorderLayout());
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
		
		haupt.add(jtp);		
	}

	/**
	 * Befuellt das Panel fuer das Hauptmenue
	 */
	public void setMenuePanelHaupt(){

		menuepanel.removeAll();
		
		this.menuebutton = new JButton[4];
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
		this.menuebutton[3].setText("Refresh");
		this.menuebutton[3].setActionCommand("refresh");

		//Hinzufügen der einzelnen Elemente
		this.menuepanel.add(new JPanel());
		this.menuepanel.add(menueueberschrift);
		for(int i = 0; i < this.menuebutton.length;++i){
			this.menuepanel.add(menuebutton[i]);
		}
		
		menuepanel.revalidate();
	}

	/**
	 * Befuellt das Panel zur Eingabe eines neuen Wertes
	 * @param table Der Tabellenname in die Eingefuegt werden soll
	 */
	public void setMenuePanelNeu(String table){
		menuepanel.removeAll();
		menuepanel.setLayout(new GridLayout(16,1));
		menuepanel.add(new JPanel());
		menuepanel.add(menueueberschrift);
		
		//Einstellungen fuer die Buttons
		menuebutton = new JButton[2];
		for(int i = 0; i < this.menuebutton.length;++i){
			this.menuebutton[i] = new JButton();
			this.menuebutton[i].addActionListener(al);
		}
		this.menuebutton[0].setText("Speichern");
		this.menuebutton[0].setActionCommand("speichern");
		this.menuebutton[1].setText("Abbrechen");
		this.menuebutton[1].setActionCommand("abbrechen");
		
		//Panel fuer die buttons
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,2));
		buttons.add(menuebutton[0]);
		buttons.add(menuebutton[1]);
		
		felder = new HashMap();
		String attribut[] = m.getAttributes(table); //Spaltennamen
		
		//Ereugt die richtige Anzahl an Eingabefeldern
		for(int i = 0; i < attribut.length; ++i){
			JLabel label = new JLabel(attribut[i] + ": ");
			
			JTextField tfield = new JTextField();
			
			menuepanel.add(label);
			menuepanel.add(tfield);
			
			felder.put(attribut[i], tfield);
		}
		
		menuepanel.add(new JPanel());
		menuepanel.add(buttons);

		menuepanel.revalidate();
	}
	
	/**
	 * Befuellt das Panel zur AEnderung eines Eintrages
	 * @param table Der Tabellenname in dem etwas geaendert werden soll
	 */
	public void SetMenuePanelAendern(String table){
		menuepanel.removeAll();
		menuepanel.setLayout(new GridLayout(16,1));
		menuepanel.add(new JPanel());
		menuepanel.add(menueueberschrift);
		
		//Einstellungen fuer die Buttons
		menuebutton = new JButton[2];
		for(int i = 0; i < this.menuebutton.length;++i){
			this.menuebutton[i] = new JButton();
			this.menuebutton[i].addActionListener(al);
		}
		this.menuebutton[0].setText("Speichern");
		this.menuebutton[0].setActionCommand("aendernspeichern");
		this.menuebutton[1].setText("Abbrechen");
		this.menuebutton[1].setActionCommand("abbrechen");
		
		//Panel fuer die Buttons
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,2));
		buttons.add(menuebutton[0]);
		buttons.add(menuebutton[1]);
		
		felder = new HashMap();
		old = new HashMap();
		String attribut[] = m.getAttributes(table);
		
		//Erzeugt die richtige anzahl an Eingabefeldern und befüllt diese mit den aktuellen werten
		for(int i = 0; i < attribut.length; ++i){
			JLabel label = new JLabel(attribut[i] + ": ");
			
			JTable jtable = this.table[jtp.getSelectedIndex()];
			String value = ((DefaultTableModel)jtable.getModel()).getValueAt(jtable.getSelectedRow(), i).toString(); //Der alte Wert
			
			JTextField tfield = new JTextField();
			tfield.setText(value);
			
			menuepanel.add(label);
			menuepanel.add(tfield);
			
			felder.put(attribut[i], tfield);
			old.put(attribut[i], value);
		}
		
		menuepanel.add(new JPanel());
		menuepanel.add(buttons);

		menuepanel.revalidate();
	}
	
	/**
	 * Gibt die Tabellen zurück
	 * @return die Tabellen
	 */
	public JTable[] getTable() {
		return table;
	}

	/**
	 * Gibt die Tabs zurück
	 * @return die Tabs
	 */
	public JTabbedPane getJtp() {
		return jtp;
	}

	/**
	 * Wählt das Eingabemenü aktuelle anzeige
	 */
	public void useMenuePanelNeu(){
		setMenuePanelNeu(getActiveTable());
		this.repaint();
	}
	
	/**
	 * Wählt das Hauptmenü als aktuelle anzeige
	 */
	public void useMenuepanelHaupt(){
		setMenuePanelHaupt();
		this.repaint();
	}
	
	/**
	 * Wählt das Panel zum Ändern als aktuelle anzeige
	 */
	public void useMenuePanelAendern(){
		SetMenuePanelAendern(getActiveTable());
		this.repaint();
	}
	
	/**
	 * Gib den Namen der aktuellen Tabelle zurück
	 * @return der Name der aktuellen Tabelle
	 */
	public String getActiveTable(){
		return jtp.getTitleAt(jtp.getSelectedIndex());
	}
	
	/**
	 * Gibt die Werte die neu gespeichertwerden zurück
	 * @return die Werte die neu gespeichert werden müssen
	 */
	public HashMap getMap(){
		return this.felder;
	}
	
	/**
	 * Gibt die alen Werte zurück
	 * @return die alten Werte
	 */
	public HashMap getMapOld(){
		return this.old;
	}
}