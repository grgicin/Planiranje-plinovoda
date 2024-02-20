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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class VodovodView {
    private JPanel panelMain;
    private JPanel mapPanel;
    private JLabel statistikaLabel;
    private JButton spremiButton;
    static JXMapViewer jxMapViewer = new JXMapViewer();
    static CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>();
    static List<GeoPosition> positionList = new ArrayList<>();
    static List<GeoPosition> getInList = new ArrayList<>();
    private static final double CLICK_TOLERANCE = 5;
    boolean isAlreadyOneClick;
    private int clicks;


    VodovodView(int id) throws SQLException {

        VodovodDaoImplementation vodovodDaoImplementation = new VodovodDaoImplementation();

        JFrame frame = new JFrame("Vodovod - "+vodovodDaoImplementation.getVodovod(id).getNaziv());
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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
        jxMapViewer.zoomToBestFit(new HashSet<GeoPosition>(positionList), 0.9);

        spremiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VodovodnaTockaDaoImplementation vodovodnaTockaDaoImplementation = new VodovodnaTockaDaoImplementation();
                System.out.println(getInList);
                System.out.println(positionList);
                for (int i = 0;i <= getInList.size()-1; i++){


                    GeoPosition geoPosition = positionList.get(i);
                    System.out.println(" "+i+" "+geoPosition);

                    VodovodnaTocka vodovodnaTocka = new VodovodnaTocka(geoPosition.getLatitude(), geoPosition.getLongitude(),i,null,id,1);
                    try {
                        vodovodnaTockaDaoImplementation.updateVodvodnaTockaPosition(vodovodnaTocka);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                /*for (int i = getInList.size();i < positionList.size(); i++){
                    GeoPosition geoPosition = positionList.get(i);
                    System.out.println(" "+i+" "+geoPosition + " "+ (getInList.size()-1+i));
                    VodovodnaTocka vodovodnaTocka = new VodovodnaTocka(0, geoPosition.getLatitude(), geoPosition.getLongitude(),i,null,id,1);
                    try {
                        vodovodnaTockaDaoImplementation.insertVodovodnaTocka(vodovodnaTocka);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }*/


            }
        });

    }


    public void osvjeziKartu(int id) throws SQLException {
        mapPanel.removeAll();
        jxMapViewer.repaint();
        mapPanel.add(jxMapViewer, BorderLayout.CENTER);


        WaypointPainter<SwingWaypoint> swingWaypointPainter = new SwingWaypointOverlayPainter();
        painter.addPainter(swingWaypointPainter);

        RoutePainter routePainter = new RoutePainter(new ArrayList<>());
        painter.addPainter(routePainter);

        jxMapViewer.setOverlayPainter(painter);

        VodovodnaTockaDaoImplementation vodovodnaTockaDaoImplementation = new VodovodnaTockaDaoImplementation();
        List<VodovodnaTocka> vodovodnaTockaList = vodovodnaTockaDaoImplementation.getTockeinVodovod(id);
        Set<SwingWaypoint> waypoints = new HashSet<SwingWaypoint>();

        for (VodovodnaTocka vodovodnaTocka : vodovodnaTockaList){
            GeoPosition geoPosition = new GeoPosition(vodovodnaTocka.getLatutude(), vodovodnaTocka.getLongitude());
            System.out.println(""+vodovodnaTocka.getPoredVodovod());
            waypoints.add(new SwingWaypoint(geoPosition, vodovodnaTocka.getPoredVodovod(), jxMapViewer));
            positionList.add(geoPosition);
            getInList.add(geoPosition);
        }
        System.out.println("bok"+positionList);
        jxMapViewer.zoomToBestFit(new HashSet<GeoPosition>(positionList), 0.9);

        for (SwingWaypoint w : waypoints) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("C:\\Users\\Matija\\Pictures\\9.png"));
                ImageIcon ikona = new ImageIcon(img.getScaledInstance(24, 24, Image.SCALE_DEFAULT));
                w.getButton().setIcon(ikona);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            jxMapViewer.add(w.getButton());
        }
        swingWaypointPainter.setWaypoints(waypoints);
        routePainter = new RoutePainter(positionList);
        painter.addPainter(routePainter);


        jxMapViewer.repaint();

        jxMapViewer.zoomToBestFit(new HashSet<GeoPosition>(positionList), 0.9);


        jxMapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isAlreadyOneClick) {
                    System.out.println("double click");
                    doubleClick(e);
                    isAlreadyOneClick = false;
                } else {
                    isAlreadyOneClick = true;
                    Timer t = new Timer("doubleclickTimer", false);
                    t.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            isAlreadyOneClick = false;
                        }
                    }, 300);
                }


            }
        });
    }
    public void doubleClick(MouseEvent e) {
        Point2D clickPoint = e.getPoint();

        if (isClickOnRoute(clickPoint) != -1) { //mozda ovo napravim uporabivim idk
            System.out.println(isClickOnRoute(clickPoint) != -1);
            GeoPosition newPoint = jxMapViewer.convertPointToGeoPosition(clickPoint);

            positionList.add(newPoint);
            updateMapNewWaypoint(newPoint);
            System.out.println(newPoint);
        }
    }

    public void updateMapNewWaypoint(GeoPosition coords){
        System.out.println("here");
        System.out.println(positionList);


        RoutePainter routePainter = new RoutePainter(positionList);
        WaypointPainter<SwingWaypoint> swingWaypointPainter = new SwingWaypointOverlayPainter();

        painter.addPainter(swingWaypointPainter);
        Set<SwingWaypoint> waypoints = new HashSet<SwingWaypoint>();

        waypoints.add(new SwingWaypoint(coords, positionList.indexOf(coords)+1, jxMapViewer));

        /*for (GeoPosition geoPosition : positionList) {
            waypoints.add(new SwingWaypoint(geoPosition, positionList.indexOf(geoPosition)+1, jxMapViewer));
        }*/

        for (SwingWaypoint w : waypoints) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("C:\\Users\\Matija\\Pictures\\9.png"));
                ImageIcon ikona = new ImageIcon(img.getScaledInstance(24, 24, Image.SCALE_DEFAULT));
                w.getButton().setIcon(ikona);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            jxMapViewer.add(w.getButton());
        }
        swingWaypointPainter.setWaypoints(waypoints);

        painter.addPainter(routePainter);
        painter.addPainter(swingWaypointPainter);

        jxMapViewer.repaint();

    }

    public static void updateMapRoute(int id, GeoPosition coord){
        positionList.set(id-1, coord);
        System.out.println("here");
        System.out.println(positionList);
        RoutePainter routePainter = new RoutePainter(positionList);
        WaypointPainter<SwingWaypoint> swingWaypointPainter = new SwingWaypointOverlayPainter();
        painter.addPainter(swingWaypointPainter);
        Set<SwingWaypoint> waypoints = swingWaypointPainter.getWaypoints();


        painter.addPainter(routePainter);

        jxMapViewer.repaint();
    }


    private static int isClickOnRoute(Point2D clickPoint) { //kill me code
        for (int i = 1; i < positionList.size(); i++) {
            GeoPosition p1 = positionList.get(i - 1);
            GeoPosition p2 = positionList.get(i);

            Point2D lineStart = jxMapViewer.getTileFactory().geoToPixel(p1, jxMapViewer.getZoom());
            Point2D lineEnd = jxMapViewer.getTileFactory().geoToPixel(p2, jxMapViewer.getZoom());


            double distance = lineStart.distance(lineEnd); // Length of the line segment
            double distToStart = lineStart.distance(clickPoint);
            double distToEnd = lineEnd.distance(clickPoint);
            System.out.println(""+(distToStart + distToEnd - distance)+" "+CLICK_TOLERANCE );


            return i; // Click is within tolerance of the line segment
        }
        return -1; // Click is not near any segment of the route
    }

    private static List<DefaultWaypoint> createWaypoints(List<GeoPosition> route) {
        List<DefaultWaypoint> waypoints = new ArrayList<>();
        for (GeoPosition position : route) {
            waypoints.add(new DefaultWaypoint(position));
        }
        return waypoints;
    }

}
