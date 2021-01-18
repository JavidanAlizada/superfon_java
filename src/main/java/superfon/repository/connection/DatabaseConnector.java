package superfon.repository.connection;

import superfon.util.PropertyReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnector {
    private static final String JDBC_DRIVER = PropertyReader.getPropByKey("db_driver");
    private static final String DB_URL = PropertyReader.getPropByKey("db_url");
    private static final String USER = PropertyReader.getPropByKey("db_user");
    private static final String PASS = PropertyReader.getPropByKey("db_passwd");

    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            if (DB_URL != null) {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            } else
                return null;
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
        return conn;
    }

//    public static void main(String[] args) {
//        Connection connection = new DatabaseConnector().getConnection();
//        System.out.println(connection);
//    }
}
