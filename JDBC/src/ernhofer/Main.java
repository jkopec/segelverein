package ernhofer;

/**
 * Fuehrt das Programm aus.
 * @author Andi Ernhofer
 * @version 2015-03-18
 */
public class Main {
	public static void main (String[] args){
		Connector c = new Connector();
		c.connect("school", "segelverein", "segel", "segel");
		Model m = new Model(c);
		GUI gui = new GUI(m);
		gui.init(-1);
	}
}