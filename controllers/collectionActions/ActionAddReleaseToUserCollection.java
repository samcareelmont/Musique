package musique.controllers.collectionActions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import musique.models.collectionModels.ModelSelectedRelease;
import musique.models.collectionModels.ModelUserCollection;

/**
 *
 * @author Sam Careelmont
 */

public class ActionAddReleaseToUserCollection extends AbstractAction {
    private ModelUserCollection modelUserCollection;
    private ModelSelectedRelease modelSelectedRelease;

    public ActionAddReleaseToUserCollection(String name, ModelUserCollection modelUserCollection, ModelSelectedRelease modelSelectedRelease) {
        super(name);
        this.modelUserCollection = modelUserCollection;
        this.modelSelectedRelease = modelSelectedRelease;
    }

    public void actionPerformed(ActionEvent e){
        // Als er een release geselecteerd is, wordt deze toegevoegd aan de UserCollectie
        if(modelSelectedRelease.getSelectedRelease() != null) {
            modelUserCollection.addRelease(modelSelectedRelease.getSelectedRelease());
        }
    }
}
