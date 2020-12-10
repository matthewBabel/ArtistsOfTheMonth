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
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import playlist.tracker.AppCenter;
import playlist.tracker.Artist;

public class AddScreen extends JPanel {

        private final Color backgroundColor = AppCenter.myGreenColor;
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

            if (!AppCenter.monthAssigned) {
                assignFirstMonth();
            }

            JLabel questionLbl = new JLabel("Who was " + AppCenter.getPlaceAsString(placePosition + 1) + "?");
            questionLbl.setFont(AppCenter.plainFont);
            questionLbl.setPreferredSize(new Dimension(200, 50));

            JTextField addArtistTf = new JTextField();
            addArtistTf.setFont(AppCenter.plainFont);
            addArtistTf.setPreferredSize(new Dimension(200, 30));

            JButton andBtn = new JButton("And?");
            andBtn.setPreferredSize(new Dimension(150, 100));
            andBtn.setFont(AppCenter.plainFont);
            andBtn.addActionListener((ActionEvent e) -> {
                strs.add(placePosition + addArtistTf.getText());
                line = line.concat(addArtistTf.getText() + "&");
                skip++;
                addArtistTf.setText("");
            });

            JButton nextBtn = new JButton("Next");
            nextBtn.setFont(AppCenter.plainFont);
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
                        AppCenter.artistsOfTheMonth.add(l);
                    }

                    AppCenter.artistsOfTheMonth.add("space");
                    AppCenter.artistsOfTheMonth.save();
                    AppCenter.viewScreen.update();
                    AppCenter.statsScreen.update();
                    AppCenter.graphScreen.update();
                    AppCenter.topPerPlaceScreen.update();
                    AppCenter.miscStatsScreen.update();
                    AppCenter.topPerYearScreen.update();
                    AppCenter.orderStatsScreen.update();
                    AppCenter.viewScreen("Monthly Screen");
                } else {
                    questionLbl.setText("Who was " + AppCenter.getPlaceAsString(placePosition + 1) + "?");
                }
            });

            JButton exitBtn = new JButton("Exit");
            exitBtn.setPreferredSize(new Dimension(150, 100));
            exitBtn.setFont(AppCenter.plainFont);
            exitBtn.addActionListener((ActionEvent e) -> {
                questionLbl.setText("Who was 1st?");
                addArtistTf.setText("");
                placePosition = 0;
                line = "";
                skip = 1;
                strs.clear();
                lines.clear();
                AppCenter.viewScreen("Monthly Screen");
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

            while (AppCenter.startMonth == -1) {
                for (int i = 0; i < AppCenter.MONTHS.length; i++) {

                    if (firstMonth.toUpperCase().equals(AppCenter.MONTHS[i].toUpperCase())) {
                        AppCenter.startMonth = i;
                        firstLine = firstLine.concat(String.valueOf(AppCenter.startMonth));
                    }
                }

                if (AppCenter.startMonth == -1) {
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

            AppCenter.year = "" + firstYear;
            AppCenter.monthAssigned = true;
            firstLine = firstLine.concat(" " + firstYear);
            AppCenter.artistsOfTheMonth.add(firstLine);
            AppCenter.artistsOfTheMonth.add("space");
        }

        /**
         * updates the Artist class by either adding a new artist or updating a
         * current one
         *
         * @param tf - JTextField to get artist name from
         */
        private void updateArtistWin(int n, String str) {
            boolean newArtist = true;
            for (Artist artist : AppCenter.ARTISTS) {
                if (artist.getName().toUpperCase().equals(
                        str.toUpperCase())) {
                    artist.addAOTM(n);
                    newArtist = false;
                }
            }

            if (newArtist) {
                AppCenter.ARTISTS.add(new Artist(str, true));
                AppCenter.ARTISTS.get(AppCenter.ARTISTS.size() - 1).addAOTM(n);
            }
        }
    }
