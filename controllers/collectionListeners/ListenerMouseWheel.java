package musique.controllers.collectionListeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import musique.views.collectionWindowViews.ViewCollectionReleasesCoverflow;

/**
 *
 * @author Sam Careelmont
 */

public class ListenerMouseWheel implements MouseWheelListener {
    private ViewCollectionReleasesCoverflow coverflowView;

    public ListenerMouseWheel(ViewCollectionReleasesCoverflow coverflowView) {
        this.coverflowView = coverflowView;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            coverflowView.moveUp();
        } else {
            coverflowView.moveDown();
        }        
    }
}
