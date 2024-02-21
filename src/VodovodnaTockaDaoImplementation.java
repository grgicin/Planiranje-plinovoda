import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VodovodnaTockaDaoImplementation implements VodovodnaTockaDao{

    Connection connection = DatabaseConnection.getConnection();
    @Override
    public List<VodovodnaTocka> getTockeinVodovod(int idVodovoda) throws SQLException {
        String query = "SELECT * FROM Vodovod " +
                "inner join VodovodnaTocka on VodovodnaTocka.idVodovod = Vodovod.idVodovod " +
                "inner join TipVodovodneTocke on VodovodnaTocka.idTipVodovodneTocke = TipVodovodneTocke.idTipVodovodneTocke " +
                "where Vodovod.idVodovod = ? order by poredVodovod;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idVodovoda);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<VodovodnaTocka> vodovodnaTockaList = new ArrayList<>();
        while (resultSet.next()){
            VodovodnaTocka vodovodnaTocka = new VodovodnaTocka();
            vodovodnaTocka.setIdVodovod(resultSet.getInt(1));
            vodovodnaTocka.setIdVodovodneTocke(resultSet.getInt(3));
            vodovodnaTocka.setLatutude(resultSet.getDouble(4));
            vodovodnaTocka.setLongitude(resultSet.getDouble(5));
            vodovodnaTocka.setPoredVodovod(resultSet.getInt(6));
            vodovodnaTocka.setKomentar(resultSet.getString(7));
            vodovodnaTocka.setIdTipVodovodneTocke(resultSet.getInt(9));
            vodovodnaTockaList.add(vodovodnaTocka);
            //System.out.println(vodovodnaTocka);
        }
        return vodovodnaTockaList;
    }

    @Override
    public void insertVodovodnaTocka(VodovodnaTocka vodovodnaTocka) throws SQLException {
        String query = "INSERT INTO VodovodnaTocka(latitude, longitude, poredVodovod, komentar, idVodovod, idTipVodovodneTocke) VALUES (?, ?, ?, ?, ?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, vodovodnaTocka.getLatutude());
        preparedStatement.setDouble(2, vodovodnaTocka.getLongitude());
        preparedStatement.setInt(3, vodovodnaTocka.getPoredVodovod());
        preparedStatement.setString(4, vodovodnaTocka.getKomentar());
        preparedStatement.setInt(5, vodovodnaTocka.getIdVodovod());
        preparedStatement.setInt(6, vodovodnaTocka.getIdTipVodovodneTocke());
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateVodvodnaTockaPosition(VodovodnaTocka vodovodnaTocka) throws SQLException {
        String query = "UPDATE VodovodnaTocka SET latitude = ?, longitude = ? WHERE (poredVodovod = ?) and (idVodovod = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, vodovodnaTocka.getLatutude());
        preparedStatement.setDouble(2, vodovodnaTocka.getLongitude());
        preparedStatement.setInt(3, vodovodnaTocka.getPoredVodovod());
        preparedStatement.setInt(4, vodovodnaTocka.getIdVodovod());
        preparedStatement.executeUpdate();
    }

    @Override
    public void removeVodovonaTocka(VodovodnaTocka vodovodnaTocka) throws SQLException {
        String query = "DELETE FROM VodovodnaTocka WHERE (latitude = ?) and (longitude = ?) and (poredVodovod = ?) and (idVodovod = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, vodovodnaTocka.getLatutude());
        preparedStatement.setDouble(2, vodovodnaTocka.getLongitude());
        preparedStatement.setInt(3, vodovodnaTocka.getPoredVodovod());
        preparedStatement.setInt(4, vodovodnaTocka.getIdVodovod());
        preparedStatement.executeUpdate();
    }


}
