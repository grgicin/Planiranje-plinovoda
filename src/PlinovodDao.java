import java.sql.SQLException;
import java.util.List;

public interface PlinovodDao {
    public List<Plinovod> getVodovodi() throws SQLException;
    public Plinovod getVodovod(int id) throws SQLException;
    public void newVodovod(String naziv) throws SQLException;
}
