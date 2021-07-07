package playlist.tracker.screen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JPanel;
import playlist.tracker.AppCenter;
import static playlist.tracker.AppCenter.MONTHS;
import static playlist.tracker.AppCenter.artistsOfTheMonth;
import static playlist.tracker.font.FontHandler.*;
import static playlist.tracker.AppCenter.setUpScrollPane;
import static playlist.tracker.AppCenter.startMonth;
import static playlist.tracker.AppCenter.viewScreen;
import static playlist.tracker.AppCenter.year;
import playlist.tracker.artist.Artist;
import static playlist.tracker.AppCenter.MYGREEN;
import playlist.tracker.component.button.SmallButton;
import playlist.tracker.component.label.ExtraLargeLabel;
import playlist.tracker.component.label.LargeSkinnyLabel;
import playlist.tracker.component.label.MediumLabel;

/**
 *
 * @author Matt
 */
public class OrderStatsScreen extends UpdatingScreen {

    private JPanel innerScrollScreen = new JPanel();
    private final JPanel scrollContainer = new JPanel();
    
    public OrderStatsScreen() {
        setUpScrollPane(scrollContainer, innerScrollScreen);
        initScreen();
    }

    @Override
    protected void initScreen() {
        removeAll();
        setLayout(new GridBagLayout());
        setBackground(MYGREEN);
        
        innerScrollScreen.removeAll();
        innerScrollScreen.setLayout(new GridBagLayout());
        innerScrollScreen.setBackground(MYGREEN);

        SmallButton backBtn = new SmallButton("Back");
        backBtn.setPreferredSize(new Dimension(120, 70));
        backBtn.setFont(plainFont);
        backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen("Stats Screen");
        });
        components.add(backBtn);

        ExtraLargeLabel titleLbl = new ExtraLargeLabel("In Order Stats", true);
        components.add(titleLbl);

        MediumLabel statTracker = new MediumLabel("Year - " + year);
        components.add(statTracker);

        GridBagConstraints titleCon = new GridBagConstraints();
        GridBagConstraints yearCon = new GridBagConstraints();
        GridBagConstraints monthCon = new GridBagConstraints();
        GridBagConstraints labelCon = new GridBagConstraints();

        titleCon.gridx = 0;
        titleCon.gridy = 0;
        titleCon.gridwidth = 2;
        titleCon.weightx = 1;
        titleCon.weighty = .1;
        titleCon.anchor = GridBagConstraints.NORTH;
        innerScrollScreen.add(titleLbl, titleCon);

        yearCon.gridx = 0;
        yearCon.gridy = 1;
        yearCon.weightx = .5;
        yearCon.weighty = .05;
        yearCon.insets = new Insets(20, 30, 0, 0);
        yearCon.anchor = GridBagConstraints.WEST;
        innerScrollScreen.add(statTracker, yearCon);

        monthCon.gridx = 0;
        monthCon.gridy = 2;
        monthCon.weightx = .5;
        monthCon.weighty = .05;
        monthCon.insets = new Insets(5, 30, 0, 0);
        monthCon.anchor = GridBagConstraints.WEST;

        labelCon.gridx = 0;
        labelCon.gridy = 2;
        labelCon.weightx = .5;
        labelCon.weighty = .01;
        labelCon.insets = new Insets(0, 50, 0, 0);
        labelCon.anchor = GridBagConstraints.WEST;

        labelLoop(yearCon, monthCon, labelCon);

        AppCenter.setUpBottomBackBtn(this, scrollContainer, backBtn);
        revalidate();
    }

    private void labelLoop(GridBagConstraints yearCon, GridBagConstraints monthCon, GridBagConstraints labelCon) {
        int intYear = Integer.valueOf(year);
        int monthNum = startMonth;

        ArrayList<String> artistsPlaced = new ArrayList<>();

        for (int i = 1; i < artistsOfTheMonth.size(); i++) { //looping through every line of artistsofthemonth file
            String line = artistsOfTheMonth.get(i);

            if (i == (artistsOfTheMonth.size() - 1)) {
                break;
            }

            if (line.equals("space")) { // new month, check for new year
                if (monthNum == 12) { // new year
                    monthNum = 0;
                    intYear++;

                    MediumLabel yearLbl = new MediumLabel("Year - " + intYear);
                    components.add(yearLbl);

                    yearCon.gridy = labelCon.gridy + 2;
                    labelCon.gridy += 2;
                    innerScrollScreen.add(yearLbl, yearCon);
                }

                LargeSkinnyLabel monthLbl = new LargeSkinnyLabel(MONTHS[monthNum] + ":");
                components.add(monthLbl);
                monthCon.gridy = labelCon.gridy + 1;
                labelCon.gridy += 2;
                innerScrollScreen.add(monthLbl, monthCon);

                monthNum++;

            } else { // artist line
                String[] names = line.split("&");
                for (String foundName : names) {
                    boolean found = false;
                    for (String artist : artistsPlaced) {
                        if (artist.toUpperCase().equals(foundName.toUpperCase())) {
                            found = true;
                        }
                        if (found) {
                            break;
                        }
                    }

                    if (!found) {
                        artistsPlaced.add(foundName);

                        Artist foundArtist = null;
                        for (Artist a : AppCenter.artistHandler.getArtistsList()) {
                            if (a.getName().equals(foundName)) {
                                foundArtist = a;
                                break;
                            }
                        }

                        LargeSkinnyLabel lbl = new LargeSkinnyLabel(foundArtist.getName() + " - " + foundArtist.getTotalScore());
                        components.add(lbl);
                        labelCon.gridy++;
                        innerScrollScreen.add(lbl, labelCon);
                    }
                }
            }
        }
    }
}
