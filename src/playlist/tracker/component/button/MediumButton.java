package playlist.tracker.component.button;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import playlist.tracker.font.FontHandler;
import playlist.tracker.frame.AppFrame;

public class MediumButton extends JButton {

    public MediumButton(String text) {
        super(text);
        init();

    }

    private void init() {
        setPreferredSize(new Dimension(AppFrame.frameSize.width / 5, 65));
        setMargin(new Insets(0, 0, 0, 0));
        setFont(FontHandler.plainFont);
    }
}
