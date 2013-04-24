package musique.models.collectionModels;

/**
 *
 * @author Sam Careelmont
 */

public class Track {
    private String position, title, duration;

    public Track(String position, String title, String duration) {
        this.position = position;
        this.title = title;
        this.duration = duration;
    }

    public String getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }
    
}
