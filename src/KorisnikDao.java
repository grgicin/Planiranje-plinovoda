import java.util.List;

public interface KorisnikDao {
    public int login(Korisnik korisnik);
    public Korisnik getKorisnik(int id);
}
