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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
import static playlist.tracker.AppCenter.readableMonthsString;
import static playlist.tracker.AppCenter.setUpScrollPane;
import static playlist.tracker.AppCenter.viewScreen;

/**
 *
 * @author Matt
 */
public class MiscStatsScreen extends JPanel {

    private JPanel viewPort;

    public MiscStatsScreen() {
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
        titleLbl.setPreferredSize(new Dimension(200, 30));
        titleLbl.setFont(boldFont);
        titleLbl.setText("Misc Stats");

        JLabel statTracker = new JLabel();
        statTracker.setPreferredSize(new Dimension(300, 24));
        statTracker.setFont(mediumBoldFont);
        statTracker.setText("Longest Streak on Playlist");

        JLabel statTracker2 = new JLabel();
        statTracker2.setPreferredSize(new Dimension(300, 24));
        statTracker2.setFont(mediumBoldFont);
        statTracker2.setText("Most Total Time On Playlist");

        JLabel statTracker3 = new JLabel();
        statTracker3.setPreferredSize(new Dimension(300, 24));
        statTracker3.setFont(mediumBoldFont);
        statTracker3.setText("Average Placement On Playlist");

        JLabel statTracker4 = new JLabel();
        statTracker4.setPreferredSize(new Dimension(500, 24));
        statTracker4.setFont(mediumBoldFont);
        statTracker4.setText("Most Recurrences On Playlist");

        JLabel statTracker5 = new JLabel();
        statTracker5.setPreferredSize(new Dimension(500, 24));
        statTracker5.setFont(mediumBoldFont);
        statTracker5.setText("Most Time between a Recurrence");

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
        statTrackerCon.weighty = .1;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        viewPort.add(statTracker, statTrackerCon);

        labelCon.gridx = 0;
        labelCon.gridy = 2;
        labelCon.weightx = .5;
        labelCon.weighty = .01;
        labelCon.insets = new Insets(0, 30, 0, 0);
        labelCon.anchor = GridBagConstraints.WEST;

        createLongestStreakLabels(labelCon, getLongestArtists(20));

        statTrackerCon.gridx = 0;
        statTrackerCon.gridy = labelCon.gridy + 2;
        statTrackerCon.weightx = .5;
        statTrackerCon.weighty = .1;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        viewPort.add(statTracker2, statTrackerCon);
        labelCon.gridy += 2;

        createMostTotalMonthsOnPlaylist(labelCon, getMostTotalMonths(20));

        statTrackerCon.gridx = 0;
        statTrackerCon.gridy = labelCon.gridy + 2;
        statTrackerCon.weightx = .5;
        statTrackerCon.weighty = .1;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        viewPort.add(statTracker3, statTrackerCon);
        labelCon.gridy += 2;

        createAveragePositionOnPlaylist(labelCon, getAveragePosition(20));

        statTrackerCon.gridx = 0;
        statTrackerCon.gridy = labelCon.gridy + 2;
        statTrackerCon.weightx = .5;
        statTrackerCon.weighty = .1;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        viewPort.add(statTracker4, statTrackerCon);
        labelCon.gridy += 2;

        createMostReoccurencesLabels(labelCon, getMostReoccurences(20));

        statTrackerCon.gridx = 0;
        statTrackerCon.gridy = labelCon.gridy + 2;
        statTrackerCon.weightx = .5;
        statTrackerCon.weighty = .1;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        viewPort.add(statTracker5, statTrackerCon);
        labelCon.gridy += 2;

        createLongestBreakReoccurencesLabels(labelCon, getLongestBreakReoccurences(20));

        backCon.gridx = 1;
        backCon.gridy = labelCon.gridy + 1;
        backCon.weightx = .2;
        backCon.weighty = .1;
        backCon.insets = new Insets(0, 0, 10, 10);
        backCon.anchor = GridBagConstraints.LAST_LINE_END;
        viewPort.add(backBtn, backCon);
    }

    public void update() {
        initScreen();
    }

