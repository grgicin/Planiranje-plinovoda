import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
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

        Set<DefaultWaypoint> waypoints = new HashSet<DefaultWaypoint>(Arrays.asList(
                new DefaultWaypoint(pitomaca),
                new DefaultWaypoint(dj)));





        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>();
        painter.addPainter(waypointPainter);
        painter.addPainter(routePainter);
        jxMapViewer.setOverlayPainter(painter);




        frame.setVisible(true);
        frame.pack();
        jxMapViewer.zoomToBestFit(new HashSet<GeoPosition>(ls), 0.9);


    }

    public static void main(String[] args) {
        try {
            VodovodView vodovodView = new VodovodView(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
