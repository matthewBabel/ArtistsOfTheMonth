package playlist.tracker.screen;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JComponent;
import javax.swing.JPanel;
import playlist.tracker.component.ComponentHandler;

public abstract class UpdatingScreen extends JPanel {

    ArrayList<JComponent> components = new ArrayList<>();
    private boolean resize = false;

    protected abstract void initScreen();

    public void update() {
        components.clear();
        initScreen();
        resize = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (resize) {
            resizeComponents(g);
        }
    }

    public final void resizeComponents(Graphics g) {
        JComponent[] comps = new JComponent[components.size()];
        ComponentHandler.scaleFonts(components.toArray(comps), g);
        resize = false;
    }

    protected final void addToComponents(JComponent[] arr) {
        components.addAll(Arrays.asList(arr));
    }
}
