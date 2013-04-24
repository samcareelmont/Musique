package musique.views.collectionWindowViews;

import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelSelectedRelease;
import musique.models.mainModels.ModelSelectedReleasesView;

/**
 *
 * @author Sam Careelmont
 */

public class ViewCollectionReleasesContainer extends JPanel implements ChangeListener {
    private JPanel cards;
    private ModelSelectedReleasesView modelSelectedReleasesView;

    public ViewCollectionReleasesContainer(IModelCollection modelCollection, ModelSelectedReleasesView modelSelectedReleasesView, ModelSelectedRelease modelSelectedRelease) {
        this.modelSelectedReleasesView = modelSelectedReleasesView;
        modelSelectedReleasesView.addChangeListener(this);
        setLayout(new CardLayout());

        // Maak de cards en voeg ze toe
        JPanel viewReleasesTab = new ViewCollectionReleasesTab(modelCollection, modelSelectedRelease, modelSelectedReleasesView);
        JPanel viewReleasesTree = new ViewCollectionReleasesTree(modelCollection, modelSelectedRelease, modelSelectedReleasesView);
        JPanel viewReleasesCoverflow = new ViewCollectionReleasesCoverflow(modelCollection, modelSelectedRelease, modelSelectedReleasesView);
        add(viewReleasesTab, "tab");
        add(viewReleasesTree, "tree");
        add(viewReleasesCoverflow, "coverflow");

        // Stel de juiste view in
        CardLayout cl = (CardLayout)(getLayout());
        cl.show(this, modelSelectedReleasesView.getSelectedView());
    }

    public void stateChanged (ChangeEvent e) {
        // Verander van view
        CardLayout cl = (CardLayout)(getLayout());
        cl.show(this, modelSelectedReleasesView.getSelectedView());
    }

}
