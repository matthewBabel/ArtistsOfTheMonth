package playlist.tracker.component.label;

import java.awt.Dimension;
import javax.swing.JLabel;
import playlist.tracker.font.FontHandler;
import playlist.tracker.frame.AppFrame;

public class ExtraLargeLabel extends JLabel {

    
    public ExtraLargeLabel(String text) {
        super(text);
        init(false);
    }
    
    public ExtraLargeLabel(String text, boolean boldFont) {
        super(text);
        init(boldFont);
    }

    private void init(boolean bold) {
        setPreferredSize(new Dimension((int)(AppFrame.frameSize.width * 0.5), 100));
        setFont((bold) ? FontHandler.boldFont : FontHandler.plainFont);
        setHorizontalAlignment(JLabel.CENTER);
    }
}
