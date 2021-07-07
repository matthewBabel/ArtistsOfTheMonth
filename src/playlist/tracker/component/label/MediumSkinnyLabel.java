package playlist.tracker.component.label;

import java.awt.Dimension;
import javax.swing.JLabel;
import playlist.tracker.font.FontHandler;
import playlist.tracker.frame.AppFrame;

public class MediumSkinnyLabel extends JLabel {

    public boolean copyPrevFont = false;

    public MediumSkinnyLabel() {
        super();
        init(false, false);
    }

    public MediumSkinnyLabel(String text) {
        super(text);
        init(false, false);
    }

    public MediumSkinnyLabel(String text, boolean slightHeightIncrease) {
        super(text);
        init(slightHeightIncrease, false);
    }

    public MediumSkinnyLabel(String text, boolean slightHeightIncrease, boolean bold) {
        super(text);
        init(slightHeightIncrease, bold);
    }

    public MediumSkinnyLabel(String text, boolean slightHeightIncrease, boolean bold, boolean prevFont) {
        super(text);
        init(slightHeightIncrease, bold);
        copyPrevFont = prevFont;
    }

    private void init(boolean slightHeightIncrease, boolean bold) {
        setPreferredSize(new Dimension((int) (AppFrame.frameSize.width * 0.35), (slightHeightIncrease) ? 40 : 30));
        setFont((bold) ? FontHandler.boldFont : FontHandler.plainFont);
        setHorizontalAlignment(JLabel.LEFT);
    }
}
