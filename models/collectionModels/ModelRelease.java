package musique.models.collectionModels;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import musique.models.Model;

/**
 *
 * @author Sam Careelmont
 */

public class ModelRelease extends Model {
    private String title, artist, label;
    private int id, year;
    private ArrayList<Track> tracklist;
    public enum Format {CD, Cassette, Vinyl, Other};
    private Format format;
    private BufferedImage bufImage;
    
    public ModelRelease(int id, String title, Format format, BufferedImage bufImage, int year, String[][] tracks, String artist, String label) {
        this.id = id;
        this.title = title;
        this.format = format;
        this.bufImage = bufImage;
        this.year = year;
        this.artist = artist;
        this.label = label;
        tracklist = new ArrayList<Track>();
        for(String[] track : tracks) {
            tracklist.add(new Track(track[0], track[1], track[2]));
        }
    }

    // Constructor om een object aan te maken met beperkte info
    public ModelRelease(int id, String title, int year) {
        this.id = id;
        this.title = title;
        this.format = format.Other;
        this.year = year;
        tracklist = new ArrayList<Track>();
    }

    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }

    public BufferedImage getBufferedImage() {
        return bufImage;
    }

    public Format getFormat() {
        return format;
    }

    public int getYear() {
        return year;
    }

    public String getArtist() {
        return artist;
    }

    public String getLabel() {
        return label;
    }

    public ArrayList<Track> getTrackList() {
        return tracklist;
    }

    // Deze methode wordt gebruikt om gegevens aan te vullen wanneer deze via een Thread geladen werden
    public void setInfo(Format format, BufferedImage bufImage, int year, ArrayList<Track> tracklist, String artist, String label) {
        this.format = format;
        this.bufImage = bufImage;
        this.year = year;
        this.artist = artist;
        this.label = label;
        this.tracklist = tracklist;
        fireStateChanged();
    }

    @Override
    public String toString() {
        return title;
    }

}
