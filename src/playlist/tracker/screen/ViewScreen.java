package playlist.tracker.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import playlist.tracker.AppCenter;
import playlist.tracker.component.button.SmallButton;
import playlist.tracker.component.label.MediumLabel;
import playlist.tracker.component.label.MediumSkinnyLabel;
import static playlist.tracker.font.FontHandler.*;
import playlist.tracker.frame.AppFrame;

/**
 *
 * @author Matt
 */
public class ViewScreen extends UpdatingScreen {

    private final Color backgroundColor = AppCenter.MYGREEN;
    private final JPanel innerScrollScreen = new JPanel();
    private final JPanel scrollContainer = new JPanel();
    private final ArrayList<JLabel> labels = new ArrayList<>();
    private final ArrayList<JLabel> yearLabels = new ArrayList<>();
    private SmallButton backBtn;

    public ViewScreen() {
        AppCenter.setUpScrollPane(scrollContainer, innerScrollScreen);
        
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

        backBtn = new SmallButton("Back");
        backBtn.setPreferredSize(new Dimension(120, 70));
        backBtn.setFont(plainFont);
        backBtn.addActionListener((java.awt.event.ActionEvent e) -> {
            AppCenter.viewScreen(AppCenter.Screen.MAINMENU.label);
        });

        GridBagConstraints labelCon = new GridBagConstraints();
        GridBagConstraints yearCon = new GridBagConstraints();

        labelCon.gridx = 0;
        labelCon.gridy = 0;
        labelCon.weightx = .6;
        labelCon.weighty = .5;

        yearCon.gridx = 1;
        yearCon.gridy = 0;
        yearCon.weightx = .9;
        yearCon.weighty = .6;
        yearCon.insets = new Insets(0, 50, 10, 0);

        int yMax = 0;
        int startOfRowY = 0;
        boolean newMonth = false;

        //adding first year label (is a special case)
        yearCon.insets = new Insets(15, 50, 10, 0);
        MediumLabel firstYearLabel = new MediumLabel(AppCenter.year, false, true);
        yearLabels.add(firstYearLabel);
        innerScrollScreen.add(firstYearLabel, yearCon);
        startOfRowY++;
        labelCon.gridy++;

        createLabels();
        for (JLabel label : labels) {  // labels output

            //add yearly label before other labels then move next label to be column 0 and one y down
            if (newMonth) {
                newMonth = false;
                String labelText = label.getText();
                if (labelText.contains("January")) {
                    MediumLabel yearLabel = new MediumLabel("", false, true);
                    yearCon.gridy = yMax;
                    yearLabel.setText(labelText.substring(labelText.length() - 4));
                    yearLabels.add(yearLabel);
                    innerScrollScreen.add(yearLabel, yearCon);
                    label.setText("January");
                    labelCon.gridx = 0;
                    labelCon.gridy = yMax + 1;
                }
            }

            innerScrollScreen.add(label, labelCon); //add label to screen

            labelCon.gridy++; //add one to y
            if (label.getText().equals("")) { // end of month

                newMonth = true;

                if (labelCon.gridy > yMax) {
                    yMax = labelCon.gridy;
                }

                if (labelCon.gridx == 2) {
                    labelCon.gridx = 0;
                    labelCon.gridy = yMax + 1;
                    startOfRowY = yMax + 1;
                } else {
                    labelCon.gridx++;
                    labelCon.gridy = startOfRowY;
                }
            }
        }

        AppCenter.setUpBottomBackBtn(this, scrollContainer, backBtn);

        components.add(backBtn);
        components.addAll(labels);
        components.addAll(yearLabels);

        revalidate();

    }

