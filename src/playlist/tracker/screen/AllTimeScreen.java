package playlist.tracker.screen;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import playlist.tracker.AppCenter;
import static playlist.tracker.AppCenter.getPlaceAsString;
import static playlist.tracker.AppCenter.setUpScrollPane;
import static playlist.tracker.AppCenter.viewScreen;
import playlist.tracker.artist.ArtistScore;
import static playlist.tracker.AppCenter.MYGREEN;
import playlist.tracker.component.button.SmallButton;
import playlist.tracker.component.label.ExtraLargeLabel;
import playlist.tracker.component.label.LargeLabel;
import playlist.tracker.component.label.MediumLabel;

/**
 *
 * @author Matt
 */
public class AllTimeScreen extends UpdatingScreen {

    private final JPanel innerScrollScreen = new JPanel();
    private final JPanel scrollContainer = new JPanel();
    private final Color backgroundColor = MYGREEN;
    private JLabel titleLbl;
    private SmallButton backBtn;

    public AllTimeScreen() {
        setUpScrollPane(scrollContainer, innerScrollScreen);
        initScreen();
    }

    @Override
    protected void initScreen() {
        removeAll();
        setLayout(new GridBagLayout());
        setBackground(backgroundColor);

        innerScrollScreen.removeAll();
        innerScrollScreen.setBackground(backgroundColor);
        innerScrollScreen.setLayout(new GridBagLayout());

        //top artists of all time
        List<ArtistScore> allTime = AppCenter.artistHandler.getAllTimeScoresList();

        titleLbl = new ExtraLargeLabel("All Time Artists", true);

        backBtn = new SmallButton("Back");
        backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen(AppCenter.Screen.MAINMENU.label);
        });

        GridBagConstraints titleCon = new GridBagConstraints();
        GridBagConstraints labelCon = new GridBagConstraints();

        titleCon.gridx = 0;
        titleCon.gridy = 0;
        titleCon.gridwidth = 3;
        titleCon.weightx = 1;
        titleCon.weighty = .4;
        innerScrollScreen.add(titleLbl, titleCon);

        labelCon.gridx = 0;
        labelCon.gridy = 1;
        labelCon.weightx = .33;
        labelCon.weighty = .3;

        List<JLabel> allTimeLabels = new ArrayList<>();
        int samePlace = 0;
        for (int i = 0; i < allTime.size(); i++) {
            MediumLabel placeLbl = new MediumLabel("", true);
            LargeLabel artistLbl = new LargeLabel("", true);
            MediumLabel scoreLbl = new MediumLabel("", true);

            if (i != 0 && allTime.get(i).score == allTime.get(i - 1).score) {
                samePlace++;
            } else {
                samePlace = 0;
            }

            placeLbl.setText(getPlaceAsString((i + 1) - samePlace) + ".");
            artistLbl.setText(allTime.get(i).name);
            scoreLbl.setText("Score - " + (int) allTime.get(i).score);

            allTimeLabels.add(placeLbl);
            allTimeLabels.add(artistLbl);
            allTimeLabels.add(scoreLbl);

            labelCon.gridx = 0;
            labelCon.anchor = GridBagConstraints.LINE_END;
            innerScrollScreen.add(placeLbl, labelCon);

            labelCon.gridx = 1;
            labelCon.anchor = GridBagConstraints.LINE_START;
            innerScrollScreen.add(artistLbl, labelCon);

            labelCon.gridx = 2;
            labelCon.anchor = GridBagConstraints.LINE_END;
            innerScrollScreen.add(scoreLbl, labelCon);

            labelCon.gridy++;
        }

        AppCenter.setUpBottomBackBtn(this, scrollContainer, backBtn);

        components.add(titleLbl);
        components.add(backBtn);
        components.addAll(allTimeLabels);

        revalidate();
    }

}
