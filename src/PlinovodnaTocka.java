public class PlinovodnaTocka {


    public PlinovodnaTocka(double latutude, double longitude, int poredVodovod, String komentar, int idVodovod, int idTipVodovodneTocke) {
        this.latutude = latutude;
        this.longitude = longitude;
        this.poredVodovod = poredVodovod;
        this.komentar = komentar;
        this.idVodovod = idVodovod;
        this.idTipVodovodneTocke = idTipVodovodneTocke;
    }

    public int getIdVodovodneTocke() {
        return idVodovodneTocke;
    }

    public void setIdVodovodneTocke(int idVodovodneTocke) {
        this.idVodovodneTocke = idVodovodneTocke;
    }

    public double getLatutude() {
        return latutude;
    }

    public void setLatutude(double latutude) {
        this.latutude = latutude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getPoredVodovod() {
        return poredVodovod;
    }

    public void setPoredVodovod(int poredVodovod) {
        this.poredVodovod = poredVodovod;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public int getIdVodovod() {
        return idVodovod;
    }

    public void setIdVodovod(int idVodovod) {
        this.idVodovod = idVodovod;
    }

    public int getIdTipVodovodneTocke() {
        return idTipVodovodneTocke;
    }

    public void setIdTipVodovodneTocke(int idTipVodovodneTocke) {
        this.idTipVodovodneTocke = idTipVodovodneTocke;
    }

    public PlinovodnaTocka(int idVodovodneTocke, double latutude, double longitude, int poredVodovod, String komentar, int idVodovod, int idTipVodovodneTocke) {
        this.idVodovodneTocke = idVodovodneTocke;
        this.latutude = latutude;
        this.longitude = longitude;
        this.poredVodovod = poredVodovod;
        this.komentar = komentar;
        this.idVodovod = idVodovod;
        this.idTipVodovodneTocke = idTipVodovodneTocke;
    }

    public PlinovodnaTocka() {
    }

    private int idVodovodneTocke;
    private double latutude;
    private double longitude;
    private int poredVodovod;
    private String komentar;
    private int idVodovod;
    private int idTipVodovodneTocke;

    @Override
    public String toString() {
        return "VodovodnaTocka{" +
                "idVodovodneTocke=" + idVodovodneTocke +
                ", latutude=" + latutude +
                ", longitude=" + longitude +
                ", poredVodovod=" + poredVodovod +
                ", komentar='" + komentar + '\'' +
                ", idVodovod=" + idVodovod +
                ", idTipVodovodneTocke=" + idTipVodovodneTocke +
                '}';
    }
}
