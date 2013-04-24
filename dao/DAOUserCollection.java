package musique.dao;

import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Sam Careelmont
 */

public class DAOUserCollection implements IDAOCollection {
    Element elementCollection;

    public DAOUserCollection(Document document) throws JDOMException {
        elementCollection = document.getRootElement();
    }

    public String getDescription() {
        String description = "";
        if(elementCollection.getChildText("info") != null) {
            description = elementCollection.getChildText("profile");
        }
        return description;
    }

    public String getName() {
        String name = "";
        if(elementCollection.getChildText("name") != null) {
            name = elementCollection.getChildText("name");
        }
        return name;
    }

    public ArrayList<String[]> getReleases() {
        ArrayList<String[]> releases = new ArrayList<String[]>();
        Element elementReleases = elementCollection.getChild("releases");
        if (elementReleases != null) {
            List listReleases = elementReleases.getChildren("release");
            for(Object obj : listReleases) {
                Element releaseElement = (Element)obj;
                String[] releaseInfo = new String[3];
                releaseInfo[0] = releaseElement.getAttributeValue("id").toString();
                releaseInfo[1] = releaseElement.getChildText("title");
                if(releaseElement.getChildText("year") != null && !releaseElement.getChildText("year").equals("")) {
                    releaseInfo[2] = releaseElement.getChildText("year");
                } else {
                    releaseInfo[2] = "-1";
                }
                releases.add(releaseInfo);
            }
        }
        return releases;
    }

    public String getImage() {
        return "";
    }
    
}
