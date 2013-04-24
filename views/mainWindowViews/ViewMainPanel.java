package musique.views.mainWindowViews;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Sam Careelmont
 */

public class ViewMainPanel extends JPanel {
    public ViewMainPanel(Map<String, AbstractAction> actions) {
        setPreferredSize(new Dimension(450, 350));
        setLayout(new GridLayout(2,2, 10, 10));
        String[] neededActions = {"openArtistCollection", "openLabelCollection", "openUserCollection", "newUserCollection"};

        // Maak knoppen aan voor de gewilde acties
        for(String strAction : neededActions) {
            AbstractAction action = actions.get(strAction);
            JButton button = new JButton(action);
            button.setToolTipText(button.getText());
            button.setText("");
            add(button);
        }
    }
}
