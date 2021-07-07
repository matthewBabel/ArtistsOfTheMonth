package playlist.tracker.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import static playlist.tracker.AppCenter.BASEDIR;
import static playlist.tracker.frame.AppFrame.frameSize;

public class FontHandler {

    public static Font extraSmallFont;
    public static Font smallFont;
    public static Font plainFont;
    public static Font mediumBoldFont;
    public static Font boldFont;
    public static Font extraBigFont;

    public static void initFonts() {
        extraSmallFont = makeFont(BASEDIR + "/resources/fonts/PT_Sans-Web-Regular.ttf", Font.BOLD, (frameSize.width / 109));
        smallFont = makeFont(BASEDIR + "/resources/fonts/PT_Sans-Web-Regular.ttf", Font.BOLD, (frameSize.width / 80));
        plainFont = makeFont(BASEDIR + "/resources/fonts/PT_Sans-Web-Regular.ttf", Font.PLAIN, (frameSize.width / 50));
        mediumBoldFont = makeFont(BASEDIR + "/resources/fonts/PT_Sans-Web-Bold.ttf", Font.BOLD, (frameSize.width / 43));
        boldFont = makeFont(BASEDIR + "/resources/fonts/PT_Sans-Web-Bold.ttf", Font.BOLD, (frameSize.width / 20));
        extraBigFont = makeFont(BASEDIR + "/resources/fonts/New Athletic M54.ttf", Font.PLAIN, (frameSize.width / 20));
    }

    /**
     * Makes font with given fileAddress, style and size
     *
     * @param fileAddress
     * @param style
     * @param size
     * @return the created font
     */
    public static Font makeFont(String fileAddress, int style, int size) {
        File fontFile = new File(fileAddress);
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            font = font.deriveFont(style, size);
            return font;
        } catch (FontFormatException | IOException e) {
            System.err.println("Font is null  makeFont : StatsCenter");
            return null;
        }
    }

    // Scales font to fit into a component, works for JLabel and JButton
    // For JButton make sure to put margins to 0 beforehand. 
    public static Font scaleFont(
            final JComponent comp, Rectangle dst, final Graphics graphics) {
        assert comp != null;
        assert dst != null;
        assert graphics != null;

        JLabel label = null;
        JButton btn = null;
        JToggleButton toggleBtn = null;
        if (comp instanceof JLabel) {
            label = (JLabel) comp;
        } else if (comp instanceof JButton) {
            final int PADDING = 10;
            btn = (JButton) comp;
            dst = new Rectangle(dst.width - PADDING, dst.height - PADDING);
        } else if (comp instanceof JToggleButton) {
            final int PADDING = 10;
            toggleBtn = (JToggleButton) comp;
            dst = new Rectangle(dst.width - PADDING, dst.height - PADDING);
        }

        final Font font = comp.getFont();
        final String text = (label != null) ? label.getText() : (btn != null) ? btn.getText()
                : (toggleBtn != null) ? toggleBtn.getText() : null;

        if (text.isEmpty()) {
            return font;
        }

        final FontRenderContext frc = ((Graphics2D) graphics).getFontRenderContext();

        final double dstWidthPx = dst.getWidth();
        final double dstHeightPx = dst.getHeight();

        float minSizePt = 1f;
        float maxSizePt = 1000f;
        Font scaledFont = font;
        float scaledPt = scaledFont.getSize();

        while (maxSizePt - minSizePt > 1f) {
            scaledFont = scaledFont.deriveFont(scaledPt);

            final TextLayout layout = new TextLayout(text, scaledFont, frc);
            final float fontWidthPx = layout.getVisibleAdvance();

            final LineMetrics metrics = scaledFont.getLineMetrics(text, frc);
            final float fontHeightPx = metrics.getHeight();

            if ((fontWidthPx > dstWidthPx) || (fontHeightPx > dstHeightPx)) {
                maxSizePt = scaledPt;
            } else {
                minSizePt = scaledPt;
            }

            scaledPt = (minSizePt + maxSizePt) / 2;
        }

        return scaledFont.deriveFont((float) Math.floor(scaledPt));
    }
}
