/**
 * 
 */
package ernhofer;

/**
 * @author Andi
 *
 */
public class Main {
	public static void main (String[] args){
		Connector c = new Connector();
		c.connect("192.168.48.128", "segelverein", "segel", "segel");
		Model m = new Model(c);
		GUI gui = new GUI(m);
		gui.init();
	}
}