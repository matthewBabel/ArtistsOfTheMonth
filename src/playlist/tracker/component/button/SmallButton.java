package playlist.tracker.component.button;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import playlist.tracker.font.FontHandler;
import playlist.tracker.frame.AppFrame;

public class SmallButton extends JButton {

    public SmallButton(String text) {
        super(text);
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(AppFrame.frameSize.width / 8, 50));
        setMinimumSize(new Dimension(AppFrame.frameSize.width / 8, 50));
        setMargin(new Insets(0, 0, 0, 0));
        setFont(FontHandler.plainFont);
    }
}
