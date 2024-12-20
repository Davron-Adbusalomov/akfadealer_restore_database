package duol.restore.akfadealer_restore_database.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConfig {

    // Method to load database config and establish connection
    public static Connection getDatabaseConnection() {
        Properties properties = new Properties();

        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db-config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find db-config.properties");
                return null;
            }
            properties.load(input);

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            String driver = properties.getProperty("db.driver");

            // Load the driver class
            Class.forName(driver);

            // Establish the connection
            Connection connection = DriverManager.getConnection(url, username, password);
            if (connection != null){
                System.out.println("Connection established..");
            }
            return connection;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
