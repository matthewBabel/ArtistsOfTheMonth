package playlist.tracker.screen;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import playlist.tracker.AppCenter;
import playlist.tracker.artist.Artist;
import playlist.tracker.component.button.MediumButton;
import playlist.tracker.component.button.SmallButton;
import playlist.tracker.component.label.CustomLabel;
import playlist.tracker.frame.AppFrame;

public class AddScreen extends UpdatingScreen {

    private final Color backgroundColor = AppCenter.MYGREEN;
    private String line;
    private int skip;
    private int placePosition;

    public AddScreen() {
        initScreen();
    }

    @Override
    protected void initScreen() {
        removeAll();
        setBackground(backgroundColor);
        setLayout(new GridBagLayout());
        
        ArrayList<String> strs = new ArrayList<>(); // takes tf input to update artist wins
        ArrayList<String> lines = new ArrayList<>(); //lines to be added to file
        placePosition = 0;
        line = "";
        skip = 1;
        final int TOP = 10; // top 5? top 10? top 20?

        if (!AppCenter.monthAssigned) {
            assignFirstMonth();
        }

        CustomLabel questionLbl = new CustomLabel("Who was " + AppCenter.getPlaceAsString(placePosition + 1) + "?",
                ((int) (AppFrame.frameSize.width * 0.25)), 50, false, true);
        
        JTextField addArtistTf = new JTextField();
        addArtistTf.setFont(questionLbl.getFont());
        addArtistTf.setPreferredSize(questionLbl.getPreferredSize());

        MediumButton andBtn = new MediumButton("And?");
        andBtn.addActionListener((ActionEvent e) -> {
            strs.add(placePosition + addArtistTf.getText());
            line = line.concat(addArtistTf.getText() + "&");
            skip++;
            addArtistTf.setText("");
        });

        MediumButton nextBtn = new MediumButton("Next");
        nextBtn.addActionListener((ActionEvent e) -> {
            strs.add(placePosition + addArtistTf.getText());
            placePosition += skip;
            skip = 1;
            line = line.concat(addArtistTf.getText());
            lines.add(line);
            line = "";

            addArtistTf.setText("");
            if (placePosition >= TOP) {       // leave method  
                placePosition = 0;
                questionLbl.setText("Who was 1st?");

                for (String str : strs) {
                    updateArtistWin(Integer.valueOf(str.substring(0, 1)),
                            str.substring(1, str.length()));
                }

                for (String l : lines) {
                    AppCenter.artistsOfTheMonth.add(l);
                }

                AppCenter.artistsOfTheMonth.add("space");
                AppCenter.artistsOfTheMonth.save();

                
                AppCenter.recordStats();
                
                AppCenter.viewScreen.update();
                AppCenter.allTimeScreen.update();
                AppCenter.graphScreen.update();
                AppCenter.topPerPlaceScreen.update();
                AppCenter.miscStatsScreen.update();
                AppCenter.topPerYearScreen.update();
                AppCenter.orderStatsScreen.update();
                AppCenter.biographyScreen.update();

                AppCenter.viewScreen(AppCenter.Screen.MAINMENU.label);
            } else {
                questionLbl.setText("Who was " + AppCenter.getPlaceAsString(placePosition + 1) + "?");
            }
        });

        SmallButton exitBtn = new SmallButton("Exit");
        exitBtn.addActionListener((ActionEvent e) -> {
            questionLbl.setText("Who was 1st?");
            addArtistTf.setText("");
            placePosition = 0;
            line = "";
            skip = 1;
            strs.clear();
            lines.clear();
            AppCenter.viewScreen(AppCenter.Screen.MAINMENU.label);
        });

        GridBagConstraints a = new GridBagConstraints();
        GridBagConstraints b = new GridBagConstraints();
        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints d = new GridBagConstraints();
        GridBagConstraints e = new GridBagConstraints();

        a.gridx = 0;
        a.gridy = 0;
        a.gridwidth = 2;
        a.weightx = .5;
        a.weighty = .5;
        a.anchor = GridBagConstraints.PAGE_END;
        add(questionLbl, a);

        b.gridx = 0;
        b.gridy = 1;
        b.gridwidth = 2;
        b.weightx = .5;
        b.weighty = .5;
        add(addArtistTf, b);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = .5;
        c.weighty = .5;
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(0, 150, 0, 0);
        add(andBtn, c);

        d.gridx = 1;
        d.gridy = 2;
        d.weightx = .5;
        d.weighty = .5;
        d.anchor = GridBagConstraints.PAGE_START;
        d.insets = new Insets(0, 0, 0, 150);
        add(nextBtn, d);

        e.gridx = 2;
        e.gridy = 3;
        e.weightx = .5;
        e.weighty = .5;
        add(exitBtn, e);
        
        
        addToComponents(new JComponent[]{questionLbl, andBtn, nextBtn, exitBtn});
        revalidate();
    }

    /**
     * Gets user input for what month the monthly artists should start at
     */
    private void assignFirstMonth() {
        String firstLine = "";
        String firstMonth = (String) JOptionPane.showInputDialog(
                this,
                "What month should the artists of the month start for? "
        );

        while (AppCenter.startMonth == -1) {
            for (int i = 0; i < AppCenter.MONTHS.length; i++) {

                if (firstMonth.toUpperCase().equals(AppCenter.MONTHS[i].toUpperCase())) {
                    AppCenter.startMonth = i;
                    firstLine = firstLine.concat(String.valueOf(AppCenter.startMonth));
                }
            }

            if (AppCenter.startMonth == -1) {
                firstMonth = (String) JOptionPane.showInputDialog(
                        this,
                        "Hmm not so sure that's a month, could you try again?"
                );
            }
        }

        int firstYear = -1;
        try {
            firstYear = Integer.parseInt(JOptionPane.showInputDialog(
                    this,
                    "Ok good and what year is this month in?"
            ));
        } catch (NumberFormatException e) {
            boolean good = false;
            while (!good) {
                good = true;
                try {
                    firstYear = Integer.parseInt(JOptionPane.showInputDialog(
                            this,
                            "That's not a valid year, could you try again?"
                    ));
                } catch (NumberFormatException f) {
                    good = false;
                }
            }
        }

        AppCenter.year = "" + firstYear;
        AppCenter.monthAssigned = true;
        firstLine = firstLine.concat(" " + firstYear);
        AppCenter.artistsOfTheMonth.add(firstLine);
        AppCenter.artistsOfTheMonth.add("space");
    }

    /**
     * updates the Artist class by either adding a new artist or updating a
     * current one
     *
     * @param tf - JTextField to get artist name from
     */
    private void updateArtistWin(int n, String str) {
        boolean newArtist = true;
        for (Artist artist : AppCenter.artistHandler.getArtistsList()) {
            if (artist.getName().toUpperCase().equals(
                    str.toUpperCase())) {
                artist.addAOTM(n);
                newArtist = false;
            }
        }

        if (newArtist) {
            AppCenter.artistHandler.getArtistsList().add(new Artist(str, true));
            AppCenter.artistHandler.getArtistsList().get(
                    AppCenter.artistHandler.getArtistsList().size() - 1).addAOTM(n);
        }
    }
}
