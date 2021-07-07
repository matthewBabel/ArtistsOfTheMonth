package playlist.tracker.artist;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import playlist.tracker.AppCenter;
import playlist.tracker.FileData;
import static playlist.tracker.AppCenter.BASEDIR;

/**
 * Holds data about a music artist.
 *
 * @author Matt Babel
 */
public class Artist implements Comparable {

    private int prevXCord = 0;
    private int prevYCord = 0;
    private final String name;
    private final int[] AOTMPlacing = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //1st, 2nd, 3rd, 4th, 5th..
    private FileData artistFile = null;

    //record keeping
    private ArrayList<Integer> streaks = new ArrayList<>();
    private ArrayList<Integer> timeBetweenAppearances = new ArrayList<>();
    private ArrayList<Integer> scorePerYear = new ArrayList<>();

    private boolean debutMade = false;
    private boolean onLastMonth = false;
    private boolean onThisMonth = false;
    private double monthsSinceDebut = 0;
    private double monthsOnSinceDebut = 0;
    private String debut;
    private String lastAppearance;
    private int lastPlacement;
    private int debutTime;
    private int lastAppearanceTime;

    // a running score starting at every month, so [0] is the start and same as all time score
    // [20] is an artists score starting at the 21st month of the playlist, any score before this month isn't counted
    private ArrayList<Integer> scorePerMonth = new ArrayList<>();

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

    public void setUpForCalculation() {
        debutMade = false;
        streaks = new ArrayList<>();
        timeBetweenAppearances = new ArrayList<>();
        scorePerMonth = new ArrayList<>();
        scorePerYear = new ArrayList<>();
        monthsSinceDebut = 0;
        monthsOnSinceDebut = 0;
    }

    public String getHighestPlacement() {
        int place = 1;
        for (int count : AOTMPlacing) {
            if (count != 0) {
                return AppCenter.getPlaceAsString(place);
            } else {
                place++;
            }
        }

        return AppCenter.getPlaceAsString(place);
    }

    public String getLowestPlacement() {
        int place = 10;
        for (int i = 9; i > -1; i--) {
            if (AOTMPlacing[i] != 0) {
                return AppCenter.getPlaceAsString(place);
            } else {
                place--;
            }
        }

        return AppCenter.getPlaceAsString(place);
    }

    public String getMostPlaced() {
        ArrayList<Integer> mostPlaced = new ArrayList<>();
        int mostCount = 0;
        int place = 1;
        for (int i = 0; i < AOTMPlacing.length; i++) {
            if (AOTMPlacing[i] > mostCount) {
                mostCount = AOTMPlacing[i];
                mostPlaced.clear();
                mostPlaced.add(place);
            } else if (AOTMPlacing[i] == mostCount) {
                mostPlaced.add(place);
            }

            place++;
        }

        String str = "";

        boolean first = true;
        for (int n : mostPlaced) {
            str = (first) ? str.concat(AppCenter.getPlaceAsString(n))
                    : str.concat(", " + AppCenter.getPlaceAsString(n));
            first = false;
        }

        str = str.concat(" (" + mostCount + ")");

        return str;
    }

    public String getAvgPlacement() {
        double totalPlaces = 0;
        double totalMonths = 0;

        int place = 1;
        for (int n : AOTMPlacing) {
            if (n != 0) {
                totalPlaces += (place * n);
                totalMonths += n;
            }

            place++;
        }

        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(totalPlaces / totalMonths);
    }

    public String getTotalTime() {
        int months = 0;
        for (int n : AOTMPlacing) {
            months += n;
        }

        return AppCenter.readableMonthsString(months);
    }

    public int getTotalTimeAsInteger() {
        int months = 0;
        for (int n : AOTMPlacing) {
            months += n;
        }

        return months;
    }

    public String getDebut() {
        return debut;
    }

    public String getLastAppearance() {
        return lastAppearance;
    }

    public int getLongestStreak() {
        int max = 0;
        for (int n : streaks) {
            if (n > max) {
                max = n;
            }
        }

        return max;
    }

    public int getShortestStreak() {
        int min = streaks.get(0);
        for (int n : streaks) {
            if (n < min) {
                min = n;
            }
        }

        return min;
    }

    public Integer[] getStreaks() {
        return streaks.toArray(new Integer[streaks.size()]);
    }

    public String getAvgStreak() {
        double streaksCount = streaks.size();
        double totalStreaks = 0;

        for (int n : streaks) {
            totalStreaks += n;
        }

        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(totalStreaks / streaksCount);
    }

    public int getAppearances() {
        return streaks.size();
    }

