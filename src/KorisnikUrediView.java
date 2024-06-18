import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class KorisnikUrediView {
    private JPanel panelMain;
    private JTextField imeTextField;
    private JTextField prezimeTextField;
    private JTextField emailTextField;
    private JButton odustaniButton;
    private JButton registerButton;
    private JTextField textFieldBrojTelefona;
    private JComboBox comboBox;

    KorisnikUrediView(Korisnik korisnik) {
        JFrame frame = new JFrame("Uredi");
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(300, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.pack();

        imeTextField.setText(korisnik.getIme());
        prezimeTextField.setText(korisnik.getPrezime());
        textFieldBrojTelefona.setText(korisnik.getBrojTelefona());
        emailTextField.setText(korisnik.getEmail());

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
                if (imeTextField.getText().isEmpty() || prezimeTextField.getText().isEmpty()  || emailTextField.getText().isEmpty() || textFieldBrojTelefona.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Registracija nije uspješana");
                    return;
                }
                try {
                    KorisnikDaoImplementation korisnikDaoImplementation = new KorisnikDaoImplementation();
                    Korisnik odlazkorisnik = new Korisnik(korisnik.getId(), imeTextField.getText(), prezimeTextField.getText(),textFieldBrojTelefona.getText(), emailTextField.getText()," ",0,0);
                    korisnikDaoImplementation.updateKorisnik(odlazkorisnik);
                    frame.dispose();
                    new KorisnikView();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
