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
import static playlist.tracker.AppCenter.myGreenColor;
import static playlist.tracker.AppCenter.plainFont;
import static playlist.tracker.AppCenter.viewScreen;

public class AdvancedStatsScreen extends JPanel {

    private final Color backgroundColor = myGreenColor;

    public AdvancedStatsScreen() {
        setBackground(backgroundColor);
        setLayout(new GridBagLayout());
        initScreen();
    }

    private void initScreen() {
        JButton perPlaceBtn = new JButton("Per Place");
        perPlaceBtn.setPreferredSize(new Dimension(200, 100));
        perPlaceBtn.setFont(plainFont);
        perPlaceBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen("Top Per Place Screen");
        });

        JButton miscBtn = new JButton("Misc Stats");
        miscBtn.setPreferredSize(new Dimension(200, 100));
        miscBtn.setFont(plainFont);
        miscBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen("Misc Screen");
        });

        JButton perYearBtn = new JButton("Per Year");
        perYearBtn.setPreferredSize(new Dimension(200, 100));
        perYearBtn.setFont(plainFont);
        perYearBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen("Top Per Year Screen");
        });

        JButton orderBtn = new JButton("In Order Stats");
        orderBtn.setPreferredSize(new Dimension(200, 100));
        orderBtn.setFont(plainFont);
        orderBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen("Order Stats Screen");
        });

        JButton backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(120, 70));
        backBtn.setFont(plainFont);
        backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen("Monthly Screen");
        });

        GridBagConstraints con = new GridBagConstraints();

        con.gridx = 0;
        con.gridy = 0;
        con.weightx = .5;
        con.weighty = .5;
        add(perYearBtn, con);

        con.gridx = 0;
        con.gridy = 1;
        con.weightx = .5;
        con.weighty = .5;
        add(perPlaceBtn, con);

        con.gridx = 0;
        con.gridy = 2;
        con.weightx = .5;
        con.weighty = .5;
        add(orderBtn, con);

        con.gridx = 0;
        con.gridy = 3;
        con.weightx = .5;
        con.weighty = .5;
        add(miscBtn, con);
            
        
        con.gridx = 0;
        con.gridy = 4;
        con.weightx = .2;
        con.weighty = .2;
        con.anchor = GridBagConstraints.LAST_LINE_END;
        con.insets = new Insets(0, 0, 10, 10);
        add(backBtn, con);
    }

}
