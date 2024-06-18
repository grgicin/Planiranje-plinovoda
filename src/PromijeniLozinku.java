import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class PromijeniLozinku {
    private JPanel panelMain;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;
    private JButton odustaniButton;
    private JButton spremiButton;



    JFrame frame = new JFrame("Promijeni Lozniku");

    PromijeniLozinku(int id) {

        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(900, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        odustaniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });



        spremiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordField2.getText().equals(passwordField3.getText())){
                KorisnikDaoImplementation korisnikDaoImplementation = new KorisnikDaoImplementation();
                    try {
                        Encryption encryption = new Encryption();
                        String yup = encryption.Encrypt(passwordField2.getText());
                        korisnikDaoImplementation.updateLoznikaKorniksnik(new Korisnik(id,"0","0","0","0",yup,0, 0));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                    }


                }
            }
        });
    }
}
