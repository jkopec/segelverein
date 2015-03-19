/**
 * 
 */
package ernhofer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
	
	public void createInsert(String table, HashMap map){
		Set settmp = map.keySet();
		
		Iterator it = settmp.iterator();
		
		while(it.hasNext()){
			//Insert erzeugen mit zuerst key VALUES value holen mit key
		}
	}
	
	public Connector getC(){
		return this.c;
	}
}
