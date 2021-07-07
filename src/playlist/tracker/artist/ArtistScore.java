package playlist.tracker.artist;


public class ArtistScore implements Comparable {

    public String name;
    public double score;

    public ArtistScore(String name, double score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        ArtistScore other = (ArtistScore) o;

        if (this.score < (double)other.score) {
            return 1;
        } else if (this.score == other.score) {
            return 0;
        } else {
            return -1;
        }
    }
}
