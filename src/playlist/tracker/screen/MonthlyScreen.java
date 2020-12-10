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
import javax.swing.JButton;
import javax.swing.JPanel;
import playlist.tracker.AppCenter;

/**
 * Creates the monthly Screen
 *
 * @author Matt
 */
public class MonthlyScreen extends JPanel {

    private final Color backgroundColor = AppCenter.myGreenColor;

    public MonthlyScreen() {
        setBackground(backgroundColor);
        setLayout(new GridBagLayout());
        tryGetMonth();
        initScreen();
    }

    private void initScreen() {
        JButton viewBtn = new JButton("View");
        viewBtn.setPreferredSize(new Dimension(200, 100));
        viewBtn.setFont(AppCenter.plainFont);
        viewBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen("View Screen");
        });

        JButton addBtn = new JButton("Add Artists");
        addBtn.setPreferredSize(new Dimension(200, 100));
        addBtn.setFont(AppCenter.plainFont);
        addBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen("Add Screen");
        });

        JButton statsBtn = new JButton("Stats");
        statsBtn.setPreferredSize(new Dimension(200, 100));
        statsBtn.setFont(AppCenter.plainFont);
        statsBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen("Stats Screen");
        });

        JButton advancedBtn = new JButton("Advanced Stats");
        advancedBtn.setPreferredSize(new Dimension(200, 100));
        advancedBtn.setFont(AppCenter.plainFont);
        advancedBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen("Advanced Stats Screen");
        });

        JButton graphBtn = new JButton("Graph");
        graphBtn.setPreferredSize(new Dimension(200, 100));
        graphBtn.setFont(AppCenter.plainFont);
        graphBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen("Graph Screen");
        });

        JButton backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(120, 70));
        backBtn.setFont(AppCenter.plainFont);
        backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen("Opening Screen");
        });

        GridBagConstraints con = new GridBagConstraints();

        con.gridx = 0;
        con.gridy = 0;
        con.weightx = .5;
        con.weighty = .5;
        add(viewBtn, con);

        con.gridx = 0;
        con.gridy = 1;
        con.weightx = .5;
        con.weighty = .5;
        add(addBtn, con);

        con.gridx = 0;
        con.gridy = 2;
        con.weightx = .5;
        con.weighty = .5;
        add(statsBtn, con);

        con.gridx = 0;
        con.gridy = 3;
        con.weightx = .5;
        con.weighty = .5;
        add(advancedBtn, con);

        con.gridx = 0;
        con.gridy = 4;
        con.weightx = .5;
        con.weighty = .5;
        add(graphBtn, con);

        con.gridx = 0;
        con.gridy = 5;
        con.weightx = .2;
        con.weighty = .2;
        con.anchor = GridBagConstraints.LAST_LINE_END;
        con.insets = new Insets(0, 0, 10, 10);
        add(backBtn, con);
    }

    /**
     * try getting month from monthlyArtists file if not there will get in
     * addAOTMScreen
     */
    private void tryGetMonth() {
        if (!AppCenter.monthAssigned && AppCenter.artistsOfTheMonth.size() != 0) {
            for (int i = 0; i < AppCenter.MONTHS.length; i++) {
                if (Integer.valueOf(AppCenter.artistsOfTheMonth.get(0).substring(0,
                        AppCenter.artistsOfTheMonth.get(0).indexOf(" ")))
                        == i) {
                    AppCenter.startMonth = i;
                    AppCenter.monthAssigned = true;
                }
            }
            AppCenter.year = AppCenter.artistsOfTheMonth.get(0).substring(
                    AppCenter.artistsOfTheMonth.get(0).indexOf(" ") + 1,
                    AppCenter.artistsOfTheMonth.get(0).length());
        }
    }
}
