/**
 * 
 */
package ernhofer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Andi Ernhofer
 *
 */
public class AListener implements ActionListener {
	
	private GUI gui;
	private Model m;
	
	public AListener(GUI gui, Model m){
		this.gui = gui;
		this.m = m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String acom = e.getActionCommand();
		JTable table;
		switch(acom){
		case("neu"):
			gui.useMenuePanelNeu();
			break;
		case("aendern"):
			table = gui.getTable()[gui.getJtp().getSelectedIndex()];
		//for(int i = 0; )
			((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), table.getSelectedColumn());
			break;
		case("loeschen"):
			table = gui.getTable()[gui.getJtp().getSelectedIndex()];
			int row = table.getSelectedRow();
			HashMap<String,String> map = new HashMap();
			for(int i = 0; i<table.getColumnCount();++i){
				String tname = table.getColumnName(i);
				String value = (String) ((DefaultTableModel)table.getModel()).getValueAt(row, i);
				map.put(tname, value);
			}
			m.createDelete(gui.getActiveTable(),map);
			
			gui.init();
			gui.repaint();
			gui.useMenuepanelHaupt();
			break;
		case("speichern"):
			m.createInsert(gui.getActiveTable(), gui.getMap());
			gui.init();
			gui.repaint();
			
		case("abbrechen"):
			gui.useMenuepanelHaupt();
			break;
		}
	}
}
