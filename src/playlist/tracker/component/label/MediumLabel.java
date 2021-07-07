package playlist.tracker.component.label;

import java.awt.Dimension;
import javax.swing.JLabel;
import playlist.tracker.font.FontHandler;
import playlist.tracker.frame.AppFrame;

public class MediumLabel extends JLabel {

    public MediumLabel() {
        super();
        init(false, false);
    }

    public MediumLabel(String text) {
        super(text);
        init(false, false);
    }

    public MediumLabel(String text, boolean shortHeight) {
        super(text);
        init(shortHeight, false);
    }

    public MediumLabel(String text, boolean shortHeight, boolean bold) {
        super(text);
        init(shortHeight, bold);
    }

    private void init(boolean shortHeight, boolean bold) {
        setPreferredSize(new Dimension(((int) (AppFrame.frameSize.width * 0.29)), (shortHeight) ? 60 : 75));
        setFont((bold) ? FontHandler.boldFont : FontHandler.plainFont);
    }
}
