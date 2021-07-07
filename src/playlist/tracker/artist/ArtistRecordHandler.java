package playlist.tracker.artist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import playlist.tracker.AppCenter;
import playlist.tracker.FileData;
import static playlist.tracker.AppCenter.BASEDIR;

public class ArtistRecordHandler {

    private static final List<String> NUMS = new ArrayList<String>() {
        {
            add("0");
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("8");
            add("9");
        }
    };

    private final List<Artist> ARTISTS = new ArrayList<>();
    private static ArrayList<ArrayList<ArtistScore>> artistScoresByTime = new ArrayList<>();
    ArrayList<ArtistScore> artistAllTimeScores = new ArrayList<>();

    //0 = 1st, 1 = 2nd
    ArrayList<ArrayList<ArtistScore>> artistTopPerPlace = new ArrayList<>();

    ArrayList<ArrayList<ArtistScore>> artistTopPerYear = new ArrayList<>();
    ArrayList<ArtistScore> onePerArtistTopStreaks = new ArrayList<>();
    ArrayList<ArtistScore> artistTopStreaks = new ArrayList<>();
    ArrayList<ArtistScore> artistTotalTime = new ArrayList<>();
    ArrayList<ArtistScore> artistAveragePlacement = new ArrayList<>();
    ArrayList<ArtistScore> artistTotalAppearances = new ArrayList<>();
    ArrayList<ArtistScore> artistTimeBetweenAppearances = new ArrayList<>();

    public ArtistRecordHandler() {
        FileData artistFolder = new FileData(BASEDIR + "/resources/data/artists");
        for (int i = 0; i < artistFolder.size(); i++) {
            ARTISTS.add(new Artist(artistFolder.get(i), false));
        }
    }

    public Artist getArtist(String name) {
        for (Artist artist : ARTISTS) {
            if (artist.getName().toUpperCase().equals(name.toUpperCase())) {
                return artist;
            }
        }
        return null;
    }

    public List<Artist> getArtistsList() {
        return ARTISTS;
    }

    public List<ArtistScore> getAllTimeScoresList() {
        return artistAllTimeScores;
    }

    public void setUpForRecordCalculation() {
        ARTISTS.forEach((a) -> a.setUpForCalculation());
    }

    public void addMonthForArtist(String name, int placement, int time) {
        Artist artist = getArtist(name);
        artist.addMonthInRecords(placement, time);
    }

    public void endMonthForArtists(int time, boolean newYear) {
        ARTISTS.forEach((a) -> a.endMonthInRecords(time, newYear));
    }

    public void sortAll() {
        sortTimeRecords();
        sortAllTimeScores();
        sortPlaceRecords();
        sortYearRecords();
        sortOnePerArtistStreaks();
        sortTotalStreaks();
        sortTotalTime();
        sortAveragePlacement();
        sortAppearances();
        sortTimeBetweenAppearances();
        sortArtistListByLetter();

    }

    public void sortArtistListByLetter() {
        Collections.sort(ARTISTS, LexicalComparator);
    }

    public void sortArtistListByScore() {
        Collections.sort(ARTISTS, TopScoreComparator);
    }

    public void sortArtistListByPlaceSinceDebut() {
        Collections.sort(ARTISTS, DebutPlaceComparator);
    }

    public void sortArtistListByAvgPlacement() {
        Collections.sort(ARTISTS, AvgPlacementComparator);
    }

    public void sortArtistListByDebutTime() {
        Collections.sort(ARTISTS, DebutTimeComparator);
    }

    public void sortArtistListByLastAppearance() {
        Collections.sort(ARTISTS, LastAppearanceTimeComparator);
    }

    public void sortArtistListByDebutTimeOn() {
        Collections.sort(ARTISTS, DebutTimeOnComparator);
    }
    
    public void sortArtistListByTotalTimeOn() {
        Collections.sort(ARTISTS, TotalTimeOnComparator);
    }

    public void sortArtistListByBestYear() {
        Collections.sort(ARTISTS, BestYearComparator);
    }

    public void reverseArtistList() {
        Collections.reverse(ARTISTS);
    }

