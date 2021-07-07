/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist.tracker.screen;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import playlist.tracker.AppCenter;
import playlist.tracker.component.button.ExtraLargeButton;
import playlist.tracker.component.button.SmallButton;
import playlist.tracker.component.label.MediumSkinnyLabel;
import playlist.tracker.font.FontHandler;

/**
 *
 * @author Matt
 */
/**
 * Creates the opening screen
 */
public class OpeningScreen extends UpdatingScreen {

    private final Color backgroundColor = AppCenter.MYGREEN;
    private JButton aotmButton;
    private JButton exitButton;

    public OpeningScreen() {
        initScreen();
    }

    @Override
    protected void initScreen() {
        removeAll();
        setBackground(backgroundColor);
        setLayout(new GridBagLayout());

        aotmButton = new ExtraLargeButton("Artists of the Month");
        aotmButton.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen(AppCenter.Screen.MAINMENU.label);
        });

        exitButton = new SmallButton("Exit");
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

        addToComponents(new JComponent[]{aotmButton, exitButton});

        revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setFontMetrics(g);
    }

    private void setFontMetrics(Graphics g) {
        MediumSkinnyLabel dummyLbl = new MediumSkinnyLabel("Xdfasdfdsafdsf");
        FontHandler.scaleFont(dummyLbl, dummyLbl.getBounds(), g);
        FontMetrics metrics = g.getFontMetrics(dummyLbl.getFont());
        AppCenter.fontCharWidth = metrics.charWidth('X');
    }
}
