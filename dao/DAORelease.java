package musique.dao;

import be.ugent.caagt.discogs.DiscogsManager;
import java.io.IOException;
import java.util.List;
import musique.models.collectionModels.ModelRelease;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Sam Careelmont
 */

public class DAORelease {
    Element elementRelease;

    public DAORelease(int id) throws IOException, JDOMException {
        Document document = DiscogsManager.getReleaseXML(id);
        Element elementRoot = document.getRootElement();
        elementRelease = (Element)elementRoot.getChild("release");
    }

    public String getTitle() {
        String title = "";
        if(elementRelease.getChildText("title") != null) {
            title = elementRelease.getChildText("title");
        }
        return title;
    }

    public String getImage() {
        String image = "";
        Element elementImages = elementRelease.getChild("images");
        if (elementImages != null) {
            List listImage = elementImages.getChildren("image");
            int i = 0;
            Element imageElement = (Element)listImage.get(i);
            while(i < listImage.size() && !imageElement.getAttributeValue("type").equals("primary")) {
                imageElement = (Element)listImage.get(i);
                i++;
            }
            image = imageElement.getAttributeValue("uri150");
        }
        return image;
    }

    public int getYear() {
        String strYear = elementRelease.getChildText("released");
        if (strYear != null) {
            strYear = strYear.substring(0, 4);
            return Integer.parseInt(strYear);
        } else {
            return -1;
        }
    }

    public ModelRelease.Format getFormat() {
        ModelRelease.Format format = ModelRelease.Format.Other;
        Element elementFormats = elementRelease.getChild("formats");
        if (elementFormats != null) {
            Element elementFormat = elementFormats.getChild("format");
            try {
                format = format.valueOf(elementFormat.getAttributeValue("name"));
            } catch(IllegalArgumentException e) {
                // doe niets
            }
        }
        return format;
    }

    public String[][] getTracks() {
        String[][] tracks = new String[0][0];
        Element elementTracklist = elementRelease.getChild("tracklist");
        if (elementTracklist != null) {
            List listTrack = elementTracklist.getChildren("track");
            tracks = new String[listTrack.size()][];
            int i = 0;
            for(Object track : listTrack) {
                Element elementTrack = (Element)track;
                String[] info = {elementTrack.getChildText("position"), elementTrack.getChildText("title"), elementTrack.getChildText("duration")};
                tracks[i] = info;
                i++;
            }
        } 
        return tracks;
    }

    public String getArtist() {
        String artist = "";
        if(elementRelease.getChild("artists").getChild("artist").getChildText("name") != null) {
            artist = elementRelease.getChild("artists").getChild("artist").getChildText("name");
        }
        return artist;
    }

    public String getLabel() {
        String label = "";
        if(elementRelease.getChild("labels").getChild("label").getAttributeValue("name") != null) {
            label = elementRelease.getChild("labels").getChild("label").getAttributeValue("name");
        }
        return label;
    }

}
