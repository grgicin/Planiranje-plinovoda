import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlinovodDaoImplementation implements PlinovodDao {
    Connection connection = DatabaseConnection.getConnection();

    @Override
    public List<Plinovod> getVodovodi() throws SQLException {
        String query = "select * from Plinovod;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Plinovod> plinovodList = new ArrayList<>();
        while (resultSet.next()) {
            Plinovod plinovod = new Plinovod();
            plinovod.setId(resultSet.getInt(1));
            plinovod.setNaziv(resultSet.getString(2));
            plinovodList.add(plinovod);
        }
        return plinovodList;
    }

    @Override
    public Plinovod getVodovod(int id) throws SQLException {
        String query = "select * from Plinovod where idVodovod = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Plinovod plinovod = new Plinovod();
        plinovod.setId(resultSet.getInt(1));
        plinovod.setNaziv(resultSet.getString(2));
        return plinovod;
    }

    @Override
    public void newVodovod(String naziv) throws SQLException {
        String query = "INSERT INTO Plinovod (naziv) VALUES (?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, naziv);
        preparedStatement.executeUpdate();
    }

}
