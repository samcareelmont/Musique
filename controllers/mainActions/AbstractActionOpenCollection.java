package musique.controllers.mainActions;

import musique.controllers.collectionListeners.ListenerWindowCollection;
import musique.controllers.collectionListeners.ListenerWindowUserCollection;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelOpenUserCollections;
import musique.models.mainModels.ModelOpenWindows;
import musique.models.collectionModels.ModelSelectedRelease;
import musique.models.mainModels.ModelSelectedReleasesView;
import musique.models.collectionModels.ModelUserCollection;
import musique.views.collectionWindowViews.ViewCollectionFrame;

/**
 *
 * @author Sam Careelmont
 */

public abstract class AbstractActionOpenCollection extends AbstractAction {
    private ModelSelectedReleasesView modelSelectedReleasesView;
    private ModelOpenWindows modelOpenWindows;
    private ModelOpenUserCollections modelOpenUserCollections;

    public AbstractActionOpenCollection(String name, Icon icon, ModelSelectedReleasesView modelSelectedReleasesView, ModelOpenWindows modelOpenWindows, ModelOpenUserCollections modelOpenUserCollections) {
        super(name, icon);
        this.modelSelectedReleasesView = modelSelectedReleasesView;
        this.modelOpenWindows = modelOpenWindows;
        this.modelOpenUserCollections = modelOpenUserCollections;
    }

    public void initCollectionFrame(IModelCollection modelCollection) {
        // Maak een model aan die bijhoudt welke release geselecteerd is
        ModelSelectedRelease modelSelectedRelease = new ModelSelectedRelease();
        // Maak het frame aan
        ViewCollectionFrame collectionFrame = new ViewCollectionFrame(modelCollection, modelSelectedReleasesView, modelSelectedRelease, modelOpenUserCollections);
        // Luisteraars om bij te houden welke venster, resp. UserCollections er openstaan
        collectionFrame.addWindowListener(new ListenerWindowCollection(modelOpenWindows, modelCollection));
        modelOpenWindows.addWindow(collectionFrame);
                if(modelCollection.getCollectionType().equals("UserCollection")) {
            modelOpenUserCollections.addCollection((ModelUserCollection)modelCollection);
            collectionFrame.addWindowListener(new ListenerWindowUserCollection((ModelUserCollection)modelCollection, modelOpenUserCollections));
        }
    }
}
