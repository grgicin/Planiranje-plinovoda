import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class KorisnikView {
    private JPanel panelMain;
    private JButton natragButton;
    private JTable tableNeodobreni;
    private JTable tableOdobreni;
    JButton odobriButton;
    JButton promijeniLozinkuButton;

    JButton urediButton;
    JButton odbijButton;

    JFrame frame = new JFrame("Korisnici");

    KorisnikView(){

        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(900, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        osvjeziTablice();

        frame.pack();
        frame.setLocationRelativeTo(null);
        natragButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new VodovodSelection();
            }
        });


    }

    private void osvjeziTablice() {
        KorisnikDaoImplementation korisnikDaoImplementation = new KorisnikDaoImplementation();
        try {
            List<Korisnik> korisnikList = korisnikDaoImplementation.getKorinsikList();
            DefaultTableModel defaultTableModelOdobreni = new DefaultTableModel();
            defaultTableModelOdobreni.addColumn(" ");
            defaultTableModelOdobreni.addColumn("Ime");
            defaultTableModelOdobreni.addColumn("Prezime");
            defaultTableModelOdobreni.addColumn("Broj telefona");
            defaultTableModelOdobreni.addColumn("Email");
            defaultTableModelOdobreni.addColumn("Uredi");
            defaultTableModelOdobreni.addColumn("Promijeni Lozniku");


            DefaultTableModel defaultTableModelNeodobreni = new DefaultTableModel();
            defaultTableModelNeodobreni.addColumn(" ");
            defaultTableModelNeodobreni.addColumn("Ime");
            defaultTableModelNeodobreni.addColumn("Prezime");
            defaultTableModelNeodobreni.addColumn("Broj telefona");
            defaultTableModelNeodobreni.addColumn("Email");
            defaultTableModelNeodobreni.addColumn("Odobri");
            defaultTableModelNeodobreni.addColumn("Odbij");

            for (Korisnik korisnik : korisnikList){
                if (korisnik.getOdobreni() == 1){
                    defaultTableModelOdobreni.addRow(new Object[]{korisnik.getId(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getBrojTelefona(), korisnik.getEmail(), 0});
                }else {
                    defaultTableModelNeodobreni.addRow(new Object[]{korisnik.getId(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getBrojTelefona(), korisnik.getEmail(), 0});

                }

            }
            odobriButton = new JButton();
            odbijButton = new JButton();

            tableNeodobreni.setModel(defaultTableModelNeodobreni);
            tableNeodobreni.setCellEditor(null);
            tableNeodobreni.setCellSelectionEnabled(false);
            tableNeodobreni.setColumnSelectionAllowed(false);
            tableNeodobreni.setDragEnabled(false);
            tableNeodobreni.setShowGrid(false);
            tableNeodobreni.getColumnModel().getColumn(0).setMinWidth(0);
            tableNeodobreni.getColumnModel().getColumn(0).setMaxWidth(0);
            tableNeodobreni.getColumnModel().getColumn(0).setWidth(0);
            tableNeodobreni.getColumnModel().getColumn(5).setCellRenderer(new ButtonRendererNeodobreni());
            tableNeodobreni.getColumnModel().getColumn(5).setCellEditor(new ButtonEditorNeodobreni(new JCheckBox()));
            tableNeodobreni.getColumnModel().getColumn(6).setCellRenderer(new ButtonRendererNeodobreni());
            tableNeodobreni.getColumnModel().getColumn(6).setCellEditor(new ButtonEditorNeodobreni(new JCheckBox()));

            odobriButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int odabrani = tableNeodobreni.getSelectedRow();
                    if (odabrani >= 0){

                        TableModel model = tableNeodobreni.getModel();

                        try {
                            korisnikDaoImplementation.odobriKorisnika(Integer.valueOf(String.valueOf(model.getValueAt(odabrani, 0))));
                            osvjeziTablice();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                }
            }});


            urediButton = new JButton();
            promijeniLozinkuButton = new JButton();
            promijeniLozinkuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int odabrani = tableNeodobreni.getSelectedRow();
                    if (odabrani >= 0){
                        TableModel model = tableNeodobreni.getModel();

                        frame.dispose();
                        new PromijeniLozinku(Integer.valueOf(String.valueOf(model.getValueAt(odabrani, 0))));
                    }


                }
            });
            odbijButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int odabrani = tableNeodobreni.getSelectedRow();
                    if (odabrani >= 0){

                        TableModel model = tableNeodobreni.getModel();

                        try {
                            korisnikDaoImplementation.removeKorisnik(Integer.valueOf(String.valueOf(model.getValueAt(odabrani, 0))));
                            osvjeziTablice();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                }
            }});
            tableOdobreni.setModel(defaultTableModelOdobreni);
            tableOdobreni.setCellEditor(null);
            tableOdobreni.setCellSelectionEnabled(false);
            tableOdobreni.setColumnSelectionAllowed(false);
            tableOdobreni.setDragEnabled(false);
            tableOdobreni.setShowGrid(false);
            tableOdobreni.getColumnModel().getColumn(0).setMinWidth(0);
            tableOdobreni.getColumnModel().getColumn(0).setMaxWidth(0);
            tableOdobreni.getColumnModel().getColumn(0).setWidth(0);
            tableOdobreni.getColumnModel().getColumn(5).setCellRenderer(new ButtonRendererOdobreni());
            tableOdobreni.getColumnModel().getColumn(5).setCellEditor(new ButtonEditorOdobreni(new JCheckBox()));
            tableOdobreni.getColumnModel().getColumn(6).setCellRenderer(new ButtonRendererOdobreni());
            tableOdobreni.getColumnModel().getColumn(6).setCellEditor(new ButtonEditorOdobreni(new JCheckBox()));

            urediButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("here");

                    int odabrani = tableOdobreni.getSelectedRow();
                    if (odabrani >= 0){

                        TableModel model = tableOdobreni.getModel();

                        Korisnik korisnik = null;
                        try {
                            korisnik = korisnikDaoImplementation.getKorisnik(Integer.valueOf(String.valueOf(model.getValueAt(odabrani, 0))));
                            System.out.println("here");
                            osvjeziTablice();
                            new KorisnikUrediView(korisnik);
                            frame.dispose();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }});


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    class ButtonRendererNeodobreni extends JButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (column == 5){
                setText("Odobri");
            }if (column == 6){
                setText("Odbij");
            }
            return this;
        }
    }

    class ButtonEditorNeodobreni extends DefaultCellEditor {
        public ButtonEditorNeodobreni(JCheckBox checkBox) {
            super(checkBox);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (column == 5){
                odobriButton.setText("Odobri");
                return odobriButton;
            }if (column == 6){
                odbijButton.setText("Odobri");
                return odbijButton;
            }

            return null;
        }
    }


    class ButtonRendererOdobreni extends JButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (column == 5){
                setText("Uredi");
            }if (column == 6){
                setText("Promijeni Lozinku");
            }
            return this;
        }
    }

    class ButtonEditorOdobreni extends DefaultCellEditor {
        public ButtonEditorOdobreni(JCheckBox checkBox) {
            super(checkBox);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (column == 5){
                urediButton.setText("Uredi");
                return urediButton;
            }if (column == 6){
                promijeniLozinkuButton.setText("Promijeni Lozinku");
                return promijeniLozinkuButton;
            }

            return null;
        }
    }





}
