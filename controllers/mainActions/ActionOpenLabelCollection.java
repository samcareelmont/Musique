package musique.controllers.mainActions;

import musique.workers.SwingWorkerLoadLabelCollectionXml;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import musique.dao.DAOLabelCollection;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelLabelCollection;
import musique.models.collectionModels.ModelOpenUserCollections;
import musique.models.mainModels.ModelOpenWindows;
import musique.models.mainModels.ModelSelectedReleasesView;
import org.jdom.Document;
import org.jdom.JDOMException;

/**
 *
 * @author Sam Careelmont
 */

public class ActionOpenLabelCollection extends AbstractActionOpenCollection implements PropertyChangeListener {
    private SwingWorker<Document, Void> worker;

    public ActionOpenLabelCollection(String name, Icon icon, ModelSelectedReleasesView modelSelectedReleasesView, ModelOpenWindows modelOpenWindows, ModelOpenUserCollections modelOpenUserCollections) {
        super(name, icon, modelSelectedReleasesView, modelOpenWindows, modelOpenUserCollections);
    }

    public void actionPerformed(ActionEvent e) {
        // Toont een dialoogvenster waar je de naam van een label kan ingeven
        Object name = JOptionPane.showInputDialog(null, "Type the name of the label", "Open label", JOptionPane.DEFAULT_OPTION, new ImageIcon (ActionOpenArtistCollection.class.getResource("/musique/images/OpenLabelCollectionSmall.png")), null, null);
        if (name != null && !name.equals("")) {
             // Maak een worker aan die de XML van het label in de achtergrond kan laden
            worker = new SwingWorkerLoadLabelCollectionXml((String)name);
            worker.addPropertyChangeListener(this);
            worker.execute();
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName()) && worker.isDone()) {
            try {
                /**
                 *  Maak een Data Acces Object aan die de info uit het JDOMdocument
                 *  kan weergeven onder de vorm van java-objecten
                 */
                DAOLabelCollection daoLabelCollection = new DAOLabelCollection(worker.get());
                 // Maak het model aan die bij de collectie zal behoren
                IModelCollection modelCollection = new ModelLabelCollection(daoLabelCollection);
                // Roep de supermethode op die het venster zal initialiseren
                super.initCollectionFrame(modelCollection);
            } catch (InterruptedException ex) {
                // doe niets
            } catch (ExecutionException ex) {
                 JOptionPane.showMessageDialog(null, "Label doesn't exist", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon (ActionOpenArtistCollection.class.getResource("/musique/images/error.png")));
            } catch (JDOMException ex) {
                // doe niets
            }
        }
    }
}
