import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	public final String USERNAME = "root";
	public final String PASSWORD = "";
	public final String DATABASE = "finalproject";
	public final String HOST = "localhost:3306";
	public final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

	
	public static ResultSet rs;
	public ResultSetMetaData rsMetaData;
	
	private Connection con;
	private Statement stat;
	private static DBConnection connect;
	
	public static DBConnection getInstance(){
		if(connect == null) {
			return new DBConnection();
		}
		return connect;
	}
	
	public ResultSet executeQuery(String query) {
		try {
			rs = stat.executeQuery(query);
			rsMetaData = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public int executeUpdate(String query) {
		int count = 0;
		try {
			count = stat.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	

	private DBConnection(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			stat = con.createStatement();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
}

