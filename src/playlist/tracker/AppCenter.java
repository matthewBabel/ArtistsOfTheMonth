package playlist.tracker;

import playlist.tracker.screen.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
    public static final Color myGreenColor = new Color(165, 230, 162);
    public static Font smallFont;
    public static Font plainFont;
    public static Font mediumBoldFont;
    public static Font boldFont;
    public static Font extraBigFont;
    public static OpeningScreen openingScreen;
    public static MonthlyScreen monthlyScreen;
    public static AddScreen addScreen;
    public static ViewScreen viewScreen;
    public static StatsScreen statsScreen;
    public static GraphScreen graphScreen;
    public static AdvancedStatsScreen advancedStatsScreen;
    public static TopPerPlaceScreen topPerPlaceScreen;
    public static MiscStatsScreen miscStatsScreen;
    public static TopPerYearScreen topPerYearScreen;
    public static OrderStatsScreen orderStatsScreen;
    private static JPanel viewer;
    public static FileData artistsOfTheMonth;
    public static final List<Artist> ARTISTS = new ArrayList<>();

    public static final String BASEDIR = configureBaseDir(); 

    //setting up screens of view
    public AppCenter() {

        
        JFrame frame = new JFrame("Artists of the Month");
        frame.setTitle("Music Database");
        frame.setLocation(100, 20);
        frame.setSize(1200, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        smallFont = makeFont(BASEDIR + "/resources/fonts/PT_Sans-Web-Regular.ttf", Font.BOLD, 15);
        plainFont = makeFont(BASEDIR + "/resources/fonts/PT_Sans-Web-Regular.ttf", Font.PLAIN, 20);
        mediumBoldFont = makeFont(BASEDIR + "/resources/fonts/PT_Sans-Web-Bold.ttf", Font.BOLD, 22);
        boldFont = makeFont(BASEDIR + "/resources/fonts/PT_Sans-Web-Bold.ttf", Font.BOLD, 30);
        extraBigFont = makeFont(BASEDIR + "/resources/fonts/New Athletic M54.ttf", Font.PLAIN, 47);

        openingScreen = new OpeningScreen();
        monthlyScreen = new MonthlyScreen();
        viewScreen = new ViewScreen();
        addScreen = new AddScreen();
        statsScreen = new StatsScreen();
        advancedStatsScreen = new AdvancedStatsScreen(); // this is going to hold 3-4 new screens
        topPerPlaceScreen = new TopPerPlaceScreen();
        miscStatsScreen = new MiscStatsScreen();
        topPerYearScreen = new TopPerYearScreen();
        orderStatsScreen = new OrderStatsScreen();
        graphScreen = new GraphScreen();

        viewer = new JPanel(new CardLayout()); // for switching screens
        viewer.add(openingScreen, "Opening Screen");
        viewer.add(monthlyScreen, "Monthly Screen");
        viewer.add(viewScreen, "View Screen");
        viewer.add(addScreen, "Add Screen");
        viewer.add(statsScreen, "Stats Screen");
        viewer.add(advancedStatsScreen, "Advanced Stats Screen");
        viewer.add(graphScreen, "Graph Screen");
        viewer.add(topPerPlaceScreen, "Top Per Place Screen");
        viewer.add(miscStatsScreen, "Misc Screen");
        viewer.add(topPerYearScreen, "Top Per Year Screen");
        viewer.add(orderStatsScreen, "Order Stats Screen");
        viewScreen("Opening Screen");

        frame.add(viewer);
        frame.setVisible(true);
        
        System.out.println(BASEDIR);
    }

    // starting point, creates a FileData to hold and control data
    public static void main(String[] args) {
        artistsOfTheMonth = new FileData(BASEDIR + "/resources/data/monthlyArtists.txt");
        FileData artistFolder = new FileData(BASEDIR + "/resources/data/artists");
        for (int i = 0; i < artistFolder.size(); i++) {
            ARTISTS.add(new Artist(artistFolder.get(i), false));
        }

        AppCenter md = new AppCenter();
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
    public static void setUpScrollPane(JPanel portScreen, JPanel viewScreen) {
        portScreen.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().add(viewScreen);
        scrollPane.getViewport().setBackground(myGreenColor);
        portScreen.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * lets viewer see a new screen
     *
     * @param str screen name
     */
    public static void viewScreen(String str) {
        CardLayout cardLayout = (CardLayout) viewer.getLayout();
        cardLayout.show(viewer, str);
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
}