    /**
     * Add a list of labels to a grid bag layout
     *
     * @param c
     * @param labels
     * @param size
     */
    private void createLongestStreakLabels(GridBagConstraints c, List<ArtistStreakInfo> titles) {

        int place = 1;
        int samePlace = 0;
        int savedNum = 0;

        for (ArtistStreakInfo info : titles) {
            if (info.topStreak == savedNum) {
                samePlace++;
            } else {
                savedNum = info.topStreak;
                samePlace = 0;
            }

            JLabel lbl = new JLabel(getPlaceAsString(place - samePlace) + ". " + info.name + " - " + readableMonthsString(info.topStreak));
            lbl.setSize(400, 5);
            lbl.setFont(plainFont);
            c.gridy++;
            place++;
            viewPort.add(lbl, c);
        }
    }

    /**
     * Add a list of labels to a grid bag layout
     *
     * @param c
     * @param labels
     * @param size
     */
    private void createMostReoccurencesLabels(GridBagConstraints c, List<ArtistReoccurenceInfo> titles) {

        int place = 1;
        int samePlace = 0;
        int savedNum = 0;

        for (ArtistReoccurenceInfo info : titles) {
            if (info.reoccurences == savedNum) {
                samePlace++;
            } else {
                savedNum = info.reoccurences;
                samePlace = 0;
            }

            JLabel lbl = new JLabel(getPlaceAsString(place - samePlace) + ". " + info.name + " - " + info.reoccurences);
            lbl.setSize(400, 5);
            lbl.setFont(plainFont);
            c.gridy++;
            place++;
            viewPort.add(lbl, c);
        }
    }

    /**
     * Add a list of labels to a grid bag layout
     *
     * @param c
     * @param labels
     * @param size
     */
    private void createLongestBreakReoccurencesLabels(GridBagConstraints c, List<ArtistReoccurenceBreakInfo> titles) {

        int place = 1;
        int samePlace = 0;
        int savedNum = 0;

        for (ArtistReoccurenceBreakInfo info : titles) {
            if (info.topBreak == savedNum) {
                samePlace++;
            } else {
                savedNum = info.topBreak;
                samePlace = 0;
            }

            JLabel lbl = new JLabel(getPlaceAsString(place - samePlace) + ". " + info.name + " - " + readableMonthsString(info.topBreak));
            lbl.setSize(400, 5);
            lbl.setFont(plainFont);
            c.gridy++;
            place++;
            viewPort.add(lbl, c);
        }
    }

    /**
     * Add a list of labels to a grid bag layout
     *
     * @param c
     * @param labels
     * @param size
     */
    private void createMostTotalMonthsOnPlaylist(GridBagConstraints c, List<ArtistMostTotalMonths> titles) {

        int place = 1;
        int samePlace = 0;
        int savedNum = 0;

        for (ArtistMostTotalMonths info : titles) {
            if (info.total == savedNum) {
                samePlace++;
            } else {
                savedNum = info.total;
                samePlace = 0;
            }

            JLabel lbl = new JLabel(getPlaceAsString(place - samePlace) + ". " + info.name + " - " + readableMonthsString(info.total));
            lbl.setSize(400, 5);
            lbl.setFont(plainFont);
            c.gridy++;
            place++;
            viewPort.add(lbl, c);
        }
    }

    /**
     * Add a list of labels to a grid bag layout
     *
     * @param c
     * @param labels
     * @param size
     */
    private void createAveragePositionOnPlaylist(GridBagConstraints c, List<ArtistAveragePlace> titles) {

        int place = 1;
        int samePlace = 0;
        double savedNum = 0;

        for (ArtistAveragePlace info : titles) {
            if (info.avgPosition == savedNum) {
                samePlace++;
            } else {
                savedNum = info.avgPosition;
                samePlace = 0;
            }

            JLabel lbl = new JLabel(getPlaceAsString(place - samePlace) + ". " + info.name + " - " + info.avgPosition + " (" +  readableMonthsString((int)info.monthsNum) + " total)");
            lbl.setSize(600, 5);
            lbl.setFont(plainFont);
            c.gridy++;
            place++;
            viewPort.add(lbl, c);
        }
    }

