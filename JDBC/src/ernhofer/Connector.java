package ernhofer;

import java.sql.*;

import org.postgresql.ds.PGSimpleDataSource;

/**
 * Verbindet das Programm mit der Datenbank
 * @author Andi Ernhofer
 * @version 2015-03-18
 */
public class Connector {

	private Connection con;
	private Statement st;
	private ResultSet rs;
	private DatabaseMetaData dmd;

	/**
	 * Stellt eine verbindung mit der Datenbank her
	 * @param servername Die Addresse des Servers
	 * @param dbname Der Name der Datenbank
	 * @param user Der Name des Benutzers
	 * @param password Das Passwort des Benutzers
	 */
	public void connect(String servername, String dbname, String user, String password){
		// Datenquelle erzeugen und konfigurieren
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setServerName(servername);
		ds.setDatabaseName(dbname);
		ds.setUser(user);
		ds.setPassword(password);

		//Verbindung herstellen
		try {
			this.con = ds.getConnection();
			
			//AUTOCOMMIT deaktivieren
			this.con.setAutoCommit(false);
			
			this.dmd = con.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Fuehrt eine query in der Datenbank aus
	 * @param query Die Query
	 * @return //Gibt die Ausgabe der Datenank zurueck
	 */
	public ResultSet executeStatement(String query){
		try {
			this.st = this.con.createStatement();
			this.rs = this.st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Fuehrt eine query in der Datenbank aus
	 * @param query Die Query
	 * @return //Gibt die Ausgabe der Datenank zurueck
	 */
	public void execute(String command){
		try {
			this.st = this.con.createStatement();
			this.st.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Fehler beim ausführen des Befehls7");
		}
	}
	
	/**
	 * Gibt die Metadaten der Datenbank zurueck
	 * @return Die Metadaten
	 */
	public DatabaseMetaData getDmd(){
		return this.dmd;
	}

	/**
	 * Schließt die verbindung
	 */
	public void close(){
		try {
			this.rs.close();
			this.st.close();
			this.con.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
