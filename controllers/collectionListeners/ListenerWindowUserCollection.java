package musique.controllers.collectionListeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import musique.models.collectionModels.ModelOpenUserCollections;
import musique.models.collectionModels.ModelUserCollection;

/**
 *
 * @author Sam Careelmont
 */

public class ListenerWindowUserCollection extends WindowAdapter {
    private ModelUserCollection modelUserCollection;
    private ModelOpenUserCollections modelOpenUserCollections;

    public ListenerWindowUserCollection(ModelUserCollection modelUserCollection, ModelOpenUserCollections modelOpenUserCollections) {
        this.modelOpenUserCollections = modelOpenUserCollections;
        this.modelUserCollection = modelUserCollection;
    }

    // Als een UserCollection gesloten wordt, verdwijnt het uit de lijst van open UserCollections
    @Override
    public void windowClosing(WindowEvent e) {
        modelOpenUserCollections.removeCollection(modelUserCollection);
    }

}
