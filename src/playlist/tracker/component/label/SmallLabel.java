package playlist.tracker.component.label;

import java.awt.Dimension;
import javax.swing.JLabel;
import playlist.tracker.font.FontHandler;
import playlist.tracker.frame.AppFrame;

public class SmallLabel extends JLabel {

    public SmallLabel(String text) {
        super(text);
        init(false);
    }

    public SmallLabel(String text, boolean bold) {
        super(text);
        init(bold);
    }
    
    
    private void init(boolean bold) {
        setSize(new Dimension((int)(AppFrame.frameSize.width * 0.08), 50));
        setPreferredSize(new Dimension((int)(AppFrame.frameSize.width * 0.08), 50));
        setFont((bold) ? FontHandler.boldFont : FontHandler.plainFont);
    }
}
