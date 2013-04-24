package musique.views.collectionWindowViews;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import musique.controllers.collectionActions.ActionAddReleaseToUserCollection;
import musique.controllers.collectionActions.ActionRemoveReleaseFromUserCollection;
import musique.controllers.collectionActions.ActionRenameUserCollection;
import musique.controllers.collectionActions.ActionSaveUserCollectionToFile;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelOpenUserCollections;
import musique.models.collectionModels.ModelSelectedRelease;
import musique.models.collectionModels.ModelUserCollection;

/**
 *
 * @author Sam Careelmont
 */

public class ViewCollectionMenu extends JMenuBar implements ChangeListener {
    private JMenu menuAddToCollection;
    private ModelOpenUserCollections modelOpenUserCollections;
    private ModelSelectedRelease modelSelectedRelease;
    private IModelCollection modelCollection;
    
    public ViewCollectionMenu(IModelCollection modelCollection, ModelOpenUserCollections modelOpenUserCollections, ModelSelectedRelease modelSelectedRelease) {
        this.modelCollection = modelCollection;
        this.modelOpenUserCollections = modelOpenUserCollections;
        this.modelSelectedRelease = modelSelectedRelease;
        modelOpenUserCollections.addChangeListener(this);
        
        //Actions
        JMenu menuActions = new JMenu("Actions");
        menuActions.setMnemonic(KeyEvent.VK_A);
            menuAddToCollection = new JMenu("Add to collection");
            menuActions.add(menuAddToCollection);
            listOpenUserCollections();

            JMenuItem menuRemoveFromCollection = new JMenuItem(
                        new ActionRemoveReleaseFromUserCollection("Remove from collection", modelCollection, modelSelectedRelease));
            menuActions.add(menuRemoveFromCollection);
            JMenuItem menuRename = new JMenuItem(
                        new ActionRenameUserCollection("Rename", modelCollection));
            menuActions.add(menuRename);
            JMenuItem menuSaveCollectionToFile = new JMenuItem(
                        new ActionSaveUserCollectionToFile("Save collection to file", modelCollection));
            menuActions.add(menuSaveCollectionToFile);

            menuActions.add(menuSaveCollectionToFile);
        add(menuActions);
    }

    public void stateChanged (ChangeEvent e) {
        listOpenUserCollections();
    }

    // Methode die alle open UserCollecions zal toevoegen aan de correcte menu
    public void listOpenUserCollections() {
        menuAddToCollection.removeAll();
        ArrayList<ModelUserCollection> openUserCollections = modelOpenUserCollections.getOpenCollections();
        if(openUserCollections.size() > 0) {
            menuAddToCollection.setEnabled(true);
            for(ModelUserCollection modelUserCollection : openUserCollections) {
                JMenuItem menuItemUserCollection = new JMenuItem(
                    new ActionAddReleaseToUserCollection(modelUserCollection.getName(), modelUserCollection, modelSelectedRelease));
                menuAddToCollection.add(menuItemUserCollection);
                // Toevoegen aan de collectie die openstaat in onmogelijk
                if(modelUserCollection == modelCollection) {
                    menuItemUserCollection.setEnabled(false);
                }
            }
        } else {
            menuAddToCollection.setEnabled(false);
        }
    }
}
