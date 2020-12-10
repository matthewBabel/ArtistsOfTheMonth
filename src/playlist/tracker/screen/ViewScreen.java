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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import playlist.tracker.AppCenter;

/**
 *
 * @author Matt
 */
public class ViewScreen extends JPanel {

        private final Color backgroundColor = AppCenter.myGreenColor;
        private final JPanel innerScreen = new JPanel();
        private ArrayList<JLabel> labels = new ArrayList<>();

        public ViewScreen() {
            AppCenter.setUpScrollPane(this, innerScreen);
            initScreen();
        }

        private void initScreen() {
            innerScreen.removeAll();
            innerScreen.setBackground(backgroundColor);
            createLabels();

            JButton backBtn = new JButton("Back");
            backBtn.setPreferredSize(new Dimension(120, 70));
            backBtn.setFont(AppCenter.plainFont);
            backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
                AppCenter.viewScreen("Monthly Screen");
            });

            innerScreen.setLayout(new GridBagLayout());
            GridBagConstraints labelCon = new GridBagConstraints();
            GridBagConstraints yearCon = new GridBagConstraints();
            GridBagConstraints backCon = new GridBagConstraints();

            labelCon.gridx = 0;
            labelCon.gridy = 0;
            labelCon.weightx = .6;
            labelCon.weighty = .5;

            yearCon.gridx = 1;
            yearCon.gridy = 0;
            yearCon.weightx = .9;
            yearCon.weighty = .6;
            yearCon.insets = new Insets(0, 50, 10, 0);

            int yMax = 0;
            int startOfRowY = 0;
            boolean newMonth = false;

            //adding frist year label (is a special case)
            yearCon.insets = new Insets(15, 50, 10, 0);
            JLabel firstYearLabel = makeYearLabel(new JLabel());
            firstYearLabel.setText(AppCenter.year);
            innerScreen.add(firstYearLabel, yearCon);
            yearCon.insets = new Insets(0, 50, 10, 0);
            startOfRowY++;
            labelCon.gridy++;

            for (JLabel label : labels) {  // labels output

                //add yearly label before other labels then move next label to be column 0 and one y down
                if (newMonth) {
                    newMonth = false;
                    String labelText = label.getText();
                    if (labelText.contains("January")) {
                        JLabel yearLabel = makeYearLabel(new JLabel());
                        yearCon.gridy = yMax;
                        yearLabel.setText(labelText.substring(labelText.length() - 4));
                        innerScreen.add(yearLabel, yearCon);
                        label.setText("January");
                        labelCon.gridx = 0;
                        labelCon.gridy = yMax + 1;
                    }
                }

                label.setPreferredSize(new Dimension(325, 25));
                innerScreen.add(label, labelCon); //add label to screen

                labelCon.gridy++; //add one to y
                if (label.getText().equals("")) { // end of month

                    newMonth = true;

                    if (labelCon.gridy > yMax) {
                        yMax = labelCon.gridy;
                    }

                    if (labelCon.gridx == 2) {
                        labelCon.gridx = 0;
                        labelCon.gridy = yMax + 1;
                        startOfRowY = yMax + 1;
                    } else {
                        labelCon.gridx++;
                        labelCon.gridy = startOfRowY;
                    }
                }
            }

            if (labelCon.gridy > yMax) {
                yMax = labelCon.gridy;
            }

            backCon.gridx = 3;
            backCon.gridy = yMax;
            backCon.weightx = .4;
            backCon.weighty = .2;
            backCon.insets = new Insets(0, 0, 10, 10);
            innerScreen.add(backBtn, backCon);
        }

        private JLabel makeYearLabel(JLabel yearLabel) {
            yearLabel.setPreferredSize(new Dimension(325, 47));
            yearLabel.setFont(AppCenter.extraBigFont);
            return yearLabel;
        }

        public void update() {
            initScreen();
        }

        /**
         * algorithm for creating labels for viewScreen
         */
        private void createLabels() {
            labels.clear();
            int lines = AppCenter.artistsOfTheMonth.size() - 1;
            int LENGTH_MULTIPLIER = (int) Math.ceil((double) (400 / 13));
            int linePosition = 1;
            int placePosition = 0;
            int skip;
            int monthPosition = AppCenter.startMonth;
            int origYear = Integer.valueOf(AppCenter.year);
            String line;

            innerScreen.setSize(getWidth(), (lines) * LENGTH_MULTIPLIER);

            for (; linePosition < lines; linePosition++) {
                if (AppCenter.artistsOfTheMonth.get(linePosition).equals("space")) {
                    placePosition = 1;
                    if (monthPosition == 12) {
                        AppCenter.year = "" + (Integer.valueOf(AppCenter.year) + 1);
                        monthPosition = 0;
                    }

                    if (linePosition != 1) {
                        labels.add(new JLabel("")); // new line
                    }

                    if (linePosition != lines - 1) {  // if not the last line
                        if (monthPosition == 0) {
                            labels.add(new JLabel(AppCenter.MONTHS[monthPosition] + "-" + AppCenter.year));  //adding yaer to january month
                            labels.get(labels.size() - 1).setFont(AppCenter.mediumBoldFont);
                        } else {
                            labels.add(new JLabel(AppCenter.MONTHS[monthPosition]));
                            labels.get(labels.size() - 1).setFont(AppCenter.mediumBoldFont);
                        }
                        monthPosition++;
                    }
                } else { // if label is for artist
                    line = AppCenter.artistsOfTheMonth.get(linePosition);
                    if (line.contains("&")) {
                        int beforeLength = line.length();
                        line = line.replaceAll("&", " / ");
                        skip = (line.length() - (beforeLength - 2)) / 2;

                        final int MAX_LINE_LENGTH = 29;
                        if (line.length() > MAX_LINE_LENGTH) {
                            labels.add(new JLabel(AppCenter.getPlaceAsString(placePosition) + ". "
                                    + line.substring(0, MAX_LINE_LENGTH) + " -"));
                            labels.get(labels.size() - 1).setFont(AppCenter.plainFont);

                            int multiplier = 1;

                            while (line.length() - MAX_LINE_LENGTH * multiplier >= MAX_LINE_LENGTH) {
                                labels.add(new JLabel("       " + line.substring(
                                        MAX_LINE_LENGTH * multiplier, MAX_LINE_LENGTH * multiplier
                                        + MAX_LINE_LENGTH) + " -"));
                                labels.get(labels.size() - 1).setFont(AppCenter.plainFont);
                                multiplier++;
                            }

                            labels.add(new JLabel("       " + line.substring(
                                    MAX_LINE_LENGTH * multiplier, line.length())));
                            labels.get(labels.size() - 1).setFont(AppCenter.plainFont);
                        } else {

                            labels.add(new JLabel(AppCenter.getPlaceAsString(placePosition) + ". " + line));
                            labels.get(labels.size() - 1).setFont(AppCenter.plainFont);
                        }
                        placePosition += skip;
                    } else {
                        final int MAX_LENGTH = 20;
                        if (line.length() >= MAX_LENGTH) {
                            labels.add(new JLabel(AppCenter.getPlaceAsString(placePosition) + ". "
                                    + line.substring(0, MAX_LENGTH) + " -"));
                            labels.get(labels.size() - 1).setFont(AppCenter.plainFont);

                            int multiplier = 1;

                            while (line.length() - MAX_LENGTH * multiplier >= MAX_LENGTH) {
                                labels.add(new JLabel("       " + line.substring(
                                        MAX_LENGTH * multiplier, MAX_LENGTH * multiplier
                                        + MAX_LENGTH) + " -"));
                                labels.get(labels.size() - 1).setFont(AppCenter.plainFont);
                                multiplier++;
                            }

                            labels.add(new JLabel("       " + line.substring(
                                    MAX_LENGTH * multiplier, line.length())));
                            labels.get(labels.size() - 1).setFont(AppCenter.plainFont);
                        } else {
                            labels.add(new JLabel(AppCenter.getPlaceAsString(placePosition) + ". " + line));
                            labels.get(labels.size() - 1).setFont(AppCenter.plainFont);
                        }
                        placePosition++;
                    }
                }
            }

            AppCenter.year = String.valueOf(origYear);
        }
    }