    /**
     * Creates a list of string to represent the artists who have been on the
     * playlist for the longest time straight
     *
     * @return artists in ascending order
     */
    private List<ArtistStreakInfo> getLongestArtists(int max) {

        // one list that holds class called artistInfo
        HashSet<ArtistStreakInfo> artistStreaks = new HashSet<>();

        // first line of data is date created information so start at line 1
        for (int i = 1; i < artistsOfTheMonth.size(); i++) {
            String line = artistsOfTheMonth.get(i);
            if (line.equals("space") && i != (artistsOfTheMonth.size() - 1)) { //month reset
                artistStreaks.stream().forEach((artist) -> {
                    if (artist.curStreak != 0 && !artist.checked) {
                        artist.streakStop();
                    } else if (artist.checked) {
                        artist.checked = false;
                    }
                });
            } else {    // artist line
                String[] names = line.split("&");
                for (String foundName : names) {
                    boolean found = false;
                    for (ArtistStreakInfo info : artistStreaks) {
                        if (info.name.toUpperCase().equals(foundName.toUpperCase())) {
                            info.curStreak++;
                            info.checked = true;
                            found = true;
                        }
                        if (found) {
                            break;
                        }
                    }

                    if (!found) {
                        artistStreaks.add(new ArtistStreakInfo(foundName));
                    }
                }
            }
        }

        artistStreaks.forEach((artist) -> {
            if (artist.checked) {
                artist.streakStop();
            }
        });

        List<ArtistStreakInfo> artistList = new ArrayList<>(artistStreaks);
        Collections.sort(artistList);

        List<ArtistStreakInfo> savedArtists = new ArrayList<>();
        int savedNum = 0;

        while (max-1 < artistList.size()) {
            ArtistStreakInfo artist = artistList.get(0);
            if (artist.topStreak == savedNum) {
                savedArtists.add(artist);
            } else {
                savedArtists.clear();
                savedNum = artist.topStreak;
                savedArtists.add(artist);
            }
            artistList.remove(artist);
        }

        for (ArtistStreakInfo artist : savedArtists) {
            artistList.add(0, artist);
        }

        Collections.reverse(artistList);
        /*
            //reverse list            
            for (int i = 0; i < max; i++) {
                ArtistInfo keep = artistList.remove(max - (i + 1));
                artistList.add(keep);
            }
         */
        return artistList;
    }

    /**
     * Creates a list of string to represent the artists who have been on the
     * playlist for the longest time straight
     *
     * @return artists in ascending order
     */
    private List<ArtistReoccurenceInfo> getMostReoccurences(int max) {

        // one list that holds class called artistInfo
        HashSet<ArtistReoccurenceInfo> artistReoccurences = new HashSet<>();

        // first line of data is date created information so start at line 1
        for (int i = 1; i < artistsOfTheMonth.size(); i++) {
            String line = artistsOfTheMonth.get(i);
            if (line.equals("space")) { //month reset
                artistReoccurences.stream().forEach((artist) -> {
                    if (artist.wasOffPlayist && artist.isCurOnPlayist) {
                        artist.reoccurences++;
                        artist.wasOffPlayist = false;
                    } else if (!artist.isCurOnPlayist) {
                        artist.wasOffPlayist = true;
                    }
                    artist.isCurOnPlayist = false;
                });
            } else {    // artist line
                String[] names = line.split("&");
                for (String foundName : names) {
                    boolean found = false;
                    for (ArtistReoccurenceInfo info : artistReoccurences) {
                        if (info.name.toUpperCase().equals(foundName.toUpperCase())) { //seeing if this months artist is in local list
                            info.isCurOnPlayist = true;
                            found = true;
                        }
                        if (found) {
                            break;
                        }
                    }

                    if (!found) { // adding a new artist to the list
                        artistReoccurences.add(new ArtistReoccurenceInfo(foundName));
                    }
                }
            }
        }

        List<ArtistReoccurenceInfo> artistList = new ArrayList<>(artistReoccurences);
        Collections.sort(artistList);

        List<ArtistReoccurenceInfo> savedArtists = new ArrayList<>();
        int savedNum = 0;

        while (max-1 < artistList.size()) {
            ArtistReoccurenceInfo artist = artistList.get(0);
            if (artist.reoccurences == savedNum) {
                savedArtists.add(artist);
            } else {
                savedArtists.clear();
                savedNum = artist.reoccurences;
                savedArtists.add(artist);
            }
            artistList.remove(artist);
        }

        for (ArtistReoccurenceInfo artist : savedArtists) {
            artistList.add(0, artist);
        }

        Collections.reverse(artistList);
        /*
            //reverse list            
            for (int i = 0; i < max; i++) {
                ArtistInfo keep = artistList.remove(max - (i + 1));
                artistList.add(keep);
            }
         */
        return artistList;
    }