    private void sortTimeRecords() {

        artistScoresByTime = new ArrayList<>();

        ArrayList<ArtistScore> artistScores;
        int artistsSize = ARTISTS.size();

        for (int time = 0; time < ARTISTS.get(0).getTimeSize(); time++) {
            artistScores = new ArrayList<>();
            for (int i = 0; i < artistsSize; i++) {
                artistScores.add(new ArtistScore(ARTISTS.get(i).getName(),
                        ARTISTS.get(i).getScoreAtMonth(time)));
            }
            Collections.sort(artistScores);
            artistScoresByTime.add(artistScores);
        }
    }

    private void sortPlaceRecords() {
        artistTopPerPlace = new ArrayList<>();

        ArrayList<ArtistScore> artistPlace;

        for (int i = 1; i < 11; i++) {
            artistPlace = new ArrayList<>();
            for (Artist artist : ARTISTS) {
                ArtistScore artistScore = new ArtistScore(artist.getName(),
                        artist.getNumberAtPlace(i));
                artistPlace.add(artistScore);
            }

            Collections.sort(artistPlace);
            artistTopPerPlace.add(artistPlace);
        }
    }

    private void sortYearRecords() {
        artistTopPerYear = new ArrayList<>();

        ArrayList<ArtistScore> artistYear;

        for (int i = 0; i < ARTISTS.get(0).getYearsSize(); i++) {
            artistYear = new ArrayList<>();
            for (Artist artist : ARTISTS) {
                ArtistScore artistScore = new ArtistScore(artist.getName(),
                        artist.getScoreByYearIndex(i));
                artistYear.add(artistScore);
            }

            Collections.sort(artistYear);
            artistTopPerYear.add(artistYear);
        }
    }

    private void sortAllTimeScores() {
        artistAllTimeScores = new ArrayList<>();

        for (int i = 0; i < ARTISTS.size(); i++) {
            artistAllTimeScores.add(new ArtistScore(ARTISTS.get(i).getName(),
                    ARTISTS.get(i).getTotalScore()));
        }

        Collections.sort(artistAllTimeScores);
    }

    private void sortOnePerArtistStreaks() {
        onePerArtistTopStreaks = new ArrayList<>();

        for (int i = 0; i < ARTISTS.size(); i++) {
            onePerArtistTopStreaks.add(new ArtistScore(ARTISTS.get(i).getName(),
                    ARTISTS.get(i).getLongestStreak()));
        }

        Collections.sort(onePerArtistTopStreaks);
    }

    private void sortTotalStreaks() {
        artistTopStreaks = new ArrayList<>();

        for (int i = 0; i < ARTISTS.size(); i++) {

            Integer[] streaks = ARTISTS.get(i).getStreaks();

            for (Integer num : streaks) {
                artistTopStreaks.add(new ArtistScore(ARTISTS.get(i).getName(),
                        num));
            }
        }

        Collections.sort(artistTopStreaks);
    }

    private void sortTotalTime() {
        artistTotalTime = new ArrayList<>();

        for (int i = 0; i < ARTISTS.size(); i++) {
            artistTotalTime.add(new ArtistScore(ARTISTS.get(i).getName(),
                    ARTISTS.get(i).getTotalTimeAsInteger()));
        }

        Collections.sort(artistTotalTime);
    }

    private void sortAveragePlacement() {
        artistAveragePlacement = new ArrayList<>();

        for (int i = 0; i < ARTISTS.size(); i++) {
            artistAveragePlacement.add(new ArtistScore(ARTISTS.get(i).getName(),
                    Double.valueOf(ARTISTS.get(i).getAvgPlacement())));
        }

        Collections.sort(artistAveragePlacement);
        Collections.reverse(artistAveragePlacement);
    }

    private void sortAppearances() {
        artistTotalAppearances = new ArrayList<>();

        for (int i = 0; i < ARTISTS.size(); i++) {
            artistTotalAppearances.add(new ArtistScore(ARTISTS.get(i).getName(),
                    ARTISTS.get(i).getAppearances()));
        }

        Collections.sort(artistTotalAppearances);
    }

    private void sortTimeBetweenAppearances() {
        artistTimeBetweenAppearances = new ArrayList<>();

        for (int i = 0; i < ARTISTS.size(); i++) {

            Integer[] timeBetween = ARTISTS.get(i).getTimeBetweenAppearances();

            for (int j = 0; j < timeBetween.length - 1; j++) {
                artistTimeBetweenAppearances.add(new ArtistScore(ARTISTS.get(i).getName(),
                        timeBetween[j]));
            }
        }

        Collections.sort(artistTimeBetweenAppearances);
    }

