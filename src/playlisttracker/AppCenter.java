package playlisttracker;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Tracks various music related things, including an artist of the month tracker 
 * 
 * alpha v1.0.1
 *
 * @author Matt Babel
 */
public final class AppCenter {     // TODO : key for point values,  

    private final String[] PLACE = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th",
        "10th", "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th", "20th",
        "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th", "30th", "31st",
        "32nd", "33rd", "34th", "35th", "36th", "37th", "38th", "39th", "40th", "41st", "42nd", 
        "43rd", "44th", "45th", "46th", "47th", "48th", "49th", "50th", "51st", "52nd", "53rd",
        "54th", "55th", "56th", "57th", "58th", "59th", "60th"};
    private final String[] MONTHS = {"January", "February", "March", "April",
        "May", "June", "July", "August", "September", "October",
        "November", "December"};
    boolean monthAssigned = false;
    private int startMonth = -1;
    private String year = "";
    private final Color myGreenColor = new Color(165, 230, 162);
    private final Font plainFont;
    private final Font boldFont;
    private final OpeningScreen openingScreen;
    private final MonthlyScreen monthlyScreen;
    private final AddScreen addScreen;
    private final ViewScreen viewScreen;
    private final StatsScreen statsScreen;
    private final GraphScreen graphScreen;
    private final JPanel viewer;
    private static FileData artistsOfTheMonth;
    private static final List<Artist> ARTISTS = new ArrayList<>();

    //setting up screens of view
    public AppCenter() {
        JFrame frame = new JFrame("Artists of the Month");
        frame.setTitle("Music Database");
        frame.setLocation(100, 20);
        frame.setSize(1200, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        plainFont = makeFont(this.getClass().getResource("/fonts/PT_Sans-Web-Regular.ttf").getPath(), Font.PLAIN, 20);
        boldFont = makeFont(this.getClass().getResource("/fonts/PT_Sans-Web-Bold.ttf").getPath(), Font.BOLD, 30);

        openingScreen = new OpeningScreen();
        monthlyScreen = new MonthlyScreen();
        viewScreen = new ViewScreen();
        addScreen = new AddScreen();
        statsScreen = new StatsScreen();
        graphScreen = new GraphScreen();

        viewer = new JPanel(new CardLayout()); // for switching screens
        viewer.add(openingScreen, "Opening Screen");
        viewer.add(monthlyScreen, "Monthly Screen");
        viewer.add(viewScreen, "View Screen");
        viewer.add(addScreen, "Add Screen");
        viewer.add(statsScreen, "Stats Screen");
        viewer.add(graphScreen, "Graph Screen");
        viewScreen("Opening Screen");

        frame.add(viewer);
        frame.setVisible(true);
    }

    // starting point, creates a FileData to hold and control data
    public static void main(String[] args) {
        artistsOfTheMonth = new FileData("/data/monthlyArtists.txt");
        FileData artistFolder = new FileData("/data/artists");
        for (int i = 0; i < artistFolder.size(); i++) {
            ARTISTS.add(new Artist(artistFolder.get(i), false));
        }

        AppCenter md = new AppCenter();
    }

    /**
     * Creates the opening screen
     */
    class OpeningScreen extends JPanel {

        private final Color backgroundColor = myGreenColor;

        public OpeningScreen() {
            setBackground(backgroundColor);
            setLayout(new GridBagLayout());
            initScreen();
        }

        private void initScreen() {

            JButton aotmButton = new JButton("Artists of the Month");
            aotmButton.setFont(plainFont);
            aotmButton.setPreferredSize(new Dimension(400, 200));
            aotmButton.addActionListener((java.awt.event.ActionEvent e) -> {
                viewScreen("Monthly Screen");
            });
            
            JButton exitButton = new JButton("Exit");
            exitButton.setFont(plainFont);
            exitButton.setPreferredSize(new Dimension(300,100));
            exitButton.addActionListener((java.awt.event.ActionEvent e) -> {
                System.exit(0);
            });
            
            GridBagConstraints a = new GridBagConstraints();
            GridBagConstraints b = new GridBagConstraints();
            

            a.gridx = 0;
            a.gridy = 0;
            a.weightx = .5;
            a.weighty = .7;
            
            b.gridx = 0;
            b.gridy = 1;
            b.weightx = .5;
            b.weighty = .3;
            b.anchor = GridBagConstraints.NORTH;

            add(aotmButton, a);
            add(exitButton, b);
        }
    }

    /**
     * Creates the monthly Screen
     */
    class MonthlyScreen extends JPanel {

        private final Color backgroundColor = myGreenColor;

        public MonthlyScreen() {
            setBackground(backgroundColor);
            setLayout(new GridBagLayout());
            tryGetMonth();
            initScreen();
        }

        private void initScreen() {
            JButton viewBtn = new JButton("View");
            viewBtn.setPreferredSize(new Dimension(200, 100));
            viewBtn.setFont(plainFont);
            viewBtn.addActionListener((java.awt.event.ActionEvent e) -> {
                viewScreen("View Screen");
            });

            JButton addBtn = new JButton("Add Artists");
            addBtn.setPreferredSize(new Dimension(200, 100));
            addBtn.setFont(plainFont);
            addBtn.addActionListener((java.awt.event.ActionEvent e) -> {
                viewScreen("Add Screen");
            });

            JButton statsBtn = new JButton("Stats");
            statsBtn.setPreferredSize(new Dimension(200, 100));
            statsBtn.setFont(plainFont);
            statsBtn.addActionListener((java.awt.event.ActionEvent e) -> {
                viewScreen("Stats Screen");
            });

            JButton graphBtn = new JButton("Graph");
            graphBtn.setPreferredSize(new Dimension(200, 100));
            graphBtn.setFont(plainFont);
            graphBtn.addActionListener((java.awt.event.ActionEvent e) -> {
                viewScreen("Graph Screen");
            });

            JButton backBtn = new JButton("Back");
            backBtn.setPreferredSize(new Dimension(120, 70));
            backBtn.setFont(plainFont);
            backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
                viewScreen("Opening Screen");
            });

            GridBagConstraints a = new GridBagConstraints();
            GridBagConstraints b = new GridBagConstraints();
            GridBagConstraints c = new GridBagConstraints();
            GridBagConstraints d = new GridBagConstraints();
            GridBagConstraints e = new GridBagConstraints();

            a.gridx = 0;
            a.gridy = 0;
            a.weightx = .5;
            a.weighty = .5;
            add(viewBtn, a);

            b.gridx = 0;
            b.gridy = 1;
            b.weightx = .5;
            b.weighty = .5;
            add(addBtn, b);

            c.gridx = 0;
            c.gridy = 2;
            c.weightx = .5;
            c.weighty = .5;
            add(statsBtn, c);

            d.gridx = 0;
            d.gridy = 3;
            d.weightx = .5;
            d.weighty = .5;
            add(graphBtn, d);

            e.gridx = 0;
            e.gridy = 4;
            e.weightx = .2;
            e.weighty = .2;
            e.anchor = GridBagConstraints.LAST_LINE_END;
            add(backBtn, e);
        }

        /**
         * try getting month from monthlyArtists file if not there will get in
         * addAOTMScreen
         */
        private void tryGetMonth() {
            if (!monthAssigned && artistsOfTheMonth.size() != 0) {
                for (int i = 0; i < MONTHS.length; i++) {
                    if (Integer.valueOf(artistsOfTheMonth.get(0).substring(0,
                            artistsOfTheMonth.get(0).indexOf(" ")))
                            == i) {
                        startMonth = i;
                        monthAssigned = true;
                    }
                }
                year = artistsOfTheMonth.get(0).substring(
                        artistsOfTheMonth.get(0).indexOf(" ") + 1,
                        artistsOfTheMonth.get(0).length());
            }
        }
    }

    class ViewScreen extends JPanel {

        private final Color backgroundColor = myGreenColor;
        private final JPanel innerScreen = new JPanel();
        private ArrayList<JLabel> labels = new ArrayList<>();
        private int yMax = 0;
        private int numOfMonths = 0;

        public ViewScreen() {
            setUpScrollPane(this, innerScreen);
            initScreen();
        }

        private void initScreen() {
            innerScreen.removeAll();
            innerScreen.setBackground(backgroundColor);
            createLabels();

            JButton backBtn = new JButton("Back");
            backBtn.setPreferredSize(new Dimension(120, 70));
            backBtn.setFont(plainFont);
            backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
                viewScreen("Monthly Screen");
            });

            innerScreen.setLayout(new GridBagLayout());
            GridBagConstraints a = new GridBagConstraints();
            GridBagConstraints b = new GridBagConstraints();

            a.gridx = 0;
            a.gridy = 0;
            a.weightx = .6;
            a.weighty = .5;

            yMax = 0;
            int columnSwitch = 1;
            switch (numOfMonths) {
                case 0:
                case 1:
                case 2:
                    yMax = labels.size();
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                    columnSwitch = 2;
                    break;
                default:
                    columnSwitch = (int) Math.ceil(numOfMonths / 3.0);
                    break;
            }

            int spaces = 1;
            for (JLabel label : labels) {  // labels output
                label.setPreferredSize(new Dimension(325, 25));
                innerScreen.add(label, a);

                a.gridy++;
                if (label.getText().equals("")) {
                    if (spaces >= columnSwitch) {
                        if (a.gridy - 1 > yMax) {
                            yMax = a.gridy - 1;
                        }
                        spaces = 1;
                        a.gridy = 0;
                        a.gridx++;
                    } else {
                        spaces++;
                    }
                }
            }

            if (a.gridy > yMax) {
                yMax = a.gridy;
            }

            b.gridx = 3;
            b.gridy = yMax;
            b.weightx = .4;
            b.weighty = .2;
            innerScreen.add(backBtn, b);
        }

        public void update() {
            initScreen();
        }

        /**
         * algorithm for creating labels for viewScreen
         */
        private void createLabels() {
            labels.clear();
            int lines = artistsOfTheMonth.size() - 1;
            int LENGTH_MULTIPLIER = (int) Math.ceil((double) (400 / 13));
            int linePosition = 1;
            int placePosition = 0;
            int skip;
            int monthPosition = startMonth;
            int origYear = Integer.valueOf(year);
            numOfMonths = 0;
            String line;

            innerScreen.setSize(getWidth(), (lines) * LENGTH_MULTIPLIER);

            for (; linePosition < lines; linePosition++) {
                if (artistsOfTheMonth.get(linePosition).equals("space")) {
                    placePosition = 0;
                    if (monthPosition == 12) {
                        year = "" + (Integer.valueOf(year) + 1);
                        monthPosition = 0;
                    }

                    if (linePosition != 1) {
                        labels.add(new JLabel("")); // new line
                    }

                    if (linePosition != lines - 1) {  // if not the last line
                        numOfMonths++;
                        if (monthPosition == 0) {
                            labels.add(new JLabel(MONTHS[monthPosition] + "-" + year));
                            labels.get(labels.size() - 1).setFont(boldFont);
                        } else {
                            labels.add(new JLabel(MONTHS[monthPosition]));
                            labels.get(labels.size() - 1).setFont(boldFont);
                        }
                        monthPosition++;
                    }
                } else { // if label is for artist
                    line = artistsOfTheMonth.get(linePosition);
                    if (line.contains("&")) {
                        int beforeLength = line.length();
                        line = line.replaceAll("&", " / ");
                        skip = (line.length() - (beforeLength - 2)) / 2;

                        final int MAX_LINE_LENGTH = 29;
                        if (line.length() > MAX_LINE_LENGTH) {
                            labels.add(new JLabel(PLACE[placePosition] + ". "
                                    + line.substring(0, MAX_LINE_LENGTH) + " -"));
                            labels.get(labels.size() - 1).setFont(plainFont);

                            int multiplier = 1;

                            while (line.length() - MAX_LINE_LENGTH * multiplier >= MAX_LINE_LENGTH) {
                                labels.add(new JLabel("       " + line.substring(
                                        MAX_LINE_LENGTH * multiplier, MAX_LINE_LENGTH * multiplier
                                        + MAX_LINE_LENGTH) + " -"));
                                labels.get(labels.size() - 1).setFont(plainFont);
                                multiplier++;
                            }

                            labels.add(new JLabel("       " + line.substring(
                                    MAX_LINE_LENGTH * multiplier, line.length())));
                            labels.get(labels.size() - 1).setFont(plainFont);
                        } else {

                            labels.add(new JLabel(PLACE[placePosition] + ". " + line));
                            labels.get(labels.size() - 1).setFont(plainFont);
                        }
                        placePosition += skip;
                    } else {
                        final int MAX_LENGTH = 26;
                        if (line.length() >= MAX_LENGTH) {
                            labels.add(new JLabel(PLACE[placePosition] + ". "
                                    + line.substring(0, MAX_LENGTH) + " -"));
                            labels.get(labels.size() - 1).setFont(plainFont);

                            int multiplier = 1;

                            while (line.length() - MAX_LENGTH * multiplier >= MAX_LENGTH) {
                                labels.add(new JLabel("       " + line.substring(
                                        MAX_LENGTH * multiplier, MAX_LENGTH * multiplier
                                        + MAX_LENGTH) + " -"));
                                labels.get(labels.size() - 1).setFont(plainFont);
                                multiplier++;
                            }

                            labels.add(new JLabel("       " + line.substring(
                                    MAX_LENGTH * multiplier, line.length())));
                            labels.get(labels.size() - 1).setFont(plainFont);
                        } else {
                            labels.add(new JLabel(PLACE[placePosition] + ". " + line));
                            labels.get(labels.size() - 1).setFont(plainFont);
                        }
                        placePosition++;
                    }
                }
            }
            
            year = String.valueOf(origYear);
        }
    }

    class AddScreen extends JPanel {

        private final Color backgroundColor = myGreenColor;
        private String line;
        private int skip;
        private int placePosition;

        public AddScreen() {
            setBackground(backgroundColor);
            setLayout(new GridBagLayout());
            initScreen();
        }

        private void initScreen() {

            ArrayList<String> strs = new ArrayList<>(); // takes tf input to update artist wins
            ArrayList<String> lines = new ArrayList<>(); //lines to be added to file
            placePosition = 0;
            line = "";
            skip = 1;
            final int TOP = 10; // top 5? top 10? top 20?

            if (!monthAssigned) {
                assignFirstMonth();
            }

            JLabel questionLbl = new JLabel("Who was " + PLACE[placePosition] + "?");
            questionLbl.setFont(plainFont);
            questionLbl.setPreferredSize(new Dimension(200, 50));

            JTextField addArtistTf = new JTextField();
            addArtistTf.setFont(plainFont);
            addArtistTf.setPreferredSize(new Dimension(200, 30));

            JButton andBtn = new JButton("And?");
            andBtn.setPreferredSize(new Dimension(150, 100));
            andBtn.setFont(plainFont);
            andBtn.addActionListener((ActionEvent e) -> {
                strs.add(placePosition + addArtistTf.getText());
                line = line.concat(addArtistTf.getText() + "&");
                skip++;
                addArtistTf.setText("");
            });

            JButton nextBtn = new JButton("Next");
            nextBtn.setFont(plainFont);
            nextBtn.setPreferredSize(new Dimension(150, 100));
            nextBtn.addActionListener((ActionEvent e) -> {
                strs.add(placePosition + addArtistTf.getText());
                placePosition += skip;
                skip = 1;
                line = line.concat(addArtistTf.getText());
                lines.add(line);
                line = "";

                addArtistTf.setText("");
                if (placePosition >= TOP) {       // leave method  
                    placePosition = 0;
                    questionLbl.setText("Who was 1st?");

                    for (String str : strs) {
                        updateArtistWin(Integer.valueOf(str.substring(0, 1)),
                                str.substring(1, str.length()));
                    }

                    for (String l : lines) {
                        artistsOfTheMonth.add(l);
                    }

                    artistsOfTheMonth.add("space");
                    artistsOfTheMonth.save();
                    viewScreen.update();
                    statsScreen.update();
                    graphScreen.update();
                    viewScreen("Monthly Screen");
                } else {
                    questionLbl.setText("Who was " + PLACE[placePosition] + "?");
                }
            });

            JButton exitBtn = new JButton("Exit");
            exitBtn.setPreferredSize(new Dimension(150, 100));
            exitBtn.setFont(plainFont);
            exitBtn.addActionListener((ActionEvent e) -> {
                questionLbl.setText("Who was 1st?");
                addArtistTf.setText("");
                placePosition = 0;
                line = "";
                skip = 1;
                strs.clear();
                lines.clear();
                viewScreen("Monthly Screen");
            });

            GridBagConstraints a = new GridBagConstraints();
            GridBagConstraints b = new GridBagConstraints();
            GridBagConstraints c = new GridBagConstraints();
            GridBagConstraints d = new GridBagConstraints();
            GridBagConstraints e = new GridBagConstraints();

            a.gridx = 0;
            a.gridy = 0;
            a.gridwidth = 2;
            a.weightx = .5;
            a.weighty = .5;
            a.anchor = GridBagConstraints.PAGE_END;
            add(questionLbl, a);

            b.gridx = 0;
            b.gridy = 1;
            b.gridwidth = 2;
            b.weightx = .5;
            b.weighty = .5;
            add(addArtistTf, b);

            c.gridx = 0;
            c.gridy = 2;
            c.weightx = .5;
            c.weighty = .5;
            c.anchor = GridBagConstraints.PAGE_START;
            c.insets = new Insets(0, 150, 0, 0);
            add(andBtn, c);

            d.gridx = 1;
            d.gridy = 2;
            d.weightx = .5;
            d.weighty = .5;
            d.anchor = GridBagConstraints.PAGE_START;
            d.insets = new Insets(0, 0, 0, 150);
            add(nextBtn, d);

            e.gridx = 2;
            e.gridy = 3;
            e.weightx = .5;
            e.weighty = .5;
            add(exitBtn, e);
        }

        /**
         * Gets user input for what month the monthly artists should start at
         */
        private void assignFirstMonth() {
            String firstLine = "";
            String firstMonth = (String) JOptionPane.showInputDialog(
                    this,
                    "What month should the artists of the month start for? "
            );

            while (startMonth == -1) {
                for (int i = 0; i < MONTHS.length; i++) {

                    if (firstMonth.toUpperCase().equals(MONTHS[i].toUpperCase())) {
                        startMonth = i;
                        firstLine = firstLine.concat(String.valueOf(startMonth));
                    }
                }

                if (startMonth == -1) {
                    firstMonth = (String) JOptionPane.showInputDialog(
                            this,
                            "Hmm not so sure that's a month, could you try again?"
                    );
                }
            }

            int firstYear = -1;
            try {
                firstYear = Integer.parseInt(JOptionPane.showInputDialog(
                        this,
                        "Ok good and what year is this month in?"
                ));
            } catch (NumberFormatException e) {
                boolean good = false;
                while (!good) {
                    good = true;
                    try {
                        firstYear = Integer.parseInt(JOptionPane.showInputDialog(
                                this,
                                "That's not a valid year, could you try again?"
                        ));
                    } catch (NumberFormatException f) {
                        good = false;
                    }
                }
            }

            year = "" + firstYear;
            monthAssigned = true;
            firstLine = firstLine.concat(" " + firstYear);
            artistsOfTheMonth.add(firstLine);
            artistsOfTheMonth.add("space");
        }

        /**
         * updates the Artist class by either adding a new artist or updating a
         * current one
         *
         * @param tf - JTextField to get artist name from
         */
        private void updateArtistWin(int n, String str) {
            boolean newArtist = true;
            for (Artist artist : ARTISTS) {
                if (artist.getName().toUpperCase().equals(
                        str.toUpperCase())) {
                    artist.addAOTM(n);
                    newArtist = false;
                }
            }

            if (newArtist) {
                ARTISTS.add(new Artist(str, true));
                ARTISTS.get(ARTISTS.size() - 1).addAOTM(n);
            }
        }
    }

    class StatsScreen extends JPanel {

        private final JPanel allTimeScreen = new JPanel();
        private final JPanel yearlyScreen = new JPanel();
        private final Color backgroundColor = myGreenColor;

        public StatsScreen() {
            initScreen();
            setUpScrollPane(this, allTimeScreen); 
        }

        private void initScreen() {
            allTimeScreen.removeAll();
            allTimeScreen.setLayout(new GridBagLayout());
            allTimeScreen.setBackground(backgroundColor);

            //top artists of all time
            final int MAXARTISTS = 60; // have this equal to how many numbers are in place array
            List<Artist> allTime = getTopArtists(MAXARTISTS);

            JLabel titleLbl = new JLabel();
            titleLbl.setPreferredSize(new Dimension(200, 50));
            titleLbl.setFont(boldFont);
            titleLbl.setText("All Time Artists");

            JButton backBtn = new JButton("Back");
            backBtn.setPreferredSize(new Dimension(120, 70));
            backBtn.setFont(plainFont);
            backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
                viewScreen("Monthly Screen");
            });
            
            JButton yearlyButton = new JButton("See Yearly Screen");
            yearlyButton.setPreferredSize(new Dimension(150, 30));
            yearlyButton.setFont(plainFont);
            yearlyButton.addActionListener((java.awt.event.ActionEvent e) -> {
                viewScreen("Monthly Screen");
            });

            GridBagConstraints a = new GridBagConstraints();
            GridBagConstraints b = new GridBagConstraints();
            GridBagConstraints c = new GridBagConstraints();

            a.gridx = 0;
            a.gridy = 0;
            a.weightx = .5;
            a.weighty = .5;
            //a.anchor = GridBagConstraints.LINE_START;
            allTimeScreen.add(titleLbl, a);

            b.gridx = 0;
            b.gridy = 1;
            b.weightx = .5;
            b.weighty = .3;

            List<JLabel> allTimeLabels = new ArrayList<>();
            int samePlace = 0;
            for (int i = 0; i < allTime.size(); i++) {
                allTimeLabels.add(new JLabel());
                allTimeLabels.get(i).setPreferredSize(new Dimension(400, 50));
                allTimeLabels.get(i).setFont(plainFont);
                if (i != 0 && allTime.get(i).getTotalScore() == allTime.get(i - 1).getTotalScore()) {
                    samePlace++;
                } else {
                    samePlace = 0;
                }
                allTimeLabels.get(i).setText(PLACE[i - samePlace] + ".   " + allTime.get(i).getName()
                        + "        Score - " + allTime.get(i).getTotalScore());
                allTimeScreen.add(allTimeLabels.get(i), b);
                b.gridy++;
            }

            c.gridx = 0;
            c.gridy = b.gridy;
            c.weightx = .5;
            c.weighty = .1;
            c.anchor = GridBagConstraints.LAST_LINE_END;
            allTimeScreen.add(backBtn, c);
        }

        public void update() {
            initScreen();
        }

        private List<Artist> getTopArtists(int maxTop) {
            List<Artist> topArtists = new ArrayList<>();

            for (Artist artist : ARTISTS) {
                boolean end = true;
                for (int j = 0; j < topArtists.size(); j++) {
                    if (artist.getTotalScore() > topArtists.get(j).getTotalScore()) {
                        topArtists.add(j, artist);
                        end = false;
                        break;
                    }
                }
                if (end) {
                    topArtists.add(artist);
                }
            }
            int topArtistSize = topArtists.size();
            for (int i = maxTop; i < topArtistSize; i++) {
                topArtists.remove(maxTop);
            }
            return topArtists;
        }
    }

    /**
     * A screen that graphs the movement of the playlist as it progresses through time.
     */
    class GraphScreen extends JPanel {

        private final JPanel midLevelScreen = new JPanel(); //panel within a panel
        private final Color backgroundColor = myGreenColor;
        private GraphPanel graphPanel;

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
            int m = 0; // current month being used for label
            int rowLocation = 0;
            int sizeOfRow = 0;
            List prevMonthArtists = new ArrayList();
            

            for (int i = 0; i < artistsOfTheMonth.size(); i++) {
                if (i == 0) {
                    String str = artistsOfTheMonth.get(i);
                    m = Integer.parseInt(str.substring(0, str.indexOf(" ")));
                } else if (i == artistsOfTheMonth.size() - 1) {
                    // do nothing
                } else if (artistsOfTheMonth.get(i).equals("space")) {
                    JLabel lbl = new JLabel(MONTHS[m]);
                    lbl.setHorizontalTextPosition(SwingConstants.CENTER);
                    if (sizeOfRow < 20) {
                        sizeOfRow = 20;
                    }
                    lbl.setSize(sizeOfRow, 20); // need to make this currect size
                    months++;

                    GridBagConstraints a = new GridBagConstraints();

                    a.gridx = rowLocation;
                    a.gridy = 0;
                    a.weightx = .1;
                    a.weighty = .1;
                    midLevelScreen.add(lbl, a);

                    rowLocation++;
                    m++;
                    if (m == 12) {
                        m = 0;
                    }
                    prevMonthArtists.clear();
                    sizeOfRow = 0;
                }
                else {
                    String str = artistsOfTheMonth.get(i);
                    if (str.contains("&")) {
                        String[] tiedArtists = str.split("&");
                        
                        for (int j = 0; j < tiedArtists.length; j++) {
                            if (!prevMonthArtists.contains(tiedArtists[j])) {
                                if (sizeOfRow < ((int)(tiedArtists[j].length() *1.5) + j*30)+10) {
                                    sizeOfRow = ((int)(tiedArtists[j].length() *1.5) + j*30)+10;
                                }
                            }
                            prevMonthArtists.add(tiedArtists[j]);
                        }
                    } else {
                    
                        if (!prevMonthArtists.contains(str)) {
                            if (sizeOfRow < (int)(str.length() *1.5)+10) {
                                sizeOfRow = ((int)(str.length() *1.5))+10;
                            }
                        }            
                        prevMonthArtists.add(str);    
                    }
                }
            }

            graphPanel = new GraphPanel(months);
            
            JButton backBtn = new JButton("Back");
            backBtn.setPreferredSize(new Dimension(100, 50));
            backBtn.setFont(plainFont);
            backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
                viewScreen("Monthly Screen");
            });

            GridBagConstraints b = new GridBagConstraints();
            GridBagConstraints c = new GridBagConstraints();
            
            
            b.gridx = 0;
            b.gridy = 1;
            b.gridwidth = months; // might need to be changed
            b.weightx = 1;
            b.weighty = .9;
            midLevelScreen.add(graphPanel, b);
            
            c.gridx = months+1;
            c.gridy = 2;
            c.weightx = .2;
            c.weighty = .2;
            midLevelScreen.add(backBtn, c);
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
            //int rightSpacer = 0;
            int numHolder = 0;
            int month = 0;
            int[] yCord = {50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
            final int BALLSIZE = 12;
            final int LINEOFFSET = 4;
            boolean active = false;

            public GraphPanel(int m) {
                this.setLayout(null);
                month = m;
                // size, m is # of months 
                //this.setPreferredSize(new Dimension((m * 153) + rightSpacer+5, 600));
                Border raisedbevel = BorderFactory.createRaisedBevelBorder();
                Border loweredbevel = BorderFactory.createLoweredBevelBorder();
                this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel), "GRAPH"));
                this.setBackground(Color.white);
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                place = 0;
                xCord = 10;
                int rowLength = 0;
                int prevRowLength = 0;
                
                // going through each line of aotm and graphing it
                for (int i = 2; i < artistsOfTheMonth.size(); i++) {

                    // checking for last month
                    /*
                    if (i >= artistsOfTheMonth.size() - artistsOfTheMonth.getLastMonthSize()) {
                        active = true;
                    }*/
                    
                    if (artistsOfTheMonth.get(i).equals("space")) {
                        place = 0;
                        xCord += rowLength; // changed this was 153
                        prevRowLength = rowLength;
                        rowLength = 0;
                    } else {

                        String curArtist = (artistsOfTheMonth.get(i));

                        // dealing with ties                   
                        if (curArtist.contains("&")) {
                            int tiedXCord = xCord;
                            int lblCount = 0;
                            int count = -1;
                            String[] tiedArtists = curArtist.split("&");
                            for (String a : tiedArtists) {
                                count++;
                                for (Artist artist : ARTISTS) {
                                    if (artist.getName().equals(a)) { //found which artist it is
                                        g2.setColor(artist.getColor());
                                        int x = artist.getPrevXCord()+LINEOFFSET;
                                        int y = artist.getPrevYCord()+LINEOFFSET;
                                        if (x > xCord-(prevRowLength+5)) {
                                            g2.drawLine(x, y, tiedXCord+LINEOFFSET, yCord[place]+LINEOFFSET);
                                        } else {
                                            
                                            
                                            
                                            // might need to change this code    
                                            /*
                                            if (active) {
                                                Double d = artist.getName().length()*1.5;                              
                                                numHolder = (d.intValue())+(30*count);
                                                if (numHolder > rightSpacer) {
                                                    rightSpacer = numHolder;
                                                }                               

                                            }
                                            */
                                            // new code
                                            if (rowLength < (findArtistLength(artist) + (30*count))) {
                                                rowLength = findArtistLength(artist) + (30*count);
                                            }
                                                
                                            JLabel lbl = new JLabel(artist.getName());
                                            lbl.setFont(plainFont);
                                            lbl.setSize(130,40);
                                            lbl.setLocation(tiedXCord, yCord[place]+8+(12*lblCount));     
                                            lblCount++;
                                            this.add(lbl);
                                        }
                                        artist.setPrevCords(tiedXCord, yCord[place]);
                                        break;
                                    }
                                }

                                // new code
                                if (rowLength < 30 * count) {
                                    rowLength = 30 * count;
                                }
                                
                                g2.fillOval(tiedXCord, yCord[place], BALLSIZE, BALLSIZE);                               
                                tiedXCord += 30;                          
                            }
                            // might change
                            /*
                            if (active) {
                                
                                if (((tiedArtists.length-1))*30 > rightSpacer) {
                                    rightSpacer = (tiedArtists.length-1)*30;
                                }
                            }*/
                        
                            lblCount = 0;
                            count = -1;
                            place += tiedArtists.length;
                        } else {
                            for (Artist artist : ARTISTS) {
                                if (artist.getName().equals(curArtist)) {
                                    g2.setColor(artist.getColor());
                                    int x = artist.getPrevXCord()+LINEOFFSET;
                                    int y = artist.getPrevYCord()+LINEOFFSET;
                                    if (x > xCord-(prevRowLength+5)) { // 250 is random
                                        g2.drawLine(x, y, xCord+LINEOFFSET, yCord[place]+LINEOFFSET);
                                    } else {
                                        
                                        // might change
                                        /*
                                        if (active) {
                                            Double d = artist.getName().length()*1.5;                           
                                            numHolder = d.intValue();
                                            if (numHolder > rightSpacer) {
                                                rightSpacer = numHolder;
                                            }
                                        }*/
                                        
                                        if (rowLength < findArtistLength(artist)) {
                                            rowLength = findArtistLength(artist);
                                        }
                                        
                                        JLabel lbl = new JLabel(artist.getName());
                                        lbl.setFont(plainFont);
                                        lbl.setSize(130,40);
                                        lbl.setLocation(xCord, yCord[place]+8);
                                        this.add(lbl);
                                    }
                                    artist.setPrevCords(xCord, yCord[place]);
                                    break;
                                }
                            }

                            g2.fillOval(xCord, yCord[place], BALLSIZE, BALLSIZE);
                            place++;
                        }
                        if (rowLength < 20) {
                            rowLength = 20;
                        }
                    }
                    //active = false;
                }
                
                for (Artist artist : ARTISTS) {
                    artist.setPrevCords(0, 0);
                }
                
                // graph size setting
                this.setPreferredSize(new Dimension(xCord, 600));
                this.revalidate();
                //rightSpacer = 0;
            }
        }
        
        private int findArtistLength(Artist artist) {
            return (int)(artist.getName().length() * 7.5);
        }
    }
    
    


    /**
     * Makes font with given fileAddress, style and size
     *
     * @param fileAddress
     * @param style
     * @param size
     * @return the created font
     */
    public static Font makeFont(String fileAddress, int style, int size) {
        File fontFile = new File(fileAddress);
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            font = font.deriveFont(style, size);
            return font;
        } catch (FontFormatException | IOException e) {
            System.err.println("Font is null  makeFont : StatsCenter");
            return null;
        }
    }

    /**
     * sets up the scrollPane for 2 jPanels
     */
    private void setUpScrollPane(JPanel portScreen, JPanel viewScreen) {
        portScreen.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(viewScreen);
        scrollPane.getViewport().setBackground(myGreenColor);
        portScreen.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * lets viewer see a new screen
     *
     * @param str screen name
     */
    private void viewScreen(String str) {
        CardLayout cardLayout = (CardLayout) viewer.getLayout();
        cardLayout.show(viewer, str);
    }
}
