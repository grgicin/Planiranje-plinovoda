public class Korisnik {
    private int id;
    private String ime;
    private String prezime;
    private String brojTelefona;
    private String email;
    private String lozinka;
    private int idUloga;
    private int odobreni;


    public Korisnik() {
    }

    public Korisnik(int id, String ime, String prezime, String brojTelefona, String email, String lozinka, int idUloga, int odobreni) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.brojTelefona = brojTelefona;
        this.email = email;
        this.lozinka = lozinka;
        this.idUloga = idUloga;
        this.odobreni = odobreni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public int getIdUloga() {
        return idUloga;
    }

    public void setIdUloga(int idUloga) {
        this.idUloga = idUloga;
    }

    public int getOdobreni() {
        return odobreni;
    }

    public void setOdobreni(int odobreni) {
        this.odobreni = odobreni;
    }
}
