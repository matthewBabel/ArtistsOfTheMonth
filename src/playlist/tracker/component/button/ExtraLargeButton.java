package playlist.tracker.component.button;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import playlist.tracker.font.FontHandler;
import playlist.tracker.frame.AppFrame;

public class ExtraLargeButton extends JButton {

    public ExtraLargeButton(String text) {
        super(text);
        init();

    }

    private void init() {
        setPreferredSize(new Dimension(AppFrame.frameSize.width / 2, 200));
        setMargin(new Insets(0, 0, 0, 0));
        setFont(FontHandler.boldFont);
    }
}
