import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class VodovodSelection {
    private JPanel panelMain;
    private JTable tablePlinovoda;
    private JButton natragButton;
    private JButton noviVodovodButton;

    JButton odaberiButton;
    JFrame frame = new JFrame("Selection");


    VodovodSelection() {

        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        osvjeziTablicu();
        frame.pack();
        frame.setLocationRelativeTo(null);

        natragButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                frame.dispose();
            }
        });

        noviVodovodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imeNovogVodovoda = JOptionPane.showInputDialog(null,"Ime Vodovoda");
                if (imeNovogVodovoda == null)return;
                PlinovodDaoImplementation vodovodDaoImplementation = new PlinovodDaoImplementation();

                try {
                    vodovodDaoImplementation.newVodovod(imeNovogVodovoda);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                osvjeziTablicu();
            }
        });





        tablePlinovoda.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int odabrani = tablePlinovoda.getSelectedRow();
                if (odabrani >= 0){

                    TableModel model = tablePlinovoda.getModel();
                    System.out.println(model.getValueAt(odabrani, 0));

                    try {
                        VodovodView vodovodView = new VodovodView((Integer) model.getValueAt(odabrani, 0));
                        frame.dispose();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });

    }

    class ButtonRenderer extends JButton implements TableCellRenderer{
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (column == 2){
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
            if (column == 2){
                odaberiButton.setText("Odaberi");
                return odaberiButton;
            }

            return null;
        }
    }

    private void osvjeziTablicu() {
        PlinovodDaoImplementation vodovodDaoImplementation = new PlinovodDaoImplementation();
        try {
            List<Plinovod> plinovodList = vodovodDaoImplementation.getVodovodi();
            DefaultTableModel defaultTableModel = new DefaultTableModel();
            defaultTableModel.addColumn("");
            defaultTableModel.addColumn("Naziv");
            defaultTableModel.addColumn("");
            for (Plinovod plinovod : plinovodList){
                defaultTableModel.addRow(new Object[]{plinovod.getId(), plinovod.getNaziv()});
            }
            odaberiButton = new JButton();
            tablePlinovoda.setModel(defaultTableModel);
            tablePlinovoda.setCellEditor(null);
            tablePlinovoda.setCellSelectionEnabled(false);
            tablePlinovoda.setColumnSelectionAllowed(false);
            tablePlinovoda.setDragEnabled(false);
            tablePlinovoda.setShowGrid(false);
            tablePlinovoda.getColumnModel().getColumn(0).setMinWidth(0);
            tablePlinovoda.getColumnModel().getColumn(0).setMaxWidth(0);
            tablePlinovoda.getColumnModel().getColumn(0).setWidth(0);
            tablePlinovoda.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
            tablePlinovoda.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox()));

            odaberiButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int odabrani = tablePlinovoda.getSelectedRow();
                    if (odabrani >= 0){

                        TableModel model = tablePlinovoda.getModel();
                        //System.out.println(model.getValueAt(odabrani, 0));


                        try {
                            VodovodView vodovodView = new VodovodView((Integer) model.getValueAt(odabrani, 0));
                            frame.dispose();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
            });



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
