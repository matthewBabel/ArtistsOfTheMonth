package playlist.tracker.component.label;

import java.awt.Dimension;
import javax.swing.JLabel;
import playlist.tracker.font.FontHandler;
import playlist.tracker.frame.AppFrame;

public class SmallSkinnyLabel extends JLabel {

    public SmallSkinnyLabel(String text) {
        super(text);
        init(false);
    }

    public SmallSkinnyLabel(String text, boolean bold) {
        super(text);
        init(bold);
    }
    
    
    private void init(boolean bold) {
        setSize(new Dimension((int)(AppFrame.frameSize.width * 0.08), 20));
        setPreferredSize(new Dimension((int)(AppFrame.frameSize.width * 0.08), 20));
        setFont((bold) ? FontHandler.boldFont : FontHandler.plainFont);
    }
}
