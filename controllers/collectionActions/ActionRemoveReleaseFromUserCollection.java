package musique.controllers.collectionActions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelSelectedRelease;
import musique.models.collectionModels.ModelUserCollection;

/**
 *
 * @author Sam Careelmont
 */

public class ActionRemoveReleaseFromUserCollection extends AbstractAction {
    private ModelUserCollection modelUserCollection;
    private ModelSelectedRelease modelSelectedRelease;

    public ActionRemoveReleaseFromUserCollection(String name, IModelCollection modelUserCollection, ModelSelectedRelease modelSelectedRelease) {
        super(name);
        // Deze actie mag enkel Enabled zijn bij een UserCollection-venster
        if(modelUserCollection.getCollectionType().equals("UserCollection")) {
            this.modelUserCollection = (ModelUserCollection)modelUserCollection;
            this.modelSelectedRelease = modelSelectedRelease;
        } else {
            setEnabled(false);
        }
    }

    public void actionPerformed(ActionEvent e){
        // Als er een release geselecteerd is, wordt deze verwijderd
        if(modelSelectedRelease.getSelectedRelease() != null) {
            modelUserCollection.removeRelease(modelSelectedRelease.getSelectedRelease());
            modelSelectedRelease.setSelectedRelease(null);
        }
    }
}
