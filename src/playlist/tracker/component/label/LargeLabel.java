package playlist.tracker.component.label;

import java.awt.Dimension;
import javax.swing.JLabel;
import playlist.tracker.font.FontHandler;
import playlist.tracker.frame.AppFrame;

public class LargeLabel extends JLabel {

    public LargeLabel() {
        super("");
        init(false, false);
    }

    public LargeLabel(String text) {
        super(text);
        init(false, false);
    }

    public LargeLabel(String text, boolean shortHeight) {
        super(text);
        init(shortHeight, false);
    }

    public LargeLabel(String text, boolean shortHeight, boolean bold) {
        super(text);
        init(shortHeight, bold);
    }

    private void init(boolean shortHeight, boolean bold) {
        setPreferredSize(new Dimension(((int) (AppFrame.frameSize.width * 0.4)), (shortHeight) ? 60 : 100));
        setFont((bold) ? FontHandler.boldFont : FontHandler.plainFont);
    }
}
