import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Register {
    private JPanel panelMain;
    private JTextField imeTextField;
    private JTextField prezimeTextField;
    private JTextField brojTelefonaTextField;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField;
    private JButton odustaniButton;
    private JButton registerButton;

    Register() {
        JFrame frame = new JFrame("Register");
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(300, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.pack();
        odustaniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                frame.dispose();
            }
        });


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imeTextField.getText().isEmpty() || prezimeTextField.getText().isEmpty() ||  brojTelefonaTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || passwordField.getText().isEmpty() || !(passwordField.getText().equals(passwordConfirmField.getText()))){
                    System.out.println("bome");
                    return;
                }
                try {
                    KorisnikDaoImplementation korisnikDaoImplementation = new KorisnikDaoImplementation();
                    Encryption encryption = new Encryption();
                    Korisnik korisnik = new Korisnik(imeTextField.getText(), prezimeTextField.getText(), brojTelefonaTextField.getText(), emailTextField.getText(), encryption.Encrypt(passwordField.getText()));
                    korisnikDaoImplementation.korisnikRegister(korisnik);

                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
