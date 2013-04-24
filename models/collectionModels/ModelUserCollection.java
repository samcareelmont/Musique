package musique.models.collectionModels;

import musique.dao.DAOUserCollection;
import org.jdom.JDOMException;

/**
 *
 * @author Sam Careelmont
 */

public class ModelUserCollection extends AbstractModelCollection {

    public ModelUserCollection(String name) {
        super(name);
        super.setDescription("no description");
    }

    public ModelUserCollection(DAOUserCollection daoUserCollection) throws JDOMException {
        super(daoUserCollection);
    }

    // Methode waarmee de naam van een collectie gewijzigd kan worden
    @Override
    public void setName(String name) {
        if(!name.equals(super.getName())) {
            super.setName(name);
            fireStateChanged();
        }
    }

    @Override
    public String getCollectionType() {
        return "UserCollection";
    }

}
