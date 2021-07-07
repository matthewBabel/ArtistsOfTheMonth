package playlist.tracker;

import playlist.tracker.artist.ArtistRecordHandler;
import playlist.tracker.artist.ArtistRecordMaker;
import playlist.tracker.screen.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import playlist.tracker.component.button.SmallButton;
import playlist.tracker.font.FontHandler;
import playlist.tracker.frame.AppFrame;

/**
 * Tracks various music related things, including an artist of the month tracker
 *
 * alpha v1.0.1
 *
 * @author Matt Babel
 */
public final class AppCenter {     // TODO : key for point values,  

    public static final String[] MONTHS = {"January", "February", "March", "April",
        "May", "June", "July", "August", "September", "October",
        "November", "December"};
    public static boolean monthAssigned = false;
    public static int startMonth = -1;
    public static String year = "";
    public static final Color MYGREEN = new Color(165, 230, 162);
    public static OpeningScreen openingScreen;
    public static MainMenuScreen menuScreen;
    public static AddScreen addScreen;
    public static ViewScreen viewScreen;
    public static AllTimeScreen allTimeScreen;
    public static GraphScreen graphScreen;
    public static StatsScreen statsScreen;
    public static TopPerPlaceScreen topPerPlaceScreen;
    public static MiscStatsScreen miscStatsScreen;
    public static TopPerYearScreen topPerYearScreen;
    public static OrderStatsScreen orderStatsScreen;
    public static BiographyScreen biographyScreen;
    private static JPanel viewer;
    public static FileData artistsOfTheMonth;
    public static ArtistRecordHandler artistHandler;
    public static ArtistRecordMaker recordMaker;
    public static AppFrame frame;

    public static final String BASEDIR = configureBaseDir();

    private static String shownScreenStr;

    public static int fontCharWidth = 0;

    public enum Screen {
        OPENING("Opening Screen", openingScreen),
        MAINMENU("Monthly Screen", menuScreen),
        VIEW("View Screen", viewScreen),
        ADD("Add Screen", addScreen),
        ALLTIME("All Time Screen", allTimeScreen),
        STATS("Stats Screen", statsScreen),
        GRAPH("Graph Screen", graphScreen),
        PERPLACE("Top Per Place Screen", topPerPlaceScreen),
        PERYEAR("Top Per Year Screen", topPerYearScreen),
        MISC("Misc Screen", miscStatsScreen),
        INORDER("Order Stats Screen", orderStatsScreen),
        BIOGRAPHY("Biography Screen", biographyScreen);

        public final String label;
        public UpdatingScreen screen;

        private Screen(String label, UpdatingScreen screen) {
            this.label = label;
            this.screen = screen;
        }
    }





    //setting up screens of view
    public AppCenter() {
        FontHandler.initFonts();
      
    }

    // starting point, creates a FileData to hold and control data
    public static void main(String[] args) {
        artistsOfTheMonth = new FileData(BASEDIR + "/resources/data/monthlyArtists.txt");
        artistHandler = new ArtistRecordHandler();
        recordMaker = new ArtistRecordMaker(); 
        frame = new AppFrame();
        
        AppCenter md = new AppCenter();

        MainMenuScreen.tryGetMonth();
        recordStats();
        setUpScreens();

    }

    public static void recordStats() {
        recordMaker.makeRecords();
        artistHandler.sortAll();
    }

    private static void setUpScreens() {
        openingScreen = new OpeningScreen();
        menuScreen = new MainMenuScreen();
        viewScreen = new ViewScreen();
        addScreen = new AddScreen();
        allTimeScreen = new AllTimeScreen();
        statsScreen = new StatsScreen(); // this is going to hold 3-4 new screens
        topPerPlaceScreen = new TopPerPlaceScreen();
        miscStatsScreen = new MiscStatsScreen();
        topPerYearScreen = new TopPerYearScreen();
        orderStatsScreen = new OrderStatsScreen();
        graphScreen = new GraphScreen();
        biographyScreen = new BiographyScreen();

        viewer = new JPanel(new CardLayout()); // for switching screens
        viewer.add(openingScreen, Screen.OPENING.label);
        viewer.add(menuScreen, Screen.MAINMENU.label);
        viewer.add(viewScreen, Screen.VIEW.label);
        viewer.add(addScreen, Screen.ADD.label);
        viewer.add(allTimeScreen, Screen.ALLTIME.label);
        viewer.add(statsScreen, Screen.STATS.label);
        viewer.add(graphScreen, Screen.GRAPH.label);
        viewer.add(topPerPlaceScreen, Screen.PERPLACE.label);
        viewer.add(miscStatsScreen, Screen.MISC.label);
        viewer.add(topPerYearScreen, Screen.PERYEAR.label);
        viewer.add(orderStatsScreen, Screen.INORDER.label);
        viewer.add(biographyScreen, Screen.BIOGRAPHY.label);
        viewScreen(Screen.OPENING.label);

        frame.add(viewer);
        frame.setVisible(true);
    }

