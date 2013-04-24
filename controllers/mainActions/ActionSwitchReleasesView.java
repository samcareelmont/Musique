package musique.controllers.mainActions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import musique.models.mainModels.ModelSelectedReleasesView;

/**
 *
 * @author Sam Careelmont
 */

public class ActionSwitchReleasesView extends AbstractAction {
    String view;
    ModelSelectedReleasesView modelSelectedReleasesView;

    public ActionSwitchReleasesView(String name, String view, ModelSelectedReleasesView modelSelectedReleasesView) {
        super(name);
        this.view = view;
        this.modelSelectedReleasesView = modelSelectedReleasesView;
    }

    public void actionPerformed(ActionEvent e) {
        modelSelectedReleasesView.setSelectedView(view);
    }
}
