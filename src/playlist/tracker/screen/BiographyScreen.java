package playlist.tracker.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import playlist.tracker.AppCenter;
import static playlist.tracker.AppCenter.viewScreen;
import playlist.tracker.artist.Artist;
import static playlist.tracker.AppCenter.MYGREEN;
import playlist.tracker.component.button.SmallButton;
import playlist.tracker.component.button.SmallToggleButton;
import playlist.tracker.component.label.LargeLabel;
import playlist.tracker.component.label.MediumSkinnyLabel;
import playlist.tracker.font.FontHandler;

public class BiographyScreen extends UpdatingScreen {

    JPanel selectionPanel;
    JPanel bioPanel;
    JPanel outerSplitPanel;
    JScrollPane artistScrollPane;
    Artist prevArtist = null;

    SmallButton backBtn;
    SmallToggleButton lexicalOrderBtn;
    SmallToggleButton alltimeOrderBtn;
    SmallToggleButton debutPlaceBtn;
    SmallToggleButton avgPlacementBtn;
    SmallToggleButton reverseOrderBtn;
    SmallToggleButton debutOrderBtn;
    SmallToggleButton lastAppearanceOrderBtn;
    SmallToggleButton debutTimeOnOrderBtn;
    SmallToggleButton totalTimeOnOrderBtn;
    SmallToggleButton bestYearOrderBtn;

    public enum SortType {
        LETTER("letter"),
        ALLTIME("alltime"),
        DEBUTPLACE("debutplace"),
        AVGPLACE("avgplace"),
        REVERSE("reverse"),
        DEBUTORDER("debutorder"),
        LASTAPPEARANCE("lastappearance"),
        DEBUTTIMEON("debuttimeon"),
        TIMEON("totaltimeon"),
        BESTYEAR("bestyear");

        private final String METHODTYPE;

        private SortType(String methodType) {
            this.METHODTYPE = methodType;
        }

        public void sort() {
            switch (METHODTYPE) {
                case "letter":
                    AppCenter.artistHandler.sortArtistListByLetter();
                    break;
                case "alltime":
                    AppCenter.artistHandler.sortArtistListByScore();
                    break;
                case "debutplace":
                    AppCenter.artistHandler.sortArtistListByPlaceSinceDebut();
                    break;
                case "avgplace":
                    AppCenter.artistHandler.sortArtistListByAvgPlacement();
                    break;
                case "reverse":
                    AppCenter.artistHandler.reverseArtistList();
                    break;
                case "debutorder":
                    AppCenter.artistHandler.sortArtistListByDebutTime();
                    break;
                case "lastappearance":
                    AppCenter.artistHandler.sortArtistListByLastAppearance();
                    break;
                case "debuttimeon":
                    AppCenter.artistHandler.sortArtistListByDebutTimeOn();
                    break;
                case "totaltimeon":
                    AppCenter.artistHandler.sortArtistListByTotalTimeOn();
                    break;
                case "bestyear":
                    AppCenter.artistHandler.sortArtistListByBestYear();
                    break;

            }
        }
    }

    public BiographyScreen() {
        outerSplitPanel = new JPanel();
        bioPanel = new JPanel();
        bioPanel.setBackground(MYGREEN);
        bioPanel.setLayout(new GridBagLayout());
        initButtons();
        updateArtistList();
    }

    class SortButtonListener implements ActionListener {

        JToggleButton btn;
        SortType sort;

        public SortButtonListener(JToggleButton btn, SortType sort) {
            this.btn = btn;
            this.sort = sort;
        }

        public SortButtonListener(JToggleButton btn, SortType sort, boolean selected) {
            this.btn = btn;
            this.sort = sort;

            if (selected) {
                this.btn.setSelected(true);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (btn.isSelected()) {
                sort.sort();
                updateArtistList();
                unSelectButtonsExcept(btn);
            } else {
                btn.setSelected(true);
            }
        }
    }

    class ReverseButtonListener implements ActionListener {

        SortType sort;

        public ReverseButtonListener(SortType sort) {
            this.sort = sort;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            sort.sort();
            updateArtistList();
        }
    }

    private void initButtons() {
        backBtn = new SmallButton("Back");
        backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            viewScreen(AppCenter.Screen.MAINMENU.label);
        });

        lexicalOrderBtn = new SmallToggleButton("ABC");
        lexicalOrderBtn.addActionListener(new SortButtonListener(lexicalOrderBtn, SortType.LETTER, true));

        alltimeOrderBtn = new SmallToggleButton("Place Alltime");
        alltimeOrderBtn.addActionListener(new SortButtonListener(alltimeOrderBtn, SortType.ALLTIME));

