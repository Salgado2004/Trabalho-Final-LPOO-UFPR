package br.ufpr.lpoo.models.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static Properties properties;

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        readProperties();
        return DriverManager.getConnection(properties.getProperty("mysql.database.url"),
                properties.getProperty("mysql.database.username"),
                properties.getProperty("mysql.database.password"));
    }

    public static void readProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.put("mysql.database.url", System.getenv("mysql_database_url"));
            properties.put("mysql.database.username", System.getenv("mysql_database_username"));
            properties.put("mysql.database.password", System.getenv("mysql_database_password"));
        }
    }
}
