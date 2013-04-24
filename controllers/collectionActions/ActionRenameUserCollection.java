package musique.controllers.collectionActions;

import musique.controllers.mainActions.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelUserCollection;

/**
 *
 * @author Sam Careelmont
 */

public class ActionRenameUserCollection extends AbstractAction {
    private ModelUserCollection modelUserCollection;

    public ActionRenameUserCollection(String name, IModelCollection modelUserCollection) {
        super(name);
        // Deze actie mag enkel Enabled zijn bij een UserCollection-venster
        if(modelUserCollection.getCollectionType().equals("UserCollection")) {
            this.modelUserCollection = (ModelUserCollection)modelUserCollection;
        } else {
            setEnabled(false);
        }
    }

    public void actionPerformed(ActionEvent e){
        // Toont een dialoogvenster waar je een nieuwe naam kan ingeven
        Object name = JOptionPane.showInputDialog(null, "Type the name of your collection", "Rename", JOptionPane.DEFAULT_OPTION, new ImageIcon (ActionOpenArtistCollection.class.getResource("/musique/images/rename.png")), null, null);
        if (name != null && !name.equals("")) {
            modelUserCollection.setName((String)name);
        }
    }
}
