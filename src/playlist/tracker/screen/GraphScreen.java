/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.awt.event.AdjustmentEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import playlist.tracker.AppCenter;
import static playlist.tracker.AppCenter.ARTISTS;
import static playlist.tracker.AppCenter.MONTHS;
import static playlist.tracker.AppCenter.artistsOfTheMonth;
import static playlist.tracker.AppCenter.myGreenColor;
import static playlist.tracker.AppCenter.plainFont;
import static playlist.tracker.AppCenter.smallFont;
import static playlist.tracker.AppCenter.viewScreen;
import playlist.tracker.Artist;

/**
 * A screen that graphs the movement of the playlist as it progresses through
 * time.
 */
public class GraphScreen extends JPanel {

    private final JPanel midLevelScreen = new JPanel(); //panel within a panel
    private final Color backgroundColor = myGreenColor;
    private GraphPanel graphPanel;
    JScrollBar hbar;
    private JLabel titleLbl;

    public GraphScreen() {
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(midLevelScreen);

        JScrollBar hbar = scrollPane.getHorizontalScrollBar();
        hbar.addAdjustmentListener((AdjustmentEvent e) -> {
            graphPanel.repaint();
        });

        JScrollBar vbar = scrollPane.getVerticalScrollBar();
        vbar.addAdjustmentListener((AdjustmentEvent e) -> {
            graphPanel.repaint();
        });

        scrollPane.getViewport().setBackground(myGreenColor);
        this.add(scrollPane, BorderLayout.CENTER);

        initScreen();
    }

    private void initScreen() {
        midLevelScreen.removeAll();
        midLevelScreen.setLayout(new GridBagLayout());
        midLevelScreen.setBackground(backgroundColor);

        int months = 0; //total number of months

        for (int i = 0; i < artistsOfTheMonth.size(); i++) {
            if (artistsOfTheMonth.get(i).equals("space")) {
                months++;
            }
        }

        graphPanel = new GraphPanel();

        JButton backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(100, 50));
        backBtn.setFont(plainFont);
        backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen("Monthly Screen");
        });

        GridBagConstraints a = new GridBagConstraints();
        GridBagConstraints b = new GridBagConstraints();
        GridBagConstraints c = new GridBagConstraints();

        titleLbl = new JLabel("The Playlist Tracker Graph");
        titleLbl.setFont(AppCenter.extraBigFont);
        titleLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        titleLbl.setVisible(false);

        a.gridx = 0;
        a.gridy = 0;
        a.gridwidth = months;
        a.weightx = 1.0;
        a.weighty = .05;
        a.anchor = GridBagConstraints.CENTER;
        midLevelScreen.add(titleLbl, a);

        b.gridx = 0;
        b.gridy = 1;
        b.gridwidth = months;
        b.weightx = 1.0;
        b.weighty = .95;
        b.anchor = GridBagConstraints.NORTH;
        midLevelScreen.add(graphPanel, b);

        c.gridx = months - 1;
        c.gridy = 2;
        c.weightx = .2;
        c.weighty = .01;
        c.anchor = GridBagConstraints.EAST;
        midLevelScreen.add(backBtn, c);

        System.out.println(graphPanel);
    }

    public void update() {
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
        static final int STANDARDROWWIDTH = 250;
        final ArrayList<ArtistBallPoints> artistPoints;

        public GraphPanel() {
            this.setLayout(null);
            // size, m is # of months 
            Border raisedbevel = BorderFactory.createRaisedBevelBorder();
            Border loweredbevel = BorderFactory.createLoweredBevelBorder();
            this.setPreferredSize(new Dimension(2000, GRAPHHEIGHT)); // this is a dummy size to allow JPanel to draw the graph from the start
            this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel), "GRAPH"));
            this.setBackground(Color.white);

            artistPoints = new ArrayList<>();

            ToolTipManager.sharedInstance().registerComponent(this);
            ToolTipManager.sharedInstance().setInitialDelay(100);
            ToolTipManager.sharedInstance().setDismissDelay(1000);
            UIManager.put("ToolTip.background", Color.black);
            UIManager.put("ToolTip.foreground", Color.white);

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            titleLbl.setVisible(true);

            this.removeAll();

            place = 0;
            xCord = 10;

            //String line = artistsOfTheMonth.get(0);
            //curMonthNum = Integer.parseInt(line.substring(0, line.indexOf(" ")));
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
                    JLabel monthLbl = new JLabel(MONTHS[curMonthNum] + " " + intYear);
                    monthLbl.setFont(AppCenter.mediumBoldFont);
                    monthLbl.setSize(STANDARDROWWIDTH, 30);
                    monthLbl.setLocation(xCord, monthYCord);
                    this.add(monthLbl);

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
                            for (Artist artist : ARTISTS) {
                                if (artist.getName().toUpperCase().equals(a)) { //found which artist it is
                                    g2.setColor(artist.getColor());
                                    int x = artist.getPrevXCord() + (BALLSIZE / 2);
                                    int y = artist.getPrevYCord() + (BALLSIZE / 2);
                                    int buffer = 10;

                                    if (artist.getPrevXCord() != 0 && x > xCord - (STANDARDROWWIDTH + buffer)) { // checking if drawing a line is needed
                                        g2.drawLine(x, y, tiedXCord + (BALLSIZE / 2), yCord[place] + (BALLSIZE / 2));
                                    } else {
                                        JLabel lbl = createArtistsLabel(artist, g, tiedXCord, lblCount);
                                        this.add(lbl);
                                        lblCount++;
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
                    } else { // dealing with no tied artists
                        for (Artist artist : ARTISTS) {
                            if (artist.getName().toUpperCase().equals(curArtist)) {
                                g2.setColor(artist.getColor());
                                int x = artist.getPrevXCord() + (BALLSIZE / 2);
                                int y = artist.getPrevYCord() + (BALLSIZE / 2);
                                int buffer = 10;

                                if (artist.getPrevXCord() != 0 && x > xCord - (STANDARDROWWIDTH + buffer)) {
                                    g2.drawLine(x, y, xCord + (BALLSIZE / 2), yCord[place] + (BALLSIZE / 2));
                                } else {
                                    JLabel lbl = createArtistsLabel(artist, g, xCord, 0);
                                    this.add(lbl);
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

            for (Artist artist : ARTISTS) {
                artist.setPrevCords(0, 0);
            }

            // graph size setting
            this.setPreferredSize(new Dimension(xCord, GRAPHHEIGHT));
            this.revalidate();
        }

        private JLabel createArtistsLabel(Artist artist, Graphics g, int x, int lblCount) {
            JLabel lbl = new JLabel(artist.getName());
            lbl.setFont(smallFont);

            int width = g.getFontMetrics(smallFont).stringWidth(artist.getName()) + 2;
            int height = g.getFontMetrics(smallFont).getHeight();

            lbl.setSize(width, height);
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
