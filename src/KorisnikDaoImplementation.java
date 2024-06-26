import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String query =  "insert into Korisnik(ime,Prezime, brojTelefona, email, lozinka,idUloga) values(?,?,?,?,?,1);";
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
        korisnik.setBrojTelefona(resultSet.getString(4));
        korisnik.setEmail(resultSet.getString(5));
        korisnik.setLozinka(resultSet.getString(6));
        korisnik.setIdUloga(resultSet.getInt(7));

        return korisnik;
    }

    @Override
    public List<Korisnik> getKorinsikList() throws SQLException {
        String query = "select * from Korisnik;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Korisnik> korisnikList = new ArrayList<>();
        while (resultSet.next()) {
            Korisnik korisnik = new Korisnik();
            korisnik.setId(resultSet.getInt(1));
            korisnik.setIme(resultSet.getString(2));
            korisnik.setPrezime(resultSet.getString(3));
            korisnik.setBrojTelefona(resultSet.getString(4));
            korisnik.setEmail(resultSet.getString(5));
            korisnik.setLozinka(resultSet.getString(6));
            korisnik.setIdUloga(resultSet.getInt(7));
            korisnik.setOdobreni(resultSet.getInt(8));

            korisnikList.add(korisnik);
        }
        return korisnikList;
    }

    @Override
    public void odobriKorisnika(int id) throws SQLException {
        String query = "UPDATE `PlinskiVodovod`.`Korisnik` SET `odobren` = '1' WHERE (`idKorisnik` = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateKorisnik(Korisnik korisnik) throws SQLException {
        String query = "UPDATE `PlinskiVodovod`.`Korisnik` SET `ime` = ?, `Prezime` = ?, `brojTelefona` = ?, `email` = ? WHERE (`idKorisnik` = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,korisnik.getIme());
        preparedStatement.setString(2,korisnik.getPrezime());
        preparedStatement.setString(3,korisnik.getBrojTelefona());
        preparedStatement.setString(4,korisnik.getEmail());
        preparedStatement.setInt(5,korisnik.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void removeKorisnik(int id) throws SQLException {
        String query = "DELETE FROM `PlinskiVodovod`.`Korisnik` WHERE (`idKorisnik` = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateLoznikaKorniksnik(Korisnik korisnik) throws SQLException {
        String query = "UPDATE `PlinskiVodovod`.`Korisnik` SET `lozinka` = ? WHERE (`idKorisnik` = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, korisnik.getLozinka());
        preparedStatement.setInt(2, korisnik.getId());
        preparedStatement.executeUpdate();
    }
}
