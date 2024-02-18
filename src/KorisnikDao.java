import java.sql.SQLException;
import java.util.List;

public interface KorisnikDao {
    public int login(Korisnik korisnik) throws SQLException;
    public Korisnik getKorisnik(int id) throws SQLException;
}
