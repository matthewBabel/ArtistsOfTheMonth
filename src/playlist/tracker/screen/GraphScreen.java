package playlist.tracker.screen;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.AdjustmentEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import playlist.tracker.AppCenter;
import static playlist.tracker.AppCenter.MONTHS;
import static playlist.tracker.AppCenter.artistsOfTheMonth;
import static playlist.tracker.font.FontHandler.*;
import static playlist.tracker.AppCenter.viewScreen;
import playlist.tracker.artist.Artist;
import static playlist.tracker.AppCenter.MYGREEN;
import playlist.tracker.component.ComponentHandler;
import playlist.tracker.component.button.SmallButton;
import playlist.tracker.component.label.CustomLabel;
import playlist.tracker.component.label.ExtraLargeLabel;

/**
 * A screen that graphs the movement of the playlist as it progresses through
 * time.
 */
public class GraphScreen extends UpdatingScreen{

    private final JPanel graphContainer = new JPanel(); //panel containing the graph screen
    private final Color backgroundColor = MYGREEN;
    private GraphPanel graphPanel;
    private ExtraLargeLabel titleLbl;
    private SmallButton backBtn;

    public GraphScreen() {
        initScroll();
        initScreen();
    }

    private void initScroll() {

        graphPanel = new GraphPanel();
        JScrollPane scrollPane = new JScrollPane(graphPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        scrollPane.getHorizontalScrollBar().setUnitIncrement(25);
        scrollPane.getHorizontalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> {
            graphPanel.repaint();
        });

        scrollPane.setMaximumSize(new Dimension(1000, 500));

        graphContainer.removeAll();
        graphContainer.setLayout(new BorderLayout());
        graphContainer.add(scrollPane);

    }

