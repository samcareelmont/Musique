package musique.controllers.mainActions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import musique.views.collectionWindowViews.ViewCollectionFrame;

/**
 *
 * @author Sam Careelmont
 */

public class ActionActivateWindow extends AbstractAction {
    private ViewCollectionFrame cf;

    public ActionActivateWindow(String name, ViewCollectionFrame cf) {
        super(name);
        this.cf = cf;
    }

    public void actionPerformed(ActionEvent e) {
        cf.setVisible(true);
    }
}