package musique.controllers.collectionListeners;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import musique.models.collectionModels.ModelUserCollection;

/**
 *
 * @author Sam Careelmont
 */

public class ListenerCollectionInfoClick extends MouseAdapter {
    private JTextArea textArea;
    private Color bg;
    private ModelUserCollection modelUserCollection;

    public ListenerCollectionInfoClick(JTextArea textArea, Color bg, ModelUserCollection modelUserCollection) {
        this.textArea = textArea;
        this.bg = bg;
        this.modelUserCollection = modelUserCollection;
    }

    // Als er geklikt wordt naast de TextArea wordt de area grijs en disabled
    @Override
    public void mouseReleased(MouseEvent e) {
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(bg);
        modelUserCollection.setDescription(textArea.getText());
    }
}
