import java.sql.SQLException;
import java.util.List;

public interface VodovodDao {
    public List<Vodovod> getVodovodi() throws SQLException;
    public Vodovod getVodovod(int id) throws SQLException;
}
