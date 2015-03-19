/**
 * 
 */
package ernhofer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Andi
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
		switch(acom){
		case("neu"):
			gui.useMenuePanelNeu();
			break;
		case("aendern"):
			
			break;
		case("loeschen"):
			
			break;
		case("speichern"):
			m.createInsert(gui.getActiveTable(), gui.getMap());
			
		case("abbrechen"):
			gui.useMenuepanelHaupt();
			break;
		}
	}
}
