package playlist.tracker.artist;

import playlist.tracker.AppCenter;
import static playlist.tracker.AppCenter.artistsOfTheMonth;
import static playlist.tracker.AppCenter.artistHandler;

public class ArtistRecordMaker {

    public static int totalMonthCount = 0;
    
    public ArtistRecordMaker() {}

    public void makeRecords() {
        artistHandler.setUpForRecordCalculation();
        
        int curMonthNum = AppCenter.startMonth;
        int monthCount = 0;
        int place = 1;
        boolean newYear = true;

        for (int i = 2; i < artistsOfTheMonth.size(); i++) {
            if (artistsOfTheMonth.get(i).equals("space")) {
                artistHandler.endMonthForArtists(monthCount, newYear);
                
                place = 1;          
                monthCount++;
                curMonthNum++;
                if (curMonthNum == 12) {
                    curMonthNum = 0;
                    newYear = true;
                } else {
                    newYear = false;
                }
            } else {
                String curArtist = (artistsOfTheMonth.get(i));
                
                if (curArtist.contains("&")) {
                    String[] tiedArtists = curArtist.split("&");
                    
                    for (String a : tiedArtists) {
                        artistHandler.addMonthForArtist(a, place, monthCount);
                    }
                    
                    place += tiedArtists.length;
                } else {
                    artistHandler.addMonthForArtist(curArtist, place, monthCount);
                    
                    place++;
                }
            }
        }
        
        totalMonthCount = monthCount;
    }
}
