/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist.tracker.screen;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import playlist.tracker.AppCenter;
import playlist.tracker.component.button.LargeButton;
import playlist.tracker.component.button.MediumButton;
import playlist.tracker.component.button.SmallButton;
import playlist.tracker.component.label.ExtraLargeLabel;

/**
 * Creates the monthly Screen
 *
 * @author Matt
 */
public class MainMenuScreen extends UpdatingScreen {

    private final Color backgroundColor = AppCenter.MYGREEN;

    private JLabel menuLbl;
    private JButton viewBtn;
    private JButton graphBtn;
    private JButton allTimeBtn;
    private JButton bioBtn;
    private JButton statsBtn;
    private JButton addBtn;
    private JButton backBtn;

    public MainMenuScreen() {
        tryGetMonth();
        initScreen();
    }

    @Override
    protected void initScreen() {
        removeAll();
        setBackground(backgroundColor);
        setLayout(new GridBagLayout());

        menuLbl = new ExtraLargeLabel("Music Database Menu", true);
        
        viewBtn = new LargeButton("Text View");
        viewBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen(AppCenter.Screen.VIEW.label);
        });

        graphBtn = new LargeButton("Graph View");
        graphBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen(AppCenter.Screen.GRAPH.label);
        });

        allTimeBtn = new LargeButton("All Time Placement");
        allTimeBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen(AppCenter.Screen.ALLTIME.label);
        });

        bioBtn = new LargeButton("Artist Biography");
        bioBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen(AppCenter.Screen.BIOGRAPHY.label);
        });

        statsBtn = new LargeButton("Stats");
        statsBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen(AppCenter.Screen.STATS.label);
        });

        addBtn = new MediumButton("Add Artists");
        addBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen(AppCenter.Screen.ADD.label);
        });

        backBtn = new SmallButton("Back");
        backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen(AppCenter.Screen.OPENING.label);
        });

        GridBagConstraints con = new GridBagConstraints();

        con.gridx = 0;
        con.gridy = 0;
        con.weightx = .1;
        con.weighty = .5;
        add(menuLbl, con);

        con.gridx = 0;
        con.gridy = 1;
        con.weightx = .7;
        con.weighty = .5;
        add(viewBtn, con);

        con.gridx = 0;
        con.gridy = 2;
        con.weightx = .7;
        con.weighty = .5;
        add(graphBtn, con);

        con.gridx = 0;
        con.gridy = 3;
        con.weightx = .7;
        con.weighty = .5;
        add(allTimeBtn, con);

        con.gridx = 0;
        con.gridy = 4;
        con.weightx = .7;
        con.weighty = .5;
        add(bioBtn, con);

        con.gridx = 0;
        con.gridy = 5;
        con.weightx = .7;
        con.weighty = .5;
        add(statsBtn, con);

        con.gridx = 0;
        con.gridy = 6;
        con.weightx = .1;
        con.weighty = .5;
        con.anchor = GridBagConstraints.PAGE_END;
        con.insets = new Insets(0, 0, 10, 0);
        add(addBtn, con);

        con.gridx = 0;
        con.gridy = 6;
        con.weightx = .2;
        con.weighty = .2;
        con.anchor = GridBagConstraints.LAST_LINE_END;
        con.insets = new Insets(0, 0, 10, 10);
        add(backBtn, con);

        
        addToComponents(new JComponent[]{menuLbl, viewBtn, graphBtn, allTimeBtn,
            bioBtn, statsBtn, addBtn, backBtn});
        revalidate();
    }

    /**
     * try getting month from monthlyArtists file if not there will get in
     * addAOTMScreen
     */
    public static void tryGetMonth() {
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
