
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.sql.SQLException;

public class SwingWaypoint extends DefaultWaypoint {
    private final JButton button;
    private int id;

    public GeoPosition getCoord() {
        return coord;
    }

    public void setCoord(GeoPosition coord) {
        this.coord = coord;
    }

    private GeoPosition coord;
    private JXMapViewer jxMapViewer;
    public SwingWaypoint(GeoPosition coord, int id, JXMapViewer jxMapViewer) {
        super(coord);
        /*this.text = text;
        button.setText(text.substring(0, 1));*/
        this.jxMapViewer = jxMapViewer;
        this.id = id;
        this.coord = coord;

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
            JMenuItem editMenuItem = new JMenuItem("Edit");
            popupMenu.add(editMenuItem);



            popupMenu.show(button, 0, button.getHeight());

            JOptionPane.showMessageDialog(button, "bok" + id);

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // Store the mouse pressed point
            //mousePressedPoint = jxMapViewer.convertPointToGeoPosition(e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            GeoPosition draggedPosition = jxMapViewer.convertPointToGeoPosition(jxMapViewer.getMousePosition());
            System.out.println(jxMapViewer.getMousePosition());
            SwingWaypoint.this.setCoord(jxMapViewer.convertPointToGeoPosition(jxMapViewer.getMousePosition()));
            System.out.println(SwingWaypoint.this.getCoord());
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
