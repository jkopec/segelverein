/**
 * 
 */
package ernhofer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Eine Klasse die Verantwortlich ist f�r das Beenden des Programms
 * @author Andi Ernhofer
 * @version 2015-03-19
 */
public class WListener extends WindowAdapter {
	
	private Connector c;
	
	/**
	 * Konstruktor, der einen Connector �bernimmt
	 * @param c der Connector
	 */
	public WListener(Connector c){
		this.c = c;
	}
	
	@Override
	/**
	 * Eine Klasse, die die Datenbankverbindung beendet und das Programm schlie�t.
	 */
	public void windowClosing(WindowEvent e){
		this.c.close();
		System.exit(0);
	}
}
