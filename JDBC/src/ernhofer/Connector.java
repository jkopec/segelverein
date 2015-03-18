package ernhofer;

import java.sql.*;

import org.postgresql.ds.PGSimpleDataSource;

/**
 * @author Andi Ernhofer
 *
 */
public class Connector {

	private Connection con;
	private Statement st;
	private ResultSet rs;
	private DatabaseMetaData dmd;

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
			this.dmd = con.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	
	public DatabaseMetaData getDmd(){
		return this.dmd;
	}

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