    /**
     * Creates a list of string to represent the artists who have been on the
     * playlist for the longest time straight
     *
     * @return artists in ascending order
     */
    private List<ArtistReoccurenceBreakInfo> getLongestBreakReoccurences(int max) {

        // one list that holds class called artistInfo
        HashSet<ArtistReoccurenceBreakInfo> artistReoccurenceBreaks = new HashSet<>();

        // first line of data is date created information so start at line 1
        for (int i = 1; i < artistsOfTheMonth.size(); i++) {
            String line = artistsOfTheMonth.get(i);
            if (line.equals("space")) { //month reset
                artistReoccurenceBreaks.stream().forEach((artist) -> {
                    if (artist.isOnPlayist) {
                        artist.curBreakStop();
                    } else if (!artist.isOnPlayist) {
                        artist.curBreakAddOne();
                    }
                    artist.isOnPlayist = false;
                });
            } else {    // artist line
                String[] names = line.split("&");
                for (String foundName : names) {
                    boolean found = false;
                    for (ArtistReoccurenceBreakInfo info : artistReoccurenceBreaks) {
                        if (info.name.toUpperCase().equals(foundName.toUpperCase())) { //seeing if this months artist is in local list
                            info.isOnPlayist = true;
                            found = true;
                        }
                        if (found) {
                            break;
                        }
                    }

                    if (!found) { // adding a new artist to the list
                        artistReoccurenceBreaks.add(new ArtistReoccurenceBreakInfo(foundName));
                    }
                }
            }
        }

        List<ArtistReoccurenceBreakInfo> artistList = new ArrayList<>(artistReoccurenceBreaks);
        Collections.sort(artistList);

        List<ArtistReoccurenceBreakInfo> savedArtists = new ArrayList<>();
        int savedNum = 0;

        while (max-1 < artistList.size()) {
            ArtistReoccurenceBreakInfo artist = artistList.get(0);
            if (artist.topBreak == savedNum) {
                savedArtists.add(artist);
            } else {
                savedArtists.clear();
                savedNum = artist.topBreak;
                savedArtists.add(artist);
            }
            artistList.remove(artist);
        }

        for (ArtistReoccurenceBreakInfo artist : savedArtists) {
            artistList.add(0, artist);
        }

        Collections.reverse(artistList);
        /*
            //reverse list            
            for (int i = 0; i < max; i++) {
                ArtistInfo keep = artistList.remove(max - (i + 1));
                artistList.add(keep);
            }
         */
        return artistList;
    }