    public String getPlaceSinceDebut(Artist artist) {
        int time = artist.getDebutTime();

        ArrayList<ArtistScore> scores = artistScoresByTime.get(time);
        return getPlaceInScores(artist, scores);
    }

    private static int getPlaceSinceDebutAsInt(Artist artist) {
        int time = artist.getDebutTime();

        ArrayList<ArtistScore> scores = artistScoresByTime.get(time);

        return Integer.valueOf(getPlaceInScores(artist, scores, true));
    }

    public String getPlaceAlltime(Artist artist) {
        return getPlaceInScores(artist, artistAllTimeScores);
    }

    public String getPlacePerPlacement(Artist artist, int placement) {
        return getPlaceInScores(artist, artistTopPerPlace.get(placement - 1));
    }

    public String[] getTopArtistsPlacement(int placement, int limit) {
        return getTopInScores(artistTopPerPlace.get(placement - 1), limit);
    }

    public String[] getTopArtistsYear(int yearIndex, int limit) {
        return getTopInScores(artistTopPerYear.get(yearIndex), limit);
    }

    public String[] getTopStreaksOnePer(int limit) {
        return getTopInScores(onePerArtistTopStreaks, limit, true);
    }

    public String[] getTopStreaks(int limit) {
        return getTopInScores(artistTopStreaks, limit, true);
    }

    public String[] getTopTotalTime(int limit) {
        return getTopInScores(artistTotalTime, limit, true);
    }

    public String[] getTopAveragePlacement(int limit) {
        return getTopInScores(artistAveragePlacement, limit, false, true);
    }

    public String[] getTopAppearances(int limit) {
        return getTopInScores(artistTotalAppearances, limit);
    }

    public String[] getTopTimeBetweenAppearances(int limit) {
        return getTopInScores(artistTimeBetweenAppearances, limit, true);
    }

    private String[] getTopInScores(ArrayList<ArtistScore> scores, int limit) {
        return getTopInScores(scores, limit, false, false);
    }

    private String[] getTopInScores(ArrayList<ArtistScore> scores, int limit, boolean scoreAstime) {
        return getTopInScores(scores, limit, scoreAstime, false);
    }

    private String[] getTopInScores(ArrayList<ArtistScore> scores, int limit, boolean scoreAsTime, boolean scoreAndTime) {

        ArrayList<String> topScores = new ArrayList<>();
        ArrayList<NameCount> nameCounts = new ArrayList<>();

        String str;
        int count = 0;
        double lastScore = -1;
        while ((count < scores.size() && lastScore != 0)
                && (limit > count || scores.get(count).score == lastScore)) {
            String name = scores.get(count).name;
            lastScore = scores.get(count).score;

            if (lastScore != 0) {
                nameCounts = addToNameCount(nameCounts, name);
                Artist artist = getArtist(name);

                String score = ((scoreAsTime) ? AppCenter.readableMonthsString((int) lastScore)
                        : (lastScore == Math.floor(lastScore)) ? (int) lastScore + "" : lastScore + "");

                str = getPlaceInScores(artist, scores, getNameCount(nameCounts, name) - 1);
                str = str.concat(" " + getReadableNameCount(nameCounts, name) + " - " + score
                        + ((scoreAndTime) ? " (" + artist.getTotalTime() + ")" : ""));
                topScores.add(str);
            }
            count++;
        }

        return topScores.toArray(new String[topScores.size()]);
    }

    private String getPlaceInScores(Artist artist, ArrayList<ArtistScore> scores) {
        return getPlaceInScores(artist, scores, 0, false);
    }

    private String getPlaceInScores(Artist artist, ArrayList<ArtistScore> scores, int skips) {
        return getPlaceInScores(artist, scores, skips, false);
    }

    private static String getPlaceInScores(Artist artist, ArrayList<ArtistScore> scores, boolean justInt) {
        return getPlaceInScores(artist, scores, 0, justInt);
    }

