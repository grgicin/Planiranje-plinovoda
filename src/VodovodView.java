import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.sql.SQLException;

public class VodovodView {
    private JPanel panelMain;
    private JPanel mapPanel;
    private JLabel statistikaLabel;

    VodovodView(int id) throws SQLException {
        VodovodDaoImplementation vodovodDaoImplementation = new VodovodDaoImplementation();

        JFrame frame = new JFrame("Vodovod - "+vodovodDaoImplementation.getVodovod(id).getNaziv());
        frame.setContentPane(panelMain);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //inicalizacija karte
        JXMapViewer jxMapViewer = new JXMapViewer();
        jxMapViewer.setMaximumSize(new Dimension(400,400));


        //stavljanje tilefactorya tj tip karte (satelit, topograf, itd.)
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jxMapViewer.setTileFactory(tileFactory);

        //postavljanje karte da se bude na nekoj točki kada se otvori jerr inače bi se otvorila na jako lošem mjestu gdje bi smo možda posmislili da ne radi
        GeoPosition geo = new GeoPosition(46.02690857698898, 15.959748148031391);
        jxMapViewer.setAddressLocation(geo);
        jxMapViewer.setZoom(5);

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





    }
}
