package musique.dao;

import java.util.ArrayList;
import java.util.List;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Sam Careelmont
 */

public abstract class AbstractDAOArtistLabelCollection implements IDAOCollection {
    private Element elementArtistLabel;

    public AbstractDAOArtistLabelCollection() throws JDOMException {

    }

    public void setElementArtistLabel(Element elementArtistLabel) {
        this.elementArtistLabel = elementArtistLabel;
    }

    public String getName() {
        return elementArtistLabel.getChildText("name");
    }

    public String getDescription() {
        String description = "";
        if(elementArtistLabel.getChildText("profile") != null) {
            description = elementArtistLabel.getChildText("profile");
        }
        return description;
    }

    public ArrayList<String[]> getReleases() {
        ArrayList<String[]> releases = new ArrayList<String[]>();
        Element elementReleases = elementArtistLabel.getChild("releases");
        if (elementReleases != null) {
            List listReleases = elementReleases.getChildren("release");            
            for(Object obj : listReleases) {
                String[] releaseInfo = new String[2];
                Element releaseElement = (Element)obj;
                releaseInfo[0] = releaseElement.getAttributeValue("id").toString();
                releaseInfo[1] = releaseElement.getChildText("title");
                releases.add(releaseInfo);
            }
        }
        return releases;
    }

    public String getImage() {
        String image = "";
        Element elementImages = elementArtistLabel.getChild("images");
        if (elementImages != null) {
            List listImage = elementImages.getChildren("image");
            int i = 0;
            Element imageElement = (Element)listImage.get(i);
            while(i < listImage.size() && !imageElement.getAttributeValue("type").equals("primary")) {
                imageElement = (Element)listImage.get(i);
                i++;
            }
            image = imageElement.getAttributeValue("uri");
        }
        return image;
    }        
}
