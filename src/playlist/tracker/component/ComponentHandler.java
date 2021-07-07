package playlist.tracker.component;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;
import playlist.tracker.component.label.MediumSkinnyLabel;
import playlist.tracker.font.FontHandler;

public class ComponentHandler {

    public static void scaleFonts(JComponent[] comps, Graphics g) {
        Font prevFont = null;

        for (JComponent comp : comps) {

            if (comp instanceof MediumSkinnyLabel && ((MediumSkinnyLabel) comp).copyPrevFont) {
                comp.setFont(prevFont);
            } else {
                comp.setFont(FontHandler.scaleFont(comp, comp.getBounds(), g));
                prevFont = comp.getFont();
            }
        }
    }
    
    public static void scaleComps(JComponent[] comps) {
        for (JComponent comp : comps) {
            comp.setSize(comp.getPreferredSize());
        }
    }
}
