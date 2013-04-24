package musique.controllers.mainActions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Sam Careelmont
 */

public class ActionExit extends AbstractAction {
    public ActionExit(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}