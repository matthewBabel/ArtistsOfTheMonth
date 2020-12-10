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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static playlist.tracker.AppCenter.ARTISTS;
import static playlist.tracker.AppCenter.MONTHS;
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
import playlist.tracker.Artist;

/**
 *
 * @author Matt
 */
public class OrderStatsScreen extends JPanel {

    private JPanel viewPort;

    public OrderStatsScreen() {
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
        titleLbl.setText("In Order Stats");

        JLabel statTracker = new JLabel();
        statTracker.setPreferredSize(new Dimension(300, 24));
        statTracker.setFont(mediumBoldFont);
        statTracker.setText("Year - " + year);

        GridBagConstraints titleCon = new GridBagConstraints();
        GridBagConstraints yearCon = new GridBagConstraints();
        GridBagConstraints monthCon = new GridBagConstraints();
        GridBagConstraints labelCon = new GridBagConstraints();
        GridBagConstraints backCon = new GridBagConstraints();

        titleCon.gridx = 0;
        titleCon.gridy = 0;
        titleCon.gridwidth = 2;
        titleCon.weightx = 1;
        titleCon.weighty = .1;
        titleCon.anchor = GridBagConstraints.NORTH;
        viewPort.add(titleLbl, titleCon);

        yearCon.gridx = 0;
        yearCon.gridy = 1;
        yearCon.weightx = .5;
        yearCon.weighty = .05;
        yearCon.insets = new Insets(20, 30, 0, 0);
        yearCon.anchor = GridBagConstraints.WEST;
        viewPort.add(statTracker, yearCon);

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

        backCon.gridx = 1;
        backCon.gridy = labelCon.gridy + 1;
        backCon.weightx = .2;
        backCon.weighty = .1;
        backCon.insets = new Insets(0, 0, 10, 10);
        backCon.anchor = GridBagConstraints.LAST_LINE_END;
        viewPort.add(backBtn, backCon);
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

                    JLabel yearLbl = new JLabel();
                    yearLbl.setPreferredSize(new Dimension(300, 24));
                    yearLbl.setFont(mediumBoldFont);
                    yearLbl.setText("Year - " + intYear);

                    yearCon.gridy = labelCon.gridy + 2;
                    labelCon.gridy += 2;
                    viewPort.add(yearLbl, yearCon);
                }

                JLabel monthLbl = new JLabel();
                monthLbl.setPreferredSize(new Dimension(300, 24));
                monthLbl.setFont(plainFont);
                monthLbl.setText(MONTHS[monthNum]+":");
                monthCon.gridy = labelCon.gridy + 1;
                labelCon.gridy += 2;
                viewPort.add(monthLbl, monthCon);
                
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
                        for (Artist a : ARTISTS) {
                            if (a.getName().equals(foundName)) {
                                foundArtist = a;
                                break;
                            }
                        }

                        JLabel lbl = new JLabel(foundArtist.getName() + " - " + foundArtist.getTotalScore());
                        lbl.setSize(400, 5);
                        lbl.setFont(plainFont);
                        labelCon.gridy++;
                        viewPort.add(lbl, labelCon);
                    }
                }
            }
        }
    }

    public void update() {
        initScreen();
    }
}