        debutPlaceBtn = new SmallToggleButton("Place Since Debut");
        debutPlaceBtn.addActionListener(new SortButtonListener(debutPlaceBtn, SortType.DEBUTPLACE));

        avgPlacementBtn = new SmallToggleButton("AVG Place");
        avgPlacementBtn.addActionListener(new SortButtonListener(avgPlacementBtn, SortType.AVGPLACE));

        reverseOrderBtn = new SmallToggleButton("Reverse");
        reverseOrderBtn.addActionListener(new ReverseButtonListener(SortType.REVERSE));

        debutOrderBtn = new SmallToggleButton("Debut");
        debutOrderBtn.addActionListener(new SortButtonListener(debutOrderBtn, SortType.DEBUTORDER));

        lastAppearanceOrderBtn = new SmallToggleButton("Last Appearance");
        lastAppearanceOrderBtn.addActionListener(new SortButtonListener(lastAppearanceOrderBtn, SortType.LASTAPPEARANCE));

        debutTimeOnOrderBtn = new SmallToggleButton("% On Since Debut");
        debutTimeOnOrderBtn.addActionListener(new SortButtonListener(debutTimeOnOrderBtn, SortType.DEBUTTIMEON));

        totalTimeOnOrderBtn = new SmallToggleButton("% On Total");
        totalTimeOnOrderBtn.addActionListener(new SortButtonListener(totalTimeOnOrderBtn, SortType.TIMEON));

