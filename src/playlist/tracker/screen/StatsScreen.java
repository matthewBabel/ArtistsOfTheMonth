/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist.tracker.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static playlist.tracker.AppCenter.ARTISTS;
import static playlist.tracker.AppCenter.boldFont;
import static playlist.tracker.AppCenter.getPlaceAsString;
import static playlist.tracker.AppCenter.myGreenColor;
import static playlist.tracker.AppCenter.plainFont;
import static playlist.tracker.AppCenter.setUpScrollPane;
import static playlist.tracker.AppCenter.viewScreen;
import playlist.tracker.Artist;

/**
 *
 * @author Matt
 */
public class StatsScreen extends JPanel {

        private final JPanel allTimeScreen = new JPanel();
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
            List<Artist> allTime = getTopArtists();

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

            GridBagConstraints titleCon = new GridBagConstraints();
            GridBagConstraints labelCon = new GridBagConstraints();
            GridBagConstraints backCon = new GridBagConstraints();

            titleCon.gridx = 0;
            titleCon.gridy = 0;
            titleCon.weightx = .5;
            titleCon.weighty = .5;
            //a.anchor = GridBagConstraints.LINE_START;
            allTimeScreen.add(titleLbl, titleCon);

            labelCon.gridx = 0;
            labelCon.gridy = 1;
            labelCon.weightx = .5;
            labelCon.weighty = .3;

            List<JLabel> allTimeLabels = new ArrayList<>();
            int samePlace = 0;
            for (int i = 0; i < allTime.size(); i++) {
                allTimeLabels.add(new JLabel());
                allTimeLabels.get(i).setPreferredSize(new Dimension(500, 50));
                allTimeLabels.get(i).setFont(plainFont);
                if (i != 0 && allTime.get(i).getTotalScore() == allTime.get(i - 1).getTotalScore()) {
                    samePlace++;
                } else {
                    samePlace = 0;
                }
                allTimeLabels.get(i).setText(getPlaceAsString((i + 1) - samePlace) + ".   " + allTime.get(i).getName()
                        + "        Score - " + allTime.get(i).getTotalScore());
                allTimeScreen.add(allTimeLabels.get(i), labelCon);
                labelCon.gridy++;
            }

            backCon.gridx = 0;
            backCon.gridy = labelCon.gridy;
            backCon.weightx = .5;
            backCon.weighty = .1;
            backCon.anchor = GridBagConstraints.LAST_LINE_END;
            backCon.insets = new Insets(0, 0, 10, 10);
            allTimeScreen.add(backBtn, backCon);
        }

        public void update() {
            initScreen();
        }

        private List<Artist> getTopArtists() {
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

            return topArtists;
        }
    }
