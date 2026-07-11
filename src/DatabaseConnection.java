import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.FileInputStream;

public class DatabaseConnection {

    private static Connection connection;

    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {

                Properties properties = new Properties();

                FileInputStream fis = new FileInputStream("db.properties");

                properties.load(fis);

                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String password = properties.getProperty("db.password");

                connection = DriverManager.getConnection(url, username, password);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}