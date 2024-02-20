import java.sql.SQLException;
import java.util.List;

public interface VodovodnaTockaDao {
    public List<VodovodnaTocka> getTockeinVodovod(int idVodovoda) throws SQLException;
    public void insertVodovodnaTocka(VodovodnaTocka vodovodnaTocka) throws SQLException;
    public void updateVodvodnaTockaPosition(VodovodnaTocka vodovodnaTocka) throws SQLException;
}
