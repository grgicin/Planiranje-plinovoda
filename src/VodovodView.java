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

public class VodovodView {
    private JPanel panelMain;
    private JPanel mapPanel;
    private JLabel statistikaLabel;
    JXMapViewer jxMapViewer = new JXMapViewer();


    VodovodView(int id) throws SQLException {
        VodovodDaoImplementation vodovodDaoImplementation = new VodovodDaoImplementation();

        JFrame frame = new JFrame("Vodovod - "+vodovodDaoImplementation.getVodovod(id).getNaziv());
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //inicalizacija karte
        jxMapViewer.setMaximumSize(new Dimension(400,400));


        //stavljanje tilefactorya tj tip karte (satelit, topograf, itd.)
        TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jxMapViewer.setTileFactory(tileFactory);


        //stavljanje nekakvih osnovnih kontrola za sami pomak po karti
        MouseInputListener mouseInputListener = new PanMouseInputListener(jxMapViewer);
        jxMapViewer.addMouseListener(mouseInputListener);
        jxMapViewer.addMouseMotionListener(mouseInputListener);
        jxMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jxMapViewer));



        mapPanel.setPreferredSize(new Dimension(600 ,400));
        mapPanel.add(jxMapViewer, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();
        statistikaLabel.setText("Vodovod - "+vodovodDaoImplementation.getVodovod(id).getNaziv());
        osvjeziKartu(id);





    }

    private void osvjeziKartu(int id) throws SQLException {
        List<GeoPosition> ls = new ArrayList<>();
        VodovodnaTockaDaoImplementation vodovodnaTockaDaoImplementation = new VodovodnaTockaDaoImplementation();
        List<VodovodnaTocka> vodovodnaTockaList = vodovodnaTockaDaoImplementation.getTockeinVodovod(id);
        Set<DefaultWaypoint> waypoints = new HashSet<DefaultWaypoint>();
        for (VodovodnaTocka vodovodnaTocka : vodovodnaTockaList){
            GeoPosition geoPosition = new GeoPosition(vodovodnaTocka.getLatutude(), vodovodnaTocka.getLongitude());
            ls.add(geoPosition);
            waypoints.add(new DefaultWaypoint(geoPosition));
        }
        RoutePainter routePainter = new RoutePainter(ls);
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);
        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>();
        painter.addPainter(waypointPainter);
        painter.addPainter(routePainter);
        jxMapViewer.setOverlayPainter(painter);
        jxMapViewer.zoomToBestFit(new HashSet<GeoPosition>(ls), 0.9);



    }
}