    public int getMostTimeBetweenAppearances() {
        int max = 0;

        ArrayList<Integer> arr = (ArrayList<Integer>) timeBetweenAppearances.clone();
        arr.remove(arr.size() - 1);

        if (arr.isEmpty()) {
            return 0;
        }

        for (int n : arr) {
            if (n > max) {
                max = n;
            }
        }
        return max;
    }

    public int getShortTimeBetweenAppearances() {

        int min = timeBetweenAppearances.get(0);
        ArrayList<Integer> arr = (ArrayList<Integer>) timeBetweenAppearances.clone();
        arr.remove(arr.size() - 1);

        if (arr.isEmpty()) {
            return 0;
        }

        for (int n : arr) {
            if (n < min) {
                min = n;
            }
        }
        return min;
    }
    
    public Integer[] getTimeBetweenAppearances() {
        return timeBetweenAppearances.toArray(new Integer[timeBetweenAppearances.size()]);
    }

    public String getAvgtimeBetweenAppearances() {
        ArrayList<Integer> arr = (ArrayList<Integer>) timeBetweenAppearances.clone();
        arr.remove(arr.size() - 1);
        if (arr.isEmpty()) {
            return 0 + "";
        }

        double totalSize = arr.size();
        double totalTime = 0;

        for (int n : arr) {
            totalTime += n;
        }

        DecimalFormat df = new DecimalFormat("#.####");
        return df.format(totalTime / totalSize);
    }

    public int getScoreAtMonth(int time) {
        return scorePerMonth.get(time);
    }

    public int getTimeSize() {
        return scorePerMonth.size();
    }

    public int getYearsSize() {
        return scorePerYear.size();
    }

    public int getScoreByYearIndex(int index) {
        return scorePerYear.get(index);
    }

    public int getDebutTime() {
        return debutTime;
    }
    
    public int getLastAppearanceTime() {
        return lastAppearanceTime;
    }

    public String getPercentageDebutTimeOn() {
        DecimalFormat df = new DecimalFormat("##.##");
        double num = 100 * (monthsOnSinceDebut / monthsSinceDebut);

        return df.format(num);
    }
    
    public String getPercentageTotalTimeOn() {
        DecimalFormat df = new DecimalFormat("##.##");
        double num = 100 * (monthsOnSinceDebut / ArtistRecordMaker.totalMonthCount);

        return df.format(num);
    }

    public String getBestYear() {
        return getBestYear(false);
    }
    
    public String getBestYear(boolean returnOne) {
        int intYear = Integer.valueOf(AppCenter.year);

        int bestScore = 0;
        int bestYear = intYear;
        String bestYearStr = "";

        for (int score : scorePerYear) {
            if (score > bestScore) {
                bestScore = score;
                bestYear = intYear;
                bestYearStr = bestYear + "";
            } else if (score == bestScore) {
                bestYearStr = bestYearStr.concat(", " + intYear);
            }

            intYear++;
        }

        
        return (returnOne) ? bestYear+"" : bestYearStr;
    }

    // only called if artist had a placement this month
    public void addMonthInRecords(int placement, int time) {
        if (!debutMade) {
            debutMade = true;
            debutTime = time;
            debut = AppCenter.getReadableTime(time);
            streaks.add(1);
            timeBetweenAppearances.add(0);
        } else if (onLastMonth) {
            int lastStreakIndex = streaks.size() - 1;
            streaks.set(lastStreakIndex, streaks.get(lastStreakIndex) + 1);
        } else {
            streaks.add(1);
            timeBetweenAppearances.add(0);
        }

        lastPlacement = placement;
        lastAppearance = AppCenter.getReadableTime(time);
        lastAppearanceTime = time;
        onThisMonth = true;
    }

    // gets called for every artist
    public void endMonthInRecords(int time, boolean newYear) {
        if (newYear) {
            scorePerYear.add(0);
        }

        if (onThisMonth) {
            onLastMonth = true;
            onThisMonth = false;

            for (int i = 0; i < scorePerMonth.size(); i++) {
                scorePerMonth.set(i, scorePerMonth.get(i) + (11 - lastPlacement));
            }
            scorePerMonth.add(11 - lastPlacement);
            int yearLastIndex = scorePerYear.size() - 1;
            scorePerYear.set(yearLastIndex, scorePerYear.get(yearLastIndex) + (11 - lastPlacement));
            monthsOnSinceDebut++;
            monthsSinceDebut++;

        } else {
            onLastMonth = false;
            scorePerMonth.add(0);

            if (debutMade) {
                int lastIndex = timeBetweenAppearances.size() - 1;
                timeBetweenAppearances.set(lastIndex, timeBetweenAppearances.get(lastIndex) + 1);
                monthsSinceDebut++;
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        Artist other = (Artist) o;

        return this.name.compareTo(other.name);
    }
}
