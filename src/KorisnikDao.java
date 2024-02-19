import java.sql.SQLException;

public interface KorisnikDao {
    public int korisnikLogin(Korisnik korisnik) throws SQLException;
    public void korisnikRegister(Korisnik korisnik) throws SQLException;
    public Korisnik getKorisnik(int id) throws SQLException;
}
