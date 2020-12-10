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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static playlist.tracker.AppCenter.ARTISTS;
import static playlist.tracker.AppCenter.boldFont;
import static playlist.tracker.AppCenter.getPlaceAsString;
import static playlist.tracker.AppCenter.mediumBoldFont;
import static playlist.tracker.AppCenter.myGreenColor;
import static playlist.tracker.AppCenter.plainFont;
import static playlist.tracker.AppCenter.setUpScrollPane;
import static playlist.tracker.AppCenter.viewScreen;

/**
 *
 * @author Matt
 */
public class TopPerPlaceScreen extends JPanel {

        private JPanel viewPort;

        public TopPerPlaceScreen() {
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
            titleLbl.setPreferredSize(new Dimension(300, 30));
            titleLbl.setFont(boldFont);
            titleLbl.setText("Top Artists Per Place");

            JLabel statTracker = new JLabel();
            statTracker.setPreferredSize(new Dimension(300, 24));
            statTracker.setFont(mediumBoldFont);
            statTracker.setText("Most Amount of 1st places");

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
            statTrackerCon.insets = new Insets(0, 30, 0, 0);
            statTrackerCon.anchor = GridBagConstraints.WEST;

            labelCon.gridx = 0;
            labelCon.gridy = 2;
            labelCon.weightx = .5;
            labelCon.weighty = .01;
            labelCon.insets = new Insets(0, 30, 0, 0);
            labelCon.anchor = GridBagConstraints.WEST;

            for (int i = 1; i <= 10; i++) {

                JLabel lbl = new JLabel();
                lbl.setPreferredSize(new Dimension(300, 24));
                lbl.setFont(mediumBoldFont);
                lbl.setText("Most Amount of " + getPlaceAsString(i) + " places");

                statTrackerCon.gridx = 0;
                statTrackerCon.gridy = labelCon.gridy + 1;
                statTrackerCon.weightx = .5;
                statTrackerCon.weighty = .05;
                statTrackerCon.insets = new Insets(20, 30, 0, 0);
                statTrackerCon.anchor = GridBagConstraints.WEST;
                viewPort.add(lbl, statTrackerCon);

                labelCon.gridx = 0;
                labelCon.gridy += 2;
                labelCon.weightx = .5;
                labelCon.weighty = .01;
                labelCon.insets = new Insets(0, 30, 0, 0);
                labelCon.anchor = GridBagConstraints.WEST;

                if (i == 1) {
                    createMostLabels(labelCon, getMostOfPlace(100, i));
                } else {
                    createMostLabels(labelCon, getMostOfPlace(10, i));
                }
            }

            backCon.gridx = 1;
            backCon.gridy = labelCon.gridy + 1;
            backCon.weightx = .2;
            backCon.weighty = .1;
            backCon.insets = new Insets(0, 0, 10, 10);
            backCon.anchor = GridBagConstraints.LAST_LINE_END;
            viewPort.add(backBtn, backCon);
        }

        private void createMostLabels(GridBagConstraints c, List<ArtistPlace> titles) {
            int place = 1;
            int samePlace = 0;
            int savedNum = 0;

            for (ArtistPlace info : titles) {
                if (info.count == savedNum) {
                    samePlace++;
                } else {
                    savedNum = info.count;
                    samePlace = 0;
                }

                JLabel lbl = new JLabel(getPlaceAsString(place - samePlace) + ". " + info.name + " - " + info.count);
                lbl.setSize(400, 5);
                lbl.setFont(plainFont);
                c.gridy++;
                place++;
                viewPort.add(lbl, c);
            }
        }

        private List<ArtistPlace> getMostOfPlace(int max, int place) {

            HashSet<ArtistPlace> artistSet = new HashSet<>();

            ARTISTS.stream().map((artist) -> {
                ArtistPlace ap = new ArtistPlace(artist.getName());
                ap.count = artist.getNumberAtPlace(place);
                return ap;
            }).forEachOrdered((ap) -> {
                artistSet.add(ap);
            });

            
            List<ArtistPlace> artistList = new ArrayList<>(artistSet);
            Collections.sort(artistList);

            List<ArtistPlace> savedArtists = new ArrayList<>();
            int savedNum = -1;

            while (max < artistList.size() || artistList.get(0).count == 0) {
                ArtistPlace artist = artistList.get(0);
                             
                if (artist.count == savedNum) {
                    savedArtists.add(artist);
                } else {
                    savedArtists.clear();
                    savedNum = artist.count;
                    savedArtists.add(artist);
                }
                artistList.remove(artist);
            }

            if (savedArtists.isEmpty() || savedArtists.get(0).count == 0
                    || artistList.get(0).count != savedArtists.get(0).count) {
                savedArtists.clear();
            }

            savedArtists.forEach((artist) -> {
                artistList.add(0, artist);
            });

            Collections.reverse(artistList);
            return artistList;
        }

        class ArtistPlace implements Comparable {

            public String name;
            public int count = 1;

            public ArtistPlace(String artistName) {
                name = artistName;
            }

            @Override
            public int compareTo(Object o) {
                ArtistPlace other = (ArtistPlace) o;

                if (this.count < other.count) {
                    return -1;
                } else if (this.count > other.count) {
                    return 1;
                } else {
                    return 0;
                }
            }

            @Override
            public boolean equals(Object o) {
                ArtistPlace otherArtist = (ArtistPlace) o;

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
