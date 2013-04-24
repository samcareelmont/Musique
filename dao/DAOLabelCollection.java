package musique.dao;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Sam Careelmont
 */

public class DAOLabelCollection extends AbstractDAOArtistLabelCollection {

    public DAOLabelCollection(Document document) throws JDOMException {
        Element elementRoot = document.getRootElement();
        super.setElementArtistLabel((Element)elementRoot.getChild("label"));
    }
    
}