    private static String getPlaceInScores(Artist artist, ArrayList<ArtistScore> scores, int skips, boolean returnInt) {

        int timesFound = 0;
        int place = 1;
        int placeSkip = 0;
        double lastScore = 0;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).name.toUpperCase().equals(artist.getName().toUpperCase())) {

                if (timesFound < skips) {
                    timesFound++;

                    if (scores.get(i).score == lastScore) {
                        placeSkip++;
                    } else {
                        lastScore = scores.get(i).score;
                        place += placeSkip;
                        placeSkip = 1;
                    }
                } else if (scores.get(i).score == lastScore) {
                    return ((returnInt) ? place + "" : AppCenter.getPlaceAsString(place));
                } else {
                    return ((returnInt) ? (place + placeSkip) + "" : AppCenter.getPlaceAsString(place + placeSkip));
                }
            } else {
                if (scores.get(i).score == lastScore) {
                    placeSkip++;
                } else {
                    lastScore = scores.get(i).score;
                    place += placeSkip;
                    placeSkip = 1;
                }
            }
        }

        return ((returnInt) ? place + "" : AppCenter.getPlaceAsString(place));
    }

    private ArrayList<NameCount> addToNameCount(ArrayList<NameCount> names, String name) {

        boolean pass = false;

        for (NameCount nc : names) {
            if (nc.name.toUpperCase().equals(name.toUpperCase())) {
                nc.count++;
                pass = true;
                break;
            }
        }

        if (!pass) {
            names.add(new NameCount(name));
        }

        return names;
    }

    private String getReadableNameCount(ArrayList<NameCount> names, String name) {
        for (NameCount nc : names) {
            if (nc.name.toUpperCase().equals(name.toUpperCase())) {
                return nc.getNameWithCount();
            }
        }

        return "No Name";
    }

    private int getNameCount(ArrayList<NameCount> names, String name) {
        for (NameCount nc : names) {
            if (nc.name.toUpperCase().equals(name.toUpperCase())) {
                return nc.count;
            }
        }

        return 0;
    }

    class NameCount {

        public String name;
        public int count;

        public NameCount(String n) {
            name = n;
            count = 1;
        }

        public String getNameWithCount() {
            return (count > 1) ? name + " (" + count + ")" : name;
        }
    }

    public static Comparator<Artist> LexicalComparator = new Comparator<Artist>() {
        @Override
        public int compare(Artist a1, Artist a2) {
            String name1 = a1.getName();
            String name2 = a2.getName();

            if (NUMS.contains(name1.substring(0, 1)) && NUMS.contains(name2.substring(0, 1))) {
                return a1.compareTo(a2);
            } else if (NUMS.contains(name1.substring(0, 1))) {
                return 1;
            } else if (NUMS.contains(name2.substring(0, 1))) {
                return -1;
            } else {
                return a1.compareTo(a2);
            }
        }
    };

    public static Comparator<Artist> TopScoreComparator = (Artist a1, Artist a2) -> a2.getTotalScore() - a1.getTotalScore();

    public static Comparator<Artist> DebutPlaceComparator = (Artist a1, Artist a2) -> getPlaceSinceDebutAsInt(a1) - getPlaceSinceDebutAsInt(a2);

    public static Comparator<Artist> AvgPlacementComparator = (Artist a1, Artist a2) -> (Double.compare(Double.valueOf(a1.getAvgPlacement()), Double.valueOf(a2.getAvgPlacement())));

    public static Comparator<Artist> DebutTimeComparator = (Artist a1, Artist a2) -> a1.getDebutTime() - a2.getDebutTime();

    public static Comparator<Artist> LastAppearanceTimeComparator = (Artist a1, Artist a2) -> a2.getLastAppearanceTime() - a1.getLastAppearanceTime();

    public static Comparator<Artist> DebutTimeOnComparator = (Artist a1, Artist a2) -> (Double.compare(Double.valueOf(a2.getPercentageDebutTimeOn()), Double.valueOf(a1.getPercentageDebutTimeOn())));

    public static Comparator<Artist> TotalTimeOnComparator = (Artist a1, Artist a2) -> (Double.compare(Double.valueOf(a2.getPercentageTotalTimeOn()), Double.valueOf(a1.getPercentageTotalTimeOn())));
    
    public static Comparator<Artist> BestYearComparator = (Artist a1, Artist a2) -> Integer.valueOf(a1.getBestYear(true)) - Integer.valueOf(a2.getBestYear(true));
}
