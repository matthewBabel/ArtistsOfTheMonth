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
import javax.swing.JComponent;
import javax.swing.JPanel;
import playlist.tracker.AppCenter;
import static playlist.tracker.font.FontHandler.*;
import static playlist.tracker.AppCenter.setUpScrollPane;
import static playlist.tracker.AppCenter.viewScreen;
import static playlist.tracker.AppCenter.MYGREEN;
import playlist.tracker.component.button.SmallButton;
import playlist.tracker.component.label.ExtraLargeLabel;
import playlist.tracker.component.label.LargeLabel;
import playlist.tracker.component.label.LargeSkinnyLabel;

/**
 *
 * @author Matt
 */
public class MiscStatsScreen extends UpdatingScreen {

    private final JPanel innerScrollScreen = new JPanel();
    private final JPanel scrollContainer = new JPanel();

    public MiscStatsScreen() {
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

        ExtraLargeLabel titleLbl = new ExtraLargeLabel("Misc Stats", true);
        LargeLabel statTracker = new LargeLabel("Longest Streak on Playlist (One Per Artist)", false, true);
        LargeLabel statTracker2 = new LargeLabel("Longest Streak on Playlist", false, true);
        LargeLabel statTracker3 = new LargeLabel("Most Total Time On Playlist", false, true);
        LargeLabel statTracker4 = new LargeLabel("Average Placement On Playlist", false, true);
        LargeLabel statTracker5 = new LargeLabel("Most Appearances On Playlist", false, true);
        LargeLabel statTracker6 = new LargeLabel("Most Time Between Appearances", false, true);

        addToComponents(new JComponent[]{backBtn, titleLbl, statTracker, statTracker2,
            statTracker3, statTracker4, statTracker5, statTracker6});

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
        statTrackerCon.weighty = .1;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        innerScrollScreen.add(statTracker, statTrackerCon);

        labelCon.gridx = 0;
        labelCon.gridy = 2;
        labelCon.weightx = .5;
        labelCon.weighty = .01;
        labelCon.insets = new Insets(0, 30, 0, 0);
        labelCon.anchor = GridBagConstraints.WEST;

        createArtistLabels(labelCon, AppCenter.artistHandler.getTopStreaksOnePer(30));

        statTrackerCon.gridx = 0;
        statTrackerCon.gridy = labelCon.gridy + 2;
        statTrackerCon.weightx = .5;
        statTrackerCon.weighty = .1;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        innerScrollScreen.add(statTracker2, statTrackerCon);
        labelCon.gridy += 2;

        createArtistLabels(labelCon, AppCenter.artistHandler.getTopStreaks(40));

        statTrackerCon.gridx = 0;
        statTrackerCon.gridy = labelCon.gridy + 2;
        statTrackerCon.weightx = .5;
        statTrackerCon.weighty = .1;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        innerScrollScreen.add(statTracker3, statTrackerCon);
        labelCon.gridy += 2;

        createArtistLabels(labelCon, AppCenter.artistHandler.getTopTotalTime(40));

        statTrackerCon.gridx = 0;
        statTrackerCon.gridy = labelCon.gridy + 2;
        statTrackerCon.weightx = .5;
        statTrackerCon.weighty = .1;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        innerScrollScreen.add(statTracker4, statTrackerCon);
        labelCon.gridy += 2;

        createArtistLabels(labelCon, AppCenter.artistHandler.getTopAveragePlacement(30));

        statTrackerCon.gridx = 0;
        statTrackerCon.gridy = labelCon.gridy + 2;
        statTrackerCon.weightx = .5;
        statTrackerCon.weighty = .1;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        innerScrollScreen.add(statTracker5, statTrackerCon);
        labelCon.gridy += 2;

        createArtistLabels(labelCon, AppCenter.artistHandler.getTopAppearances(20));

        statTrackerCon.gridx = 0;
        statTrackerCon.gridy = labelCon.gridy + 2;
        statTrackerCon.weightx = .5;
        statTrackerCon.weighty = .1;
        statTrackerCon.insets = new Insets(20, 30, 0, 0);
        statTrackerCon.anchor = GridBagConstraints.WEST;
        innerScrollScreen.add(statTracker6, statTrackerCon);
        labelCon.gridy += 2;

        createArtistLabels(labelCon, AppCenter.artistHandler.getTopTimeBetweenAppearances(20));

        AppCenter.setUpBottomBackBtn(this, scrollContainer, backBtn);
        revalidate();
    }

    private void createArtistLabels(GridBagConstraints c, String[] titles) {
        for (String info : titles) {
            LargeSkinnyLabel lbl = new LargeSkinnyLabel(info);
            components.add(lbl);
            c.gridy++;
            innerScrollScreen.add(lbl, c);
        }
    }
}
