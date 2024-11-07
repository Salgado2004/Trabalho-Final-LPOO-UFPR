package br.ufpr.lpoo.models.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.System.exit;

public class ConnectionFactory {
    private static Properties properties;
    private ConnectionFactory(){}

    public static Connection getConnection() throws SQLException {
        readProperties();
        return DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
    }

    public static void readProperties() {
        if (properties == null) {
            try (FileInputStream file = new FileInputStream("./src/main/java/resources/application.properties")) {
                Properties props = new Properties();
                props.load(file);
                properties = props;
            } catch (IOException e) {
                exit(400);
            }
        }
    }
}
