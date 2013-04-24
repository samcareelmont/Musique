package musique.views.collectionWindowViews;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import musique.models.collectionModels.ModelRelease;

/**
 *
 * @author Sam Careelmont
 */

public class RendererTree extends DefaultTreeCellRenderer {
    public RendererTree() {

    }

    // Methode die een icon plaatst bij elke release in de tree
    @Override
    public Component getTreeCellRendererComponent(JTree tree,Object value,boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if (leaf && node.getUserObject() instanceof ModelRelease) {
            ModelRelease nodeInfo = (ModelRelease)(node.getUserObject());
            if(!nodeInfo.getTrackList().isEmpty()) {
                setIcon(new ImageIcon(RendererTree.class.getResource("/musique/images/" + nodeInfo.getFormat().toString()+".png")));
            } else {
                 setIcon(new ImageIcon(RendererTree.class.getResource("/musique/images/buzy.png")));
            }
    }
        return this;
    }
}