    /**
     * Creates a list of string to represent the artists who have been on the
     * playlist for the longest time straight
     *
     * @return artists in ascending order
     */
    private List<ArtistMostTotalMonths> getMostTotalMonths(int max) {

        // one list that holds class called artistInfo
        HashSet<ArtistMostTotalMonths> artistMonthTotal = new HashSet<>();

        // first line of data is date created information so start at line 1
        for (int i = 1; i < artistsOfTheMonth.size(); i++) {
            String line = artistsOfTheMonth.get(i);
            if (line.equals("space") && i != (artistsOfTheMonth.size() - 1)) { //month reset
                // do nothing
            } else {    // artist line
                String[] names = line.split("&");
                for (String foundName : names) {
                    boolean found = false;
                    for (ArtistMostTotalMonths info : artistMonthTotal) {
                        if (info.name.toUpperCase().equals(foundName.toUpperCase())) { //seeing if this months artist is in local list
                            info.AddOneToTotal();
                            found = true;
                        }
                        if (found) {
                            break;
                        }
                    }

                    if (!found) { // adding a new artist to the list
                        artistMonthTotal.add(new ArtistMostTotalMonths(foundName));
                    }
                }
            }
        }

        List<ArtistMostTotalMonths> artistList = new ArrayList<>(artistMonthTotal);
        Collections.sort(artistList);

        List<ArtistMostTotalMonths> savedArtists = new ArrayList<>();
        int savedNum = 0;

        while (max-1 < artistList.size()) {
            ArtistMostTotalMonths artist = artistList.get(0);
            if (artist.total == savedNum) {
                savedArtists.add(artist);
            } else {
                savedArtists.clear();
                savedNum = artist.total;
                savedArtists.add(artist);
            }
            artistList.remove(artist);
        }

        for (ArtistMostTotalMonths artist : savedArtists) {
            artistList.add(0, artist);
        }

        Collections.reverse(artistList);
        /*
            //reverse list            
            for (int i = 0; i < max; i++) {
                ArtistInfo keep = artistList.remove(max - (i + 1));
                artistList.add(keep);
            }
         */
        return artistList;
    }

    /**
     * Creates a list of string to represent the artists who have been on the
     * playlist for the longest time straight
     *
     * @return artists in ascending order
     */
    private List<ArtistAveragePlace> getAveragePosition(int max) {

        // one list that holds class called artistInfo
        HashSet<ArtistAveragePlace> artistAveragePosition = new HashSet<>();

        // first line of data is date created information so start at line 1
        int place = 1;
        for (int i = 1; i < artistsOfTheMonth.size(); i++) {
            String line = artistsOfTheMonth.get(i);
            if (line.equals("space") && i != (artistsOfTheMonth.size() - 1)) { //month reset
                place = 1;
            } else {    // artist line
                String[] names = line.split("&");
                for (String foundName : names) {
                    boolean found = false;
                    for (ArtistAveragePlace info : artistAveragePosition) {
                        if (info.name.toUpperCase().equals(foundName.toUpperCase())) { //seeing if this months artist is in local list
                            info.AddToAverage(place);
                            found = true;
                        }
                        if (found) {
                            break;
                        }
                    }

                    if (!found) { // adding a new artist to the list
                        artistAveragePosition.add(new ArtistAveragePlace(foundName, place));
                    }
                }

                place += names.length;
            }
        }

        List<ArtistAveragePlace> artistList = new ArrayList<>(artistAveragePosition);
        Collections.sort(artistList);

        List<ArtistAveragePlace> savedArtists = new ArrayList<>();
        double savedNum = 0;

        while (max-1 < artistList.size()) {
            ArtistAveragePlace artist = artistList.get(0);
            if (artist.avgPosition == savedNum) {
                savedArtists.add(artist);
            } else {
                savedArtists.clear();
                savedNum = artist.avgPosition;
                savedArtists.add(artist);
            }
            artistList.remove(artist);
        }

        for (ArtistAveragePlace artist : savedArtists) {
            artistList.add(0, artist);
        }

        Collections.reverse(artistList);
        /*
            //reverse list            
            for (int i = 0; i < max; i++) {
                ArtistInfo keep = artistList.remove(max - (i + 1));
                artistList.add(keep);
            }
         */
        return artistList;
    }

    class ArtistStreakInfo implements Comparable {

        public String name;
        public int topStreak = 0;
        public int curStreak;
        public boolean checked;

        public ArtistStreakInfo(String artistName) {
            name = artistName;
            curStreak = 1;
            checked = true;
        }

        public void streakStop() {
            if (curStreak > topStreak) {
                topStreak = curStreak;
            }
            curStreak = 0;
        }

