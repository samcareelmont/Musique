package musique.views.collectionWindowViews;

import java.awt.BorderLayout;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import musique.controllers.collectionListeners.ListenerTree;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelRelease;
import musique.models.collectionModels.ModelSelectedRelease;
import musique.models.mainModels.ModelSelectedReleasesView;

/**
 *
 * @author Sam Careelmont
 */

public class ViewCollectionReleasesTree extends AbstractViewCollectionReleases implements ChangeListener {
    private SortedMap<Integer,DefaultMutableTreeNode> mapNodes;
    private JTree jTree;
    private DefaultTreeModel treeModel;
    
    public ViewCollectionReleasesTree(IModelCollection modelCollection, ModelSelectedRelease modelSelectedRelease, ModelSelectedReleasesView modelSelectedReleasesView) {
        super(modelCollection, modelSelectedRelease, modelSelectedReleasesView);

        mapNodes = new TreeMap<Integer,DefaultMutableTreeNode>(new ComparatorYear());

        // Maak JTree aan
        treeModel = new DefaultTreeModel(null);
        jTree = new JTree(treeModel);        
        RendererTree renderer = new RendererTree();
        jTree.setCellRenderer(renderer);
        jTree.addTreeSelectionListener(new ListenerTree(modelSelectedRelease));

        // Voeg de tree toe aan het paneel
        setLayout(new BorderLayout());
        add(new JScrollPane(jTree), BorderLayout.CENTER);

        refreshTree();
    }

    public void refreshTree()  {
        // Sla de geselecteerde release op
        ModelRelease selectedRelease = modelSelectedRelease.getSelectedRelease();

         // Wis alle jLists en de tabs
        jTree.removeAll();
        mapNodes.clear();
        
        treeModel.setRoot(new DefaultMutableTreeNode ("root"));

        // Overloop alle releases en voeg deze toe zodat mapNodes gevuld wordt
        for(int i = 0; i < modelCollection.getNumberOfReleases(); i++) {
           addRelease(modelCollection.getRelease(i));
        }

        // Overloop nu de map en voeg elke node toe aan de root
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
        for(DefaultMutableTreeNode node : mapNodes.values()) {
            root.add(node);            
        }

        // Klap de volledige tree open
        jTree.setRootVisible(false);
        // Overloop alle kinderen van de root-node
        for(int i = 0; i < root.getChildCount(); i++) {
            // Klap het pad open
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)root.getChildAt(i);
            jTree.expandPath(new TreePath(node.getPath()));
            // Als er een geselecteerde release bestaat, dient de node die deze release bevat, geselecteer te worden
            if(selectedRelease != null) {
                int j = 0;
                // Zoek de plaats waar de geselecteerde release voorkomt
                while(j < node.getChildCount() &&
                        ((ModelRelease)((DefaultMutableTreeNode)node.getChildAt(j)).getUserObject()).getId() != selectedRelease.getId()) {
                        j++;
                }
                // Selecteer dit pad
                if(j != node.getChildCount()) {
                    jTree.setSelectionPath( new TreePath(((DefaultMutableTreeNode)node.getChildAt(j)).getPath()));
                }
            }
        }      
    }

    /*
     * Methode die een release zal toevoegen aan de juiste node
     * Dit gebeurt in een map zodat de nodes later vlot overlopen kunnen worden
     */
    public void addRelease(ModelRelease release) {
        int year = release.getYear();
        // Er bestaat reeds een node voor dit jaartal
        if (mapNodes.containsKey(year)) {
            mapNodes.get(year).add((new DefaultMutableTreeNode (release)));
        // Er bestaat nog geen node voor dit jaartal
        } else {
            DefaultMutableTreeNode newNode;

            if(year == -1) {
                newNode = new DefaultMutableTreeNode ("Unknown");
            } else {
                newNode = new DefaultMutableTreeNode (year);
            }
            mapNodes.put(year, newNode);
            newNode.add(new DefaultMutableTreeNode (release));
        }
    }

    public void stateChanged (ChangeEvent e) {
         // Reageer alleen op wijziging als deze view geselecteerd is
        if(modelSelectedReleasesView.getSelectedView().equals("tree")) {
            refreshTree();
        }
    }     
}
