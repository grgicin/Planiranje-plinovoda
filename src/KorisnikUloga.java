public class KorisnikUloga {
    private int idKorisnikUloga;
    private String naziv;

    @Override
    public String toString() {
        return naziv;
    }

    public int getIdKorisnikUloga() {
        return idKorisnikUloga;
    }

    public void setIdKorisnikUloga(int idKorisnikUloga) {
        this.idKorisnikUloga = idKorisnikUloga;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public KorisnikUloga(int idKorisnikUloga, String naziv) {
        this.idKorisnikUloga = idKorisnikUloga;
        this.naziv = naziv;
    }

    public KorisnikUloga() {
    }
}
