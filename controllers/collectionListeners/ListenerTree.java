package musique.controllers.collectionListeners;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import musique.models.collectionModels.ModelRelease;
import musique.models.collectionModels.ModelSelectedRelease;

/**
 *
 * @author Sam Careelmont
 */

public class ListenerTree implements TreeSelectionListener {
    ModelSelectedRelease modelSelectedRelease;

    public ListenerTree(ModelSelectedRelease modelSelectedRelease) {
        this.modelSelectedRelease = modelSelectedRelease;
    }

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
        if (node != null) {
            Object nodeInfo = node.getUserObject();
            if (node.isLeaf()) {
                // Haal de release uit de geselecteerde node
                ModelRelease release = (ModelRelease)nodeInfo;
                 // Maak aan modelSelectedRelease duidelijk welke release nu geselecteerd is
                modelSelectedRelease.setSelectedRelease(release);
            } else {
                 // Als er geen blad geselecteerd werd, wordt de geselecteer release op null geplaatst
                modelSelectedRelease.setSelectedRelease(null);
            }
        }
    }

}
