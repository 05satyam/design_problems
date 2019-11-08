package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CrawlerRepositiory {

	private static final String SQL = "";

	public ResultSet getUrlResultSet(String url) throws Throwable {
		DatabaseConnection db = new DatabaseConnection();
		try {
			db.createConnection();
			String sql = "select * from Record where URL = '" + url + "'";
			return db.runSql(sql);

		} catch (Exception e) {
			throw new Exception();
		} finally {
			db.finalize();

		}
	}

	public void insertUrlIntoRepositiory(String url) throws Throwable {
		DatabaseConnection db = new DatabaseConnection();
		try {
			String sql = "INSERT INTO  `Crawler`.`Record` " + "(`URL`) VALUES " + "(?);";
			db.createConnection();
			PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, url);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.finalize();

		}
	}
}
