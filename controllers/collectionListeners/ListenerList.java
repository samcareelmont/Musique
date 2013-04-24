package musique.controllers.collectionListeners;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import musique.models.collectionModels.ModelRelease;
import musique.models.collectionModels.ModelSelectedRelease;

/**
 *
 * @author Sam Careelmont
 */

public class ListenerList implements ListSelectionListener {
    private ModelSelectedRelease modelSelectedRelease;
    
    public ListenerList(ModelSelectedRelease modelSelectedRelease) {
        this.modelSelectedRelease = modelSelectedRelease;
    }

    public void valueChanged(ListSelectionEvent e) {
        // Er mag enkel iets gebeuren als de gebruiker klaar is met selecteren
        if (!e.getValueIsAdjusting()) {
            JList jList = (JList)e.getSource();
            // Er mag enkel iets gebeuren als de er iets geselecteerd is in de lijst
            if(!jList.isSelectionEmpty()) {                
                DefaultListModel modelList = (DefaultListModel)jList.getModel();
                // Vraag de release op die bij de geselecteerde index hoort
                ModelRelease modelRelease = (ModelRelease)modelList.get(jList.getSelectedIndex());
                // Maak aan modelSelectedRelease duidelijk welke release nu geselecteerd is
                modelSelectedRelease.setSelectedRelease(modelRelease);
            }
        }
    }
}
