import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class Main {
    private JPanel panelMain;
    private JPanel mapPanel;

    Main(){
        JFrame frame = new JFrame("ver: 0.0.4");
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(800,800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //inicalizacija karte
        JXMapViewer jxMapViewer = new JXMapViewer();
        jxMapViewer.setMaximumSize(new Dimension(400,400));
        jxMapViewer.setPreferredSize(new Dimension(400,400));


        //stavljanje tilefactorya tj tip karte (satelit, topograf, itd.)
        TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jxMapViewer.setTileFactory(tileFactory);


        //stavljanje nekakvih osnovnih kontrola za sami pomak po karti
        MouseInputListener mouseInputListener = new PanMouseInputListener(jxMapViewer);
        jxMapViewer.addMouseListener(mouseInputListener);
        jxMapViewer.addMouseMotionListener(mouseInputListener);
        jxMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jxMapViewer));


        mapPanel.setPreferredSize(new Dimension(200 ,200));
        mapPanel.add(jxMapViewer, BorderLayout.CENTER);

        GeoPosition pitomaca = new GeoPosition(45.94898157351707, 17.232299608291896);
        GeoPosition dj = new GeoPosition(46.03947751666308, 17.068050954410364);
        List<GeoPosition> ls = new ArrayList<>();
        ls.add(pitomaca);
        ls.add(dj);
        RoutePainter routePainter = new RoutePainter(ls);

        Set<SwingWaypoint> waypoints = new HashSet<SwingWaypoint>(Arrays.asList(
                new SwingWaypoint(pitomaca, 1, jxMapViewer),
                new SwingWaypoint(dj,2, jxMapViewer)));

        for (SwingWaypoint w : waypoints) {
            BufferedImage img = null;
            try{

                img = ImageIO.read(new File("C:\\Users\\Matija\\Pictures\\8.png"));
                ImageIcon ikona = new ImageIcon(img.
                        getScaledInstance(24, 24, Image.SCALE_DEFAULT));
                w.getButton().setIcon(ikona);


            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }



            jxMapViewer.add(w.getButton());
        }



        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);


        WaypointPainter<SwingWaypoint> swingWaypointPainter = new SwingWaypointOverlayPainter();
        swingWaypointPainter.setWaypoints(waypoints);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>();
        painter.addPainter(swingWaypointPainter);
        painter.addPainter(routePainter);
        jxMapViewer.setOverlayPainter(painter);




        frame.setVisible(true);
        frame.pack();
        jxMapViewer.zoomToBestFit(new HashSet<GeoPosition>(ls), 0.9);


    }

    public static void main(String[] args) throws SQLException {
        //Main main  = new Main();


        //VodovodView vodovodView = new VodovodView(1);
        Login login = new Login();
    }
}
