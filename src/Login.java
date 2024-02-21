import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Login {
    private JPanel panelMain;
    private JButton registerButton;
    private JButton loginButton;
    private JTextField emailTextField;
    private JPasswordField passwordField;


    Login(){
        JFrame frame = new JFrame("Login");
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(300,300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.pack();


        VodovodnaTockaDaoImplementation vodovodnaTockaDaoImplementation = new VodovodnaTockaDaoImplementation();
        try {
            System.out.println(vodovodnaTockaDaoImplementation.getTockeinVodovod(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KorisnikDaoImplementation korisnikDaoImplementation = new KorisnikDaoImplementation();
                Encryption encryption = new Encryption();
                try {

                    Korisnik korisnik = new Korisnik();
                    korisnik.setEmail(String.valueOf(emailTextField.getText()));
                    String sha = encryption.Encrypt(passwordField.getText());
                    korisnik.setLozinka(sha);

                    //dobivanje informacije o loginu ako je uspješan a ako nije dobivamo nepostojeći id koji javlja grešku
                    int idKorisnik = korisnikDaoImplementation.korisnikLogin(korisnik);

                    //ako postoji bilo koji neuspjesan slucaj na razini baze iz nje se vraća 0 i govori korisniku da je login neuspješan
                    if (idKorisnik == 0){JOptionPane.showMessageDialog(null, "Login nije uspješan");return;}

                    //ako je login uspješan postavljaju se korisnikove informacije u LoggedUser klasu
                    Korisnik korinsnikLoggedIn =  korisnikDaoImplementation.getKorisnik(idKorisnik);
                    LoggedKorisnik.id = korinsnikLoggedIn.getId();
                    LoggedKorisnik.ime = korinsnikLoggedIn.getIme();
                    LoggedKorisnik.prezime = korinsnikLoggedIn.getPrezime();
                    LoggedKorisnik.brojTelefona = korinsnikLoggedIn.getBrojTelefona();
                    LoggedKorisnik.email = korinsnikLoggedIn.getEmail();
                    LoggedKorisnik.idUloga = korinsnikLoggedIn.getIdUloga();
                    frame.dispose();
                    VodovodSelection vodovodSelection = new VodovodSelection();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                frame.dispose();
            }
        });


    }
}
