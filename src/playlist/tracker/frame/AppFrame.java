package playlist.tracker.frame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import playlist.tracker.AppCenter;
import static playlist.tracker.AppCenter.frame;
import static playlist.tracker.AppCenter.updateShownScreen;
import playlist.tracker.font.FontHandler;

public class AppFrame extends JFrame {

    public static Dimension defaultSize;
    public static Dimension frameSize;

    ActionListener doneResizeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frameSize = frame.getSize();
            FontHandler.initFonts();
            updateShownScreen();
        }
    };

    private Timer resizeTimer = new Timer(50, doneResizeListener);

    public AppFrame() {
        defaultSize = new Dimension(1200, 1000);
        frameSize = defaultSize;
        resizeTimer.setRepeats(false);
        init();
    }

    private void init() {
        setTitle("Music Database");
        setLocation(100, 20);
        setSize(defaultSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(900, 600));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                if (resizeTimer.isRunning()) {
                    resizeTimer.restart();
                } else {
                    resizeTimer.start();
                }
            }
        });

        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                int oldState = e.getOldState();
                int newState = e.getNewState();

                if ((oldState & frame.MAXIMIZED_BOTH) == 0 && (newState & frame.MAXIMIZED_BOTH) != 0) {
                    frameSize = frame.getSize();
                    FontHandler.initFonts();
                    AppCenter.updateShownScreen();
                }
            }
        });

    }

}
