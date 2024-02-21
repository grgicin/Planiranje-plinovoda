
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.sql.SQLException;

public class SwingWaypoint extends DefaultWaypoint {
    private final JButton button;
    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private GeoPosition coord;
    public GeoPosition getCoord() {
        return coord;
    }
    public void setCoord(GeoPosition coord) {
        this.coord = coord;
    }
    private JXMapViewer jxMapViewer;
    public SwingWaypoint(GeoPosition coord, int id, JXMapViewer jxMapViewer) {
        super(coord);
        /*this.text = text;
        button.setText(text.substring(0, 1));*/
        this.jxMapViewer = jxMapViewer;
        this.id = id;
        this.coord = coord;
        //System.out.println(id);

        button = new JButton();
        button.setSize(24, 24);
        button.setPreferredSize(new Dimension(24, 24));

        button.addMouseListener(new SwingWaypointMouseListener());
        button.addMouseMotionListener(new SwingWaypointMouseMotionListener());

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setVisible(true);
    }

    JButton getButton() {
        return button;
    }

    private class SwingWaypointMouseListener implements MouseListener {


        @Override
        public void mouseClicked(MouseEvent e) {
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.setPreferredSize(new Dimension(50,80));
            JMenuItem urediMenuItem = new JMenuItem("Edit");
            JMenuItem obrisiMenuItem = new JMenuItem("obrisi");
            popupMenu.add(urediMenuItem);
            popupMenu.add(obrisiMenuItem);

            obrisiMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //System.out.println(VodovodView.positionList);
                    VodovodView.positionList.remove(SwingWaypoint.this.coord);
                    //System.out.println(VodovodView.positionList);
                    VodovodView.removeWaypoint(SwingWaypoint.this);

                }
            });



            popupMenu.show(button, 0, button.getHeight());

            urediMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(button, "bok " + id + " coords " +coord);
                }
            });

            //JOptionPane.showMessageDialog(button, "bok" + id);

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // Store the mouse pressed point
            //mousePressedPoint = jxMapViewer.convertPointToGeoPosition(e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //System.out.println(jxMapViewer.getMousePosition());
            SwingWaypoint.this.setCoord(jxMapViewer.convertPointToGeoPosition(jxMapViewer.getMousePosition()));
            //System.out.println(SwingWaypoint.this.getCoord());
            VodovodView.updateMapRoute(SwingWaypoint.this.id, SwingWaypoint.this.coord);

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }
    private class SwingWaypointMouseMotionListener extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (jxMapViewer.getMousePosition() != null) {
                GeoPosition draggedPosition = jxMapViewer.convertPointToGeoPosition(jxMapViewer.getMousePosition());

                SwingWaypoint.this.setPosition(draggedPosition);


                jxMapViewer.repaint();

            }
        }
    }



}