        @Override
        public int compareTo(Object o) {
            ArtistStreakInfo other = (ArtistStreakInfo) o;

            if (this.topStreak < other.topStreak) {
                return -1;
            } else if (this.topStreak > other.topStreak) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public boolean equals(Object o) {
            ArtistStreakInfo otherArtist = (ArtistStreakInfo) o;

            return (this.name.equals(otherArtist.name));
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 67 * hash + Objects.hashCode(this.name);
            return hash;
        }
    }

    class ArtistReoccurenceInfo implements Comparable {

        public String name;
        public int reoccurences = 0;
        public boolean wasOffPlayist;
        public boolean isCurOnPlayist;

        public ArtistReoccurenceInfo(String artistName) {
            name = artistName;
            wasOffPlayist = true;
            isCurOnPlayist = true;
        }

        @Override
        public int compareTo(Object o) {
            ArtistReoccurenceInfo other = (ArtistReoccurenceInfo) o;

            if (this.reoccurences < other.reoccurences) {
                return -1;
            } else if (this.reoccurences > other.reoccurences) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public boolean equals(Object o) {
            ArtistReoccurenceInfo otherArtist = (ArtistReoccurenceInfo) o;

            return (this.name.equals(otherArtist.name));
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 67 * hash + Objects.hashCode(this.name);
            return hash;
        }
    }

    class ArtistReoccurenceBreakInfo implements Comparable {

        public String name;
        public int topBreak = 0;
        public int curBreak = 0;
        public boolean isOnPlayist;

        public ArtistReoccurenceBreakInfo(String artistName) {
            name = artistName;
            isOnPlayist = true;
        }

        public void curBreakAddOne() {
            curBreak++;
        }

        public void curBreakStop() {
            if (curBreak > topBreak) {
                topBreak = curBreak;
            }
            curBreak = 0;
        }

        @Override
        public int compareTo(Object o) {
            ArtistReoccurenceBreakInfo other = (ArtistReoccurenceBreakInfo) o;

            if (this.topBreak < other.topBreak) {
                return -1;
            } else if (this.topBreak > other.topBreak) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public boolean equals(Object o) {
            ArtistReoccurenceBreakInfo otherArtist = (ArtistReoccurenceBreakInfo) o;

            return (this.name.equals(otherArtist.name));
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 67 * hash + Objects.hashCode(this.name);
            return hash;
        }
    }

    class ArtistMostTotalMonths implements Comparable {

        public String name;
        public int total;

        public ArtistMostTotalMonths(String artistName) {
            name = artistName;
            total = 1;
        }

        public void AddOneToTotal() {
            total++;
        }

        @Override
        public int compareTo(Object o) {
            ArtistMostTotalMonths other = (ArtistMostTotalMonths) o;

            if (this.total < other.total) {
                return -1;
            } else if (this.total > other.total) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public boolean equals(Object o) {
            ArtistMostTotalMonths otherArtist = (ArtistMostTotalMonths) o;

            return (this.name.equals(otherArtist.name));
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 67 * hash + Objects.hashCode(this.name);
            return hash;
        }
    }

    class ArtistAveragePlace implements Comparable {

        public String name;
        public double avgPosition;
        public double totalPositions;
        public double monthsNum;
        DecimalFormat df;

        public ArtistAveragePlace(String artistName, int position) {
            name = artistName;
            avgPosition = position;
            totalPositions = position;
            monthsNum = 1;

            df = new DecimalFormat("#.####");
            df.setRoundingMode(RoundingMode.CEILING);
        }

        public void AddToAverage(int position) {
            monthsNum++;
            totalPositions += position;
            avgPosition = Double.valueOf(df.format(totalPositions / monthsNum));
        }

        @Override
        public int compareTo(Object o) {
            ArtistAveragePlace other = (ArtistAveragePlace) o;

            if (this.avgPosition > other.avgPosition) {
                return -1;
            } else if (this.avgPosition < other.avgPosition) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public boolean equals(Object o) {
            ArtistAveragePlace otherArtist = (ArtistAveragePlace) o;

            return (this.name.equals(otherArtist.name));
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 67 * hash + Objects.hashCode(this.name);
            return hash;
        }
    }
}
