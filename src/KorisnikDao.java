import java.sql.SQLException;
import java.util.List;

public interface KorisnikDao {
    int korisnikLogin(Korisnik korisnik) throws SQLException;
    void korisnikRegister(Korisnik korisnik) throws SQLException;
    Korisnik getKorisnik(int id) throws SQLException;
    List<Korisnik> getKorinsikList() throws SQLException;
    void odobriKorisnika(int id) throws SQLException;
    void updateKorisnik(Korisnik korisnik) throws SQLException;
    void removeKorisnik(int id) throws SQLException;
    void updateLoznikaKorniksnik(Korisnik korisnik) throws SQLException;
}
