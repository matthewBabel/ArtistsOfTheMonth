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
import javax.swing.JPanel;
import playlist.tracker.AppCenter;
import static playlist.tracker.font.FontHandler.*;
import static playlist.tracker.AppCenter.setUpScrollPane;
import static playlist.tracker.AppCenter.viewScreen;
import static playlist.tracker.AppCenter.year;
import static playlist.tracker.AppCenter.MYGREEN;
import playlist.tracker.component.button.SmallButton;
import playlist.tracker.component.label.ExtraLargeLabel;
import playlist.tracker.component.label.LargeSkinnyLabel;
import playlist.tracker.component.label.MediumLabel;

/**
 *
 * @author Matt
 */
public class TopPerYearScreen extends UpdatingScreen {

    private JPanel innerScrollScreen = new JPanel();
    private JPanel scrollContainer = new JPanel();

    public TopPerYearScreen() {
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
            viewScreen(AppCenter.Screen.STATS.label);
        });
        components.add(backBtn);

        ExtraLargeLabel titleLbl = new ExtraLargeLabel("Top Artists By Year", true);
        components.add(titleLbl);

        MediumLabel statTracker = new MediumLabel("Year - " + year);
        components.add(statTracker);

        GridBagConstraints titleCon = new GridBagConstraints();
        GridBagConstraints statTrackerCon = new GridBagConstraints();
        GridBagConstraints labelCon = new GridBagConstraints();

        titleCon.gridx = 0;
        titleCon.gridy = 0;
        titleCon.gridwidth = 2;
        titleCon.weightx = 1;
        titleCon.weighty = .1;
        titleCon.anchor = GridBagConstraints.NORTH;
        innerScrollScreen.add(titleLbl, titleCon);

        statTrackerCon.gridx = 0;
        statTrackerCon.gridy = 1;
        statTrackerCon.weightx = .5;
        statTrackerCon.weighty = .05;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        innerScrollScreen.add(statTracker, statTrackerCon);

        labelCon.gridx = 0;
        labelCon.gridy = 2;
        labelCon.weightx = .5;
        labelCon.weighty = .01;
        labelCon.insets = new Insets(0, 30, 0, 0);
        labelCon.anchor = GridBagConstraints.WEST;

        labelLoop(statTrackerCon, labelCon);

        AppCenter.setUpBottomBackBtn(this, scrollContainer, backBtn);

        revalidate();
    }

    private void labelLoop(GridBagConstraints statTrackerCon, GridBagConstraints labelCon) {

        int intYear = Integer.valueOf(year);
        intYear++;

        int totalYears = AppCenter.artistHandler.getArtistsList().get(0).getYearsSize();

        for (int i = 0; i < totalYears; i++) {
            String[] titles = AppCenter.artistHandler.getTopArtistsYear(i, 100);

            for (String title : titles) {
                LargeSkinnyLabel lbl = new LargeSkinnyLabel(title);
                labelCon.gridy++;
                innerScrollScreen.add(lbl, labelCon);
                components.add(lbl);
            }

            if (i != totalYears - 1) {
                MediumLabel statTracker = new MediumLabel("Year - " + intYear);
                statTrackerCon.gridy = labelCon.gridy + 2;
                labelCon.gridy += 2;
                innerScrollScreen.add(statTracker, statTrackerCon);
                intYear++;
                components.add(statTracker);
            }
        }
    }
}
