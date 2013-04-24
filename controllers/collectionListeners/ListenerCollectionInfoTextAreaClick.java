package musique.controllers.collectionListeners;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;

/**
 *
 * @author Sam Careelmont
 */

public class ListenerCollectionInfoTextAreaClick extends MouseAdapter {
    
    // Als er geklikt wordt op de TextArea wordt de area wit en enabled
    @Override
    public void mouseReleased(MouseEvent e) {
        JTextArea textArea = (JTextArea)e.getSource();
        textArea.setEditable(true);
        textArea.setFocusable(true);
        textArea.setBackground(new Color(255, 255, 255));
    }

}
