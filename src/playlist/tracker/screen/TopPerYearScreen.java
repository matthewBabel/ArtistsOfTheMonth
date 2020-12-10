/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist.tracker.screen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static playlist.tracker.AppCenter.artistsOfTheMonth;
import static playlist.tracker.AppCenter.boldFont;
import static playlist.tracker.AppCenter.getPlaceAsString;
import static playlist.tracker.AppCenter.mediumBoldFont;
import static playlist.tracker.AppCenter.myGreenColor;
import static playlist.tracker.AppCenter.plainFont;
import static playlist.tracker.AppCenter.setUpScrollPane;
import static playlist.tracker.AppCenter.startMonth;
import static playlist.tracker.AppCenter.viewScreen;
import static playlist.tracker.AppCenter.year;

/**
 *
 * @author Matt
 */
public class TopPerYearScreen extends JPanel {

    private JPanel viewPort;

    public TopPerYearScreen() {
        viewPort = new JPanel();
        setUpScrollPane(this, viewPort);
        initScreen();
    }

    private void initScreen() {
        viewPort.removeAll();
        viewPort.setLayout(new GridBagLayout());
        viewPort.setBackground(myGreenColor);

        JButton backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(120, 70));
        backBtn.setFont(plainFont);
        backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen("Advanced Stats Screen");
        });

        JLabel titleLbl = new JLabel();
        titleLbl.setPreferredSize(new Dimension(400, 30));
        titleLbl.setFont(boldFont);
        titleLbl.setText("Top Artists By Year");

        JLabel statTracker = new JLabel();
        statTracker.setPreferredSize(new Dimension(300, 24));
        statTracker.setFont(mediumBoldFont);
        statTracker.setText("Year - " + year);

        GridBagConstraints titleCon = new GridBagConstraints();
        GridBagConstraints statTrackerCon = new GridBagConstraints();
        GridBagConstraints labelCon = new GridBagConstraints();
        GridBagConstraints backCon = new GridBagConstraints();

        titleCon.gridx = 0;
        titleCon.gridy = 0;
        titleCon.gridwidth = 2;
        titleCon.weightx = 1;
        titleCon.weighty = .1;
        titleCon.anchor = GridBagConstraints.NORTH;
        viewPort.add(titleLbl, titleCon);

        statTrackerCon.gridx = 0;
        statTrackerCon.gridy = 1;
        statTrackerCon.weightx = .5;
        statTrackerCon.weighty = .05;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        viewPort.add(statTracker, statTrackerCon);

        labelCon.gridx = 0;
        labelCon.gridy = 2;
        labelCon.weightx = .5;
        labelCon.weighty = .01;
        labelCon.insets = new Insets(0, 30, 0, 0);
        labelCon.anchor = GridBagConstraints.WEST;

        labelLoop(statTrackerCon, labelCon);

        backCon.gridx = 1;
        backCon.gridy = labelCon.gridy + 1;
        backCon.weightx = .2;
        backCon.weighty = .1;
        backCon.insets = new Insets(0, 0, 10, 10);
        backCon.anchor = GridBagConstraints.LAST_LINE_END;
        viewPort.add(backBtn, backCon);
    }

    private void labelLoop(GridBagConstraints statTrackerCon, GridBagConstraints labelCon) {

        int intYear = Integer.valueOf(year);
        int monthNum = startMonth;

        ArrayList<ArtistYearlyScoreInfo> yearlyArtists = new ArrayList<>();

        int place = 1;
        for (int i = 1; i < artistsOfTheMonth.size(); i++) { //looping through every line of artistsofthemonth file
            String line = artistsOfTheMonth.get(i);

            if (line.equals("space")) { // new month, check for new year
                if (monthNum == 12) {
                    monthNum = 0;
                    intYear += 1;
                    endYear(yearlyArtists, labelCon, intYear, statTrackerCon, true);
                } else if (i == (artistsOfTheMonth.size() - 1)) {
                    intYear += 1;
                    endYear(yearlyArtists, labelCon, intYear, statTrackerCon, false); // if the last month then just post the yearly scores so far
                }
                place = 1;
                monthNum++;
            } else { // artist line
                String[] names = line.split("&");
                for (String foundName : names) {
                    boolean found = false;
                    for (ArtistYearlyScoreInfo artist : yearlyArtists) {
                        if (artist.name.toUpperCase().equals(foundName.toUpperCase())) {
                            artist.score += (11 - place);
                            found = true;
                        }
                        if (found) {
                            break;
                        }
                    }

                    if (!found) {
                        yearlyArtists.add(new ArtistYearlyScoreInfo(foundName, (11 - place)));
                    }
                }

                place += names.length;
            }
        }
    }

    private void endYear(ArrayList<ArtistYearlyScoreInfo> yearlyArtists, GridBagConstraints labelCon, int intYear, GridBagConstraints statTrackerCon, boolean addTrackerLabel) {
        addArtistLabels(yearlyArtists, labelCon);
        yearlyArtists.clear();

        if (addTrackerLabel) {
            JLabel statTracker = new JLabel();
            statTracker.setPreferredSize(new Dimension(300, 24));
            statTracker.setFont(mediumBoldFont);
            statTracker.setText("Year - " + intYear);
            statTrackerCon.gridy = labelCon.gridy + 2;
            labelCon.gridy += 2;
            viewPort.add(statTracker, statTrackerCon);
        }
    }

    private void addArtistLabels(ArrayList<ArtistYearlyScoreInfo> artists, GridBagConstraints con) {
        Collections.sort(artists);
        Collections.reverse(artists);

        int place = 1;
        int samePlace = 0;
        int savedNum = 0;

        for (ArtistYearlyScoreInfo info : artists) {
            if (info.score == savedNum) {
                samePlace++;
            } else {
                savedNum = info.score;
                samePlace = 0;
            }

            JLabel lbl = new JLabel(getPlaceAsString(place - samePlace) + ". " + info.name + " - " + info.score);
            lbl.setSize(400, 5);
            lbl.setFont(plainFont);
            con.gridy++;
            place++;
            viewPort.add(lbl, con);
        }
    }

    class ArtistYearlyScoreInfo implements Comparable {

        public String name;
        public int score = 0;

        public ArtistYearlyScoreInfo(String artistName, int initialScore) {
            name = artistName;
            score = initialScore;
        }

        @Override
        public int compareTo(Object o) {
            ArtistYearlyScoreInfo other = (ArtistYearlyScoreInfo) o;

            if (this.score < other.score) {
                return -1;
            } else if (this.score > other.score) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public boolean equals(Object o) {
            ArtistYearlyScoreInfo otherArtist = (ArtistYearlyScoreInfo) o;

            return (this.name.equals(otherArtist.name));
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 67 * hash + Objects.hashCode(this.name);
            return hash;
        }
    }

    public void update() {
        initScreen();
    }
}
