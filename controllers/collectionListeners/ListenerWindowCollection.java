package musique.controllers.collectionListeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import musique.models.collectionModels.IModelCollection;
import musique.models.mainModels.ModelOpenWindows;
import musique.views.collectionWindowViews.ViewCollectionFrame;

/**
 *
 * @author Sam Careelmont
 */

public class ListenerWindowCollection extends WindowAdapter {
    private ModelOpenWindows modelOpenWindows;
    private IModelCollection modelCollection;

    public ListenerWindowCollection(ModelOpenWindows modelOpenWindows, IModelCollection modelCollection) {
        this.modelOpenWindows = modelOpenWindows;
        this.modelCollection = modelCollection;
    }

    // Als een venster gesloten wordt, verdwijnt het uit de lijst van open vensters
    @Override
    public void windowClosing(WindowEvent e) {
        modelOpenWindows.removeWindow((ViewCollectionFrame)e.getWindow());
        // Stop de thread die de releases inlaadt van de collectie behorende bij dit venster
        modelCollection.stopWorker();
    }
}
