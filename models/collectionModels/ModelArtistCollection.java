package musique.models.collectionModels;

import musique.dao.DAOArtistCollection;
import org.jdom.JDOMException;

/**
 *
 * @author Sam Careelmont
 */

public class ModelArtistCollection extends AbstractModelCollection {
    public ModelArtistCollection(DAOArtistCollection daoArtistCollection) throws JDOMException {
        super(daoArtistCollection);
    }
    
    @Override
    public String getCollectionType() {
            return "ArtistCollection";
    }
}