    @Override
    protected void initScreen() {
        initScroll();

        removeAll();
        setLayout(new GridBagLayout());
        setBackground(backgroundColor);

        backBtn = new SmallButton("Back");
        backBtn.setPreferredSize(new Dimension(100, 50));
        backBtn.setFont(plainFont);
        backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen(AppCenter.Screen.MAINMENU.label);
        });

        titleLbl = new ExtraLargeLabel("The Playlist Tracker Graph", true);

        GridBagConstraints con = new GridBagConstraints();
        con.gridx = 0;
        con.gridy = 0;
        con.weightx = 1;
        con.weighty = .1;
        this.add(titleLbl, con);

        graphContainer.setMinimumSize(graphPanel.getPreferredSize());
        con.gridy = 1;
        con.weighty = .9;
        this.add(graphContainer, con);

        con.gridy = 2;
        con.weighty = .01;
        con.anchor = GridBagConstraints.SOUTHEAST;
        this.add(backBtn, con);

        addToComponents(new JComponent[]{titleLbl, backBtn});
        
        revalidate();
    }

    @Override
    public void update() {
        components.clear();
        initScroll();
        initScreen();
    }

    /**
     * Panel that holds the graph.
     */
    class GraphPanel extends JPanel {

        int place = 0;
        int xCord = 0;
        int numHolder = 0;
        int month = 0;
        int[] yCord = {150, 200, 250, 300, 350, 400, 450, 500, 550, 600};
        final int monthYCord = 25;
        final int BALLSIZE = 15;
        final int LINEOFFSETX = 30;
        final int LINEOFFSETY = 20;
        final int BALLBUFFER = BALLSIZE;
        final int GRAPHHEIGHT = 700;
        final int INITX = 30;
        static final int STANDARDROWWIDTH = 250;
        final ArrayList<ArtistBallPoints> artistPoints;

        Stroke dashedStroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        Stroke basicStroke = new BasicStroke(3);
        Color dashedColor = new Color(0, 0, 0, 40);

        public GraphPanel() {
            artistPoints = new ArrayList<>();
            init();
        }

        private void init() {
            Border raisedbevel = BorderFactory.createRaisedBevelBorder();
            Border loweredbevel = BorderFactory.createLoweredBevelBorder();
            this.setLayout(null);
            this.setPreferredSize(new Dimension(2000, GRAPHHEIGHT)); // this is a dummy size to allow JPanel to draw the graph from the start
            this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel), "GRAPH"));
            this.setBackground(Color.white);

            ToolTipManager.sharedInstance().registerComponent(this);
            ToolTipManager.sharedInstance().setInitialDelay(100);
            ToolTipManager.sharedInstance().setDismissDelay(1000);
            UIManager.put("ToolTip.background", Color.black);
            UIManager.put("ToolTip.foreground", Color.white);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            ArrayList<JComponent> compList = new ArrayList<>();

            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(basicStroke);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            this.removeAll();

            place = 0;
            xCord = INITX;

            int curMonthNum = AppCenter.startMonth;
            int intYear = Integer.valueOf(AppCenter.year);

            // going through each line of aotm and graphing it
            for (int i = 1; i < artistsOfTheMonth.size(); i++) {

                if (artistsOfTheMonth.get(i).equals("space")) {
                    place = 0;

                    if (i != 1) {
                        xCord += STANDARDROWWIDTH;
                    }

                    // draw month label
                    if (i != artistsOfTheMonth.size() - 1) {
                        CustomLabel monthLbl = new CustomLabel(MONTHS[curMonthNum] + " " + intYear, ((int) (STANDARDROWWIDTH * 0.8)), 60, true);
                        monthLbl.setLocation(xCord - INITX + ((int) (STANDARDROWWIDTH * 0.1)), monthYCord);
                        this.add(monthLbl);
                        compList.add(monthLbl);
                    }

                    //draw month divider line
                    g2.setColor(dashedColor);
                    g2.setStroke(dashedStroke);
                    g2.drawLine(xCord - INITX + STANDARDROWWIDTH, 10, xCord - INITX + STANDARDROWWIDTH, this.getY() + GRAPHHEIGHT);
                    g2.setStroke(basicStroke);

                    curMonthNum++;
                    if (curMonthNum == 12) {
                        curMonthNum = 0;
                        intYear++;
                    }

                } else {

                    String curArtist = (artistsOfTheMonth.get(i)).toUpperCase();

                    // dealing with ties                   
                    if (curArtist.contains("&")) {
                        int tiedXCord = xCord;
                        int lblCount = 0;
                        String[] tiedArtists = curArtist.split("&");
                        for (String a : tiedArtists) {
                            for (Artist artist : AppCenter.artistHandler.getArtistsList()) {
                                if (artist.getName().toUpperCase().equals(a)) { //found which artist it is
                                    g2.setColor(artist.getColor());
                                    int x = artist.getPrevXCord() + (BALLSIZE / 2);
                                    int y = artist.getPrevYCord() + (BALLSIZE / 2);
                                    int buffer = 10;

                                    if (artist.getPrevXCord() != 0 && x > xCord - (STANDARDROWWIDTH + buffer)) { // checking if drawing a line is needed
                                        g2.drawLine(x, y, tiedXCord + (BALLSIZE / 2), yCord[place] + (BALLSIZE / 2));
                                    } else {
                                        JLabel lbl = createArtistsLabel(artist, tiedXCord, lblCount);
                                        this.add(lbl);
                                        lblCount++;
                                        compList.add(lbl);
                                    }
                                    artist.setPrevCords(tiedXCord, yCord[place]);
                                    artistPoints.add(new ArtistBallPoints(tiedXCord, yCord[place], BALLSIZE, BALLSIZE, artist.getName()));
                                    break;
                                }
                            }

                            g2.fillOval(tiedXCord, yCord[place], BALLSIZE, BALLSIZE);
                            tiedXCord += LINEOFFSETX;
                        }

                        place += tiedArtists.length;
                    } else { // dealing with no ties
                        for (Artist artist : AppCenter.artistHandler.getArtistsList()) {
                            if (artist.getName().toUpperCase().equals(curArtist)) {
                                g2.setColor(artist.getColor());
                                int x = artist.getPrevXCord() + (BALLSIZE / 2);
                                int y = artist.getPrevYCord() + (BALLSIZE / 2);
                                int buffer = 10;

                                if (artist.getPrevXCord() != 0 && x > xCord - (STANDARDROWWIDTH + buffer)) {
                                    g2.drawLine(x, y, xCord + (BALLSIZE / 2), yCord[place] + (BALLSIZE / 2));
                                } else {
                                    JLabel lbl = createArtistsLabel(artist, xCord, 0);
                                    this.add(lbl);
                                    compList.add(lbl);
                                }
                                artist.setPrevCords(xCord, yCord[place]);
                                artistPoints.add(new ArtistBallPoints(xCord, yCord[place], BALLSIZE, BALLSIZE, artist.getName()));
                                break;
                            }
                        }

                        g2.fillOval(xCord, yCord[place], BALLSIZE, BALLSIZE);
                        place++;
                    }
                }
            }

            AppCenter.artistHandler.getArtistsList().forEach((artist) -> {
                artist.setPrevCords(0, 0);
            });

            // draw place divider lines
            g2.setColor(dashedColor);
            g2.setStroke(dashedStroke);

            int diff = yCord[1] - yCord[0];
            for (int y : yCord) {
                g2.drawLine(0, y - (diff / 2) + (BALLSIZE / 2), xCord + 100, y - (diff / 2) + (BALLSIZE / 2));
            }

            g2.drawLine(0, yCord[yCord.length - 1] + (diff / 2) + (BALLSIZE / 2), xCord + 100, yCord[yCord.length - 1] + (diff / 2) + (BALLSIZE / 2));
            g2.setStroke(basicStroke);

            JComponent[] comps = new JComponent[compList.size()];
            ComponentHandler.scaleFonts(compList.toArray(comps), g);
            ComponentHandler.scaleComps(compList.toArray(comps));

            this.setPreferredSize(new Dimension(xCord, GRAPHHEIGHT));
            this.revalidate();
        }

        private CustomLabel createArtistsLabel(Artist artist, int x, int lblCount) {
            CustomLabel lbl = new CustomLabel(artist.getName(), ((int) (STANDARDROWWIDTH * 0.4)), 20, true);

            lbl.setLocation(x, yCord[place] + BALLBUFFER + (LINEOFFSETY * lblCount));
            lbl.setBackground(artist.getColor());
            lbl.setOpaque(true);
            lbl.setForeground((AppCenter.isColorDark(artist.getColor()) ? Color.WHITE : Color.BLACK));
            return lbl;
        }

        @Override
        public String getToolTipText(MouseEvent event) {
            Point p = new Point(event.getX(), event.getY());

            for (ArtistBallPoints points : artistPoints) {
                if (points.contains(p)) {
                    return points.getName();
                }
            }

            int diff = (yCord[1] - yCord[0]) / 2;
            int diffAddon = (BALLSIZE / 2);

            int place = 1;
            for (int y : yCord) {
                if (p.y > y - (diff + diffAddon) && p.y < y + (diff + diffAddon)) {
                    return AppCenter.getPlaceAsString(place);
                }
                place++;
            }

            return null;
        }

        class ArtistBallPoints extends Ellipse2D.Double {

            private String name;

            public ArtistBallPoints(double x, double y, double w, double h, String name) {
                super(x, y, w, h);
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }

    }

}
