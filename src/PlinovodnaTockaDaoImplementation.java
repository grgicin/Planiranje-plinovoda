import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlinovodnaTockaDaoImplementation implements PlinovodnaTockaDao {

    Connection connection = DatabaseConnection.getConnection();
    @Override
    public List<PlinovodnaTocka> getTockeinVodovod(int idVodovoda) throws SQLException {
        String query = "SELECT * FROM Plinovod " +
                "inner join PlinovodnaTocka on PlinovodnaTocka.idVodovod = Plinovod.idVodovod " +
                "inner join TipPlinovodneTocke on PlinovodnaTocka.idTipVodovodneTocke = TipPlinovodneTocke.idTipVodovodneTocke " +
                "where Plinovod.idVodovod = ? order by poredVodovod;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idVodovoda);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<PlinovodnaTocka> plinovodnaTockaList = new ArrayList<>();
        while (resultSet.next()){
            PlinovodnaTocka plinovodnaTocka = new PlinovodnaTocka();
            plinovodnaTocka.setIdVodovod(resultSet.getInt(1));
            plinovodnaTocka.setIdVodovodneTocke(resultSet.getInt(3));
            plinovodnaTocka.setLatutude(resultSet.getDouble(4));
            plinovodnaTocka.setLongitude(resultSet.getDouble(5));
            plinovodnaTocka.setPoredVodovod(resultSet.getInt(6));
            plinovodnaTocka.setKomentar(resultSet.getString(7));
            plinovodnaTocka.setIdTipVodovodneTocke(resultSet.getInt(9));
            plinovodnaTockaList.add(plinovodnaTocka);
            //System.out.println(vodovodnaTocka);
        }
        return plinovodnaTockaList;
    }

    @Override
    public void insertVodovodnaTocka(PlinovodnaTocka plinovodnaTocka) throws SQLException {
        String query = "INSERT INTO PlinovodnaTocka(latitude, longitude, poredVodovod, komentar, idVodovod, idTipVodovodneTocke) VALUES (?, ?, ?, ?, ?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, plinovodnaTocka.getLatutude());
        preparedStatement.setDouble(2, plinovodnaTocka.getLongitude());
        preparedStatement.setInt(3, plinovodnaTocka.getPoredVodovod());
        preparedStatement.setString(4, plinovodnaTocka.getKomentar());
        preparedStatement.setInt(5, plinovodnaTocka.getIdVodovod());
        preparedStatement.setInt(6, plinovodnaTocka.getIdTipVodovodneTocke());
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateVodvodnaTockaPosition(PlinovodnaTocka plinovodnaTocka) throws SQLException {
        String query = "UPDATE PlinovodnaTocka SET latitude = ?, longitude = ? WHERE (poredVodovod = ?) and (idVodovod = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, plinovodnaTocka.getLatutude());
        preparedStatement.setDouble(2, plinovodnaTocka.getLongitude());
        preparedStatement.setInt(3, plinovodnaTocka.getPoredVodovod());
        preparedStatement.setInt(4, plinovodnaTocka.getIdVodovod());
        preparedStatement.executeUpdate();
    }

    @Override
    public void removeVodovonaTocka(PlinovodnaTocka plinovodnaTocka) throws SQLException {
        String query = "DELETE FROM PlinovodnaTocka WHERE (latitude = ?) and (longitude = ?) and (poredVodovod = ?) and (idVodovod = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, plinovodnaTocka.getLatutude());
        preparedStatement.setDouble(2, plinovodnaTocka.getLongitude());
        preparedStatement.setInt(3, plinovodnaTocka.getPoredVodovod());
        preparedStatement.setInt(4, plinovodnaTocka.getIdVodovod());
        preparedStatement.executeUpdate();
    }


}
