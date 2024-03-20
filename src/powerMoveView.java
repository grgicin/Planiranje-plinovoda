import javax.swing.*;
import java.awt.*;

public class powerMoveView {
    private JPanel panelMain;
    powerMoveView(){
        JFrame frame = new JFrame("Vodovod");
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.pack();
    }

}
