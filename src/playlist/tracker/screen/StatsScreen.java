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
import playlist.tracker.AppCenter;
import static playlist.tracker.AppCenter.viewScreen;
import static playlist.tracker.AppCenter.MYGREEN;
import playlist.tracker.component.button.LargeButton;
import playlist.tracker.component.button.SmallButton;

public class StatsScreen extends UpdatingScreen {

    private final Color backgroundColor = MYGREEN;
    private JButton perPlaceBtn;
    private JButton miscBtn;
    private JButton perYearBtn;
    private JButton orderBtn;
    private JButton backBtn;

    public StatsScreen() {
        initScreen();
    }

    protected void initScreen() {
        removeAll();
        setBackground(backgroundColor);
        setLayout(new GridBagLayout());

        perPlaceBtn = new LargeButton("Per Place");
        perPlaceBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen(AppCenter.Screen.PERPLACE.label);
        });

        miscBtn = new LargeButton("Misc Stats");
        miscBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen(AppCenter.Screen.MISC.label);
        });

        perYearBtn = new LargeButton("Per Year");
        perYearBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen(AppCenter.Screen.PERYEAR.label);
        });

        orderBtn = new LargeButton("In Order Stats");
        orderBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen(AppCenter.Screen.INORDER.label);
        });

        backBtn = new SmallButton("Back");
        backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen(AppCenter.Screen.MAINMENU.label);
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
        
        addToComponents(new JComponent[]{perPlaceBtn, perYearBtn, miscBtn, orderBtn, backBtn});
        
        revalidate();
    }
}
