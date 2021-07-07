package playlist.tracker.component.label;

import java.awt.Dimension;
import javax.swing.JLabel;
import playlist.tracker.font.FontHandler;

public class CustomLabel extends JLabel {

    public CustomLabel(String text, int width, int height) {
        super(text);
        init(width, height, false, false);
    }

    public CustomLabel(String text, int width, int height, boolean bold) {
        super(text);
        init(width, height, bold, false);
    }

    public CustomLabel(String text, int width, int height, boolean bold, boolean setPreferred) {
        super(text);
        init(width, height, bold, setPreferred);
    }

    private void init(int width, int height, boolean bold, boolean preferred) {
        setSize(new Dimension(width, height));
        if (preferred) {
            setPreferredSize(new Dimension(width, height));
        }
        setFont((bold) ? FontHandler.boldFont : FontHandler.plainFont);
    }
}
