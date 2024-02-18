import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VodovodDaoImplementation implements VodovodDao{
    Connection connection = DatabaseConnection.getConnection();

    @Override
    public List<Vodovod> getVodovodi() throws SQLException {
        String query = "select * from Vodovod;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Vodovod> vodovodList = new ArrayList<>();
        while (resultSet.next()) {
            Vodovod vodovod = new Vodovod();
            vodovod.setId(resultSet.getInt(1));
            vodovod.setNaziv(resultSet.getString(2));
            vodovodList.add(vodovod);
        }
        return vodovodList;
    }

    @Override
    public Vodovod getVodovod(int id) throws SQLException {
        String query = "select * from Vodovod where idVodovod = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Vodovod vodovod = new Vodovod();
        vodovod.setId(resultSet.getInt(1));
        vodovod.setNaziv(resultSet.getString(2));
        return vodovod;
    }

}
