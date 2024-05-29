package Server.Database;
import Values.Value;
import raven.toast.Notifications;
import java.sql.*;
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return  instance;
    }

    private DatabaseConnection() {}

    public void connectToDatabase() throws SQLException {
        connection = DriverManager.getConnection(Value.url);
        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Connected To The Database");
    }

    public Connection getConnection() {
        return connection;
    }

}
