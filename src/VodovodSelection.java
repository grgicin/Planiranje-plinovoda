import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class VodovodSelection {
    private JPanel panelMain;
    private JTable tablePlinovoda;
    private JButton natragButton;
    private JButton noviVodovodButton;

    JButton odaberiButton;

    VodovodSelection() {

        JFrame frame = new JFrame("Login");
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        osvjeziTablicu();
        frame.pack();

    }

    class ButtonRenderer extends JButton implements TableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (column == 1){
                setText("Odaberi");
            }
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (column == 1){
                odaberiButton.setText("Odaberi");
                return odaberiButton;
            }

            return null;
        }
    }

    private void osvjeziTablicu() {
        VodovodDaoImplementation vodovodDaoImplementation = new VodovodDaoImplementation();
        try {
            List<Vodovod> vodovodList = vodovodDaoImplementation.getVodovodi();
            DefaultTableModel defaultTableModel = new DefaultTableModel();
            defaultTableModel.addColumn("Naziv");
            defaultTableModel.addColumn("Odaberi");
            for (Vodovod vodovod : vodovodList){
                defaultTableModel.addRow(new Object[]{vodovod.getNaziv()});
            }
            odaberiButton = new JButton();
            tablePlinovoda.setModel(defaultTableModel);
            tablePlinovoda.setCellEditor(null);
            tablePlinovoda.setCellSelectionEnabled(false);
            tablePlinovoda.setColumnSelectionAllowed(false);
            tablePlinovoda.setDragEnabled(false);
            tablePlinovoda.setShowGrid(false);
            tablePlinovoda.getColumn("Odaberi").setCellRenderer(new ButtonRenderer());
            tablePlinovoda.getColumn("Odaberi").setCellEditor(new ButtonEditor(new JCheckBox()));
            odaberiButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