    /**
     * algorithm for creating labels for viewScreen
     */
    private void createLabels() {
        labels.clear();
        int lines = AppCenter.artistsOfTheMonth.size() - 1;
        int LENGTH_MULTIPLIER = (int) Math.ceil((double) (400 / 13));
        int linePosition = 1;
        int placePosition = 0;
        int skip;
        int monthPosition = AppCenter.startMonth;
        int origYear = Integer.valueOf(AppCenter.year);
        int MAX_LINE_LENGTH = getMaxLineLength();
        String line;

        innerScrollScreen.setSize(getWidth(), (lines) * LENGTH_MULTIPLIER);

        for (; linePosition < lines; linePosition++) {
            if (AppCenter.artistsOfTheMonth.get(linePosition).equals("space")) {
                placePosition = 1;
                if (monthPosition == 12) {
                    AppCenter.year = "" + (Integer.valueOf(AppCenter.year) + 1);
                    monthPosition = 0;
                }

                if (linePosition != 1) {
                    labels.add(new MediumSkinnyLabel("")); // new line
                }

                if (linePosition != lines - 1) {  // if not the last line

                    labels.add(new MediumSkinnyLabel(
                            AppCenter.MONTHS[monthPosition] + ((monthPosition == 0)
                                    ? AppCenter.year : ""),
                            true, true));

                    monthPosition++;
                }
            } else { // if label is for artist
                line = AppCenter.artistsOfTheMonth.get(linePosition);
                if (line.contains("&")) {
                    int beforeLength = line.length();
                    line = line.replaceAll("&", " / ");

                    skip = (line.length() - (beforeLength)) / 2;
                    skip+=1; // add 1 to skip so when skip = 1, placePosition += skip adds 2 to placePosition

                    if (line.length() > MAX_LINE_LENGTH) {

                        boolean endsWithBadChar = isBadChar((line.substring(MAX_LINE_LENGTH - 2,
                                (line.length() <= MAX_LINE_LENGTH+2) ? MAX_LINE_LENGTH+1 : MAX_LINE_LENGTH+2)));
                        labels.add(new MediumSkinnyLabel(AppCenter.getPlaceAsString(placePosition) + ". "
                                + line.substring(0, MAX_LINE_LENGTH) + ((endsWithBadChar) ? "" : " -")));

                        int multiplier = 1;

                        while (line.length() - MAX_LINE_LENGTH * multiplier > MAX_LINE_LENGTH) {
                            
                            endsWithBadChar = isBadChar(line.substring(MAX_LINE_LENGTH * multiplier
                                    + MAX_LINE_LENGTH - 2, (line.length() <= MAX_LINE_LENGTH * multiplier + MAX_LINE_LENGTH+1) ?
                                    MAX_LINE_LENGTH * multiplier + MAX_LINE_LENGTH : 
                                    MAX_LINE_LENGTH * multiplier + MAX_LINE_LENGTH+2));                        

                            labels.add(new MediumSkinnyLabel("       " + line.substring(
                                    MAX_LINE_LENGTH * multiplier, MAX_LINE_LENGTH * multiplier
                                    + MAX_LINE_LENGTH) + ((endsWithBadChar) ? "" : " -"), false, false, true));
                            multiplier++;
                        }

                        labels.add(new MediumSkinnyLabel("       " + line.substring(
                                MAX_LINE_LENGTH * multiplier, line.length()), false, false, true));
                    } else {
                        labels.add(new MediumSkinnyLabel(AppCenter.getPlaceAsString(placePosition) + ". " + line));
                    }
                    placePosition += skip;
                } else {
                    if (line.length() > MAX_LINE_LENGTH) {
                        boolean endsWithBadChar = isBadChar(line.substring(MAX_LINE_LENGTH - 2, 
                                (line.length() <= MAX_LINE_LENGTH+1) ? MAX_LINE_LENGTH : MAX_LINE_LENGTH+2));

                        labels.add(new MediumSkinnyLabel(AppCenter.getPlaceAsString(placePosition) + ". "
                                + line.substring(0, MAX_LINE_LENGTH) + ((endsWithBadChar) ? "" : " -")));

                        int multiplier = 1;

                        while (line.length() - MAX_LINE_LENGTH * multiplier > MAX_LINE_LENGTH) {
                            endsWithBadChar = isBadChar(line.substring(MAX_LINE_LENGTH * multiplier
                                    + MAX_LINE_LENGTH - 2,
                                    (line.length() <= MAX_LINE_LENGTH * multiplier + MAX_LINE_LENGTH+1) ?
                                    MAX_LINE_LENGTH * multiplier + MAX_LINE_LENGTH : 
                                    MAX_LINE_LENGTH * multiplier + MAX_LINE_LENGTH+2));

                            labels.add(new MediumSkinnyLabel("       " + line.substring(
                                    MAX_LINE_LENGTH * multiplier, MAX_LINE_LENGTH * multiplier
                                    + MAX_LINE_LENGTH) + ((endsWithBadChar) ? "" : " -"), false, false, true));
                            multiplier++;
                        }

                        labels.add(new MediumSkinnyLabel("       " + line.substring(
                                MAX_LINE_LENGTH * multiplier, line.length()), false, false, true));
                    } else {
                        labels.add(new MediumSkinnyLabel(AppCenter.getPlaceAsString(placePosition) + ". " + line));
                    }
                    placePosition++;
                }
            }
        }

        AppCenter.year = String.valueOf(origYear);
    }

    private boolean isBadChar(String str) {
        char c1 = str.charAt(0);
        char c2 = str.charAt(1);
        
        if (str.length() == 2) {
            return c1 == '/' ||  c2 == '/';
        }
        
        char c3 = str.charAt(2);
        
        if (str.length() == 3) {
             return c1 == '/' || c2 == '/' || c3 == '/';
        }
        
        char c4 = str.charAt(3);
        
        return c1 == '/' || c2 == '/' || c3 == '/' || (c3 == ' ' && c4 == '/');
    }


    private int getMaxLineLength() {
        return ((int) (((AppFrame.frameSize.width * 0.28)) / AppCenter.fontCharWidth));
    }

}
