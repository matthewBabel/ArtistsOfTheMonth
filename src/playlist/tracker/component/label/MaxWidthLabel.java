package playlist.tracker.component.label;

import java.awt.Dimension;
import javax.swing.JLabel;
import playlist.tracker.font.FontHandler;
import playlist.tracker.frame.AppFrame;

public class MaxWidthLabel extends JLabel {

    public MaxWidthLabel(String text) {
        super(text);
        init();

    }

    private void init() {
        setPreferredSize(new Dimension(((int)(AppFrame.frameSize.width * 0.75)), 100));
        setFont(FontHandler.plainFont);
    }
}
