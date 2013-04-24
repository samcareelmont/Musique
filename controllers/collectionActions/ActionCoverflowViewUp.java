package musique.controllers.collectionActions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import musique.views.collectionWindowViews.ViewCollectionReleasesCoverflow;

/**
 *
 * @author Sam Careelmont
 */

public class ActionCoverflowViewUp extends AbstractAction {
    private ViewCollectionReleasesCoverflow coverflowView;

    public ActionCoverflowViewUp(ViewCollectionReleasesCoverflow coverflowView) {
        this.coverflowView = coverflowView;
    }

    public void actionPerformed(ActionEvent e) {
        coverflowView.moveUp();
    }
}
