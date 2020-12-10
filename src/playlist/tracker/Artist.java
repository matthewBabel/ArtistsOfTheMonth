package playlist.tracker;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import static playlist.tracker.AppCenter.BASEDIR;

/**
 * Holds data about a music artist.
 *
 * @author Matt Babel
 */
public class Artist {

    private int prevXCord = 0;
    private int prevYCord = 0;
    private final String name;
    private final int[] AOTMPlacing = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //1st, 2nd, 3rd, 4th, 5th..
    private FileData artistFile = null;

    /**
     * Creates Artist and gives a datafile if not already there.
     *
     * @param myName name of artist
     * @param newArtist true if a new artist
     */
    public Artist(String myName, boolean newArtist) {

        String strPath;
        Path filePath = null;

        if (newArtist) {
            strPath = "/data/artists/" + myName + ".txt";
            String dir = BASEDIR + "/resources";
            filePath = Paths.get(dir + strPath);

            name = myName;

            try {
                Files.createFile(filePath);
                artistFile = new FileData(dir + strPath);
                for (int i = 0; i < 10; i++) {
                    artistFile.add("0");
                }

                Random rand = new Random();
                artistFile.add(rand.nextInt(256) + " " + rand.nextInt(256) + " " + rand.nextInt(256));
                artistFile.save();
            } catch (FileAlreadyExistsException x) {
                System.err.format("Artist already Exists - " + strPath);
            } catch (IOException x) {
                System.err.format("createFile error: %s%n", x);
            }

        } else {
            strPath = BASEDIR + "/resources/data/artists/" + myName;
            artistFile = new FileData(strPath);
            name = myName.substring(0, myName.length() - 4);
            for (int i = 0; i < 10; i++) {
                AOTMPlacing[i] = Integer.valueOf(artistFile.get(i));
            }
        }
    }

    public int getNumberAtPlace(int place) {
        return AOTMPlacing[place - 1];
    }

    /**
     * Adds to the artist of the month total score.
     *
     * @param place
     */
    public void addAOTM(int place) {
        AOTMPlacing[place]++;
        artistFile.changeData(place, String.valueOf(AOTMPlacing[place]));
        artistFile.save();
    }

    /**
     * gets artist name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets total artist of the month score.
     *
     * @return total score
     */
    public int getTotalScore() {
        int total = 0;
        for (int i = 10; i > 0; i--) {
            total += i * AOTMPlacing[10 - i];
        }
        return total;
    }

    public Color getColor() {
        String str = artistFile.get(10);
        return new Color(Integer.valueOf(str.substring(0, str.indexOf(" "))),
                Integer.valueOf(str.substring(str.indexOf(" ") + 1, str.lastIndexOf(" "))),
                Integer.valueOf(str.substring(str.lastIndexOf(" ") + 1, str.length())));
    }

    public int getPrevXCord() {
        return prevXCord;
    }

    public int getPrevYCord() {
        return prevYCord;
    }

    public void setPrevCords(int x, int y) {
        prevXCord = x;
        prevYCord = y;
    }
}
