/**
 * 
 */
package ernhofer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Andi Ernhofer
 *
 */
public class WListener extends WindowAdapter {
	
	private Connector c;
	
	public WListener(Connector c){
		this.c = c;
	}
	
	@Override
	public void windowClosing(WindowEvent e){
		this.c.close();
		System.exit(0);
	}
}
