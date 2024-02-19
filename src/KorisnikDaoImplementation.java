import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KorisnikDaoImplementation implements KorisnikDao{
    Connection connection = DatabaseConnection.getConnection();

    @Override
    public int korisnikLogin(Korisnik korisnik) throws SQLException {
        String query = "select idKorisnik from Korisnik where (email like ?) and (lozinka like ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        preparedStatement.setString(1, korisnik.getEmail());
        preparedStatement.setString(2, korisnik.getLozinka());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.last();
        int broj = resultSet.getRow();
        if (broj != 0){
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public void korisnikRegister(Korisnik korisnik) throws SQLException {
        String query =  "insert into korisnik(ime,Prezime, brojTelefona, email, lozinka) values(?,?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,korisnik.getIme());
        preparedStatement.setString(2,korisnik.getPrezime());
        preparedStatement.setString(3,korisnik.getBrojTelefona());
        preparedStatement.setString(4,korisnik.getEmail());
        preparedStatement.setString(5,korisnik.getLozinka());
        preparedStatement.executeUpdate();

    }

    @Override
    public Korisnik getKorisnik(int id) throws SQLException {
        String query = "select * from Korisnik where idKorisnik = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Korisnik korisnik = new Korisnik();
        resultSet.next();
        korisnik.setId(resultSet.getInt(1));
        korisnik.setIme(resultSet.getString(2));
        korisnik.setPrezime(resultSet.getString(3));
        korisnik.setBrojTelefona(resultSet.getString(5));
        korisnik.setEmail(resultSet.getString(4));
        korisnik.setLozinka(resultSet.getString(6));
        korisnik.setIdUloga(resultSet.getInt(7));

        return korisnik;
    }
}
