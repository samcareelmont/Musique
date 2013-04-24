package musique.models.collectionModels;

import musique.dao.DAOLabelCollection;
import org.jdom.JDOMException;

/**
 *
 * @author Sam Careelmont
 */

public class ModelLabelCollection extends AbstractModelCollection {
    public ModelLabelCollection(DAOLabelCollection daoLabelCollection) throws JDOMException {
        super(daoLabelCollection);
    }

    @Override
    public String getCollectionType() {
        return "LabelCollection";
    }
    
}
