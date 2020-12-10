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
import javax.swing.JButton;
import javax.swing.JPanel;
import playlist.tracker.AppCenter;

/**
 *
 * @author Matt
 */
/**
 * Creates the opening screen
 */
public class OpeningScreen extends JPanel {

    private final Color backgroundColor = AppCenter.myGreenColor;

    public OpeningScreen() {
        setBackground(backgroundColor);
        setLayout(new GridBagLayout());

        initScreen();
    }

    private void initScreen() {

        JButton aotmButton = new JButton("Artists of the Month");
        aotmButton.setFont(AppCenter.plainFont);
        aotmButton.setPreferredSize(new Dimension(400, 200));
        aotmButton.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen("Monthly Screen");
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(AppCenter.plainFont);
        exitButton.setPreferredSize(new Dimension(300, 100));
        exitButton.addActionListener((java.awt.event.ActionEvent e) -> {
            System.exit(0);
        });

        GridBagConstraints a = new GridBagConstraints();
        GridBagConstraints b = new GridBagConstraints();

        a.gridx = 0;
        a.gridy = 0;
        a.weightx = .5;
        a.weighty = .7;

        b.gridx = 0;
        b.gridy = 1;
        b.weightx = .5;
        b.weighty = .3;
        b.anchor = GridBagConstraints.NORTH;

        add(aotmButton, a);
        add(exitButton, b);
    }
}
