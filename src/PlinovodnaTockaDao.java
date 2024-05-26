import java.sql.SQLException;
import java.util.List;

public interface PlinovodnaTockaDao {
    public List<PlinovodnaTocka> getTockeinVodovod(int idVodovoda) throws SQLException;
    public void insertVodovodnaTocka(PlinovodnaTocka plinovodnaTocka) throws SQLException;
    public void updateVodvodnaTockaPosition(PlinovodnaTocka plinovodnaTocka) throws SQLException;
    public void removeVodovonaTocka(PlinovodnaTocka plinovodnaTocka) throws SQLException;
}