        bestYearOrderBtn = new SmallToggleButton("Best Year");
        bestYearOrderBtn.addActionListener(new SortButtonListener(bestYearOrderBtn, SortType.BESTYEAR));

    }

    private void unSelectButtonsExcept(JToggleButton exceptBtn) {
        JToggleButton[] btns = new JToggleButton[]{lexicalOrderBtn, alltimeOrderBtn, debutPlaceBtn, avgPlacementBtn, reverseOrderBtn,
            debutOrderBtn, lastAppearanceOrderBtn, debutTimeOnOrderBtn, totalTimeOnOrderBtn, bestYearOrderBtn};

        for (JToggleButton btn : btns) {
            if (!btn.equals(exceptBtn) && btn.isSelected()) {
                btn.setSelected(false);
            }
        }
    }

    @Override
    protected void initScreen() {
        removeAll();
        setLayout(new GridBagLayout());
        setBackground(MYGREEN);

        outerSplitPanel.removeAll();
        outerSplitPanel.setLayout(new GridLayout(1, 2));
        outerSplitPanel.setBackground(MYGREEN);

        selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridBagLayout());
        selectionPanel.setBackground(MYGREEN);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BorderLayout());
        scrollPanel.add(artistScrollPane, BorderLayout.CENTER);
        GridBagConstraints con = new GridBagConstraints();

        con.gridx = 0;
        con.gridy = 0;
        con.gridwidth = 6;
        con.weightx = 1;
        con.weighty = .99;
        con.fill = GridBagConstraints.BOTH;
        selectionPanel.add(scrollPanel, con);

        con.gridx = 1;
        con.gridy = 1;
        con.gridwidth = 1;
        con.weightx = .2;
        con.weighty = .01;
        con.fill = GridBagConstraints.NONE;
        con.anchor = GridBagConstraints.WEST;
        selectionPanel.add(lexicalOrderBtn, con);

        con.gridx = 2;
        con.anchor = GridBagConstraints.WEST;
        selectionPanel.add(alltimeOrderBtn, con);

        con.gridx = 3;
        con.anchor = GridBagConstraints.WEST;
        selectionPanel.add(avgPlacementBtn, con);

        con.gridx = 4;
        con.anchor = GridBagConstraints.WEST;
        selectionPanel.add(bestYearOrderBtn, con);

        con.gridx = 5;
        con.anchor = GridBagConstraints.WEST;
        selectionPanel.add(reverseOrderBtn, con);

        con.gridx = 1;
        con.gridy = 2;
        con.anchor = GridBagConstraints.WEST;
        selectionPanel.add(debutOrderBtn, con);

        con.gridx = 2;
        con.anchor = GridBagConstraints.WEST;
        selectionPanel.add(lastAppearanceOrderBtn, con);

        con.gridx = 3;
        con.anchor = GridBagConstraints.WEST;
        selectionPanel.add(debutPlaceBtn, con);

        con.gridx = 4;
        con.anchor = GridBagConstraints.WEST;
        selectionPanel.add(debutTimeOnOrderBtn, con);

        con.gridx = 5;
        con.anchor = GridBagConstraints.WEST;
        selectionPanel.add(totalTimeOnOrderBtn, con);

        //two panels split left and right are added to top level panel
        outerSplitPanel.add(selectionPanel);
        outerSplitPanel.add(bioPanel);

        addToComponents(new JComponent[]{backBtn, lexicalOrderBtn, alltimeOrderBtn, avgPlacementBtn,
            bestYearOrderBtn, reverseOrderBtn, debutOrderBtn, lastAppearanceOrderBtn, debutPlaceBtn, debutTimeOnOrderBtn, totalTimeOnOrderBtn});

        if (prevArtist != null) {
            updateBio(prevArtist);
        }

        AppCenter.setUpBottomBackBtn(this, outerSplitPanel, backBtn);

        outerSplitPanel.revalidate();
    }

    private void updateArtistList() {

        //turn array of artists into array of strings
        int selectedIndex = 0;
        int count = 0;

        ArrayList<String> artistsList = new ArrayList<>();

        for (Artist artist : AppCenter.artistHandler.getArtistsList()) {
            artistsList.add(artist.getName());
            if (artist.equals(prevArtist)) {
                selectedIndex = count;
            }

            count++;
        }

        // scrollable artists list
        final JList<String> artistJList
                = new JList<>(artistsList.toArray(new String[artistsList.size()]));

        artistScrollPane = new JScrollPane();
        artistScrollPane.setViewportView(artistJList);
        artistScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        artistScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

        artistJList.setLayoutOrientation(JList.VERTICAL);
        ListCellRenderer renderer = new customCellRenderer();
        artistJList.setCellRenderer(renderer);

        if (prevArtist != null) {
            artistJList.setSelectedIndex(selectedIndex);
        }

        //list selection listener
        artistJList.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                prevArtist = AppCenter.artistHandler.getArtist(artistJList.getSelectedValue());
                updateBio(prevArtist);
                update();
            }
        });

        update();
    }

    public void updateBio(Artist artist) {
        bioPanel.removeAll();
        bioPanel.revalidate();
        bioPanel.repaint();

        LargeLabel artistLabel = new LargeLabel(artist.getName(), false, true);

        MediumSkinnyLabel alltimePlaceLbl = new MediumSkinnyLabel("Alltime place: " + AppCenter.artistHandler.getPlaceAlltime(artist));
        alltimePlaceLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel totalScoreLbl = new MediumSkinnyLabel("Total Score: " + artist.getTotalScore());
        totalScoreLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel totalTimeLbl = new MediumSkinnyLabel("Total Time: " + artist.getTotalTime());
        totalTimeLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel bestYearLbl = new MediumSkinnyLabel("Best Year: " + artist.getBestYear());
        bestYearLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel debutLbl = new MediumSkinnyLabel("Debut: " + artist.getDebut());
        debutLbl.setHorizontalAlignment(SwingConstants.RIGHT);

        MediumSkinnyLabel lastAppearanceLbl = new MediumSkinnyLabel("Last Seen: " + artist.getLastAppearance());
        lastAppearanceLbl.setHorizontalAlignment(SwingConstants.RIGHT);

        MediumSkinnyLabel placeDebutLbl = new MediumSkinnyLabel("Place Since Debut: " + AppCenter.artistHandler.getPlaceSinceDebut(artist));
        placeDebutLbl.setHorizontalAlignment(SwingConstants.RIGHT);

        MediumSkinnyLabel percentageDebutLbl = new MediumSkinnyLabel("% Time On Since Debut: " + artist.getPercentageDebutTimeOn());
        percentageDebutLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        
        MediumSkinnyLabel percentageTotalLbl = new MediumSkinnyLabel("% Time On Total: " + artist.getPercentageTotalTimeOn());
        percentageTotalLbl.setHorizontalAlignment(SwingConstants.RIGHT);

        MediumSkinnyLabel highestPlacementLbl = new MediumSkinnyLabel("Highest Placement: " + artist.getHighestPlacement());
        highestPlacementLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel lowestPlacementLbl = new MediumSkinnyLabel("Lowest Placement: " + artist.getLowestPlacement());
        lowestPlacementLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel avgPlacementLbl = new MediumSkinnyLabel("Average Placement: " + artist.getAvgPlacement());
        avgPlacementLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel mostPlacedLbl = new MediumSkinnyLabel("Most Placed: " + artist.getMostPlaced());
        mostPlacedLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel longestStreakLbl = new MediumSkinnyLabel("Longest Streak: " + AppCenter.readableMonthsString(artist.getLongestStreak()));
        longestStreakLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel shortestStreakLbl = new MediumSkinnyLabel("Shortest Streak: " + AppCenter.readableMonthsString(artist.getShortestStreak()));
        shortestStreakLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel avgStreakLbl = new MediumSkinnyLabel("Average Streak: " + artist.getAvgStreak());
        avgStreakLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel appearancesLbl = new MediumSkinnyLabel("Appearances: " + artist.getAppearances());
        appearancesLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel mostTimeAppearanceLbl = new MediumSkinnyLabel("Most Time Between Appearances: " + AppCenter.readableMonthsString(artist.getMostTimeBetweenAppearances()));
        mostTimeAppearanceLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel shortTimeAppearanceLbl = new MediumSkinnyLabel("Shortest Time Between Appearances: " + AppCenter.readableMonthsString(artist.getShortTimeBetweenAppearances()));
        shortTimeAppearanceLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel avgTimeAppearanceLbl = new MediumSkinnyLabel("Average Time Between Appearances: " + artist.getAvgtimeBetweenAppearances());
        avgTimeAppearanceLbl.setHorizontalAlignment(SwingConstants.LEFT);

        MediumSkinnyLabel[] blankLbls = new MediumSkinnyLabel[3];
        for (int i = 0; i < 3; i++) {
            blankLbls[i] = new MediumSkinnyLabel();
        }

        GridBagConstraints con = new GridBagConstraints();

        con.gridx = 0;
        con.gridy = 0;
        con.gridwidth = 2;
        con.weightx = 1;
        con.weighty = 0.1;
        bioPanel.add(artistLabel, con);

        con.gridx = 0;
        con.gridy = 1;
        con.gridwidth = 1;
        con.weightx = 0.5;
        con.weighty = 0.5;
        con.anchor = GridBagConstraints.WEST;
        con.insets = new Insets(0, 10, 0, 0);
        bioPanel.add(alltimePlaceLbl, con);

        con.gridy = 2;
        bioPanel.add(totalScoreLbl, con);

        con.gridy = 3;
        bioPanel.add(totalTimeLbl, con);

        con.gridy = 4;
        bioPanel.add(bestYearLbl, con);

        con.gridx = 1;
        con.gridy = 1;
        con.anchor = GridBagConstraints.EAST;
        con.insets = new Insets(0, 0, 0, 10);
        bioPanel.add(debutLbl, con);

        con.gridy = 2;
        bioPanel.add(lastAppearanceLbl, con);

        con.gridy = 3;
        bioPanel.add(placeDebutLbl, con);

        con.gridy = 4;
        bioPanel.add(percentageDebutLbl, con);

        con.gridy = 5;
        bioPanel.add(percentageTotalLbl, con);
        
        con.gridx = 0;
        con.gridy = 6;
        con.gridwidth = 2;      
        bioPanel.add(blankLbls[0], con);

        con.gridy = 6;
        con.gridwidth = 1;
        con.anchor = GridBagConstraints.WEST;
        con.insets = new Insets(0, 10, 0, 0);
        bioPanel.add(highestPlacementLbl, con);

        con.gridy = 7;
        con.gridwidth = 2;
        bioPanel.add(lowestPlacementLbl, con);

        con.gridy = 8;
        bioPanel.add(avgPlacementLbl, con);

        con.gridy = 9;
        bioPanel.add(mostPlacedLbl, con);

        con.gridy = 10;
        bioPanel.add(blankLbls[1], con);

        con.gridy = 11;
        bioPanel.add(longestStreakLbl, con);

        con.gridy = 12;
        bioPanel.add(shortestStreakLbl, con);

        con.gridy = 13;
        bioPanel.add(avgStreakLbl, con);

        con.gridy = 14;
        bioPanel.add(appearancesLbl, con);

        con.gridy = 15;
        bioPanel.add(blankLbls[2], con);

        con.gridy = 16;
        bioPanel.add(mostTimeAppearanceLbl, con);

        con.gridy = 17;
        bioPanel.add(shortTimeAppearanceLbl, con);

        con.gridy = 18;
        bioPanel.add(avgTimeAppearanceLbl, con);

        addToComponents(new JComponent[]{artistLabel, alltimePlaceLbl, totalScoreLbl, totalTimeLbl, bestYearLbl, debutLbl, lastAppearanceLbl, placeDebutLbl,
            percentageDebutLbl, highestPlacementLbl, lowestPlacementLbl, avgPlacementLbl, mostPlacedLbl, longestStreakLbl, shortestStreakLbl,
            avgStreakLbl, appearancesLbl, mostTimeAppearanceLbl, shortTimeAppearanceLbl, avgTimeAppearanceLbl});
    }

    class customCellRenderer implements ListCellRenderer {

        Border noFocusBorder = new LineBorder(Color.black, 1);

        Border focusBorder = new LineBorder(Color.black, 4);

        DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            renderer.setBorder(isSelected ? focusBorder : noFocusBorder);
            renderer.setFont(FontHandler.plainFont);
            renderer.setHorizontalAlignment(SwingConstants.CENTER);
            return renderer;
        }
    }
}
