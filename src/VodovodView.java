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
import java.util.stream.Stream;

public class VodovodView {
    private static VodovodView instance;
    private JPanel panelMain;
    private JPanel containterPanel;
    private JPanel mapPanel;
    private JLabel statistikaLabel;
    private JButton spremiButton;
    private JButton natragButton;
    private JButton powerMoveButton;
    private JTextArea textArea1;
    static JXMapViewer jxMapViewer = new JXMapViewer();
    static CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>();
    static List<GeoPosition> positionList = new ArrayList<>();
    static List<GeoPosition> getInList = new ArrayList<>();
    private static final double CLICK_TOLERANCE = 5;
    boolean isAlreadyOneClick;
    public static Set<SwingWaypoint> waypoints = new HashSet<SwingWaypoint>();

    static List<String> komentariList = new ArrayList<>();

    VodovodView(int id) throws SQLException {
        jxMapViewer = new JXMapViewer();
        painter = new CompoundPainter<JXMapViewer>();
        positionList = new ArrayList<>();
        getInList = new ArrayList<>();
        waypoints = new HashSet<SwingWaypoint>();
        List<String> komentariList = new ArrayList<>();

        PlinovodDaoImplementation vodovodDaoImplementation = new PlinovodDaoImplementation();

        JFrame frame = new JFrame("Plinovod - "+vodovodDaoImplementation.getVodovod(id).getNaziv());
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //TODO: postaviti sve vezano za kartu u svoju inicijalizacijsku metodu
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

        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        statistikaLabel.setText("Vodovod - "+vodovodDaoImplementation.getVodovod(id).getNaziv());
        osvjeziKartu(id);
        jxMapViewer.zoomToBestFit(new HashSet<GeoPosition>(positionList), 0.9);


        try {
            buttonCusomization();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        powerMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                powerMoveView powerMoveView = new powerMoveView();
                frame.dispose();
            }
        });


        natragButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int odluceno = JOptionPane.showConfirmDialog(null, "Želite li spremit promjene?", "Spremanje",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (odluceno==JOptionPane.YES_OPTION){
                    spremiTockeUbazu(id);
                    VodovodSelection vodovodSelection = new VodovodSelection();
                    frame.dispose();
                    waypoints = new HashSet<SwingWaypoint>();
                    positionList = new ArrayList<>();
                    getInList = new ArrayList<GeoPosition>();
                    painter = null;
                    jxMapViewer = null;
                } else if (odluceno==JOptionPane.NO_OPTION) {
                    painter = null;
                    jxMapViewer = null;
                    positionList = new ArrayList<>();
                    getInList = new ArrayList<GeoPosition>();
                    waypoints = new HashSet<SwingWaypoint>();
                    VodovodSelection vodovodSelection = new VodovodSelection();
                    frame.dispose();
                } else{
                    return;
                }
            }
        });

        spremiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spremiTockeUbazu(id);
            }
        });

    }

    private void buttonCusomization() throws IOException {
        Image backimage = ImageIO.read(new File("resources\\back-button.png"));
        ImageIcon backImageIcon = new ImageIcon(backimage.getScaledInstance(25,25,Image.SCALE_SMOOTH));
        natragButton.setText("");
        natragButton.setIcon(backImageIcon);
        natragButton.setOpaque(false);
        natragButton.setContentAreaFilled(false);
        natragButton.setBorderPainted(false);
        natragButton.setSelected(false);
        natragButton.setVisible(true);


        Image powerImage = ImageIO.read(new File("resources\\power-button.png"));
        ImageIcon powerImageIcon = new ImageIcon(powerImage.getScaledInstance(25,25,Image.SCALE_SMOOTH));
        powerMoveButton.setText("");
        powerMoveButton.setIcon(powerImageIcon);
        powerMoveButton.setOpaque(false);
        powerMoveButton.setContentAreaFilled(false);
        powerMoveButton.setBorderPainted(false);
        powerMoveButton.setSelected(false);
        powerMoveButton.setVisible(true);

    }

    public void spremiTockeUbazu(int id){
        waypointsToKomentari();
        System.out.println(komentariList);
        PlinovodnaTockaDaoImplementation vodovodnaTockaDaoImplementation = new PlinovodnaTockaDaoImplementation();
        System.out.println(getInList);
        System.out.println(positionList);
        int difference = 0;
        if (getInList.size() > positionList.size()){
            difference = positionList.size()-getInList.size();
            System.out.println(difference);
        }
        for (int i = 0;i <= getInList.size()-1+difference; i++){//updateanje postojecih tocaka


            GeoPosition geoPosition = positionList.get(i);
            //System.out.println(" "+i+" "+geoPosition);

            PlinovodnaTocka plinovodnaTocka = new PlinovodnaTocka(geoPosition.getLatitude(), geoPosition.getLongitude(),i+1,komentariList.get(i),id,1);
            try {
                vodovodnaTockaDaoImplementation.updateVodvodnaTockaPosition(plinovodnaTocka);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (difference!=0){//brisanje visak tocke koje su potencijonalno nastale
            //System.out.println("deletion accelerated");
            for (int i = positionList.size();i <= getInList.size()-1; i++){
                GeoPosition geoPosition = getInList.get(i);
                //System.out.println("deletion "+i+" "+geoPosition + " "+ (getInList.size()-1+i));
                PlinovodnaTocka plinovodnaTocka = new PlinovodnaTocka(geoPosition.getLatitude(), geoPosition.getLongitude(),i+1,null,id,1);
                try {
                    vodovodnaTockaDaoImplementation.removeVodovonaTocka(plinovodnaTocka);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }

            return;
        }
        //if na kraju izlazi iz metode ako je modificirana lista manja od one s kojom se ulazi u view, tako da ova for petlja se samo poziva dok to nije slučaj
        for (int i = getInList.size();i < positionList.size(); i++){
            GeoPosition geoPosition = positionList.get(i);
            //System.out.println("insert "+i+" "+geoPosition + " "+ (getInList.size()-1+i));
            PlinovodnaTocka plinovodnaTocka = new PlinovodnaTocka(geoPosition.getLatitude(), geoPosition.getLongitude(),i+1,komentariList.get(i),id,1);
            try {
                vodovodnaTockaDaoImplementation.insertVodovodnaTocka(plinovodnaTocka);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }



    }
    public static void removeWaypoint(SwingWaypoint swingWaypoint){

        //makiva gumb sa same karte i makiva točku iz liste točaka
        swingWaypoint.getButton().getParent().remove(swingWaypoint.getButton());
        waypoints.remove(swingWaypoint);


        //uzima sortiranu listu tocaka na karti
        Stream<SwingWaypoint> ls = waypoints.stream().sorted(Comparator.comparing(SwingWaypoint::getId));
        List<SwingWaypoint> ul = ls.toList();

        //prolazi kroz točke na karti i updatea njihov id tako da budu svi smisleno i po redu
        int i = 0;
        for (SwingWaypoint swingWaypointUpdate : ul){
            i++;
            System.out.println(""+swingWaypointUpdate.getId()+ i);
            swingWaypointUpdate.setId(i);
        }

        //repainta mapu sa novim podatcima
        WaypointPainter<SwingWaypoint> swingWaypointPainter = new SwingWaypointOverlayPainter();
        swingWaypointPainter.setWaypoints(waypoints);
        RoutePainter routePainter = new RoutePainter(positionList);

        painter.addPainter(swingWaypointPainter);
        painter.addPainter(routePainter);

        jxMapViewer.repaint();
    } //azurira kartu kada se izbrise waypoint TODO:osvjezivanje karte staviti u svoju sazebnu metodu i azurirati i ocistiti sve metode koje bi mogle to koristit
    public void osvjeziKartu(int id) throws SQLException {
        mapPanel.removeAll();
        jxMapViewer.repaint();
        mapPanel.add(jxMapViewer, BorderLayout.CENTER);


        WaypointPainter<SwingWaypoint> swingWaypointPainter = new SwingWaypointOverlayPainter();
        painter.addPainter(swingWaypointPainter);

        RoutePainter routePainter = new RoutePainter(new ArrayList<>());
        painter.addPainter(routePainter);

        jxMapViewer.setOverlayPainter(painter);

        PlinovodnaTockaDaoImplementation vodovodnaTockaDaoImplementation = new PlinovodnaTockaDaoImplementation();
        List<PlinovodnaTocka> plinovodnaTockaList = vodovodnaTockaDaoImplementation.getTockeinVodovod(id);
        boolean keepNone = false;

        if (plinovodnaTockaList.size() == 0) {
            plinovodnaTockaList.add(new PlinovodnaTocka(45.82363197001308, 15.966461867597275, 1, "",id,1));
            plinovodnaTockaList.add(new PlinovodnaTocka(45.82023783618998, 16.072515674094756, 2, "",id,1));
            keepNone = true;
        }



        for (PlinovodnaTocka plinovodnaTocka : plinovodnaTockaList){
            GeoPosition geoPosition = new GeoPosition(plinovodnaTocka.getLatutude(), plinovodnaTocka.getLongitude());
            System.out.println(""+ plinovodnaTocka.getPoredVodovod());
            waypoints.add(new SwingWaypoint(geoPosition, plinovodnaTocka.getPoredVodovod(), jxMapViewer,plinovodnaTocka.getKomentar()));
            positionList.add(geoPosition);
            komentariList.add("");
            if (keepNone==false)
                getInList.add(geoPosition);
        }
        if (waypoints.size() == 0){
            GeoPosition zagreb = new GeoPosition(45.82363197001308, 15.966461867597275);
            waypoints.add(new SwingWaypoint(zagreb, 1, jxMapViewer,""));
            positionList.add(zagreb);
            komentariList.add("");
            getInList.add(zagreb);
            jxMapViewer.setZoom(10);
            jxMapViewer.setAddressLocation(zagreb);

            GeoPosition zagreb2 = new GeoPosition(45.82023783618998, 16.072515674094756);
            waypoints.add(new SwingWaypoint(zagreb2, 1, jxMapViewer,""));
            positionList.add(zagreb2);
            komentariList.add("");
            getInList.add(zagreb2);
        }
        //System.out.println("bok"+positionList);
        jxMapViewer.zoomToBestFit(new HashSet<GeoPosition>(positionList), 0.9);

        for (SwingWaypoint w : waypoints) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("resources\\waypoint.png"));
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
                    //System.out.println("double click");
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

    } //overcomplycated metoda za postavljanje kartu TODO:remake
    public void doubleClick(MouseEvent e) {
        Point2D clickPoint = e.getPoint();

        if (isClickOnRoute(clickPoint) != -1) { //mozda ovo napravim uporabivim idk
            System.out.println(isClickOnRoute(clickPoint) != -1);
            GeoPosition newPoint = jxMapViewer.convertPointToGeoPosition(clickPoint);

            positionList.add(newPoint);
            komentariList.add("");
            updateMapNewWaypoint(newPoint);
            System.out.println(newPoint);
        }
    }

    public void updateMapNewWaypoint(GeoPosition coords){
        //System.out.println("here");
        //System.out.println(positionList);


        RoutePainter routePainter = new RoutePainter(positionList);
        WaypointPainter<SwingWaypoint> swingWaypointPainter = new SwingWaypointOverlayPainter();

        painter.addPainter(swingWaypointPainter);
        waypoints.add(new SwingWaypoint(coords, positionList.indexOf(coords)+1, jxMapViewer,""));

        for (SwingWaypoint w : waypoints) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("resources\\waypoint.png"));
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

    } //dodaje swingwaypoint gumb na kartu TODO:clean up

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
    } //ovaj kod je trenutačno nekorisniv i nije uporabiv TODO:porpraviti ili maknuti i maknuti svako pizovanje
    private void waypointsToKomentari(){
        komentariList = new ArrayList<>();
        List<SwingWaypoint> swingWaypointList = waypoints.stream().toList();
        for (SwingWaypoint swingWaypoint : swingWaypointList){
            komentariList.add("");

        }
        for (SwingWaypoint swingWaypoint : swingWaypointList){
            System.out.println(swingWaypoint.getKomentar() + swingWaypoint.getId());

            komentariList.set(swingWaypoint.getId()-1, swingWaypoint.getKomentar());
        }
        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+komentariList.size());



    }
}
