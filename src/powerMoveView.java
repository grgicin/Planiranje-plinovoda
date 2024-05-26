import javax.swing.*;
import java.awt.*;

public class powerMoveView {
    private JPanel panelMain;
    private JButton spremiButton;
    private JButton odustaniButton;
    private JComboBox comboBox1;

    powerMoveView(){
        JFrame frame = new JFrame("Plinovod");
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

}
