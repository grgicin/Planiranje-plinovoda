import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static Connection connection = null;
    static{
        String url ="";
        String user ="";
        String pass = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            DriverManager.getConnection(url,user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Connection getConnection(){return connection;}
}
