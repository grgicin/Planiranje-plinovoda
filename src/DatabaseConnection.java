

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static Connection connection = null;

    static{
        Object object;
        try {
            JSONParser jsonParser = new JSONParser();

            object = jsonParser.parse(new FileReader("databaseConnectionInfo.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        JSONObject jsonObject =  (JSONObject) object;

        String url = jsonObject.get("url").toString();
        String user =jsonObject.get("user").toString();
        String pass =jsonObject.get("password").toString();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(url,user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Connection getConnection(){return connection;}
}
