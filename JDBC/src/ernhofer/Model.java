/**
 * 
 */
package ernhofer;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Andi Ernhofer
 *
 */
public class Model {

	private Connector c;
	private DatabaseMetaData dmd;

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
	 * @param table ein Tabellenname, aus dem die Spalten ausgelesen werden sollen
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
		} return columns.toArray(re);
	}

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
		// for rs.next mit den .getString(tablename);

		return data.toArray(re);
	}
}