    /**
     * sets up the scrollPane for 2 jPanels
     *
     * @param portScreen
     * @param viewScreen
     */
    public static void setUpScrollPane(JPanel portScreen, JPanel viewScreen) {
        portScreen.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().add(viewScreen);
        scrollPane.getViewport().setBackground(MYGREEN);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        portScreen.add(scrollPane, BorderLayout.CENTER);
    }

    public static void setUpBottomBackBtn(JPanel outerScreen, JPanel scrollPanel, SmallButton backBtn) {
        outerScreen.removeAll();
        GridBagConstraints con = new GridBagConstraints();
        con.gridx = 0;
        con.gridy = 0;
        con.weightx = 1;
        con.weighty = .99;
        con.fill = GridBagConstraints.BOTH;
        outerScreen.add(scrollPanel, con);

        con.gridy = 1;
        con.weighty = .01;
        con.fill = GridBagConstraints.NONE;
        con.anchor = GridBagConstraints.SOUTHEAST;
        outerScreen.add(backBtn, con);
    }

    /**
     * lets viewer see a new screen
     *
     * @param str screen name
     */
    public static void viewScreen(String str) {
        CardLayout cardLayout = (CardLayout) viewer.getLayout();

        shownScreenStr = str;
        UpdatingScreen screen = getUpdatingScreen(str);
        screen.update();
        screen.update();

        cardLayout.show(viewer, str);
    }

    private static UpdatingScreen getUpdatingScreen(String str) {
        for (Screen s : Screen.values()) {
            if (s.label.equals(str)) {
                return s.screen;
            }
        }

        return null;
    }

    public static void updateShownScreen() {
        UpdatingScreen screen = getUpdatingScreen(shownScreenStr);
        screen.update();
    }

    public static String getPlaceAsString(int num) {
        String numStr = "" + num;
        //st - 1, 21, 31, 41
        //nd - 2, 22, 32, 42
        //rd - 3, 23, 33, 43, 54,
        //th - 4 - 20
        // if ends with 1 and isn't 11 - st
        // if ends with 2 and isn't 12 - nd
        // if ends with 3 and isn't 13 - rd
        // else - th

        if (numStr.endsWith("1") && !numStr.endsWith("11")) {
            return numStr + "st";
        } else if (numStr.endsWith("2") && !numStr.endsWith("12")) {
            return numStr + "nd";
        } else if (numStr.endsWith("3") && !numStr.endsWith("13")) {
            return numStr + "rd";
        } else {
            return numStr + "th";
        }
    }

    public static String readableMonthsString(int months) {
        if (months == 0) {
            return "0";
        }

        if (months < 12) {
            return (months == 1) ? months + " month" : months + " months";
        }

        int years = months / 12;
        int monthsRemainder = months % 12;

        if (monthsRemainder == 0) {
            return (years == 1) ? years + " year" : years + " years";
        }

        return ((years == 1) ? years + " year " : years + " years ") + ((monthsRemainder == 1) ? monthsRemainder + " month" : monthsRemainder + " months");
    }

    public static boolean isColorDark(Color color) {
        double brightness = 0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue();

        return (brightness < 128);
    }

    public static String configureBaseDir() {
        String dir = new File("").getAbsolutePath();

        return dir;
    }

    // timeIndex 0 starts at first month
    public static String getReadableTime(int monthTimeIndex) {
        int intYear = Integer.valueOf(year);
        int monthNum = startMonth;

        if (monthTimeIndex > (11 - monthNum)) {
            monthTimeIndex -= (12 - monthNum);
            intYear++;
        } else {
            return MONTHS[monthNum + monthTimeIndex] + " " + intYear;
        }

        while (monthTimeIndex >= 12) {
            monthTimeIndex -= 12;
            intYear++;
        }

        monthNum = monthTimeIndex;
        return MONTHS[monthNum] + " " + intYear;
    }
}
