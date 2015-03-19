package ernhofer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Andi Ernhofer
 * @version 2015-03-19
 */
public class AListener implements ActionListener {
	
	private GUI gui;
	private Model m;
	
	/**
	 * Konstruktor
	 * @param gui Eine Graphische Oberfläche
	 * @param m Eine Model Klasse
	 */
	public AListener(GUI gui, Model m){
		this.gui = gui;
		this.m = m;
	}

	@Override
	/**
	 * Eine Methode, die bestimmt, was Passiert, wenn ein Button gedrückt wird
	 */
	public void actionPerformed(ActionEvent e) {
		
		String acom = e.getActionCommand();
		JTable table;
		
		switch(acom){
		case("neu"):
			gui.useMenuePanelNeu();
			break;
			
		case("aendern"):
			try{
				gui.useMenuePanelAendern();
			}catch(ArrayIndexOutOfBoundsException e1){
				System.out.println("Keine Zeile ausgewählt");
				gui.init(gui.getJtp().getSelectedIndex());
				gui.repaint();
				gui.useMenuepanelHaupt();
			}
			break;
			
		case("loeschen"):
			try{
			table = gui.getTable()[gui.getJtp().getSelectedIndex()];
			int row = table.getSelectedRow();
			HashMap<String,String> map = new HashMap();
			for(int i = 0; i<table.getColumnCount();++i){
				String tname = table.getColumnName(i);
				String value = (String) ((DefaultTableModel)table.getModel()).getValueAt(row, i);
				map.put(tname, value);
			}
				m.createDelete(gui.getActiveTable(),map);
			}catch(Exception e1){
				System.out.println("Fehler beim löschen");
			}
			gui.init(gui.getJtp().getSelectedIndex());
			gui.repaint();
			gui.useMenuepanelHaupt();
			break;
			
		case("aendernspeichern"):
			try{
				m.createUpdate(gui.getActiveTable(), gui.getMap(),gui.getMapOld());
			}catch(Exception e1){
				System.out.println("Fehler beim überspeichern");
			}
			gui.init(gui.getJtp().getSelectedIndex());
			gui.useMenuepanelHaupt();
			gui.repaint();
			break;
			
		case("speichern"):
			try{
				m.createInsert(gui.getActiveTable(), gui.getMap());
			}catch(Exception e1){
				System.out.println("Fehler beim Speichern");
			}
			gui.init(gui.getJtp().getSelectedIndex());
			gui.repaint();
			//Kein break, weil der Inhalt von abbrechen auch ausgeführt wird
			
		case("abbrechen"):
			gui.useMenuepanelHaupt();
			break;
			
		case("refresh"):
			System.out.println(gui.getJtp().getSelectedIndex());
			gui.init(gui.getJtp().getSelectedIndex());
			gui.repaint();
			break;
		}
	}
}
