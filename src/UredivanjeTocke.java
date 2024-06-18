import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UredivanjeTocke {
    private JPanel panelMain;
    private JTextArea textArea1;
    private JButton odusatniButton;
    private JButton spremiButton;
    private JTextField textField1;
    private JTextField textField2;

    UredivanjeTocke(SwingWaypoint swingWaypoint){
        JFrame frame = new JFrame("Uređivanje Točke");
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(300,300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        textArea1.setText(swingWaypoint.getKomentar());
        textField1.setText(String.valueOf(swingWaypoint.getCoord().getLatitude()));
        textField2.setText(String.valueOf(swingWaypoint.getCoord().getLongitude()));

        odusatniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        spremiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                swingWaypoint.setKomentar(textArea1.getText());
                frame.dispose();
            }
        });


    }

}
