package DatabaseLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SupplierDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/CarCare";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final Logger LOGGER = Logger.getLogger(SupplierDatabase.class.getName());
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to establish a database connection", e);
            throw new RuntimeException("Failed to establish a database connection", e);
        }
    }

    public static String getDatabaseUserName(){
        return USERNAME;
    }
    public static String getDatabaseURL(){
        return URL;
    }
    public static String getDatabasePassword(){
        return PASSWORD;
    }

}
