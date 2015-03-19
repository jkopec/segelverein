/**
 * 
 */
package ernhofer;

import java.sql.*;
import java.util.*;

import javax.swing.JTextField;

/**
 * Klasse zur bereitstellug vpn Methoden
 * @author Andi Ernhofer
 * @version 2015-03-18
 */
public class Model {

	private Connector c;
	private DatabaseMetaData dmd;

	/**
	 * Konstruktor
	 * @param c Eine Datenbankverbindung 
	 */
	public Model(Connector c){
		this.c = c;
		this.dmd = c.getDmd();
	}

	/**
	 * Gibt die Namen aller Tabellen einer DB in einem String-Array zurueck
	 * @return die Namen aller Tabellen einer DB
	 */
	public String[] getTables(){
		String[] re = new String[1];
		ArrayList<String> tables = new ArrayList<String>();
		try {
			ResultSet t = dmd.getTables(null, "public", null, new String[] {"TABLE"});
			while(t.next()){
				tables.add(t.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return tables.toArray(re);
	}

	/** 
	 * Gibt die Namen aller Spalten einer Tabelle zurueck
	 * @param table der Tabellenname, aus dem die Spalten ausgelesen werden sollen
	 * @return die Namen aller Spalten dieser Tabelle
	 */
	public String[] getAttributes(String table){
		String[] re = new String[1];
		ArrayList<String> columns = new ArrayList<String>();
		try {
			ResultSet t = dmd.getColumns(null, null, table, null);
			while(t.next()){
				columns.add(t.getString("COLUMN_NAME"));
			}
		} catch (SQLException e){
			System.out.println(e.getMessage()); 
		}
		return columns.toArray(re);
	}

	/**
	 * Gibt alle Daten einer Tabelle zurueck
	 * @param table der Tabellenname, von dem die Daten ausgelesen werden sollen
	 * @return Alle Daten dieser Tabelle
	 */
	public String[][] getData(String table){
		String[][] re = new String[1][1];
		ArrayList<String> row = new ArrayList();
		ArrayList<String[]> data = new ArrayList();
		String[] r = new String[1];

		String query = "SELECT * FROM " + table + ";";
		ResultSet rs = c.executeStatement(query);

		String[] attribut = getAttributes(table);

		try {
			while(rs.next()){
				row = new ArrayList();
				for(int i = 0; i < attribut.length;++i){
					row.add(rs.getString(attribut[i]));
				}
				r = new String[1];
				data.add(row.toArray(r));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.toArray(re);
	}
	
	/**
	 * Erzeugt einen insert befehl und führt diesen aus
	 * @param table der name der Tabelle
	 * @param map Die daten die hineingespeichert werden sollen
	 */
	public void createInsert(String table, HashMap map){
		Set settmp = map.keySet();
		Iterator it = settmp.iterator();
		
		String insert = "INSERT INTO " + table + " (";
		
		//Erzeugen der reihenfolge der Spaten in die eingefügt werden soll
		while(it.hasNext()){
			//Insert erzeugen mit zuerst key VALUES value holen mit key
			insert += (it.next());
			if(it.hasNext()){
				insert += ",";
			}
		}
		insert += ") VALUES (";
		
		//Schreiben der Inhalte die gespeichert weden sollen
		it = settmp.iterator();
		while(it.hasNext()){
			JTextField tfield = (JTextField) map.get(it.next());
			insert += "'"+tfield.getText()+"'";
			if(it.hasNext()){
				insert += ",";
			}
		}
		insert += ");";
		//System.out.println(insert);
		
		//Ausführen des Befehls
		c.execute("begin;");
		c.execute(insert);
		c.execute("commit;");
	}
	
	/**
	 * Erzeugt einen UPDATE Befehl und führt diesen aus
	 * @param table Der Tabellenname
	 * @param neu Die neuen Werte
	 * @param old Die alten werte
	 */
	public void createUpdate(String table, HashMap neu, HashMap old){
		Set settmp = old.keySet();
		Iterator it = settmp.iterator();
		
		String update = "UPDATE " + table + " SET ";
		
		//Festlegen der zu ueberschreibenden werte
		while(it.hasNext()){
			String key = it.next().toString();
			JTextField tfield = (JTextField) neu.get(key);
			update += key+"='"+tfield.getText()+"'";
			if(it.hasNext()){
				update += ", ";
			}
		}
		update += "WHERE ";
		settmp = neu.keySet();
		it = settmp.iterator();
		
		//Angeben des zu überschreibenden Eintrages
		while(it.hasNext()){
			String key = it.next().toString();
			update += key+"='"+old.get(key)+"'";
			if(it.hasNext()){
				update += " AND ";
			}
		}
		update +=";";
		
		//ausführen des Befehls
		System.out.println(update);
		c.execute("begin;");
		c.execute(update);
		c.execute("commit;");
	}
	
	/**
	 * Erzeugt einen delete befehl und führt diesen aus
	 * @param table der Tabellename
	 * @param map Die zu überschreibenden werte
	 */
	public void createDelete(String table, HashMap map){
		Set settmp = map.keySet();
		Iterator it = settmp.iterator();
		
		String delete = "DELETE FROM " + table + " WHERE ";
		
		//Gibt die edingungen zur wahl des zu löschenden eintrages
		while(it.hasNext()){
			String key = (String) it.next();
			delete += key +"='" + map.get(key)+ "'";
			if(it.hasNext()){
				delete += " AND ";
			}
		}
		delete += ";";
		System.out.println(delete);
		
		//Auaführen des Befehls
		c.execute("begin;");
		c.execute(delete);
		c.execute("commit;");
	}
	
	/**
	 * Gibt den Connector zurück
	 * @return der Connector
	 */
	public Connector getC(){
		return this.c;
	}
}
