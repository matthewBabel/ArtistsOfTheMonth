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
import static playlist.tracker.AppCenter.getPlaceAsString;
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
public class TopPerPlaceScreen extends UpdatingScreen {

    private final JPanel innerScrollScreen = new JPanel();
    private final JPanel scrollContainer = new JPanel();

    public TopPerPlaceScreen() {
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

        ExtraLargeLabel titleLbl = new ExtraLargeLabel("Top Artists Per Place", true);
        components.add(titleLbl);

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
        statTrackerCon.anchor = GridBagConstraints.WEST;

        labelCon.gridx = 0;
        labelCon.gridy = 2;
        labelCon.weightx = .5;
        labelCon.weighty = .01;
        labelCon.anchor = GridBagConstraints.WEST;

        for (int i = 1; i <= 10; i++) {

            LargeLabel lbl = new LargeLabel("Most Amount of " + getPlaceAsString(i) + " places", false, true);

            statTrackerCon.gridx = 0;
            statTrackerCon.gridy = labelCon.gridy + 1;
            statTrackerCon.weightx = .5;
            statTrackerCon.weighty = .05;
            statTrackerCon.insets = new Insets(20, 30, 0, 0);
            statTrackerCon.anchor = GridBagConstraints.WEST;
            innerScrollScreen.add(lbl, statTrackerCon);
            components.add(lbl);

            labelCon.gridx = 0;
            labelCon.gridy += 2;
            labelCon.weightx = .5;
            labelCon.weighty = .01;
            labelCon.insets = new Insets(0, 30, 0, 0);
            labelCon.anchor = GridBagConstraints.WEST;

            if (i == 1) {
                createMostLabels(labelCon, AppCenter.artistHandler.getTopArtistsPlacement(i, 100));
            } else {
                createMostLabels(labelCon, AppCenter.artistHandler.getTopArtistsPlacement(i, 10));
            }
        }

        AppCenter.setUpBottomBackBtn(this, scrollContainer, backBtn);

        revalidate();
    }

    private void createMostLabels(GridBagConstraints c, String[] titles) {
        for (String info : titles) {
            LargeSkinnyLabel lbl = new LargeSkinnyLabel(info);
            c.gridy++;
            innerScrollScreen.add(lbl, c);
            components.add(lbl);
        }
    }


}
